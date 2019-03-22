package indi.toaok.rxandroiddemo.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;

import java.util.List;

import indi.toaok.ndk.Security;
import indi.toaok.rxandroiddemo.R;
import indi.toaok.rxandroiddemo.model.AppInfo;
import indi.toaok.rxandroiddemo.ui.base.BaseRecyclerAdapter;
import indi.toaok.rxandroiddemo.ui.base.BaseViewHolder;
import indi.toaok.rxandroiddemo.utils.FileIOUtils;
import indi.toaok.rxandroiddemo.utils.ImageUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author Toaok
 * @version 1.0  2019/3/13.
 */
public class AppInfoListAdapter extends BaseRecyclerAdapter<AppInfo> {

    public static final String TAG = AppInfoListAdapter.class.getSimpleName();

    public AppInfoListAdapter(Context context, List<AppInfo> data) {
        super(context, data);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_app_info;
    }

    @Override
    public void bindData(BaseViewHolder holder, int position) {
        AppInfo appInfo = mData.get(position);
        Observable.just(appInfo.getIcon())
                .map(iconPath -> {
                    String iconStr = FileIOUtils.readFile2String(iconPath);
                    Log.d(TAG, "读取数据" + iconStr);
                    String iconStrDecrypt = Security.decrypt(iconStr);
                    Log.d(TAG, "解密后数据" + iconStrDecrypt);
                    return ImageUtils.bytes2Bitmap(Base64.decode(iconStr, 0));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> holder.getImageView(R.id.iv_app_icon).setImageBitmap(bitmap));

        holder.getTextView(R.id.tv_app_name).setText(appInfo.getName());

        holder.getTextView(R.id.tv_version).setText(appInfo.getVersion());
    }
}
