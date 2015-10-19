package com.ninetowns.tootoopluse.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.LoginBean;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.parser.LoginParser;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.tootoopluse.util.INetConstanst;
import com.ninetowns.ui.Activity.BaseActivity;

/**
 * @ClassName: MobileLoginActivity
 * @Description: TODO(描述这个类的作用)
 * @author zhou 登陆成功开启聊天服务建立长连接 连接成功进入主页面
 * @date 2015-2-6 上午11:01:23
 * 
 */
public class MobileLoginActivity extends BaseActivity implements INetConstanst {

	private Activity mContext;
	private EditText mobile_login_num, mobile_login_pwd;

	private LoginParser loginParser;
	/**
	 * @Fields service : 聊天服务
	 */
//	private ChatService service;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.mobile_login);
		mContext = this;
		init();
	}

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
//			while (isConnect==false) {
//				isConnect = service.connectRtmp(
//						SharedPreferenceHelper.getReqRtmpUrl(mContext),
//						SharedPreferenceHelper.getLoginUserId(mContext));
//				LogUtils.i("isConnect===" + isConnect + "userid===="
//						+ SharedPreferenceHelper.getLoginUserId(mContext));
//			}
//			
//			if (isConnect) {
//				unBindChatService();
//				enterHomeActivity();
//			} else {
//				UIUtils.showCenterToast(mContext, "聊天室长连接失败");
//			}
//
//		}
//
//	};

	/**
	 * @Title: enterHomeActivity
	 * @Description:进入homeactivity
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void enterHomeActivity() {
		Intent intent = new Intent(MobileLoginActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 绑定服务
	 */
