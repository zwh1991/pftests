package com.socket.util;

import java.io.IOException;

import javax.naming.Context;

import org.java_websocket.exceptions.WebsocketNotConnectedException;
//import org.json.JSONException;
//import org.json.JSONObject;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

//import alex.zhrenjie04.wordfilter.WordFilterUtil;
//import android.content.Context;

//import com.melot.meshow.sns.socketparser.SocketMsgParser;
//import com.melot.meshow.util.Log;

/**发送消息的一个线程*/
public class MessageOutThread extends MessageThread {

	private final static String TAG = "MessageOutThread";
	private	MeShowSocket mSocket;
	private RoomMessageListener mListener;
	private Context mContext;
//	public MessageOutThread(Context con,RoomMessageListener listener,MeShowSocket socket)
//	{
//		mSocket = socket;
//		mListener = listener;
//		mContext = con;
//	}
	public MessageOutThread(Context con,MeShowSocket socket)
	{
		mSocket = socket;
//		mListener = listener;
		mContext = con;
	}
//	@Override
//	protected void addTask(String msg) {
//		if(null == mMessageQueue)
//			return;
//		if(mMessageQueue.contains(msg))
//		{
//			Log.e(TAG, "MessageOutThread has contain this msg task->"+msg);
//			return;
//		}
//		super.addTask(msg);
//	}
	@Override
	protected void doTask(String msg) {
	//	Log.i(TAG, "doTask->"+msg);
		
		if(msg == null || mSocket == null)
			return;
	//	android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
		try {
			if(msg.indexOf("\""+SocketMessagFormer.KEY_MSG_TAG+"\":"+SocketMessagFormer.MessageType.SEND_TXT) != -1)
			{
//	//			WordFilterUtil.filterText(msg, '*');
//	//			发送消息
//				if(!WordFilterUtil.isInited())
//				{
//					try {
//						WordFilterUtil.init(mContext.getAssets().open("kktv/words.dict"));
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				msg = WordFilterUtil.filterText(msg, '*').getFilteredContent();
			}
			mSocket.send(msg);
		} catch (WebsocketNotConnectedException e) {
			e.printStackTrace();
			if(mListener != null)
			{
				JSONObject jo = null;
				try {
					jo = JSONObject.parseObject(msg);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				//mListener.onError(SocketMessageManager.ErrorType.SEND_FAILED, SocketMsgParser.getMessageType(jo));
			}
		} 
	}

}
