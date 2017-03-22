package com.roch.fbyw.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.roch.fbyw.application.MyApplication;

/**
 * Created by llbt on 2016/4/25.
 */
public class ToastUtil {

    /**
     * 显示吐丝
     * @param msg 要显示的内容
     */
    public static void show(String msg) {
        Toast toast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
