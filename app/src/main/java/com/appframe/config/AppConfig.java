package com.appframe.config;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.appframe.biz.Factory.ImageOptionFactory;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by Roy
 * Date: 16/4/15
 */
public class AppConfig {
    public static void init(Application context) {

//        /* 初始化ActivtAndroid */
        ActiveAndroid.initialize(context);

        /* 初始化 ImageLoader */
        initImageLoader(context);

    }

    private static void initImageLoader(Context context) {
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 10;  //系统可用内存的1/10

        // File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(context.getApplicationContext())
                .threadPoolSize(2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(cacheSize))
                .memoryCacheSize(cacheSize)
                .diskCache(new UnlimitedDiskCache(new File(Config.getAppImagesPath()))) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(500)
                .defaultDisplayImageOptions(ImageOptionFactory.getDefaultImageOptions())
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
