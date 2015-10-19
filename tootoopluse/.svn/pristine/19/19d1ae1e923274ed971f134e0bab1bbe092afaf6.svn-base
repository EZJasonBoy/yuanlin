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
//#include<malloc.h>

#ifndef TAG
#define TAG "ChatService"
#endif

#define JAVA_SERVICE_CHAT_CLASS             	"com/wiriamubin/service/chat/ChatService"

JavaVM *JVM;

jclass CLASS_CHATSERVICE;

jobject INSTANCE_CHATACTIVITY;

jmethodID METHOD_CALLJAVA_RECEIVEDMESSAGE;
jmethodID METHOD_CALLJAVA_ONDISCONNECT;
static ChatNuke _nuke;

/**
 * 聊天服务断开连接的回调
 */
void disConnect(ChatStatus status, void *context) {
//	JNIEnv *t_env;
//	JVM->AttachCurrentThread(&t_env, NULL);
//	if (!t_env) {
//		JVM->DetachCurrentThread();
//		return;
//	}
//	t_env->CallVoidMethod(INSTANCE_CHATACTIVITY, METHOD_CALLJAVA_ONDISCONNECT);
//	JVM->DetachCurrentThread();
}

void receivedMessage(ChatReceivedType type, const char* groupId, void *data,
		int len, void *context) {
	LOGE("RECEIVED %d", type);
	JNIEnv *t_env;
	JVM->AttachCurrentThread(&t_env, NULL);
	if (!t_env) {
		JVM->DetachCurrentThread();
		return;
	}

	switch (type) {
	case ChatReceivedTypeMessage:{
		LOGE("Receivemessage...........%s",groupId);
		const char *message = (const char *) data;
		t_env->CallVoidMethod(INSTANCE_CHATACTIVITY,METHOD_CALLJAVA_RECEIVEDMESSAGE,t_env->NewStringUTF(message),t_env->NewStringUTF(groupId));
		LOGE("CallVoidMethod...........");
		break;
	}
	case ChatReceivedTypeInform:{
		LOGE("Receivemessage...........%s",groupId);
		const char *msg = (const char *) data;
		t_env->CallVoidMethod(INSTANCE_CHATACTIVITY,METHOD_CALLJAVA_RECEIVEDMESSAGE,t_env->NewStringUTF(msg),t_env->NewStringUTF(groupId));
		LOGE("CallVoidMethod...........");
		break;
	}
	}
	JVM->DetachCurrentThread();
}

/**
 * rtmp是否连接
 */
jboolean isConnectRtmp() {

	return _nuke.isConnect() > 0;
}

/**
 * 聊天室长连接   host聊天室地址       userId  用户id
 */
jboolean connectRtmp(JNIEnv* env, jobject clazz, jstring host, jstring userId) {
	if (!INSTANCE_CHATACTIVITY)
		INSTANCE_CHATACTIVITY = env->NewGlobalRef(clazz);

	if(JNI_FALSE!=isConnectRtmp()){
		_nuke.shutDown();
	}
	jboolean t_iscopy = JNI_FALSE;
	const char *t_host = env->GetStringUTFChars(host, &t_iscopy);
	const char *t_userId = env->GetStringUTFChars(userId, &t_iscopy);
	//注册回调方法
	_nuke.registCallbackWithContext(&receivedMessage, &disConnect, NULL);
	//启动聊天室长连接
	int result = _nuke.startUp(t_host, t_userId);
	env->ReleaseStringUTFChars(host, t_host);
	env->ReleaseStringUTFChars(userId, t_userId);
	LOGE("result =====%d",result);
	return result > 0;
}

/**
 * 直接传递 json字符串(data)
 */

  jboolean send(JNIEnv * env,jobject clazz,jstring data,jstring groupId){

	  jboolean t_iscopy = JNI_FALSE;
	  const char *t_groupId =  env->GetStringUTFChars(groupId,&t_iscopy);
	  const char *t_data =  env->GetStringUTFChars(data,&t_iscopy);


	  LOGE("send data jsonstring===%s",t_data);
	  if (_nuke.send(t_groupId, t_data, strlen(t_data))) {
	  		LOGE("send success");
	  		return JNI_TRUE;
	  	} else {
	  		LOGE("send failed");
	  		return JNI_FALSE;

	  	}
	  	env->ReleaseStringUTFChars(groupId,t_groupId);
	  	env->ReleaseStringUTFChars(groupId,t_data);

  }


void shutDown(JNIEnv *env , jobject object)
{
    _nuke.shutDown();
	LOGE("shutdown");
}
/**
 * 需要注册的方法   java层调用    发送消息 和 连接rtmp
 */
static JNINativeMethod s_methods[] = {
		{"isConnectRtmp","()Z",(void*)&isConnectRtmp},
		{"shutDown","()V",(void*)&shutDown},
		{ "send","(Ljava/lang/String;Ljava/lang/String;)Z", (void*) &send }, {
		"connectRtmp", "(Ljava/lang/String;Ljava/lang/String;)Z",
		(void*) &connectRtmp } //Ljava/lang/String;
};

jint JNI_OnLoad(JavaVM* jvm, void* reserved) {
	JNIEnv* env = NULL;
	JVM = jvm;
	if (jvm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK)
		return JNI_ERR;
	//初始化类
	CLASS_CHATSERVICE = (jclass) env->NewGlobalRef(
			env->FindClass(JAVA_SERVICE_CHAT_CLASS));


	METHOD_CALLJAVA_RECEIVEDMESSAGE = env->GetMethodID(CLASS_CHATSERVICE,
			"receivedMessage", "(Ljava/lang/String;Ljava/lang/String;)V");
	METHOD_CALLJAVA_ONDISCONNECT = env->GetMethodID(CLASS_CHATSERVICE,
			"onDisconnect", "()V");
	LOGE("getfeild  success");

	env->RegisterNatives(CLASS_CHATSERVICE, s_methods,
			sizeof(s_methods) / sizeof(s_methods[0]));

	return JNI_VERSION_1_6;
}

void JNI_OnUnLoad(JavaVM *vm, void *reserved) {
	JNIEnv* env = NULL;
	if (vm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) {
		env->DeleteGlobalRef(INSTANCE_CHATACTIVITY);
		env->DeleteGlobalRef(CLASS_CHATSERVICE);
		env->UnregisterNatives(CLASS_CHATSERVICE);
	}
}
