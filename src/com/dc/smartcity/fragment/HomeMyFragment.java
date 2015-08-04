package com.dc.smartcity.fragment;

import android.content.Intent;
import android.view.View;
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


    @Override
    protected int setContentView() {
        return R.layout.fragment_my;
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
