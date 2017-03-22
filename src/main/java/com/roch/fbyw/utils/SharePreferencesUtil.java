package com.roch.fbyw.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Sharepreferences 工具
 * @author zds
 */
public class SharePreferencesUtil {

	// 服务器地址
	public static final String SERVER_ADDRESS = "server";
	
	/**
	 * 保存登陆时的用户名和密码
	 * @param context
	 * @param loginName
	 * @param loginPassword
	 * @param checked
	 * 2016年11月8日
	 */
	public void saveNameAndPassword(Context context,String loginName,String loginPassword,boolean checked){
		SharedPreferences sp = context.getSharedPreferences(Common.SP_NameAndPassword, Context.MODE_APPEND);
		Editor ed = sp.edit();

		ed.putString("loginName", loginName);
		ed.putString("loginPassword", loginPassword);
		ed.putBoolean("isChecked", checked);
		ed.commit();
		System.out.println("保存成功用户名和密码！");
	}
	
	/**
	 * 获取登陆时的用户名
	 * @param context
	 * @return
	 * 2016年11月8日
	 */
	public String getLonginName(Context context){
		SharedPreferences sp = context.getSharedPreferences(Common.SP_NameAndPassword, Context.MODE_APPEND);
		return sp.getString("loginName", "");
	}
	
	/**
	 * 获取登陆时的密码
	 * @param context
	 * @return
	 * 2016年11月8日
	 */
	public String getLonginPassword(Context context){
		SharedPreferences sp = context.getSharedPreferences(Common.SP_NameAndPassword, Context.MODE_APPEND);
		return sp.getString("loginPassword", "");
	}
	
	/**
	 * 获取登陆时是否设置了保存用户名和密码
	 * @param context
	 * @return
	 * 2016年11月8日
	 */
	public boolean getLonginChecked(Context context){
		SharedPreferences sp = context.getSharedPreferences(Common.SP_NameAndPassword, Context.MODE_APPEND);
		return sp.getBoolean("isChecked", false);
	}

	/**
	 * 清空登录信息
	 * @param context
	 * @param loginname 用户名
	 * 2016年6月2日 ZhaoDongShao
	 */
	public void clearLoginUser(Context context, String loginname) {
		SharedPreferences sp = context.getSharedPreferences(loginname, Context.MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.clear();
		ed.commit();
	}

	/**
	 * 保存服务地址
	 * @param serveraddress
	 * @param context
	 */
	public static void saveServerAddress(String serveraddress, Context context) {
		SharedPreferences sp = context.getSharedPreferences(SERVER_ADDRESS, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("server", serveraddress);
		editor.commit();
	}

	/**
	 * 获取服务地址
	 * @param context
	 */
	public static String getServerAddress(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SERVER_ADDRESS, Context.MODE_PRIVATE);
		return sp.getString("server", "");
	}

}
