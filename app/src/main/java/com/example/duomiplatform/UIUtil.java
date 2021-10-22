package com.example.duomiplatform;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;



import java.io.File;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by ZHP on 2017/6/24.
 */
public class UIUtil {

    public static Context getContext() {
        return LYApplication.getApplication();
    }

    public static Thread getMainThread() {
        return LYApplication.getMainThread();
    }

    private static long getMainThreadId() {
        return LYApplication.getMainThreadId();
    }

    /**
     * 把 xxxdip转成px
     *
     * @param value
     * @return
     */
    public static int dip2px(String value) {
        if (value.contains("dip")) {
            String dip = value.replace("dip", "");
            float dp = Float.parseFloat(dip);
            return dip2px((int) dp);
        }
        return -1;
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取主线程的handler
     */
    private static Handler getHandler() {
        return LYApplication.getMainThreadHandler();
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    private static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }



    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    // 判断当前的线程是不是在主线程
    private static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }


//    /**
//     * 对toast的简易封装。线程安全，可以在非UI线程调用。
//     */
//    public static void showToastSafe(final int resId) {
//        showToastSafe(getString(resId));
//    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final String str) {
        if (isRunInMainThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showLongToastSafe(final String str) {
        if (isRunInMainThread()) {
            showLongToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showLongToast(str);
                }
            });
        }

    }

    public static void showToast(String str) {
        if (LYApplication.getApplication() != null) {
            ToastView toastView = new ToastView();
            toastView.showCenter(str, UIUtil.getContext());
//            Toast toast = Toast.makeText(App.getApplication(), str,
//                    Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
            // ToastUtil.showToast(BaseApplication.getApplication(), str);
        }
    }

    private static void showLongToast(String str) {
        if (LYApplication.getApplication() != null) {
//            Toast toast = Toast.makeText(App.getApplication(), str,
//                    Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
            ToastView toastView = new ToastView();
            toastView.showCenter(str, UIUtil.getContext());
            // ToastUtil.showToast(BaseApplication.getApplication(), str);
        }
    }

    /**
     * 获取控件宽度
     *
     * @param v
     * @return
     */
    public static int getWidth(View v) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        return v.getMeasuredWidth();
    }

    /**
     * 获取控件高度
     *
     * @param v
     * @return
     */
    public static int getHeight(View v) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        return v.getMeasuredHeight();
    }

    public static String getVersionName() {

        String versionName = null;
        try {
            PackageManager packageManager = getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {

        }

        return versionName;
    }

    public static int getVersionCode() {

        int versionCode = 0;
        try {
            PackageManager packageManager = getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {

        }

        return versionCode;
    }

    /**
     * 程序是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }



    public static int screenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(dm);
            return dm.widthPixels;
        }
        return 1080;
    }

    /**
     * 设置EditText的hint字体大小
     *
     * @param editText EditText控件
     * @param hintText hint内容
     * @param size     hint字体大小，单位为sp
     */
    public static void setEditTextHintWithSize(EditText editText, String hintText, @Dimension int size) {
        if (!TextUtils.isEmpty(hintText)) {
            SpannableString ss = new SpannableString(hintText);
            //设置字体大小 true表示单位是sp
            AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
            ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.setHint(new SpannedString(ss));
        }
    }

    /**
     * 判断进程是否存活
     *
     * @param context
     * @param proessName
     * @return
     */
    public static boolean isProessRunning(Context context, String proessName) {

        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.contains(proessName)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isExistMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        ComponentName cmpName = intent.resolveActivity(UIUtil.getContext().getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) UIUtil.getContext().getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList =am.getRunningTasks(10);//
            ActivityManager.RunningTaskInfo runningTaskInfo = taskInfoList.get(0);// 只是拿          当前运行的栈
            int numActivities = taskInfoList.get(0).numActivities;

            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) {// 说明它已经启动了
                    flag = true;
                    break;//跳出循环，优化效率
                }
            }
        }
        return flag;

    }


}