//	private void bindChatService() {
//		Intent intent = new Intent(this, ChatService.class);
//		startService(intent);
//		bindService(intent, connection, Context.BIND_AUTO_CREATE);
//	}
//
//
//	/**
//	 * @Title: unBindChatService
//	 * @Description: 解除绑定服务
//	 * @param 设定文件
//	 * @return void 返回类型
//	 * @throws
//	 */
//	private void unBindChatService() {
//		if (null != connection) {
//			unbindService(connection);
//			connection = null;
//		}
//	}

	private void init() {
		// 返回按钮
		LinearLayout two_or_one_btn_head_back = (LinearLayout) findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// 标题
		TextView two_or_one_btn_head_title = (TextView) findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.mobile_login_title);
		
		TextView two_or_one_btn_head_second_tv = (TextView)findViewById(R.id.two_or_one_btn_head_second_tv);
		two_or_one_btn_head_second_tv.setVisibility(View.VISIBLE);
		two_or_one_btn_head_second_tv.setText(R.string.login_register);
		//点击注册
		RelativeLayout two_or_one_btn_head_second_layout = (RelativeLayout)findViewById(R.id.two_or_one_btn_head_second_layout);
		two_or_one_btn_head_second_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MobileLoginActivity.this, MobileRegisterActivity.class);
				startActivity(intent);
			}
		});

		mobile_login_num = (EditText) findViewById(R.id.mobile_login_num);
		mobile_login_pwd = (EditText) findViewById(R.id.mobile_login_pwd);

		// 登录
		TextView mobile_login_login_btn = (TextView) findViewById(R.id.mobile_login_login_btn);
		mobile_login_login_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String mobile_num = mobile_login_num.getText().toString()
						.trim();
				String mobile_pwd = mobile_login_pwd.getText().toString()
						.trim();
				if (mobile_num == null || "".equals(mobile_num)) {
					ComponentUtil.showToast(
							MobileLoginActivity.this,
							getResources().getString(
									R.string.mobile_num_no_empty));
				} else if (mobile_pwd == null || "".equals(mobile_pwd)) {
					ComponentUtil.showToast(
							MobileLoginActivity.this,
							getResources().getString(
									R.string.mobile_pwd_no_empty));
				} else if (!CommonUtil.isValidMobiNumber(mobile_num)) {
					ComponentUtil.showToast(
							MobileLoginActivity.this,
							getResources().getString(
									R.string.mobile_num_format_error));
				} else if (!CommonUtil.isValidPwd(mobile_pwd)) {
					ComponentUtil.showToast(
							MobileLoginActivity.this,
							getResources().getString(
									R.string.mobile_login_pwd_hint));
				} else {
					// 显示进度
					showProgressDialog(MobileLoginActivity.this);

					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(
							TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_SOURCE,
							"phone");
					requestParamsNet
							.addQueryStringParameter(
									TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_RELATIONID,
									"");
					requestParamsNet
							.addQueryStringParameter(
									TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_RELATIONNAME,
									"");
					requestParamsNet.addQueryStringParameter(
							TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_LOGOURL,
							"");
					requestParamsNet
							.addQueryStringParameter(
									TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_PHONENUMBER,
									mobile_num);
					requestParamsNet.addQueryStringParameter(
							TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_PASSWORD,
							CommonUtil.md5(mobile_pwd));
					requestParamsNet
							.addQueryStringParameter(
									TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_PHONE_ONLY_CODE,
									SharedPreferenceHelper
											.phoneUniqueId(MobileLoginActivity.this));
					// "1"为商家
					requestParamsNet
							.addQueryStringParameter(
									TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_BUSINESS_STATUS,
									"1");
					CommonUtil.xUtilsGetSend(
							TootooeNetApiUrlHelper.CHECK_LOGIN_URL,
							requestParamsNet, new RequestCallBack<String>() {

								@Override
								public void onSuccess(
										ResponseInfo<String> responseInfo) {
									// TODO Auto-generated method stub
									closeProgressDialog(MobileLoginActivity.this);
									String loginStr = new String(
											responseInfo.result);
									loginParser = new LoginParser();
									try {
										LoginBean loginBean = loginParser
												.parserLogin(loginStr);
										if (loginBean.getLogin_status().equals(
												"1")) {
											SharedPreferenceHelper
													.saveLoginMsg(
															MobileLoginActivity.this,
															"phone",
															"",
															loginBean
																	.getLogin_Id(),
															loginBean
																	.getLogin_name(),
															loginBean
																	.getLogin_logoUrl(),
															loginBean
																	.getLogin_publishStory(),
															loginBean
																	.getLogin_businessStatus(),
															loginBean
																	.getLogin_medal(),
															loginBean
																	.getLogin_tCurrency(),
															loginBean
																	.getLogin_isLOHAS(),
															loginBean
																	.getLogin_userGrade(), loginBean.getLogin_coverImage());

											enterHomeActivity();

										} else if (loginBean.getLogin_status()
												.equals("1112")) {
											// 手机号不存在
											ComponentUtil
													.showToast(
															MobileLoginActivity.this,
															getResources()
																	.getString(
																			R.string.mobile_num_no_exist));
										} else if (loginBean.getLogin_status()
												.equals("1111")) {
											// 手机号和密码不匹配
											ComponentUtil
													.showToast(
															MobileLoginActivity.this,
															getResources()
																	.getString(
																			R.string.mobile_num_pwd_error));
										}

									} catch (Exception e) {
										e.printStackTrace();
									}

								}

								@Override
								public void onFailure(HttpException error,
										String msg) {
									// TODO Auto-generated method stub
									closeProgressDialog(MobileLoginActivity.this);
									ComponentUtil
											.showToast(
													MobileLoginActivity.this,
													getResources()
															.getString(
																	R.string.errcode_network_response_timeout));
								}
							});

				}
			}
		});

		// 忘记密码
		TextView mobile_login_forget_pwd = (TextView) findViewById(R.id.mobile_login_forget_pwd);
		mobile_login_forget_pwd.setTextColor(getResources().getColor(
				R.color.login_forget_pwd_text_color));
		mobile_login_forget_pwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		mobile_login_forget_pwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent forget_pwd_intent = new Intent(MobileLoginActivity.this,
						MobileForgetPwdActivity.class);
				forget_pwd_intent.putExtra("mobile_num", mobile_login_num
						.getText().toString().trim());
				startActivity(forget_pwd_intent);

			}
		});
	}

}
