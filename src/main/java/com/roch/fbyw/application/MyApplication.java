package com.roch.fbyw.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.roch.fbyw.utils.CommonUtil;
import com.roch.fbyw.utils.FileUtil;
import com.roch.fbyw.utils.LogUtil;
import com.roch.fbyw.utils.NetConnect;
import com.roch.fbyw.utils.SharePreferencesUtil;
import com.roch.fbyw.utils.ToastUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import java.util.Stack;

/**
 * 作者：ZDS
 * 时间：2017/3/8/008 15:07
 */
public class MyApplication extends Application {

    private static MyApplication myapp;
    Stack<Activity> activityStack;

    private static NetConnect netConnect;
    private static SharePreferencesUtil sharePreferencesUtil;
    private static CommonUtil commonUtil;
    private static ToastUtils toastUtil;

    @Override
    public void onCreate() {
        super.onCreate();

        myapp = this;
        try {
            if (!FileUtil.isFileExist(com.roch.fbyw.utils.Common.CACHE_DIR)) {
                FileUtil.creatSDDir(com.roch.fbyw.utils.Common.CACHE_DIR);
            }
            if (!FileUtil.isFileExist(com.roch.fbyw.utils.Common.DOWNLOAD_DIR)) {
                FileUtil.creatSDDir(com.roch.fbyw.utils.Common.DOWNLOAD_DIR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initTbs();
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .showImageForEmptyUri(R.drawable.empty_photo)
//                .showImageOnFail(R.drawable.empty_photo)
//                .cacheInMemory(true)
//                .cacheOnDisc(true)
//                .build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                .defaultDisplayImageOptions(defaultOptions)
//                .discCacheSize(50 * 1024 * 1024)//
//                .discCacheFileCount(100)// 缓存一百张图片
//                .writeDebugLogs()
//                .build();
//        ImageLoader.getInstance().init(config);

        // 开启线程
        new Thread(new MyThread()).start();
    }

    private void initTbs() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                LogUtil.i("View是否初始化完成:" + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                LogUtil.i("X5内核初始化完成");
            }
        };

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                LogUtil.i("腾讯X5内核 下载结束");
            }

            @Override
            public void onInstallFinish(int i) {
                LogUtil.i("腾讯X5内核 安装完成");
            }

            @Override
            public void onDownloadProgress(int i) {
                LogUtil.i("腾讯X5内核 下载进度:%" + i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    /**
     * 获取application的实例
     */
    public static MyApplication getInstance() {
        return myapp;
    }

    /**
     * 每隔10s发送广播判断网络连接状态
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Intent intent = null;
            if (msg.what == 1) {
                boolean isConntion = getNetConnectInstance().ischeackNet(myapp);
                intent = new Intent(com.roch.fbyw.utils.Common.MESSAGE_RECEIVED_ACTION);
                intent.putExtra(com.roch.fbyw.utils.Common.KEY_MESSAGE, isConntion);
            }
            sendBroadcast(intent);
            super.handleMessage(msg);
        }
    };

    /**
     * 自定义的判断网络连接状态的线程类
     * @author ZhaoDongShao
     * 2016年5月12日
     */
    public class MyThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);// 线程暂停10秒，单位毫秒
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);// 发送消息
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @return 获取手机连接网络状态的实体类对象NetConnect
     */
    public NetConnect getNetConnectInstance() {
        if (netConnect == null) {
            netConnect = new NetConnect();
        }
        return netConnect;
    }

    /**
     * 获取当前Toast对象的实例
     * @return 实例
     */
    public ToastUtils getToastUtilsInstance() {
        if (toastUtil == null) {
            toastUtil = new ToastUtils();
        }
        return toastUtil;
    }

    /**
     * 获取SharePreferences工具类实例
     * @return
     */
    public SharePreferencesUtil getSharePreferencesUtilInstance() {
        if (sharePreferencesUtil == null) {
            sharePreferencesUtil = new SharePreferencesUtil();
        }
        return sharePreferencesUtil;
    }

    /**
     * CommonUtil实例
     * @return
     */
    public CommonUtil getCommonUtilInstance() {
        if (commonUtil == null) {
            commonUtil = new CommonUtil();
        }
        return commonUtil;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }

}
