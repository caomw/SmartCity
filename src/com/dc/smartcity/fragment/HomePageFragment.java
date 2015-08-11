package com.dc.smartcity.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.activity.MyWebViewActivity;
import com.dc.smartcity.activity.SearchServiceActivity;
import com.dc.smartcity.activity.ServiceMarketActivity;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.bean.AdObj;
import com.dc.smartcity.net.ImageLoader;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.view.advertisement.AdvertisementView;
import com.dc.smartcity.view.gridview.IconWithTextGridAdapter;
import com.dc.smartcity.view.gridview.ScrollGridView;

import java.util.ArrayList;

/**
 * 主页
 * <p>
 * Created by vincent on 2015/8/3.
 */
public class HomePageFragment extends BaseFragment {

    private String TAG = HomePageFragment.class.getSimpleName();

    private ArrayList<AdObj> advertismentlist = new ArrayList<AdObj>();
    private AdvertisementView advertisementControlLayout;

    @ViewInject(R.id.ll_ad_layout)
    private LinearLayout ll_ad_layout;

    @ViewInject(R.id.gridview)
    private ScrollGridView gridview;
    @ViewInject(R.id.mudule_1)
    private RelativeLayout mudule_1;
    @ViewInject(R.id.mudule_2)
    private RelativeLayout mudule_2;
    @ViewInject(R.id.mudule_3)
    private RelativeLayout mudule_3;
    @ViewInject(R.id.mudule_4)
    private RelativeLayout mudule_4;

    public HomePageFragment() {
    }


    public HomePageFragment(ActionBar actionBar) {
        super(actionBar);
        ULog.debug("---HomePageFragment", TAG);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_service;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        view = super.onCreateView(inflater, container, bundle);
        initActionBarAction();
        initADS();
        initGridView();
        initBottomMudule();
        ULog.debug("---onCreateView");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ULog.debug("---onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        ULog.debug("---onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initActionBarAction();
        }
    }


    private void initBottomMudule() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;

        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mudule_1.getLayoutParams();
        layoutParams1.width = widthPixels / 3;
        layoutParams1.height = widthPixels * 2 / 5;
        mudule_1.setLayoutParams(layoutParams1);

        mudule_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyWebViewActivity.class));
            }
        });


        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) mudule_2.getLayoutParams();
        layoutParams2.width = widthPixels * 2 / 3;
        layoutParams2.height = widthPixels / 5;
        mudule_2.setLayoutParams(layoutParams2);

        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) mudule_3.getLayoutParams();
        layoutParams3.width = widthPixels / 3;
        layoutParams3.height = widthPixels / 5;
        mudule_3.setLayoutParams(layoutParams3);

        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) mudule_4.getLayoutParams();
        layoutParams4.width = widthPixels / 3;
        layoutParams4.height = widthPixels / 5;
        mudule_4.setLayoutParams(layoutParams4);


    }

    private void initGridView() {
        gridview.setAdapter(new IconWithTextGridAdapter(getActivity()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ServiceMarketActivity.class));
            }
        });
    }


    // 初始化广告
    private void initADS() {
        advertisementControlLayout = new AdvertisementView(getActivity());
        advertisementControlLayout.setAdvertisementRate(8, 5);
        advertisementControlLayout.setImageLoader(ImageLoader.getInstance());
        ll_ad_layout.addView(advertisementControlLayout);

        AdObj adObj1 = new AdObj();
        adObj1.imageUrl = "http://b.hiphotos.baidu.com/image/pic/item/0dd7912397dda144dc2ba0bbb0b7d0a20cf4869d.jpg";
        AdObj adObj2 = new AdObj();
        adObj2.imageUrl = "http://c.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c810a4107e5be3eb13533fa404c.jpg";
        AdObj adObj3 = new AdObj();
        adObj3.imageUrl = "http://c.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c810a4107e5be3eb13533fa404c.jpg";
        AdObj adObj4 = new AdObj();
        adObj4.imageUrl = "http://f.hiphotos.baidu.com/image/pic/item/eac4b74543a982261a1836268882b9014b90ebd2.jpg";
        advertismentlist.add(adObj1);
        advertismentlist.add(adObj2);
        advertismentlist.add(adObj3);
        advertismentlist.add(adObj4);
        if (advertismentlist != null && advertismentlist.size() > 0) {
            advertisementControlLayout.setAdvertisementData(advertismentlist);
            ll_ad_layout.setVisibility(View.VISIBLE);
        }
    }

    private void initActionBarAction() {
        iv_actionbar_left.setVisibility(View.GONE);
        tv_actionbar_left.setVisibility(View.VISIBLE);
        tv_actionbar_left.setText("常熟");
        tv_actionbar_title.setVisibility(View.GONE);

        et_actionbar_search.setVisibility(View.VISIBLE);
        et_actionbar_search.setHint("搜服务");
        et_actionbar_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(getActivity(), SearchServiceActivity.class));
                return true;
            }
        });

        tv_actionbar_right.setVisibility(View.VISIBLE);
        Drawable drawable = getActivity().getResources().getDrawable(R.drawable.baoxue);
        tv_actionbar_right.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        tv_actionbar_right.setText("31℃");

        iv_actionbar_right.setVisibility(View.GONE);
    }
}
