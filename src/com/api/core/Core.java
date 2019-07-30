package com.api.core;

import com.api.core.ApiEnum;
import com.api.core.ApiEnum.AppId;
import com.api.core.Config;
import com.api.core.ApiEnum.NodeCaseType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;

import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fileio.util.BaseIO;
import com.fileio.util.Excels;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melot.kktv.util.SecurityFunctions;
import com.socket.util.SocketMessageManager;
import com.socket.util.SocketMessagFormer.MessageType;
import com.tools.util.Tools;
import com.web.execution.HttpConnect;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;

/**
 * Created on 2015-7-14
 * @author kaixu  meshow
 * @version 1.0 Description
 */
public class Core extends TestCase {
	public static String environment;
	private static String domain;
	private static String url;
	public static String inspLoginUrl;
	public static String inspRefreshLSUrl;
	public static String inspPublishLSUrl;
	public static String inspPublishUPUrl;
	public static String inspGetPictureList;
	public static String inspUsername;
	public static String inspPassword;
	public static String paymentUrl;
	public static String notifyUrl;
	public static String alipayUrl;

    private static String xls;
    private static String logPath;
    private static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyyMMdd hh:mm:ss");
    public static Excels kktvXLS;
    private static Properties prop;
    
    Context con;
    public static SocketMessageManager smm=null;

//    private static Logger log = Logger.getLogger("Core");
   
    public Core() {

    }
    
