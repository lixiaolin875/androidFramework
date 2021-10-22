package com.example.duomiplatform;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

public class LYApplication extends Application {

    private static LYApplication mContext;

    private static Handler mMainHandler;

    private static Thread mMainThread;

    private static int mMainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = (LYApplication) getApplicationContext();
        mMainHandler = new Handler();
        mMainThread = Thread.currentThread();
        mMainThreadId = Process.myTid();
    }

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static LYApplication getApplication() {
        return mContext;
    }

    /**
     * 获取主线程handler
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return mMainHandler;
    }

    /**
     * 获取主线程
     *
     * @return
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程id
     *
     * @return
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }
}
