//
//  com_ninetowns_libchar_ChatActivity.cpp
//  jni
//
//  Created by Wiriamubin on 8/8/14.
//  Copyright (c) 2014 沱沱社区. All rights reserved.
//

#include <chat/ChatNuke.h>
#include "log.h"
#include <stdio.h>
#include <stdlib.h>
#include <jni.h>

#ifndef TAG
#define TAG "ChatService"
#endif

#define JAVA_SERVICE_CHAT_CLASS             	"com/wiriamubin/service/chat/ChatService"
#define JAVA_SERVICE_CHAT_USERINFO_CLASS    	"com/wiriamubin/service/chat/ChatUserInfo"
#define JAVA_SERVICE_CHAT_MESSAGE_CLASS     	"com/wiriamubin/service/chat/ChatMessage"
#define JAVA_SERVICE_CHAT_FEEDUSER_CLASS    	"com/wiriamubin/service/chat/ChatFeeduser"
#define JAVA_SERVICE_CHAT_FEEDINFO_CLASS     	"com/wiriamubin/service/chat/ChatFeedInfo"
#define JAVA_SERVICE_CHAT_ROOMINFO_CLASS     	"com/wiriamubin/service/chat/ChatRoomInfo"

JavaVM *JVM;

jclass CLASS_CHATUSERINFO;
jclass CLASS_CHATMESSAGE;
jclass CLASS_CHATSERVICE;
jclass CLASS_CHATROOMINFO;
jclass CLASS_CHATFEEDUSER;
jclass CLASS_CHATFEEDINFO;

jobject INSTANCE_CHATACTIVITY;

jmethodID METHOD_CALLJAVA_RECEIVEDMESSAGE;
jmethodID METHOD_CALLJAVA_USERLEAVE;
jmethodID METHOD_CALLJAVA_USERENTER;
jmethodID METHOD_CALLJAVA_ONLINEUSERS;
jmethodID METHOD_CALLJAVA_ONDISCONNECT;
jmethodID METHOD_CALLJAVA_ROOMINFO;
jmethodID METHOD_CALLJAVA_FEEDUSER;
jmethodID METHOD_CALLJAVA_FEEDLIST;

jmethodID CONSTRUCT_CHATMESSAGE;
jmethodID CONSTRUCT_CHATUSERINFO;
jmethodID CONSTRUCT_CHATROOMINFO;

/*chatmessage method*/
jfieldID FIELD_CHATMESSAGE_ROOMID;
jfieldID FIELD_CHATMESSAGE_TYPE;
jfieldID FIELD_CHATMESSAGE_ISPRIVATE;
jfieldID FIELD_CHATMESSAGE_FROM_UID;
jfieldID FIELD_CHATMESSAGE_FROM;
jfieldID FIELD_CHATMESSAGE_TO_UID;
jfieldID FIELD_CHATMESSAGE_TO;
jfieldID FIELD_CHATMESSAGE_BODY;
jfieldID FIELD_CHATMESSAGE_ATTACHMENT;
jfieldID FIELD_CHATMESSAGE_TIMESPAN;

static ChatNuke _nuke;

void disConnect(ChatStatus status, void *context) {
	JNIEnv *t_env;
	JVM->AttachCurrentThread(&t_env, NULL);
	if (!t_env) {
		JVM->DetachCurrentThread();
		return;
	}
	t_env->CallVoidMethod(INSTANCE_CHATACTIVITY, METHOD_CALLJAVA_ONDISCONNECT);
	JVM->DetachCurrentThread();
}

