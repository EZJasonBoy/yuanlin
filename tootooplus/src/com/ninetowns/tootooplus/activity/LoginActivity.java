package com.ninetowns.tootooplus.activity;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.LoginBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.LoginParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;


public class LoginActivity extends BaseActivity implements OnClickListener, PlatformActionListener
{
	
	//跳转页面把包名带着Activity名传过来
	private String clazzName = "";
	//需要跳到的页面携带的参数
	private Bundle bundle = null;
	
	private LinearLayout two_or_one_btn_head_back;
	
	private EditText login_phone_num_et;
	
	private EditText login_pwd_et;
	
	private LoginParser loginParser;
	
	private final int THIRD_LOGIN_CANCEL = 1;
	
	private final int THIRD_LOGIN_ERROR = 2;
	
	private final int THIRD_LOGIN_COMPLETE = 3;
	
	private ImageView login_pwd_eye_iv;
	
	private boolean isPwdEyeOpen = false;
	
	private static final int JPUSH_SET_ALIAS = 1001;
	
	private String come_from = "";
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case THIRD_LOGIN_CANCEL:
				ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.login_auth_cancel));
				break;
			case THIRD_LOGIN_ERROR:
				ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.login_auth_error));
				break;
			case THIRD_LOGIN_COMPLETE:
				final Platform platform = (Platform)msg.obj;
				
				//第三方id
				final String loginRelationId = platform.getDb().getUserId();
				
				RequestParamsNet requestParamsNet = new RequestParamsNet();
				
				requestParamsNet.addQueryStringParameter("access_token", platform.getDb().getToken());
				requestParamsNet.addQueryStringParameter("openid", loginRelationId);
				requestParamsNet.addQueryStringParameter("lang", "zh_CN");
				HttpUtils httpUtils = new HttpUtils();
				httpUtils.send(HttpMethod.GET, "https://api.weixin.qq.com/sns/userinfo?", requestParamsNet,
						new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						String wechatStr = new String(responseInfo.result);
						loginParser = new LoginParser();
						try {
							String unionid = loginParser.parserWechat(wechatStr);
							RequestParamsNet requestParamsNet = new RequestParamsNet();
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_SOURCE, "wechat");
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_RELATIONID, unionid);
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_RELATIONNAME, platform.getDb().getUserName());
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_LOGOURL, platform.getDb().getUserIcon());
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_PHONENUMBER, "");
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_PASSWORD, "");
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_PHONE_ONLY_CODE, SharedPreferenceHelper.phoneUniqueId(LoginActivity.this));
							// "0"为客户
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_BUSINESS_STATUS, "0");
							
							CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.CHECK_LOGIN_URL,
									requestParamsNet, new RequestCallBack<String>() {

										@Override
										public void onSuccess(ResponseInfo<String> responseInfo) {
											// TODO Auto-generated method stub
											closeProgressDialog(LoginActivity.this);
											String loginStr = new String(responseInfo.result);
											loginParser = new LoginParser();
											try {
												LoginBean loginBean = loginParser.parserLogin(loginStr);
												if (loginBean.getLogin_status().equals("1")) {
														SharedPreferenceHelper.saveLoginMsg(LoginActivity.this, "wechat", loginRelationId, loginBean.getLogin_Id(), 
																loginBean.getLogin_name(), loginBean.getLogin_logoUrl(), loginBean.getLogin_publishStory(), loginBean.getLogin_businessStatus(),
																loginBean.getLogin_medal(), loginBean.getLogin_tCurrency(), loginBean.getLogin_isLOHAS(), loginBean.getLogin_userGrade(), loginBean.getLogin_coverImage());
														
													//登录成功以后调用JPush API设置Alias(别名)
													handler.sendMessage(handler.obtainMessage(JPUSH_SET_ALIAS, SharedPreferenceHelper.getLoginUserId(LoginActivity.this)));
														
													Intent intent = new Intent(LoginActivity.this, Class.forName(clazzName));
													intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
													intent.putExtra("bundle", bundle);
													startActivity(intent);
													finish();
													
												} else {
													ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.login_fail));
												}

											} catch (Exception e) {
												e.printStackTrace();
											}

										}

										@Override
										public void onFailure(HttpException error,
												String msg) {
											// TODO Auto-generated method stub
											closeProgressDialog(LoginActivity.this);
											ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
										}
									});
							
							
							
							
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(HttpException error,
							String msg) {
						// TODO Auto-generated method stub
						ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
					}
				});
				
				
				break;
			case JPUSH_SET_ALIAS:
            	JPushInterface.setAlias(LoginActivity.this, (String)msg.obj, new TagAliasCallback() {

			        @Override
			        public void gotResult(int code, String alias, Set<String> tags) {
			        	//该变量未用到
			            String logs ;
			            switch (code) {
			            case 0:
			            	//设置别名成功
			                logs = "Set tag and alias success";
			                break;
			                
			            case 6002:
			            	//设置别名失败,60秒以后重新设置
			                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
			                handler.sendMessageDelayed(handler.obtainMessage(JPUSH_SET_ALIAS, SharedPreferenceHelper.getLoginUserId(LoginActivity.this)), 1000 * 60);
			               
			                break;
			            
			            default:
			            }
			            
			        }
				    
				});
            	break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.login);
		ShareSDK.initSDK(this);
		init();
	}
	
	
	private void init(){
		two_or_one_btn_head_back = (LinearLayout)findViewById(R.id.two_or_one_btn_head_back);
		
		TextView two_or_one_btn_head_title = (TextView)findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.login_login);
		
		login_phone_num_et = (EditText)findViewById(R.id.login_phone_num_et);
		login_pwd_et = (EditText)findViewById(R.id.login_pwd_et);
		login_pwd_eye_iv = (ImageView)findViewById(R.id.login_pwd_eye_iv);
		
		//默认情况, 为密码状态
		login_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		login_pwd_eye_iv.setImageResource(R.drawable.icon_login_eye_close);
		
		TextView login_mobile_login = (TextView)findViewById(R.id.login_mobile_login);
		
		LinearLayout login_phone_register_layout = (LinearLayout)findViewById(R.id.login_phone_register_layout);
		
		LinearLayout login_forget_pwd_layout = (LinearLayout)findViewById(R.id.login_forget_pwd_layout);
		
