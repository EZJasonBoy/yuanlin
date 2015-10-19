package com.ninetowns.tootoopluse.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
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
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;

public class MobileForgetPwdActivity extends BaseActivity {

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
		//返回
		LinearLayout two_or_one_btn_head_back = (LinearLayout)findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//标题
		TextView two_or_one_btn_head_title = (TextView)findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.mobile_login_forget_pwd);
		
		String default_mobile_num = getIntent().getStringExtra("mobile_num");
		mobile_register_mobile_num = (EditText)findViewById(R.id.mobile_register_mobile_num);
		if(default_mobile_num != null){
			mobile_register_mobile_num.setText(default_mobile_num);
			mobile_register_mobile_num.setSelection(default_mobile_num.length());
		}
		
		mobile_register_next_btn = (TextView)findViewById(R.id.mobile_register_next_btn);
		mobile_register_next_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String register_mobile_num = mobile_register_mobile_num.getText().toString().trim();
				if(register_mobile_num == null || "".equals(register_mobile_num)){
					ComponentUtil.showToast(MobileForgetPwdActivity.this, getResources().getString(R.string.mobile_num_no_empty));
				} else if(!CommonUtil.isValidMobiNumber(register_mobile_num)){
					ComponentUtil.showToast(MobileForgetPwdActivity.this, getResources().getString(R.string.mobile_num_format_error));
				} else {
					//显示进度
					showProgressDialog(MobileForgetPwdActivity.this);
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SEND_VERIFICATION_PHONENUMBER, register_mobile_num);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SEND_VERIFICATION_TYPE, "2");
					//"1"为商家
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SEND_VERIFICATION_BUSINESS_STATUS, "1");
					CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.SEND_VERIFICATION_URL, requestParamsNet, new RequestCallBack<String>() {
						
						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							closeProgressDialog(MobileForgetPwdActivity.this);
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
									CommonUtil.sysApiLog("MobileForgetPwdActivity+++验证码", jsonData.getString("Verification"));
								}
								
								if(register_status.equals("1")){
									Intent intent = new Intent(MobileForgetPwdActivity.this, MobileForgetPwdNextActivity.class);
									intent.putExtra("mobile_num", register_mobile_num);
									startActivity(intent);
									finish();
								} else if(register_status.equals("1123")){
									ComponentUtil.showToast(MobileForgetPwdActivity.this, getResources().getString(R.string.mobile_num_no_exist));
								}
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						
						@Override
						public void onFailure(HttpException error, String msg) {
							// TODO Auto-generated method stub
							closeProgressDialog(MobileForgetPwdActivity.this);
							ComponentUtil.showToast(MobileForgetPwdActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
						}
					});
					
				}
			}
		});	
	}
	
	
}
