package com.ninetowns.tootooplus.activity;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.fragment.HomeCommentFragment;
import com.ninetowns.tootooplus.fragment.HomePageFragment;
import com.ninetowns.tootooplus.fragment.MineFragment;
import com.ninetowns.tootooplus.fragment.ParentGroupChatFragment;
import com.ninetowns.tootooplus.fragment.WishFragment;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.INetConstanst;
import com.ninetowns.tootooplus.util.ReceiverManager;
import com.ninetowns.tootooplus.util.UIUtils;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
import com.umeng.analytics.AnalyticsConfig;
import com.wiriamubin.service.chat.ChatService;
import com.wiriamubin.service.chat.ChatService.OnChatMessageLisenter;

public class HomeActivity extends FragmentGroupActivity implements
		OnCheckedChangeListener, INetConstanst, OnChatMessageLisenter {
	@ViewInject(R.id.rg_tab_bar)
	private RadioGroup mRGroup;// tab组
	@ViewInject(R.id.rb_tab_home_page)
	private RadioButton mRbtnHomePage;// 首页
	@ViewInject(R.id.rb_tab_group_chat)
	private RadioButton mRbtnGroupChat;// 活动群
	@ViewInject(R.id.rb_tab_comment)
	private RadioButton mRbtnComment;
	@ViewInject(R.id.rb_tab_wish)
	private RadioButton mRbtnWish;// 心愿
	@ViewInject(R.id.rb_tab_mine)
	private RadioButton mRbtnMine;// 我的
	private Bundle bundle;
	private int tabIndex = 0;
	@ViewInject(R.id.tv_no_write_count_act)
	public TextView mTVCount;
	public  String message;

	private static final String activityName = "com.ninetowns.tootooplus.activity.ChatActivity";

	/**
	 * @Fields service : 聊天服务
	 */
	private ChatService service;

	private Handler mChatHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MAIN_RECEIVED_MESSAGE:
				ReceiverManager.getIntance().sendBroadCastReceiver(mContext,
						(Bundle) msg.obj, RECEIVE_MESSAGE_ACTION);
				break;
			case MAIN_CONNECT_DISCONNECT:
				UIUtils.showCenterToast(mContext, "链接断开");
				break;
			}
		};
	};

	private class ChatServiceAsynTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			bindChatService();
			return null;
		}

	}

	private HomeActivity mContext;
	private ChatServiceAsynTask mChatServiceAsynTask;
	
	
	public static boolean isForeground = false;
	private MessageReceiver mMessageReceiver;