//		//下划线和颜色,只要对应的textView的颜色设置为红色,下划线的颜色就为红色
//		login_service_term.setTextColor(Color.RED);
//		login_service_term.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		LinearLayout login_wechat_login_layout = (LinearLayout)findViewById(R.id.login_wechat_login_layout);
		
		LinearLayout login_pwd_eye_layout = (LinearLayout)findViewById(R.id.login_pwd_eye_layout);
		
		login_mobile_login.setOnClickListener(this);
		login_phone_register_layout.setOnClickListener(this);
		login_forget_pwd_layout.setOnClickListener(this);
		login_wechat_login_layout.setOnClickListener(this);
		two_or_one_btn_head_back.setOnClickListener(this);
		login_pwd_eye_layout.setOnClickListener(this);
		
		
		Intent intent = getIntent();
		bundle = intent.getExtras().getBundle("bundle");
		clazzName = bundle.getString("clazzName");
		come_from = getIntent().getStringExtra("come_from");
	}

	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.two_or_one_btn_head_back:
			if(come_from != null && come_from.equals("first_guide")){
				Intent back_to_intent = new Intent(LoginActivity.this, FirstGuideActivity.class);
				startActivity(back_to_intent);
				finish();
			} else {
				LoginActivity.this.finish();
			}
			
			break;
		case R.id.login_wechat_login_layout:
			//微信登录
			//测试时，需要打包签名；sample测试时，用项目里面的demokey.keystore
			//打包签名apk,然后才能产生微信的登录
			Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
			authorize(wechat);
			
			break;
		case R.id.login_mobile_login:
			
			mobileLogin();
			
			break;
		case R.id.login_phone_register_layout:
			Intent login_register_intent = new Intent(this, MobileRegisterActivity.class);
			login_register_intent.putExtra("bundle", bundle);
			startActivity(login_register_intent);
			finish();
			break;
		case R.id.login_forget_pwd_layout:
			Intent login_forget_intent = new Intent(this, MobileForgetPwdActivity.class);
			login_forget_intent.putExtra("bundle", bundle);
			login_forget_intent.putExtra("mobile_num", login_phone_num_et.getText().toString().trim());
			startActivity(login_forget_intent);
			finish();
			
			break;
			
		case R.id.login_pwd_eye_layout:
			//小眼睛睁眼或者闭眼
			isPwdEyeOpen = !isPwdEyeOpen;  
			if(isPwdEyeOpen){
				//正常文本, 比如"123456"
				login_pwd_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				login_pwd_eye_iv.setImageResource(R.drawable.icon_login_eye_open);
			} else {
				//密码, 比如"."
				login_pwd_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				login_pwd_eye_iv.setImageResource(R.drawable.icon_login_eye_close);

			}
			login_pwd_et.setSelection(login_pwd_et.getText().length());
			
			break;
		}
	}
	
	/**
	 * 点击新浪微博或者qq登录
	 * @param plat
	 */
	private void authorize(Platform plat) {
		
		showProgressDialog(this);
		
		plat.setPlatformActionListener(this);
		//关闭SSO授权
		plat.SSOSetting(true);
		plat.showUser(null);
	}

	
	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
		// TODO Auto-generated method stub
		closeProgressDialog(this);
		if (arg1 == Platform.ACTION_USER_INFOR) {
			
			handler.sendMessage(handler.obtainMessage(THIRD_LOGIN_COMPLETE, arg0));
		}
		
	}

	
	@Override
	public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub
		closeProgressDialog(this);
		if (arg1 == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(THIRD_LOGIN_CANCEL);
		}
		
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		closeProgressDialog(this);
		if (arg1 == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(THIRD_LOGIN_ERROR);
		}
		
	}
	
	
	/**
	 * 手机登录
	 */
	private void mobileLogin(){
		String mobile_num = login_phone_num_et.getText().toString().trim();
		String mobile_pwd = login_pwd_et.getText().toString().trim();
		if (mobile_num == null || "".equals(mobile_num)) {
			ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.mobile_num_no_empty));
		} else if (mobile_pwd == null || "".equals(mobile_pwd)) {
			ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.mobile_pwd_no_empty));
		} else if (!CommonUtil.isValidMobiNumber(mobile_num)) {
			ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.mobile_num_format_error));
		} else if (!CommonUtil.isValidPwd(mobile_pwd)) {
			ComponentUtil.showToast(LoginActivity.this,getResources().getString(R.string.mobile_login_pwd_hint));
		} else {
			// 显示进度
			showProgressDialog(LoginActivity.this);

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
									.phoneUniqueId(LoginActivity.this));
			// "0"为客户
			requestParamsNet
					.addQueryStringParameter(
							TootooeNetApiUrlHelper.CHECK_LOGIN_PARAM_BUSINESS_STATUS,
							"0");
			CommonUtil.xUtilsGetSend(
					TootooeNetApiUrlHelper.CHECK_LOGIN_URL,
					requestParamsNet, new RequestCallBack<String>() {

						@Override
						public void onSuccess(
								ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							closeProgressDialog(LoginActivity.this);
							String loginStr = new String(
									responseInfo.result);
							loginParser = new LoginParser();
							try {
								LoginBean loginBean = loginParser.parserLogin(loginStr);
								if (loginBean.getLogin_status().equals("1")) {
									SharedPreferenceHelper.saveLoginMsg(LoginActivity.this, "phone", "", loginBean.getLogin_Id(), loginBean.getLogin_name(),
										loginBean.getLogin_logoUrl(), loginBean.getLogin_publishStory(), loginBean.getLogin_businessStatus(),
										loginBean.getLogin_medal(), loginBean.getLogin_tCurrency(), loginBean.getLogin_isLOHAS(), loginBean.getLogin_userGrade(), loginBean.getLogin_coverImage());

									//登录成功以后调用JPush API设置Alias(别名)
									handler.sendMessage(handler.obtainMessage(JPUSH_SET_ALIAS, SharedPreferenceHelper.getLoginUserId(LoginActivity.this)));
									
									Intent intent = new Intent(LoginActivity.this, Class.forName(clazzName));
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									intent.putExtra("bundle", bundle);
									startActivity(intent);
									finish();
									
								} else if (loginBean.getLogin_status()
										.equals("1112")) {
									// 手机号不存在
									ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.mobile_num_no_exist));
								} else if (loginBean.getLogin_status().equals("1111")) {
									// 手机号和密码不匹配
									ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.mobile_num_pwd_error));
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(HttpException error,
								String msg) {
							// TODO Auto-generated method stub
							closeProgressDialog(LoginActivity.this);
							ComponentUtil.showToast(LoginActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
						}
					});

		}
	}
	
	
	/**
	 * 返回键回到登录页面
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(come_from != null && come_from.equals("first_guide")){
				Intent back_to_intent = new Intent(LoginActivity.this, FirstGuideActivity.class);
				startActivity(back_to_intent);
				finish();
			} else {
				LoginActivity.this.finish();
			}
        }
		
		return super.onKeyDown(keyCode, event);
	}
	

}
