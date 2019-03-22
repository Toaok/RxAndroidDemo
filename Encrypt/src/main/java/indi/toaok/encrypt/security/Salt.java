package indi.toaok.encrypt.security;

import indi.toaok.ndk.Security;

/**
 * Created by sj on 16/03/2017.
 */

public class Salt {

    protected static String getSalt(String baseSalt){
        /**
         * 返回salt
         */
        return Security.getSalt(baseSalt);
    }
}
