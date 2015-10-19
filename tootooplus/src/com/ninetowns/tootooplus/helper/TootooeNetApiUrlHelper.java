package com.ninetowns.tootooplus.helper;

import com.ninetowns.library.helper.ConstantsHelper;

public class TootooeNetApiUrlHelper extends ConstantsHelper {
	/** 内外网切换, 只需该变该变量。若改为true, 为公网; 若改为false, 为内网 **/
	public static boolean PUBLIC_OR_IN_NET = true;
//	public static boolean PUBLIC_OR_IN_NET = false;
	/** 内网url。只有需要改变内网ip或者端口号时需要改变该变量 **/
	public static final String BASES_RTMP_IN_URL = "http://172.16.8.133:83/";

	/** 外网url。只有需要改变外网ip或者端口号时需要改变该变量 **/
	public static final String BASES_RTMP_PUBLIC_URL = "http://server.tootoojia.com/";
	public static final String CHAT_RTMP_SERVER_TYPE_CONSTANTS = "5";
	public static final String HTTP_SERVER_TYPE_CONSTANTS = "8";
	/** 获取聊天地址和后台数据地址---接口-----参数 **/
	public static final String CHAT_RTMP_HTTP_URL = "server/GetServerIPPortByType.htm?";
	public static final String CHAT_RTMP_HTTP_SERVER_TYPE = "ServerType";

	/** 私信列表请求页面大小*/
	public static final  int PRIVATE_LETTER_PAGESIZE = 15;
	
	public static final String USER_ID = "UserId";// UserId：用户Id (必填)
	public static final String CITY_NAME="CityName";
	public static final String PAGE_SIZE = "PageSize";// Pagesize：每页显示的条数 (必填)
	public static final String PAGE = "Page";// Page：当前第几页 (不填写默认第一页)
	public static final String PUBLISHER = "Publisher";// Publisher：发布者身份（1商家，2个人）
	public static final String SORT = "Sort";// Sort：排序方式（1最新，2最热，3推荐）
	public static final String CATEGORYID = "CategoryId";// CategoryId：子分类筛选
	public static final String CATEGORY_PARENTID = "CategoryParentId";// CategoryParentId：分类筛选
	/******* 心愿列表 *******/
	public static final String WISH_LIST_API = "story/wishList.htm?";
	/******* 我的心愿 *******/
	public static final String MY_WISH_LIST = "story/myWishList.htm?";

	/** 发送验证码---接口---参数 **/
	public static final String SEND_VERIFICATION_URL = "user/SendVerification.htm?";
	public static final String SEND_VERIFICATION_PHONENUMBER = "PhoneNumber";
	public static final String SEND_VERIFICATION_TYPE = "Type";
	public static final String SEND_VERIFICATION_BUSINESS_STATUS = "BusinessStatus";

	/** 手机号注册---接口---参数 **/
	public static final String REGIST_PHONE_MUMBER_URL = "user/RegistPhoneNumber.htm?";
	public static final String REGIST_PHONE_NUM = "PhoneNumber";
	public static final String REGIST_PHONE_VERIFICATION = "Verification";
	public static final String REGIST_PHONE_PASSWORD = "Password";
	public static final String REGIST_PHONE_NAME = "UserName";
	public static final String REGIST_PHONE_ONLY_CODE = "PhoneOnlyCode";
	public static final String REGIST_PHONE_BUSINESS_STATUS = "BusinessStatus";

	/** 手机登录---接口---参数 **/
	public static final String CHECK_LOGIN_URL = "user/CheckLogin.htm?";
	public static final String CHECK_LOGIN_PARAM_SOURCE = "Source";
	public static final String CHECK_LOGIN_PARAM_RELATIONID = "RelationId";
	public static final String CHECK_LOGIN_PARAM_RELATIONNAME = "RelationName";
	public static final String CHECK_LOGIN_PARAM_LOGOURL = "LogoUrl";
	public static final String CHECK_LOGIN_PARAM_PHONENUMBER = "PhoneNumber";
	public static final String CHECK_LOGIN_PARAM_PASSWORD = "Password";
	public static final String CHECK_LOGIN_PARAM_PHONE_ONLY_CODE = "PhoneOnlyCode";
	public static final String CHECK_LOGIN_PARAM_BUSINESS_STATUS = "BusinessStatus";

