package com.socket.util;


public abstract class  MessageThread extends Thread {
	
	protected MessageThread()
	{
		
	}
	protected void destroyThread()
	{
		
		
	}
	
	/**called by onNewIntent */
	protected void release()
	{
		
	}
	
	/**add msg into queueu and send it or parse it*/
	protected void addTask(String msg)
	{
		doTask(msg);
	}
	
	/**子类必须重写，来处理添加进来的消息*/
	protected abstract void doTask(String msg);
}
