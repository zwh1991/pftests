package com.api.core;

import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.fastjson.JSONObject;
import com.api.core.ApiEnum.AppId;
import com.api.core.ApiEnum.RoomSource;
import com.web.execution.HttpConnect;

public class Config{
	/* case文档*/
	public static final String XLS = "meShow.xls";
	public static final int  renewNobilityPoint=80;
	/* redis配置*/
	public static final String test = "redis1.kktv2.com";
	public static final String Beta = "10.0.13.32";
	public static final String Gtest = "10.0.0.71";
	
	
	
	/* 配置文件设置*/
	public static final String PROFILE = "profile.properties";
	/* 扣币礼物id*/
	public static final int comGiftId = 40000761;
	public static final int comGiftPrice = 100;
	public static final String comGiftName = "猜拳";
	/*世界杯*/
	public static final int guessBetItemId1 = 2;//意大利5，英国6//美国2，中国3
	public static final int guessBetItemId2 = 3;
	public static final int guessBetItemId3 = 0;//平局
	public static final int seasonId = 21;
	/* 库存礼物id*/
	public static final int storeGiftId = 40000721;
	public static final int storeGiftPrice = 50;
	public static final String storeGiftName = "四叶草";
	public static final int actorRate = 26;
	/* 幸运礼物id*/
	public static final int lucklyGiftId = 10000011;
	public static final int lucklyGiftPrice = 50;
	public static final String lucklyGiftName = "红玫瑰";
	public static final int lucklyGiftRsvPrice = 10;
	/* 礼物个数*/
	public static final int giftCount = 1;
	/* 点歌单价*/
	public static final int choiceSongPrice = 5000;
	
	public static final int carId = 1127;
	public static final int roomSource = RoomSource.SHOWPLATE;

	public static final String mongoIP = "mons.kktv2.com";
	public static final int mongoPort = 4001;
	
	public static final String redisIP = "redis1.kktv2.com";
	public static final int redisPort = 6379;
	public static final String redisPwd = "youknowthat";
	//主播
	private static int resourceId = 172;
	
	
	private String setPlatForm="2";
	// static public String userid="1151093";
	//public static final String pgsqlIP = "10.0.0.15";
	public static final String pgsqlIP = "10.0.13.31";
	private int userid=1099198;
	private String token="1F3AAEE3BD5025DDE050007F01007011";
	private String version ="2";
	private String container="2";
	private String meshowurl="http://10.0.13.29:9494";	
	  public String getMeshowUrl(){
			 return meshowurl;
			 }
		public void config(){
		setDefaultpostbackgroudHearderlis();
		setDefaultgetbackgroudHearderlis();
	}			
		
		

//	/********************************************
//	 * 开发环境
//	 */
//	private String urlNodeKK="http://10.0.3.8:81/";
//	private static String ipAdminBkg="http://10.0.0.7:9494";
//	// 用户测试账号
//	public static int userId = 7528106;
//	public static String nickname = "富豪619596";
//	// 主播测试账号
//	public static int roomId = 7530258;//7530130;//7528103;
//	// 家族长
//	public static int familyleader = 10000644;
//	public static int familyId = 11209;
//	public static int familyMedelId = 9520;
//	public static String familyMedelName = "芒果";
//	public static int buyPeriod = 1;
//	public static int familyMedelIdPrice = 2000;
//	// 抢红包 10个人
//    // 财富等级三级以上
//    public static ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(
////    		7505798,
////    		7507321,
////    		7507322,
////    		7507323,
////    		7507324,
////    		7507325,
////    		7507326,
////    		7507327,
////    		7507328,
////    		7507329,
//    		7507330,
//    		7507331
//    		));
//    
////    public static ArrayList<Integer> noLevelList = new ArrayList<Integer>(Arrays.asList(
////    		7507340,
////			7507341
////			));
	
