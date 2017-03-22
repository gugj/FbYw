package com.roch.fbyw.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.roch.fbyw.pager.BasePager;
import java.util.List;

/**
 * 作者：ZDS
 * 时间：2017/3/8/008 14:42
 */
public class MyContentPagerAdapter extends PagerAdapter {

    private List<BasePager> pagers;

    public MyContentPagerAdapter(List<BasePager> pagers){
        this.pagers=pagers;
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager currentPager = pagers.get(position);
        View currentView = currentPager.initView();
        container.addView(currentView);
        //***不要忘记调用initData()
        currentPager.initData();
        return currentView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
