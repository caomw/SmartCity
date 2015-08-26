package com.dc.smartcity.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.activity.CordovaWebwiewActivity;
import com.dc.smartcity.activity.SearchServiceActivity;
import com.dc.smartcity.activity.ServiceListActivity;
import com.dc.smartcity.activity.ServiceMarketActivity;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.bean.AdObj;
import com.dc.smartcity.bean.DCMenuItem;
import com.dc.smartcity.bean.HomeObj;
import com.dc.smartcity.bean.ScenceItem;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.net.ImageLoader;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.view.advertisement.AdvertisementView;
import com.dc.smartcity.view.gridview.IconWithTextGridAdapter;
import com.dc.smartcity.view.gridview.ScrollGridView;

import java.util.ArrayList;
import java.util.List;

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

    private ActionBar actionbar;

    public HomePageFragment() {
    }

    public HomePageFragment(ActionBar actionBar) {
        super(actionBar);
        this.actionbar=actionBar;
        ULog.debug("---HomePageFragment", TAG);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_service;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        view = super.onCreateView(inflater, container, bundle);
        initActionBarAction();
        // initGridView();
        // initBottomMudule();

        initBiz();
        ULog.debug("---onCreateView");
        return view;
    }

    private void initBiz() {
        sendRequestWithNoDialog(RequestPool.GetHomePage(), new RequestProxy() {

            @Override
            public void onSuccess(String msg, String result) {
                updatePage(result);
            }
        });
    }

    protected void updatePage(String result) {
        HomeObj home = JSON.parseObject(result, HomeObj.class);
        // 加载广告
        if (null != home.bannerPic && home.bannerPic.size() > 0) {
            initADS(home.bannerPic);
        }

        // 加载按钮
        if (null != home.recommendServiceList
                && home.recommendServiceList.size() > 0) {
            initGridView(home.recommendServiceList);
        }

        // 服务
        if (null != home.sceneColumnList && 4 == home.sceneColumnList.size()) {
            initBottomMudule(home.sceneColumnList);
        }

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

    /**
     * 场景设置
     *
     * @param sceneColumnList
     */
    private void initBottomMudule(List<ScenceItem> sceneColumnList) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;

        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mudule_1
                .getLayoutParams();
        layoutParams1.width = widthPixels / 3;
        layoutParams1.height = widthPixels * 2 / 5;
        mudule_1.setLayoutParams(layoutParams1);

        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) mudule_2
                .getLayoutParams();
        layoutParams2.width = widthPixels * 2 / 3;
        layoutParams2.height = widthPixels / 5;
        mudule_2.setLayoutParams(layoutParams2);

        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) mudule_3
                .getLayoutParams();
        layoutParams3.width = widthPixels / 3;
        layoutParams3.height = widthPixels / 5;
        mudule_3.setLayoutParams(layoutParams3);

        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) mudule_4
                .getLayoutParams();
        layoutParams4.width = widthPixels / 3;
        layoutParams4.height = widthPixels / 5;
        mudule_4.setLayoutParams(layoutParams4);

        List<RelativeLayout> modules = new ArrayList<RelativeLayout>();
        modules.add(mudule_1);
        modules.add(mudule_2);
        modules.add(mudule_3);
        modules.add(mudule_4);

        modifyScenceItems(modules, sceneColumnList);
    }

    /**
     * 设置监听和图标初始化
     *
     * @param modules
     * @param sceneColumnList
     */
    private void modifyScenceItems(List<RelativeLayout> modules,
                                   List<ScenceItem> sceneColumnList) {
        for (int i = 0; i < modules.size(); i++) {
            RelativeLayout item = modules.get(i);
            final ScenceItem sitem = sceneColumnList.get(i);
            ImageView ivItem = (ImageView) item.findViewById(R.id.iv_item);
            TextView tvItem = (TextView) item.findViewById(R.id.tv_item);
            tvItem.setText(sitem.getColumnName());
            ImageLoader.getInstance().displayImage(sitem.getColumnPicUrl(),
                    ivItem);
            item.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(getActivity(), ServiceListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BundleKeys.SCENCEITEM, sitem);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * 菜单
     */
    private void initGridView(final List<DCMenuItem> list) {
        DCMenuItem more = new DCMenuItem();
        more.setLevel("0");
        more.setServiceName("更多服务");
        more.setServicePicUrl("" + R.drawable.more);
        more.setServiceUrl("");
        list.add(more);

        gridview.setAdapter(new IconWithTextGridAdapter(getActivity(), list));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (TextUtils.isEmpty(list.get(position).serviceUrl)) {

                    startActivity(new Intent(getActivity(),
                            ServiceMarketActivity.class));
                } else {
                    Intent intent = new Intent(getActivity(),
                            CordovaWebwiewActivity.class);
                    intent.putExtra(BundleKeys.WEBVIEW_LOADURL,
                            list.get(position).serviceUrl);
                    intent.putExtra(BundleKeys.WEBVIEW_TITLE,
                            list.get(position).serviceName);
                    startActivity(intent);
                }
            }
        });
    }

    // 初始化广告
    private void initADS(List<String> bannerPic) {
        advertisementControlLayout = new AdvertisementView(getActivity());
        advertisementControlLayout.setAdvertisementRate(8, 5);
        advertisementControlLayout.setImageLoader(ImageLoader.getInstance());
        ll_ad_layout.addView(advertisementControlLayout);
        for (String ad : bannerPic) {
            AdObj adObj = new AdObj();
            adObj.imageUrl = ad;
            advertismentlist.add(adObj);
        }
        if (advertismentlist != null && advertismentlist.size() > 0) {
            advertisementControlLayout.setAdvertisementData(advertismentlist);
            ll_ad_layout.setVisibility(View.VISIBLE);
        }
    }

    private void initActionBarAction() {
        actionbar.show();
        iv_actionbar_left.setVisibility(View.GONE);
        tv_actionbar_left.setVisibility(View.VISIBLE);
        tv_actionbar_left.setText("常熟");
        tv_actionbar_title.setVisibility(View.GONE);

        et_actionbar_search.setVisibility(View.VISIBLE);
        et_actionbar_search.setHint("搜服务");
        et_actionbar_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(getActivity(),
                        SearchServiceActivity.class));
                return true;
            }
        });

//		tv_actionbar_right.setVisibility(View.VISIBLE);
//		Drawable drawable = getActivity().getResources().getDrawable(
//				R.drawable.baoxue);
//		tv_actionbar_right.setCompoundDrawablesWithIntrinsicBounds(drawable,
//				null, null, null);
//		tv_actionbar_right.setText("31℃");

        iv_actionbar_right.setVisibility(View.GONE);
    }
}
