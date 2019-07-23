package com.socket.util;

import java.util.ArrayList;

import javax.naming.Context;

import org.apache.http.util.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.socket.util.SocketMessageManager.CONNECT_STATE;

import com.socket.util.SocketMessagFormer.MessageType;

/** 接收消息，专门parse 消息的一个线程 */
public class MessageInThread extends MessageThread {

	private final static String TAG = "MessageInThread";
//	private RoomMessageListener mRoomListener;
	private Context mContext;
	private CONNECT_STATE socketState = CONNECT_STATE.CONNECTED;

//	public MessageInThread(Context con, RoomMessageListener roomListener) {
//		mContext = con;
//		mRoomListener = roomListener;
//	}
	public MessageInThread(Context con) {
		mContext = con;

	}

	@Override
	protected void doTask(String message) {
//		Log.i(TAG, "doTask->" + message);
//		Log.i(TAG, "socket doTask->" + message);
		if (socketState == CONNECT_STATE.CLOSED) {
			return;
		}
		//android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
		JSONObject jo = null;
		try {
			jo = JSONObject.parseObject(message);
		} catch (JSONException e) {
//			Log.e(TAG, "JSONObject format error:" + e.getMessage());
			e.printStackTrace();
		}
		if (jo == null) {
//			Log.e(TAG, "what msg ?!! ->" + message);
			return;
		}

		int msgTag = jo.getIntValue("MsgTag");

		//sendGameSocketMsg(jo);

	//	Log.i(TAG, "msgTag->" + msgTag);
		switch (msgTag) {
		case MessageType.LOGIN_ROOM:
//			RoomLoginParser roomparser = new RoomLoginParser(jo);
//			roomparser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onLoginRoom(null, roomparser.getMoney());
//			roomparser.release();
			break;
		case MessageType.ROOM_GIFT_LIST_MSG:
//			RoomGiftListParser roomGiftListParser = new RoomGiftListParser(jo);
//			roomGiftListParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onGiftInfo();
			break;
		case MessageType.ROOM_STOCK_GIFT_LIST_MSG:
//			StockGiftListParser stockGiftListParser = new StockGiftListParser(jo);
//			stockGiftListParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onStockGiftInfo(false);
			break;
		case MessageType.ROOM_REFRESH_STOCK_GIFT_MSG:
//			StockGiftParser stockGiftParser = new StockGiftParser(jo);
//			stockGiftParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onStockGiftInfo(true);
			break;
		case MessageType.ROOM_STOCK_GIFT_COUNT_INSUFFICIENT_MSG:
//			StockGiftParser stockParser = new StockGiftParser(jo);
//			stockParser.parse();
//			if (mRoomListener != null && stockParser.getGiftCount() > 0) {
//				StockGift stockGift = new StockGift();
//				stockGift.setGiftCount(stockParser.getGiftCount());
//				stockGift.setId(stockParser.getGiftId());
//				stockGift.setName(stockParser.getGiftName());
//				stockGift.setUnit(stockParser.getGiftUnit());
//				mRoomListener.onStockGiftInSufficient(stockGift);
//			}
			break;
		case MessageType.ROOM_OWNER_INFO_MSG:
//			RoomOwnerParser roomOwnerParser = new RoomOwnerParser(jo);
//			roomOwnerParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onRoomInfo(roomOwnerParser);
			break;
		case MessageType.SEND_TXT:
//			MessageParser mp = new MessageParser(jo);
//			mp.parse();
//			if (mRoomListener != null){
//				//判断是否语音
//				if(mp.isAudio()){
//					mRoomListener.onRoomRecordText(mp);
//				}else{
//					mRoomListener.onSendTxt(mp);
//				}
//			}
//			mp.release();
			break;
		case MessageType.SEND_GIFT:
//			RoomSendGiftParser rg = new RoomSendGiftParser(jo);
//			rg.parse();
//			if (mRoomListener != null)
//				mRoomListener.onSendGift(rg);
//			rg.release();
			break;
		case MessageType.ROOM_GUEST_OUT:
	//		Log.d(TAG, "[userLogTAG] " + message);
//			final String KEY_USERCOUNT_GUEST_OUT = "userCount";
//			final String KEY_GUESTCOUNT_GUEST_OUT = "guestCount";
//			int userCount = 0;
//			int guestCount = 0;
//			try {
//				if (jo.containsKey(KEY_USERCOUNT_GUEST_OUT))
//					userCount = jo.getIntValue(KEY_USERCOUNT_GUEST_OUT);
//				if (jo.containsKey(KEY_GUESTCOUNT_GUEST_OUT))
//					guestCount = jo.getIntValue(KEY_GUESTCOUNT_GUEST_OUT);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			if (mRoomListener != null)
//				mRoomListener.onGuestOut(userCount, guestCount);
			break;
		case MessageType.ROOM_GUEST_IN:
	//		Log.d(TAG, "[userLogTAG] " + message);
//			final String KEY_USERCOUNT_GUEST_IN = "userCount";
//			final String KEY_GUESTCOUNT_GUEST_IN = "guestCount";
//			int userCount_in = 0;
//			int guestCount_in = 0;
//			try {
//				if (jo.containsKey(KEY_USERCOUNT_GUEST_IN))
//					userCount_in = jo.getIntValue(KEY_USERCOUNT_GUEST_IN);
//				if (jo.containsKey(KEY_GUESTCOUNT_GUEST_IN))
//					guestCount_in = jo.getIntValue(KEY_GUESTCOUNT_GUEST_IN);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			if (mRoomListener != null)
//				mRoomListener.onGuestIn(userCount_in, guestCount_in);
			break;
		case MessageType.ROOM_USER_OUT:
//			Log.d(TAG, "[userLogTAG] " + message);
//
//			final String KEY_USERCOUNT_USER_OUT = "userCount";
//			final String KEY_GUESTCOUNT_USER_OUT = "guestCount";
//			final String KEY_USERID_USER_OUT = "userId";
//			final String KEY_USERNAME_USER_OUT = "nickname";
//
//			int userCount_userOut = 0;
//			int guestCount_userOut = 0;
//			RoomMember user = new RoomMember();
//			try {
//				if (jo.containsKey(KEY_USERCOUNT_USER_OUT))
//					userCount_userOut = jo.getIntValue(KEY_USERCOUNT_USER_OUT);
//				if (jo.containsKey(KEY_GUESTCOUNT_USER_OUT))
//					guestCount_userOut = jo.getIntValue(KEY_GUESTCOUNT_USER_OUT);
//				if (jo.containsKey(KEY_USERID_USER_OUT))
//					user.userId = jo.getIntValue(KEY_USERID_USER_OUT);
//				if (jo.containsKey(KEY_USERNAME_USER_OUT))
//					user.userName = jo.getString(KEY_USERNAME_USER_OUT);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			if (mRoomListener != null)
//				mRoomListener.onUserOut(user, userCount_userOut, guestCount_userOut);
			break;
		case MessageType.ROOM_USER_IN:
//			Log.d(TAG, "[userLogTAG] " + message);
//			RoomMemberParser rpIn = new RoomMemberParser(jo);
//			rpIn.parse();
//			if (mRoomListener != null)
//				mRoomListener.onUserIn(rpIn.getUser(), rpIn.isGiftStar(), rpIn.getCar(),
//						rpIn.getIdx(), rpIn.getUserCount(), rpIn.getGuestCount());
			break;
		case MessageType.GET_MEM:
//			RoomMemListParser memParser = new RoomMemListParser(jo);
//			memParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onGetRoomMember(memParser);
			break;
		case MessageType.GIFT_WIN:
//			GiftWinParser giftwinParser = new GiftWinParser(jo);
//			giftwinParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onGiftWin(giftwinParser);
			break;
		case MessageType.GET_ROOM_RANK:
//			RoomRankParser roomRankParser = new RoomRankParser(jo);
//			roomRankParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onGetRankData(roomRankParser);
			break;
		case MessageType.ROOM_SONG_CHOICE:
//			long price = 0;
//			int userId = 0;
//			final String KEY_PRICE = "price";
//			final String KEY_USER_ID = "userId";
//			if (jo.containsKey(KEY_USER_ID)) {
//				try {
//					userId = jo.getIntValue(KEY_USER_ID);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//			if (userId != Setting.getInstance().getUserId()) {
//				break;
//			}
//			if (jo.containsKey(KEY_PRICE)) {
//				try {
//					price = jo.getLong(KEY_PRICE);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//			if (mRoomListener != null) {
//				mRoomListener.onChoiceSongBack(price);
//			}
			break;
		case MessageType.GET_ROOM_NOTICE:

//			String notice = null;
//			String noticeLink = null;
//			final String KEY_NOTICE = "noticeContent";
//			final String KEY_NOTICE_LINK = "noticeHref";
//			try {
//				if (jo.containsKey(KEY_NOTICE))
//					notice = jo.getString(KEY_NOTICE);
//				if (jo.containsKey(KEY_NOTICE_LINK))
//					noticeLink = jo.getString(KEY_NOTICE_LINK);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if (mRoomListener != null)
//				mRoomListener.onRoomNotice(notice, noticeLink);
			break;
		case MessageType.ROOM_FLYWAY_MSG:
//			FlyWayParser flywayParser = new FlyWayParser(mContext, jo);
//			flywayParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onFlyWayMessage(flywayParser.getMarqueeList());
			break;
		case MessageType.ROOM_PARK_MSG:
//			RoomParkParser rp = new RoomParkParser(jo);
//			rp.parse();
//			if (mRoomListener != null)
//				mRoomListener.onParkListMessage(rp.getParkList());
			break;
		case MessageType.ROOM_SEAT_MSG:
//			RoomSeatParser rp2 = new RoomSeatParser(jo);
//			rp2.parse();
//			if (mRoomListener != null)
//				mRoomListener.onSeatListMessage(rp2.getRoomSeatList());
			break;
		case MessageType.ROOM_MONEY:
//			final String key_money = "leftMoney";
//			if (jo.containsKey(key_money)) {
//				if (mRoomListener != null)
//					try {
//						mRoomListener.onMoneyUpdate(jo.getLong(key_money));
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//			}
			break;

		case MessageType.ON_FORCE_EXIT:
//			if (mRoomListener != null) {
//				ForceExitParser forceExitParser = new ForceExitParser(jo);
//				forceExitParser.parse();
//				mRoomListener.onForceExit(forceExitParser.getTag(), forceExitParser.getTitle(),
//						forceExitParser.getMsg(), forceExitParser.getPositiveStr(),
//						forceExitParser.getPositiveUrl(), forceExitParser.getCancelStr(),
//						forceExitParser.getCancelUrl());
//				forceExitParser.release();
//			}
			break;
		case MessageType.ON_ROOM_TIPS_DIALOG:
//
//			RoomTipsParser roomTipsParser = new RoomTipsParser(jo);
//			roomTipsParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onRoomTipsDialog(roomTipsParser.getTitle(), roomTipsParser.getMsg(),
//						roomTipsParser.getPositiveStr(), roomTipsParser.getPositiveUrl());
//			roomTipsParser.release();
			break;
		case MessageType.ROOM_REMIN_MSG:
//			MessageParser reminParser = new MessageParser(jo);
//			reminParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onRoomInspectorRemind(reminParser);
			break;
		case MessageType.ROOM_WARNING_MSG:
//			MessageParser warningParser = new MessageParser(jo);
//			warningParser.parse();
//			if (mRoomListener != null)
//				mRoomListener.onRoomInspectorWarning(warningParser);
			break;

		case MessageType.ON_GIFT_BELONG_COMMON_VIP:
		case MessageType.ON_GIFT_BELONG_ELSE:
		case MessageType.ON_GIFT_BELONG_LV11:
		case MessageType.ON_GIFT_BELONG_PRETTY4:
		case MessageType.ON_GIFT_BELONG_PRETTY5:
		case MessageType.ON_GIFT_BELONG_QQ:
		case MessageType.ON_GIFT_BELONG_SUPER_VIP:
//			if (mRoomListener != null)
//				mRoomListener.onGiftSendLitmited(msgTag);
			break;
		case MessageType.ON_ROOM_WELCOME_MSG:
//
//			String welMsg = null;
//			final String keyWel = "a";
//			if (jo.containsKey(keyWel))
//				try {
//					welMsg = jo.getString(keyWel);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			if (mRoomListener != null && welMsg != null)
//				mRoomListener.onWelcomeMsg(welMsg);
			break;
		case MessageType.TOAST_MSG:
//			String msg = null;
//			final String key_content = "content";
//		//	Log.d(TAG, "TOAST_MSG = " + jo.toString());
//			if (jo.containsKey(key_content))
//				try {
//					msg = jo.getString(key_content);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			if (msg != null && mRoomListener != null)
//				mRoomListener.onToastMessage(msg);
			break;
		case MessageType.DIALOG_MSG:
//			String dmsg = null;
//			final String key_content2 = "content";
//			if (jo.containsKey(key_content2))
//				try {
//					dmsg = jo.getString(key_content2);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			if (dmsg != null && mRoomListener != null)
//				mRoomListener.onDialogMessage(dmsg);
			break;
		case MessageType.ON_ROOM_CONFIG:
//			int privateMsgCount = 0;
//			final String key_pmc = "pmc";
//			if (jo.containsKey(key_pmc))
//				try {
//					privateMsgCount = jo.getInt(key_pmc);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			if (privateMsgCount != 0 && mRoomListener != null)
//				mRoomListener.onRoomConfig(privateMsgCount);
			break;
		case MessageType.ON_ROOM_PUBLIC_HISTORY_MSG:
//			PublicHistoryMessageParser phmp = new PublicHistoryMessageParser(mContext, jo, false);
//			phmp.parse();
//			RoomDataHelper.getInstance().getChatHelper().addMessage(phmp.getChatList());
//			if (mRoomListener != null)
//				mRoomListener.onPublicHistoryMessage(phmp.getChatList());
			break;
		case MessageType.ON_ROOM_PRIVATE_HISTORY_MSG:
//			PublicHistoryMessageParser prp = new PublicHistoryMessageParser(mContext, jo, true);
//			prp.parse();
//			if (mRoomListener != null)
//				mRoomListener.onPrivateHistoryMessage(prp.getChatList(), prp.isUnreadPrivateMessage());
			break;
		// ========================================Error======================================
		case MessageType.ROOM_MEM_FULL:
		case MessageType.ROOM_LOGINED_ELSE:
		case MessageType.FRIEND_HAS_LEAVE:
		case MessageType.NO_ROOM_ENTERED:
		case MessageType.LOGIN_ROOM_DIFF:
		case MessageType.ROOM_NOT_EXISTS:
		case MessageType.ROOM_LOGINED_ALREADY:
		case MessageType.GET_TOKEN_FAILED:
		case MessageType.CHECK_TOKEN_FAILED:
		case MessageType.MSG_TYPE_INVALID:
		case MessageType.MSG_CHAT_TYPE_INVALID:
		case MessageType.MONEY_NOT_ENOUGH:
		case MessageType.ROOM_GRAB_PARK_NOT_ENOUGH_MONEY_MSG:
		case MessageType.GIFT_NOT_EXISTS:
		case MessageType.SEND_FROM_TO_SAME:
		case MessageType.ROOM_ENTER_FORBIDDEN:
//			if (mRoomListener != null)
//				mRoomListener.onError(msgTag, -1);
			break;
		case MessageType.SYSTEM_MSG:
//			SystemMsgParser parser = new SystemMsgParser(jo);
//			parser.parse();
//			if (mRoomListener != null && !TextUtils.isEmpty(parser.getContent()))
//				mRoomListener.onSystemMsg(parser.getContent());
			break;
		case MessageType.KICK_SOMEBODY:
		case MessageType.SHUT_UP:
//			LimitMsgParser kp = new LimitMsgParser(jo);
//			kp.parse();
//			if (mRoomListener != null) {
//				if (msgTag == MessageType.KICK_SOMEBODY)
//					mRoomListener.onMemberKickedOut(kp.getFrom(), kp.getTo(), kp.getContent(), kp.getInterval());
//				else
//					mRoomListener.onMemberShutUp(kp.getFrom(), kp.getTo(), kp.getContent());
//			}
			break;
		case MessageType.ROOM_SONG_ADD:
		case MessageType.ROOM_SONGS_LIST:
		case MessageType.ROOM_SONG_DELETE:
//			SongsMsgParser sp = new SongsMsgParser(jo);
//			sp.parse();
//			if (mRoomListener != null)
//				mRoomListener.onGetSongList(sp);
			break;
		case MessageType.ROOM_SONG_NO_EXIST:
//			if (mRoomListener != null)
//				mRoomListener.onSongNoExist();
			break;
		case MessageType.USER_LEVEl_INFO:
//			UserLevelParser LevelParser = new UserLevelParser(jo);
//			LevelParser.parse();
//            if (mRoomListener != null)
//                mRoomListener.onUserLevelInfo();
			break;
		case MessageType.ROOM_SET_PASSWORD://登录频道时需要密码
//			if (mRoomListener != null)
//                mRoomListener.onRoomNeedPassword();
			break;
        case MessageType.ROOM_GET_ROOM_ADMIN_LIST:
//            RoomAdminListParser adminParser = new RoomAdminListParser(jo);
//            adminParser.parse();
//            if (mRoomListener != null)
//                mRoomListener.onGetRoomAdmin(adminParser);
            break;
        case MessageType.ROOM_SET_ROOM_ADMIN:
        case MessageType.ROOM_CANCEL_ROOM_ADMIN:
//            if (mRoomListener != null)
//                mRoomListener.onOpRoomAdmin();
            break;
        case MessageType.ONLIVENEW_LIVE_VIDEO_STATE:
        case MessageType.ONLIVENEW_CANCEL_USER:
        case MessageType.ONLIVENEW_REMOVE_USER:
//            RoomOnLiveControlParser rpOL = new RoomOnLiveControlParser(jo);
//            rpOL.parse();
////
////            if (ChatRoom.instance != null && ((ChatRoom) ChatRoom.instance).getPubProcesser() != null) {
////                if(rpOL.getUserId() > 0)
////                    ((ChatRoom) ChatRoom.instance).getPubProcesser().addRoomOnLineMessage(rpOL.getNickName(), rpOL.getUserId(), msgTag == MessageType.ONLIVENEW_LIVE_VIDEO_STATE);
////            }
//            if (mRoomListener != null){
//            	 if(rpOL.getUserId() > 0){
//            		 mRoomListener.onRoomLiveChanged(rpOL,msgTag);
//            	 }
//            	
//                mRoomListener.isMsgHandled(msgTag, jo);
//            }
            break;
        case MessageType.ROOM_MODE://设置主播直播模式
//        	if(RoomDataHelper.getInstance().isInit()){
//        		String key_a = "a";
//        		int serverMode = RoomDataHelper.SERVER_MODE_OFFLINE;
//				// int mode = -1;
//				if (jo.containsKey(key_a)) {
//					try {
//						serverMode = jo.getIntValue(key_a);
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//				RoomDataHelper.getInstance().setServerMode(serverMode);
//        	}
//        	 if (mRoomListener != null)
//                 mRoomListener.isMsgHandled(msgTag, jo);
        	break;
        case MessageType.ROOM_CHAT_SEND_FAILED:
//        	RoomDataHelper.getInstance().getChatHelper().sendRecordFailed();
        	break;
		default:
//			if (mRoomListener != null && mRoomListener.isMsgHandled(msgTag, jo))
				break;
	//		else
	//			Log.e(TAG, "uncache msgtag->" + msgTag);
		}
	}

	/** 发送消息到JS游戏 */
//	private boolean sendGameSocketMsg(JSONObject jsonObject) {
//		boolean isSend = false;
//		final String MODETYPE = "modType";
//		int gameMsgId = -1;
//		int gameType = -1;
//		if (ChatRoom.instance != null) {
//			gameMsgId = ((ChatRoom) ChatRoom.instance).getGameId();
//		}
//		if (jsonObject.containsKey(MODETYPE)) {
//			try {
//				gameType = jsonObject.getIntValue(MODETYPE);
//			} catch (JSONException e) {
//				e.printStackTrace();
//				return isSend;
//			}
//		}
//
//		if (gameType == gameMsgId && gameMsgId > 0) {
//			MessageDump.getInstance().dispatch(
//					new AppMessage(TaskType.SERVER_RETURN_GAME_MSG, 0, 0, jsonObject.toString(),
//							null, null));
//			isSend = true;
//		}
//		return isSend;
//	}
	
	public void setSocketState(CONNECT_STATE socketState) {
		this.socketState = socketState;
	}

}
