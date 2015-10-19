package com.ninetowns.tootooplus.activity;

import org.json.JSONObject;

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
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;

public class ChangePwdActivity extends BaseActivity {
	
	private EditText ch_pwd_old_pwd, ch_pwd_new_pwd, ch_pwd_sure_new_pwd;
	
	private TextView ch_pwd_submit_btn;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.change_pwd);
		
		init();
	}

	private void init(){
		//返回按钮
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
		two_or_one_btn_head_title.setText(R.string.personal_center_change_pwd);
		
		ch_pwd_old_pwd = (EditText)findViewById(R.id.ch_pwd_old_pwd);
		ch_pwd_new_pwd = (EditText)findViewById(R.id.ch_pwd_new_pwd);
		ch_pwd_sure_new_pwd = (EditText)findViewById(R.id.ch_pwd_sure_new_pwd);
		
		ch_pwd_submit_btn = (TextView)findViewById(R.id.ch_pwd_submit_btn);
		ch_pwd_submit_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String old_pwd = ch_pwd_old_pwd.getText().toString().trim();
				String new_pwd = ch_pwd_new_pwd.getText().toString().trim();
				String sure_new_pwd = ch_pwd_sure_new_pwd.getText().toString().trim();
				if(old_pwd == null || "".equals(old_pwd)){
					ComponentUtil.showToast(ChangePwdActivity.this, getResources().getString(R.string.change_pwd_old_pwd_no_empty));
				} else if(new_pwd == null || "".equals(new_pwd)){
					ComponentUtil.showToast(ChangePwdActivity.this, getResources().getString(R.string.change_pwd_new_pwd_no_empty));
				} else if(!CommonUtil.isValidPwd(new_pwd)){ 
					ComponentUtil.showToast(ChangePwdActivity.this, getResources().getString(R.string.mobile_login_pwd_hint));
				}else if(sure_new_pwd == null || !new_pwd.equals(sure_new_pwd)){
					ComponentUtil.showToast(ChangePwdActivity.this, getResources().getString(R.string.change_pwd_new_pwd_differ));
				} else {
					//显示进度
					showProgressDialog(ChangePwdActivity.this);
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_PWD_USERID, SharedPreferenceHelper.getLoginUserId(ChangePwdActivity.this));
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_PWD_OLD_PWD, CommonUtil.md5(old_pwd));
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_PWD_NEW_PWD, CommonUtil.md5(new_pwd));
					CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.CHANGE_PWD_URL, requestParamsNet, new RequestCallBack<String>(){

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							closeProgressDialog(ChangePwdActivity.this);
							String jsonStr = new String(responseInfo.result);
							try {
								JSONObject jsonObj = new JSONObject(jsonStr);
								String change_status = "";
								if(jsonObj.has("Status")){
									change_status = jsonObj.getString("Status");
								}
								
								if(change_status.equals("1")){
									//修改成功
									ComponentUtil.showToast(ChangePwdActivity.this, getResources().getString(R.string.change_pwd_change_success));
									//关闭修改密码页
									finish();
									
								} else if(change_status.equals("1312")){
									ComponentUtil.showToast(ChangePwdActivity.this, getResources().getString(R.string.change_pwd_old_pwd_error));
								} else {
									ComponentUtil.showToast(ChangePwdActivity.this, getResources().getString(R.string.change_pwd_change_fail));
								}
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							// TODO Auto-generated method stub
							closeProgressDialog(ChangePwdActivity.this);
							ComponentUtil.showToast(ChangePwdActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
						}
						
					});
					
				}
			}
		});
		
	}
}
