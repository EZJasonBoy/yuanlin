package com.ninetowns.tootooplus.bean;

import java.io.Serializable;
import java.util.List;

public class StoryDetailListBean implements Serializable {
	private String StoryName;// ：故事名称
	private String PageList;// ：故事页列表
	private String PageId;// ：故事页Id
	private String PageName;// ：故事页标题
	private String PageType;// ：故事页类型：1,文字，2图片，3视频，4直播
	private String PageImg;// ：故事页图片
	private String PageContent;// ：故事页文字
	private String PageSortOrder;// ：故事页的显示顺序
	private String CreateDate;// ：创建日期
	private String CreateUser;// ：创建人对象
	private String UserId;// ：创建人用户Id
	private String LogoUrl;// ：创建人头像
	private String UserName;// ：创建人昵称
	private String storyImage;// 官方故事图片
	private String IsOfficial;
	private String goodsName;
	private String LikeCount;
	private String CommentCount;
	private List<Integer> list;
	private String Template;// 未应用模版1.android 2 ios
	private int ItemIndex;// 单元序列号从0开始
	private int ElementType;// 元素类型 区分模版 1为大元素 2为小元素
	private int Location = 1;// 位置从1开始
	private String fileUrl;
	private String DragSquareImg;// 正方行
	private String DragRectangleImg;// 长方形
	private String PageImgThumbBigRectangle;//4:3
	private String PageImgThumbBigSquare;//大正方形1：1
	private String DefaultType;
	

	public String getDefaultType() {
		return DefaultType;
	}

	public void setDefaultType(String defaultType) {
		DefaultType = defaultType;
	}

	public String getPageImgThumbBigRectangle() {
		return PageImgThumbBigRectangle;
	}

	public void setPageImgThumbBigRectangle(String pageImgThumbBigRectangle) {
		PageImgThumbBigRectangle = pageImgThumbBigRectangle;
	}

	public String getPageImgThumbBigSquare() {
		return PageImgThumbBigSquare;
	}

	public void setPageImgThumbBigSquare(String pageImgThumbBigSquare) {
		PageImgThumbBigSquare = pageImgThumbBigSquare;
	}

	private String PageImgThumbRectangle;
	private String PageImgThumbSquare;
	public String getPageImgThumbRectangle() {
		return PageImgThumbRectangle;
	}

	public void setPageImgThumbRectangle(String pageImgThumbRectangle) {
		PageImgThumbRectangle = pageImgThumbRectangle;
	}

	public String getPageImgThumbSquare() {
		return PageImgThumbSquare;
	}

	public void setPageImgThumbSquare(String pageImgThumbSquare) {
		PageImgThumbSquare = pageImgThumbSquare;
	}

	private String UpdateStoryId;
	private String StoryId;

	public String getUpdateStoryId() {
		return UpdateStoryId;
	}

	public void setUpdateStoryId(String updateStoryId) {
		UpdateStoryId = updateStoryId;
	}

	public String getStoryId() {
		return StoryId;
	}

	public void setStoryId(String storyId) {
		StoryId = storyId;
	}

	private String ListCoverImg;
	/** 文本的 字体大小 1是大字体 标题 2是小字体 内容 */
	private int FontSize;

	public int getFontSize() {
		return FontSize;
	}

	public void setFontSize(int fontSize) {
		FontSize = fontSize;
	}

	// -----------------------------封面信息-------------------------------------
	/** 故事封面类型 2图片，3视频 */
	private int StoryType;
	/** 故事封面缩略图 */
	private String CoverThumb;
	/** 故事封面图片或直播录播封面图地址 */
	private String CoverImg;
	/** 故事封面录播视频地址 */
	private String StoryVideoUrl;

	/** 故事封面类型 2图片，3视频 */
	public int getStoryType() {
		return StoryType;
	}

	public void setStoryType(int storyType) {
		StoryType = storyType;
	}

	/** 故事封面缩略图 */
	public String getCoverThumb() {
		return CoverThumb;
	}

	public void setCoverThumb(String coverThumb) {
		CoverThumb = coverThumb;
	}

	/** 故事封面图片或直播录播封面图地址 */
	public String getCoverImg() {
		return CoverImg;
	}

	public void setCoverImg(String coverImg) {
		CoverImg = coverImg;
	}

	/** 故事封面录播视频地址 */
	public String getStoryVideoUrl() {
		return StoryVideoUrl;
	}

	public void setStoryVideoUrl(String storyVideoUrl) {
		StoryVideoUrl = storyVideoUrl;
	}

	public String getListCoverImg() {
		return ListCoverImg;
	}

	public void setListCoverImg(String listCoverImg) {
		ListCoverImg = listCoverImg;
	}

	public String getDragSquareImg() {
		return DragSquareImg;
	}

	public void setDragSquareImg(String dragSquareImg) {
		DragSquareImg = dragSquareImg;
	}

	public String getDragRectangleImg() {
		return DragRectangleImg;
	}

