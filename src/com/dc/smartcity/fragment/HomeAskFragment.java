package com.dc.smartcity.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.net.ImageLoader;
import com.dc.smartcity.bean.AdObj;
import com.dc.smartcity.view.advertisement.AdvertisementView;

import java.util.ArrayList;

/**
 * 有问必答
 * Created by vincent on 2015/8/3.
 */
public class HomeAskFragment extends BaseFragment {
    private ArrayList<AdObj> advertismentlist = new ArrayList<AdObj>();
    private AdvertisementView advertisementControlLayout;

    @ViewInject(R.id.ll_ad_layout)
    private LinearLayout ll_ad_layout;

    public HomeAskFragment(ActionBar actionBar) {
        super(actionBar);
    }


    @Override
    protected int setContentView() {
        return R.layout.fragment_ask;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        view = super.onCreateView(inflater, container, bundle);
        initADS();
        initActionBar();
        return view;
    }

    private void initActionBar() {
        tv_actionbar_title.setVisibility(View.VISIBLE);
        tv_actionbar_title.setText("有问必答");

        iv_actionbar_right.setVisibility(View.VISIBLE);
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
        adObj3.imageUrl = "http://e.hiphotos.baidu.com/image/pic/item/3801213fb80e7bec203a3fe12d2eb9389a506bc9.jpg";
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
}
