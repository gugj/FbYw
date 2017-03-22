package com.roch.fbyw.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.roch.fbyw.R;
import com.roch.fbyw.dialog.NoticeDialog;
import com.roch.fbyw.utils.Common;
import com.roch.fbyw.utils.ToastUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Fragment基类
 * 
 * @author ZhaoDongShao
 * 2016年9月5日
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

	ProgressDialog mProgressDialog;
	BroadcastReceiver mReceiver;
	Activity mActivity;
	Context mContext;

	EditText etWebsite;
	TextView tvEnter;
	WebView webView;
	ProgressBar progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.mActivity = getActivity();
		this.mContext = getContext();
		
		// 通过获取的android版本改变状态栏的颜色
//		CommonUtil.getInstance(mActivity).getState();
		// 获取手机的屏幕密度DPI、屏幕的宽度和高度
		initDensityDpi();

		View contentView = View.inflate(getActivity(), R.layout.activity_web, null);
		//1.在contentView中查找View控件
		findView(contentView);
		//2.初始化数据---即请求的WebView的网址
		initData();
		return contentView;
	}

	/**
	 * 1.在contentView中查找View控件（并设置WebView）
	 */
	public void findView(View contentView) {
		etWebsite = (EditText) contentView.findViewById(R.id.etWebsite);
		tvEnter = (TextView) contentView.findViewById(R.id.tvEnter);
		tvEnter.setOnClickListener(this);
		progressBar = (ProgressBar) contentView.findViewById(R.id.progressBar);
		webView = (com.tencent.smtt.sdk.WebView) contentView.findViewById(R.id.webView);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
	}


	/**
	 * 2.初始化数据---即请求的WebView的网址
	 */
	public abstract void initData();

	public boolean loadSuccess=false;
	/**
	 * 3.加载网址
	 */
	public void loadUrl(String url) {
		etWebsite.setText(url);
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
				String title=view.getTitle();
				System.out.println("网页的title为：" + title);
				setTitle(title);
				view.loadUrl(url);
				loadSuccess=true;

				return true;
			}

			@Override
			public void onReceivedError(WebView var1, int var2, String var3, String var4) {
				progressBar.setVisibility(View.GONE);
				loadSuccess=false;
				ToastUtils.showNormalToast(getActivity(), "网页加载失败");
			}
		});
		//进度条
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					etWebsite.setText(webView.getOriginalUrl());
					progressBar.setVisibility(View.GONE);
					tvEnter.setText("刷新");
					return;
				}
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgress(newProgress);
			}
		});
		if(loadSuccess){

		}
	}



	/**
	 * 4.点击输入网址地址栏---改变刷新为进入
	 */
	public void openWebsite() {
		etWebsite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tvEnter.setText("进入");
			}
		});
	}

	/**
	 * 当点击刷新或进入按钮时调用此方法
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tvEnter:
				String url = etWebsite.getText().toString();
				if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
//					ToastUtil.show("网址错误!");
					ShowToast("网址错误!");
					return;
				}
				loadUrl(url);
				break;
		}
	}

	/**
	 * 设置网页的标题
	 * @param title
	 */
	private void setTitle(String title) {
		TextView titleView= (TextView) getActivity().findViewById(R.id.tv_title);
		titleView.setText(title);
	}

	/**
	 * 当fragment被销毁时调用
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (webView != null) webView.destroy();
	}

	/**
	 * 获取fragment中的WebView控件
	 */
	public View getWebView(){
		if (webView != null){
			return webView;
		}
		return null;
	}

	/**
	 * 获取手机的屏幕密度DPI、屏幕的宽度和高度
	 */
	private void initDensityDpi() {
		DisplayMetrics metrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Common.densityDpi = metrics.densityDpi;
		Common.Width = metrics.widthPixels;
		Common.Hight = metrics.heightPixels;
	}

	/*
	 * 显示进度条对话框--不可取消
	 */
	public void showProgressDialog(String msg) {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(mActivity);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setMessage(msg);
		}
		if (!mProgressDialog.isShowing())
			mProgressDialog.show();
	}

	/*
	 * 显示进度条对话框--可取消
	 */
	public void showProgressDialog(String msg, boolean cancleble) {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(mActivity);
			mProgressDialog.setCancelable(cancleble);
			mProgressDialog.setMessage(msg);
		}
		if (!mProgressDialog.isShowing())
			mProgressDialog.show();
	}

	/**
	 * 隐藏进度条对话框
	 */
	public void cancelProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.cancel();
	}

	/**
	 * 2016年10月27日
	 * 
	 * 显示一般时间的吐司
	 */
	public void ShowToast(String msg) {
		ToastUtils.showNormalToast(getActivity(),msg);
//		MyApplication.getInstance().getToastUtilsInstance().showNormalToast(getActivity(), msg);
	}

	/**
	 * 如果当前连接网络时出现故障，给出提示：服务器验证失败，请重新登陆！
	 * 2016年5月19日
	 * ZhaoDongShao
	 */
	@SuppressLint("NewApi")
	public void ShowNoticDialog() {
		NoticeDialog dialog = new NoticeDialog();
		dialog.show(getActivity().getFragmentManager(), "BaseActivity");
	}

	/**
	 * 返回EditText所编辑的内容
	 * @param ed
	 * @return
	 */
	public String getEditText(EditText ed) {
		return ed.getText().toString().trim();
	}

	/**
	 * 返回TextView所显示的内容
	 * @param tv
	 * @return
	 */
	public String getTextView(TextView tv) {
		return tv.getText().toString().trim();
	}

	// /**
	// * 软件更新弹出
	// */
	// CheckUpDialog versionUpdateDialog;
	//
	// void showProductOrderDialog(final String url, final String fileName,
	// String content, final String fileSize, String IsQiangZhi) {
	// Log.e("showProductOrderDialog", "软件更新弹出");
	// if (versionUpdateDialog != null && versionUpdateDialog.isShowing()) {
	// return;
	// }
	// versionUpdateDialog = new CheckUpDialog(activity,
	// R.style.popup_dialog_style);
	// Window win = versionUpdateDialog.getWindow();
	// win.setGravity(Gravity.CENTER);
	// WindowManager mWindowManager = (WindowManager)
	// activity.getSystemService(Context.WINDOW_SERVICE);
	// win.setWindowManager(mWindowManager, null, null);
	// versionUpdateDialog.setCancelable(false);
	// versionUpdateDialog.show();
	// versionUpdateDialog.setContent(content);
	// if (IsQiangZhi.equals("1")) {
	// // 强制更新
	// versionUpdateDialog.setCancelVisibility(View.GONE);
	// versionUpdateDialog.setCanceledOnTouchOutside(false);
	// } else {
	// versionUpdateDialog.setCancelVisibility(View.VISIBLE);
	// versionUpdateDialog.setCanceledOnTouchOutside(true);
	// }
	//
	// versionUpdateDialog.setIfBack(IsQiangZhi);
	// // 弹出框dialog 点击事件
	// versionUpdateDialog.setOnClickListener(new OnClickListener() {
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.dialog_product_done:
	// // 判断SD卡是否存在，并且是否具有读写权限
	// if
	// (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
	// {
	//
	// File file = new File(Common.DOWNLOAD_DIR + "/" + fileName);
	// if (file.exists()) {
	// installApk(activity, file);
	// } else {
	// if (MyApplication.getInstance().isDownload()) {
	// /*
	// * Intent it = new Intent(SettingActivity.this,
	// * DownloadService.class); it.putExtra("url",
	// * Constant.SERVER_URL + url);
	// * it.putExtra("fileName", fileName);
	// * SettingActivity.this.startService(it);
	// * SettingActivity.this.bindService(it, conn,
	// * Context.BIND_AUTO_CREATE);
	// */
	//
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("url", url);
	// map.put("name", "零工�??");
	// map.put("filename", fileName);
	// map.put("fileSize", fileSize);
	// UpdateManager manager = new UpdateManager(activity, map,
	// MyApplication.getInstance());
	// manager.showNotifiction();
	// } else {
	// MyApplication.getInstance().getToastUtilsInstance().showNormalToast(activity,"更新文件正在下载，稍后会自动安装");
	// }
	//
	// }
	//
	// } else {
	// MyApplication.getInstance().getToastUtilsInstance().showNormalToast(activity,"SD卡不存在,
	// 无法下载更新程序");
	// }
	// versionUpdateDialog.dismiss();
	// break;
	// case R.id.dialog_product_cancel:
	// // 更改标记
	// versionUpdateDialog.dismiss();
	// break;
	// }
	// }
	// });
	// }
	//
	//
	// /**
	// * 安装APK文件
	// */
	// private void installApk(Context context, File file) {
	// File apkfile = new File(file.getAbsolutePath());
	// if (!apkfile.exists()) {
	// return;
	// }
	// // 通过Intent安装APK文件
	// Intent i = new Intent(Intent.ACTION_VIEW);
	// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
	// "application/vnd.android.package-archive");
	// context.startActivity(i);
	// }

}
