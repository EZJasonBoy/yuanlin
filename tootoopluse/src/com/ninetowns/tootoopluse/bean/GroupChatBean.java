package com.ninetowns.tootoopluse.bean;

import java.io.Serializable;

/** 
* @ClassName: GroupChatBean 
* @Description: 每个分组的信息
* @author zhou
* @date 2015-2-10 下午1:28:13 
*  
*/
public class GroupChatBean implements Serializable{
	private String GroupId;
	private String GroupName;
	private String ActivityId;
	
	private String MemberCount;
	
	private String Type;
	
	public String getMemberCount() {
		return MemberCount;
	}
	public void setMemberCount(String memberCount) {
		MemberCount = memberCount;
	}
	
	public String getGroupId() {
		return GroupId;
	}
	public void setGroupId(String groupId) {
		this.GroupId = groupId;
	}
	public String getGroupName() {
		return GroupName;
	}
	public void setGroupName(String groupName) {
		GroupName = groupName;
	}
	public String getActivityId() {
		return ActivityId;
	}
	public void setActivityId(String activityId) {
		ActivityId = activityId;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	@Override
	public String toString() {
		return "GroupChatBean [GroupId=" + GroupId + ", GroupName=" + GroupName
				+ ", ActivityId=" + ActivityId + ", MemberCount=" + MemberCount
				+ ", Type=" + Type + "]";
	}
	
	
	

}
