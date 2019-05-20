package indi.toaok.rxandroiddemo.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;

/**
 * @author Toaok
 * @version 1.0  2019/5/20.
 */
public class SectionMultipleItem extends SectionMultiEntity<AppInfo> implements MultiItemEntity {


    public static final int SYSTEM_APP = 1;
    public static final int GOOGLE_APP = 2;
    public static final int OTHER_APP = 3;

    public static final int SYSTEM_APP_SPAN_SIZE = 2;

    public static final int GOOGLE_APP_SPAN_SIZE = 1;
    public static final int OTHER_APP_SPAN_SIZE = 4;

    private int itemType;
    private int spanSize;

    private AppInfo mAppInfo;

    public SectionMultipleItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionMultipleItem(int itemType, int spanSize, AppInfo appInfo) {
        super(appInfo);
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.mAppInfo = appInfo;
    }


    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public AppInfo getAppInfo() {
        return mAppInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        mAppInfo = appInfo;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
