package com.appframe.lib.utils;

import android.app.Activity;

import com.appframe.MyApplication;
import com.appframe.biz.extend.BaseActivity;
import com.appframe.biz.manager.AppManager;
import com.appframe.lib.ToastUtil.ToastHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Roy
 * Date: 16/3/10
 */
public class ExitUtil {
    private static boolean isExit = false;

    /**
     * 双击退出函数
     *
     * @param context
     */
    public static void exitForDoubleClick(Activity context) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToastHelper.showToast(context, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            exit(context);
        }
    }

    /**
     * 退出
     *
     * @param context
     */
    public static void exit(Activity context) {
        //context.moveTaskToBack(true);
        if(context instanceof BaseActivity){
            ((BaseActivity)context).getSupportFragmentManager().getFragments().clear();
        }
        ImageLoader.getInstance().clearMemoryCache();
        AppManager.getInstance().AppExit(MyApplication.getInstance());
    }
}
