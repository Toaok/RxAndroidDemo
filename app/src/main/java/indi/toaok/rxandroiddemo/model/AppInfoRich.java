package indi.toaok.rxandroiddemo.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import indi.toaok.rxandroiddemo.BuildConfig;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author Toaok
 * @version 1.0  2019/3/12.
 */
@Data
@Accessors(prefix = "m")
public class AppInfoRich {

    private static final String TAG=AppInfoRich.class.getSimpleName();

    @Getter
    private ResolveInfo mResolveInfo;
    @Getter
    private PackageInfo mPackageInfo;
    @Getter
    private Context mContext;
    @Getter
    private Context mOtherContext;

    private Drawable mIcon;
    private Drawable mOriginIcon;
    private String mName;
    private String mVersion;


    public AppInfoRich(@NonNull Context context, @NonNull ResolveInfo resolveInfo) {
        mResolveInfo = resolveInfo;
        mContext = context;
        try {
            mPackageInfo = mContext.getPackageManager().getPackageInfo(mResolveInfo.activityInfo.packageName, 0);
            mOtherContext = mContext.createPackageContext(mPackageInfo.applicationInfo.packageName, Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Drawable getOriginIcon() {
        if (mOriginIcon == null) {
            if (Build.VERSION.SDK_INT > 21)
                mOriginIcon = mOtherContext.getDrawable(mResolveInfo.getIconResource());
            else
                mOriginIcon = mResolveInfo.loadIcon(mContext.getPackageManager());
        }
        return mOriginIcon;
    }

    public Drawable getIcon() {
        if (mIcon == null) {
            mIcon = mResolveInfo.loadIcon(mContext.getPackageManager()).mutate();
        }
        return mIcon;
    }

    public String getName() {
        if (TextUtils.isEmpty(mName)) {
            mName = mResolveInfo.loadLabel(mContext.getPackageManager()).toString();
        }
        return mName;
    }


    public String getVersion() {
        if (TextUtils.isEmpty(mVersion)) {
            mVersion = mPackageInfo.versionName;
        }
        return mVersion;
    }

    public long getLastUpdateTime() {
        if (mPackageInfo == null) {
            try {
                mPackageInfo = mContext.getPackageManager().getPackageInfo(mResolveInfo.activityInfo.packageName, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return mPackageInfo.lastUpdateTime;
    }

}
