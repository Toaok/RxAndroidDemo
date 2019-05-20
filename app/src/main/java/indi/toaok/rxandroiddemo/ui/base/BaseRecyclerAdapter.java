package indi.toaok.rxandroiddemo.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import indi.toaok.rxandroiddemo.R;

/**
 * @author Toaok
 * @version 1.0  2019/3/13.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {


    protected Context mContext;

    protected List<T> mData;

    protected boolean mIsUserAnimation;

    protected RecyclerView.LayoutManager mLayoutManager;

    public LayoutInflater mInflater;

    OnItemClickListener mOnItemClickListener;

    public BaseRecyclerAdapter(Context context, List<T> data) {
        this(context, data, true);
    }

    public BaseRecyclerAdapter(Context context, List<T> data, boolean isUserAnimation) {
        this(context, data, isUserAnimation, null);
    }

    public BaseRecyclerAdapter(Context context, List<T> data, boolean isUserAnimation, RecyclerView.LayoutManager layoutManager) {
        mContext = context;
        mData = data;
        mIsUserAnimation = isUserAnimation;
        mLayoutManager = layoutManager;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (mIsUserAnimation
                && holder.itemView.getAnimation() != null
                && holder.itemView.getAnimation().hasStarted()) {
            holder.itemView.clearAnimation();
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_app_info, parent, false);

        return new BaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (mData != null && position < mData.size()) {
            bindData(holder, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mData != null) {
            count = mData.size();
        }
        return count;
    }

    public void append(T item) {
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
    }

    public void insert(@IntRange(from = 0) int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(@IntRange(from = 0) int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(T item) {
        int position = mData.indexOf(item);
        if (position > 0) {
            mData.remove(position);
            notifyItemInserted(position);
        } else {
            throw new IndexOutOfBoundsException("the index=" + position + "in" + mData.getClass().getSimpleName());
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    protected void setAnimation(View viewToAnimate, int postion) {
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public abstract @LayoutRes
    int getItemLayoutId();

    public abstract void bindData(BaseViewHolder holder, int position);

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickLiistener {
        void onItemLongClick(View view, int position);
    }

}
