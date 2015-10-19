package com.ninetowns.tootoopluse.activity;

import org.json.JSONObject;

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
import com.ninetowns.tootoopluse.R.layout;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;

public class ChangeNickActivity extends BaseActivity {
	
	private EditText change_nick_nick;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(layout.change_nick);
		
		init();
	}

	private void init(){
		String default_nick = getIntent().getStringExtra("nick");
		change_nick_nick = (EditText)findViewById(R.id.change_nick_nick);
		change_nick_nick.setText(default_nick);
		change_nick_nick.setSelection(default_nick.length());
		//返回按钮
		LinearLayout two_or_one_btn_head_back = (LinearLayout)findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
		//标题
		TextView two_or_one_btn_head_title = (TextView)findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.change_nick_title);
		
		//右上角按钮
		TextView two_or_one_btn_head_second_tv = (TextView)findViewById(R.id.two_or_one_btn_head_second_tv);
		//暂时先用这个图片
		two_or_one_btn_head_second_tv.setText(R.string.rainbo_sure);
		two_or_one_btn_head_second_tv.setVisibility(View.VISIBLE);
		RelativeLayout two_or_one_btn_head_second_layout = (RelativeLayout)findViewById(R.id.two_or_one_btn_head_second_layout);
		two_or_one_btn_head_second_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//提交修改
				String new_nick = change_nick_nick.getText().toString().trim();
				if(new_nick == null || "".equals(new_nick)){
					ComponentUtil.showToast(ChangeNickActivity.this, getResources().getString(R.string.mobile_nick_no_empty));
				} else if(!CommonUtil.isValidName(new_nick)){
					//判断是否是中英文,数字,"-","_"
					ComponentUtil.showToast(ChangeNickActivity.this, getResources().getString(R.string.mobile_nick_name_hint));
				}  else if(!CommonUtil.isValidNameLength(new_nick)){
					//判断是否是4-24个字符
					ComponentUtil.showToast(ChangeNickActivity.this, getResources().getString(R.string.mobile_nick_name_hint));
				} else {
					//显示进度
					showProgressDialog(ChangeNickActivity.this);
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_USERNAME_USERID, SharedPreferenceHelper.getLoginUserId(ChangeNickActivity.this));
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_USERNAME_USERNAME, new_nick);
					
					CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.CHANGE_USERNAME_URL, requestParamsNet, new RequestCallBack<String>(){

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							closeProgressDialog(ChangeNickActivity.this);
							String jsonStr = new String(responseInfo.result);
							try {
								JSONObject jsonObj = new JSONObject(jsonStr);
								String change_status = "";
								if(jsonObj.has("Status")){
									change_status = jsonObj.getString("Status");
								}
								
								String bg_nick_name = "";
								if(jsonObj.has("Data")){
									JSONObject jsonData = jsonObj.getJSONObject("Data");
									if(jsonData.has("UserName")){
										bg_nick_name = jsonData.getString("UserName");
									}
								}
								
								
								if(change_status.equals("1")){
									//修改成功
									ComponentUtil.showToast(ChangeNickActivity.this, getResources().getString(R.string.change_nick_nick_success));
									//修改本地存储的用户昵称
									SharedPreferenceHelper.changeLoginName(bg_nick_name, ChangeNickActivity.this);
									finish();
									
								} else {
									ComponentUtil.showToast(ChangeNickActivity.this, getResources().getString(R.string.change_nick_nick_fail));
								}
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							// TODO Auto-generated method stub
							closeProgressDialog(ChangeNickActivity.this);
							ComponentUtil.showToast(ChangeNickActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
						}
						
					});
					
					
					
				}
			}
		});
					
	}
}