	/********************************************
	 * beta测试环境
	 */
	private String urlNodeKK="http://10.0.13.26:81/";
	private static String ipAdminBkg="http://10.0.13.29:9494";
	// 用户测试账号
	public static int userId = 550493987;
	public static String nickname = "富豪151342";
	// 主播测试账号
	public static int roomId = 10008038;
	// 家族长
	public static int familyleader = 10000267;
	public static int familyId = 11096;
	public static int familyMedelId = 9520;
	public static String familyMedelName = "芒果";
	public static int buyPeriod = 1;
	public static int familyMedelIdPrice = 2000;
	 //抢红包 10个人
     //财富等级三级以上
    public static ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(
//    		7539750,
//    		7539749,
//    		7539748,
//    		7539747,
//    		7539746,
//    		7539745,
//    		7539744,
//    		7539743,
//    		7539742,
    		7539741,
    		7539740
    		));
    
    public static ArrayList<Integer> noLevelList = new ArrayList<Integer>(Arrays.asList(
    		7539739,
    		7539738
			));
    
    /********************************************
	 * 正式环境
	 */
//	private String urlNodeKK="http://10.0.13.26:81/";
//	private static String ipAdminBkg="http://10.0.13.29:9494";
//	// 用户测试账号
//	public static int userId = 122590540;
//	public static String nickname = "富豪925179";
//	// 主播测试账号
//	public static int roomId = 112566898;
//	// 家族长
//	public static int familyleader = 10000267;
//	public static int familyId = 11096;
//	public static int familyMedelId = 9520;
//	public static String familyMedelName = "芒果";
//	public static int buyPeriod = 1;
//	public static int familyMedelIdPrice = 2000;
//	// 抢红包 10个人
//    // 财富等级三级以上
//    public static ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(
////    		7539750,
////    		7539749,
////    		7539748,
////    		7539747,
////    		7539746,
////    		7539745,
////    		7539744,
////    		7539743,
////    		7539742,
//    		7539741,
//    		7539740
//    		));
//    
//    public static ArrayList<Integer> noLevelList = new ArrayList<Integer>(Arrays.asList(
//    		7539739,
//    		7539738
//			));
    
	/**********************************************
	 * 
	 */
	private String SessionidName;
	private String Sessionidvalue;
	
	public  String backgroudBaseUrl="http://10.0.13.29:9494";
	//public static String backgroudBaseUrl=Config.PROFILE.equals("Beta.properties")?"http://10.0.13.29:9494":"http://10.0.0.7:9494";
	
	//private String backgroudBaseUrl="http://10.0.13.29:9494";
	private String backgrouduser="melotadmin";
	private String backgroudpassword="123456";
	private String backgroudurl = ipAdminBkg + "/meShowAdmin/login.action?method=login";
			//"/kkopp/loginAction!userLogin.action";
	private String viewActorId = ipAdminBkg + "/kkopp/opt/actorAction!viewActorId.action";
	private String updateActorApplyStatus = 
			 ipAdminBkg + "/kkopp/opt/actorAction!updateActorApplyStatus.action?resourceId=172&operCode=2";
	private String cancelactorurl =
			 ipAdminBkg + "/kkopp/opt/roomInfoAction!deleteRoomInfo.action?resourceId=584&roomId=";
	
	// 家族长管理后台相关
//	public String familyBgUserId = "6991496";
//	public String familyBgPwd = "123456";
	public String indexPage = ipAdminBkg + "/family/login.jsp";
	private static String familyLogin = ipAdminBkg + "/family/login.action?";
	private String applyPass = ipAdminBkg + "/family/applypass.action";
	private String actorCounts = ipAdminBkg + "/family/actorcounts.action";
	private String actortestpass = ipAdminBkg + "/family/actortestpass.action";	
	public String refererLogin = ipAdminBkg + "/family/login.action";
	public String refererApplylist = ipAdminBkg + "/family/getactorapplylist.action";

	private JSONObject postbackgroudhearderlist = new JSONObject();
	private JSONObject getbackgroudHearderlist = new JSONObject();

