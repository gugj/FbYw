package com.roch.fbyw.web_activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.roch.fbyw.R;
import com.roch.fbyw.activity.MainBaseActivity;
import com.roch.fbyw.application.MyApplication;
import com.roch.fbyw.utils.LogUtil;
import com.roch.fbyw.utils.ToastUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class WebActivity extends MainBaseActivity implements View.OnClickListener {

    private EditText etWebsite;
    private TextView tvEnter, tvStatus;
    private ProgressBar progressBar;
    private com.tencent.smtt.sdk.WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        LogUtil.i("WebActivity.onCreate()");
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getBundle();
        initView();
        loadUrl(url);
        openWebsite();
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
    }

    public static void gotoActivity(String url) {
        if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
            ToastUtil.show("网址错误!");
            return;
        }
        LogUtil.i("MyApplication.getInstance()==="+MyApplication.getInstance());
        LogUtil.i("WebActivity.class==="+WebActivity.class);
//        Intent intent = new Intent(BaseApplication.getInstance(), WebActivity.class);
        Intent intent = new Intent();
        LogUtil.i("intent==="+intent);
        intent.setAction("web_activity");
        intent.putExtra("url", url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getInstance().startActivity(intent);
    }

    private void openWebsite() {
        etWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWebsite.setText("");
                tvEnter.setText("进入");
            }
        });
    }

    private void initView() {
        etWebsite = (EditText) findViewById(R.id.etWebsite);
        tvEnter = (TextView) findViewById(R.id.tvEnter);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webView);
        tvEnter.setOnClickListener(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        int tbsVersion = QbSdk.getTbsVersion(this);
        String TID = QbSdk.getTID();
        String qBVersion = QbSdk.getMiniQBVersion(this);
        tvStatus.setText("TbsVersion:" + tbsVersion + "\nTID:" + TID + "\nMiniQBVersion:" + qBVersion);
    }

    private void loadUrl(String url) {
        etWebsite.setText(url);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                progressBar.setVisibility(View.GONE);
                ToastUtil.show("网页加载失败");
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvEnter:
                String url = etWebsite.getText().toString();
                if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
                    ToastUtil.show("网址错误!");
                    return;
                }
                loadUrl(url);
                break;
        }
    }

}
