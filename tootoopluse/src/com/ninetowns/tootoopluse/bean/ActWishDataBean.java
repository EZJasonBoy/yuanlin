package com.ninetowns.tootoopluse.bean;

import java.util.List;

public class ActWishDataBean {
	
	/**总转化率**/
	private String data_Conversion;
	
	/**总浏览量**/
	private String data_Pageviews;
	
	/**开始时间**/
	private String data_StartTimestamp;
	/**结束时间**/
	private String data_EndTimestamp;
	
	private List<ActWishDataItem> actWishList;
	private String UserConversion;
	
	public String getUserConversion() {
		return UserConversion;
	}
	public void setUserConversion(String userConversion) {
		UserConversion = userConversion;
	}
	public String getData_Conversion() {
		return data_Conversion;
	}
	public void setData_Conversion(String data_Conversion) {
		this.data_Conversion = data_Conversion;
	}
	public String getData_Pageviews() {
		return data_Pageviews;
	}
	public void setData_Pageviews(String data_Pageviews) {
		this.data_Pageviews = data_Pageviews;
	}
	public String getData_StartTimestamp() {
		return data_StartTimestamp;
	}
	public void setData_StartTimestamp(String data_StartTimestamp) {
		this.data_StartTimestamp = data_StartTimestamp;
	}
	public String getData_EndTimestamp() {
		return data_EndTimestamp;
	}
	public void setData_EndTimestamp(String data_EndTimestamp) {
		this.data_EndTimestamp = data_EndTimestamp;
	}
	public List<ActWishDataItem> getActWishList() {
		return actWishList;
	}
	public void setActWishList(List<ActWishDataItem> actWishList) {
		this.actWishList = actWishList;
	}
	@Override
	public String toString() {
		return "ActWishDataBean [data_Conversion=" + data_Conversion
				+ ", data_Pageviews=" + data_Pageviews
				+ ", data_StartTimestamp=" + data_StartTimestamp
				+ ", data_EndTimestamp=" + data_EndTimestamp + ", actWishList="
				+ actWishList + "]";
	}
	
	

}