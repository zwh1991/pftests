package com.api.core;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpException;

import com.tools.HttpResponds;

import com.api.core.Core;
import com.api.core.Config;



public class ApiEnum {	
	/** 处理正确，无错误返回 */
	public static final String ERROR_CODE_SUCCESS = "00000000";
	
	public static final String trim = "";               // 需要remove的空串
	public static final String dynamic = "#dynamic#";   // 传入参数
	public static final String blank = "#void#";        // 参数值为空
	
	/** 入参需要调用其他接口返回值的 接口 */
	public static final String USERFUNCTIONS_REGISTER = "register";
	public static final String USERFUNCTIONS_LOGIN_NEW = "Login_new";
	public static final String USERFUNCTIONS_LOGOUT = "logout";
	public static final String USERFUNCTIONS_LOGIN_VIA_USER_AND_TOKEN = 
			"loginViaUserIdAndToken";
	public static final String USERFUNCTIONS_CHANGE_PASSWORD = "changePwd";
	public static final String USERFUNCTIONS_BIND_QQ = "bindQQ";
	public static final String USERFUNCTIONS_UNBIND_QQ = "unbindQQ";
	public static final String USERFUNCTIONS_BIND_PHONE = "bindPhone";
	public static final String USERFUNCTIONS_UNBIND_PHONE = "unbindPhone";
	public static final String USERFUNCTIONS_BIND_WEIBO = "bindWeibo";
	public static final String USERFUNCTIONS_UNBIND_WEIBO = "unbindWeibo";
	public static final String USERFUNCTIONS_GET_BOUND_ACCOUNT = "getBoundAccount";
	public static final String USERFUNCTIONS_GET_AWARDS = "getAwards";
	public static final String USERFUNCTIONS_UPDATE_USER_LNGLAT = "updateUserLngLat";
	public static final String USERFUNCTIONS_GET_EXTENSION_USER_SECURITY_CENTER = 
			"getExtensionUserSecurityCenter";
	public static final String USERFUNCTIONS_GET_EXTENSION_ACCOUNT_ASSETS = 
			"getExtensionAccountAssets";
	public static final String USERFUNCTIONS_GET_EXTENSION_DETAILS = 
			"getExtendedDetails";
	public static final String USERFUNCTIONS_EXCHANGE_POINT_TO_MIMONEY = 
			"exhangePoiontToMimoney";
	public static final String USERFUNCTIONS_GET_BACK_USERS_AND_GIVE_AUTO_COUNTS = 
			"getBackUsersAndGiveAutoCounts";
	public static final String USERFUNCTIONS_GET_USER_TOKEN = "getUserToken";
	
	public static final String USERFUNCTIONS_SEND_SMS = "sendSMS";
	public static final String USERFUNCTIONS_MOBILE_GET_USERID = "mobileGetUserId";
	public static final String USERFUNCTIONS_GET_USER_FROM_BY_USERID = "getUserFromByUserId";
	public static final String USERFUNCTIONS_BUY_FLOW_CARD = "buyFlowCard";
	
	public static final String WIFIFUNCTIONS_USE_FLOW_CARD = "useFlowCard";

	public static final String PROFILEFUNCTIONS_GET_CHARGE_INFO = "getChargeInfo";
	public static final String PROFILEFUNCTIONS_GET_USER_ASSET_LIST = "getUserAssetList";
	
	/** 房间用例类型 */
	public class NodeCaseType {
		/** 赠送礼物 */
		public final static int SEND_GIFT = 10000001;
		/** 弹幕 */
		public final static int SEND_MSG = 10000002;
		/** 绑定手机号 — 砸金蛋 */
		public final static int SMASH_GOLDEN_EGG_BINDPHONE = 10000003;		
		/** 未绑定手机号 — 砸金蛋 */
		public final static int SMASH_GOLDEN_EGG_UNBINDPHONE = 10000004;
		/** 用户发送小喇叭 */
		public final static int USER_BROADCAST = 10000005;
		/** 抢车位 */
		public final static int GRAB_PARKING = 10000006;
		/** 抢座 */
		public final static int GRAB_SEAT = 10000007;
		/** 发送文字消息 */
		public final static int SEND_TXT = 10000008;
		/** 跑道消息 */
		public final static int FLY_WAY = 10000009;
		/** 房间礼物记录消息 */
		public final static int ROOM_GIFT_HISTORY = 10000010;
		/** 房间点歌消息 */
		public final static int ROOM_SONG_CHOICEED = 10000011;
		/** 踢人消息 */
		public final static int ROOM_KICK_OUT = 10000012;
		/** 守护消息 */
		public final static int PURCHASE_GUARD = 10000013;
		/** 公聊、私聊 消息 */
		public final static int ROOM_CHATSCREEN = 10000014;
		/** 弹幕 消息 */
		public final static int BARRAGE = 10000015;
		/** 公聊屏 关闭 消息 */
		public final static int PUBLICSCREEN_CLOSE = 10000016;
		/** 私聊屏 关闭 消息 */
		public final static int PRIVATECSCREEN_CLOSE = 10000017;
		/** 守护座驾 消息 */
		public final static int SET_GUARD_CAR = 10000018;
		/** 购买爵位勋章 消息 */
		public final static int BUY_DUKE_MEDEL = 10000019;
	}
	
	public class AppId {
		/** KKTV1 */
		public final static int KKTV = 1;
		/** KKTV5 */
		public final static int KKGAME = 2;
		/** Bang */
		public final static int BANG = 10;
		/** VR */
		public final static int VR = 12;
	}
	
	public class Agency {
		public final static int AGENCY1 = 1001;//"宁波爱云长科技有限公司";
		public final static int AGENCY2 = 1002;//"宁波微工猫信息科技发展有限公司";
		public final static int AGENCY3 = 1000;//"天津海格力斯文化经纪有限公司";
	}
	
	public class LiveType {
		/** 停播 */
		public final static int STOPLIVE = 1;
		/** 开播 */
		public final static int STARTLIVE = 2;
	}
	
	public class RoomSource {
		/** 娱乐PC模板 */
		public final static int SHOWPLATE = 1;
		/** 游戏PC模板  */
		public final static int GAMEPLATE = 2;
		/** 棒手机模板  */
		public final static int BANGPLATE = 10;
	}
	
	public class vip {
		/** VIP */
		public final static int VIP = 100001;
		/** SVIP  */
		public final static int SVIP = 100004;
	}
	
	public class ApplyActorKKopp {
		/** 登录帐号 */
		public final static String LOGIN_NAME = "melotadmin";
		/** 登录密码 */
		public final static String LOGIN_PWD = "123456";
		/** 实名认证登录 */
		public final static String LOGIN_URL = 
				"http://10.0.0.2:9494/kkopp/loginAction!userLogin.action";

		public final static String BASE_URL = "http://10.0.0.2:9494";

		public final static String setactorurl =
				"http://10.0.0.2:9494/kkopp/opt/roomInfoAction!inputActor.action?resourceId=172";
		
		public final static String cancelactorurl =
				"http://10.0.0.2:9494/kkopp/opt/roomInfoAction!deleteRoomInfo.action?resourceId=172&roomId=";
	}
	
	
	
}