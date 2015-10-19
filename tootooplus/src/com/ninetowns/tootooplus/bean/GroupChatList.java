package com.ninetowns.tootooplus.bean;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: GroupChatList 
* @Description: 聊天群组集合实体
* @author zhou
* @date 2015-2-10 下午1:30:23 
*  
*/
public class GroupChatList implements Serializable{
	
	public int MsgCount=0;
	private String ActivityId;
	private String ActivityName;
	private String Counts;
	private String CoverThumb;
	
	private String StoryId;
	
	private List<GroupChatBean>groupChatBeans;
	
	public String getCoverThumb() {
		return CoverThumb;
	}
	public void setCoverThumb(String coverThumb) {
		CoverThumb = coverThumb;
	}
	
	public List<GroupChatBean> getGroupChatBeans() {
		return groupChatBeans;
	}
	public void setGroupChatBeans(List<GroupChatBean> groupChatBeans) {
		this.groupChatBeans = groupChatBeans;
	}
	public String getActivityId() {
		return ActivityId;
	}
	public void setActivityId(String activityId) {
		ActivityId = activityId;
	}
	public String getActivityName() {
		return ActivityName;
	}
	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}
	public String getCounts() {
		return Counts;
	}
	public void setCounts(String counts) {
		Counts = counts;
	}
	public int getMsgCount() {
		return MsgCount;
	}
	public void setMsgCount(int msgCount) {
		MsgCount = msgCount;
	}
	public String getStoryId() {
		return StoryId;
	}
	public void setStoryId(String storyId) {
		StoryId = storyId;
	}
	@Override
	public String toString() {
		return "GroupChatList [MsgCount=" + MsgCount + ", ActivityId="
				+ ActivityId + ", ActivityName=" + ActivityName + ", Counts="
				+ Counts + ", CoverThumb=" + CoverThumb + ", StoryId="
				+ StoryId + ", groupChatBeans=" + groupChatBeans + "]";
	}
}
