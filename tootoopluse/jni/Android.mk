LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Rtmp

LOCAL_SRC_FILES := ../../RtmpChat/chat/librtmp/amf.c \
				   ../../RtmpChat/chat/librtmp/hashswf.c \
				   ../../RtmpChat/chat/librtmp/log.c \
				   ../../RtmpChat/chat/librtmp/parseurl.c \
				   ../../RtmpChat/chat/librtmp/rtmp.c \

include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := RtmpChat

LOCAL_SRC_FILES := ../../RtmpChat/chat/chat.cpp \
				   ../../RtmpChat/chat/ChatNuke.cpp
LOCAL_STATIC_LIBRARIES := Rtmp
LOCAL_CFLAGS += -std=gnu++11


include $(BUILD_STATIC_LIBRARY)




#-----------------------------------
include $(CLEAR_VARS)  

LOCAL_MODULE    := ChatService
LOCAL_SRC_FILES := ChatService.cpp

LOCAL_C_INCLUDES  := $(LOCAL_PATH)/../../RtmpChat

LOCAL_STATIC_LIBRARIES := RtmpChat
LOCAL_LDLIBS :=-llog
LOCAL_CFLAGS +=-std=gnu++11

include $(BUILD_SHARED_LIBRARY)

