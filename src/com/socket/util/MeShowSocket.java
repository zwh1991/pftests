package com.socket.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

//import common.tools.HttpResponds;
//import common.tools.config;
//import common.web.HttpConnect;

/**socket 通用类,根据socketFactory产生特定的socket*/

public class MeShowSocket extends WebSocketClient {

	private IWebSocketListener mListener;
	
	public MeShowSocket(String url) throws URISyntaxException {
		super(new URI(url));
	}
	
	public MeShowSocket(String url, int connectTimeout) throws URISyntaxException {
		super(new URI(url), new Draft_17(), null, connectTimeout);
	}
	 
	public void setWebSocketListener(IWebSocketListener l)
	{
		mListener = l;
	}
	
	void destroy()
	{
		mListener = null;
		close();
	}
	
	@Override
	public void onMessage(String message) {
		if(mListener != null)
			mListener.onMessage(message);
	}

	@Override
	public void onError(Exception ex) {
		if(mListener != null)
			mListener.onError(ex);
	}
	
	@Override
	public void onOpen(ServerHandshake handshakedata) {
		if(mListener != null)
			mListener.onOpen();
	}
	@Override
	public void onClose(int code, String reason, boolean remote) {
		if(mListener != null)
			mListener.onClose( code,  reason,  remote);
	}

	
//public static void addGiftOnline(TConfig config,int giftid,String giftname,int giftprice,int giftcount,int userid) throws HttpException, IOException{
//      	
//      	String reg1=giftid+" "+giftname+" ("+giftprice+"币)";
//      	reg1=URLEncoder.encode(reg1,"UTF-8");
//      	String reg2="提交";
//      	reg2=URLEncoder.encode(reg2,"UTF-8");
//      	String reg="giftId="+giftid+"&giftCount="+giftcount+"&giftAppId=1&giftName="+reg1+"&userId="+userid+"&data=save&dosubmit="+reg2;
//      	//config.putpostbackgroudHearderlis("Referer", "http://10.0.0.7:9494/meShowAdmin/queryUserStore.action?data=sendGift&userIds=7516988&giftAppId=1");
//    	HttpResponds sessionstr=HttpConnect.sendPostHeader(config.backgroudBaseUrl+"/meShowAdmin/queryUserStore.action",reg,config.getpostbackgroudHearderlis());
//    	System.out.println(sessionstr.getCode());
//    	System.out.println(config.getpostbackgroudHearderlis());
//	}
	
}