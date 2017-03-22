package com.roch.fbyw.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.roch.fbyw.web_activity.WebActivity;

/**
 * Created by wangdh on 2016/8/15.
 * 政务指南对应的自定义pager
 */
public class GovPager extends  BasePager {

    private TextView textView;

    public GovPager(Context context) {
        super(context);
    }
    @Override
    public View initView(){
        textView = new TextView(context);
        return textView;
    }

    @Override
    public void initData(){
        textView.setText("点击进入钛媒体页面");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.gotoActivity("http://m.tmtpost.com");
            }
        });
    }
}
