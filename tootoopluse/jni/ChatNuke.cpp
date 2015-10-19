//
//  chatnuke.cpp
//  jni
//
//  Created by Wiriamubin on 8/7/14.
//  Copyright (c) 2014 沱沱社区. All rights reserved.
//

#include "chatNuke.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

/*
void received(void *context,char *data_key,int key_length, char *chatRoom, void *userdata, int userdata_length)
{
    ChatNuke *nuke = (ChatNuke*)context;
    ChatData *data = (ChatData*)userdata;
    ChatReceivedType type = ChatReceivedTypeOther;
    void    *pass = NULL;
    
    if (strstr(data_key, RTMP_CHAT_MESSAGE))
    {
        ChatMessage message;
        bzero(&message, sizeof(message));
        memcpy(&message, userdata, sizeof(message));
        type = ChatReceivedTypeMessage;
        pass = &message;
    }
    else if(strstr(data_key, RTMP_CHAT_USRELIST))
    {
        UserInfo list[userdata_length];
        for (int i = 0; i < userdata_length; i++)
        {
            list[i] = *(UserInfo*)data->value;
            data++;
        }
        type = ChatReceivedTypeUserList;
        pass = &list;
    }
    else if(strstr(data_key, RTMP_CHAT_USER_COME))
    {
        UserInfo *info = (UserInfo*)data->value;
        type = ChatReceivedTypeOnline;
        pass = info;
    }
    else if(strstr(data_key , RTMP_CHAT_ROOMINFO))
    {
        RoomInfo roomInfo;
        memset(&roomInfo, 0, sizeof(RoomInfo));
        for (int i = 0; i<userdata_length; i++)
        {
			if(strstr(data->key,"ChatroomId"))
			{
				roomInfo.roomId = atoi(data->value);

			}else if(strstr(data->key,"ChatroomName"))
			{
				memcpy(roomInfo.name, data->value, data->value_len);

			}else if(strstr(data->key,"CreateDate"))
			{
				roomInfo.create_date = atoi(data->value);

			}else if(strstr(data->key,"GoodsId"))
			{
				roomInfo.goodsId = atoi(data->value);

			}else if(strstr(data->key,"Image"))
			{
				memcpy(roomInfo.image, data->value, data->value_len);

			}else if(strstr(data->key,"ThrowFoodTime"))
			{
				memcpy(roomInfo.throw_ft, data->value, data->value_len);

			}else if(strstr(data->key,"UserId"))
			{
				roomInfo.uid = atoi(data->value);
			}else if(strstr(data->key,"UserName"))
			{
				memcpy(roomInfo.username, data->value, data->value_len);
			}else if(strstr(data->key,"VideoUrl"))
			{
				memcpy(roomInfo.video_url, data->value, data->value_len);
			}else
			{

			}
//            switch (*data->key)
//            {
//                case "ChatroomId":
//                    roomInfo.roomId = atoi(data->value);
//                    break;
//                case 1:
//                    memcpy(roomInfo.name, data->value, data->value_len);
//                    break;
//                case 2:
//                    roomInfo.create_date = atoi(data->value);
//                    break;
//                case 3:
//                    //roomInfo.goodsId = atoi(data->value);
//                    break;
//                case 4:
//                    memcpy(roomInfo.image, data->value, data->value_len);
//                    break;
//                case 5:
//                    memcpy(roomInfo.throw_ft, data->value, data->value_len);
//                    break;
//                case 6:
//                    memcpy(roomInfo.username, data->value, data->value_len);
//                    break;
//                case 7:
//                    memcpy(roomInfo.video_url, data->value, data->value_len);
//                    break;
//                default:
//                    memcpy(roomInfo.video_url, data->value, data->value_len);
//                    break;
//            }
            
            data++;
        }
        type = ChatReceivedTypeRoomInfo;
        pass = &roomInfo;
    }
    else if(strstr(data_key, RTMP_CHAT_FEEDLIST))
    {
        if (userdata_length)
        {
            UserInfo    infos[userdata_length];
            for (int i = 0; i<userdata_length; i++)
            {
                UserInfo user = *(UserInfo*)(data->value);
                infos[i] = user;
                data++;
            }
            type = ChatReceivedTypeFeedlist;
            pass = &infos;
        }
    }
    else if(strstr(data_key, RTMP_CHAT_FEEDUSER))
    {
        if (userdata_length)
        {
            UserInfo users[userdata_length];
            for (int i = 0; i < userdata_length; i++)
            {
                UserInfo user = *(UserInfo*)(data->value);
                users[i] = user;
                data++;
            }
            type = ChatReceivedTypeFeeduser;
            pass = &users;
        }
    }
    else if(strstr(data_key, RTMP_CHAT_USER_LEAVE))
    {
        type = ChatReceivedTypeOffline;
        pass = data->key;
    }
    else
    {
        
    }
    if (pass)
        nuke->getReceivedCallback()(type,pass,userdata_length,nuke->getContext());
}

void disconnect(void *context)
{
    ChatNuke *nuke = (ChatNuke*)context;
    nuke->shutDown();
    nuke->getStatusCallback()(ChatStatusDisconnect,nuke->getContext());
}

ChatNuke::ChatNuke():m_Isconnect(false)
{
    
}


//by_songgp add
bool ChatNuke::connectRtmp(const char* host)
{
	assert(m_obj || m_receivedCallback || m_statusCallback);
    bool result = false;
    if (!m_Isconnect) 
	{
        result = m_rtmp.open(host,disconnect,(void*)this);
        if (!result)
        {
            m_rtmp.close();
            return result;
        }
    }

	m_Isconnect = true;
    return m_Isconnect;
}

//by_songgp add
bool ChatNuke::connectRoom(int roomId,UserInfo user, int len)
{
    char str_room[10];
    memset(str_room, 0, sizeof(str_room));
    sprintf(str_room, "%d",roomId);
    if (m_chats.find(roomId)==m_chats.end())
    {
        auto chat = new class chat();
        char t_uid[RTMP_CHAT_USERID_MAX_LEN];
        sprintf(t_uid, "%d",user.uid);
        result = chat->open(m_rtmp, str_room, received, (void*)this, t_uid, (char*)&user, len);
        if (!result)
        {
            chat->close();
            return result;
        }
        m_chats[roomId] = chat;
    }
  
    return true;
}

void ChatNuke::disConnect(int roomId)
{
    auto p = m_chats.find(roomId);
    if (p!=m_chats.end())
    {
        p->second->close();
        m_chats.erase(p);
    }
}

void ChatNuke::shutDown()
{
    if(!m_chats.size()) return;
    for (auto it = m_chats.begin(); it!=m_chats.end(); it++)
    {
        it->second->close();
        delete it->second;
    }
    m_chats.erase(m_chats.begin(), m_chats.end());
    m_rtmp.close();
    m_Isconnect = false;
}

bool ChatNuke::send(ChatSendActionType type,int roomId,const char *data , int len)
{
    auto chat = m_chats.find(roomId);
    if (chat!=m_chats.end())
    {
        switch (type)
        {
            case ChatSendActionTypeMessage:
                return chat->second->send((char*)data,len);
            case ChatSendActionTypeFeed:
                return chat->second->feeding((char*)data, len);
            default:
                return false;
        }
    }
    return false;
}

bool ChatNuke::isConnect()
{
    return m_Isconnect;
}

ChatNuke::~ChatNuke()
{
    shutDown();
}
*/