void receivedMessage(ChatReceivedType type, void *data, int len,
		void *context) {
	LOGE("RECEIVED %d", type);
	JNIEnv *t_env;
	JVM->AttachCurrentThread(&t_env, NULL);
	if (!t_env) {
		JVM->DetachCurrentThread();
		return;
	}
	switch (type) {
	case ChatReceivedTypeUserList: {
		UserInfo *users = (UserInfo*) data;
		auto t_jusers = t_env->NewObjectArray(len, CLASS_CHATUSERINFO, NULL);
		for (int i = 0; i < len; i++) {
			auto object = t_env->NewObject(CLASS_CHATUSERINFO,
					CONSTRUCT_CHATUSERINFO, users[i].uid, users[0].roleid,
					t_env->NewStringUTF((const char*) users[i].name),
					t_env->NewStringUTF(users[i].profileImage));
			t_env->SetObjectArrayElement(t_jusers, i, object);
		}
		t_env->CallVoidMethod(INSTANCE_CHATACTIVITY,
				METHOD_CALLJAVA_ONLINEUSERS, t_jusers);
		break;
	}
	case ChatReceivedTypeMessage: {
		ChatMessage *message = (ChatMessage*) data;
		auto t_message = t_env->NewObject(CLASS_CHATMESSAGE,
				CONSTRUCT_CHATMESSAGE, message->roomId, message->type,
				message->is_private, message->from, message->to,
				t_env->NewStringUTF(message->body),
				t_env->NewStringUTF(message->att), message->time);

		t_env->CallVoidMethod(INSTANCE_CHATACTIVITY,
				METHOD_CALLJAVA_RECEIVEDMESSAGE, t_message);
		break;
	}
	case ChatReceivedTypeOnline: {
		auto t_jusers = t_env->NewObjectArray(len, CLASS_CHATUSERINFO, NULL);
		UserInfo *info = (UserInfo*) data;
		for (int i = 0; i < len; i++) {
			auto object = t_env->NewObject(CLASS_CHATUSERINFO,
					CONSTRUCT_CHATUSERINFO, info[i].uid, info[i].roleid,
					t_env->NewStringUTF((const char*) info[i].name),
					t_env->NewStringUTF(info[i].profileImage));
			t_env->SetObjectArrayElement(t_jusers, i, object);
		}
		t_env->CallVoidMethod(INSTANCE_CHATACTIVITY, METHOD_CALLJAVA_USERENTER,
				t_jusers);
		break;
	}
	case ChatReceivedTypeOffline: {
		t_env->CallVoidMethod(INSTANCE_CHATACTIVITY, METHOD_CALLJAVA_USERLEAVE,
				atoi((char*) data));
		break;
	}
	case ChatReceivedTypeRoomInfo: {
		RoomInfo info = *(RoomInfo*) data;
		auto t_roomInfo = t_env->NewObject(CLASS_CHATROOMINFO,
				CONSTRUCT_CHATROOMINFO, info.roomId,
				t_env->NewStringUTF(info.name), info.create_date, info.goodsId,
				t_env->NewStringUTF(info.image),
				t_env->NewStringUTF(info.throw_ft), info.uid,
				t_env->NewStringUTF(info.username),
				t_env->NewStringUTF(info.video_url));
		t_env->CallVoidMethod(INSTANCE_CHATACTIVITY, METHOD_CALLJAVA_ROOMINFO,
				t_roomInfo);
		break;
	}
	case ChatReceivedTypeFeedlist: {
		UserInfo *infos = (UserInfo*) data;
		jobjectArray t_infos_array = t_env->NewObjectArray(len,
				CLASS_CHATFEEDINFO, nullptr);
		for (int i = 0; i < len; i++) {
			auto object = t_env->NewObject(CLASS_CHATUSERINFO,
					CONSTRUCT_CHATUSERINFO, infos[i].uid, infos[i].roleid,
					t_env->NewStringUTF((const char*) infos[i].name),
					t_env->NewStringUTF(infos[i].profileImage));
			t_env->SetObjectArrayElement(t_infos_array, i, object);
		}
		t_env->CallVoidMethod(INSTANCE_CHATACTIVITY, METHOD_CALLJAVA_FEEDLIST,
				t_infos_array);
		break;
	}
	case ChatReceivedTypeFeeduser: {
		UserInfo info = *(UserInfo*) data;
		auto t_user = t_env->NewObject(CLASS_CHATUSERINFO,
				CONSTRUCT_CHATUSERINFO, info.uid, info.roleid,
				t_env->NewStringUTF((const char*) info.name),
				t_env->NewStringUTF(info.profileImage));
		t_env->CallVoidMethod(INSTANCE_CHATACTIVITY, METHOD_CALLJAVA_FEEDUSER,
				t_user);
		break;
	}
	default:
		break;
	}
	JVM->DetachCurrentThread();
}