	/** 重置密码---接口---参数 **/
	public static final String RESET_PWD_MOBILE_URL = "user/ResetPasswordMobile.htm?";
	public static final String RESET_PWD_MOBILE_PHONENUMBER = "PhoneNumber";
	public static final String RESET_PWD_MOBILE_VERIFICATION = "Verification";
	public static final String RESET_PWD_MOBILE_PASSWORD = "Password";

	/** 创建活动第一步---接口---参数 **/
	public static final String CREATE_ACT_FIRST_STEP_URL = "activitys/activityCreatePre.htm?";
	public static final String CREATE_ACT_FIRST_STEP_USERID = "UserId";

	/** 创建活动第三步提交---接口---参数 **/
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_URL = "activitys/activityInfoCreate.htm?";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_USERID = "UserId";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_ACTID = "ActivityId";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_TYPE = "Type";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_COUNT_PARTICIPANT = "CountParticipant";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_DATE_REGISTER_START = "DateRegisterStart";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_DATE_REGISTER_END = "DateRegisterEnd";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_LOCATION = "Location";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_AREAID = "AreaId";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_DISPATCHTYPE = "DispatchingType";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_EXPRESS_NAME = "ExpressName";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_DATE_START = "DateStart";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_DATE_END = "DateEnd";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_PLACE = "Place";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_PLACE_MAP = "PlaceMap";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_SUPPLIERNAME = "SupplierName";
	public static final String CREATE_ACT_THIRD_STEP_SUBMIT_ACT_NAME = "ActivityName";

	/** 获取城市列表---接口 **/
	public static final String SELECT_AREA_LIST_URL = "userAddress/AllCity.htm?";

	/** 个人中心---接口---参数 **/
	public static final String MINE_URL = "user/GetUserInfo.htm?";
	public static final String MINE_USERID = "UserId";
	public static final String MINE_MY_USERID = "MyUserId";

	/** 个人详情---接口---参数 **/
	public static final String PERSON_INFO_URL = "user/GetUserCenterInfo.htm?";
	public static final String PERSON_INFO_USERID = "UserId";

	/** 修改密码---接口---参数 **/
	public static final String CHANGE_PWD_URL = "user/ChangePasswordMobile.htm?";
	public static final String CHANGE_PWD_USERID = "UserId";
	public static final String CHANGE_PWD_OLD_PWD = "OldPassword";
	public static final String CHANGE_PWD_NEW_PWD = "NewPassword";

	/** 取消关注---接口---参数 **/
	public static final String CANCEL_FOLLOW_URL = "friend/Cancelfriend.htm?";
	public static final String CANCEL_FOLLOW_USERID = "UserId";
	public static final String CANCEL_FOLLOW_FOLLOWID = "FollowId";

	/** 添加关注---接口---参数 **/
	public static final String ADD_FOLLOW_URL = "friend/AddFriend.htm?";
	public static final String ADD_FOLLOW_USERID = "UserId";
	public static final String ADD_FOLLOW_FOLLOWID = "FollowId";

	/** 点评故事---接口---参数 **/
	public static final String REMARK_STORY_URL = "story/myCommentList.htm?";
	public static final String REMARK_STORY_USERID = "UserId";
	public static final String REMARK_STORY_PAGESIZE = "PageSize";
	public static final String REMARK_STORY_PAGE = "Page";

	/** 关注列表---接口---参数 **/
	public static final String FOLLOW_LIST_URL = "friend/GetConcernList.htm?";
	public static final String FOLLOW_LIST_USERID = "UserId";
	public static final String FOLLOW_LIST_OTHER_USERID = "OtherUserId";

