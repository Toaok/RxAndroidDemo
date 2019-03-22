package indi.toaok.rxandroiddemo;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import indi.toaok.rxandroiddemo.utils.Utils;

/**
 * @author Toaok
 * @version 1.0  2019/3/12.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
