package com.roch.fbyw.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判断当前的object是否为null或为空-""
	 */
	public static boolean isEmpty(Object obj) {
		return null == obj || "".equals(obj.toString().trim());
	}

	/**
	 * 判断当前对象是否不为null或不为空-""
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	public static String getSequenceId() {
		String mark = String.valueOf(System.currentTimeMillis());
		return mark;
	}

	/**
	 * 获取当前时间(yyyyMMddHHmmss)
	 */
	public static String getCurrentlyDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
		return dateFormat.format(new Date());
	}

	/**
	 * 获取时间的长日期
	 */
	public static String transformDateTime(long t) {
		Date date = new Date(t);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		return dateFormat.format(date);
	}

	/**
	 * 获取当前日期(yyyyMMdd)
	 */
	public static String getCurrentlyDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		return dateFormat.format(new Date());
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 字符串特殊处理
	 */
	public static SpannableString handlerText(String str, int start, Context context) {
		SharedPreferences sh = context.getSharedPreferences("dpi", 0);
		SpannableString sp = new SpannableString(str);
		if (sh.getInt("dpi_type", 0) == 1) {
			sp.setSpan(new AbsoluteSizeSpan(25), start, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		} else {
			sp.setSpan(new AbsoluteSizeSpan(18), start, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return sp;
	}

	/**
	 * 判断该字符串是不是为手机号
	 */
	public static boolean checkPhone(String phone) {
		Pattern pattern = Pattern.compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

}