//	public void config(){
//		setDefaultpostbackgroudHearderlis();
//		setDefaultgetbackgroudHearderlis();
//	}
	
	  public void setactorurl(String tokenurl){
		  updateActorApplyStatus = tokenurl;
	 }
	  public String updateActorApplyStatus(){
		 return updateActorApplyStatus;
	 }
	  
	  public String viewActorId(){
			 return viewActorId;
	}
	  
	  public String indexPage(){
			 return indexPage;
	}
	  public static String familyLogin(){
			 return familyLogin;
	}
	  public String applyPass(){
			 return applyPass;
	}
	  public String actorCounts(){
			 return actorCounts;
	}
	  public String actortestpass(){
			 return actortestpass;
	}
	  
	  public void setcancelactorurl(String tokenurl){
		 cancelactorurl=tokenurl;
	 }
	  public String getcancelactorurl(){
		 return cancelactorurl;
	 }

	  public void setbackgrouduser(String tokenurl){
		 backgrouduser=tokenurl;
	 }
	  public String getbackgrouduser(){
		 return backgrouduser;
	 }
	  public void setbackgroudpassword(String tokenurl){
		 backgroudpassword=tokenurl;
	 }
	  public String getbackgroudpassword(){
		 return backgroudpassword;
	 }
	  public void setBackGroudurl(String bu){
		 backgroudurl=bu;
	 }
	  public String getBackGroudurl(){
		 return backgroudurl;
	 }
	 
	  public void setSessionidName(String sn){
		 SessionidName=sn;
	 }
	  public String getSessionidName(){
		 return SessionidName;
	 }
	  public void setSessionidvalue(String sv){
		 Sessionidvalue=sv;
	 }
	  public String getSessionidvalue(){
		 return Sessionidvalue;
	 }
	 
	  public void setPlatForm(String spf){
		 setPlatForm=spf;
	 }
	  public String getPlatForm(){
		 return setPlatForm;
	 }
