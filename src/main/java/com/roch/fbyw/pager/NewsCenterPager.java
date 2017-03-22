package com.roch.fbyw.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.roch.fbyw.web_activity.WebActivity;

/**
 * Created by wangdh on 2016/8/15.
 * 新闻中心对应的自定义pager
 */
public class NewsCenterPager extends  BasePager {

    private TextView textView;

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public View initView(){
        textView = new TextView(context);
        return textView;
    }

    @Override
    public void initData(){
        textView.setText("点击进入腾讯网页面");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.gotoActivity("http://3g.qq.com");
            }
        });
    }
}
