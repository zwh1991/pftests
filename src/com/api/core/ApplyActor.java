package com.api.core;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;






import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.Headers;
import com.web.execution.HttpResponds;
import com.web.execution.HttpConnect;
import com.api.core.ApiEnum.AppId;
import com.api.core.Config;


public class ApplyActor {
	static public void setSessionId(String user,String password,
			Config config) throws HttpException, IOException{

		getSession(user,password,config.getpostbackgroudHearderlis(),config);
		config.putpostbackgroudHearderlis("Cookie", 
				config.getSessionidName()+"="+config.getSessionidvalue());
//		String reg2="登录";
//      	reg2=URLEncoder.encode(reg2,"UTF-8");
//      	String res1="dosubmit=&username="+user+"&password="+password;
//		HttpResponds sessionstr = HttpConnect.sendPostHeader(config.getBackGroudurl()
//				+";"+config.getSessionidName()
//				+"="+config.getSessionidvalue(),res1,
//				config.getpostbackgroudHearderlis());
//				
//		System.out.println(sessionstr.getbody());
	}
	static public void setMeshowSessionId(String user,String password,Config Config) throws HttpException, IOException{

		getMeshowSession(user,password,Config.getpostbackgroudHearderlis(),Config);
		if(null==Config.getSessionidvalue()){
			return;
		}
		Config.putpostbackgroudHearderlis("Cookie", 
				Config.getSessionidName()+"="+Config.getSessionidvalue());
		//HttpResponds sessionstr=HttpConnect.sendPostHeader(config.getMeshowUrl()+";"+config.getSessionidName()+"="+config.getSessionidvalue(),"loginName="+user+"&passWord="+password,config.getpostbackgroudHearderlis());
				
		//System.out.println(sessionstr.getbody());
	}
	static public void getMeshowSession(String user,String password,JSONObject head,Config Config) throws HttpException, IOException{
		HttpResponds sessionstr=HttpConnect.sendPostHeader(
				Config.backgroudBaseUrl,"loginName="+user+"&passWord="+password, head);
				//Config.getMeshowUrl()+"/meShowAdmin/login.action?method=login","dosubmit=&username="+user+"&password="+password,head);
		//System.out.println("loginName="+user+"&passWord="+password);
		String jsessionid = "";
		for(Header tp:sessionstr.getHeaderList()){
			if(tp.getName().equals("Set-Cookie")){
				jsessionid=tp.getValue();
				jsessionid=jsessionid.split(";")[0];
				//System.out.println("\n"+jsessionid);
				//System.out.println("\nbody="+sessionstr.getbody());
				Config.setSessionidName(jsessionid.split("=")[0]);
				Config.setSessionidvalue(jsessionid.split("=")[1]);
			}
		}

	}
public static void addGiftOnline(Config config,int giftid,String giftname,int giftprice,int giftcount,int userid) throws HttpException, IOException{
      	
      	String reg1=giftid+" "+giftname+" ("+giftprice+"币)";
      	reg1=URLEncoder.encode(reg1,"UTF-8");
      	String reg2="提交";
      	reg2=URLEncoder.encode(reg2,"UTF-8");
      	String reg="giftId="+giftid
      			  +"&giftCount="+giftcount
      			  +"&giftAppId=1&giftName="
      			  +reg1+"&userId="
      			  +userid+"&data=save&dosubmit="
      			  +reg2;
      	//config.putpostbackgroudHearderlis("Referer", "http://10.0.0.7:9494/meShowAdmin/queryUserStore.action?data=sendGift&userIds=7516988&giftAppId=1");
      	HttpResponds sessionstr = HttpConnect.sendPostHeader(config.backgroudBaseUrl
    			+"/meShowAdmin/queryUserStore.action",reg,
    			config.getpostbackgroudHearderlis());
    	System.out.println("增加库存礼物:"+sessionstr);
    	System.out.println(config.getpostbackgroudHearderlis());
    	
	}	
	static public void getSession(String user,String password,
			JSONObject head,Config config) throws HttpException, IOException{
		
		HttpResponds sessionstr = HttpConnect.sendPostHeader(
				config.getMeshowUrl()+"/meShowAdmin/login.action?method=login",
				"dosubmit=&username="+user+"&password="+password, head);
//		System.out.println("loginName="+user+"&passWord="+password);
		String jsessionid = "";
		for(Header tp:sessionstr.getHeaderList()){
			if(tp.getName().equals("Set-Cookie")){
				jsessionid=tp.getValue();
			}
		}
		jsessionid=jsessionid.split(";")[0];
//		System.out.println("\n"+jsessionid);
//		System.out.println("\nbody="+sessionstr.getbody());
		config.setSessionidName(jsessionid.split("=")[0]);
		config.setSessionidvalue(jsessionid.split("=")[1]);
	}
	
	static public String setActor(String url, JSONObject content, 
			String sessionname, String sessionvalue, Config config) 
					throws HttpException, IOException{

		config.putpostbackgroudHearderlis("Cookie", sessionname + "=" + sessionvalue);
		System.out.println("==========="+config.getpostbackgroudHearderlis());
		
		HttpResponds sessionstr = 
				HttpConnect.sendPostHeaderJSON(url, content, 
						config.getpostbackgroudHearderlis());
		return sessionstr.getbody();
	}
	
	static public String cancelActor(String url,String sessionname,
			String sessionvalue,String actorid,Config config) 
					throws HttpException, IOException{

		JSONObject content=new JSONObject();
		content.put("userIdStr", actorid);
		config.putgetbackgroudHearderlis("Cookie", sessionname+"="+sessionvalue);
		HttpResponds sessionstr = HttpConnect.sendGetBackGroud(url, actorid,
				sessionname,sessionvalue, config.getgetbackgroudHearderlis());
		return sessionstr.getbody();
	}	
	
