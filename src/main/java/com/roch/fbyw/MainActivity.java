package com.roch.fbyw;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.roch.fbyw.activity.MainBaseActivity;
import com.roch.fbyw.adapter.MyContentPagerAdapter;
import com.roch.fbyw.fragment.BaseFragment;
import com.roch.fbyw.listener.MyOnCheckedChangeListener;
import com.roch.fbyw.pager.BasePager;
import com.roch.fbyw.pager.GovPager;
import com.roch.fbyw.pager.HomePager;
import com.roch.fbyw.pager.NewsCenterPager;
import com.roch.fbyw.pager.SettingsPager;
import com.roch.fbyw.pager.SmartServicePager;
import com.roch.fbyw.utils.ToastUtils;
import com.roch.fbyw.view.MyLazyViewpager;
import com.tencent.smtt.sdk.WebView;
import com.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MainBaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private Toolbar toolbar;
    private MyLazyViewpager vp_home;
    private RadioButton rb_home;
    private RadioButton rb_news_center;
    private RadioButton rb_smart_service;
    private RadioButton rb_gov_affairs;
    private RadioButton rb_setting;
    private RadioGroup rg_home;

    private List<BasePager> pagers = new ArrayList<BasePager>();
    private ImageView iv_saoyisao;

    Fragment[] fragments;
    FragmentManager manager;
    FragmentTransaction transaction;

    MyOnCheckedChangeListener myOnchekedChangeListener;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.初始化查找View控件
        initView();

        // 设置顶部状态栏的颜色
        setStatusBarColor();

        //2.初始化Tool控件
        initToolbar();
        //3.初级化Fragment数组，并控制显示第一个fragment
        initFragment();
//        initPager();
        //4.初始化RadioGroup导航按钮的点击监听
        initListener();
    }

    /**
     * 设置顶部状态栏的颜色
     * 2016年11月3日
     */
    private void setStatusBarColor() {
        // 如果版本大于等于21即Android 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View statusBarView = mContentView.getChildAt(0);
            // 移除假的 View
            if (statusBarView != null && statusBarView.getLayoutParams() != null
                    && statusBarView.getLayoutParams().height == getStatusBarHeight()) {
                mContentView.removeView(statusBarView);
            }
            // 不预留空间
            if (mContentView.getChildAt(0) != null) {
                ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
            }
            LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
            int height = getStatusBarHeight();
            lParams.height = height;

            layout.setLayoutParams(lParams);  //ResourceUtil.getInstance().getColorById(R.color.color_145bba)
            layout.setBackgroundColor(getResources().getColor(R.color.color_145bba));
        }

        // 如果版本大于等于19即Android 4.4，小于21即Android 5.0
        if (Build.VERSION_CODES.LOLLIPOP > Build.VERSION.SDK_INT
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            // 首先使 ChildView 不预留空间
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }

            int statusBarHeight = getStatusBarHeight();
            // 需要设置这个 flag 才能设置状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 避免多次调用该方法时,多次移除了 View
            if (mChildView != null && mChildView.getLayoutParams() != null
                    && mChildView.getLayoutParams().height == statusBarHeight) {
                // 移除假的 View.
                mContentView.removeView(mChildView);
                mChildView = mContentView.getChildAt(0);
            }
            if (mChildView != null) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                // 清除 ChildView 的 marginTop 属性
                if (lp != null && lp.topMargin >= statusBarHeight) {
                    lp.topMargin -= statusBarHeight;
                    mChildView.setLayoutParams(lp);
                }
            }
            LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
            int height = getStatusBarHeight();
            lParams.height = height;
            layout.setLayoutParams(lParams); // ResourceUtil.getInstance().getColorById(R.color.color_145bba)
            layout.setBackgroundColor(getResources().getColor(R.color.color_145bba));
        }

        // 如果版本小于19，即小于Android 4.4
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            layout.setVisibility(View.GONE);
        }
    }

    /**
     * 1.初始化查找View控件
     */
    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        vp_home = (MyLazyViewpager) findViewById(R.id.vp_home);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_news_center = (RadioButton) findViewById(R.id.rb_news_center);
        rb_smart_service = (RadioButton) findViewById(R.id.rb_smart_service);
        rb_gov_affairs = (RadioButton) findViewById(R.id.rb_gov_affairs);
        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
        rg_home = (RadioGroup) findViewById(R.id.rg_home);
        iv_saoyisao = (ImageView) findViewById(R.id.iv_saoyisao);
        iv_saoyisao.setVisibility(View.VISIBLE);
        iv_saoyisao.setOnClickListener(this);
        layout = (LinearLayout) findViewById(R.id.ll);
//        layout.setOnClickListener(this);
    }

    /**
     * 2.初始化Tool控件
     */
    private void initToolbar() {
        toolbar.setTitle("");
        tv_title.setText("首页");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 3.初级化Fragment数组，并控制显示第一个fragment
     */
    private void initFragment() {
        fragments = new Fragment[5];
        manager = getSupportFragmentManager();
        fragments[0] = manager.findFragmentById(R.id.fragment_home);
        fragments[1] = manager.findFragmentById(R.id.fragment_newcenter);
        fragments[2] = manager.findFragmentById(R.id.fragment_gov);
        fragments[3] = manager.findFragmentById(R.id.fragment_smartservice);
        fragments[4] = manager.findFragmentById(R.id.fragment_setting);
        transaction = manager.beginTransaction()
                .hide(fragments[0])
                .hide(fragments[1])
                .hide(fragments[2])
                .hide(fragments[3])
                .hide(fragments[4]);
        transaction.show(fragments[0]).commit();
    }

    /**
     * 不用这种方法了
     */
    private void initPager() {
        pagers.add(new HomePager(this));
        pagers.add(new NewsCenterPager(this));
        pagers.add(new SmartServicePager(this));
        pagers.add(new GovPager(this));
        pagers.add(new SettingsPager(this));
        MyContentPagerAdapter adapter = new MyContentPagerAdapter(pagers);
        vp_home.setAdapter(adapter);
    }

    /**
     * 4.初始化RadioGroup导航按钮的点击监听
     */
    private void initListener() {
        rg_home.check(R.id.rb_home);
        myOnchekedChangeListener = new MyOnCheckedChangeListener(vp_home, tv_title, fragments, manager, transaction);
        rg_home.setOnCheckedChangeListener(myOnchekedChangeListener);
    }

    /**
     * 当布局的的View控件被点击时调用此方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_saoyisao: //点击了扫一扫
                //	扫描二维码
                Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 1);
                break;

            default:
                break;
        }
    }

    /**
     * 当此页面被打开时返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == 1) { //扫描二维码后返回的结果
            String resultStr = intent.getStringExtra("resultStr");
            ToastUtils.showNormalToast(this, resultStr); //---跳转到扫一扫后的贫困户详情界面
//            Intent intent2 = new Intent(mActivity, PoorHouseDetailActivity_SaoYiSao.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(Common.BUNDEL_KEY, resultStr);
//            intent2.putExtra(Common.INTENT_KEY, bundle);
//            startActivity(intent2);
        }
    }

    /**
     * 当此页面被点击返回按钮时调用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView webView = (WebView) ((BaseFragment) fragments[myOnchekedChangeListener.getIndex()]).getWebView();
        if (null != webView) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    String title = webView.getTitle();
                    //设置网页的标题
                    setWebTitle(title);
                    webView.goBack();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setWebTitle(String title) {
        tv_title.setText(title);
    }

}
