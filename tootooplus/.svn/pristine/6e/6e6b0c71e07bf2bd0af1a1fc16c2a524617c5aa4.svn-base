package com.ninetowns.tootooplus.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.AppVersionBean;
import com.ninetowns.tootooplus.fragment.VersionDownDialogFragment;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.AppVersionParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.INetConstanst;
import com.ninetowns.ui.Activity.BaseActivity;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.OnDismissDialogInterface;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.TooDialogCallBack;

public class StartActivity extends BaseActivity implements INetConstanst,OnDismissDialogInterface {
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};
	private final int DELAY_TIME_MILLIS = 1000;
	
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	boolean isFirstLoc = true;// 是否首次定位

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.start);
		getRtmpHttp();
		
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		// 设置gps优先
		option.setPriority(LocationClientOption.GpsFirst);

		option.setPoiNumber(10);
		option.disableCache(true);
		mLocClient.setLocOption(option);
		
		mLocClient.start();

	}

	private void getRtmpHttp() {
		if (NetworkUtil.isNetworkAvaliable(StartActivity.this)) {
			RequestParamsNet params = new RequestParamsNet();
			params.addQueryStringParameter(
					TootooeNetApiUrlHelper.CHAT_RTMP_HTTP_SERVER_TYPE,
					TootooeNetApiUrlHelper.CHAT_RTMP_SERVER_TYPE_CONSTANTS
							+ ","
							+ TootooeNetApiUrlHelper.HTTP_SERVER_TYPE_CONSTANTS); 

			CommonUtil.startGetSend(TootooeNetApiUrlHelper.CHAT_RTMP_HTTP_URL,
					params, new RequestCallBack<String>() {

						private String port;
						private String serverType;

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {

							String jsonStr = responseInfo.result;
							try {
								JSONObject jsonObj = new JSONObject(jsonStr);
								String rtmp_http_status = "";
								String req_rtmp_url = "";
								String req_http_url = "";

								if (jsonObj.has("Status")) {
									rtmp_http_status = jsonObj
											.getString("Status");
								}

								if (rtmp_http_status.equals("1")) {
									if (jsonObj.has("Data")) {
										JSONArray jsonData = jsonObj
												.getJSONArray("Data");
										for (int i = 0; i < jsonData.length(); i++) {
											JSONObject jsonItem = jsonData
													.getJSONObject(i);
											if (jsonItem.has("ServerType")) {
												serverType = jsonItem
														.getString("ServerType");
												if (serverType.equals("8")) {//当是8的是外网
													req_http_url = jsonItem
															.getString("RequestHttpPath");
												} else {
													req_rtmp_url = jsonItem
															.getString("RequestRtmpPath");
												}
											}
										}

										SharedPreferenceHelper.saveRtmpHttpUrl(
												StartActivity.this,
												req_rtmp_url, req_http_url);


										
										// 检测版本
										checkVersion();
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							ComponentUtil
									.showToast(
											StartActivity.this,
											getResources()
													.getString(
															R.string.errcode_network_response_timeout));
						}
					});
		} else {

		}

	}

	/**
	 * @Fields service : 聊天服务
	 */
//	private ChatService service;
	private Runnable runable = new Runnable() {
		@Override
		public void run() {
			Intent intent = null;

			if (!StringUtils.isEmpty(SharedPreferenceHelper.getLoginUserId(StartActivity.this))) {
				enterHomeActivity();
//				bindChatService();
			} else {
				intent = new Intent(StartActivity.this, FirstGuideActivity.class);
				startActivity(intent);
				finish();
			}

			
		}

	};

	/**
	 * 绑定服务
	 */
//	private void bindChatService() {
//		Intent intent = new Intent(this, ChatService.class);
//		startService(intent);
//		bindService(intent, connection, Context.BIND_AUTO_CREATE);
//	}
//
//	private ServiceConnection connection = new ServiceConnection() {
//
//		public void onServiceDisconnected(ComponentName name) {
//			// service.disconnect();
//			service = null;
//		}
//
//		public void onServiceConnected(ComponentName name, IBinder binder) {
//			service = ((ChatService.LocalBinder) binder).getService();
//			boolean isConnect = false;
//			// 在jni中做了判断
//			//rtmp://182.92.104.81:1938/chatroom/   297
//			
////			if(!service.isConnectRtmp()){
//				isConnect = service.connectRtmp(SharedPreferenceHelper.getReqRtmpUrl(StartActivity.this),
//						SharedPreferenceHelper.getLoginUserId(StartActivity.this));
//				LogUtils.i("isConnect===" + isConnect);	
//			
//			if (isConnect) {
//				unBindChatService();
//				enterHomeActivity();
//				
//			} else {
//				UIUtils.showCenterToast(StartActivity.this, "聊天室长连接失败");
//			}
//
//		}
//
//	};
	
	private void enterHomeActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * @Title: unBindChatService
	 * @Description: 解除绑定服务
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
//	private void unBindChatService() {
//		if (null != connection) {
//			unbindService(connection);
//			connection = null;
//		}
//	}
	private boolean isSure;
	
	// 检测版本
	// 1为强制升级,0为不强制升级
	private void checkVersion() {
		RequestParamsNet requestParamsNet = new RequestParamsNet();
    	requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.VERSION_UP_GRADE_TYPE, "android");
    	requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.VERSION_UP_GRADE_VERSION, CommonUtil.getVersionName(this));
    	CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.VERSION_UP_GRADE_URL,
				requestParamsNet, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						AppVersionParser versionResponse = new AppVersionParser();
						if(TextUtils.isEmpty(responseInfo.result)){
							handler.postDelayed(runable, DELAY_TIME_MILLIS);
							return;
						}
						String resultStr = new String(responseInfo.result);
						try{
							final AppVersionBean versionBean = versionResponse.parserVersion(resultStr);
							
							if(versionBean.getVersion_lineTypes() != null && versionBean.getVersion_lineTypes().size() > 0){
								SharedPreferenceHelper.saveLineType(StartActivity.this, versionBean.getVersion_lineTypes());
							}
							String upgrate=versionBean.getVersion_upgradeType();
							if(!TextUtils.isEmpty(upgrate)){
								if(upgrate.equals("1")){

									TooSureCancelDialog tooDialog = new TooSureCancelDialog(
											StartActivity.this);
									tooDialog.setOnDismissDialogInterFace(StartActivity.this);
									tooDialog.setDialogTitle(R.string.rainbo_hint);
									tooDialog.setDialogContent(versionBean.getVersion_msg());
									tooDialog.setCancelButtonVisible(View.GONE);
									tooDialog.setTooDialogCallBack(new TooDialogCallBack() {
												

												@Override
												public void onTooDialogSure() {
													// 下载app
													if (!TextUtils.isEmpty(versionBean.getVersion_downloadUrl())) {
														isSure=true;
														downloadApp(versionBean
																.getVersion_downloadUrl());
													}
												}

												@Override
												public void onTooDialogCancel() {

													if (versionBean.getVersion_upgradeType().equals("0")) {
														handler.postDelayed(runable, DELAY_TIME_MILLIS);
													} else {
														finish();
													}
												}
											});

									tooDialog.show();

								
								}else{

									TooSureCancelDialog tooDialog = new TooSureCancelDialog(
											StartActivity.this);
									tooDialog.setDialogTitle(R.string.rainbo_hint);
									tooDialog.setDialogContent(versionBean.getVersion_msg());
									tooDialog.setTooDialogCallBack(new TooDialogCallBack() {
												@Override
												public void onTooDialogSure() {
													// 下载app
													if (!TextUtils.isEmpty(versionBean.getVersion_downloadUrl())) {

														downloadApp(versionBean
																.getVersion_downloadUrl());
													}
												}

												@Override
												public void onTooDialogCancel() {

														handler.postDelayed(runable, DELAY_TIME_MILLIS);
												}
											});

								
									tooDialog.show();
								
								
								
								
								}
								
							}else{
								handler.postDelayed(runable, DELAY_TIME_MILLIS);
							}
							
							
							
						}catch(JSONException e){
							e.printStackTrace();
						}
						
					} 

					@Override
					public void onFailure(HttpException error, String msg) {
						ComponentUtil
						.showToast(
								StartActivity.this,
								getResources()
										.getString(
												R.string.errcode_network_response_timeout));
						
					}
				});

	}
	
	private void downloadApp(String download_url) {
		// 启动DialogFragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		VersionDownDialogFragment versionFragment = new VersionDownDialogFragment(
				download_url, "start");
		if (fragmentManager != null) {
			// 屏幕较小，以全屏形式显示
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			// 指定一个过渡动画
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			versionFragment.show(fragmentManager, "dialog");
		}

	}
	
	
	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if(mLocClient != null){
			mLocClient.stop();
		}
		super.onDestroy();
		isSure=false;
	}
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			if (isFirstLoc) {
				//如果拒绝打开GPS, 定位结果为4.9E-324
				isFirstLoc = false;
				SharedPreferenceHelper.saveUserLocation(StartActivity.this, location.getCity(), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
			
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	public void OnButtonDismiss() {
		if(isSure){
			
		}else{
			finish();
		}
	
		
		
	}
}
