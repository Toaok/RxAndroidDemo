#include <jni.h>
#include <string.h>
#include <android/log.h>

#define LOG_TAG "Security-native"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

#ifdef __cplusplus
extern "C" {
#endif

char *_JString2CStr(JNIEnv *env, jstring jstr)
{
    char *rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env, "GB2312");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid, strencode);
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (alen > 0)
    {
	rtn = (char *)malloc(alen + 1);
	memcpy(rtn, ba, alen);
	rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);
    return rtn;
}

char *str_combine(const char *str1, const char *str2)
{
    char *result;
    result = (char *)malloc(strlen(str1) + strlen(str2) + 1); //str1的长度 + str2的长度 + \0;
    if (!result)
    {
	printf("Error: malloc failed in concat! \n");
    }
    strcpy(result, str1);
    strcat(result, str2);
    return result;
}

#ifdef __cplusplus
}
#endif