//	private String stringchannel;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		ViewUtils.inject(this);
		mContext = this;
		// bindChatService();
		mChatServiceAsynTask = new ChatServiceAsynTask();
		mChatServiceAsynTask.execute();

		mRGroup.setOnCheckedChangeListener(this);
		justIndexTab();
		switch (tabIndex) {
		case 0:
			mRbtnHomePage.setChecked(true);
			break;
		case 1:
			mRbtnGroupChat.setChecked(true);
			break;
		case 2:
			mRbtnWish.setChecked(true);
			break;
		case 3:
			mRbtnComment.setChecked(true);

			break;
		case 4:
			mRbtnMine.setChecked(true);
			break;
		default:
			mRbtnWish.setChecked(true);
			break;
		}
		initBroadcast();
		registerMessageReceiver();
	}

	private NetChangeBroadcast netChangeBroadcast;

	private void initBroadcast() {
		netChangeBroadcast = new NetChangeBroadcast();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(NET_CHANGE_ACTION);
		registerReceiver(netChangeBroadcast, intentFilter);
	}
	/**
	 * 
	 * @Title: justIndexTab
	 * @Description: 跳转初始化页
	 * @param
	 * @return
	 * @throws
	 */
	private void justIndexTab() {
		bundle = getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
		if (bundle != null){
			tabIndex = bundle.getInt("tab_index");
		}
	

	}

	@Override
	protected void initPrimaryFragment() {
		switchTab(R.id.rb_tab_home_page);// 默认第一个
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {

		Class<? extends Fragment> clazz = null;
		switch (fragmentId) {
		case R.id.rb_tab_home_page:// 首页
			clazz = HomePageFragment.class;
			break;
		case R.id.rb_tab_group_chat:// 活动群
			 clazz = ParentGroupChatFragment.class;
			break;
		case R.id.rb_tab_wish:// 心愿
			clazz = WishFragment.class;
			break;
		case R.id.rb_tab_comment:
			clazz = HomeCommentFragment.class;
			break;
		case R.id.rb_tab_mine:// 我的
			clazz = MineFragment.class;
			break;
		default:
			throw new IllegalArgumentException();
		}
		return clazz;

	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return R.id.fragment_stub;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switchTab(checkedId);// 切换tab页
	}

	/**
	 * 
	 * @Title: HomeActivity.java
	 * @Description: 切换tab页
	 * @author wuyulong
	 * @date 2015-1-21 上午9:56:24
	 * @param
	 * @return void
	 */
	private void switchTab(int index) {
		switch (index) {
		case R.id.rb_tab_home_page:// 首页
			break;
		case R.id.rb_tab_group_chat:// 活动群
			break;
		case R.id.rb_tab_wish:// 心愿
			break;
		case R.id.rb_tab_comment:

			break;
		case R.id.rb_tab_mine:// 我的

			break;
		default:
			throw new IllegalArgumentException();
		}
		switchPrimaryFragment(index);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			super.exitApp(this);
			return false;
		}
		return false;
	}

	/**
	 * 绑定服务
	 */
	private void bindChatService() {
		Intent intent = new Intent(this, ChatService.class);
		startService(intent);
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection connection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {
			// service.disconnect();
			service = null;
		}

		public void onServiceConnected(ComponentName name, IBinder binder) {
			service = ((ChatService.LocalBinder) binder).getService();
			service.setMessageListener(mContext);
			boolean isConnect = false;
			// 在jni中做了判断
			if (service.isConnectRtmp()) {
				return;
			}
			service.connectRtmp(SharedPreferenceHelper.getReqRtmpUrl(mContext),
					SharedPreferenceHelper.getLoginUserId(mContext));

			LogUtils.i("connect..............");
			LogUtils.i("isConnect===" + isConnect + "userid===="
					+ SharedPreferenceHelper.getLoginUserId(mContext));
			LogUtils.i("url==="
					+ SharedPreferenceHelper.getReqRtmpUrl(mContext));

			if (service.isConnectRtmp()) {
				unBindChatService();
			} else {
				while (!service.isConnectRtmp()) {
					service.connectRtmp(
							SharedPreferenceHelper.getReqRtmpUrl(mContext),
							SharedPreferenceHelper.getLoginUserId(mContext));
					isConnect = service.isConnectRtmp();
					LogUtils.i("connect..............");
					LogUtils.i("isConnect===" + isConnect + "userid===="
							+ SharedPreferenceHelper.getLoginUserId(mContext));
					LogUtils.i("url==="
							+ SharedPreferenceHelper.getReqRtmpUrl(mContext));
				}
				// UIUtils.showCenterToast(mContext, "聊天室长连接失败");
			}

		}

	};
	private String stringchannel;

	/**
	 * @Title: unBindChatService
	 * @Description: 解除绑定服务
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void unBindChatService() {
		if (null != connection) {
			unbindService(connection);
			connection = null;
		}
	}

	@Override
	public void onReceivedMessage(String data, String groupId) {
		
		JSONObject object;
		try {
			object = new JSONObject(data);
			if (null != object) {
				if(object.has("NoticeType")&&SINGLE_LONGIN_NOTICE.equals(object.getString("NoticeType"))){
					mChatHandler.post(new Runnable() {
						@Override
						public void run() {
							UIUtils.showCenterToast(HomeActivity.this, "您的账号已在别处登录");
						}
					});
					
					mChatHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							CommonUtil.exitApp(TootooPlusApplication.getAppContext());
						}
					}, 1500);
					return ;
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Bundle bundle = new Bundle();
		bundle.putString("data", data);
		bundle.putString("groupid", groupId);
		Message msg = Message.obtain();
		msg.what = MAIN_RECEIVED_MESSAGE;
		msg.obj = bundle;
		// msg.obj = mMyChatData;
		mChatHandler.sendMessage(msg);
	}

	@Override
	public void onDisconnect() {
		LogUtils.i("onDisconnect");

		// mChatHandler.sendEmptyMessage(MAIN_CONNECT_DISCONNECT);
	}

	/**
	 * @ClassName: NetChangeBroadcast
	 * @Description: 网络状态发生改变时的广播
	 * @author zhou
	 * @date 2015-3-30 上午10:03:38
	 * 
	 */
	private class NetChangeBroadcast extends BroadcastReceiver {
		State wifiState = null;
		State mobileState = null;

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (NET_CHANGE_ACTION.equals(intent.getAction())) {
				// 获取手机的连接服务管理器，这里是连接管理器类
				ConnectivityManager cm = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.getState();
				mobileState = cm
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
						.getState();

				if (wifiState != null && mobileState != null
						&& State.CONNECTED != wifiState
						&& State.CONNECTED != mobileState) {// 掉线 了
					UIUtils.showCenterToast(mContext, "链接断开了，请检查网络");
				}
				if (UIUtils.isConnect(mContext)) {
					// connectRtmp();
				}
				// else if ((wifiState != null &&{
				// mobileState != null && State.CONNECTED == wifiState)
				// || (wifiState != null && mobileState != null &&
				// State.CONNECTED == mobileState)) {
				// // connectRtmp();
				// }
			}
		}

		/**
		 * @Title: connectRtmp
		 * @Description: 连接聊天室 服务器
		 * @param 设定文件
		 * @return void 返回类型
		 * @throws
		 */
		private void connectRtmp() {
			ChatService service = ChatService.getInstance();
			// boolean isConnect = false;
			// 在jni中做了判断
			while (false == service.isConnectRtmp()) {
				service.connectRtmp(
						SharedPreferenceHelper.getReqRtmpUrl(mContext),
						SharedPreferenceHelper.getLoginUserId(mContext));
			}
		}

	}
	
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {


		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
               message = intent.getStringExtra(KEY_MESSAGE);
              if(mTVCount!=null){
            	  if(!TextUtils.isEmpty(message)&&!message.equals("0")){
            		  mTVCount.setVisibility(View.VISIBLE);
            		  mTVCount.setText(message);
            	  }else{
            		  mTVCount.setVisibility(View.GONE);
            	  }
            	 
              }
              System.out.println("+++++++++++messge+++++++++++++>" + message);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != netChangeBroadcast) {
			unregisterReceiver(netChangeBroadcast);
			netChangeBroadcast = null;
		}
		
		if(mMessageReceiver != null){
			unregisterReceiver(mMessageReceiver);
		}
	}

	
	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
//		stringchannel=AnalyticsConfig.getChannel(this);
//		Toast.makeText(getApplicationContext(), stringchannel, 0).show();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}

}
