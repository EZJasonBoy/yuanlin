package com.ninetowns.tootooplus.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: ActivityDetailBean
 * @Description: 活动详情数据结构
 * @author wuyulong
 * @date 2015-2-10 下午2:41:13
 * 
 */
@SuppressWarnings("serial")
public class ActivityDetailBean implements Serializable {
	private String ActivityId;// 活动的id
	private String ActivityName;// 活动的名称
	private String Type;// ：0线上小白痴活动1线下大白痴活动;
	private String UserId;// 活动人
	private String UserName;
	private String LogoUrl;// 头像
	private String UserGrade;// 等级
	private String DateUpdate;// 时间
	private String CountCollent;// 收藏数
	private String Chat;// 点击过群聊 0默认值1点击过
	private String Collent;//点击收藏  0没有点1点击过
	private Integer TopCoefficient;//：置顶系数，从0（普通）开始,大于0为置顶new
	public Integer getTopCoefficient() {
		return TopCoefficient;
	}

	public void setTopCoefficient(Integer topCoefficient) {
		TopCoefficient = topCoefficient;
	}

	public String getCollent() {
		return Collent;
	}

	public void setCollent(String collent) {
		Collent = collent;
	}

	private String Comment;// 是否评论
	private String CountRecommend;//体验点评，推荐指数（值为0时，代表没有点评，不显示体验点评栏）
	private String GroupMembers;//参与组团，报名人数（值为0时，代表没有报名，不显示此栏）
	private String ActivityStatus;//1即将开始 2组团 3已组团 4已结束（当ActivityStatus==4，查看StoryList中Comment的值，心愿可单独点评 ）
	public String getActivityStatus() {
		return ActivityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		ActivityStatus = activityStatus;
	}

	public String getCountRecommend() {
		return CountRecommend;
	}

	public void setCountRecommend(String countRecommend) {
		CountRecommend = countRecommend;
	}

	public String getGroupMembers() {
		return GroupMembers;
	}

	public void setGroupMembers(String groupMembers) {
		GroupMembers = groupMembers;
	}

	private List<WishDetailBean> listWish;
	// 活动信息的bean
	private ActivityInfoBean activityInfoBean;

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

	public String getDateUpdate() {
		return DateUpdate;
	}

	public void setDateUpdate(String dateUpdate) {
		DateUpdate = dateUpdate;
	}

	public String getCountCollent() {
		return CountCollent;
	}

	public void setCountCollent(String countCollent) {
		CountCollent = countCollent;
	}

	public String getChat() {
		return Chat;
	}

	public void setChat(String chat) {
		Chat = chat;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	public List<WishDetailBean> getListWish() {
		return listWish;
	}

	public void setListWish(List<WishDetailBean> listWish) {
		this.listWish = listWish;
	}

	public ActivityInfoBean getActivityInfoBean() {
		return activityInfoBean;
	}

	public void setActivityInfoBean(ActivityInfoBean activityInfoBean) {
		this.activityInfoBean = activityInfoBean;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	@Override
	public String toString() {
		return "ActivityDetailBean [ActivityId=" + ActivityId
				+ ", ActivityName=" + ActivityName + ", Type=" + Type
				+ ", UserId=" + UserId + ", UserName=" + UserName
				+ ", LogoUrl=" + LogoUrl + ", UserGrade=" + UserGrade
				+ ", DateUpdate=" + DateUpdate + ", CountCollent="
				+ CountCollent + ", Chat=" + Chat + ", Collent=" + Collent
				+ ", Comment=" + Comment + ", CountRecommend=" + CountRecommend
				+ ", GroupMembers=" + GroupMembers + ", ActivityStatus="
				+ ActivityStatus + ", listWish=" + listWish
				+ ", activityInfoBean=" + activityInfoBean + "]";
	}
}
