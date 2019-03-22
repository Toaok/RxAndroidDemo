package indi.toaok.rxandroiddemo.utils;

import java.io.File;

/**
 * 外部存储
 */
public class FilePathUtil {

    private static String getBaseDirPath(String childDir) {
        return PathUtils.getInternalAppCachePath()+ "/" + Utils.getAppName() + "/" + childDir;
    }

    public static String getCacheCropPath() {
        return checkAndMkdirs(getBaseDirPath("cache/crop/"));
    }

    public static String getCacheImagePath() {
        return checkAndMkdirs(getBaseDirPath("cache/images/"));
    }


    public static String getCacheImagePickPath() {
        return checkAndMkdirs(getBaseDirPath("cache/image/")) + System.currentTimeMillis() + ".jpg";
    }

    public static String getCacheWebPath() {
        return checkAndMkdirs(getBaseDirPath("cache/web/"));
    }

    public static String getCache() {
        return checkAndMkdirs(getBaseDirPath("cache/"));
    }

    public static String getImagePath() {
        return checkAndMkdirs(getBaseDirPath("images/"));
    }

    public static String getSplashImagePath() {
        return checkAndMkdirs(getBaseDirPath("images/splash/"));
    }

    /**
     * 检查文件夹是否存在
     *
     * @param dir
     * @return
     */
    public static String checkAndMkdirs(String dir) {
        File file = new File(dir);
        if (file.exists() == false) {
            file.mkdirs();
        }
        return dir;
    }

}