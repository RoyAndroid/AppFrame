package com.appframe.lib.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.appframe.MyApplication;
import com.appframe.lib.Log.L;
import com.appframe.lib.ToastUtil.ToastHelper;

import java.io.File;
import java.util.List;

/**
 * Created by Roy
 * Date: 15/11/24
 */
public class AndroidUtils {

    /**
     * 打开软键盘
     *
     * @param window
     */
    public static void showSoftKeyBoard(final Window window) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (window.getCurrentFocus() != null) {
                    InputMethodManager inputManager = (InputMethodManager) window.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInputFromInputMethod(window.getCurrentFocus().getWindowToken(), 0);
                }
            }
        }, 200);
    }

    /**
     * 关闭软键盘
     *
     * @param window
     */
    public static void hideSoftKeyBoard(final Window window) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (window.getCurrentFocus() != null) {
                    InputMethodManager inputManager = (InputMethodManager) window.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), 0);
                }
            }
        }, 200);
    }

    /**
     * 安装apk
     *
     * @param apkFilePath
     */
    public static void installApk(Context ctx, String apkFilePath) {
        File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            ToastHelper.showToast(ctx, "安装主程序失败，找不到主程序文件，请尝试重新更新。");
            return;
        }
        L.d("install apk: " + apkfile.getPath());
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }


    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            L.e("程序包名无法找到", e);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 获取metaData信息
     */
    public static String getMetaData(Context context, String key) {
        String metaData = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            metaData = appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            L.e("获取metaData信息", e);
        } catch (Exception e) {

        }
        return metaData;
    }

    /**
     * 获取当前程序版本名称
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // Get the package info
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            L.e("获取当前程序版本名称", e);
        } catch (Exception e) {

        }
        return versionName;
    }

    /**
     * 获取程序版本名称
     */
    public static String getAppVersionName(Context context, String packageName) {
        String versionName;
        try {
            // Get the package info
            PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            L.d("获取程序版本名称", e);
            return "";
        } catch (Exception e) {
            return "";
        }
        return versionName;
    }

    /**
     * 获取当前代码版本号
     */
    public static int getAppVersionCode(Context context) {
        int localVersion = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            localVersion = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            L.e("获取当前代码版本号", e);
        } catch (Exception e) {

        }
        return localVersion;
    }

    /**
     * 比较两个app版本号，看是否需要更新
     *
     * @param targetVersion
     * @param baseVersion
     * @return true if targetVersion > baseVersion,return true if haven't
     * install
     */
    public static boolean needToUpdate(String targetVersion, String baseVersion) {
        if (StringUtils.isEmpty(targetVersion)) {
            return false;// empty target, return false
        }
        if (StringUtils.isEmpty(baseVersion)) {
            return true;// not install, return true
        }
        List<String> targetItems = StringUtils.stringToList(targetVersion, StringUtils.VERSION_SEPERATOR);
        List<String> baseItems = StringUtils.stringToList(baseVersion, StringUtils.VERSION_SEPERATOR);
        Log.e("targetItems", targetItems.toString());
        Log.e("baseItems", baseItems.toString());

        if (CollectionUtils.isEmpty(targetItems) || CollectionUtils.isEmpty(baseItems)) {
            return false;
        }

        final int targetSize = targetItems.size();
        final int baseSize = baseItems.size();
        final int total = targetSize > baseSize ? targetSize : baseSize;

        for (int i = 0; i < total; i++) {
            int targetV = (i >= targetSize) ? 0 : Integer.parseInt(targetItems.get(i));
            int baseV = (i >= baseSize) ? 0 : Integer.parseInt(baseItems.get(i));
            if (targetV > baseV) {
                return true;
            }
            if (targetV < baseV) {
                return false;
            }

        }
        return false;
    }

    /**
     * 获取当前系统版本名称
     */
    public static String getSystemVersionName(){
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取当前android手机型号
     */
    public static String getDriverModel() {
        String DeviceModel;
        DeviceModel = Build.MODEL;
        if (TextUtils.isEmpty(DeviceModel)) {
            return "";
        }
        return DeviceModel;
    }

    /**
     * 获取当前系统版本号
     */
    public static String getDriverVersionName() {
        String DeviceVersionName;
        DeviceVersionName = Build.VERSION.RELEASE;
        if (TextUtils.isEmpty(DeviceVersionName)) {
            return "";
        }
        return DeviceVersionName;
    }

    public static String getImei(Context context) {
        String imei = "";
        try {
            imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {

        }

        return imei;
    }

    /**
     * 是否锁屏状态
     *
     * @return
     */
    public static boolean isKeyguard() {
        KeyguardManager mKeyguardManager = (KeyguardManager) MyApplication.getInstance().getSystemService(MyApplication.getInstance().KEYGUARD_SERVICE);

        if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
            return true;
        }
        return false;
    }

    public static boolean hasGingerbread() { /*2.3.1 API 9*/
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {/*3.0.X  API 11*/
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {/*3.1.X  API 12*/
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasHoneycombMR2() {/*3.2 API 13*/
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    public static boolean hasIceCreamSandwich() {/*4.0, 4.0.1, 4.0.2 API 14*/
        return Build.VERSION.SDK_INT > -Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasJellyBean() {/*4.1, 4.1.1  API 16 */
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {/*4.4  API 19 */
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
}
