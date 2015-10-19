package com.ninetowns.tootooplus.bean;

import java.io.Serializable;
/**
 * 
* @ClassName: GridViewPhotoBean 
* @Description: 我要白吃头像的信息
* @author wuyulong
* @date 2015-2-5 下午4:50:49 
*
 */
@SuppressWarnings("serial")
public class GridViewPhotoBean implements Serializable{
	private String UserId;
	private String UserName;
	private String LogoUrl;
	private String UserGrade;
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getLogoUrl() {
		return LogoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		LogoUrl = logoUrl;
	}
	public String getUserGrade() {
		return UserGrade;
	}
	public void setUserGrade(String userGrade) {
		UserGrade = userGrade;
	}
	

}
