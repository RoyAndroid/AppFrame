package com.appframe.config;

/**
 * Created by Roy
 * Date: 15/11/23
 */
public class Constants {
    /**
     * 开发环境配置信息
     */
//    public final static boolean DEV_MODE = BuildConfig.DEBUG;
    public final static boolean DEV_MODE = true;

    /* 半秒 */
    public final static int HALF_SECOND = 500;

    /* 网络请求一次条数 */
    public final static int PAGESIZE = 20;

    /*选择图片的最大数目 */
    public static final int MAX_VALUE_PHOTOS = 30;

    // TODO 临时记录在这个文件夹，后期统一整理
    /* 拍摄图片保存文件夹 */
    public final static String CameraImageFileDictory = Config.getAppTempPath() + "cameraImage/";
    /* 裁剪图片保存文件夹 */
    public final static String CropImageFileDictory = Config.getAppTempPath() + "cropImage/";

    public static final String X_CLIENT_VERSION = "X-CLIENT-VERSION";

}
