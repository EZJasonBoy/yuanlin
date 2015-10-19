package com.ninetowns.tootoopluse.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.wiriamubin.service.chat.EntityBase;

/**
 * @ClassName: PrivateLetterMessageBean
 * @Description: 私信详情中的实体类
 * @author zhou
 * @date 2015-3-9 下午4:41:38
 * 
 */

@Table(name = "privateLettermessage")
public class PrivateLetterMessageBean extends EntityBase {

	// "MailId": "98",
	// "UserIdSend": "3",
	// "UserName": "TestUserName1",
	// "LogoUrl": "http://172.16.8.96:90/3.png",
	// "Content": "",
	// "Type": "1",
	// "CreateDate": "2015-03-06 11:39:19"
	@Column(column = "MailId")
	private String MailId;
	/** 
	* @Fields UserIdSend : 发送方的用户id
	*/ 
	@Column(column = "UserIdSend")
	private String UserIdSend;
	@Column(column = "UserName")
	private String UserName;
	@Column(column = "LogoUrl")
	private String LogoUrl;
	@Column(column = "Content")
	private String Content;
	@Column(column = "Type")
	private String Type;
	@Column(column = "CreateDate")
	private String CreateDate;
	@Override
	public String toString() {
		return "PrivateLetterMessageBean [MailId=" + MailId + ", UserIdSend="
				+ UserIdSend + ", UserName=" + UserName + ", LogoUrl="
				+ LogoUrl + ", Content=" + Content + ", Type=" + Type
				+ ", CreateDate=" + CreateDate + "]";
	}
	public String getMailId() {
		return MailId;
	}
	public void setMailId(String mailId) {
		MailId = mailId;
	}
	public String getUserIdSend() {
		return UserIdSend;
	}
	public void setUserIdSend(String userIdSend) {
		UserIdSend = userIdSend;
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
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}
	

}
