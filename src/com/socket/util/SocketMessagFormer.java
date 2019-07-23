package com.socket.util;

import org.apache.http.util.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import com.api.core.Config;
import com.api.core.Config.RoomVideoChatLayout;

//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.text.TextUtils;
//
//import com.melot.meshow.ReleaseConfig;
//import com.melot.meshow.Setting;
//import com.melot.meshow.room.mode.RoomVideoChatLayout;
//import com.melot.meshow.sns.ServerConfig;
//import com.melot.meshow.util.Log;
//import com.tencent.openqq.protocol.im_open.stat_forceoffline;

/**
 * 负责生成socket消息
 * */
public class SocketMessagFormer {
	
	public static Config config=new Config();

	private static final String KEY_CONTAINER = "container";

	public static final String KEY_PLATFORM = "platform";
	public final static String KEY_MSG_TAG = "MsgTag";

	private final static String KEY_ROOM_ID = "roomId";
	private final static String KEY_USERID = "userId";
	private final static String KEY_TOKEN = "token";
	private final static String KEY_CHATTYPE = "chatType";
	private final static String KEY_DUSERID = "dUserId";
	private final static String KEY_CONTENTTYPE = "contentType";
	private final static String KEY_CONTENT = "content";
	private final static String KEY_GIFTID = "giftId";
	private final static String KEY_GIFTCOUNT = "giftCount";
	private final static String KEY_STOCK_GIFTCOUNT = "storeHouseCount";
	private static final String KEY_SONGID = "songId";
	// 频道成员
	private static final String KEY_STARTINDEX = "startIndex";
	private static final String KEY_ENDINDEX = "endIndex";

	private static final String KEY_SEATINDEX = "seatIndex";
	private static final String KEY_COUNT = "count";

	private static final String KEY_POSITION = "position";
	private static final String KEY_PRICE = "price";
	private static final String KEY_UCID = "ucId";

	private static final String KEY_GIFTVERSION = "giftVersion";

	private static final String KEY_SOFT_VERSION = "softVersion";

	private static final String KEY_ANNOUNCEMENT_COTENT = "noticeContent";
	
	private static final String KEY_LIVE_TYPE = "liveType";
	
	private static final String KEY_INTERVAL = "interval";
	
	private static final String KEY_GUEST_ID = "guestId";
	
	private static final String KEY_PASSWORD = "password";
	
	private static final String KEY_ROOM_MODE = "roomMode";
	
	private static final String KEY_SORT_INDEX = "sortIndex";
	
	private static final String KEY_HISTORY_TIMESTAMP= "i";
	
	private static final String KEY_HISTORY_COUNT= "c";
	
	private static final String KEY_CHAT_TYPE = "chatType";
    private static final String KEY_VOICE_URL = "voiceUrl";
    private static final String KEY_VOICE_DURATION = "voiceTime";
    
	/** 频道消息类型 */
	public class MessageType {
		///** config */
		//public final config config = new config();
		/** 进入频道首先获取频道地址的方法 */
		public final static int GET_ROOM_URL = 10010401;
		/** 拿到频道URL之后，getRoomInfo */
		public final static int LOGIN_ROOM = 10010201;
		/** 发送文本,emo,彩条 */
		public static final int SEND_TXT = 10010209;
		/** 赠送礼物 */
		public static final int SEND_GIFT = 10010208;
		/** 获取成员列表 */
		public static final int GET_MEM = 10010206;
		
		/** 砸金蛋 */
		public static final int SMASH_GOLDEN_EGG = 10010330;

		/** 购买守护 */
		public static final int PURCHASE_GUARD = 10010366;
		/** 购买爵位勋章 */
		public static final int BUY_DUKE_MEDEL = 10010367;
		/** 频道公告消息推送 */
		public static final int GET_ROOM_NOTICE = 10010229;

		/** youke退出 */
		public static final int ROOM_USER_OUT = 20020113;
		/** 注册用户进入 */
		public static final int ROOM_USER_IN = 20020123;
		/** 游客进入 */
		public static final int ROOM_GUEST_IN = 20020122;
		/** 游客退出 */
		public static final int ROOM_GUEST_OUT = 20020112;

		/** 踢人 */
		public static final int KICK_SOMEBODY = 10010223;
		/** 禁言 */
		public static final int SHUT_UP = 10010224;