jboolean connect(JNIEnv *env, jobject clazz, jstring host, jint roomId,
		jobject object) {
	if (!INSTANCE_CHATACTIVITY)
		INSTANCE_CHATACTIVITY = env->NewGlobalRef(clazz);
	jboolean t_iscopy = JNI_FALSE;
	jclass t_class = env->GetObjectClass(object);
	jfieldID t_userid_field = env->GetFieldID(t_class, "userId", "I");
	jfieldID t_name_field = env->GetFieldID(t_class, "name",
			"Ljava/lang/String;");
	jfieldID t_profileimage_field = env->GetFieldID(t_class, "profileImage",
			"Ljava/lang/String;");

	UserInfo userInfo;
	memset(&userInfo, 0, sizeof(userInfo));
	userInfo.uid = env->GetIntField(object, t_userid_field);

	char t_userId[RTMP_CHAT_USERID_MAX_LEN];
	sprintf(t_userId, "%d", userInfo.uid);

	jstring js_name = (jstring) env->GetObjectField(object, t_name_field);
	const char *t_name = env->GetStringUTFChars(js_name, &t_iscopy);

	jstring js_profileimage = (jstring) env->GetObjectField(object,
			t_profileimage_field);
	const char *t_profileimage = env->GetStringUTFChars(js_profileimage,
			&t_iscopy);

	memcpy(userInfo.name, t_name, sizeof(userInfo.name));
	env->ReleaseStringUTFChars(js_name, t_name);
	memcpy(userInfo.profileImage, t_profileimage,
			sizeof(userInfo.profileImage));
	env->ReleaseStringUTFChars(js_profileimage, t_profileimage);

	const char *t_host = env->GetStringUTFChars(host, &t_iscopy);
	_nuke.registCallbackWithContext(&receivedMessage, &disConnect, NULL);
	int result = _nuke.startUp(t_host, roomId, userInfo, sizeof(UserInfo));
	LOGE("connect status %d", result);
	return result > 0;
}

void send(JNIEnv *env, jobject clazz, jobject messageObject) {
	ChatMessage message;
	memset(&message, 0, sizeof(message));
	message.roomId = env->GetIntField(messageObject, FIELD_CHATMESSAGE_ROOMID);
	message.type = (ChatMessageType) env->GetByteField(messageObject,
			FIELD_CHATMESSAGE_TYPE);
	message.from = env->GetIntField(messageObject, FIELD_CHATMESSAGE_FROM_UID);
	message.to = env->GetIntField(messageObject, FIELD_CHATMESSAGE_TO_UID);
	jstring body = (jstring) env->GetObjectField(messageObject,
			FIELD_CHATMESSAGE_BODY);
	jstring att = (jstring) env->GetObjectField(messageObject,
			FIELD_CHATMESSAGE_ATTACHMENT);
	message.time = env->GetLongField(messageObject, FIELD_CHATMESSAGE_TIMESPAN);
	jboolean t_iscopy;
	if (body) {
		const char *t_body = env->GetStringUTFChars(body, &t_iscopy);
		memcpy(message.body, t_body, sizeof(message.body));
		env->ReleaseStringUTFChars(body, t_body);
	}
	if (att) {
		const char *t_att = env->GetStringUTFChars(att, &t_iscopy);
		memcpy(message.att, t_att, sizeof(message.att));
		env->ReleaseStringUTFChars(att, t_att);
	}
	if (_nuke.send(ChatSendActionTypeMessage, message.roomId, (char*) &message,
			sizeof(message)))
		LOGE("send success");
elseLOGE("send error");
}

void shutDown(JNIEnv *env, jobject object) {
	_nuke.shutDown();
}

void disconnect(JNIEnv *env, jobject clazz, jint roomId) {
	_nuke.disConnect(roomId);
}

void feed(JNIEnv *env, jobject object, jint roomid, jint uid) {
	char t_userId[RTMP_CHAT_USERID_MAX_LEN];
	sprintf(t_userId, "%d", uid);
	_nuke.send(ChatSendActionTypeFeed, roomid, t_userId,
			(int) strlen(t_userId));
}

static JNINativeMethod s_methods[] = { { "connect",
		"(Ljava/lang/String;ILcom/wiriamubin/service/chat/ChatUserInfo;)Z",
		(void*) &connect }, { "send",
		"(Lcom/wiriamubin/service/chat/ChatMessage;)V", (void*) &send }, {
		"disconnect", "(I)V", (void*) &disconnect }, { "shutDown", "()V",
		(void*) &shutDown }, { "feed", "(II)V", (void*) &feed } };

