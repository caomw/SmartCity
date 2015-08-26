package com.dc.smartcity.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.view.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * Created by vincent on 2015/8/3.
 */
public class WelcomeActivity extends BaseActionBarActivity {

    @ViewInject(R.id.indicator)
    private CirclePageIndicator indicator;
    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    private int totalNum = -1;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_welcome);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        final boolean isFromMy = getIntent().getBooleanExtra(BundleKeys.ISFROMMY, false);


        if (!isFromMy) {//不是从个人中心进入，记录已经打开过
            SharedPreferences sharedPreferences = getSharedPreferences(BundleKeys.KEY_SHAREDPREFERENCES,
                    Activity.MODE_PRIVATE);
            String isopen = sharedPreferences.getString(BundleKeys.ISFIRSTOPEN, "0");
            if ("1".equals(isopen)) {   //打开过APP
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                WelcomeActivity.this.finish();
                return;
            } else {  //没有打开过APP
                SharedPreferences mySharedPreferences = getSharedPreferences(BundleKeys.KEY_SHAREDPREFERENCES, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString(BundleKeys.ISFIRSTOPEN, "1");
                editor.commit();
            }
        }

        List<View> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            View view = mLayoutInflater.inflate(R.layout.view_welcome, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.wel_bg);
            list.add(view);
        }

        totalNum = list.size();
        ViewPagerAdapter adapter = new ViewPagerAdapter(list);
        viewPager.setAdapter(adapter);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == totalNum - 1) {
                    viewPager.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (isFromMy) {
                                WelcomeActivity.this.finish();
                            } else {
                                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                                WelcomeActivity.this.finish();
                            }
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }


    class ViewPagerAdapter extends PagerAdapter {

        //界面列表
        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        //获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }

            return 0;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            return views.get(arg1);
        }

        //判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub
        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub
        }
    }

}