	/** 粉丝列表---接口---参数 **/
	public static final String FANS_LIST_URL = "friend/GetFansList.htm?";
	public static final String FANS_LIST_USERID = "UserId";
	public static final String FANS_LIST_OTHER_USERID = "OtherUserId";

	/*** 故事心愿创建 */
	public static final String STORYCREATEAPI = "story/storyCreate.htm?";
	/*** 创建封面图 ***/
	public static final String STORY_NAME_UPDATE = "story/storyNameUpdate.htm?";
	public static final String COVER_THUMB = "CoverThumb";
	public static final String COVER_IMG = "CoverImg";
	public static final String STORY_TYPE = "StoryType";
	public static String STORYCREATE_PAGE_VIDEO_URL = "PageVideoUrl";
	public static String STORY_VIDEO_URL = "StoryVideoUrl";
	public static final String RESET_PWD_MOBILE_BUSINESS_STATUS = "BusinessStatus";

	/** 故事页创建--接口------参数 **/
	public static final String STORYCREATE_PAGE_API = "story/storyPageAdd.htm?";
	public static final String STORYCREATE_STORYID = "StoryId";
	public static final String STORYCREATE_UPDATE_STORYID = "UpdateStoryId";
	public static final String STORYCREATE_PAGE_TYPE = "PageType";
	public static final String STORYCREATE_PAGE_CONTENT = "PageContent";
	public static final String STORYCREATE_PAGE_IMAGE = "PageImg";
	public static final String STORYCREATE_THUMBIMAGE = "PageImgThumb";
	public static final String FontSize = "FontSize";// ：字体大小
	/**** 商品分类 ****/
	public static final String GOODS_CATEGORY = "story/goodsCategory.htm?";
	/**** 文件上传 ***********/
	public static final String UPLOAD_FIRLE = "FileUpload/FileUpload.htm?";
	public static final String UPLOAD_FILE_USERID = "UserId";
	public static final String UPLOAD_FILE_APPLICATIONID = "ApplicationId";
	public static final String UPLOAD_FILE_TYPE = "Type";
	/***** 推荐故事文件上传 *******/
	public static final String UPLOAD_RECDOMMMEND_FIRLE = "FileUpload/FileUploadScenario.htm?";
	public static final String UPLOAD_RECDOMMMEND_FILE_SCENARIOTYPE = "ScenarioType";

	public static final String UPLOAD_FIRLE_TYPE = "Type";
	public static final String UPLOAD_FIRLE_TYPE_PHOTO = "1";
	public static final String UPLOAD_FIRLE_TYPE_AIDEO = "2";
	public static final String UPLOAD_FIRLE_TYPE_VIDEO = "3";
	public static final String UPLOAD_FIRLE_TYPE_PHOTOlIST = "4";
	public static final String UPLOAD_FIRLE_WIDTH = "Width";
	public static final String UPLOAD_FIRLE_HEIGHT = "Height";
	public static final String UPLOAD_FIRLE_FLAG = "Flag";
	public static final String UPLOAD_FIRLE_FILE1 = "File1";
	public static final String UPLOAD_FIRLE_FILE2 = "File2";
	public static final String UPLOAD_FIRLE_FLAG_DEBUG = "1";// 是调试
	public static final String UPLOAD_FIRLE_FLAG_NO_DEBUG = "0";// 不调试
	/** 修改头像---接口-----参数 **/
	public static final String CHANGE_LOGO_URL = "user/ChangeLogoUrlMobile.htm?";
	public static final String CHANGE_LOGO_USERID = "UserId";
	public static final String CHANGE_LOGO_LOGOURL = "LogoUrl";
	/******* 采集相片 *********/
	public static final String GATHER_IMAGES = "story/gatherImages.htm?";
	public static final String URL = "Url";
	/***** 编辑故事标题---接口 ----参数 ******/
	public static final String STORYCREATE_NAME_UPDATE = "story/storyNameUpdate.htm?";
	public static final String STORYCREATE_STORY_NAME = "StoryName";
	public static final String STORYTYPE = "StoryType";