		/** 礼物中奖 */
		public static final int GIFT_WIN = 20020132;

		/** 本场排行 */
		public static final int GET_ROOM_RANK = 10010233;

		/** 点歌列表 */
		public static final int ROOM_SONGS_LIST = 10010216;
		/** 点歌 */
		public static final int ROOM_SONG_CHOICE = 10010219;
		/** 已点歌曲 */
		public static final int ROOM_SONG_CHOICEED = 10010220;
		/** 接受点歌 */
		public static final int ROOM_SONG_ACCEPT = 10010221;
		/** 歌曲不存在 */
		public static final int ROOM_SONG_NO_EXIST = 20020120;
		/** 加歌曲 */
		public static final int ROOM_SONG_ADD = 10010217;
		/** 删歌曲 */
		public static final int ROOM_SONG_DELETE = 10010218;
		
		/** 关闭公聊 */
		public static final int ROOM_CLOSE_PUBLICKCHAT = 10010290;
		/** 开启公聊 */
		public static final int ROOM_OPEN_PUBLICKCHAT = 10010291;
		/** 关闭私聊 */
		public static final int ROOM_CLOSE_PRIVATECHAT = 10010297;
		/** 开启公聊 */
		public static final int ROOM_OPEN_PRIVATECHAT = 10010298;


		/** 喇叭消息 */
		public static final int ROOM_BROADCAST = 50010101;
		/** 跑道消息 */
		public static final int ROOM_FLYWAY_MSG = 50010102;

		/** 停车位 */
		public static final int ROOM_PARK_MSG = 50010201;

		/** 频道沙发消息 */
		public static final int ROOM_SEAT_MSG = 10010239;

		/** 抢沙发消息 */
		public static final int ROOM_GET_SEAT_MSG = 10010213;
		/** 飞屏消息 */
		public static final int ROOM_FLY_SCREEN = 10010214;
		/** 房间礼物记录 */
		public static final int ROOM_GIFT_HISTORY = 10010215;

		/** 抢车位 */
		public static final int ROOM_GRAB_PARK_MSG = 10010227;
		/** 抢车位余额不足 */
		public static final int ROOM_GRAB_PARK_NOT_ENOUGH_MONEY_MSG = 50010202;

		/** 礼物列表 */
		public static final int ROOM_GIFT_LIST_MSG = 10010207;
		
		/** 库存礼物列表 */
		public static final int ROOM_STOCK_GIFT_LIST_MSG = 10010266;
		
		/** 刷新单个库存礼物数量 */
		public static final int ROOM_REFRESH_STOCK_GIFT_MSG = 10010267;
		/** 送库存礼物的数量大于实际数量 */
		public static final int ROOM_STOCK_GIFT_COUNT_INSUFFICIENT_MSG = 10010268;

		/** 主播信息 */
		public static final int ROOM_OWNER_INFO_MSG = 10010204;

		/** 开始直播 */
		public static final int ROOM_START_LIVE= 10010210;
		
		/** 结束直播 */
		public static final int ROOM_END_LIVE= 10010211;

		/** 直播状态 */
		public static final int ROOM_MODE = 10010250;

		/** 余额 */
		public static final int ROOM_MONEY = 10010238;
		
		/** 房间红包 */
		public static final int ROOM_RED_EVELOPE = 10010800;

		/** 频道强制退出 */
		public static final int ON_FORCE_EXIT = 30000003;

		/** 频道系统消息提示 */
		public static final int ON_ROOM_TIPS_DIALOG = 21;

		/** qq会员专属礼物 */
		public static final int ON_GIFT_BELONG_QQ = 5;
		/** vip专属礼物 */
		public static final int ON_GIFT_BELONG_COMMON_VIP = 6;
		/** 紫色vip专属礼物 */
		public static final int ON_GIFT_BELONG_SUPER_VIP = 7;
		/** 知府以上专属礼物 */
		public static final int ON_GIFT_BELONG_LV11 = 8;
		/** 5位靓号专属礼物 */
		public static final int ON_GIFT_BELONG_PRETTY5 = 9;
		/** 4位专属礼物 */
		public static final int ON_GIFT_BELONG_PRETTY4 = 10;
		/** 新版本专属礼物 */
		public static final int ON_GIFT_BELONG_ELSE = 11;
		/** 频道欢迎消息 */
		public static final int ON_ROOM_WELCOME_MSG = 10010248;
		/** 频道配置 */
		public static final int ON_ROOM_CONFIG = 10010265;
		/** 公聊历史消息 */
		public static final int ON_ROOM_PUBLIC_HISTORY_MSG = 10010264;
		/** 私聊历史消息 */
		public static final int ON_ROOM_PRIVATE_HISTORY_MSG = 10010263;
		