    public static void assertBuyGuard(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag"); 
			
			switch(msgTag){

			// 购买守护 10010366
			case MessageType.PURCHASE_GUARD:
				assertEquals(jo.getString("roomId"), map.get("actorId").toString());
				assertEquals(jo.getString("guardId"), map.get("guardId").toString());
				assertEquals(jo.getString("guardName"), map.get("guardName").toString());
				break;
				
			default:
				break;
			}
		}
    }
    
    public static String assertGrabRed(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	boolean bTag = false;
    	String sendId = "";
    	
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag");

			switch(msgTag){

			// 房间红包 10010800
			case MessageType.ROOM_RED_EVELOPE:
				bTag = true;
				sendId = jo.getString("sendId");
//				if (map.containsKey("tag") && map.get("tag").toString().equals("1")){
//					assertEquals(jo.getString("userId"), "7507325");
//					assertEquals(jo.getString("nickname"), "富豪份很好用来宁静");
//				}else {
//					assertEquals(jo.getString("userId"), map.get("roomId").toString());
//					assertEquals(jo.getString("nickname"), map.get("nickname").toString());
//				}
//				assertEquals(jo.getString("roomId"), map.get("roomId").toString());
//				
//				assertEquals(jo.getString("amount"), map.get("amount").toString());
//				assertEquals(jo.getString("count"), map.get("count").toString());
				
				break;

			default:
				break;
			}
		}
    	// 红包喇叭 延时
    	if (Boolean.valueOf(map.get("bDelay").toString()).booleanValue()){
    		assertFalse(bTag);
    	}else {
    		assertTrue(bTag);
    	}
    	
    	return sendId;
    }
    
    public static void assertFollow(Map<String, Object> map, 
    		JSONObject response, String method){
    	
    	JSONObject jo = JSONObject.parseObject(map.get(method+"request").toString());
		JSONArray ja = response.getJSONArray("roomList");

    	if (method.equals("Follow")){
			boolean bTag = false;
			for (int i=0; i<ja.size(); i++){
				if (ja.getJSONObject(i).getIntValue("userId") == 
						jo.getIntValue("followedIds")){
					bTag = true;
					break;
				}
			}
			assertTrue(bTag);
		}else if (method.equals("CancelFollow")){
			boolean bTag = true;
			for (int i=0; i<ja.size(); i++){
				if (ja.getJSONObject(i).getIntValue("userId") == 
						jo.getIntValue("canceledId")){
					bTag = false;
					break;
				}
			}
			assertTrue(bTag);
		}
    }

    public static void assertSendGift(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	long sendPrice = -1;
    	
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag"); 
			
			switch(msgTag){

			// 赠送礼物 10010208
			case MessageType.SEND_GIFT:
				sendPrice = jo.getLongValue("sendPrice");
				assertEquals(jo.getString("sNickname"), map.get("nickname"));
				assertEquals(jo.getString("sRichLevel"), map.get("richLevel"));
//				assertEquals(jo.getIntValue("giftId"), Config.giftId);
				assertEquals(jo.getIntValue("giftCount"), Config.giftCount);
				break;
			
			// 余额 10010238
			case MessageType.ROOM_MONEY:
				assertEquals((Long.parseLong(map.get("money").toString()) 
								- Long.parseLong(map.get("giftCount").toString())*sendPrice)
								,jo.getLongValue("leftMoney"));
				break;
				
			default:
				break;
			}
		}
    }
    
    public static void assertSendMsg(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	long price = -1;
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag"); 
			
			switch(msgTag){

			// 飞屏 10010214
			case MessageType.ROOM_FLY_SCREEN:
				price = jo.getLongValue("price");
				assertEquals(jo.getString("sNickname"), map.get("nickname"));
				assertEquals(jo.getString("sUserId"), map.get("userId"));
				assertEquals(jo.getString("content"), map.get("content"));
				break;
				
			// 余额 10010238
			case MessageType.ROOM_MONEY:
				assertEquals((Long.parseLong(map.get("money").toString()) - price)
						,jo.getLongValue("leftMoney"));
				break;

			default:
				break;
			}
		}
    }
    
    public static void assertBarrage(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag"); 
			
			switch(msgTag){

			// 发送文本,emo,彩条 10010209
			case MessageType.SEND_TXT:
				assertEquals(jo.getString("sNickname"), map.get("nickname"));
				assertEquals(jo.getString("content"), map.get("content"));
				assertEquals(jo.getString("roomId"), map.get("roomId").toString());
				break;
				
			// Toast 消息  40000001
			case MessageType.TOAST_MSG:
				assertEquals(jo.getString("content"), "弹幕最多发送50个字.");
				break;

			default:
				break;
			}
		}
    }
    
    public static void assertPublicChat(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag"); 
			
			switch(msgTag){
				
			// System 消息  30000001
			case MessageType.SYSTEM_MSG:
				assertEquals(jo.getString("content"), "房主开启了V0用户禁言");
				break;
			
			// Toast 消息  40000001
			case MessageType.TOAST_MSG:
				assertEquals(jo.getString("content"), "V1及以上用户才可以发言~");
				break;

			default:
				break;
			}
		}
    }
    
    public static void assertSetGuardCar(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag"); 
			
			switch(msgTag){
				
			//  10010206
			case MessageType.GET_MEM:
				int guardCarType = -1;
				JSONArray ja = jo.getJSONArray("userList");
				for (int j=0; j<ja.size(); j++){
					if (Integer.valueOf(map.get("userId").toString())
							== ja.getJSONObject(j).getIntValue("userId")){
						if (ja.getJSONObject(j).containsKey("guardCarType")){
							guardCarType = ja.getJSONObject(j).getIntValue("guardCarType");
						}else {
							guardCarType = 0;
						}
					}
				}
				assertEquals(map.get("type").toString(), String.valueOf(guardCarType));
				break;

			default:
				break;
			}
		}
    }
    
    public static void assertPrivateChat(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag"); 
			
			switch(msgTag){
				
			// System 消息  30000001
			case MessageType.SYSTEM_MSG:
				assertEquals(jo.getString("content"), "房主开启了V0用户禁言");
				break;

			default:
				break;
			}
		}
    }
    
    public static void assertSendTxt(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){

    	if (map.get("chatType").toString().equals("2")
    			&& (map.get("userId").toString().equals(map.get("dUserId").toString())
    					|| map.get("userId").toString().equals(map.get("sUserId").toString()))){
    		int a = 0;
    		if (msgList.size()>0) {
    			for (int i=0; i<msgList.size(); i++){
        			JSONObject jo = msgList.get(i);
        			int msgTag = jo.getIntValue("MsgTag");
        			if (msgTag == MessageType.SEND_TXT){
        				assertEquals(jo.getString("sNickname"), map.get("nickname"));
        				assertEquals(jo.getString("content"), map.get("content"));
        				assertEquals(jo.getString("roomId"), map.get("roomId").toString());
        				a ++;
        			}
        		}
    			assertEquals(1, a);
    		}else{
    			assertTrue(false);
    		}
    	}
    	// 公聊，房间内每个人都能收到
    	else if ((map.get("chatType").toString().equals("0")
    			|| map.get("chatType").toString().equals("1"))
    			&& (map.get("dUserId").toString().equals("0") 
    					|| map.get("dUserId").toString().equals("-1"))){
    		int a = 0;
    		if (msgList.size()>0) {
    			for (int i=0; i<msgList.size(); i++){
        			JSONObject jo = msgList.get(i);
        			int msgTag = jo.getIntValue("MsgTag");
        			if (msgTag == MessageType.SEND_TXT){
        				assertEquals(jo.getString("sNickname"), map.get("nickname"));
        				assertEquals(jo.getString("content"), map.get("content"));
        				assertEquals(jo.getString("roomId"), map.get("roomId").toString());
        				a ++;
        			}
        		}
    			assertEquals(1, a);
    		}else{
    			assertTrue(false);
    		}
    	}
    	
//    	for (int i=0; i<msgList.size(); i++){
//			JSONObject jo = msgList.get(i);
//			int msgTag = jo.getIntValue("MsgTag"); 
//			
//			switch(msgTag){
//
//			// 发送文本,emo,彩条 10010209
//			case MessageType.SEND_TXT:
//				assertEquals(jo.getString("sNickname"), map.get("nickname"));
//				assertEquals(jo.getString("content"), map.get("content"));
//				assertEquals(jo.getString("roomId"), map.get("roomId").toString());
//				break;
//
//			// 余额 10010238
//			case MessageType.ROOM_MONEY:
//				assertEquals(Long.parseLong(map.get("money").toString())
//						,jo.getLongValue("leftMoney"));
//				break;
//
//			default:
//				break;
//			}
//		}
    }
    
    public static void assertSmashGoldenEgg(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			JSONObject giftInfo = jo.getJSONObject("giftInfo");
			int msgTag = jo.getIntValue("MsgTag");
			int giftPrice = -1;
			
			switch(msgTag){

			// 砸金蛋 10010330
			case MessageType.SMASH_GOLDEN_EGG:
				giftPrice = giftInfo.getIntValue("sendPrice") 
					* giftInfo.getIntValue("quantity");
				assertEquals(jo.getIntValue("chance"), map.get("sendPrice"));
				break;
				
			// 余额 10010238
			case MessageType.ROOM_MONEY:
				assertEquals((Long.parseLong(map.get("money").toString())
//								+ giftPrice
								), jo.getLongValue("leftMoney"));
				break;
			
			// 库存礼物列表 10010266 
			case MessageType.ROOM_STOCK_GIFT_LIST_MSG:
				break;

			default:
				break;
			}
		}
    }
    
    public static void assertUserBroadcast(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			JSONArray MsgList = jo.getJSONArray("MsgList");
			int msgTag = jo.getIntValue("MsgTag");
			
			switch(msgTag){

			// 喇叭消息 50010101
			case MessageType.ROOM_BROADCAST:
				for (int k=0; k<MsgList.size(); k++){
					JSONObject jObject = MsgList.getJSONObject(k);
					assertEquals(jObject.getString("content"), map.get("content"));
					assertEquals(jObject.getString("nickname"), map.get("nickname"));
					assertEquals(jObject.getIntValue("roomId"), map.get("roomId"));
				}
				break;
			
			default:
				break;
			}
		}
    }
    
    public static void assertParking(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			JSONArray ParkList = jo.getJSONArray("ParkList");
			int msgTag = jo.getIntValue("MsgTag");
			
			switch(msgTag){

			// 停车位 50010201
			case MessageType.ROOM_PARK_MSG:
				for (int k=0; k<ParkList.size(); k++){
					JSONObject jObject = ParkList.getJSONObject(k);
					if (Integer.valueOf(map.get("position").toString())
							== jObject.getIntValue("position")){
						assertEquals(jObject.getString("content"), map.get("content"));
						assertEquals(jObject.getString("nickname"), map.get("nickname"));
						assertEquals(jObject.getString("userId"), map.get("userId"));
					}	
				}
				break;
			// 余额 10010238
			case MessageType.ROOM_MONEY:
				assertEquals((Long.parseLong(map.get("money").toString())
								- Long.parseLong(map.get("price").toString()))
								, jo.getLongValue("leftMoney"));
				break;
			default:
				break;
			}
		}
    }
    
    public static void assertSeat(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	int count = -1;
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag");

			switch(msgTag){

			// 频道沙发 10010239
			case MessageType.ROOM_SEAT_MSG:
				JSONArray seatList = jo.getJSONArray("seatList");
				JSONObject jObject = seatList.getJSONObject(0);
				count = Integer.valueOf(map.get("count").toString());
				assertEquals(jObject.getIntValue("count"), count);
				
				JSONObject joUInfo = jObject.getJSONObject("userInfo");
				assertEquals(joUInfo.getString("nickname"), map.get("nickname"));
				assertEquals(joUInfo.getString("userId"), map.get("userId"));
				break;
			// 余额 10010238
			case MessageType.ROOM_MONEY:
				assertEquals(Long.parseLong(map.get("money").toString()),
						(jo.getLongValue("leftMoney") 
						+ Long.parseLong(String.valueOf(100*count))));
				break;
			default:
				break;
			}
		}
    }
    
    public static void assertFlyWay(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag"); 
			
			switch(msgTag){

			// 跑道消息 50010102
			case MessageType.ROOM_FLYWAY_MSG:
				JSONArray MsgList = jo.getJSONArray("MsgList");
				JSONObject jObject = new JSONObject();
				for (int k=0; k<MsgList.size(); k++){
					jObject = MsgList.getJSONObject(k);
					if (jObject.getJSONObject("sUser").getString("userId").equals(map.get("userId"))){
						assertEquals(jObject.getJSONObject("sUser").getString("sNickname"), 
								map.get("nickname"));
						assertEquals(jObject.getJSONObject("sUser").getString("sRichLevel"), 
								map.get("richLevel"));
						assertEquals(jObject.getJSONObject("dUser").getString("userId"), 
								map.get("roomId"));
//						assertEquals(jObject.getJSONObject("gift").getIntValue("giftId"), 
//								Config.giftId);
//						assertEquals(jObject.getIntValue("totalMoney"), 
//								(Config.giftCount * Config.giftPrice));
					}
				}
				break;

			default:
				break;
			}
		}
    }
    
    public static void assertRoomGiftHistory(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	int count = -1;
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag");

			switch(msgTag){

			// 房间礼物记录 10010215
			case MessageType.ROOM_GIFT_HISTORY:
				JSONArray rsvGiftList = jo.getJSONArray("rsvGiftList");
				for (int k=0; k<rsvGiftList.size(); k++){
					JSONObject jObject = rsvGiftList.getJSONObject(k);
					if (map.get("giftId").toString().equals(jObject.getString("giftId"))){
						assertEquals(jObject.getString("count"), 
								map.get("giftCount").toString());
						break;
					}
				}

				break;

			default:
				break;
			}
		}
    }
    
    public static void assertSongChoice(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){

    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag");

			switch(msgTag){

			// 点歌 10010219
			case MessageType.ROOM_SONG_CHOICE:
				assertEquals(map.get("nickname"), jo.getString("c"));
				assertEquals(map.get("songName"), jo.getString("b"));
				break;
			// 余额 10010238
			case MessageType.ROOM_MONEY:
				assertEquals(Long.parseLong(map.get("money").toString())
						- Config.choiceSongPrice,
						jo.getLongValue("leftMoney"));
				break;
			// 接受点歌 10010221
			case MessageType.ROOM_SONG_ACCEPT:
				assertEquals(map.get("nickname"), jo.getString("c"));
				assertEquals(map.get("songName"), jo.getString("b"));
				assertEquals(map.get("roomId").toString(), jo.getString("roomId"));
				break;
				
			default:
				break;
			}
		}
    }
    
    public static void assertKickOut(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){

    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag");

			switch(msgTag){

			// 点歌 10010219
			case MessageType.ROOM_SONG_CHOICE:
				break;
				
			default:
				break;
			}
		}
    }
    
    public static void assertChatScreen(ArrayList<JSONObject> msgList,
    		Map<String, Object> map){

    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag");

			switch(msgTag){

			// 关闭公聊 10010290
			case MessageType.ROOM_CLOSE_PUBLICKCHAT:
				assertEquals(map.get("minRichLevel").toString(), jo.getString("minRichLevel"));
				break;
				
			// 开启公聊 10010291
			case MessageType.ROOM_OPEN_PUBLICKCHAT:
				break;
				
			// 关闭私聊 10010297
			case MessageType.ROOM_CLOSE_PRIVATECHAT:
				System.err.println("1111");
				assertEquals(map.get("minRichLevel").toString(), jo.getString("minRichLevel"));
				break;
				
			// 开启私聊 10010298
			case MessageType.ROOM_OPEN_PRIVATECHAT:
				break;
				
			default:
				break;
			}
		}
    }
    
    /**
	 * 断言房间 socket 响应数据
	 * @param msgList
	 * @param map
	 */
    public static void assertMessage(int nodeCaseType, 
    		ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	
    	switch(nodeCaseType){
    	// 送礼物
    	case NodeCaseType.SEND_GIFT:
    		assertSendGift(msgList, map);
    		break;
    	// 弹幕
    	case NodeCaseType.SEND_MSG:
    		assertSendMsg(msgList, map);
    		break;
    	// 发送文字信息
    	case NodeCaseType.SEND_TXT:
    		assertSendTxt(msgList, map);
    		break;
    	// 砸金蛋	
    	case NodeCaseType.SMASH_GOLDEN_EGG_BINDPHONE:
    		assertSmashGoldenEgg(msgList, map);
    		break;
    		
    	case NodeCaseType.SMASH_GOLDEN_EGG_UNBINDPHONE:
    		break;
    	// 发送小喇叭功能测试	
    	case NodeCaseType.USER_BROADCAST:
    		assertUserBroadcast(msgList, map);
    		break;
    		
    	// 抢车位功能测试	
    	case NodeCaseType.GRAB_PARKING:
    		assertParking(msgList, map);
    		break;
    	
    	// 抢座	
    	case NodeCaseType.GRAB_SEAT:
    		assertSeat(msgList, map);
    		break;
    	
    	// 抢座	
    	case NodeCaseType.FLY_WAY:
    		assertFlyWay(msgList, map);
    		break;
    	// 查看房间礼物信息
    	case NodeCaseType.ROOM_GIFT_HISTORY:
    		assertRoomGiftHistory(msgList, map);
    		break;
    	// 点歌
    	case NodeCaseType.ROOM_SONG_CHOICEED:
    		assertSongChoice(msgList, map);
    		break;
    	// 踢人
    	case NodeCaseType.ROOM_KICK_OUT:
    		break;
    		
    	// 购买守护
    	case NodeCaseType.PURCHASE_GUARD:
    		assertBuyGuard(msgList, map);
    		break;
    		
    	// 公聊、私聊 开启、关闭
    	case NodeCaseType.ROOM_CHATSCREEN:
    		assertChatScreen(msgList, map);
    		break;
    	// 弹幕	
    	case NodeCaseType.BARRAGE:
    		assertBarrage(msgList, map);
    		break;
    		
    	// 关闭公聊屏	
    	case NodeCaseType.PUBLICSCREEN_CLOSE:
    		assertPublicChat(msgList, map);
    		break;
    		
    	// 关闭私聊屏	
    	case NodeCaseType.PRIVATECSCREEN_CLOSE:
    		assertPrivateChat(msgList, map);
    		break;
    		
    	// 守护座驾开关	
    	case NodeCaseType.SET_GUARD_CAR:
    		assertSetGuardCar(msgList, map);
    		break;
    		
    	default:
    		break;
    	}
    }
    /**
     * . 将文件转成base64 字符串
     * @param path
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encodeBase64File(final String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) inputFile.available()];
        inputFile.read(buffer);
        inputFile.close();
        return buffer;
    }
    
    /**
     * 初始化
     * @author kaixu
     * Create On 2015-7-14.
     */
    public static void initParams() {
        File pFile = new File(Config.PROFILE);
        FileInputStream pInStream;
        try {
            pInStream = new FileInputStream(pFile);
            Properties p = new Properties();
            p.load(pInStream);
            prop = p;
            environment = p.getProperty("environment");
            domain = p.getProperty(environment+"Domain");
            url = p.getProperty("Url");
            xls = p.getProperty("xls");
            logPath = p.getProperty("logPath");
            inspLoginUrl = p.getProperty("inspLoginUrl");
            inspRefreshLSUrl = p.getProperty("inspRefreshLSUrl");
            inspPublishLSUrl = p.getProperty("inspPublishLSUrl");
            inspPublishUPUrl = p.getProperty("inspPublishUPUrl");
            inspGetPictureList = p.getProperty("inspGetPictureList");
            inspUsername = p.getProperty("inspUsername");
            inspPassword = p.getProperty("inspPassword");
            paymentUrl = p.getProperty("PaymentUrl");
            notifyUrl = p.getProperty("NotifyUrl");
            alipayUrl = p.getProperty("AlipayUrl");

            File logfile = new File(logPath);
            if (!(logfile.exists())) { // 判断是否存在该目录
                logfile.mkdir(); // 如果不存在则新建一个目录
            }
        } catch (IOException e) {
            e.printStackTrace();
        }        
        kktvXLS = new Excels(xls);
    }
    
    /**
     * 判断字符串是否为中文
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str){
        boolean temp = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
        Matcher m=p.matcher(str); 
        if(m.find()){ 
            temp =  true;
        }
        return temp;
    }
    
    /**
     * 判断字符串是否为中文
     * @param str
     * @return
     */
    public static boolean isContainMsg(ArrayList<JSONObject> msgList,
    		int msgTag){
        boolean temp = false;
        
        for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			
			if (msgTag == jo.getIntValue("MsgTag")){
				temp = true;
				break;
			}
		}
        
        return temp;
    }
    
    public static List<Object[]> getRows(String method) {
    	kktvXLS = new Excels(Config.XLS);
		// List<Integer> result= new ArrayList<Integer>();
		File pFile = new File(Config.PROFILE);
		FileInputStream pInStream;
		try {
			pInStream = new FileInputStream(pFile);
			Properties p = new Properties();
			p.load(pInStream);
//			domain = p.getProperty("domain");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Object[]> ls = new ArrayList<Object[]>();
		for (int i = 1; i <= kktvXLS.getRowNum(method); i++) {
			Object[] o = new Object[1];
			o[0] = i;
			// result.add(i) ;
			ls.add(o);
		}
		int aa=ls.size();
		return ls;
	}
    
    public static long getLastedMoney(Map<String, Object> map) throws Throwable{
    	JSONObject obj = Start(1, "GetUserInfo", map);
        return obj.getJSONObject("res").getLongValue("money");
    }
    
    /**
    * @Title: JsonStrAssemble  
    * @author : kaixu  
    * @date : 2015-7-17  
    * @Description: 请求的jsonobject 去掉空串，并传入前置接口返回的参数
    * @param jsonStr  
    * @return : 预请求的jsonobject 
    * @exception:(异常说明)  
    */
    public static JSONObject JsonStrAssemble(String method, 
    		JSONObject request, Map<String, Object> map){
    	JSONObject reagobj = request ;
        Object[] keys = reagobj.keySet().toArray();

        if (method.equals("Register")){
        	if (request.getString("username").equals(ApiEnum.dynamic)){
    			request.put("username", "kai"+Tools.getRandomString(7));
    		}
        }
        // 去掉 jsonobject 空串，并传入前置接口返回的参数 
		for (int i = 0; i < keys.length; i++) {
            String key = (String)keys[i];
            if (reagobj.get(key) instanceof String){
            	String value = (String)reagobj.get(key);
            	if (value.equals(ApiEnum.trim)){
                	reagobj.remove(key);
            	}else if (value.equals(ApiEnum.blank)){
                	reagobj.put(key, "");
            	}else if (value.equals(ApiEnum.dynamic)){
            		if (key.equals("userId1")){
//        				reagobj.put(key, map.get("userId"));
        			}else if (key.equals("uuid") || key.equals("deviceUId")){
//            		}else if (key.equals("deviceUId")){
        				reagobj.put(key, Tools.getRandomString(32));
        			}else if (key.equals("virtualId1")){
        				reagobj.put(key, map.get("virtualId"));
        			}else if (key.equals("identityNumber")){
        				reagobj.put(key, Tools.getIdentiyNumber(18));
        			}else if (key.equals("startTime")){
        				if (method.equals("FetchMessage")) {
        					reagobj.put(key, Tools.setEndTime());
        				}else {
        					reagobj.put(key, Tools.setStartTime());
        				}      				
        			}else if (key.equals("endTime")){
        				reagobj.put(key, Tools.setEndTime());
        			}else {
        				reagobj.put(key, map.get(key));
        			}
            	}else if (value.equals("#random#")){
            		if (key.equals("city")){
            			Random random = new Random();
            			reagobj.put(key, random.nextInt(100));
            		}else {
            			reagobj.put(key, Tools.getRandomString(10));
            		}
            	}
            }  
        }
		
        return reagobj;  
    } 
    
    /**
     * 返回配置文件的属性.
     * @return prop
     */
    public static Properties getProp() {
        return prop;
    }
    
    /**
     * 获取当前的项目名
     * @return String
     */
    public static String[] getProjectN(StackTraceElement[] stacktrace){
        StackTraceElement e = stacktrace[1];
        String className = e.getClassName();
        
//        String projectName = null;
        String[] s = className.split("\\.");
//        for (int i=0; i<s.length; i++){
//        	System.err.println(s[i]);
//        	if (s[i].contains("kk")){
//        		projectName = s[i];
//        	};
//        }
        return s;
    }
    
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.getInstance().YEAR);
    }
    
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.getInstance().MONTH);
    }
    
    public static int getDate() {
        return Calendar.getInstance().get(Calendar.getInstance().DATE);
    }
    
    public static String getCurrentDate() {

    	Date now = new Date(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    	String date = null;
		date = dateFormat.format(now);

        return date;
    }
    
    public static long setStartTime() {

    	Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

    	int year = c.get(Calendar.YEAR); 
    	int month = c.get(Calendar.MONTH) + 1; 
    	int day = c.get(Calendar.DATE); 

    	String time = year + "/" + month + "/" + day + " " + "00:00:00";
//    	Date now = new Date(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = null;
		try {
			date = dateFormat.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        long timeStemp = date.getTime();

        return timeStemp;
    }
    
    public static long setEndTime() {

    	Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

    	int year = c.get(Calendar.YEAR); 
    	int month = c.get(Calendar.MONTH) + 1; 
    	int day = c.get(Calendar.DATE); 

    	String time = year + "/" + month + "/" + day + " " + "23:00:00";
    	
//    	Date now = new Date(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = null;
		try {
			date = dateFormat.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        long timeStemp = date.getTime();

        return timeStemp;
    }
    
    /**
     * 获取用户进入房间信息
     * @return String
     */
    public static Map<Integer, Object> getRoomInUserInfo(
    		ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	
    	Map<Integer, Object> hashMap=new HashMap<Integer, Object>();
    	
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag");
			
			switch(msgTag){

			// 停车位 50010201
			case MessageType.ROOM_PARK_MSG:
				JSONArray ParkList = jo.getJSONArray("ParkList");
				hashMap.put(msgTag, ParkList);
				break;
			// 沙发消息  10010239	
			case MessageType.ROOM_SEAT_MSG:
				JSONArray seatList = jo.getJSONArray("seatList");
				hashMap.put(msgTag, seatList);
				break;
			// 已点歌曲 10010220	
			case MessageType.ROOM_SONG_CHOICEED:
				JSONArray a = jo.getJSONArray("a");
				hashMap.put(msgTag, a);
				break;
			// 点歌列表 10010216	
			case MessageType.ROOM_SONGS_LIST:
				JSONArray songList = jo.getJSONArray("songList");
				hashMap.put(msgTag, songList);
				break;
			// 点歌 10010219
			case MessageType.ROOM_SONG_CHOICE:
				hashMap.put(msgTag, jo);
				break;
			default:
				break;
			}
		}
        return hashMap;
    }
    
    /**
     * 获取用户礼物历史记录
     * @return String
     */
    public static Map<Integer, Object> getRoomGiftHistory(
    		ArrayList<JSONObject> msgList,
    		Map<String, Object> map){
    	
    	Map<Integer, Object> hashMap=new HashMap<Integer, Object>();
    	
    	for (int i=0; i<msgList.size(); i++){
			JSONObject jo = msgList.get(i);
			int msgTag = jo.getIntValue("MsgTag");
			
			switch(msgTag){

			// 房间礼物记录 10010215
			case MessageType.ROOM_GIFT_HISTORY:
				JSONArray rsvGiftList = new JSONArray();

				if (jo.getInteger("giftCount")>0){
					rsvGiftList = jo.getJSONArray("rsvGiftList");
				}
				hashMap.put(msgTag, rsvGiftList);
				break;

			default:
				break;
			}
		}
        return hashMap;
    }
    
    /**
     * 发起url请求
     * @return response
     */
    public static JSONObject Start(Integer i, String method, 
    		Map<String, Object> map) throws Throwable {
		String temp = null;
		JSONObject request;
		JSONObject result;
		JSONObject rObj = new JSONObject();
		
		request = kktvXLS.getParamsCase(i, method);
		System.out.println("\n" + "===============> Case " + i + ": "
                + request.getString("comments"));
		String rc = request.getString("expected");
		String comments = request.getString("comments");

		JsonStrAssemble(method, request, map);
		// 手机注册接口(10001031),deviceUId 由前置传入
		if (request.getString("FuncTag").equals("10001031")){
			request.remove("deviceUId");
			request.put("deviceUId", map.get("deviceUId"));
		}
		
		request.remove("comments");
		request.remove("expected");
		
		String webUrl = domain + url;
		
		if (method.equals("XiaoMiLogin")){
//			webUrl = "http://10.0.0.25:9191/payment/entrance";
			webUrl = "http://10.0.0.25:9191/api8/entrance";
			//webUrl = "http://api8.kktv1.com:8080/api8/entrance";
		}
		if (method.equals("PlaceOrderAndCheckOut")){
//			webUrl = "http://10.0.0.25:9191/payment/s0";
			webUrl = paymentUrl;
			//webUrl = "http://api8.kktv1.com:8080/api8/s0";
		}
		if (method.equals("Alipay")){
			webUrl = alipayUrl;
		}
		
		temp = HttpConnect.sendRequest(webUrl, request);
//        BaseIO.lineToFile("[" + sdf.format(new java.util.Date()) 
//        		+ "] Case " + i + "_Request: " + request, logPath + method + ".txt");
//		BaseIO.lineToFile("[" + sdf.format(new java.util.Date()) 
//				+ "] Case " + i + "_Response: " + temp, logPath + method + ".txt");
		
		result = JSONObject.parseObject(temp);

		request.put("expected", rc);
		request.put("comments", comments);
		
		if (map != null){
			map.put((method +"request"), request);
		}
		result.put("expected", rc);
//		JsonResAssert(method, map, result);
		
		rObj.put("res", result);
		rObj.put("req", request);
		
		return rObj;
	}
    
    public static JSONObject uniteLogin(int i, String method, 
    		Map<String, Object> map){
    	JSONObject request = new JSONObject();
    	String up = null;
    	String sv = null;
    	String temp = null;
    	
    	try {
			request = kktvXLS.getParamsCase(i, method);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n" + "===============> Case " + i + ": "
                + request.getString("comments"));
		String expected = request.getString("expected");
		request.remove("comments");
		request.remove("expected");
		
		JsonStrAssemble(method, request, map);
		
		up = Core.upEncode(request.getString("userId"), 
				request.getString("psword"));
    	request.put("up", up);
    	request.remove("userId");
		request.remove("psword");
    	sv = Core.svEncode(request.toJSONString());
    	request.put("sv", sv);

    	String webUrl = domain + url;
		
		try {
			temp = HttpConnect.sendRequest(webUrl, request);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.parseObject(temp);
    }
    
    public static JSONObject secStart(int i, String method, 
    		Map<String, Object> map) throws Exception{
    	JSONObject request = new JSONObject();
    	JSONObject result = new JSONObject();
    	JSONObject rObj = new JSONObject();
    	String temp = null;
    	int passwordSafetyRank = -1;
    	
    	try {
			request = kktvXLS.getParamsCase(i, method);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n" + "===============> Case " + i + ": "
                + request.getString("comments"));
		String expected = request.getString("expected");
		request.remove("comments");
		request.remove("expected");
		
		JsonStrAssemble(method, request, map);
		
		if ((request.getString("FuncTag").equals("40000001")
				|| request.getString("FuncTag").equals("40000008")
				|| request.getString("FuncTag").equals("40000012"))
				&& request.containsKey("passwordSafetyRank")){			
			passwordSafetyRank = request.getIntValue("passwordSafetyRank");
			request.remove("passwordSafetyRank");
		}
		
		// iphone游客注册(40000017),deviceUId 由前置传入
		if (request.getString("FuncTag").equals("40000017")){
			request.remove("deviceUId");
			request.put("deviceUId", map.get("deviceUId"));
		}

		request = securityReq(request, method);
		
    	String webUrl = domain + url;		
		try {
			temp = HttpConnect.sendRequest(webUrl, request);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = JSONObject.parseObject(temp);
		result.put("expected", expected);
		
		request.put("passwordSafetyRank", passwordSafetyRank);
		rObj.put("req", request);
		rObj.put("res", result);
		
		return rObj;
    }
    
    public static Map<String, Object> StartInspector(String webUrl, 
    		JSONObject request) throws Throwable {
    	
    	Map<String, Object> temp = new HashMap<String, Object>();
		
		temp = HttpConnect.sendRequestInspector(webUrl, request);

		return temp;
	}
    
    public static String svEncode(String json){

	    JsonParser parser = new JsonParser();
	    JsonElement jsonEl = parser.parse(json);	    
	    JsonObject jsonObj = null;
	    jsonObj = jsonEl.getAsJsonObject();//转换成Json对象
        
    	return SecurityFunctions.getSingedValue(jsonObj);
    }
    
    public static String upEncodeWeb(String userId, String password){
    	String intputStr = "u="+userId+"&p="+password;
    	return SecurityFunctions.upEncode_web(intputStr);
    }
    
    public static String upEncode(String userId, String password){
    	String intputStr = "u="+userId+"&p="+password;
    	return SecurityFunctions.upEncode(intputStr);
    }
    
    public static String upEncode(String newPwd){
    	String intputStr = "dp="+newPwd;
    	return SecurityFunctions.upEncode(intputStr);
    }
    
    public static String upEncodeWeb(String newPwd){
    	String intputStr = "dp="+newPwd;
    	return SecurityFunctions.upEncode_web(intputStr);
    }
    
    public static String upEncodeUid(String uuid){
    	String intputStr = "uuid="+uuid;
    	return SecurityFunctions.upEncode(intputStr);
    }
    
    public static String upEncodeUnid(String unionid){
    	String intputStr = "unionid="+unionid;
    	return SecurityFunctions.upEncode(intputStr);
    }
    
    public static SocketMessageManager initSocket(int actorId, int appId){
    	SocketMessageManager smm = null;
    	String ws = "";
    	
		Config config = new Config();
		
		ws = config.getWs1(config.getUrl(appId), actorId);
		System.out.println("ws:"+ws);
		// 创建人sockect
		smm = new SocketMessageManager();
		smm.initConnection(ws);
		
		return smm;
    }
    
    public static void roomIn(int actorId, int userId, String token, 
    		int appId, int roomSource, SocketMessageManager smm){
		// 进入房间
		String msg = "{\"MsgTag\":10010201,\"roomId\":"
				+ actorId +",\"platform\":\"1\",\"container\":\"1\",\"softVersion\":\"2\",\"token\":\""
				+ token +"\",\"userId\":"
				+ userId 
//				+"\",\"roomSource\":"
//				+ roomSource 
				+"}";
		smm.sendMessage(msg);
		Tools.sleep(5);
	}
	
    public static void roomOut(SocketMessageManager smm){
		smm.release();
	}
    
    public static void videoPlay(int livetype, int roomSource, 
    		SocketMessageManager smm) {		
		System.err.println("videoPlay");
		String msg = "{\"MsgTag\":10010210,\"liveType\":"
				+ livetype+",\"roomSource\":"
				+ roomSource+",\"roomMode\":"
						+ roomSource + ",\"liveScene\":1}";
		smm.sendMessage(msg);	
	}

	public static void videoStop(int livetype, int roomSource, 
			SocketMessageManager smm) {
		System.err.println("videoStop");
		String msg = "{\"MsgTag\":10010211,\"liveType\":"
				+ livetype+",\"roomSource\":"
				+ roomSource+"}";
		smm.sendMessage(msg);
	}
    
    public static JSONObject securityReq(JSONObject jo, 
    		String method) throws Exception{
    	String up = null;
    	String sv = null;
    	int funcTag = jo.getIntValue("FuncTag");
    	
    	if (10001009 == funcTag){
    		String uuid = Core.upEncodeUid(jo.getString("uuid"));
//    		System.err.println(SecurityFunctions.upd(uuid));
    		jo.put("uuid", uuid);
    		
    		if (20 == jo.getIntValue("openPlatform")){
    			String unionId = Core.upEncodeUnid(jo.getString("unionid"));
//        		System.err.println(SecurityFunctions.upd(unionId));
        		jo.put("unionid", unionId);
    		}
    			
    		return jo;
    	}
    	
    	if (method.equals("Register")){
    		up = (jo.getString("platform").equals("1"))?
					Core.upEncodeWeb(jo.getString("username"),jo.getString("psword"))
					:Core.upEncode(jo.getString("username"),jo.getString("psword"));
//		    		System.err.println(SecurityFunctions.upd(
//					SecurityFunctions.upEncode("u="+jo.getString("username")+"&p="+jo.getString("psword")+"")));
//			System.err.println(SecurityFunctions.upd(up));
    		jo.put("up", up);
    		jo.remove("username");
    	}else if (method.equals("Login_new")){
    		if (!(jo.get("loginType") instanceof String)){
    			switch(jo.getIntValue("loginType")){
    			//
    			case -1:
    				up = (jo.getString("platform").equals("1"))?
    						Core.upEncodeWeb(jo.getString("userId"),jo.getString("token"))
    						:Core.upEncode(jo.getString("userId"),jo.getString("token"));
    	    		jo.remove("userId");
    	    		jo.remove("token");
    				break;
    			// 用户名密码必填，加密成up
    			case -2:
    				up = (jo.getString("platform").equals("1"))?
    						Core.upEncodeWeb(jo.getString("username"),jo.getString("psword"))
    						:Core.upEncode(jo.getString("username"),jo.getString("psword"));
    				System.err.println(SecurityFunctions.upd(up));

    	    		jo.remove("username");
    	    		jo.remove("psword");
    				break;
    			// 用户ID密码必填，加密成up
    			case -3:
    			case 37:
//    				System.err.println(jo);
    				up = (jo.getString("platform").equals("1"))?
    						Core.upEncodeWeb(jo.getString("userId"),jo.getString("psword"))
    						:Core.upEncode(jo.getString("userId"),jo.getString("psword"));
//    				System.err.println(SecurityFunctions.upd(up));
    	    		jo.remove("userId");
    	    		jo.remove("psword");
    				break;
    			// 手机号密码必填，加密成up
    			case -4:
    				break;
    				
    			default:
    				break;
        		}
    		}else {
    			up = (jo.getString("platform").equals("1"))?
						Core.upEncodeWeb(jo.getString("userId"),jo.getString("psword"))
						:Core.upEncode(jo.getString("userId"),jo.getString("psword"));
	    		jo.remove("userId");
    		}
    		jo.put("up", up);
    		
    		// 移除不需要的字段
    		String bb2 = null;
    		ArrayList<String> list = new ArrayList<String>(jo.keySet());
    		for (int i=0; i<list.size(); i++){
    			bb2 = list.get(i);
    			if (!bb2.equals("FuncTag") && !bb2.equals("platform")
            			&& !bb2.equals("loginType") && !bb2.equals("sv")
            			&& !bb2.equals("up")){
            		jo.remove(bb2);
            	}
    		}
    	}else if (40000015 == funcTag){
        	up = Core.upEncode(jo.getString("userId"),jo.getString("psword"));
        	jo.put("up", up);
    		jo.remove("userId");
    	}else if (40000008 == funcTag) {		
    		up = (jo.containsKey("platform") && jo.getString("platform").equals("1"))?
    				Core.upEncodeWeb(jo.getString("username"),jo.getString("psword"))
    				:Core.upEncode(jo.getString("username"),jo.getString("psword"));
        	jo.put("up", up);
    		System.err.println(SecurityFunctions.upd(
			SecurityFunctions.upEncode("u="+jo.getString("userId")+"&p="+jo.getString("psword")+"")));

    		String dp = 
    				(jo.containsKey("platform") && jo.getString("platform").equals("1"))?
    				Core.upEncodeWeb(jo.getString("newpwd"))
    				:Core.upEncode(jo.getString("newpwd"));   				
    		jo.put("dp", dp);
    		jo.remove("newpwd");
    	}else if (40000003 == funcTag){
    		String uuid = Core.upEncodeUid(jo.getString("uuid"));
    		System.err.println(SecurityFunctions.upd(uuid));
//    		if (null != jo.getString("uuid")){
//    			jo.put("uuid", uuid);
//    		}
    		jo.put("uuid", uuid);
    	}else if (40000012 == funcTag){
    		up = Core.upEncode(jo.getString("userId"),jo.getString("psword"));
    		jo.put("up", up);
    		System.err.println(SecurityFunctions.upd(
        			SecurityFunctions.upEncode("u="+jo.getString("userId")+"&p="+jo.getString("psword")+"")));
    	}else if (40000017 == funcTag){
    		up = Core.upEncode(jo.getString("userId"),jo.getString("psword"));
//    		up = SecurityFunctions.upEncode(jo.getString("platform"));
    		jo.put("up", up);
    		System.err.println(SecurityFunctions.upd(
        			SecurityFunctions.upEncode("u="+jo.getString("userId")+"&p="+jo.getString("psword")+"")));
    	}
    	else if (52051108 == funcTag){
    		//up = Core.upEncode(jo.getString("userId"),jo.getString("psword"));
//    		up = SecurityFunctions.upEncode(jo.getString("platform"));
    		//jo.put("up", up);
    		System.err.println(SecurityFunctions.upd(
        			SecurityFunctions.upEncode("u="+jo.getString("userId")+"&p="+jo.getString("psword")+"")));
    	}
    	
    	sv = Core.svEncode(jo.toJSONString());
    	jo.put("sv", sv);

    	return jo;
    }
    

    
    public static void main(String[ ] arg){
    	String str = "{\"a\":1,\"c\":525,\"FuncTag\":40000019,\"phoneNum\":\"15968105592\",\"platform\":3,\"token\":\"A13AA19FA2B8C0C167E050007F0100313B\",\"userId\":7505812,\"verifyCode\":\"125853\"}";
    	System.err.println(svEncode(str));
    }
}