	/**** 修改故事页 *******/
	public static final String STORYCREATE_PAGE_UPDATE = "story/storyPageUpdate.htm?";
	public static final String STORYCREATE_PAGE_UPDATE_PageImgThumb = "PageImgThumb";
	public static final String STORYCREATE_PAGEID = "PageId";
	public static final String STORYCREATE_PAGE_UPDATE_PageVideoUrl = "PageVideoUrl";
	public static final String STORYCREATE_PAGE_UPDATE_RecordId = "RecordId";
	public static final String STORYCREATE_PAGE_UPDATE_PageDesc = "PageDesc";
	public static final String STORYCREATE_PAGE_UPDATE_PageImg = "PageImg";
	/**
	 * 排序
	 */
	public static final String PAGE_SORT = "story/storyPageSort.htm?";
	public static final String PageList = "PageList";
	public static final String StoryList = "StoryList";
	/*** 故事发布 **/
	public static final String STORYCREATE_PAGE_PUSH = "story/storyPublish.htm?";
	public static final String STORYCREATE_PAGE_PUSH_UPDATE_STORYID = "UpdateStoryId";
	public static final String CATEGORY_PARENT_ID = "CategoryParentId";// 商品父分类
	public static final String COUNTRECOMMEND = "CountRecommend";// 点评故事 推荐指数
	
	public static final String CATE_GORYID = "CategoryId";// 商品子分类 (必填)
	public static final String SHOPPING_URL = "ShoppingUrl";// 导购链接

	/******* 保存草稿 **********/
	public static final String STORY_DETAIL_SAVE_TO_DRAFT = "story/storySaveToDraft.htm?";

	/*********** 心愿详情 ****************/
	public static final String WISH_DETAIL_API = "story/storyDetail.htm?";
	/******* 白吃心愿用户列表 ********/
	public static final String PHOTO_WISH_LIST = "story/wishFreeUserList.htm?";
	/********* 我要白吃 ***********/
	public static final String ADD_I_WANT_FREE = "story/addIWantFree.htm?";
	/************ 活动首页 ****************/
	public static final String ACTIVITYS_MY_FREE_ACTIVITYSE = "activitys/activityList.htm?";
	public static final String STATUS = "Status";// 审核状态 1待审核2审核通过-1审核不通过，这里传值2
													// (必填)
	/****** 创建活动第二步 ******/
	public static final String ACTIVITY_CREATE_PRE = "activitys/activityStoryCreatePre.htm?";
	/****** 我的活动详情 *******/
	public static final String ACTIVITY_DETAIL = "activitys/ActivityDetail.htm?";
	/********** 故事或者草稿修改 ***********/
	public static final String EDITEXT_WISH = "story/storyUpdatePre.htm?";
	/********** 我的心愿删除 *************/
	public static final String DEL_STORY = "story/storyDelete.htm?";
	/******** 收藏活动的人数 ***********/
	public static final String COLLECT_COUNT = "activitys/activityCollentUserList.htm?";
	/******** 参与活动的人员 ********/
	public static final String GROUP_MAN = "activitys/activityGroupList.htm?";

	/** 证件提交认证---接口---参数 **/
	public static final String SHOP_CERT_URL = "user/SubmitUserAuthenticate.htm?";
	public static final String SHOP_CERT_USERID = "UserId";
	public static final String SHOP_CERT_VERIFY_TYPE = "VerifyType";
	public static final String SHOP_CERT_IDCARD_FRONT = "IDCardFront";
	public static final String SHOP_CERT_IDCARD_REVERSE = "IDCardReverse";
	public static final String SHOP_CERT_BUSINESS_LICENCE = "BusinessLicence";
	public static final String SHOP_CERT_FOOD_PRODUCTION_LICENCE = "FoodProductionLicence";
	public static final String SHOP_CERT_FOOD_HYGIENE_LICENCE = "FoodHygieneLicence";
	public static final String SHOP_CERT_FOOD_CIRCULATION_PERMITS = "FoodCirculationPermits";

