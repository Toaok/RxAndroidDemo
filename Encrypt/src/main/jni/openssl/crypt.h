#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

char *_encrypt(char * str) ;

char *_encrypt_key(char * key, char * str) ;

char *_decrypt(char * str) ;

char *_decrypt_key(char * key, char * str) ;

#ifdef __cplusplus
}
#endif
