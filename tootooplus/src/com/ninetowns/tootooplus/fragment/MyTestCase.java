package com.ninetowns.tootooplus.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.Des3;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;

import android.test.AndroidTestCase;
import android.text.TextUtils;

public class MyTestCase extends AndroidTestCase{
	private static final String API="http://172.16.8.133:91/userAddress/AddAddress.htm?";
	public MyTestCase() {
	}
	public void httpPostTestCase(){

		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ID, "3");
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_REALNAME, "fly_hi");
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_PHONENUMBER, "15210622255");
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_DETAILS, "北京九城");
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_POSTCODE, "100085");
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_CITYID, "2");
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_DISTRICTID, "7");
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_PROVINCEID, "1");
		CommonUtil.testxUtilsPostSend(API,
				requestParamsNet, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String strRespone = responseInfo.result;
						try {
							strRespone = Des3.decode(strRespone);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!TextUtils.isEmpty(strRespone)) {
							try {
								JSONObject jsobj = new JSONObject(
										strRespone);
								if (jsobj.has("Status")) {
									String status = jsobj
											.getString("Status");

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
					
					}
				});

	} 
		
	public void httpGetTestCase(){

		RequestParamsNet requestParamsNet = new RequestParamsNet();

		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.STORYCREATE_STORYID, "");
		CommonUtil.testxUtilsGetSend(TootooeNetApiUrlHelper.PAGE_SORT,
				requestParamsNet, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String strRespone = responseInfo.result;
						if (!TextUtils.isEmpty(strRespone)) {
							try {
								JSONObject jsobj = new JSONObject(
										strRespone);
								if (jsobj.has("Status")) {
									String status = jsobj
											.getString("Status");

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
					
					}
				});

	} 
		
		
		

}
