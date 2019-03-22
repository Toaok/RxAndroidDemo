package indi.toaok.encrypt.security.crypt;

import android.text.TextUtils;

import java.security.GeneralSecurityException;

/**
 * Created by sj on 15/03/2017.
 */

public class AESCrypt extends AESCryptBase {

    private AESCrypt() {
        super();
    }

    public static String encryptStr(String salt, String content) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(salt)) {
            return "";
        }
        try {
            return encrypt(salt, content);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decryptStr(String salt, String content) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(salt)) {
            return "";
        }
        try {
            return decrypt(salt, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
