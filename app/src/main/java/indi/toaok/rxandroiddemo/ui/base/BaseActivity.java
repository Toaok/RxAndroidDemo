package indi.toaok.rxandroiddemo.ui.base;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Toaok
 * @version 1.0  2019/3/13.
 */
public class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
