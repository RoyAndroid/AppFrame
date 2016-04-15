package com.appframe.lib.utils;

import com.appframe.config.Constants;

/**
 * Created by Roy
 * Date: 15/11/23
 */
public class PreventContinuousClickUtil {
    private static long lastClickTime;

    public static boolean isContinuousClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < Constants.HALF_SECOND) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
