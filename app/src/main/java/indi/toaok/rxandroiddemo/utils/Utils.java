package indi.toaok.rxandroiddemo.utils;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @author Toaok
 * @version 1.0  2019/3/12.
 */
public class Utils {

    static Application sApplication;

    public static void init(Application application) {
        if (sApplication == null) {
            sApplication = application;
        }
    }

    public static Application getApp(){
        if(sApplication==null)new  Throwable("Please init in application...");
        return sApplication;
    }

    /**
     * Return the application's name.
     *
     * @return the application's name
     */
    public static String getAppName() {
        return getAppName(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's name.
     *
     * @param packageName The name of the package.
     * @return the application's name
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
