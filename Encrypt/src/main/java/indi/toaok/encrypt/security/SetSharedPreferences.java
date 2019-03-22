package indi.toaok.encrypt.security;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;

import java.util.HashMap;

import indi.toaok.encrypt.uitl.JSONParserHelper;

/**
 * SharedPreferences 抽象基类 集合加密基类
 * Created by sj on 10/22/16.
 */

public abstract class SetSharedPreferences {

    private static final String EXTRA_DEVICE_KEY = "EXTRA_DEVICE_KEY";

    private String _SET_KEY;
    private String _DEVICE_KEY;
    private String _BASESALT_KEY;

    private HashMap<String, String> config;

    protected SetSharedPreferences() {
        super();
        _BASESALT_KEY = initBaseSalt();
        _SET_KEY = initSetKey();
        _DEVICE_KEY = initDeviceKey();
        if (TextUtils.isEmpty(_BASESALT_KEY)) {
            throw new NullPointerException("BASE_SALT can't be null!");
        }
        if (TextUtils.isEmpty(_SET_KEY)) {
            throw new NullPointerException("SET_KEY can't be null!");
        }
        if (isCheckDeviceKey() && TextUtils.isEmpty(_DEVICE_KEY)) {
            throw new NullPointerException("DEVICE_KEY can't be null!");
        }
    }

    protected abstract String initBaseSalt();

    protected abstract String initSetKey();

    protected abstract String initDeviceKey();

    protected boolean isCheckDeviceKey() {
        return true;
    }

    protected HashMap<String, String> getConfigMap(Context context) throws JSONException{
        if (config != null) {
            return config;
        }

        String json = SharedPreferencesUtil.getSecurityString(context, _BASESALT_KEY, _SET_KEY);
        if (TextUtils.isEmpty(json)) {
            return new HashMap<>();
        }
        return JSONParserHelper.jsonToMap(json);
    }

    public int put(Context context, String key, String value) {
        try {
            config = getConfigMap(context);
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
        config.put(key, value);

        /**
         * 写入设备key
         */
        if (isCheckDeviceKey()) {
            if (TextUtils.isEmpty(_DEVICE_KEY)) {
                throw new NullPointerException("DEVICE_KEY can't be null!");
            }

            if (!config.containsKey(EXTRA_DEVICE_KEY)) {
                config.put(EXTRA_DEVICE_KEY, value);
            }
        }
        return SharedPreferencesUtil.putMapData(context, _BASESALT_KEY, _SET_KEY, config);
    }

    public Object get(Context context, String key) {
        try {
            config = getConfigMap(context);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        /**
         * 校验设备失败，防止数据被复制使用
         */
        if (!checkDevice()) {
            return null;
        }
        return config == null ? null : config.get(key);
    }

    public void remove(Context context, String key) {
        try {
            config = getConfigMap(context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (config == null) {
            return;
        }
        config.remove(key);
        SharedPreferencesUtil.putMapData(context, _BASESALT_KEY, _SET_KEY, config);
    }

    public void clear(Context context, String key) {
        SharedPreferencesUtil.removeSecurityString(context, key);
    }

    private boolean checkDevice() {
        // 无需检验设备
        if (!isCheckDeviceKey()) {
            return true;
        }

        if (TextUtils.isEmpty(_DEVICE_KEY)) {
            throw new NullPointerException("DEVICE_KEY can't be null!");
        }

        if (config == null) {
            return true;
        }

        // 不包含设备校验id
        if (!config.containsKey(EXTRA_DEVICE_KEY)) {
            return true;
        }

        // 校验成功
        if (_DEVICE_KEY.equals(config.get(EXTRA_DEVICE_KEY))) {
            return true;
        }
        return false;
    }
}
