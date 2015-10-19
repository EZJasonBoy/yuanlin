package com.ninetowns.library.net;

import java.util.Date;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 
 * @Title: NetRequestParams.java
 * @Description: 设置请求的方式，参数，以及接口的地址
 * @author wuyulong
 * @date 2015-1-16 下午5:43:08
 * @version V1.0
 */
public class RequestParamsNet extends RequestParams {
	public RequestParamsNet() {
		this.addQueryStringParameter("TimeStamp", new Date().getTime()
				+ "");// 加入时间戳
	}
	
	private HttpMethod mHttpMethod;// 设置http get访问的方式

	public void setmHttpMethodPost() {
		this.mHttpMethod = HttpMethod.POST;
	}

	public HttpMethod getmHttpMethod() {
		return mHttpMethod;
	}

	public void setmHttpMethodGet() {
		this.mHttpMethod = HttpMethod.GET;
	}

	private String mStrHttpApi;// 获取网络的api接口

	public String getmStrHttpApi() {
		return mStrHttpApi;
	}

	public void setmStrHttpApi(String mStrHttpApi) {
		this.mStrHttpApi = mStrHttpApi;
	}

}
