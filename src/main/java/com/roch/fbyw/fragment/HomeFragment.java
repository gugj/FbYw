package com.roch.fbyw.fragment;

public class HomeFragment extends BaseFragment {

    public void initData(){
//        String path=Environment.getExternalStorageDirectory().getAbsolutePath();
//        System.out.println("手机中储存卡路径为：=="+path);
        loadUrl("http://192.168.1.151:8080/login/demo.html");
        openWebsite();
    }

}
