package com.wiriamubin.service.chat;

import java.util.Comparator;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * @ClassName: MyChatData
 * @Description: java实体类
 * @author zhou
 * @date 2015-1-29 下午1:28:11 , execAfterTableCreated =
 *       "CREATE UNIQUE INDEX index_name ON mychatdata(groupId)")
 */
@Table(name = "mychatdata")
public class MyChatData extends EntityBase  {

	public MyChatData() {
	}

	public MyChatData(ChatData data, String groupId, boolean isReceiver,
			boolean isChatRoom, String time) {
		this.userId = data.userId;
		this.name = data.name;
		this.profileImage = data.profileImage;
		this.type = data.type;
		this.body = data.body;
		this.groupId = groupId;
		this.isRecever = isReceiver;
		this.isChatRoom = isChatRoom;
		this.time = time;
	}

	@Column(column = "groupId")
	public String groupId;
	@Column(column = "time")
	public String time;
	/**
	 * @Fields isRecever : 是否为接受者 false为发送者 true接受者
	 */
	@Column(column = "isRecever")
	public boolean isRecever;
	@Column(column = "isChatRoom")
	private boolean isChatRoom;// 1为 聊天室 2私信

	public MyChatData(String userId, String name, String profileImage,
			String type, String body, String groupId, boolean isReceiver,
			boolean isChatRoom, String time) {
		super();
		this.userId = userId;
		this.name = name;

		this.profileImage = profileImage;
		this.type = type;
		this.body = body;
		this.groupId = groupId;
		this.isRecever = isReceiver;
		this.isChatRoom = isChatRoom;
		this.time = time;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isRecever() {
		return isRecever;
	}

	public void setRecever(boolean isRecever) {
		this.isRecever = isRecever;
	}

	public boolean isChatRoom() {
		return isChatRoom;
	}

	public void setChatRoom(boolean isChatRoom) {
		this.isChatRoom = isChatRoom;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @Fields userId : 用户id
	 */
	@Column(column = "userId")
	// 建议加上注解， 混淆后列名不受影响
	public String userId;

	/**
	 * @Fields name : 用户名
	 */
	@Column(column = "name")
	// 建议加上注解， 混淆后列名不受影响
	public String name;
	/**
	 * @Fields profileImage :用户头像地址
	 */
	@Column(column = "profileImage")
	// 建议加上注解， 混淆后列名不受影响
	public String profileImage;

	/**
	 * 消息类型
	 */
	@Column(column = "type")
	public String type;

	/**
	 * 消息正文
	 */
	@Column(column = "body")
	public String body;

	@Override
	public String toString() {
		return "MyChatData [groupId=" + groupId + ", isRecever=" + isRecever
				+ ", userId=" + userId + ", name=" + name + ", profileImage="
				+ profileImage + ", type=" + type + ", body=" + body + ",id="
				+ id + "]";
	}

	

}
