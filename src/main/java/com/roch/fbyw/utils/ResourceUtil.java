package com.roch.fbyw.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.roch.fbyw.application.MyApplication;

import java.io.InputStream;

/**
 * 资源工具类——单例模式
 */
public class ResourceUtil {
	
	private ResourceUtil(){
	}

	private static ResourceUtil getResourceUtil;
	
	/**
	 * @return 获取ResourceUtil的实例
	 */
	public static ResourceUtil getInstance(){
		if (getResourceUtil == null) {
			getResourceUtil = new ResourceUtil();
		}
		return getResourceUtil;
	}

	/***
	 * 通过ID获取得到View
	 * @param resId
	 * @return
	 */
	public  String getTextViewById(Activity ctx, int resId) {
		String str = ((TextView) ctx.findViewById(resId)).getText().toString();
		return str;
	}

	/***
	 * 设置View的ID
	 * @param resId
	 * @return
	 */
	public  void setTextViewById(Activity ctx, int resId, String str) {
		((TextView) ctx.findViewById(resId)).setText(str);
	}
	
	/**
	 * 通过resID获取资源文件
	 */
	public  String getStringById(int resId) {
		return MyApplication.getInstance().getResources().getString(resId);
	}

	/**
	 * 通过resID获取颜色资源
	 */
//	public int getColorById(int resId) {
//		return MyApplication.getInstance().getResources().getColor(resId);
//	}

	/**
	 * 通过资源ID获取Drawable对象
	 */
	public Drawable getDrawableByID(int resId) {
		return MyApplication.getInstance().getResources().getDrawable(resId);
	}

	/**
	 * 获取屏幕尺寸
	 */
	public  float getDensity() {
		return MyApplication.getInstance().getResources().getDisplayMetrics().density;
	}

	/**
	 * 通过资源ID获取Bitmap对象
	 */
	public  Bitmap getBitmapById(int resId) {
		InputStream is = MyApplication.getInstance().getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, null);
	}

	/**
	 * 通过资源ID获取尺寸
	 */
	public  float getDeminVal(int redId) {
		return MyApplication.getInstance().getResources().getDimension(redId);
	}

	public static int getResIdentifier(String id, String type) {
		return MyApplication.getInstance().getResources().getIdentifier(id, type, MyApplication.getInstance().getPackageName());
	}
	
	/**
	 * 通过资源ID获取StringArray
	 */
	public String[] getStringArrayById(int id){
		return MyApplication.getInstance().getResources().getStringArray(id);
	}

}
