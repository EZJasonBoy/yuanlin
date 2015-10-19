package com.ninetowns.tootooplus.util;


public interface INetConstanst {

	static final int MAIN_CONNECT_DISCONNECT = 0x01;
	static final int MAIN_RECEIVED_MESSAGE = 0x02;
	static final int UPLOAD_IMAGE_SUCCESS = 0x03;
	static final int UPLOAD_AUDIO_SUCCESS = 0x04;
	static final int UPLOAD_VIDEO_SUCCESS = 0x05;
	
	static final String USER_ID="UserId";
	static final String PHONE_NUM="PhoneNumber";
	static final String VERTIFY_CODE="Verification";
	static final String INVITATION_CODE="InviteCode";
	/** 
	* @Fields PRIORITYCODE_NOTUSED_TYPE : 未使用优先码
	*/ 
	static final String PRIORITYCODE_NOTUSED_TYPE="1";
	/** 
	* @Fields PRIORITYCODE_HASUSED_TYPE : 已使用优先码
	*/ 
	static final String PRIORITYCODE_HASUSED_TYPE="2";
	/** 
	* @Fields PRIORITYCODE_HASPRESENTED_TYPE : 已赠送
	*/ 
	static final String PRIORITYCODE_HASPRESENTED_TYPE="3";
	
	
	
//	static final String MYUSER_ID="MyUserId";//自己的用户 id
	/** 
	* @Fields RECORD_TIMER : 录制 计时器  
	*/ 
	static final int RECORD_TIMER= 0x06;

	static final int CHOICE_PHOTO = 0x10; // 用于标识从用户相册选择照片
	static final int TAKE_PHOTO = 0x11; // 用于标识拍照
//	static final int UPLOAD_VIDEO = 0x12;// 选择视频文件
	static final int REQUESTCODE_RECORD_VIDEO = 0x13;// 选择视频文件

	/**
	 * @Fields PRIVATELETTER_LIST_URL : 私信列表
	 */
	static final String PRIVATELETTER_LIST_URL = "mail/GetMailUserList.htm?";
	
	/** 
	* @Fields PRIORITYCODE_LIST_URL : 优先码
	*/ 
	static final String PRIORITYCODE_LIST_URL = "priorityCode/myPriorityCodeList.htm?";
	/**
	 * @Fields GET_VETTIFYCODE : 获取验证码
	 */
	static final String GET_VETTIFYCODE = "captcha/Captcha.htm?";
	/**
	 * @Fields COMMIT_INVITATION : 提交邀请
	 */
	static final String COMMIT_INVITATION = "captcha/SubmitCaptcha.htm?";
	/**
	 * @Fields NOTICE_LIST_URL : 通知列表
	 */
	static final String NOTICE_LIST_URL = "notice/noticeList.htm?";
	/**
	 * @Fields ACTIVITY_CHATGROUP_LIST_URL : 活动聊天群组列表接口
	 */
	static final String ACTIVITY_CHATGROUP_LIST_URL = "group/GetActivityGroupList.htm?";

	/** 
	* @Fields SINGLE_LONGIN_TYPE :表示同一账号在别的客户端登录 
	*/ 
	static final String SINGLE_LONGIN_NOTICE="kick";
	/** 
	* @Fields MY_INVATATION : 我的邀请列表
	*/ 
	static final String MY_INVATATION = "user/GetInviteUser.htm?";
	
	/** 
	* @Fields WISH_CHATGROUP_LIST_URL :心愿活动群组接口 
	*/ 
	static final String WISH_CHATGROUP_LIST_URL = "group/GetWishGroupList.htm?";
	static final int PAGE_SIZE = 10;

	/**
	 * @Fields CHAT_RTMP_URL : 长连接地址
	 */
//	final static String CHAT_RTMP_URL = "rtmp://172.16.8.115:1938/chatroom/";

	/**
	 * @Fields UPLOAD_URL :上传文件
	 */
	static final String UPLOAD_URL = "FileUpload/FileUpload.htm";
	/**
	 * @Fields GET_GROUPMEMBER_LIST :获取群组成员列表
	 */
	static final String GET_GROUPMEMBER_LIST = "group/GetGroupMemberList.htm?";

	/**
	 * @Fields DELETE_SINGLE_MESSAGE : 删除单条消息
	 */
	static final String DELETE_SINGLE_MESSAGE = "message/deleteMessage.htm?";
	/** 
	* @Fields GET_CHAT_HISTORY : 获取 聊天记录
	*/ 
	static final String GET_CHAT_HISTORY = "group/GetGroupInfoList.htm?";

