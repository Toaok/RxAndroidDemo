
package indi.toaok.rxandroiddemo.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import indi.toaok.rxandroiddemo.R;
import indi.toaok.rxandroiddemo.model.AppInfo;
import indi.toaok.rxandroiddemo.model.AppInfoRich;
import indi.toaok.rxandroiddemo.model.SectionMultipleItem;
import indi.toaok.rxandroiddemo.ui.adapter.AppListAdapter;
import indi.toaok.rxandroiddemo.ui.base.BaseActivity;
import indi.toaok.rxandroiddemo.utils.FilePathUtil;
import indi.toaok.rxandroiddemo.utils.ImageUtils;
import io.reactivex.Observable;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    public static void startActvity(Context packageContext) {
        Intent intent = new Intent();
        intent.setClass(packageContext, MainActivity.class);
        packageContext.startActivity(intent);
    }


    @BindView(R.id.recycler_view_apps)
    RecyclerView mRecyclerView;

    private List<AppInfo> mAppInfos = new ArrayList();
    private List<AppInfoRich> mAppInfoRiches = new ArrayList();

    private AppListAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @SuppressLint("WrongConstant")
    private void init() {
        initView();
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        initAppAdapter();
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }


    private static final String SYSTEM_APP_PREFIX = "com.android";
    private static final String GOOGLE_APP_PREFIX = "com.google";


    private void initAppAdapter() {
        getAppInfos()
                .map(appInfo -> {
                    if (appInfo.getPackageName().startsWith(SYSTEM_APP_PREFIX)) {
                        return new SectionMultipleItem(SectionMultipleItem.SYSTEM_APP, SectionMultipleItem.SYSTEM_APP_SPAN_SIZE, appInfo);
                    } else if (appInfo.getPackageName().startsWith(GOOGLE_APP_PREFIX)) {
                        return new SectionMultipleItem(SectionMultipleItem.GOOGLE_APP, SectionMultipleItem.GOOGLE_APP_SPAN_SIZE, appInfo);
                    } else {
                        return new SectionMultipleItem(SectionMultipleItem.OTHER_APP, SectionMultipleItem.OTHER_APP_SPAN_SIZE, appInfo);
                    }
                })
                .toList()
                .subscribe(sectionMultipleItems -> {
                            for (int i = 0; i < sectionMultipleItems.size(); i++) {
                                SectionMultipleItem sectionMultipleItem = sectionMultipleItems.get(i);
                                if (i > 0) {
                                    SectionMultipleItem previousSectionMultipleItem = sectionMultipleItems.get(i - 1);
                                    if (previousSectionMultipleItem.getItemType() != 0 && sectionMultipleItem.getItemType() != previousSectionMultipleItem.getItemType()) {
                                        sectionMultipleItems.add(i, getSectionHeader(sectionMultipleItem.getItemType()));
                                    }
                                } else {
                                    sectionMultipleItems.add(i, getSectionHeader(sectionMultipleItem.getItemType()));
                                }
                            }
                            mRecyclerAdapter = new AppListAdapter(R.layout.layout_section_head, sectionMultipleItems);
                            mRecyclerAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    SectionMultipleItem item = (SectionMultipleItem) adapter.getData().get(position);
                                    switch (view.getId()) {
                                        case R.id.card_view:
                                            // 获取主体item相应数据给后期使用
                                            if (item.getAppInfo() != null) {
                                                Toast.makeText(MainActivity.this, item.getAppInfo().getPackageName(), Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(MainActivity.this, "OnItemChildSectionHead " + position, Toast.LENGTH_LONG).show();
                                            }
                                            break;
                                        default:
                                            Toast.makeText(MainActivity.this, "OnItemChildClickListener " + position, Toast.LENGTH_LONG).show();
                                            break;

                                    }
                                }
                            });
                            mRecyclerAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                                    return mRecyclerAdapter.getData().get(i).getSpanSize();
                                }
                            });
                        }
                );


    }

    private SectionMultipleItem getSectionHeader(int itemType) {
        switch (itemType) {
            case SectionMultipleItem.SYSTEM_APP:
                return new SectionMultipleItem(true, "系统");
            case SectionMultipleItem.GOOGLE_APP:
                return new SectionMultipleItem(true, "Google");
            default:
                return new SectionMultipleItem(true, "其他");
        }
    }

    private Observable<AppInfo> getAppInfos() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        return Observable.fromIterable(getPackageManager().queryIntentActivities(mainIntent, 0))
                .map(resolveInfo -> {
                    AppInfoRich appInfoRich = new AppInfoRich(MainActivity.this, resolveInfo);
                    Bitmap icon = ImageUtils.drawable2Bitmap(appInfoRich.getIcon());
                    String name = appInfoRich.getName();
                    //保存PNG格式图片时要注意加上后缀,不然不会以png方式读取
                    String iconPath = FilePathUtil.getCacheImagePath() + name + ".png";
                    ImageUtils.save(icon, iconPath, Bitmap.CompressFormat.PNG);
                    return new AppInfo(name, appInfoRich.getPackageName(), iconPath, appInfoRich.getVersion(), appInfoRich.getLastUpdateTime());
                });
    }

}
