package com.appframe;

import android.app.Application;

import com.appframe.config.AppConfig;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Roy
 * Date: 16/4/15
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        AppConfig.init(this);

    }

    /**
     * 应用退出
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        onExit();
    }

    /**
     * 应用内存不足
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //清理内存中的图片缓存
        ImageLoader.getInstance().clearMemoryCache();
    }

    /**
     * 应用退出，由AppManager回调
     */
    public void onExit() {
        //清理内存中的图片缓存
        ImageLoader.getInstance().clearMemoryCache();
    }

}