		/** 删除私聊历史消息 */
		public static final int DEL_ROOM_PRIVATE_HISTORY_MSG = 10010269;

		// ==========================================error================================
		/** 频道被禁止进入 */
		public static final int ROOM_ENTER_FORBIDDEN = 20020130;
		/** 频道人数满员 */
		public static final int ROOM_MEM_FULL = 20020133;
		/** 在其他频道已登录 */
		public static final int ROOM_LOGINED_ELSE = 20020131;
		/** 对方已离开 */
		public static final int FRIEND_HAS_LEAVE = 20020114;
		/** 尚未登录频道,游客模式 */
		public static final int ROOM_NOT_LOGINED = 20020102;
		/** 未进入任何频道 */
		public static final int NO_ROOM_ENTERED = 20020101;
		/** 用户所在频道和用户请求登陆的频道不一样 */
		public static final int LOGIN_ROOM_DIFF = 20020103;
		/** 频道不存在 */
		public static final int ROOM_NOT_EXISTS = 20020104;
		/** 已经登录频道,重复登入 */
		public static final int ROOM_LOGINED_ALREADY = 20020105;
		/** 获取token失败 */
		public static final int GET_TOKEN_FAILED = 20020106;
		/** 验证token失败 */
		public static final int CHECK_TOKEN_FAILED = 20020107;
		/** 聊天类型为私聊或悄悄话时接收者不能为所有人 */
		public static final int MSG_TYPE_INVALID = 20020109;
		/** 消息的chatType不对 */
		public static final int MSG_CHAT_TYPE_INVALID = 20020110;
		/** 余额不足 */
		public static final int MONEY_NOT_ENOUGH = 20020111;
		/** 礼物不存在 */
		public static final int GIFT_NOT_EXISTS = 20020115;
		/** 发送者和接受者一样 */
		public static final int SEND_FROM_TO_SAME = 20020118;
		/** 用户的等级信息 */
		public static final int USER_LEVEl_INFO = 10010202;

		// =============综合直播间============
		/** 发送,接受消息 */
		public static final int ROOM_CHAT_MSG = 10010249;
		public static final int ROOM_CHAT_RECEIVE = 10010211;
		/** 获取直播间历史聊天记录 */
		public static final int GET_ROOM_CHAT_MSG = 10010261;

		// =============系统消息============
		// 巡官提醒
		public static final int ROOM_REMIN_MSG = 60000001;
		// 巡官警告
		public static final int ROOM_WARNING_MSG = 60000002;
		// Toast 消息
		public static final int TOAST_MSG = 40000001;
		// Dialog 消息
		public static final int DIALOG_MSG = 40000002;

		// 系统消息
		public static final int SYSTEM_MSG = 30000001;
		// 心跳包
		public static final int SEND_HEARTBEAT = 10010299;

		// =============麦序============
		/**频道模式 */
		public static final int ONLIVENEW_GET_ROOM_MODE = 10010251;
		/**用户请求上麦*/
		public static final int ONLIVENEW_USER_REQUEST = 10010252;
		
		/**用户发送一条弹幕*/
		public static final int DAN_MA_REQUEST = 10010300;

