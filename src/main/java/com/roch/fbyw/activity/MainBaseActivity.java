package com.roch.fbyw.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.roch.fbyw.R;

/**
 * 继承自BaseActivity，里面仅只有一个方法，就是在onCreate中根据版本确定状态栏的颜色
 * @author ZhaoDongShao
 * 2016年8月12日
 */
public class MainBaseActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//设置状态栏的颜色
		setMyStatusBarColor();

//		if (Build.VERSION_CODES.LOLLIPOP > Build.VERSION.SDK_INT && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			CommonUtil.getInstance(this).getState();
//		}
	}

	/**
	 * 设置状态栏的颜色
	 * 2016年11月3日
	 */
	@SuppressLint({ "InlinedApi", "NewApi" })
	private void setMyStatusBarColor() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			// 取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

			// 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			// 设置状态栏颜色
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			} else {
			}      //ResourceUtil.getInstance().getColorById(R.color.color_145bba)
			window.setStatusBarColor(getResources().getColor(R.color.bule_155bbb));

			ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
			View mChildView = mContentView.getChildAt(0);
			if (mChildView != null) {
				// 注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView
				// 的第一个子 View . 预留出系统 View 的空间.
				ViewCompat.setFitsSystemWindows(mChildView, true);
			}
		}
	}

	/**
	 * 获取状态栏的高度
	 * @return
	 */
	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

}
