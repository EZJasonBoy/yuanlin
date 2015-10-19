//
//  chatnuke.h
//  jni
//
//  Created by Wiriamubin on 8/7/14.
//  Copyright (c) 2014 沱沱社区. All rights reserved.
//


/**
 
 此类是聊天室管理类（具体功能有启动链接聊天室 断开房间，断开服务器 同时支持多个聊天室共存）
 
 2014 9 19 修改
 
 修改部分接口 （启动聊天室 由自身注册 聊天室消息，聊天室状态）
 
 */

#ifndef jni_chatnuke_h
#define jni_chatnuke_h

#include "chat.h"
#include <string>
#include <map>
#include "packet.h"
using namespace std;
/**消息接受回调
 @type          消息类型
 @data          消息内容
 @len           消息长度
 @context       传递的上下文    一般用于ios
 */
typedef void(*ChatReceivedMessageCallback)(ChatReceivedType type,const char* groupId,void *data,int len,void *context);
/**
 @status        链接状态(目前只用断开连接)
 @context       上下文 一般用于ios
 */
typedef void(*ChatStatusCallback)(ChatStatus status,void *context);


class ChatNuke
{
public:
    ChatNuke();
    ~ChatNuke();
    
    /**
     注册消息回调函数 和聊天室链接状态改变函数 
     @received      消息接受函数
     @status        状态改变函数
     @context       上下文（ios 一般传递controller ...）
     */
    inline void registCallbackWithContext(ChatReceivedMessageCallback received, ChatStatusCallback status,void *context)
    {
        m_receivedCallback = received;
        m_statusCallback = status;
        m_obj = context;
    }
    /**
     * 启动聊天室并且链接
     * @param host          服务器地址
     * @param roomId        房间id
     * @param disConnect    userId 用户id
     * @param userInfo      UserInfo 实体
     * @param len           UserInfo 实体大小
     */
    bool startUp(const char *host,const char* user_id);
    
    /**
     * 根据房间 id 关闭指定的聊天室房间
     * @param roomId        房间id
     */
    //void disConnect(int roomId);
    
    /**
     * 关闭聊天室 同时关闭所有房间
     */
    void shutDown();
    /**
     * 关闭聊天室房间
     * @param type          发送类型（有聊天信息 和 投食）
     * @param roomId        房间id
     * @param data          消息内容
     * @param len           消息长度
     */
    //bool send(ChatSendActionType type,int roomId,const char *data,int len);
	bool send(const char* groupId,const char *data,int len);
    /**
     * 聊天室是否连接
     */
    bool isConnect();
    
    inline ChatReceivedMessageCallback getReceivedCallback(){ return m_receivedCallback;}
    inline ChatStatusCallback getStatusCallback(){ return m_statusCallback;}
    inline void*    getContext(){return m_obj;}
private:
    RtmpChat                    m_rtmp;
    /**
     标示链接标示
     */
    bool                    m_Isconnect;
    /**
     当前聊天室hash 用roomid 对应聊天室
     */
    //map<int, chat*>         m_chats;
	chat					 m_chat;
    /**
     消息回调
     */
    ChatReceivedMessageCallback             m_receivedCallback;
    /**
     聊天室状态回调
     */
    ChatStatusCallback      m_statusCallback;
    void                    *m_obj;
};

#endif
