package com.wiriamubin.service.chat;

public class SendMsgData {

	@Override
	public String toString() {
		return "SendMsgData [UserInfo="+ UserInfo.LogoUrl+ UserInfo.UserId+UserInfo.UserName+ ", Messages=" + Messages.Content+Messages.Type
				+ "]";
	}

	private UserInfo UserInfo;

	private Messages Messages;
	public UserInfo getUserInfo() {
		return UserInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		UserInfo = userInfo;
		if (null != userInfo) {
			UserInfo.LogoUrl = userInfo.LogoUrl;
			UserInfo.UserId = userInfo.UserId;
			UserInfo.UserName = userInfo.UserName;
		}
	}

	public Messages getMessages() {
		return Messages;
	}

	public void setMessage(Messages message) {
		this.Messages = message;
		if(null!=message){
			this.Messages.Content=message.Content;
			this.Messages.Type=message.Type;
		}
	}
}