	/** 修改封面图---接口---参数 **/
	public static final String CHANGE_COVER_IMAGE_URL = "user/ChangeCover.htm?";
	public static final String CHANGE_COVER_IMAGE_USERID = "UserId";
	public static final String CHANGE_COVER_IMAGE_COVER = "CoverImage";

	/** 修改活动第一步---接口---参数 **/
	public static final String UPDATE_ACT_FIRST_STEP_URL = "activitys/activityUpdatePre.htm?";
	public static final String UPDATE_ACT_FIRST_STEP_USERID = "UserId";
	public static final String UPDATE_ACT_FIRST_STEP_ACTID = "ActivityId";
	public static final String UPDATE_ACT_FIRST_STEP_AGAIN_RELEASED = "OnceAgainReleased";
	/*********** 活动修改 ****************/
	public static final String ACTIVITY_STORY_UPDATEPRE = "activitys/activityStoryUpdatePre.htm?";

	/** 修改活动第三步---接口---参数 **/
	public static final String ACTIVITY_INFO_UPDATE_PRE_URL = "activitys/activityInfoUpdatePre.htm?";
	public static final String ACTIVITY_INFO_UPDATE_PRE_ACT_ID = "ActivityId";
	public static final String ACTIVITY_INFO_UPDATE_PRE_USERID = "UserId";
	public static final String ACTIVITY_INFO_UPDATE_PRE_AGAIN_RELEASED = "OnceAgainReleased";

	/** 修改活动提交---接口---参数 **/
	public static final String ACTIVITY_INFO_UPDATE_URL = "activitys/activityInfoUpdate.htm?";
	public static final String ACTIVITY_INFO_UPDATE_STORYID = "StoryId";

	/** 版本升级---接口---参数 **/
	public static final String VERSION_UP_GRADE_URL = "notice/versionUpgrade.htm?";
	public static final String VERSION_UP_GRADE_TYPE = "Type";
	public static final String VERSION_UP_GRADE_VERSION = "Version";

	/************* 群聊 ****************/
	public static final String CHAT_GROUP = "group/AddGroup.htm?";
	/*********** 体验点评列表 ************/
	public static final String COMMENT_LIST = "activitys/activityCommentList.htm?";
	/*********** 删除心愿页 **************/
	public static final String DEL_PAGE_STORY = "story/storyPageDelete.htm?";
	public static final String PAGE_ID = "PageId";

	/** 修改昵称----接口---参数 **/
	public static final String CHANGE_USERNAME_URL = "user/ChangeUserNameMobile.htm?";
	public static final String CHANGE_USERNAME_USERID = "UserId";
	public static final String CHANGE_USERNAME_USERNAME = "UserName";

	public static final String AGAIN_PUSH = "OnceAgainReleased";

	/** 创建活动或者心愿时判断用户审核和心愿情况---接口---参数 **/
	public static final String VERIFY_PERMISSIONS_URL = "user/verifyPermissions.htm?";
	public static final String VERIFY_PERMISSIONS_TYPE = "Type";
	public static final String VERIFY_PERMISSIONS_USERID = "UserId";

	public static final String STORY_NAME = "StoryName";
	/**
	 * 首页点评
	 */
	public static final String FREE_COMMENT = "story/freeCommentList.htm?";
	/**
	 * 我的活动
	 */
	public static final String MY_ACT_FREE_COMMENT = "activitys/myActivityList.htm?";

	/** 我的白吃团---接口---参数 **/
	public static final String MY_FREE_GROUP_URL = "group/GetUserGroupList.htm?";
	public static final String MY_FREE_GROUP_USERID = "UserId";
	public static final String MY_FREE_GROUP_PAGE_SIZE = "PageSize";
	public static final String MY_FREE_GROUP_PAGE = "Page";
	public static final String MY_FREE_GROUP_TYPE = "Type";

