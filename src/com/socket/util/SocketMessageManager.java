package com.socket.util;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;

import org.java_websocket.framing.CloseFrame;

import com.alibaba.fastjson.JSONObject;
import com.api.core.Config;
import com.socket.util.KKSocket;

//import android.util.Log;

//import android.R.integer;
//import android.content.Context;

/**
 * 管理socket消息发送队列
 * */
public class SocketMessageManager implements IWebSocketListener{

	private final String TAG = "SocketMessageManager";
	
	private static final int SOCKET_TIMEOUT = 20000;
	private int KEY_USERID=-1;
	
	public ArrayList<JSONObject> msgList=new ArrayList<JSONObject>();
	private Config config = null;
	//2个消息队列和线程
	private MessageInThread mMsgInThread = null;
	private MessageOutThread mMsgOutThread = null;
	//private RoomMessageListener mRoomListener;
	private MeShowSocket mSocket = null;
	private Context mContext;
	
	private Timer mTimer ;
	//心跳包时间间隔
	private final static long ACTOR_SEND_HEARTBEAT_PERIOD = 5 * 1000;
	
	private final static long USER_SEND_HEARTBEAT_PERIOD = 60 * 1000;
	private static final int MAX_REPEATE = 5;
	private long heartBeatPeriod = USER_SEND_HEARTBEAT_PERIOD;
	private String socketUrl = null;
	private int repeatCount = 0;
	private Timer mReconnectTimer = null;
	/**这个环节所导致的失败类型*/
	public class ErrorType
	{
		/**socket 初始化失败*/
		public static final int SOCKET_INIT_FAILED = 001;
		/**room url = null频道不存在*/
		public static final int ROOM_URL_NULL = 002;
		/**发送各种请求失败*/
		public static final int SEND_FAILED = 101;
		/**close*/
		public static final int ON_CLOSE = 201;
	}
	
	private long mRoomId;
	
	public enum CONNECT_STATE
	{
		CONNECTING,CONNECTED,CLOSED,NONE
	}
	//初始化CLOSED的话 进频道连2次socket
	private CONNECT_STATE connectState = CONNECT_STATE.NONE;
	public boolean needReConnect()
	{
		return connectState == CONNECT_STATE.CLOSED;
	}
	
	public boolean isConnected()
	{
		return connectState == CONNECT_STATE.CONNECTED;
	}
	
//	public SocketMessageManager(Context con, Config cf)
//	{
//	//	Log.i(TAG, "SocketMessageManager init");
//		mContext = con;
//		this.mRoomId = cf.getRoomid();
//		this.config=cf;
//	}
	
	public void setRoomListener(RoomMessageListener l)
	{
//		Log.i(TAG, "setRoomListener:"+l);
	//	mRoomListener = l;
	}
	
	public void destroy()
	{
		repeatCount = 0;
	//	mRoomListener = null;
		release();
	}
	
	public void release()
	{
		if(mTimer != null)
			mTimer.cancel();
		mTimer = null;
		stopReConnectTimer();
		connectState = CONNECT_STATE.CLOSED;
//		Log.v(TAG, "release");
		if(mSocket != null)
			mSocket.destroy();
		mSocket = null;
//		mRoomListener = null;
		if(mMsgInThread != null)
			mMsgInThread.release();
		mMsgInThread = null;
		if(mMsgOutThread != null)
			mMsgOutThread.release();
		mMsgOutThread = null;
	}
	
	
	/**初始化一个socket，来获取room url ,然后初始化mSocket，ok*/
	public void initConnection(String wsStr)
	{
		//heartBeatPeriod = (Global.currentRoomId == Setting.getInstance().getUserId()) ? ACTOR_SEND_HEARTBEAT_PERIOD : USER_SEND_HEARTBEAT_PERIOD;
		heartBeatPeriod =  ACTOR_SEND_HEARTBEAT_PERIOD;
		if(mSocket != null)
			mSocket.destroy();
		mTimer = new Timer();
		socketUrl = wsStr;
	//	Log.v(TAG, "initConnection "+socketUrl);
		try {
			mSocket = new MeShowSocket(socketUrl, SOCKET_TIMEOUT);
	//		Log.v(TAG, "connecting..."+socketUrl);
			mSocket.setWebSocketListener(this);
			
			mSocket.connect();
			connectState = CONNECT_STATE.CONNECTING;
			int time=0;
			while(!mSocket.isOpen()&&time<10){
				Thread.sleep(1000);
				time++;
			}
			
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
//			if(mRoomListener != null)
//				mRoomListener.onError(ErrorType.SOCKET_INIT_FAILED, -1);
		}catch (Exception e) {
			e.printStackTrace();
//			if(mRoomListener != null)
//				mRoomListener.onError(ErrorType.SOCKET_INIT_FAILED, -1);
		}
	}
	
