package com.ninetowns.tootoopluse.activity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mechat.mechatlibrary.MCClient;
import com.mechat.mechatlibrary.MCOnlineConfig;
import com.mechat.mechatlibrary.MCUserConfig;
import com.ninetowns.library.helper.AppManager;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.AppVersionBean;
import com.ninetowns.tootoopluse.fragment.VersionDownDialogFragment;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.parser.AppVersionParser;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.tootoopluse.util.FileUtils;
import com.ninetowns.tootoopluse.util.UIUtils;
import com.ninetowns.ui.Activity.BaseActivity;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.TooDialogCallBack;

/**
 * @ClassName: SettingActivity
 * @Description: 设置页面
 * @author zhou
 * @date 2015-2-5 下午2:18:36
 * 
 */
@ContentView(R.layout.setting_activity)
public class SettingActivity extends BaseActivity {

	/**
	 * @Fields mBackButton : 返回
	 */
	@ViewInject(R.id.setting_iv_back)
	private ImageView mBackButton;

	@ViewInject(R.id.setting_listview)
	private ListView mSettingListView;
	@ViewInject(R.id.set_log_off_btn)
	private TextView set_log_off_btn;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		SettingAdapter adpter = new SettingAdapter();
		mSettingListView.setAdapter(adpter);

		mSettingListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = null;
				switch (position) {
				case 0:
					break;
				case 1:// update
					checkVersion();
					break;
				case 2:// feedback
					intent = new Intent(SettingActivity.this,
							FeedBackActivity.class);
					startActivity(intent);
					break;
				case 3://
					
					skipToServicer();//客服
					break;
				case 4:// 
					clearCache();
					break;
				case 5://
					intent = new Intent(SettingActivity.this,
							HelpActivity.class);
					startActivity(intent);
					break;
				case 6://
					intent = new Intent(SettingActivity.this,
							AboutUsActivity.class);
					startActivity(intent);
					break;
				}
			}
		});
		// 退出账号
		set_log_off_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TooSureCancelDialog tooDialog = new TooSureCancelDialog(
						SettingActivity.this);
				tooDialog.setDialogTitle(R.string.rainbo_hint);
				tooDialog
						.setDialogContent(R.string.settings_exist_dialog_content);
				tooDialog.setTooDialogCallBack(new TooDialogCallBack() {

					@Override
					public void onTooDialogSure() {
						// 退出登录
						CommonUtil.exitApp(getApplicationContext());
					}

					@Override
					public void onTooDialogCancel() {

					}
				});
				tooDialog.show();
			}
		});
	}
	/** 
	* @Title: skipToServicer 
	* @Description: 客服 
	* @param  
	* @return   
	* @throws 
	*/
	private void skipToServicer() {
		MCOnlineConfig onlineConfig = new MCOnlineConfig();
		//onlineConfig.setChannel("channel"); // 设置渠道

		// onlineConfig.setSpecifyAgent("4840", false); // 设置指定客服
		// onlineConfig.setSpecifyGroup("1"); // 设置指定分组

		// 更新用户信息，可选. 
		// 详细信息可以到文档中查看：https://meiqia.com/docs/sdk/android.html
		MCUserConfig mcUserConfig = new MCUserConfig();
		Map<String,String> userInfo = new HashMap<String,String>();
		userInfo.put(MCUserConfig.PersonalInfo.REAL_NAME,SharedPreferenceHelper.getLoginUserId(this) );
		userInfo.put(MCUserConfig.Contact.TEL,"130000000");
		Map<String,String> userInfoExtra = new HashMap<String,String>();
		userInfoExtra.put("extra_key",SharedPreferenceHelper.getLoginUserId(this));
		mcUserConfig.setUserInfo(this,userInfo,userInfoExtra,null);
		// 启动客服对话界面
		MCClient.getInstance().startMCConversationActivity(onlineConfig);
	}
	
	/** 
	* @Title: clearCache 
	* @Description:清除缓存
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void clearCache() {
		FileUtils.deleteFile(new File(CommonUtil.PHOTO_HOST));
		FileUtils.deleteFile(TootooPlusEApplication.cacheDir);
		UIUtils.showCenterToast(this, "清除缓存完成");
	}
	@OnClick(R.id.setting_iv_back)
	public void onBackClicked(View v) {
		AppManager.getAppManager().finishActivity(this);
	}

	private class SettingAdapter extends BaseAdapter {
/**,R.drawable.icon_kf,客服**/
		private final int[] imgResIds = { R.drawable.icon_vision,
				R.drawable.icon_update, R.drawable.icon_feedback,R.drawable.icon_kf,
				R.drawable.icon_clearcache, R.drawable.icon_help,
				R.drawable.icon_aboutus };

		Resources res = SettingActivity.this.getResources();
		String[] data = res.getStringArray(R.array.setting_list_data);

		// private final String[]DATA={"版本号3.0","检测更新",};
		@Override
		public int getCount() {
			return data.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView itemText = null;
			ImageView itemIcon = null;
			ImageView itemRightArrow = null;
			convertView = View.inflate(SettingActivity.this,
					R.layout.setting_item, null);
			itemText = (TextView) convertView
					.findViewById(R.id.settingitem_tv_name);
			itemIcon = (ImageView) convertView
					.findViewById(R.id.settingitem_icon);
			itemRightArrow = (ImageView) convertView
					.findViewById(R.id.settingitem_icon_rightarrow);
			if(position==0){
				itemText.setText(data[position]+CommonUtil.getVersionName(SettingActivity.this));
				UIUtils.setViewGone(itemRightArrow);
			}else{
				itemText.setText(data[position]);
				UIUtils.setViewVisible(itemRightArrow);
			}
			itemIcon.setImageResource(imgResIds[position]);
			return convertView;
		}

	}

	// 检测版本
	// 1为强制升级,0为不强制升级
	private void checkVersion() {
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter(
				//androidBusiness 商户版
				TootooeNetApiUrlHelper.VERSION_UP_GRADE_TYPE, "androidBusiness");
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.VERSION_UP_GRADE_VERSION,
				CommonUtil.getVersionName(this));
		CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.VERSION_UP_GRADE_URL,
				requestParamsNet, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						AppVersionParser versionResponse = new AppVersionParser();
						String resultStr = new String(responseInfo.result);
						try {
							final AppVersionBean versionBean = versionResponse
									.parserVersion(resultStr);

							if (versionBean.getVersion_status().equals("1")) {
								final TooSureCancelDialog tooDialog = new TooSureCancelDialog(
										SettingActivity.this);
								tooDialog.setDialogTitle(R.string.rainbo_hint);
								tooDialog.setDialogContent(versionBean
										.getVersion_msg());
								tooDialog
										.setTooDialogCallBack(new TooDialogCallBack() {
											@Override
											public void onTooDialogSure() {
												// 下载app
												if (!TextUtils.isEmpty(versionBean
														.getVersion_downloadUrl())) {

													downloadApp(versionBean
															.getVersion_downloadUrl());
												}
											}

											@Override
											public void onTooDialogCancel() {

												tooDialog.cancel();
											}
										});

								tooDialog.show();

							}else{
								UIUtils.showCenterToast(SettingActivity.this, "已是最新版本");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						ComponentUtil
								.showToast(
										SettingActivity.this,
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

}
