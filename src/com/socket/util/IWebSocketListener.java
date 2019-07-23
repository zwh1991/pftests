package com.socket.util;



/**
 * 从底层socket反馈到socketManager 的接口
 * */
public interface IWebSocketListener {
	public  void onMessage(String message);
	public void onOpen();
	public void onClose(int code, String reason, boolean remote);
	/**system error form webSocket*/
	public void onError(Exception ex);
}
