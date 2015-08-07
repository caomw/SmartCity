package com.dc.smartcity.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.activity.LoginActivity;
import com.dc.smartcity.activity.MessageActivity;
import com.dc.smartcity.activity.SettingActivity;
import com.dc.smartcity.base.BaseFragment;

/**
 * 个人中心
 * Created by vincent on 2015/8/3.
 */
public class HomeMyFragment extends BaseFragment {


    public HomeMyFragment(ActionBar actionBar) {
        super(actionBar);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        view = super.onCreateView(inflater, container, bundle);

        initActionBar();
        return view;
    }

    private void initActionBar() {
        tv_actionbar_title.setVisibility(View.VISIBLE);
        tv_actionbar_title.setText("我");
    }

    @OnClick(value = {R.id.userHead, R.id.set, R.id.message})
    private void OnClick(View v) {
        switch (v.getId()) {
            case R.id.userHead:
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                break;
            case R.id.set:
                Intent s = new Intent(getActivity(), SettingActivity.class);
                startActivity(s);
                break;
            case R.id.message:
                Intent m = new Intent(getActivity(), MessageActivity.class);
                startActivity(m);
                break;

            default:
                break;
        }
    }
}
