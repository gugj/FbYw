package com.roch.fbyw.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.roch.fbyw.web_activity.WebActivity;

/**
 * Created by wangdh on 2016/8/15.
 * 智慧服务对应的自定义pager
 */
public class SmartServicePager extends  BasePager {

    private TextView textView;

    public SmartServicePager(Context context) {
        super(context);
    }
    @Override
    public View initView(){
        textView = new TextView(context);
        return textView;
    }

    @Override
    public void initData(){
        textView.setText("点击进入腾讯视频页面");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.gotoActivity("http://m.v.qq.com");
            }
        });
    }
}