	public static void applyActor(String userId, String identityNumber,
			int resourceId) 
			throws HttpException, IOException{
		Config config=new Config();
    	
    	setSessionId(config.getbackgrouduser(), config.getbackgroudpassword(), config);
		
		JSONObject content = new JSONObject();
		content.put("resourceId", resourceId);
		content.put("identityNumber", identityNumber);
		content.put("actorId", userId);

		String applyRes = setActor(config.viewActorId(), content,
				config.getSessionidName(), config.getSessionidvalue(), config);
//		System.err.println(applyRes);
		assertEquals("00000000", JSONObject.parseObject(applyRes).getString("tagCode"));				
		content.clear();
		
		content.put("actorId", userId);
		content.put("status", 1);
		content.put("checkReason", "");		
		applyRes = setActor(config.updateActorApplyStatus(), content,
				config.getSessionidName(), config.getSessionidvalue(), config);
//		System.err.println(applyRes);
		assertEquals("00000000", JSONObject.parseObject(applyRes).getString("tagCode"));
	}
	
	static public String setFamily(String url, JSONObject content, 
			Config config, int familyId) throws HttpException, IOException{

//		config.putFamilyBgHearder(config.refererApplylist, 
//				content.getString("sessionId"), familyId);
		config.putFamilyBgHearder(config.refererApplylist, 
				null, familyId);
		content.remove("sessionId");
		HttpResponds sessionstr = 
				HttpConnect.sendPostHeaderJSON(url, content, 
						config.getFamilyBgHearder());
		return sessionstr.getbody();
	}
	
	public static void applyFamily(String actorId, String familyBgUserId,
			String familyBgPwd, int appId, int familyId) 
			throws HttpException, IOException{
		Config config=new Config();
		
		config.putFamilyBgHearder(config.indexPage, 
				null, familyId);
		HttpResponds resPond = HttpConnect.sendPostHeaderJSON(config.indexPage(), null, config.getFamilyBgHearder());
		String res = resPond.getbody();
		System.err.println(res.split("<input type=\"hidden\" name=\"token\" value=\"")[1].split("\" />")[0]);
		String token = res.split("<input type=\"hidden\" name=\"token\" value=\"")[1]
				.split("\" />")[0];
		
		// 登录家族长后台
		System.out.println("===============================");
		System.out.println("====     登录家族长后台                      ===");
		System.out.println("===============================");
		JSONObject content = new JSONObject();
		content.put("date", System.currentTimeMillis());
		content.put("userid", familyBgUserId);
		content.put("password", familyBgPwd);
		content.put("type", appId);
		content.put("token", token);
		resPond = HttpConnect.sendPostHeaderJSON(config.familyLogin(), content, config.getFamilyBgHearder());
		res = resPond.getbody();
//		System.err.println(res);
		Header[] headers = resPond.getHeaderList();
		String sessionId = null;
		for (int i=0; i<headers.length; i++){
			String[] strArray = headers[i].toString().split(":");
			if (strArray[0].equals("Set-Cookie")) {
				sessionId = strArray[1].split(";")[0];
				break;
			}
		}
		assertEquals("00000000", JSONObject.parseObject(res).getString("tagcode"));
		content.clear();
		
		if (appId == AppId.KKTV) {
			// 试播通过
			System.out.println("===============================");
			System.out.println("====         试播通过                     ===");
			System.out.println("===============================");
			content.put("actorId", actorId);
			content.put("status", 3);
			content.put("note", "");
			content.put("sessionId", sessionId);
			res = setFamily(config.applyPass(), content, config, familyId);
			System.err.println(res);
			assertEquals("00000000", JSONObject.parseObject(res).getString("tagCode"));
			content.clear();
		}		
		
		// 允许加入家族
		System.out.println("===============================");
		System.out.println("====        允许加入家族                   ===");
		System.out.println("===============================");
		content.put("sessionId", sessionId);
		res = setFamily(config.actorCounts(), content, config, familyId);
//		System.err.println(res);
		assertEquals("00000000", JSONObject.parseObject(res).getString("tagCode"));
		content.clear();

		content.put("actorId", actorId);
		content.put("status", 5);
		content.put("agreement", 1);
		content.put("note", "");
		content.put("subPass", "");
		content.put("sessionId", sessionId);
		res = setFamily(config.actortestpass(), content, config, familyId);
//		System.err.println(res);
		assertEquals("00000000", JSONObject.parseObject(res).getString("tagCode"));
		content.clear();
	}

    //注册账号并进行实名认证
// public  static void  formalactorAll(Connection oraclecon, redis Redis,String realname,String namepwd, String userid,String token,String phone,String formalname,String qqnum,config cfg) throws Exception{
//    //手机号码绑定。
//	 ApplyActor.PhoneBundling(userid,phone,oraclecon,Redis);
//    //第一步发送申请
//    JSONObject result=KK.applyForRealNameKK(userid, token, realname, formalname, phone, qqnum, "1", "2922");
//    //第二步kkopp确认申请
//    ApplyActor.applyForRealNameKK_kkopp(cfg,userid,formalname);
//
// 	
// }	
	
	public static void joinFamily(int appId, String actorId, 
			String familyBgUserId,
			String familyBgPwd,
			int familyType,
			int familyId) 
			throws HttpException, IOException{
		
		switch (appId){
		case AppId.KKTV:
		case AppId.VR:
			applyFamily(actorId, familyBgUserId, familyBgPwd, familyType, familyId);
			break;
		case AppId.BANG:
			break;
		
		default:
			break;
		}
	}

}