	/** 已招募团员---接口---参数 **/
	public static final String JOIN_GROUP_MEMEBER_URL = "group/GetMyGroupMemberList.htm?";
	public static final String JOIN_GROUP_MEMEBER_GROUPID = "GroupId";
	/**
	 * 点击收藏
	 * 
	 */
	public static final String CLICK_COLLECT = "activitys/addActivityCollent.htm?";
	public static final String JOIN_GROUP_MEMEBER_USERID = "UserId";

	/** 删除组成员 **/
	public static final String DEL_GROUP_MEMBER_URL = "group/DelGroupMember.htm?";
	public static final String DEL_GROUP_MEMBER_GROUPID = "GroupId";
	public static final String DEL_GROUP_MEMBER_USERID = "UserId";
	/**
	 * 我要点赞
	 */
	public static final String WANT_TO_COMMENT = "story/addCommentLike.htm?";

	/**
	 * 用户评论列表
	 */
	public static final String COMMENT_LIST_USERS = "comment/commentList.htm?";
	/**
	 * 创建活动的id
	 */
	public static final String ACTIVITY_CREATE_ID = "create_activity_id";

	public static final String STORY_CREATE_ID = "story_activity_id";
    
    /**招募团员页一键组团---接口---参数**/
    public static final String JOIN_MEM_SUBMIT_GROUP_URL = "group/SubmitGroup.htm?";
    public static final String JOIN_MEM_SUBMIT_GROUP_USERID = "UserId";
    public static final String JOIN_MEM_SUBMIT_GROUP_GROUPID = "GroupId";
    public static final String JOIN_MEM_SUBMIT_GROUP_ACTIVITYID = "ActivityId";
    public static final String JOIN_MEM_SUBMIT_GROUP_PRIORITY_CODE = "PriorityCode";
    public static final String JOIN_MEM_SUBMIT_GROUP_ADDRESS_ID = "AddressId";
    
    /**收货地址列表---接口---参数**/
    public static final String ADDRESS_LIST_URL = "userAddress/ShowAddressList.htm?";
    public static final String ADDRESS_LIST_USERID = "UserId";
    //两处调用该接口，一处传ActivityId这个参数，一处不传
    public static final String ADDRESS_LIST_ACTID = "ActivityId";
    
    /**配送区域---接口---参数**/
    public static final String  ADDRESS_GOODS_AREA_LIST_URL = "userAddress/GoodsAreaList.htm?";
    public static final String ADDRESS_GOODS_AREA_LIST_ACTID = "ActivityId";
    
    /** 用户新增收货地址---接口-----参数 **/
    public static final String USER_ADD_ADDRESS_URL = "userAddress/AddAddress.htm?";
    public static final String USER_ADD_ADDRESS_USERID = "UserId";
    public static final String USER_ADD_ADDRESS_REALNAME = "RealName";
    public static final String USER_ADD_ADDRESS_PHONENUMBER = "PhoneNumber";
    public static final String USER_ADD_ADDRESS_DETAILS = "DetailedAddress";
    public static final String USER_ADD_ADDRESS_POSTCODE = "Postcode";
    public static final String USER_ADD_ADDRESS_PROVINCEID = "ProvinceId";
    public static final String USER_ADD_ADDRESS_CITYID = "CityId";
    public static final String USER_ADD_ADDRESS_DISTRICTID = "DistrictId";

    /** 用户修改收货地址---接口-----参数 **/
    public static final String USER_CHANGE_ADDRESS_URL = "userAddress/ChangeAddress.htm?";
    public static final String USER_CHANGE_ADDRESS_USERID = "UserId";
    public static final String USER_CHANGE_ADDRESS_ADDRESSID = "AddressId";
    public static final String USER_CHANGE_ADDRESS_REALNAME = "RealName";
    public static final String USER_CHANGE_ADDRESS_PHONENUMBER = "PhoneNumber";
    public static final String USER_CHANGE_ADDRESS_DETAILS = "DetailedAddress";
    public static final String USER_CHANGE_ADDRESS_POSTCODE = "Postcode";
    public static final String USER_CHANGE_ADDRESS_PROVINCEID = "ProvinceId";
    public static final String USER_CHANGE_ADDRESS_CITYID = "CityId";
    public static final String USER_CHANGE_ADDRESS_DISTRICTID = "DistrictId";

