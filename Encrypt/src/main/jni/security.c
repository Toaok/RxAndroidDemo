#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <android/log.h>
#include "constant.h"
#include "signature.h"
#include "openssl/crypt.h"
#include "util.h"

#define LOG_TAG "Security-native"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

#ifndef _Included_indi_toaok_ndk_Security
#define _Included_indi_toaok_ndk_Security

#ifdef __cplusplus
extern "C" {
#endif

jint JNI_OnLoad(JavaVM *vm, void *reserved)
{
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void **)&env, JNI_VERSION_1_4) != JNI_OK)
    {
	return JNI_ERR;
    }
    if (verify_sign(env) == JNI_OK)
    {
	return JNI_VERSION_1_4;
    }
    return JNI_ERR;
}

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_getSalt(JNIEnv *env, jclass object, jstring ketStr)
{
    return (*env)->NewStringUTF(env, SALT);
}

JNIEXPORT jboolean JNICALL Java_indi_toaok_ndk_Security_isDz(JNIEnv *env, jclass object, jstring key)
{
    char *str = _JString2CStr(env, key);
    char *decrypt_str = _decrypt(DEV_SIGN);
    return (strcmp(str, DEV_SIGN) == 0);
}

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_getSign(JNIEnv *env, jclass object)
{
    int length;
    length = sizeof(SIGN_ARRAY) / sizeof(SIGN_ARRAY[0]);

    int i, size = 0;
    for (i = 0; i < length; ++i)
    {
	size += strlen(SIGN_ARRAY[i]);
    }
    size += length - 1;
    char *reslut = NULL;
    if ((reslut = (char *)malloc(sizeof(char) * (size + 1))) == NULL)
    {
	LOGD("Malloc heap failed!");
	return NULL;
    }
    memset(reslut, 0, sizeof(char) * (size + 1));
    strcpy(reslut, SIGN_ARRAY[0]);
    for (i = 1; i < length; ++i)
    {
	strcat(reslut, ",");
	strcat(reslut, SIGN_ARRAY[i]);
    }
    return (*env)->NewStringUTF(env, reslut);
}

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_getVersion(JNIEnv *env, jclass object)
{
    return (*env)->NewStringUTF(env, VERSION);
}

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_encrypt(JNIEnv *env, jclass object, jstring data)
{
    char *str = _JString2CStr(env, data);
    return (*env)->NewStringUTF(env, _encrypt(str));
}

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_decrypt(JNIEnv *env, jclass object, jstring data)
{
    char *str = _JString2CStr(env, data);
    return (*env)->NewStringUTF(env, _decrypt(str));
}

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_encryptWithKey(JNIEnv *env, jclass object, jstring keyStr, jstring data)
{
    char *key = _JString2CStr(env, keyStr);
    char *str = _JString2CStr(env, data);
    return (*env)->NewStringUTF(env, _encrypt_key(key, str));
}

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_decryptWithKey(JNIEnv *env, jclass object, jstring keyStr, jstring data)
{
    char *key = _JString2CStr(env, keyStr);
    char *str = _JString2CStr(env, data);
    return (*env)->NewStringUTF(env, _decrypt_key(key, data));
}

#ifdef __cplusplus
}
#endif
#endif