	/**
	 * @Fields DELETE_SINGLE_PRIVATE : 删除单条私信
	 */
	static final String DELETE_SINGLE_PRIVATE = "mail/DeleteMail.htm?";

	/**
	 * @Fields DELETE_SINGLE_NOTICE :删除单条通知
	 */
	static final String DELETE_SINGLE_NOTICE = "notice/deleteNotice.htm?";

	/**
	 * @Fields CLEAR_ALL_PRIVATELETTER : 清除所有私信
	 */
	static final String CLEAR_ALL_PRIVATELETTER = "mail/ClearMail.htm?";
	/**
	 * @Fields CLEAR_ALL_MESSAGE : 清除所有消息
	 */
	static final String CLEAR_ALL_MESSAGE = "message/deleteAllMessage.htm?";

	/**
	 * @Fields CLEAR_ALL_NOTICE : 清除所有通知
	 */
	static final String CLEAR_ALL_NOTICE = "notice/deleteAllNotice.htm?";
	/**
	 * @Fields GET_USERPRIVATELETTER_DATA : 获取 用户私信信息
	 */
	static final String GET_USERPRIVATELETTER_DATA = "mail/GetMailList.htm?";
	/** 
	* @Fields SEND_PRIVATELETTER :发送私信
	*/ 
	static final String SEND_PRIVATELETTER = "mail/SendMail.htm?";
	/** 
	* @Fields SEND_FEEDBACK :发送意见反馈信息 
	*/ 
	static final String SEND_FEEDBACK = "feedback/sendFeedback.htm?";
	/** 
	* @Fields AGREE_ACTIVITY : 同意活动
	*/ 
	static final String AGREE_ACTIVITY = "message/AgreeActivity.htm?";
	/** 
	 * @Fields GET_USERINFO_DETAIL : 获取用户详情
	 */ 
//	static final String GET_USERINFO_DETAIL = "user/GetUserInfo.htm?";
	static final String GET_USERINFO_DETAIL = "user/GradeAndHelpInfo.htm?";
	/** 
	 * @Fields MY_COLLECTION : 我的 收藏
	 */ 
	static final String MY_COLLECTION_API = "activitys/myActivityCollent.htm?";
	
	/** 
	* @Fields URL_HELP : 帮助
	*/ 
	static final String URL_HELP = "help/Index.htm?";

	static final String USERID = "2";

	/**
	 * @Fields MSGTYPE_TEXT :消息类型 文字
	 */
	static final String MSGTYPE_TEXT = "ChatMessageTypeText";
	/**
	 * @Fields MSGTYPE_IMG : 消息类型 图片
	 */
	static final String MSGTYPE_IMG = "ChatMessageTypeImage";
	/**
	 * @Fields MSGTYPE_AUDIO : 语音
	 */
	static final String MSGTYPE_AUDIO = "ChatMessageTypeAudio";
	/**
	 * @Fields MSGTYPE_VIDEO : 视频
	 */
	static final String MSGTYPE_VIDEO = "ChatMessageTypeVideo";
	/** 
	* @Fields PRIVATELETTER_TEXT : 文字私信
	*/ 
	static final Integer PRIVATELETTER_TEXT = 0;
	/** 
	 * @Fields PRIVATELETTER_IMG : 图片私信
	 */ 
	static final Integer PRIVATELETTER_IMG = 1;
	/** 
	 * @Fields PRIVATELETTER_AUDIO : 语因私信
	 */ 
	static final Integer PRIVATELETTER_AUDIO = 2;
	 
	/** 
	* @Fields PRIVATELETTER_VIDEO : 视频私信
	*/ 
	static final Integer PRIVATELETTER_VIDEO = 3;
	
	static final String FINISH_RECORDVIDEO_ACTION = "com.ninetowns.tootoopluse.finishrecordvideo";
	
	static final String RECEIVE_MESSAGE_ACTION = "com.ninetowns.tootoopluse.receivemessage";

	 static final String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";  
	 
	 
	 static final String GROUP_CHAT_TAB_POSITION="position";
	 
	 /** 
	* @Fields GROUP_CHAT_TAB_SIZE : 聊天群组的tab数量
	*/ 
	static final int GROUP_CHAT_TAB_SIZE = 2;
}
