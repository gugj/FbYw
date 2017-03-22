package com.roch.fbyw.pager;

import android.content.Context;
import android.view.View;

/**
 * Created by wangdh on 2016/8/15.
 * 自定义pager
 */
public abstract class BasePager {

    public Context context;

    public BasePager(Context context) {
        this.context = context;
    }

    /**
     * 初始化View
     * 1. 确定布局
     * 2. findViewById
     */
    public abstract View initView();

    /**
     * 初始化数据
     * 1. 获取网络数据
     * 2. 给控件填充内容
     */
    public abstract void initData();


}
