#include <jni.h>
#include <malloc.h>
#include <string.h>
#include <android/log.h>
#include "aes.h"
#include "base64.h"
#include "./util.h"
#include "./constant.h"
#include "md5.h"

#define LOG_TAG "Security-native"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

const static int SIZE_M = 32;

/**
 * 将秘钥转为32位
 */
char *keyToMd5_32(char *key_original)
{
    Md5Context md5Context;
    MD5_HASH md5Hash;
    Md5Initialise(&md5Context);
    Md5Update(&md5Context, key_original, (uint32_t)strlen(key_original));
    Md5Finalise(&md5Context, &md5Hash);

    char tmp[3] = {}, buf[33] = {};
    int j;
    for (j = 0; j < sizeof(md5Hash); j++)
    {
        sprintf(tmp, "%02X", md5Hash.bytes[j]);
        strcat(buf, tmp);
    }
    return buf;
}

/**
 * 加密
 */
char *_encrypt(char *str)
{
    char *key = DEFAULT_KEY;

    //计算字符串的长度  (字符长度/16)*16+16  确保长度为16的倍数
    long strLen = ((strlen(str) / SIZE_M) * SIZE_M) + SIZE_M;

    char *aesEnc = malloc(strLen);
    memset(aesEnc, 0, strLen);

    AES_KEY aes_key;
    //设置AES加密密钥
    AES_set_encrypt_key(key, 256, &aes_key);
    int i = 0;
    //AES加密
    for (i = 0; i < strlen(str); i += SIZE_M)
    {
	AES_encrypt(str + i, aesEnc + i, &aes_key);
    }

    //Base64一次加密
    char *basEnc = base64_encode(aesEnc, strlen(aesEnc));

    //拼接 到 加密字符
    char len[strLen + 2];
    sprintf(len, "%d__", strLen);
    char *len_str = str_combine(len, basEnc);

    // Base64二次加密
    char *result = base64_encode(len_str, strlen(len_str));

    return result;
}

/**
 * 加密
 */
char *_encrypt_key(char *key, char *str)
{

    //计算字符串的长度  (字符长度/16)*16+16  确保长度为16的倍数
    long strLen = ((strlen(str) / SIZE_M) * SIZE_M) + SIZE_M;

    char *aesEnc = malloc(strLen);
    memset(aesEnc, 0, strLen);

    AES_KEY aes_key;
    //设置AES加密密钥
    AES_set_encrypt_key(key, 256, &aes_key);
    int i = 0;
    //AES加密
    for (i = 0; i < strlen(str); i += SIZE_M)
    {
	AES_encrypt(str + i, aesEnc + i, &aes_key);
    }

    //Base64一次加密
    char *basEnc = base64_encode(aesEnc, strlen(aesEnc));

    //拼接 到 加密字符
    char len[strLen + 2];
    sprintf(len, "%d__", strLen);
    char *len_str = str_combine(len, basEnc);

    // Base64二次加密
    char *result = base64_encode(len_str, strlen(len_str));

    return result;
}

/**
 * 解密
 */
char *_decrypt(char *str)
{
    char *key = DEFAULT_KEY;

    //Base64一次解密
    char *len_str = base64_decode(str, strlen(str));

    //分割字符串
    char strs[strlen(len_str)];
    char cstrlen[SIZE_M];
    sscanf(len_str, "%[0-9]__%[^.]", cstrlen, strs);
    long strLen = atoi(cstrlen);

    //Base64二次解密
    char *basDec = base64_decode(strs, strlen(strs));

    char *aesDec = malloc(strLen);
    memset(aesDec, 0, strLen);
    AES_KEY aes_key;
    AES_set_decrypt_key(key, 256, &aes_key);
    int i = 0;
    //AES解密，解密basDec，得aesDec
    for (i = 0; i < strLen; i += SIZE_M)
    {
	AES_decrypt(basDec + i, aesDec + i, &aes_key);
    }
    return aesDec;
}

/**
 * 解密
 */
char *_decrypt_key(char *key, char *str)
{
    //Base64一次解密
    char *len_str = base64_decode(str, strlen(str));

    //分割字符串
    char strs[strlen(len_str)];
    char cstrlen[SIZE_M];
    sscanf(len_str, "%[0-9]__%[^.]", cstrlen, strs);
    long strLen = atoi(cstrlen);

    //Base64二次解密
    char *basDec = base64_decode(strs, strlen(strs));

    char *aesDec = malloc(strLen);
    memset(aesDec, 0, strLen);
    AES_KEY aes_key;
    AES_set_decrypt_key(key, 256, &aes_key);
    int i = 0;
    //AES解密，解密basDec，得aesDec
    for (i = 0; i < strLen; i += SIZE_M)
    {
	AES_decrypt(basDec + i, aesDec + i, &aes_key);
    }
    return aesDec;
}