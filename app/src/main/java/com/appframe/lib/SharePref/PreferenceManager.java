package com.appframe.lib.SharePref;

import android.content.Context;
import android.content.SharedPreferences;

import com.appframe.MyApplication;

/**
 * Created by Roy
 * Date: 15/4/18
 */
public class PreferenceManager {
    private static SharedPreferences getUserPreferences() {
        return MyApplication.getInstance().getSharedPreferences(SharePrefKeys.PREF_USER_NAME, Context.MODE_PRIVATE);
    }

    public static void setData(String key, Object object) {
        SharedPreferences.Editor editor;
        editor = getUserPreferences().edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, ((Integer) object).intValue());
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) object).booleanValue());
        } else if (object instanceof Long) {
            editor.putLong(key, ((Long) object).longValue());
        } else if (object instanceof Float) {
            editor.putFloat(key, ((Float) object).floatValue());
        }
        editor.commit();
    }

    public static void removeData(String key) {
        SharedPreferences.Editor editor = getUserPreferences().edit();
        editor.remove(key);
        editor.commit();
    }


    public static String getStringData(String key) {
        return getUserPreferences().getString(key, "");
    }

    public static int getIntData(String key) {
        return getUserPreferences().getInt(key, 0);
    }

    public static int getSpecialIntData(String key, int special) {
        return getUserPreferences().getInt(key, special);
    }

    public static long getLongData(String key) {
        return getUserPreferences().getLong(key, 0);
    }

    public static Float getFloatData(String key) {
        return getUserPreferences().getFloat(key, 0);
    }

    public static boolean getBooleanData(String key) {
        return getUserPreferences().getBoolean(key, false);
    }

    public static void cleanUserPreference() {
        SharedPreferences.Editor editor = getUserPreferences().edit();
        editor.clear();
        editor.commit();
    }
}
