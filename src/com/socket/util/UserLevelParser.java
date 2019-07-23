//package com.socket.util;
//
//import com.melot.meshow.Global;
//import com.melot.meshow.util.Log;
//import org.json.JSONObject;
//
//import com.melot.meshow.Setting;
//import com.melot.meshow.sns.ErrorCode;
//import com.melot.meshow.sns.socketparser.SocketMsgParser;
//import com.melot.meshow.struct.UserProfile;
//
//import java.util.Set;
//
//public class UserLevelParser extends SocketMsgParser{
//	private final String KEY_SELF = "self";
//    private final String KEY_NICKNAME = "nickname";
//	private final String KEY_USERINFO = "userInfo";
//	private final String KEY_ACTORLEVEL = "actorLevel";
//	private final String KEY_CURRENT_ACTORLEVEL_START = "actorMin";
//	private final String KEY_CURRENT_ACTORLEVEL_END = "actorMax";
//	private final String KEY_CURRENT_ACTORLEVEL = "earnTotal";
//	private final String KEY_RICHLEVEL = "richLevel";
//	private final String KEY_CURRENT_RICHLEVEL_START = "richMin";
//	private final String KEY_CURRENT_RICHLEVEL_END = "richMax";
//	private final String KEY_CURRENT_RICHLEVEL = "consumeTotal";
//    private final String KEY_ISMYS = "isMys";
//	public int ret;
//	private UserProfile myprofile = new UserProfile();
//
//	public UserLevelParser(JSONObject jo) {
//		super(jo);
//	}
//
//	@Override
//	public void parse() {
//		ret = ErrorCode.UNKNOWN;
//		try {
//			if(jo.has(KEY_SELF)) {
//				String self = jo.getString(KEY_SELF);
//				JSONObject selfJson = new JSONObject(self);
//				if(selfJson.has(KEY_USERINFO)) {
//					String userInfo = selfJson.getString(KEY_USERINFO);
//					JSONObject userInfoJson = new JSONObject(userInfo);
//					if(userInfoJson.has(KEY_ACTORLEVEL)) {
//						//myprofile.setActorLevel(userInfoJson.getInt(KEY_ACTORLEVEL));
//					}
//					if(userInfoJson.has(KEY_CURRENT_ACTORLEVEL_START)) {
//						int start = userInfoJson.getInt(KEY_CURRENT_ACTORLEVEL_START);
//						//myprofile.setActorLevelStart(start);
//					}
//					if(userInfoJson.has(KEY_CURRENT_ACTORLEVEL_END)) {
//						int end = userInfoJson.getInt(KEY_CURRENT_ACTORLEVEL_END);
//						//myprofile.setActorLevelEnd(end);
//					}
//					if(userInfoJson.has(KEY_CURRENT_ACTORLEVEL)) {
//						int current = userInfoJson.getInt(KEY_CURRENT_ACTORLEVEL);
//						//myprofile.setActorLevelCurrent(current);
//					}
//					if(userInfoJson.has(KEY_RICHLEVEL)) {
//						//myprofile.setRicheLv(userInfoJson.getInt(KEY_RICHLEVEL));
//					}
//					if(userInfoJson.has(KEY_CURRENT_RICHLEVEL_START)) {
//						int start = userInfoJson.getInt(KEY_CURRENT_RICHLEVEL_START);
//						//myprofile.setRicheLvStart(start);
//					}
//					if(userInfoJson.has(KEY_CURRENT_RICHLEVEL_END)) {
//						int end = userInfoJson.getInt(KEY_CURRENT_RICHLEVEL_END);
//						//myprofile.setRicheLvEnd(end);
//					}
//					if(userInfoJson.has(KEY_CURRENT_RICHLEVEL)) {
//						int current = userInfoJson.getInt(KEY_CURRENT_RICHLEVEL);
//						//myprofile.setRicheLvCurrent(current);
//					}
//
//                    String nickName = null;
//                    if(userInfoJson.has(KEY_NICKNAME)){
//                        nickName = userInfoJson.getString(KEY_NICKNAME);
//                    }
//
////                    if(userInfoJson.has(KEY_ISMYS)) {
////                        int isMys = userInfoJson.getInt(KEY_ISMYS);
////                        if(Global.currentRoomId != Setting.getInstance().getUserId())
////                            Setting.getInstance().setStealth(isMys == 1); // 频道消息，1则为神秘人
////                        if(isMys == 1 && nickName != null){
////                            Setting.getInstance().setStealthName(nickName);
////                        }
////                    }else{
////                        if(Global.currentRoomId != Setting.getInstance().getUserId())
////                            Setting.getInstance().setStealth(false);
////                    }
//
////					myprofile.setSex(Setting.getInstance().getSex());
//					//TODO 暂时先从这里存进去吧
//					Setting.getInstance().saveMyProfile(myprofile);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret = ErrorCode.UNKNOWN;
//		}
//		
//	}
//
//	@Override
//	public void release() {
//		jo = null;
//	}
//
//}