//	  public void setRoomid(int rid){
//		 roomid=rid;
//	 }
//	  public int getRoomid(){
//		 return roomid;
//	 }
	  public void setUrl(String ul){
		  urlNodeKK=ul;
	 }
	  public String getUrl(int appId){
		 return urlNodeKK;
	 }
	 
	  public void setUserId(int userId2){
		 userid=userId2;
	 }
	  public int getUserId(){
		 return userid;
	 }
	 
	  public void setToken(String tk){
		 token=tk;
	 }
	  public String getToken(){
		 return token;
	 }

	  public void setVersion(String ver){
		 version=ver;
	 }
	  public String getVersion(){
		 return version;
	 }
	 
	  public void setContainer(String contain){
		 container=contain;
	 }
	  public String getContainer(){
		 return container;
	 }
	 
	 public static enum RoomVideoChatLayout{
		 ROOM_RECORD_MODE_AUDIO,
		 ROOM_RECORD_MODE_VIDEO,
		 ROOM_RECORD_MODE_AUTH;
	 }
	 static public String getWs(String url,int rid,int userId,String platform,String softVersion){
		 Config config=new Config();
		 String result=HttpConnect.sendGet(config.urlNodeKK, "roomId="+rid+"&userId="+userId+"&platform="+platform+"&softVersion="+softVersion);
		 JSONObject resultjs=JSONObject.parseObject(result);
		 System.out.println(resultjs);
		 String ws=resultjs.getString("ws");
		 System.out.println(ws);
		 return ws;
	 }
	 
	 static public String getWs1(String url, int rid){
		 Config config=new Config();
		 String result=HttpConnect.sendGet(url, "roomId="+rid);
		 JSONObject resultjs=JSONObject.parseObject(result);
		 System.out.println(resultjs);
		 String ws=resultjs.getString("ws");
		 
		 return ws;
	 }
 
	 /*************************************
	  * 实名认证运营后台  请求包头 默认参数
	  *********************************/
	 public void setDefaultpostbackgroudHearderlis(){
		 postbackgroudhearderlist.put("Proxy-Connection", "keep-alive");
		 postbackgroudhearderlist.put("Cache-Control", "max-age=0");
		 postbackgroudhearderlist.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		 postbackgroudhearderlist.put("Origin", "http://10.0.13.29:9494");
		 postbackgroudhearderlist.put("Upgrade-Insecure-Requests", "1");
		 postbackgroudhearderlist.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");
		 postbackgroudhearderlist.put("Content-Type", "application/x-www-form-urlencoded");
		 postbackgroudhearderlist.put("Referer", "http://10.0.13.29:9494/meShowAdmin/login.action");
		 postbackgroudhearderlist.put("Accept-Encoding", "gzip, deflate");
		 postbackgroudhearderlist.put("Accept-Language", "zh-CN,zh;q=0.8");
		 postbackgroudhearderlist.put("Cookie", "JSESSIONID9494=D63236383563E5020A0352300439489F");
	 }
	 
	 public JSONObject getpostbackgroudHearderlis(){
		 return postbackgroudhearderlist;
	 }
	 
	 public void putpostbackgroudHearderlis(String name,String value){
		 setDefaultpostbackgroudHearderlis();
		 postbackgroudhearderlist.put(name,value);
	 }
	 
	 public void setDefaultgetbackgroudHearderlis(){
		 getbackgroudHearderlist.put("Proxy-Connection", "keep-alive");
		 getbackgroudHearderlist.put("Cache-Control", "max-age=0");
		 getbackgroudHearderlist.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.");
		 getbackgroudHearderlist.put("Upgrade-Insecure-Requests", "1");
		 getbackgroudHearderlist.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");
		 getbackgroudHearderlist.put("Referer", "http://10.0.0.7:9494/kkopp/loginAction!userLogin.action");
		 getbackgroudHearderlist.put("Accept-Encoding", "gzip, deflate");
		 getbackgroudHearderlist.put("Accept-Language", "zh-CN,zh;q=0.8");
		 getbackgroudHearderlist.put("Cookie", "JSESSIONID9494=D63236383563E5020A0352300439489F");
	 }
	 
	 public JSONObject getgetbackgroudHearderlis(){
		 return getbackgroudHearderlist;
	 }
	 
	 public void putgetbackgroudHearderlis(String name,String value){
		 getbackgroudHearderlist.put(name,value);
	 }
	 
	 /*************************************
	  * 请求家族长后台管理页面的 请求包头 默认参数
	  *********************************/	 
	 public void setFamilyBgDefaultHearder(String Referer, String jSessionId, int familyId){
		 postbackgroudhearderlist.put("Connection", "keep-alive");
		 postbackgroudhearderlist.put("Cache-Control", "max-age=0");
		 postbackgroudhearderlist.put("Accept", "application/json, text/javascript, */*; q=0.01");
		 postbackgroudhearderlist.put("X-Requested-With", "XMLHttpRequest");
		 postbackgroudhearderlist.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");
		 postbackgroudhearderlist.put("Referer", Referer);
		 postbackgroudhearderlist.put("Accept-Encoding", "gzip, deflate, sdch");
		 postbackgroudhearderlist.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		 postbackgroudhearderlist.put("Cookie", "family_"+ familyId +"_prot1=1; JSESSIONID9494=F008CC8FC0C9330BB32593638079CEC1");
	 }
	 
	 public JSONObject getFamilyBgHearder(){
		 return postbackgroudhearderlist;
	 }
	 
	 public void putFamilyBgHearder(String Referer, 
			 String jSessionId, int familyId){
		 setFamilyBgDefaultHearder(Referer, jSessionId, familyId);
	 }

	public String backgroudBaseUrl() {
		// TODO Auto-generated method stub
		return null;
	}
}
