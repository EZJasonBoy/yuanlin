package com.ninetowns.tootoopluse.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
/**
 * 
* @ClassName: SecondStepStoryBean 
* @Description: 第二步bean
* @author wuyulong
* @date 2015-2-9 下午6:53:42 
*
 */
@SuppressWarnings("serial")
public class SecondStepStoryBean implements Serializable {
	private HashMap<String,Integer> mHashMap;//记录位置的hashmap
	public HashMap<String, Integer> getmHashMap() {
		return mHashMap;
	}
	public void setmHashMap(HashMap<String, Integer> mHashMap) {
		this.mHashMap = mHashMap;
	}
	private String ActivityName;//活动名称
	private String storyIdArray;
	public String getStoryIdArray() {
		return storyIdArray;
	}
	public void setStoryIdArray(String storyIdArray) {
		this.storyIdArray = storyIdArray;
	}
	public String getActivityName() {
		return ActivityName;
	}
	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}
	public String getActivityId() {
		return ActivityId;
	}
	public void setActivityId(String activityId) {
		ActivityId = activityId;
	}
	public List<CreateActivitySecondBean> getStoryList() {
		return storyList;
	}
	public void setStoryList(List<CreateActivitySecondBean> storyList) {
		this.storyList = storyList;
	}
	private String  ActivityId;//活动的id
	private List<CreateActivitySecondBean> storyList;

}