	public void sendMessage(String msg)
	{
		//Log.i(TAG, "sendMessage->"+msg);
		//Log.i("SeeNetMsg", "socket sendMessage->"+msg);
		System.out.println("socket sendMessage->"+msg);
		if(mMsgOutThread != null)
			mMsgOutThread.addTask(msg);
	//	else
		//	Log.e(TAG, "mMsgOutThread null");
	}
	
	@Override
	public void onMessage(String message) {
//		Log.i(TAG, "message->"+message);
		System.out.println(mMsgInThread.getId()+ "_receivemsg="+message);
		msgList.add(JSONObject.parseObject(message));
		if(mMsgInThread != null)
			mMsgInThread.addTask(message);
	}
	
	public ArrayList<JSONObject> getMsgList(){
		return msgList;
	}

	@Override
	public void onOpen() {
//		Log.i(TAG, "onOpen");
		
		connectState = CONNECT_STATE.CONNECTED;
		mMsgInThread = new MessageInThread(mContext);
		mMsgInThread.setName("MessageInThread:"+mRoomId);
		mMsgInThread.setSocketState(connectState);
		mMsgOutThread = new MessageOutThread(mContext,mSocket);
		mMsgOutThread.setName("MessageOutThread:"+mRoomId);
//		if(mRoomListener != null)
//			mRoomListener.onConnected();
		setUserId(config.getUserId());
		SocketMessagFormer.config=this.config;
		String loginMsg = SocketMessagFormer.createLoginRoomMsg(mRoomId, null);
		mMsgOutThread.addTask(loginMsg);
		if(repeatCount > 0)
			mMsgOutThread.addTask(SocketMessagFormer.requestOnLiveList());
		stopReConnectTimer();
		//开始定时发送心跳包
		if(mTimer != null)
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					String msg = SocketMessagFormer.createHeartbeatMsg();
					if (mMsgOutThread != null) {
						mMsgOutThread.addTask(msg);
					}				
				}
			}, 0, heartBeatPeriod);
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		connectState = CONNECT_STATE.CLOSED;
		if(mMsgInThread != null)
			mMsgInThread.setSocketState(connectState);
	//	Log.e(TAG, "onClose->("+code+","+reason+","+remote+"),repeatCount = " + repeatCount);
		if(code > 0 && code != CloseFrame.NORMAL)
			//UtilActionEvent.insertDisconnectInfo(mContext,ErrorCode.ERROR_DISCONNECT_IO,code,reason);
		if(repeatCount > MAX_REPEATE - 1 || code == CloseFrame.NORMAL){
//			if(mRoomListener != null)
//				mRoomListener.onError(ErrorType.ON_CLOSE, -1);
			stopReConnectTimer();
		}else if(repeatCount == 0){
			startReConnectTimer();
		}
	}
	
	private void startReConnectTimer() {
//		if (mReconnectTimer == null && Global.currentRoomId == Setting.getInstance().getUserId()) {
//			mReconnectTimer = new Timer();
//			mReconnectTimer.schedule(new ReconnectTask(), 0,heartBeatPeriod);
//		}
	}
	
	private void stopReConnectTimer(){
		if(mReconnectTimer != null){
			mReconnectTimer.cancel();
		}
		mReconnectTimer = null;
		repeatCount = 0;
	}
	public void setUserId(int userid){
		this.KEY_USERID=userid;
	}
	public int getUserId(){
		return this.KEY_USERID;
	}
	private class ReconnectTask extends TimerTask {
		@Override
		public void run() {
			if(repeatCount < MAX_REPEATE){
				initConnection(socketUrl);
				repeatCount++;
			}
		}
	};
	@Override
	public void onError(Exception ex) {
	//	Log.e(TAG, "onError:"+ex + ",repeatCount = " + repeatCount);
//		if(repeatCount > MAX_REPEATE - 1)
//			if(mRoomListener != null)
//				mRoomListener.onError(ex);
	}
	
}
