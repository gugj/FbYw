package com.roch.fbyw.pager;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.roch.fbyw.R;
import com.roch.fbyw.utils.ToastUtil;
import com.roch.fbyw.utils.ToastUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by wangdh on 2016/8/15.
 * 首页对应的自定义pager
 */
public class HomePager extends  BasePager implements View.OnClickListener {

    private EditText etWebsite;
    private TextView tvEnter;
    private WebView webView;
    private ProgressBar progressBar;
    private boolean isFirstEnter=true;

    public HomePager(Context context) {
        super(context);
    }

    @Override
    public View initView(){
        View contentView = View.inflate(context, R.layout.activity_web, null);
        findView(contentView);
        return contentView;
    }

    private void findView(View contentView) {
        etWebsite = (EditText) contentView.findViewById(R.id.etWebsite);
        tvEnter = (TextView) contentView.findViewById(R.id.tvEnter);
        progressBar = (ProgressBar) contentView.findViewById(R.id.progressBar);
        webView = (com.tencent.smtt.sdk.WebView) contentView.findViewById(R.id.webView);
        tvEnter.setOnClickListener(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void initData(){
        if(isFirstEnter){
            loadUrl("http://m.v.qq.com");
            openWebsite();
        }
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

    private void loadUrl(String url) {
        isFirstEnter=false;
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
                ToastUtils.showNormalToast(context, "网页加载失败");
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

    private void openWebsite() {
        etWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWebsite.setText("");
                tvEnter.setText("进入");
            }
        });
    }
}
