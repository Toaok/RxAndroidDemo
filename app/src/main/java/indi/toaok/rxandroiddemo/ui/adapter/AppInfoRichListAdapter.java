package indi.toaok.rxandroiddemo.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.List;

import indi.toaok.rxandroiddemo.R;
import indi.toaok.rxandroiddemo.model.AppInfo;
import indi.toaok.rxandroiddemo.model.AppInfoRich;
import indi.toaok.rxandroiddemo.ui.base.BaseRecyclerAdapter;
import indi.toaok.rxandroiddemo.ui.base.BaseViewHolder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author Toaok
 * @version 1.0  2019/3/13.
 */
public class AppInfoRichListAdapter extends BaseRecyclerAdapter<AppInfoRich> {


    public AppInfoRichListAdapter(Context context, List<AppInfoRich> data) {
        super(context, data);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_app_info;
    }

    @Override
    public void bindData(BaseViewHolder holder, int position) {
        AppInfoRich appInfoRich = mData.get(position);
        holder.getImageView(R.id.iv_app_icon).setImageDrawable(appInfoRich.getIcon());

        holder.getTextView(R.id.tv_app_name).setText(appInfoRich.getName());

        holder.getTextView(R.id.tv_version).setText(appInfoRich.getVersion());
    }
}
