package indi.toaok.rxandroiddemo.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import indi.toaok.rxandroiddemo.ui.base.BaseActivity;

/**
 * @author Toaok
 * @version 1.0  2019/3/18.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.startActvity(this);
        finish();
    }
}
