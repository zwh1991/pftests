package com.socket.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;

import junit.framework.TestCase;

import com.alibaba.fastjson.JSONObject;

import com.api.core.Config;
import com.web.execution.HttpConnect;

public class KKSocket extends TestCase {
	Context con;
	//如果flag=1那么登陆成功，其他的代码可以用SocketMessageManager.flag去查看登陆是否成功
	static public int flag=0;
//
//	public SocketMessageManager userLogin(int loginType,int platform,int userId, String password, int roomid) throws InterruptedException{
//		//String result=HttpConnect.sendPostUrlParam(config.getGetTokenUrl()+"{\"FuncTag\":\"10000001\",\"loginType\":-3,\"platform\":2,\"psword\":\"kk83293960\",\"userId\":7506620}");
//		Config config=new Config();
//		String result=HttpConnect.sendPostUrlParam("http://10.0.0.105:9090/kkworld/entrance"
//				,"{\"FuncTag\":\"10000001\",\"loginType\":"+loginType+",\"platform\":"+platform+",\"psword\":"+password+",\"userId\":"+userId+"}");
//		JSONObject rjs=JSONObject.parseObject(result);
//		//这里setToken和setUserId在onopen的roomlogin里面 要用，roomlogin直接从config里面取token和userid
//		//下面的设置是为了smm.initConnection(ws);时,里面连接建立后自动回调的onopen函数里面有createLoginRoomMsg接口自动登陆用.必须要设置一下
//		config.setToken(rjs.getString("token"));
//		config.setUserId(userId);
//		config.setRoomid(roomid);
//		String ws=config.getWs(config.getUrl(), roomid,userId,"2","100031");
//		SocketMessageManager smm=new SocketMessageManager(con,config);
//		
//		smm.initConnection(ws);
//		return smm;
//		
//	}
	public SocketMessageManager messageSend(SocketMessageManager smm,int MsgTag,String msg) throws InterruptedException{
		
		//smm.sendMessage("{\"MsgTag\":10010211,\"liveType\":2}");
		smm.sendMessage(msg);
		//System.out.println(msg);
		int time=0;
		flag=0;
		while(time<=10){
			for(JSONObject jo:smm.msgList){
				if(jo.getIntValue("MsgTag")==MsgTag||flag>=10){
					flag=1;
					System.out.println("checkmessage="+jo);
					break;
				}
			}
			if(flag==1){
				break;
			}
			else{
				Thread.sleep(1000);
				time++;
			}
		}

		//System.out.println(smm.msgList);
		return smm;
	}
	
	public SocketMessageManager videoPlay(SocketMessageManager smm) throws InterruptedException{
		
		//smm.sendMessage("{\"MsgTag\":10010211,\"liveType\":2}");
		messageSend(smm,10010210,"{\"MsgTag\":10010210,\"liveType\":2,\"roomMode\":1,\"liveScene\":1}");
		
		return smm;
	}
	
	public SocketMessageManager videoStop(SocketMessageManager smm) throws InterruptedException{
		
		//smm.sendMessage("{\"MsgTag\":10010211,\"liveType\":2}");
		messageSend(smm,10010211,"{\"MsgTag\":10010211,\"liveType\":2}");
		
		return smm;
	}
	
	public SocketMessageManager kickout(SocketMessageManager smm,int userId, int interval) throws InterruptedException{
		
		//smm.sendMessage("{\"MsgTag\":10010211,\"liveType\":2}");
		messageSend(smm,10010223,"{\"MsgTag\":10010223,\"userId\":"+userId+",\"interval\":"+interval+"}");
		
		return smm;
	}
	
	public SocketMessageManager chatSend(SocketMessageManager smm,int contentType,int chatType,int object_userId,String content) throws InterruptedException{
		
		//smm.sendMessage("{\"MsgTag\":10010211,\"liveType\":2}");
		messageSend(smm,10010209,"{\"MsgTag\":10010209,\"contentType\":"+contentType+",\"chatType\":"+chatType+",\"dUserId\":"+object_userId+",\"content\":\""+content+"\"}");
		
		return smm;
	}
//	
//	public SocketMessageManager userLogin1(int loginType,int platform,int userId, String password, int roomid,int MsgTag,String msg) throws InterruptedException{
//		Config config=new Config();
//		//String result=HttpConnect.sendPostUrlParam(config.getGetTokenUrl()+"{\"FuncTag\":\"10000001\",\"loginType\":-3,\"platform\":2,\"psword\":\"kk83293960\",\"userId\":7506620}");
//		String result=HttpConnect.sendPostUrlParam("http://10.0.0.105:9090/kkworld/entrance"
//				,"{\"FuncTag\":\"10000001\",\"loginType\":"+loginType+",\"platform\":"+platform+",\"psword\":"+password+",\"userId\":"+userId+"}");
//		JSONObject rjs=JSONObject.parseObject(result);
//		//这里setToken和setUserId在onopen的roomlogin里面 要用，roomlogin直接从config里面取token和userid
//		
//		config.setToken(rjs.getString("token"));
//		config.setUserId(userId);
//		config.setRoomid(roomid);
//		String ws=config.getWs(config.getUrl(), config.getRoomid(),config.getUserId(),"2","100031");
//
//		SocketMessageManager smm=new SocketMessageManager(con,config);
//		smm.initConnection(ws);
//		smm.sendMessage(msg);
//		int time=0;
//		flag=0;
//		while(time<=10){
//			for(JSONObject jo:smm.msgList){
//				if(jo.getIntValue("MsgTag")==MsgTag||flag>=10){
//					flag=1;
//					break;
//				}
//			}
//			if(flag==1){
//				break;
//			}
//			else{
//				Thread.sleep(1000);
//				time++;
//			}
//		}
//
//		System.out.println(smm.msgList);
//		return smm;
//		
//	}
//	
//	public static void assertMsg(String message){
//		JSONObject jo = new JSONObject();
//		if (!message.isEmpty()){
//			jo = JSONObject.parseObject(message);
//		}
//		
//		switch (jo.getIntValue("MsgTag")){
//		case 10010206:
//			System.err.println("----");
//			break;
//		case 10010202:
//			System.err.println("----");
//			break;
//		case 10010352:
//			System.err.println("----");
//			break;
//		case 20020123:
//			System.err.println("----");
//			break;
//		}
		
//	}

}
