
package indi.toaok.rxandroiddemo.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import indi.toaok.ndk.Security;
import indi.toaok.rxandroiddemo.R;
import indi.toaok.rxandroiddemo.model.AppInfo;
import indi.toaok.rxandroiddemo.model.AppInfoRich;
import indi.toaok.rxandroiddemo.ui.adapter.AppInfoListAdapter;
import indi.toaok.rxandroiddemo.ui.adapter.AppInfoRichListAdapter;
import indi.toaok.rxandroiddemo.ui.base.BaseActivity;
import indi.toaok.rxandroiddemo.ui.base.BaseRecyclerAdapter;
import indi.toaok.rxandroiddemo.utils.FileIOUtils;
import indi.toaok.rxandroiddemo.utils.FilePathUtil;
import indi.toaok.rxandroiddemo.utils.ImageUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    public static void startActvity(Context packageContext) {
        Intent intent = new Intent();
        intent.setClass(packageContext, MainActivity.class);
        packageContext.startActivity(intent);
    }


    @BindView(R.id.recycler_view_apps)
    RecyclerView mRecyclerView;

    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<AppInfo> mAppInfos = new ArrayList();
    private List<AppInfoRich> mAppInfoRiches = new ArrayList();

    private BaseRecyclerAdapter mRecyclerAdapter;

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
        initAppInfoAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        if (mRecyclerAdapter != null) {
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }
        mSwipeRefreshLayout.setColorSchemeColors(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                        getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            refreshAppList();
        });
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        refreshAppList();
    }

    private void initAppInfoRichAdapter() {
        mRecyclerAdapter = new AppInfoRichListAdapter(this, mAppInfoRiches);
    }

    private void initAppInfoAdapter() {
        mRecyclerAdapter = new AppInfoListAdapter(this, mAppInfos);
    }


    private Observable<AppInfo> getApps() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        return Observable.fromIterable(getPackageManager().queryIntentActivities(mainIntent, 0))
                .map(resolveInfo -> {
                    AppInfoRich appInfoRich = new AppInfoRich(MainActivity.this, resolveInfo);
                    Bitmap icon = ImageUtils.drawable2Bitmap(appInfoRich.getIcon());
                    String name = appInfoRich.getName();
                    //保存PNG格式图片时要注意加上后缀,不然不会以png方式读取
                    String iconPath = FilePathUtil.getCacheImagePath() + name + ".png";
                    byte[] iconBytes = ImageUtils.bitmap2Bytes(icon, Bitmap.CompressFormat.PNG);
                    String iconStr = android.util.Base64.encodeToString(iconBytes, android.util.Base64.DEFAULT);
                    Log.d(TAG, "原数据" + iconStr);
                    String iconStrEncode= Security.encrypt(iconStr);
                    Log.d(TAG, "加密后数据" + iconStrEncode);
                    FileIOUtils.writeFileFromString(iconPath,iconStrEncode);
                    ImageUtils.save(icon, iconPath, Bitmap.CompressFormat.PNG);
                    mAppInfoRiches.add(appInfoRich);
                    return new AppInfo(name, iconPath, appInfoRich.getVersion(), appInfoRich.getLastUpdateTime());
                });
    }

    @SuppressLint("CheckResult")
    private void refreshAppList() {
        mRecyclerAdapter.clear();
        getApps().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(new Observer<AppInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Toast.makeText(MainActivity.this, "list has subscribe!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerAdapter.append(appInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "There is the list!", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }).subscribe();
    }
}
