package com.ninetowns.tootooplus.bean;

import java.io.Serializable;

public class ConVertBean implements Serializable {
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

	public String getCoverImg() {
		return CoverImg;
	}

	public void setCoverImg(String coverImg) {
		CoverImg = coverImg;
	}

	public String getStoryVideoUrl() {
		return StoryVideoUrl;
	}

	public void setStoryVideoUrl(String storyVideoUrl) {
		StoryVideoUrl = storyVideoUrl;
	}

	public String getRecordId() {
		return RecordId;
	}

	public void setRecordId(String recordId) {
		RecordId = recordId;
	}

	private String StoryId;// ：故事Id (必填)
	private String StoryName;// ：故事名称
	private String StoryType;// ：故事封面类型：2图片，3视频 new
	private String CoverThumb;// ：故事封面缩略图 new
	private String CoverImg;// ：故事封面图片或直播录播封面图地址 new
	private String StoryVideoUrl;// ：故事封面录播视频地址 new
	private String RecordId;// ：录制Id new
	private String CategoryParentId;
	private String CountRecommend;//评论的指数
	public String getCountRecommend() {
		return CountRecommend;
	}

	public void setCountRecommend(String countRecommend) {
		CountRecommend = countRecommend;
	}

	public String getCategoryParentId() {
		return CategoryParentId;
	}

	public void setCategoryParentId(String categoryParentId) {
		CategoryParentId = categoryParentId;
	}

	public String getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(String categoryId) {
		CategoryId = categoryId;
	}

	public String getShoppingUrl() {
		return ShoppingUrl;
	}

	public void setShoppingUrl(String shoppingUrl) {
		ShoppingUrl = shoppingUrl;
	}

	private String CategoryId;
	private String ShoppingUrl;
	

}
