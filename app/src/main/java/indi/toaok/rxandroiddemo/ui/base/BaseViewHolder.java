package indi.toaok.rxandroiddemo.ui.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Toaok
 * @version 1.0  2019/3/13.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }


    private <T extends View> T findViewById(@IdRes int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mViews.put(resId, view);
        }
        return (T) view;
    }

    public View getView(@IdRes int resId) {
        return findViewById(resId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public ImageButton getImageButton(int viewId) {
        return (ImageButton) getView(viewId);
    }

    public EditText getEditText(int viewId) {
        return (EditText) getView(viewId);
    }



    public BaseViewHolder setOnClickListener(@IdRes int resId, View.OnClickListener listener) {
        findViewById(resId).setOnClickListener(listener);
        return this;
    }

}