        /**
		 * 请求用户上麦的限制测试
		 * 异常返回:如果直播模式为主播模式返回异常消息:{MsgTag : 16}
		 * 异常返回:如果主播申请排麦返回异常消息:{MsgTag : 12} 
		 * 异常返回:如果非紫色VIP申请排麦返回异常消息:{MsgTag :13} 
		 * 异常返回:如果麦序列表已满返回异常消息:{MsgTag : 19} 
		 * 异常返回:如果用户已经在麦序列表中返回异常消息:{MsgTag: 18}
		 * **/
		public static final int ONLIVENEW_MODE_PLAYER = 16;
		public static final int ONLIVENEW_REQUEST_MAC = 12;
		public static final int ONLIVENEW_NOT_VIP = 13;
		public static final int ONLIVENEW_MAX_USER = 19;
		public static final int ONLIVENEW_HAVEED_USER = 18;
		public static final int ONLIVENEW_OTHER_ROOM = 20;
		/**获取麦序列表 */
		public static final int ONLIVENEW_GET_ROOM_LIST = 10010253;
		/**调整用户在麦序中的位置*/
		public static final int ONLIVENEW_SEQUENCE_ADJUST = 10010254;
 		/**将用户从麦序中移出     所有客户端收到返回:{"MsgTag":10010257,"sortIndex":1}*/
		public static final int ONLIVENEW_REMOVE_USER = 10010257;
		/**用户取消上麦  返回:{"MsgTag":10010258,"sortIndex":1} */
		public static final int ONLIVENEW_CANCEL_USER = 10010258;
		/**让麦序中的用户连麦  改变状态*/
		public static final int ONLIVENEW_CONNECT_USER = 10010259;
		/**上麦用户视频流地址*/
		public static final int ONLIVENEW_LIVE_VIDEO_ADDRESS = 10010294;

		/**让连麦中的用户直播  改变状态*/
		public static final int ONLIVENEW_LIVE_VIDEO_STATE = 10010260;
		/**让连麦中的用户直播 */
		public static final int ONLIVENEW_LIVE_PLAY_USER = 10010293;
		
		/**让直播中的用户回到连麦状态 */
		public static final int ONLIVENEW_CONNECT_STATE = 10010262;
		
		/** out: 给私密频道设置密码      in: 通知用户该频道是私密频道*/
		public static final int ROOM_SET_PASSWORD = 10010308;

        /**获取房间管理员列表 */
        public static final int ROOM_GET_ROOM_ADMIN_LIST = 10010205;
        /**将用户设置为管理员 */
        public static final int ROOM_SET_ROOM_ADMIN = 10010234;
        /**取消用户管理员身份 */
        public static final int ROOM_CANCEL_ROOM_ADMIN = 10010235;
        
        /**通知或被通知当前只推音频流 */
        public static final int ROOM_LIVE_STREAM_AUDIO = 10010312;     
        /**通知或被通知当前推视频流 */
        public static final int ROOM_LIVE_STREAM_VIDEO = 10010313;
        
        /**公聊发送失败 */
        public static final int ROOM_CHAT_SEND_FAILED = 10010336;
        
        //public static final int ROOM_VOICE_INFO = 10010209;

    }

	/** 公聊，私聊，悄悄话 */
	public class ChatType {
		/** 公聊 */
		public final static int PUBLIC = 0;
		/** 私聊 */
		public final static int SINGLE = 1;
		/** 悄悄话 */
		public final static int PRIVATE = 2;
	}

	/** 文本类型：文本,表情;彩条; */
	public class ContentType {
		/** 文本,表情 */
		public final static int TXT_EMO = 1;
		/** 彩条 */
		// public final static int COLOR_FONT = 2;
	}
	
	//3 音频 4 随手播 5 私密房
	public class RoomMode {
		/** 音频 */
		public final static int AUDIO = 3;
		/** 随手播 */
		public final static int VIDEO = 4;
		/** 私密房 */
		public final static int AUTH = 5;
	}


	public static String createHeartbeatMsg() {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.SEND_HEARTBEAT);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	// public static String createGetRoomUrlMsg(int roomId) {
	// JSONObject js = null;
	// js = new JSONObject();
	// try {
	// js.put(KEY_MSG_TAG, MessageType.GET_ROOM_URL);
	// js.put(KEY_ROOM_ID, roomId);
	// js.put(KEY_PLATFORM, config.getPlatForm());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return js.toString();
	// }

	/**
	 * 发送消息获取此room的info
	 * 
	 * @param roomId
	 *            频道id
	 * */
	public static String createLoginRoomMsg(long roomId, String password) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.LOGIN_ROOM);
			js.put(KEY_ROOM_ID, roomId);
			if(!TextUtils.isEmpty(password))
				js.put(KEY_PASSWORD, password);
			js.put(KEY_PLATFORM, config.getPlatForm());
			if (config.getToken() != null) {
				js.put(KEY_USERID, config.getUserId());
				js.put(KEY_TOKEN, config.getToken());
			}
			
