package indi.toaok.encrypt.uitl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;

/**
 * Created by sj on 17/03/2017.
 */

public class SignUtil {

    public static String getSign(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature sign;
            if (Build.VERSION.SDK_INT > 28) {
                SigningInfo signingInfo = packageInfo.signingInfo;
                Signature[] signs;
                if (!signingInfo.hasMultipleSigners()) {
                    signs = signingInfo.getApkContentsSigners();
                } else {
                    signs = signingInfo.getSigningCertificateHistory();
                }
                sign = signs[0];
            } else {
                Signature[] signs = packageInfo.signatures;
                sign = signs[0];
            }
            return sign.toCharsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
