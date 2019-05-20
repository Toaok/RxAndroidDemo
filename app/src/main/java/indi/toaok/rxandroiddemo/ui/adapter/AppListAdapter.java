package indi.toaok.rxandroiddemo.ui.adapter;

import android.graphics.BitmapFactory;

import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import indi.toaok.rxandroiddemo.R;
import indi.toaok.rxandroiddemo.model.AppInfo;
import indi.toaok.rxandroiddemo.model.SectionMultipleItem;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author Toaok
 * @version 1.0  2019/3/13.
 */
public class AppListAdapter extends BaseSectionMultiItemQuickAdapter<SectionMultipleItem, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public AppListAdapter(int sectionHeadResId, List<SectionMultipleItem> data) {
        super(sectionHeadResId, data);
        addItemType(SectionMultipleItem.SYSTEM_APP, R.layout.item_system_app_info);
        addItemType(SectionMultipleItem.GOOGLE_APP, R.layout.item_google_app_info);
        addItemType(SectionMultipleItem.OTHER_APP, R.layout.item_app_info);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionMultipleItem item) {
        helper.setText(R.id.header, item.header);
        helper.addOnClickListener(R.id.card_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionMultipleItem item) {
        helper.addOnClickListener(R.id.card_view);
        AppInfo appInfo = item.getAppInfo();
        switch (helper.getItemViewType()) {
            case SectionMultipleItem.GOOGLE_APP:
                Observable.just(appInfo.getIcon())
                        .map(iconPath -> BitmapFactory.decodeFile(iconPath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bitmap -> helper.setImageBitmap(R.id.iv_app_icon, bitmap));
                break;
            case SectionMultipleItem.SYSTEM_APP:
                Observable.just(appInfo.getIcon())
                        .map(iconPath -> BitmapFactory.decodeFile(iconPath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bitmap -> helper.setImageBitmap(R.id.iv_app_icon, bitmap));
                helper.setText(R.id.tv_app_name,appInfo.getName());
                helper.setText(R.id.tv_version,appInfo.getVersion());
                break;
            case SectionMultipleItem.OTHER_APP:
                Observable.just(appInfo.getIcon())
                        .map(iconPath -> BitmapFactory.decodeFile(iconPath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bitmap -> helper.setImageBitmap(R.id.iv_app_icon, bitmap));
                helper.setText(R.id.tv_app_name,appInfo.getName());
                helper.setText(R.id.tv_version,appInfo.getVersion());
                break;
            default:
                break;
/*
*  Observable.just(appInfo.getIcon())
                .map(iconPath -> BitmapFactory.decodeFile(iconPath))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> holder.getImageView(R.id.iv_app_icon).setImageBitmap(bitmap));

        holder.getTextView(R.id.tv_app_name).setText(appInfo.getName());

        holder.getTextView(R.id.tv_version).setText(appInfo.getVersion());
* */
        }
    }
}
