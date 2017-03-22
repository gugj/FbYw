package com.roch.fbyw.utils;

import android.os.Environment;

import java.util.UUID;

/**
 * 常量类，保存了各种数据的常量
 */
public class Common {

	/**
	 * 登陆页面在登陆成功时输入的登陆用户名，例如admin
	 */
	public static String LoginName = "";
	
	/**
	 * 保存登陆界面输入的登陆用户名和密码的sp的名字
	 */
	public static String SP_NameAndPassword = "SP_NameAndPassword";

	/**
	 * 标题
	 */
	public static final String TITLE_KEY = "title";

	/**
	 * Bundle传参key
	 */
	public static final String BUNDEL_KEY = "bundle_key";

	/**
	 * Bundle传参筛选条件key
	 */
	public static final String BUNDEL_FILTER_KEY_1 = "bundle_filter_key_1";
	public static final String BUNDEL_FILTER_KEY_2 = "bundle_filter_key_2";

	/**
	 * Intent传参key
	 */
	public static final String INTENT_KEY = "intent_key";
	public static final String UP_LOAD_PHOTO_KEY = "image_url";

	/**
	 * 登陆失效
	 */
	public static final int LOGIN_FILE = -2;

	/**
	 * 列表当前显示页数
	 */
	public static String PAGR = "1";
	/**
	 * 页码参数
	 */
	public static final String EXTS_PAGE = "page";
	/**
	 * 菜单
	 */
	public static final String MENU_BUNDEL_KEY = "menu";
	public static final String USER_BUNDEL_KEY = "user";

	/**
	 * 判断动画是否结束
	 */
	public static boolean isAnimaEnd = true;

	/**
	 * 在登陆页面登陆成功后保存的flag标志---登录状态
	 */
	public static boolean isLogin = false;

	/**
	 * 是否定位
	 */
	public static final String LOCTION = "isLoction";
	
	/**
	 * 保存当前手机DPI
	 */
	public static int densityDpi = 0;
	
	/**
	 * 保存手机的宽度
	 */
	public static int Width = 0;
	
	/**
	 * 保存手机的高度
	 */
	public static int Hight = 0;
	
	/**
	 * 缓存路径
	 */
	public static final String CACHE_PATHE = "/Android/data/com.roch.fbyw_2_0";
	
	/**
	 * 数据库版本
	 */
	public static final int DB_VERSION = 3;

	/**
	 * 数据库名称
	 */
	public static final String DB_NAME = "fbyw_2_0_DB";
	/**
	 * 定时进行网络判断action
	 */
	public static final String MESSAGE_RECEIVED_ACTION = "com.roch.fbyw.MESSAGE_RECEIVED_INTERNET";

	/**
	 * 发送的信息
	 */
	public static final String KEY_MESSAGE = "message";

	/**
	 * APP更新action
	 */
	public static final String UPDATA_APP = "com.roch.fbyw_2_0.update";
	/**
	 * 取消更新
	 */
	public static final String CANCEL_BROADCAST = "com.roch.fbyw_2_0.update_cancel";
	/**
	 * 存储卡路径
	 */
	public static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getPath();
	public static final String CACHE_DIR = Environment.getExternalStorageDirectory().getPath() + CACHE_PATHE + "/cache";
	public static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory().getPath() + CACHE_PATHE + "/download";

	/**
	 * 返回uuid
	 * @param str
	 * @return
	 */
	public static String uuidToStr(String str) {
		String objectKey = null;
		if (null != str && str.length() > 0) {
			int index = str.lastIndexOf(".");
			int len = str.length();
			objectKey = UUID.randomUUID().toString().replaceAll("-", "") + str.substring(index, len);
		}
		return objectKey;
	}
}
