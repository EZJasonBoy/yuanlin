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
	@Override
	public String toString() {
		return "UserInfo [UserId=" + UserId + ", UserName=" + UserName
				+ ", LogoUrl=" + LogoUrl + "]";
	}
	

}
