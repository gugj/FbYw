package com.roch.fbyw.listener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.roch.fbyw.R;
import com.roch.fbyw.view.MyLazyViewpager;

/**
 * 作者：ZDS
 * 时间：2017/3/8/008 14:45
 */
public class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

    private FragmentTransaction transaction;
    private FragmentManager manager;
    private Fragment[] fragments;
    private TextView tv_title;
    private MyLazyViewpager vp_home;
    int index= 0;

    public MyOnCheckedChangeListener(MyLazyViewpager vp_home, TextView tv_title, Fragment[] fragments, FragmentManager manager, FragmentTransaction transaction){
        this.vp_home=vp_home;
        this.tv_title=tv_title;
        this.fragments=fragments;
        this.manager=manager;
        this.transaction=transaction;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedRBid) {
        switch (checkedRBid){
            case  R.id.rb_home:
                index = 0;
                tv_title.setText("首页");
                transaction = manager.beginTransaction()
                        .hide(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[2])
                        .hide(fragments[3])
                        .hide(fragments[4]);
                transaction.show(fragments[0]).commit();
                break;

            case  R.id.rb_news_center:
                index = 1;
                tv_title.setText("分类");
                transaction = manager.beginTransaction()
                        .hide(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[2])
                        .hide(fragments[3])
                        .hide(fragments[4]);
                transaction.show(fragments[1]).commit();
                break;

            case  R.id.rb_smart_service:
                index = 2;
                tv_title.setText("管理");
                transaction = manager.beginTransaction()
                        .hide(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[2])
                        .hide(fragments[3])
                        .hide(fragments[4]);
                transaction.show(fragments[2]).commit();
                break;

            case  R.id.rb_gov_affairs:
                index = 3;
                tv_title.setText("指南");
                transaction = manager.beginTransaction()
                        .hide(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[2])
                        .hide(fragments[3])
                        .hide(fragments[4]);
                transaction.show(fragments[3]).commit();
                break;

            case  R.id.rb_setting:
                index = 4;
                tv_title.setText("设置");
                transaction = manager.beginTransaction()
                        .hide(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[2])
                        .hide(fragments[3])
                        .hide(fragments[4]);
                transaction.show(fragments[4]).commit();
                break;
        }
//        vp_home.setCurrentItem(index);//item 当前界面索引
    }

    public int getIndex(){
        return index;
    }

}
