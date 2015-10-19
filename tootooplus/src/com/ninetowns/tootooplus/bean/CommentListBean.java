package com.ninetowns.tootooplus.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: CommentListBean
 * @Description: 点评列表数据实体类
 * @author wuyulong
 * @date 2015-3-24 上午11:12:32
 * 
 */
public class CommentListBean implements Serializable {
	// /　StoryId：故事Id
	// StoryName：故事名称
	// StoryType：故事封面类型 2图片，3视频
	// CoverThumb：故事封面图，类型为图片或视频的改字段有值
	// StoryVideoUrl：故事封面录播视频地址
	// CountLike：点评故事，点赞的数量
	// CountRecommend：点评故事，推荐指数
	// UserId：故事创建者用户Id
	// UserName：故事创建者用户名称
	// LogoUrl：故事创建者用户头像
	// UserGrade：故事创建者用户等级
	private String StoryId;
	private String StoryName;
	private String StoryType;
	private String CoverThumb;
	private String StoryVideoUrl;
	private String CountLike;
	private String CountRecommend;
	private String UserId;
	private String UserName;
	private String LogoUrl;
	private String UserGrade;
	private String IsRecommend;//是否推荐
	public String getIsRecommend() {
		return IsRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		IsRecommend = isRecommend;
	}
	public String getStoryId() {
		return StoryId;
	}
	public void setStoryId(String storyId) {
		StoryId = storyId;
	}
	public String getStoryName() {
		return StoryName;
	}
	public void setStoryName(String storyName) {
		StoryName = storyName;
	}
	public String getStoryType() {
		return StoryType;
	}
	public void setStoryType(String storyType) {
		StoryType = storyType;
	}
	public String getCoverThumb() {
		return CoverThumb;
	}
	public void setCoverThumb(String coverThumb) {
		CoverThumb = coverThumb;
	}
	public String getStoryVideoUrl() {
		return StoryVideoUrl;
	}
	public void setStoryVideoUrl(String storyVideoUrl) {
		StoryVideoUrl = storyVideoUrl;
	}
	public String getCountLike() {
		return CountLike;
	}
	public void setCountLike(String countLike) {
		CountLike = countLike;
	}
	public String getCountRecommend() {
		return CountRecommend;
	}
	public void setCountRecommend(String countRecommend) {
		CountRecommend = countRecommend;
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

}
