package indi.toaok.ndk;

/**
 * @author Toaok
 * @version 1.0  2019/3/11.
 */
public class Security {
    static {
        System.loadLibrary("Security");
    }

    /**
     * 获取盐  空为 -> 大众出行约定秘钥
     * @param key 基础key (真正的盐由 基础key + c库key 加密获取)
     * @return
     */
    public static native String getSalt(String key);

    /**
     * 开发者模式
     */
    public static native boolean isDz(String key);

    /**
     * 获取so库内置的签名数组，以为 , 分割
     */
    public static native String getSign();

    /**
     * 获取版本号
     */
    public static native String getVersion();

    /**
     * 加密 使用内置默认秘钥 （此处加解密只做秘钥加解密，其他加解密建议使用 {@link AESCrypt}）
     * @param data
     * @return
     */
    public static native String encrypt(String data) throws Exception;

    /**
     * 解密 使用内置默认秘钥 （此处加解密只做秘钥加解密，其他加解密建议使用 {@link AESCrypt}）
     * @param data
     * @return
     */
    public static native String decrypt(String data) throws Exception;

    /**
     * 加密 使用自定义秘钥 （此处加解密只做秘钥加解密，其他加解密建议使用 {@link AESCrypt}）
     * @param data
     * @return
     */
    public static native String encryptWithKey(String key, String data) throws Exception;

    /**
     * 解密 使用自定义秘钥 （此处加解密只做秘钥加解密，其他加解密建议使用 {@link AESCrypt}）
     * @param data
     * @return
     */
    public static native String decryptWithKey(String key, String data) throws Exception;
}
