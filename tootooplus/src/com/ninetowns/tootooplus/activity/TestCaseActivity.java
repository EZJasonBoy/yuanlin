package com.ninetowns.tootooplus.activity;

import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.util.URLEncodedUtils;
import com.lidroid.xutils.util.Des3;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;



public class TestCaseActivity extends Activity{
	public TestCaseActivity() {
		// TODO Auto-generated constructor stub
	}
	private TextView mTvTest;
	private TextView mTvClick;
	private TextView tv_test_click_get;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		mTvTest=(TextView)findViewById(R.id.tv_test);
		mTvClick=(TextView)findViewById(R.id.tv_test_click);
		tv_test_click_get=(TextView)findViewById(R.id.tv_test_click_get);
		tv_test_click_get.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				httpGetTestCase();
			}
		});
		mTvClick.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				httpPostTestCase();
			}
		});
		
		
		
	}
//	private static final String API="http://172.16.8.133:91/userAddress/AddAddress.htm?";
	private static final String API="http://172.16.8.133:91/notice/versionUpgrade.htm?";
	
	public void httpPostTestCase(){

		
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter("Type", "androidBusiness");
		requestParamsNet.addQueryStringParameter("Version", "1.7.0");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ID, "3");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_REALNAME, "fly_hi$%+");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_PHONENUMBER, "15210622255");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_DETAILS, "北京九城");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_POSTCODE, "100085");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_CITYID, "2");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_DISTRICTID, "7");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_PROVINCEID, "1");
		CommonUtil.testxUtilsPostSend(API,
				requestParamsNet, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String str="";
						String strRespone = responseInfo.result;
//						try {
////							str = Des3.decode(strRespone);
//							mTvTest.setText(str);
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						if (!TextUtils.isEmpty(strRespone)) {
							mTvTest.setText(strRespone);
							try {
								JSONObject jsobj = new JSONObject(
										strRespone);
								if (jsobj.has("Status")) {
									String status = jsobj
											.getString("Status");

								}
								if (jsobj.has("Type")) {
									String status = jsobj
											.getString("Type");
									mTvTest.setText(status);
								}

							} catch (JSONException e) {
								LogUtil.error("CreateStoryFragment",
										e.toString());
								e.printStackTrace();
							}
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						System.out.println(msg);
					
					}
				});

	} 
	public void httpGetTestCase(){

		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter("Type", "androidBusiness");
		requestParamsNet.addQueryStringParameter("Version", "1.7.0");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ID, "101620");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_REALNAME, "哼哈&+$$二将&");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_PHONENUMBER, "13012345678");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_DETAILS, "地方搞好环境基金");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_POSTCODE, "100000");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_CITYID, "2");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_DISTRICTID, "7");
//		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_PROVINCEID, "1");
		CommonUtil.testxUtilsGetSend(API,
				requestParamsNet, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String strRespone = responseInfo.result;
//				strRespone=strRespone.replace("=", "");
//				String strRespone = "﻿﻿w3ofcSLz7i463nB6TuY0PcaPzBVDQag/vqxIivgxG6RZkUn/djBBnw==";
//				try {
//					strRespone = Des3.decode(strRespone);
//					mTvTest.setText(strRespone);
//				} catch (Exception e) {
//					mTvTest.setText(e.toString());
//					e.printStackTrace();
//				}
				
				if (!TextUtils.isEmpty(strRespone)) {
					mTvTest.setText(strRespone);
					try {
						JSONObject jsobj = new JSONObject(
								strRespone);
						if (jsobj.has("Status")) {
							String status = jsobj
									.getString("Status");

						}
						
						if (jsobj.has("DetailedAddress")) {
							String adree = jsobj
									.getString("DetailedAddress");
							mTvTest.setText(adree);
						}
						
						

					} catch (JSONException e) {
						LogUtil.error("CreateStoryFragment",
								e.toString());
						e.printStackTrace();
					}
				}

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				System.out.println(msg);
			
			}
		});

	} 
}