//			if(Setting.getInstance().isVisitor() && Setting.getInstance().getUserId() > 0) {
//				js.put(KEY_GUEST_ID, Setting.getInstance().getUserId());
//			}
			
			js.put(KEY_SOFT_VERSION, config.getVersion());
			js.put(KEY_CONTAINER, config.getContainer());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	/**
	 * 当chatType 为public的时候，userIdx 为-1,其他则对方userIdx
	 * 
	 * @param chatType
	 *            聊天类型
	 * @param txt
	 *            发送内容
	 * */
	public static String createSendTxtMsg(int chatType, long dUserid, String txt) {
		JSONObject js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.SEND_TXT);
			js.put(KEY_CHATTYPE, chatType);
			js.put(KEY_DUSERID, dUserid);
			js.put(KEY_CONTENTTYPE, ContentType.TXT_EMO);
			js.put(KEY_CONTENT, txt);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String checkGiftVersionMsg(int giftVersion) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.ROOM_GIFT_LIST_MSG);
			js.put(KEY_GIFTVERSION, giftVersion);	
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}
	
	public static String getStockGiftMsg() {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.ROOM_STOCK_GIFT_LIST_MSG);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String createGetRoomMemMsg(int start, int end) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.GET_MEM);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_STARTINDEX, start);
			js.put(KEY_ENDINDEX, end);
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String createModifyAnnouncementMsg(String announcementMsg) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.GET_ROOM_NOTICE);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_ANNOUNCEMENT_COTENT, announcementMsg);
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String createGetSongsMsg() {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.ROOM_SONGS_LIST);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	/** 请求频道本场排行 */
	public static String createGetRankMsg() {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.GET_ROOM_RANK);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String createChoiceSongsMsg(String songId) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.ROOM_SONG_CHOICE);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SONGID, songId);
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}


	public static String createSendGiftMsg(int giftId, long toUserId, int giftCount, boolean bIsStockGift) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.SEND_GIFT);
			js.put(KEY_GIFTID, giftId);
			js.put(KEY_DUSERID, toUserId);
			if (bIsStockGift) {
				js.put(KEY_STOCK_GIFTCOUNT, giftCount);
			}
			else {
				js.put(KEY_GIFTCOUNT, giftCount);
			}
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String createKickMsg(long userid) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.KICK_SOMEBODY);
			js.put(KEY_USERID, userid);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}
	
	public static String createKickMinuteMsg(long userid) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.KICK_SOMEBODY);
			js.put(KEY_USERID, userid);
			js.put(KEY_INTERVAL, 1);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String createShutUpMsg(long userid) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.SHUT_UP);
			js.put(KEY_USERID, userid);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String createGrabSofaMsg(int seatIdx, int count) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.ROOM_GET_SEAT_MSG);
			js.put(KEY_SEATINDEX, seatIdx);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_COUNT, count);
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String createParkMsg(int pos, int price, int ucId) {
		JSONObject js = null;
		js = new JSONObject();
		try {
			js.put(KEY_MSG_TAG, MessageType.ROOM_GRAB_PARK_MSG);
			js.put(KEY_POSITION, pos);
			js.put(KEY_PRICE, price);
			js.put(KEY_UCID, ucId);
			js.put(KEY_PLATFORM, config.getPlatForm());
			js.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static String createPublicHistory(long start, int needCount) {
//		Log.v("aaa", "send history >>> "+start + "needcount > "+needCount);
		JSONObject jo = new JSONObject();
		try {
			jo.put(SocketMessagFormer.KEY_MSG_TAG, MessageType.ON_ROOM_PUBLIC_HISTORY_MSG);
			jo.put(SocketMessagFormer.KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
			jo.put("i", start);
			jo.put("c", needCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		Log.v("aaa", "send history >>> json > "+jo.toString());
		return jo.toString();
	}

	public static String createPrivateHistory(long start, int needCount) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(SocketMessagFormer.KEY_MSG_TAG, MessageType.ON_ROOM_PRIVATE_HISTORY_MSG);
			jo.put(SocketMessagFormer.KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
			if (start > 0)
				jo.put("i", start);
			if (needCount > 0)
				jo.put("c", needCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	public static String createDelPrivateHistory() {
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.DEL_ROOM_PRIVATE_HISTORY_MSG);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	public static String startLive(int liveType, int recordMode) {
		JSONObject jo = new JSONObject();
		int roomMode = RoomMode.AUDIO;
		RoomVideoChatLayout rc;
		switch (recordMode) {
		case 1:
			roomMode = RoomMode.AUDIO;
			break;
		case 2:
			roomMode = RoomMode.VIDEO;
			break;
		case 3:
			roomMode = RoomMode.AUTH;
			break;
		}
		
		try {
			jo.put(KEY_MSG_TAG, MessageType.ROOM_START_LIVE);
			jo.put(KEY_LIVE_TYPE, liveType);
			jo.put(KEY_ROOM_MODE, roomMode);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	public static String stopLive(int liveType) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.ROOM_END_LIVE);
			jo.put(KEY_LIVE_TYPE, liveType);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	/**用户请求上麦*/
	public static String requestOnLiveNew() {
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.ONLIVENEW_USER_REQUEST);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	/***用户发送一条弹幕消息***/
	public static String requestDanma(String content){
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.DAN_MA_REQUEST);
			jo.put(KEY_CONTENT, content);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}

	public static String requestOnLiveList() {
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.ONLIVENEW_GET_ROOM_LIST);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}

	public static String requestOnLiveNewEnd() {
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.ONLIVENEW_CANCEL_USER);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	public static String requestConnectUser(int sortIndex) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.ONLIVENEW_CONNECT_USER);
			jo.put(KEY_SORT_INDEX, sortIndex);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	public static String requestLiveUser(int sortIndex) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.ONLIVENEW_LIVE_VIDEO_STATE);
			jo.put(KEY_SORT_INDEX, sortIndex);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	public static String requestRemoveUser(int sortIndex) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.ONLIVENEW_REMOVE_USER);
			jo.put(KEY_SORT_INDEX, sortIndex);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	public static String setPassword(String password) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(KEY_MSG_TAG, MessageType.ROOM_SET_PASSWORD);
			jo.put(KEY_PASSWORD, password);
			jo.put(KEY_PLATFORM, config.getPlatForm());
			jo.put(KEY_SOFT_VERSION, config.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();
	}

    public static String getRoomAdminList() {
        JSONObject jo = new JSONObject();
        try {
            jo.put(KEY_MSG_TAG, MessageType.ROOM_GET_ROOM_ADMIN_LIST);
            jo.put(KEY_PLATFORM, config.getPlatForm());
            jo.put(KEY_SOFT_VERSION, config.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    public static String setRoomAdmin(long userId) {
        JSONObject jo = new JSONObject();
        try {
            jo.put(KEY_MSG_TAG, MessageType.ROOM_SET_ROOM_ADMIN);
            jo.put(KEY_PLATFORM, config.getPlatForm());
            jo.put(KEY_USERID, userId);
            jo.put(KEY_SOFT_VERSION, config.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    public static String cancelRoomAdmin(long userId) {
        JSONObject jo = new JSONObject();
        try {
            jo.put(KEY_MSG_TAG, MessageType.ROOM_CANCEL_ROOM_ADMIN);
            jo.put(KEY_PLATFORM, config.getPlatForm());
            jo.put(KEY_USERID, userId);
            jo.put(KEY_SOFT_VERSION, config.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    public static String setLiveStreamMode(boolean bOnlyAudio) {
        JSONObject jo = new JSONObject();
        try {
        	if(bOnlyAudio)
        		jo.put(KEY_MSG_TAG, MessageType.ROOM_LIVE_STREAM_AUDIO);
        	else 
        		jo.put(KEY_MSG_TAG, MessageType.ROOM_LIVE_STREAM_VIDEO);
            jo.put(KEY_PLATFORM, config.getPlatForm());
            jo.put(KEY_SOFT_VERSION, config.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo.toString();
	}
    
    public static String getSendVoiceInfoUrl(String voiceUrl, String voiceText,long duration) {
		JSONObject jo = new JSONObject();
        try {
            jo.put(KEY_MSG_TAG,MessageType.SEND_TXT);
            jo.put(KEY_DUSERID,"-1");
            jo.put(KEY_CHAT_TYPE,"0");
            jo.put(KEY_CONTENT, voiceText);
            jo.put(KEY_VOICE_URL, voiceUrl);
            jo.put(KEY_VOICE_DURATION, String.valueOf(duration));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  jo.toString();
	}
    
}
