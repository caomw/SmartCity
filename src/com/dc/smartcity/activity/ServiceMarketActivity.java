package com.dc.smartcity.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.bean.more.MoreObj;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.fragment.ServiceListFragment;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.view.viewpagerindicator.TabPageIndicator;

/**
 * 更多服务
 * Created by vincent on 2015/8/7.
 */
public class ServiceMarketActivity extends BaseActionBarActivity {

    private final String TAG = ServiceMarketActivity.class.getSimpleName();

    //	@ViewInject(R.id.viewPager)
    private ViewPager mViewPager;
    //	@ViewInject(R.id.tab_indicator)
    private TabPageIndicator tab_indicator;

    private FragmentPagerAdapter mAdapter;

//    public final String[] TITLES = new String[]{"办事大厅", "融合账单", "生活周边", "智能出行", "旅游咨询", "教育生涯", "医疗健康", "文化体育", "职业生涯", "居家服务", "我的办事"};

    private MoreObj more;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_service_market);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        initViews();
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle("服务超市");
        iv_actionbar_right.setVisibility(View.VISIBLE);
        iv_actionbar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceMarketActivity.this, SearchServiceActivity.class));
            }
        });
    }


    private void initViews() {
        tab_indicator = (TabPageIndicator) findViewById(R.id.tab_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        //请求加载更多服务
        sendRequestWithDialog(RequestPool.GetMoreService(), new DialogConfig.Builder().build(), new RequestProxy() {

            @Override
            public void onSuccess(String msg, String result) {
                more = JSON.parseObject(result, MoreObj.class);

                List<BaseFragment> list_fragments = new ArrayList<BaseFragment>();
                if (null != more.columnList && more.columnList.size() > 0) {
                    for (int i = 0; i < more.columnList.size(); i++) {
                        ServiceListFragment fragment = new ServiceListFragment();
                        if (i == 0) {
                            fragment.setData(more.columnList.get(i), more.serviceList);
                        }else
                        {
                            fragment.setData(more.columnList.get(i));
                        }
                        list_fragments.add(fragment);
                    }
                    mAdapter = new TabAdapter(ServiceMarketActivity.this.getSupportFragmentManager(), list_fragments);
                    mViewPager.setAdapter(mAdapter);
                    tab_indicator.setViewPager(mViewPager);
                    tab_indicator.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    class TabAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> list_fragments;


        public TabAdapter(FragmentManager fm, List<BaseFragment> list_fragments) {
            super(fm);
            this.list_fragments = list_fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return more.columnList.get(position).getColumnName();
        }

        @Override
        public int getCount() {
            return more.columnList.size();
        }
    }
}