void received(void *context,char *data_key,int key_length, char *chatRoom, void *userdata, int userdata_length)
{
	ChatNuke *nuke = (ChatNuke*)context;
	ChatReceivedType type = ChatReceivedTypeOther;
	void    *pass = NULL;
    
	//若str2是str1的子串，则先确定str2在str1的第一次出现的位置，并返回此位置到str1末尾的所有字符；如果str2不是str1的子串，则返回NULL。（注：若想返回str2在str1第一次出现的位置，不是这个函数）
    if(strstr(data_key, RTMP_CHAT_MESSAGE))
    {
        ChatData cd;
        bzero(&cd, sizeof(cd));
        memcpy(&cd, userdata, sizeof(cd));
        type = ChatReceivedTypeMessage;
        pass = &cd;

    }
/*
	switch (data_key)
	{
	case RTMP_CHAT_MESSAGE:

		ChatData cd;
		bzero(&cd, sizeof(cd));
		memcpy(&cd, userdata, sizeof(cd));
		type = ChatReceivedTypeMessage;
		pass = &cd;

		break;
	default:
		 break;
	}
*/

	if (pass)
		nuke->getReceivedCallback()(type,chatRoom,pass,userdata_length,nuke->getContext());
	
}

void disconnect(void *context)
{
	ChatNuke *nuke = (ChatNuke*)context;
	nuke->shutDown();
	nuke->getStatusCallback()(ChatStatusDisconnect,nuke->getContext());
}

ChatNuke::ChatNuke():m_Isconnect(false)
{

}

bool ChatNuke::startUp(const char *host,const char* user_id)
{
    assert(m_obj || m_receivedCallback || m_statusCallback);

    bool result = false;
    if (!m_Isconnect) 
	{
        result = m_rtmp.open(host,user_id,disconnect,(void*)this);
        if (!result)
        {
            m_rtmp.close();
            return result;

        }else
		{
			m_chat.open(m_rtmp,received,(void*)this);
		}
    }

	m_Isconnect = true;
	return m_Isconnect;
}


void ChatNuke::shutDown()
{
	m_chat.close();
    m_rtmp.close();
    m_Isconnect = false;
}

bool ChatNuke::send(const char* groupId,const char *data,int len)
{
	return m_chat.send(groupId,(char*)data,len);
}

bool ChatNuke::isConnect()
{
    return m_Isconnect;
}

ChatNuke::~ChatNuke()
{
    shutDown();
}
