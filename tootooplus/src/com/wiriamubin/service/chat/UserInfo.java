package com.wiriamubin.service.chat;


/** 
* @ClassName: ChatUserInfo 
* @Description: 用户信息实体类
* @author zhou
* @date 2015-1-26 下午7:16:25 
*  
*/
public class UserInfo {
	/** 
	* @Fields userId : 用户id
	*/ 
	public String UserId;
	
	/** 
	* @Fields name :  用户名
	*/ 
	public String UserName;
	/** 
	* @Fields profileImage :用户头像地址 
	*/ 
	public String LogoUrl;
	public UserInfo(String userId, String userName, String logoUrl) {
		super();
		UserId = userId;
		UserName = userName;
		LogoUrl = logoUrl;
	}
	
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

	@Override
	public String toString() {
		return "UserInfo [UserId=" + UserId + ", UserName=" + UserName
				+ ", LogoUrl=" + LogoUrl + "]";
	}


}
