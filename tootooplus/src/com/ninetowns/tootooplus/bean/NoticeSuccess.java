package com.ninetowns.tootooplus.bean;

import java.io.Serializable;

public class NoticeSuccess implements Serializable {
	private String ActivityId;
	private String Content;
	private String CreateDate;

	public String getActivityId() {
		return ActivityId;
	}

	public void setActivityId(String activityId) {
		ActivityId = activityId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

}