package com.ninetowns.tootooplus.activity;

import java.util.Set;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.LoginBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.LoginParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;

public class MobileRegisterNextActivity extends BaseActivity {
	//跳转页面把包名带着Activity名传过来
	private String clazzName = "";
	//需要跳到的页面携带的参数
	private Bundle bundle = null;
	
	private String mobile_num;
	
	private EditText register_next_verification, register_next_pwd, register_next_nick;
	
	private TextView register_next_register_btn, register_next_again_send;
	
	private LoginParser loginParser;
	
	private final long COUNT_ONCLICK_PERIOD = 1000;
	
	private final long COUNT_TOTAL_TIME = 60 * 1000;
	
	private CountDownTimer countDownTimer;
	
	private static final int JPUSH_SET_ALIAS = 1001;
	
	private CheckBox register_cb_term;
	
	private TextView register_term_after_tv;
	
	
	private Handler jPushHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			 switch (msg.what) {
	            case JPUSH_SET_ALIAS:
	            	JPushInterface.setAlias(MobileRegisterNextActivity.this, (String)msg.obj, new TagAliasCallback() {

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
				                jPushHandler.sendMessageDelayed(jPushHandler.obtainMessage(JPUSH_SET_ALIAS, SharedPreferenceHelper.getLoginUserId(MobileRegisterNextActivity.this)), 1000 * 60);
				               
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
		setContentView(R.layout.mobile_register_next);
		
		init();
	}

	private void init(){
		//返回
		LinearLayout two_or_one_btn_head_back = (LinearLayout)findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent back_to_regist_intent = new Intent(MobileRegisterNextActivity.this, MobileRegisterActivity.class);
				back_to_regist_intent.putExtra("bundle", bundle);
				startActivity(back_to_regist_intent);
				finish();
			}
		});
		
		register_cb_term = (CheckBox)findViewById(R.id.register_cb_term);
		