	public void setDragRectangleImg(String dragRectangleImg) {
		DragRectangleImg = dragRectangleImg;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public StoryDetailListBean() {
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	/** 实体类在集合中的位置 */
	private int position;
	public int id;//
	/** 标题 */
	public String text = "";

	/** 单元格的 水平位置 */
	public int cellX;
	/** 单元格竖直位置 */
	public int cellY;
	/** 2为最小单位 单元格宽度 */
	public int spanX;
	/** 2为最小单位 单元格高度 */
	public int spanY;

	// 实体类中如果存在数据库中没有的字段,需要在这个字段中加上@Transient注释，不会被映射，也就不会被持久化

	public String getTemplate() {
		return Template;
	}

	public void setTemplate(String template) {
		Template = template;
	}

	/** 用户vip等级 **/
	private String userVipGrade;

	public int getItemIndex() {
		return ItemIndex;
	}

	public void setItemIndex(int itemIndex) {
		ItemIndex = itemIndex;
	}

	/**
	 * 
	 * @Title: getElementType
	 * @Description: 元素类型 区分模版 1为大元素 2为小元素
	 * @param
	 * @return
	 * @throws
	 */
	public int getElementType() {
		return ElementType;
	}

	public void setElementType(int elementType) {
		ElementType = elementType;
	}

	public int getLocation() {
		return Location;
	}

	public void setLocation(int location) {
		Location = location;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

	public String getLikeCount() {
		return LikeCount;
	}

	public void setLikeCount(String likeCount) {
		LikeCount = likeCount;
	}

	public String getCommentCount() {
		return CommentCount;
	}

	public void setCommentCount(String commentCount) {
		CommentCount = commentCount;
	}

	private String PageDesc;

	public String getPageDesc() {
		return PageDesc;
	}

	public void setPageDesc(String pageDesc) {
		PageDesc = pageDesc;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getIsOfficial() {
		return IsOfficial;
	}

	public void setIsOfficial(String isOfficial) {
		IsOfficial = isOfficial;
	}

	private String PageImgThumb;

	public String getPageImgThumb() {
		return PageImgThumb;
	}

	public void setPageImgThumb(String pageImgThumb) {
		PageImgThumb = pageImgThumb;
	}

	public String getStoryName() {
		return StoryName;
	}

	public void setStoryName(String storyName) {
		StoryName = storyName;
	}

	public String getPageList() {
		return PageList;
	}

	public void setPageList(String pageList) {
		PageList = pageList;
	}

	public String getPageId() {
		return PageId;
	}

	public void setPageId(String pageId) {
		PageId = pageId;
	}

	public String getPageName() {
		return PageName;
	}

	public void setPageName(String pageName) {
		PageName = pageName;
	}

	public String getPageType() {
		return PageType;
	}

	public void setPageType(String pageType) {
		PageType = pageType;
	}

	public String getPageImg() {
		return PageImg;
	}

	public void setPageImg(String pageImg) {
		PageImg = pageImg;
	}

	public String getPageContent() {
		return PageContent;
	}

	public void setPageContent(String pageContent) {
		PageContent = pageContent;
	}

	public String getPageSortOrder() {
		return PageSortOrder;
	}

	public void setPageSortOrder(String pageSortOrder) {
		PageSortOrder = pageSortOrder;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public String getCreateUser() {
		return CreateUser;
	}

	public void setCreateUser(String createUser) {
		CreateUser = createUser;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getLogoUrl() {
		return LogoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		LogoUrl = logoUrl;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getStoryImage() {
		return storyImage;
	}

	public void setStoryImage(String storyImage) {
		this.storyImage = storyImage;
	}

	public String getUserVipGrade() {
		return userVipGrade;
	}

	public void setUserVipGrade(String userVipGrade) {
		this.userVipGrade = userVipGrade;
	}

	@Override
	public String toString() {
		return "StoryDetailListBean [StoryName=" + StoryName + ", PageList="
				+ PageList + ", PageId=" + PageId + ", PageName=" + PageName
				+ ", PageType=" + PageType + ", PageImg=" + PageImg
				+ ", PageContent=" + PageContent + ", PageSortOrder="
				+ PageSortOrder + ", CreateDate=" + CreateDate
				+ ", CreateUser=" + CreateUser + ", UserId=" + UserId
				+ ", LogoUrl=" + LogoUrl + ", UserName=" + UserName
				+ ", storyImage=" + storyImage + ", IsOfficial=" + IsOfficial
				+ ", goodsName=" + goodsName + ", LikeCount=" + LikeCount
				+ ", CommentCount=" + CommentCount + ", list=" + list
				+ ", Template=" + Template + ", ItemIndex=" + ItemIndex
				+ ", ElementType=" + ElementType + ", Location=" + Location
				+ ", fileUrl=" + fileUrl + ", DragSquareImg=" + DragSquareImg
				+ ", DragRectangleImg=" + DragRectangleImg + ", UpdateStoryId="
				+ UpdateStoryId + ", StoryId=" + StoryId + ", ListCoverImg="
				+ ListCoverImg + ", FontSize=" + FontSize + ", StoryType="
				+ StoryType + ", CoverThumb=" + CoverThumb + ", CoverImg="
				+ CoverImg + ", StoryVideoUrl=" + StoryVideoUrl
				+ ", cellModule=" + ", position=" + position + ", id=" + id
				+ ", text=" + text + ", cellX=" + cellX + ", cellY=" + cellY
				+ ", spanX=" + spanX + ", spanY=" + spanY + ", userVipGrade="
				+ userVipGrade + ", PageDesc=" + PageDesc + ", PageImgThumb="
				+ PageImgThumb + "]";
	}

}