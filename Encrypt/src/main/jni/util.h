#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

char *_JString2CStr(JNIEnv *env, jstring jstr);

char *str_combine(const char *str1, const char *str2);

#ifdef __cplusplus
}
#endif