		register_term_after_tv = (TextView)findViewById(R.id.register_term_after_tv);
		register_term_after_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//服务条款
				Intent intent = new Intent(MobileRegisterNextActivity.this, ServiceTermActivity.class);
				startActivity(intent);
			}
		});
		
		//标题
		TextView two_or_one_btn_head_title = (TextView)findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.mobile_register_title);
		
		Intent intent = getIntent();
		bundle = intent.getExtras().getBundle("bundle");
		clazzName = bundle.getString("clazzName");
		//手机号码
		mobile_num = intent.getStringExtra("mobile_num");
		
		register_next_verification = (EditText)findViewById(R.id.register_next_verification);
		register_next_pwd = (EditText)findViewById(R.id.register_next_pwd);
		register_next_nick = (EditText)findViewById(R.id.register_next_nick);
		
		register_next_register_btn = (TextView)findViewById(R.id.register_next_register_btn);
		register_next_register_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!register_cb_term.isChecked()){
					ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.register_term_unread));
				} else {
					String reg_verification = register_next_verification.getText().toString().trim();
					String reg_pwd = register_next_pwd.getText().toString().trim();
					String reg_nick = register_next_nick.getText().toString().trim();
					if(reg_verification == null || "".equals(reg_verification)){
						ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.mobile_ver_no_empty));
					} else if(reg_pwd == null || "".equals(reg_pwd)){
						ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.mobile_pwd_no_empty));
					} else if(reg_nick == null || "".equals(reg_nick)){
						ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.mobile_nick_no_empty));
					} else if(!CommonUtil.isValidPwd(reg_pwd)){
						ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.mobile_login_pwd_hint));
					} else if(!CommonUtil.isValidName(reg_nick)){
						//判断是否是中英文,数字,"-","_"
						ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.mobile_nick_name_hint));
					}  else if(!CommonUtil.isValidNameLength(reg_nick)){
						//判断是否是4-24个字符
						ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.mobile_nick_name_hint));
					} else {
						//显示进度
						showProgressDialog(MobileRegisterNextActivity.this);
						RequestParamsNet requestParamsNet = new RequestParamsNet();
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REGIST_PHONE_NUM, mobile_num);
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REGIST_PHONE_VERIFICATION, reg_verification);
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REGIST_PHONE_PASSWORD, CommonUtil.md5(reg_pwd));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REGIST_PHONE_NAME, reg_nick);
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REGIST_PHONE_ONLY_CODE, SharedPreferenceHelper.phoneUniqueId(MobileRegisterNextActivity.this));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REGIST_PHONE_BUSINESS_STATUS, "0");
						CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.REGIST_PHONE_MUMBER_URL, requestParamsNet, new RequestCallBack<String>() {
							
							@Override
							public void onSuccess(ResponseInfo<String> responseInfo) {
								// TODO Auto-generated method stub
								closeProgressDialog(MobileRegisterNextActivity.this);
								String loginStr = new String(responseInfo.result);
								loginParser = new LoginParser();
								try {
									LoginBean loginBean = loginParser.parserLogin(loginStr);
									if(loginBean.getLogin_status().equals("1")){
										SharedPreferenceHelper.saveLoginMsg(MobileRegisterNextActivity.this, "phone", "", loginBean.getLogin_Id(), 
												loginBean.getLogin_name(), loginBean.getLogin_logoUrl(), loginBean.getLogin_publishStory(), loginBean.getLogin_businessStatus(), 
												loginBean.getLogin_medal(), loginBean.getLogin_tCurrency(), loginBean.getLogin_isLOHAS(), loginBean.getLogin_userGrade(), loginBean.getLogin_coverImage());
										
										//注册成功以后调用JPush API设置Alias(别名)
										jPushHandler.sendMessage(jPushHandler.obtainMessage(JPUSH_SET_ALIAS, SharedPreferenceHelper.getLoginUserId(MobileRegisterNextActivity.this)));
										
										Intent intent = new Intent(MobileRegisterNextActivity.this, Class.forName(clazzName));
										intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										intent.putExtra("bundle", bundle);
										startActivity(intent);
										finish();
										
										
									} else if(loginBean.getLogin_status().equals("1132")){
										ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.mobile_ver_error));
									}
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}
							
							@Override
							public void onFailure(HttpException error, String msg) {
								// TODO Auto-generated method stub
								closeProgressDialog(MobileRegisterNextActivity.this);
								ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
							}
						});
						
						
					}
				}
			}
		});
		
		
		register_next_again_send = (TextView)findViewById(R.id.register_next_again_send);
		
		countDownTimer = new CountDownTimer(COUNT_TOTAL_TIME, COUNT_ONCLICK_PERIOD) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				register_next_again_send.setText(getResources().getString(R.string.mobile_ver_again_send) + "(" + millisUntilFinished / 1000 + ")");
				register_next_again_send.setBackgroundResource(R.drawable.btn_register_send_again_gray);
			}
			
			@Override
			public void onFinish() {
				register_next_again_send.setText(getResources().getString(R.string.mobile_ver_again_send));
				register_next_again_send.setBackgroundResource(R.drawable.btn_register_send_again_red);
			}
		};
		countDownTimer.start();
		register_next_again_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(register_next_again_send.getText().toString().trim().equals(getResources().getString(R.string.mobile_ver_again_send))){
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SEND_VERIFICATION_PHONENUMBER, mobile_num);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SEND_VERIFICATION_TYPE, "1");
					//"0"为客户
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SEND_VERIFICATION_BUSINESS_STATUS, "0");
					CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.SEND_VERIFICATION_URL, requestParamsNet, new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							String jsonStr = new String(responseInfo.result);
							
							try {
								JSONObject jsonObj = new JSONObject(jsonStr);
								String register_status = "";
								if(jsonObj.has("Status")){
									register_status = jsonObj.getString("Status");
								}
								
								
								if(register_status.equals("1")){
									/**
									 * 暂时不能发短信，所以要记住这个字段
									 */
									if(jsonObj.has("Data")){
										JSONObject jsonData = jsonObj.getJSONObject("Data");
		CommonUtil.sysApiLog("MobileRegisterNextActivity+++++验证码", jsonData.getString("Verification"));
									}
									
									//接口调用成功以后再启动倒计时
									countDownTimer.start();;
								}
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							// TODO Auto-generated method stub
							ComponentUtil.showToast(MobileRegisterNextActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
						}
						
					});
					
					
				}
				
			}
		});
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		countDownTimer.cancel();
	}
	
	/**
	 * 返回键回到注册第一步
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent back_to_regist_intent = new Intent(MobileRegisterNextActivity.this, MobileRegisterActivity.class);
			back_to_regist_intent.putExtra("bundle", bundle);
			startActivity(back_to_regist_intent);
			finish();
        }
		
		return super.onKeyDown(keyCode, event);
	}
	
}
