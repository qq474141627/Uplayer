package com.opar.mobile.uplayer.util;

import android.os.Handler;
import android.os.Message;

public class HandlerUtil {
	/**
	 * 发送 消息到主界面
	 * 
	 * @param handler
	 * @param type
	 */
	public static void sendMsgToHandler(Handler handler, int type) {
		if (handler != null) {
			Message message = handler.obtainMessage();
			message.what = type;
			handler.sendMessage(message);
		} else {
			System.out.println("handler--->等于null");
		}
	}
	/**
	 * 发送 消息到主界面
	 * 
	 * @param handler
	 * @param type
	 * @param object
	 */
	public static void sendMsgToHandler(Handler handler, int type, Object object) {
		if (handler != null) {
			Message message = handler.obtainMessage();
			message.what = type;
			message.obj = object;
			handler.sendMessage(message);
		} else {
			System.out.println("handler--->等于null");
		}
	}
	
	/**
	 * 发送 消息到主界面
	 * 
	 * @param handler
	 * @param type
	 * @param object
	 */
	public static void sendMsgToHandler(Handler handler, int type,int arg1, Object object) {
		if (handler != null) {
			Message message = handler.obtainMessage();
			message.what = type;
			message.arg1 = arg1;
			message.obj = object;
			handler.sendMessage(message);
		} else {
			System.out.println("handler--->等于null");
		}
	}
	
	/**
	 * 延迟发送 消息到主界面
	 * 
	 * @param handler
	 * @param type
	 */
	public static void sendMsgToHandlerDelay(Handler handler, int type,
			Object object,int time) {
		if (handler != null) {
			// handler.sendEmptyMessage(type);
			Message message = handler.obtainMessage();
			message.what = type;
			message.obj = object;
			handler.sendMessageDelayed(message, time);
		} else {
			System.out.println("handler--->等于null");
		}
	}
	
}
