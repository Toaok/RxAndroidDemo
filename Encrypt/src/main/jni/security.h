#include <jni.h>

#ifndef _Included_indi_toaok_ndk_Security
#define _Included_indi_toaok_ndk_Security

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_getSalt(JNIEnv *, jobject, jstring);

JNIEXPORT jboolean JNICALL Java_indi_toaok_ndk_Security_isDz(JNIEnv *, jobject, jstring);

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_getSign(JNIEnv *, jobject);

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_getVersion(JNIEnv *, jobject);

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_encrypt(JNIEnv *, jobject, jstring);

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_decrypt(JNIEnv *, jobject, jstring);

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_encryptWithKey(JNIEnv *, jobject, jstring, jstring);

JNIEXPORT jstring JNICALL Java_indi_toaok_ndk_Security_decryptWithKey(JNIEnv *, jobject, jstring, jstring);

#ifdef __cplusplus
}
#endif
#endif
