package com.roch.fbyw.utils;

import android.util.Log;

/**
 * Created by xiongwenwei@aliyun.com
 * CreateTime: 2017/1/12
 * Note:
 */
public class LogUtil {

    private static String TAG = "system.out";
    private static boolean debug = true;

    /**
     * 打印log日志，过滤时用System.out
     * @param msg 打印log日志的内容
     */
    public static void i(String msg) {
        if(debug){
            Log.i(TAG,msg);
        }
    }
}
