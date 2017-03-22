package com.roch.fbyw.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

	// 单例模式, 构造方法私有化
	private static Toast toast = null;

	/**
	 * 在屏幕中间显示Toast的方法
	 * @param context 上下文
	 * @param content 显示的内容
	 */
	public static void showMiddleToast(Context context, String content) {
		if (null == toast) {
			toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(content);
		}
		toast.show();
	}

	/**
	 * 显示Toast--长时间
	 * @param context 上下文对象
	 * @param content 显示的内容
	 */
	public static void showLongToast(Context context, String content) {
		if (null == toast) {
			toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
		} else {
			toast.setText(content);
		}
		toast.show();
	}

	/**
	 * 显示Toast--短时间
	 * @param context 上下文对象
	 * @param content 显示的内容
	 */
	public static void showNormalToast(Context context, String content) {
		if (null == toast) {
			toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
		} else {
			toast.setText(content);
		}
		toast.show();
	}

	/**
	 * 屏幕底部显示Toast--短时间
	 * @param context 上下文
	 * @param content 显示的内容
	 */
	public static void showBottomToast(Context context, String content) {
		if (null == toast) {
			toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		} else {
			toast.setText(content);
		}
		toast.show();
	}

}
