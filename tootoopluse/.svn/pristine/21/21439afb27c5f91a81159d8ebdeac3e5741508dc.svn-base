package com.ninetowns.tootoopluse.util;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * 
 */
public class ReceiverManager {
	private LocalBroadcastManager manager;
	private static ReceiverManager receiverManager;

	/**
	 * 实例化
	 * 
	 * @return
	 */
	public static ReceiverManager getIntance() {
		if (receiverManager == null) {
			receiverManager = new ReceiverManager();
		}
		return receiverManager;
	}

	/**
	 * 注册广播
	 * 
	 * @param context
	 * @param filters
	 * @param receiver
	 */
	public void registerReceiver(Context context, BroadcastReceiver receiver,
			String... filters) {
		if (filters == null || filters.length == 0) {
			return;
		}
		manager = LocalBroadcastManager.getInstance(context);
		IntentFilter filter = new IntentFilter();
		for (String s : filters) {
			filter.addAction(s);
		}
		manager.registerReceiver(receiver, filter);
	}

	/**
	 * 取消注册
	 * 
	 * @param receiver
	 */
	public void unRegisterReceiver(BroadcastReceiver receiver) {
		manager.unregisterReceiver(receiver);
	}

	/**
	 * 发送广播
	 * 
	 * @param context
	 * @param filter
	 */
	public void sendBroadCastReceiver(Context context, Bundle bundle,String... filter) {
		if (filter == null || filter.length == 0) {
			return;
		}
		manager = LocalBroadcastManager.getInstance(context);
		for (String s : filter) {
			Intent intent =new Intent(s);
			if(null!=bundle){
				intent.putExtras(bundle);
			}
			manager.sendBroadcast(intent);
		}
	}
}
