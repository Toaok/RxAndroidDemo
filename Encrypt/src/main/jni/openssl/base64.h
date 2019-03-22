#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

char* base64_encode(const char* data, int data_len);

char *base64_decode(const char* data, int data_len);

#ifdef __cplusplus
}
#endif
