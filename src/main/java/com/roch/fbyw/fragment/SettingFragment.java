package com.roch.fbyw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.roch.fbyw.R;
import com.roch.fbyw.utils.Mobile.Util;
import com.roch.fbyw.utils.baiduGPS.GpsHelp;

public class SettingFragment extends Fragment {

//    public void initData(){
////        webv.getSettings().setJavaScriptEnabled(true);
////        webv.addJavascriptInterface(new Util(this),"util");
////        webv.addJavascriptInterface(new GpsHelp(this,webv),"gps");
////        webv.loadUrl("file:///android_asset/test.html");

//        loadUrl("http://m.site.baidu.com/");
//        openWebsite();
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = View.inflate(getActivity(), R.layout.fragment_setting, null);

        WebView webv= (WebView)contentView.findViewById(R.id.webView1);
        webv.getSettings().setJavaScriptEnabled(true);
        webv.addJavascriptInterface(new Util(getActivity()),"util");        //  file:///android_asset/test.html
        webv.addJavascriptInterface(new GpsHelp(getActivity(),webv),"gps"); // file:///android_asset/login/demo5.html   http://192.168.1.151:8080/login/demo5.html
        webv.loadUrl("file:///android_asset/login/demo5.html");

        return contentView;
    }
}
