package com.socket.util;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
//import com.melot.meshow.sns.socketparser.*;
//import com.melot.meshow.room.RoomPark;
//import com.melot.meshow.room.RoomSeat;
//import com.melot.meshow.room.chat.IChatMessage;
//import com.melot.meshow.room.chat.RoomMember;
//import com.melot.meshow.room.gift.MarqueeItem;
//import com.melot.meshow.room.gift.StockGift;
//import com.melot.meshow.shop.Car;
//import com.melot.meshow.struct.LuckyStar;
//import com.melot.meshow.struct.UserProfile;

public interface RoomMessageListener {
//	/** 拿到了频道URL并且已经连接ok,待发消息状态 */
//	public void onConnected();
//
//	public void onError(int type, int functionId);
//
//	/** system error form webSocket */
//	public void onError(Exception ex);
//
//	/** 获取频道信息 */
//	public void onLoginRoom(UserProfile mySelf, long money);
//
//	/** 频道公告消息 */
//	public void onRoomNotice(String notice, String noticeLink);
//
//	/** 发送消息 */
//	public void onSendTxt(MessageParser parser);
//
//	/** 送礼 */
//	public void onSendGift(RoomSendGiftParser parser);
//
//	/** 获取成员列表 */
//	public void onGetRoomMember(RoomMemListParser parser);
//
//    /** 获取助理列表 */
//    public void onGetRoomAdmin(RoomAdminListParser parser);
//
//    /** 操作助理 */
//    public void onOpRoomAdmin();
//
//	/** 获取点歌单 */
//	public void onGetSongList(SongsMsgParser parser);
//
//	public void onSystemMsg(String msg);
//
//	/** 踢人 */
//	public void onMemberKickedOut(RoomMember from, RoomMember to, String content, int interval);
//
//	/** 禁言 */
//	public void onMemberShutUp(RoomMember from, RoomMember to, String content);
//
//	public void onGiftWin(GiftWinParser parser);
//
//	public void onGetRankData(RoomRankParser parser);
//
//	public void onChoiceSongBack(long price);
//
//	/** 跑道消息 */
//	public void onFlyWayMessage(ArrayList<MarqueeItem> items);
//
//	/** 停车消息 */
//	public void onParkListMessage(ArrayList<RoomPark> parkList);
//
//	/** 沙发消息 */
//	public void onSeatListMessage(ArrayList<RoomSeat> seatList);
//
//	/** 频道信息 */
//	public void onRoomInfo(RoomOwnerParser parser);
//
//	/** 余额 */
//	public void onMoneyUpdate(long money);
//
//	/** 频道强制退出 */
//	public void onForceExit(int tag, String title, String msg, String positiveStr, String positiveUrl,
//			String cancelStr, String cancelUrl);
//
//	/** 频道系统信息提示框 */
//	public void onRoomTipsDialog(String title, String msg, String positiveStr, String positiveUrl);
//
//	/** 限制礼物不能送出 */
//	public void onGiftSendLitmited(int tag);
//
//	public void onWelcomeMsg(String msg);
//
//	public boolean isMsgHandled(int tag, JSONObject jo);
//
//	// 成员进出
//	public void onUserIn(RoomMember user, boolean isGiftStar, Car car, int idx, int memTotalCount, int guestCount);
//
//	public void onUserOut(RoomMember user, int memTotalCount, int guestCount);
//
//	public void onGuestIn(int memTotalCount, int guestCount);
//
//	public void onGuestOut(int memTotalCount, int guestCount);
//
//    public void onUserLevelInfo();
//
//    /** 所点歌曲不存在 */
//	public void onSongNoExist();
//
//	/** 频道配置 */
//	public void onRoomConfig(int privateMsgCount);
//
//	/** 公聊历史消息 */
//	public void onPublicHistoryMessage(ArrayList<IChatMessage> list);
//
//	/** 私聊历史消息 */
//	public void onPrivateHistoryMessage(ArrayList<IChatMessage> list,boolean isUnread);
//
//	/** 礼物列表信息ok */
//	public void onGiftInfo();
//	
//	/** 库存礼物列表信息 */
//	public void onStockGiftInfo(boolean bSendGift);
//	
//	/** 库存礼物数量不足 */
//	public void onStockGiftInSufficient(StockGift stockGift);
//
//	public void onDialogMessage(String msg);
//
//	public void onToastMessage(String msg);
//	
//	/** 巡官提醒 */
//	public void onRoomInspectorRemind(MessageParser parser);
//	
//	/** 巡官警告 */
//	public void onRoomInspectorWarning(MessageParser parser);
//	
//	/** 登录频道时需要密码 */
//	public void onRoomNeedPassword();
//	
//	/** 连麦状态变更(公聊) */
//	public void onRoomLiveChanged(RoomOnLiveControlParser parser,int msgTag);
//	
//	/** 获取音频消息(和文本聊天共用一个协议) */
//	public void onRoomRecordText(MessageParser parser);
}
