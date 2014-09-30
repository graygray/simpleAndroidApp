LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := eng
LOCAL_MODULE    := libhidsend
LOCAL_SRC_FILES := hidsend.c

include $(BUILD_STATIC_LIBRARY)


include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := eng
LOCAL_MODULE    := libJNIinterface
LOCAL_SRC_FILES := JNIinterface.c

LOCAL_STATIC_LIBRARIES := libhidsend
include $(BUILD_SHARED_LIBRARY)