    /** 用户删除收货地址---接口-----参数**/
    public static final String USER_DELETE_ADDRESS_URL = "userAddress/DeleteAddress.htm?";
    public static final String USER_DELETE_ADDRESS_USERID = "UserId";
    public static final String USER_DELETE_ADDRESS_ADDRESSID = "AddressId";
    
    /**创建群主----接口----参数**/
    public static final String ACT_DETAILS_CREATE_GROUP_URL ="group/CreateGroup.htm?";
    public static final String ACT_DETAILS_CREATE_GROUP_ACT_ID = "ActivityId";
    public static final String ACT_DETAILS_CREATE_GROUP_GROUP_NAME = "GroupName";
    public static final String ACT_DETAILS_CREATE_GROUP_MEMBER = "Member";
    public static final String ACT_DETAILS_CREATE_GROUP_ADMIN = "Admin";
    public static final String ACT_DETAILS_CREATE_GROUP_LATITUDE = "Latitude";
    public static final String ACT_DETAILS_CREATE_GROUP_LONGITUDE = "Longitude";
    
    public static final String ACT_DETAILS_CREATE_GROUP_SOURCE = "Source";
    /**
     * 搜索关键字
     */
    public static final String HOT_SEARCH = "activitys/getHotSearchList.htm?";
    /**
     * 	点评或者心愿
     */
    public final static String WISH_COMMENT_SHARE="story/share?StoryId=";
    /**
     * 	活动分享
     */
    public final static String ACTIVITY_SHARE="activitys/share?ActivityId=";
    /**个人首页活动---接口**/
    public final static String INDEX_MY_FREE_ACTIVITY = "activitys/myFreeActivitiesE.htm?";
    
    /**退群---接口---参数**/
    public final static String ALL_MEMBER_EXIT_GROUP_URL = "group/DeleteGroupMember.htm?";
    public final static String ALL_MEMBER_EXIT_GROUP_GROUPID = "GroupId";
    public final static String ALL_MEMBER_EXIT_GROUP_USERID = "UserId";
    
    /**
     * 活动提醒
     */
    public final static String ACTIVITY_SUCCESS_NOTIFY = "activitys/activitySuccessNotify.htm?";
    
    /**分享回调---接口---参数**/
    public final static String SHARE_CALL_BACK_URL = "user/ShareCallback.htm?";
    public final static String SHARE_CALL_BACK_USERID = "UserId";
    public final static String SHARE_CALL_BACK_STORYID = "StoryId";
    public final static String SHARE_CALL_BACK_SOURCE = "Source";
    
    /**
     * 浏览数据的接口
     */
    public final static String BROWSER = "user/Conversion.htm?";
    /**
     * 面对面输入邀请
     */
    public final static String FACETOFACE="group/FaceToFace.htm?";
    public final static String ACTIVITY_CODE="ActivityCode";	
    
    /**
     *点击去购买的接口
     */
    public final static String GOTOBUY = "user/PageViews.htm?";
    public final static String CONTACTLIST = "Contact/ShowContactList.htm?";
//    http://www.tootoojia.com/Contact/ShowContactList?UserId=1
    /** 用户联系地址---接口-----参数**/
    public static final String DELETE_INOF_PHONE = "Contact/deleteContact.htm?";
    /*****添加*****/
    public static final String ADD_INOF_PHONE = "Contact/addContact.htm?";
    /*****更改*****/
    public static final String UPDATE_INOF_PHONE = "Contact/ChangeContact?";
    
    /*****确认加入*****/
    public static final String SURE_GO = "group/FaceToFaceAddGroup.htm?";
    
   
//    http://www.tootoojia.com/Contact/deleteContact?UserId=1&ContactId=1
}