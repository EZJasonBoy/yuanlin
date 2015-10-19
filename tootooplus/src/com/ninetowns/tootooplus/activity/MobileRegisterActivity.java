package com.ninetowns.tootooplus.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;

public class MobileRegisterActivity extends BaseActivity {
	
	//需要跳到的页面携带的参数
	private Bundle bundle = null;
	
	//来自哪里，注册页面有可能来自登录页面，有可能来自导航页面
	private String come_from;

	private EditText mobile_register_mobile_num;
	
	private TextView mobile_register_next_btn;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.mobile_register);
		
		init();
	}

	private void init(){
		
		bundle = getIntent().getExtras().getBundle("bundle");
		come_from = getIntent().getStringExtra("come_from");
		
		//返回
		LinearLayout two_or_one_btn_head_back = (LinearLayout)findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(come_from != null && come_from.equals("first_guide")){
					Intent back_to_intent = new Intent(MobileRegisterActivity.this, FirstGuideActivity.class);
					startActivity(back_to_intent);
					finish();
				} else {
					Intent back_to_intent = new Intent(MobileRegisterActivity.this, LoginActivity.class);
					back_to_intent.putExtra("bundle", bundle);
					startActivity(back_to_intent);
					finish();
				}
				
			}
		});
		
		//标题
		TextView two_or_one_btn_head_title = (TextView)findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.mobile_register_title);
		
		mobile_register_mobile_num = (EditText)findViewById(R.id.mobile_register_mobile_num);
		mobile_register_next_btn = (TextView)findViewById(R.id.mobile_register_next_btn);
		mobile_register_next_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String register_mobile_num = mobile_register_mobile_num.getText().toString().trim();
				if(register_mobile_num == null || "".equals(register_mobile_num)){
					ComponentUtil.showToast(MobileRegisterActivity.this, getResources().getString(R.string.mobile_num_no_empty));
				} else if(!CommonUtil.isValidMobiNumber(register_mobile_num)){
					ComponentUtil.showToast(MobileRegisterActivity.this, getResources().getString(R.string.mobile_num_format_error));
				} else {
					//显示进度
					showProgressDialog(MobileRegisterActivity.this);
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SEND_VERIFICATION_PHONENUMBER, register_mobile_num);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SEND_VERIFICATION_TYPE, "1");
					//"1"为商家, "0"为客户
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SEND_VERIFICATION_BUSINESS_STATUS, "0");
					CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.SEND_VERIFICATION_URL, requestParamsNet, new RequestCallBack<String>() {
						
						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							closeProgressDialog(MobileRegisterActivity.this);
							String jsonStr = new String(responseInfo.result);
							
							try {
								JSONObject jsonObj = new JSONObject(jsonStr);
								String register_status = "";
								if(jsonObj.has("Status")){
									register_status = jsonObj.getString("Status");
								}
								
								/**
								 * 暂时不能发短信，所以要记住这个字段，留着下一页输入
								 */
								if(jsonObj.has("Data")){
									JSONObject jsonData = jsonObj.getJSONObject("Data");
//									CommonUtil.sysApiLog("MobileRegisterActivity+++验证码", jsonData.getString("Verification"));
								}
								
								if(register_status.equals("1")){
									Intent intent = new Intent(MobileRegisterActivity.this, MobileRegisterNextActivity.class);
									intent.putExtra("mobile_num", register_mobile_num);
									intent.putExtra("bundle", bundle);
									startActivity(intent);
									finish();
								} else if(register_status.equals("1122")){
									ComponentUtil.showToast(MobileRegisterActivity.this, getResources().getString(R.string.mobile_num_exist));
								}
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						
						@Override
						public void onFailure(HttpException error, String msg) {
							// TODO Auto-generated method stub
							closeProgressDialog(MobileRegisterActivity.this);
							ComponentUtil.showToast(MobileRegisterActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
						}
					});
				}
			}
		});	
		
	}
	
	
	/**
	 * 返回键回到登录页面
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(come_from != null && come_from.equals("first_guide")){
				Intent back_to_intent = new Intent(MobileRegisterActivity.this, FirstGuideActivity.class);
				startActivity(back_to_intent);
				finish();
			} else {
				Intent back_to_intent = new Intent(MobileRegisterActivity.this, LoginActivity.class);
				back_to_intent.putExtra("bundle", bundle);
				startActivity(back_to_intent);
				finish();
			}
        }
		
		return super.onKeyDown(keyCode, event);
	}
	
}
