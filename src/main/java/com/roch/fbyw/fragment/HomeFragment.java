package com.roch.fbyw.fragment;

public class HomeFragment extends BaseFragment {

    public void initData(){
//        String path=Environment.getExternalStorageDirectory().getAbsolutePath();
//        System.out.println("手机中储存卡路径为：=="+path);  //file:///android_asset/login/demo5.html   http://192.168.1.151:8080/login/demo5.html
        loadUrl("file:///android_asset/login/demo5.html");
        openWebsite();
    }

}