jint JNI_OnLoad(JavaVM* jvm, void* reserved) {
	JNIEnv* env = NULL;
	JVM = jvm;
	if (jvm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK)
		return JNI_ERR;
	CLASS_CHATSERVICE = (jclass) env->NewGlobalRef(
			env->FindClass(JAVA_SERVICE_CHAT_CLASS));
	CLASS_CHATUSERINFO = (jclass) env->NewGlobalRef(
			env->FindClass(JAVA_SERVICE_CHAT_USERINFO_CLASS));
	CLASS_CHATMESSAGE = (jclass) env->NewGlobalRef(
			env->FindClass(JAVA_SERVICE_CHAT_MESSAGE_CLASS));
	CLASS_CHATROOMINFO = (jclass) env->NewGlobalRef(
			env->FindClass(JAVA_SERVICE_CHAT_ROOMINFO_CLASS));
	CLASS_CHATFEEDINFO = (jclass) env->NewGlobalRef(
			env->FindClass(JAVA_SERVICE_CHAT_FEEDINFO_CLASS));
	CLASS_CHATFEEDUSER = (jclass) env->NewGlobalRef(
			env->FindClass(JAVA_SERVICE_CHAT_FEEDUSER_CLASS));

	CONSTRUCT_CHATUSERINFO = env->GetMethodID(CLASS_CHATUSERINFO, "<init>",
			"(IILjava/lang/String;Ljava/lang/String;)V");
	CONSTRUCT_CHATMESSAGE = env->GetMethodID(CLASS_CHATMESSAGE, "<init>",
			"(IBZIILjava/lang/String;Ljava/lang/String;J)V");
	CONSTRUCT_CHATROOMINFO =
			env->GetMethodID(CLASS_CHATROOMINFO, "<init>",
					"(ILjava/lang/String;JJLjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V");

	FIELD_CHATMESSAGE_ROOMID = env->GetFieldID(CLASS_CHATMESSAGE, "roomId",
			"I");
	FIELD_CHATMESSAGE_TYPE = env->GetFieldID(CLASS_CHATMESSAGE, "type", "B");
	FIELD_CHATMESSAGE_FROM_UID = env->GetFieldID(CLASS_CHATMESSAGE, "fromUid",
			"I");
	FIELD_CHATMESSAGE_TO_UID = env->GetFieldID(CLASS_CHATMESSAGE, "toUid", "I");
	FIELD_CHATMESSAGE_ISPRIVATE = env->GetFieldID(CLASS_CHATMESSAGE,
			"isPrivate", "Z");
	FIELD_CHATMESSAGE_BODY = env->GetFieldID(CLASS_CHATMESSAGE, "body",
			"Ljava/lang/String;");
	FIELD_CHATMESSAGE_ATTACHMENT = env->GetFieldID(CLASS_CHATMESSAGE,
			"attachment", "Ljava/lang/String;");
	FIELD_CHATMESSAGE_TIMESPAN = env->GetFieldID(CLASS_CHATMESSAGE, "timespan",
			"J");

	METHOD_CALLJAVA_RECEIVEDMESSAGE = env->GetMethodID(CLASS_CHATSERVICE,
			"receivedMessage", "(Lcom/wiriamubin/service/chat/ChatMessage;)V");
	METHOD_CALLJAVA_ONLINEUSERS = env->GetMethodID(CLASS_CHATSERVICE,
			"getUserList", "([Lcom/wiriamubin/service/chat/ChatUserInfo;)V");
	METHOD_CALLJAVA_USERENTER = env->GetMethodID(CLASS_CHATSERVICE,
			"userDidEnter", "([Lcom/wiriamubin/service/chat/ChatUserInfo;)V");
	METHOD_CALLJAVA_USERLEAVE = env->GetMethodID(CLASS_CHATSERVICE,
			"userDidLeave", "(I)V");
	METHOD_CALLJAVA_ROOMINFO = env->GetMethodID(CLASS_CHATSERVICE,
			"getRoomInfo", "(Lcom/wiriamubin/service/chat/ChatRoomInfo;)V");
	METHOD_CALLJAVA_ONDISCONNECT = env->GetMethodID(CLASS_CHATSERVICE,
			"onDisconnect", "()V");
	METHOD_CALLJAVA_FEEDLIST = env->GetMethodID(CLASS_CHATSERVICE,
			"getFeedList", "([Lcom/wiriamubin/service/chat/ChatUserInfo;)V");
	METHOD_CALLJAVA_FEEDUSER = env->GetMethodID(CLASS_CHATSERVICE,
			"getFeedUser", "(Lcom/wiriamubin/service/chat/ChatUserInfo;)V");

	env->RegisterNatives(CLASS_CHATSERVICE, s_methods,
			sizeof(s_methods) / sizeof(s_methods[0]));

	return JNI_VERSION_1_6;
}

void JNI_OnUnLoad(JavaVM *vm, void *reserved) {
	JNIEnv* env = NULL;
	if (vm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) {
		env->DeleteGlobalRef(CLASS_CHATMESSAGE);
		env->DeleteGlobalRef(INSTANCE_CHATACTIVITY);
		env->DeleteGlobalRef(CLASS_CHATUSERINFO);
		env->DeleteGlobalRef(CLASS_CHATSERVICE);
		env->UnregisterNatives(CLASS_CHATSERVICE);
	}
}
