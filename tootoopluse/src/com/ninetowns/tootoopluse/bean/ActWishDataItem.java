package com.ninetowns.tootoopluse.bean;

public class ActWishDataItem {
	/**活动id或者故事id**/
	private String data_item_Id;
	/**封面图**/
	private String data_item_CoverThumb;
	/**名称**/
	private String data_item_Name;
	private String UserConversion;
	
	public String getUserConversion() {
		return UserConversion;
	}
	public void setUserConversion(String userConversion) {
		UserConversion = userConversion;
	}
	/**浏览量**/
	private String data_item_Pageviews;
	/**转换率**/
	private String data_item_Conversion;
	
	public String getData_item_Id() {
		return data_item_Id;
	}
	public void setData_item_Id(String data_item_Id) {
		this.data_item_Id = data_item_Id;
	}
	public String getData_item_CoverThumb() {
		return data_item_CoverThumb;
	}
	public void setData_item_CoverThumb(String data_item_CoverThumb) {
		this.data_item_CoverThumb = data_item_CoverThumb;
	}
	public String getData_item_Name() {
		return data_item_Name;
	}
	public void setData_item_Name(String data_item_Name) {
		this.data_item_Name = data_item_Name;
	}
	public String getData_item_Pageviews() {
		return data_item_Pageviews;
	}
	public void setData_item_Pageviews(String data_item_Pageviews) {
		this.data_item_Pageviews = data_item_Pageviews;
	}
	public String getData_item_Conversion() {
		return data_item_Conversion;
	}
	public void setData_item_Conversion(String data_item_Conversion) {
		this.data_item_Conversion = data_item_Conversion;
	}
	@Override
	public String toString() {
		return "ActWishDataItem [data_item_Id=" + data_item_Id
				+ ", data_item_CoverThumb=" + data_item_CoverThumb
				+ ", data_item_Name=" + data_item_Name
				+ ", data_item_Pageviews=" + data_item_Pageviews
				+ ", data_item_Conversion=" + data_item_Conversion + "]";
	}
	
}
