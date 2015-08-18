package com.dc.smartcity.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.activity.*;
import com.dc.smartcity.base.BaseApplication;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.Utils;

/**
 * 个人中心
 * Created by vincent on 2015/8/3.
 */
public class HomeMyFragment extends BaseFragment {

    public HomeMyFragment() {
    }

    public HomeMyFragment(ActionBar actionBar) {
        super(actionBar);
    }

    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.tvNotlogin)
    private TextView tvNotlogin;
    @ViewInject(R.id.l_login)
    private LinearLayout l_login;

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

    @Override
    public void onResume() {
        super.onResume();
//        if (Utils.isLogin()) {
        if (true) {
            name.setVisibility(View.VISIBLE);
//            name.setText(Utils.getUserInfo().userName);
            tvNotlogin.setVisibility(View.GONE);
            l_login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initActionBar();
        }
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.GONE);
        tv_actionbar_left.setVisibility(View.GONE);
        et_actionbar_search.setVisibility(View.GONE);
        tv_actionbar_title.setVisibility(View.VISIBLE);
        tv_actionbar_title.setText("我");
        iv_actionbar_right.setVisibility(View.GONE);
        tv_actionbar_right.setVisibility(View.GONE);
    }

    @OnClick(value = {R.id.userHead, R.id.ll_safe_set, R.id.ll_edit_info, R.id.ll_news, R.id.ll_news1, R.id.ll_news2, R.id.tvNotlogin, R.id.tv_setting, R.id.tv_about, R.id.tv_share, R.id.tv_feedback, R.id.tv_problem, R.id.tv_welcome})
    private void OnClick(View v) {
        switch (v.getId()) {
            case R.id.userHead:
                //更换头像
                break;
            case R.id.tvNotlogin:
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                break;
            case R.id.ll_safe_set:
                Intent intent_safe = new Intent(getActivity(), AccountSettingActivity.class);
                startActivity(intent_safe);
                break;
            case R.id.ll_edit_info:
                Intent m = new Intent(getActivity(), ModifyUserInfoActivity.class);
                startActivity(m);
                break;
            case R.id.ll_news:
                break;
            case R.id.ll_news1:
                break;
            case R.id.ll_news2:
                Intent o = new Intent(getActivity(), NewsDetailActivity.class);
                startActivity(o);
                break;
            case R.id.tv_setting:
                Intent s = new Intent(getActivity(), SettingActivity.class);
                startActivity(s);
                break;
            case R.id.tv_about:
                //关于
                Intent intent_about = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent_about);
                break;
            case R.id.tv_share:
                //分享

                break;
            case R.id.tv_feedback:
                //反馈
                Intent intent_feedback = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent_feedback);
                break;
            case R.id.tv_problem:
                //常见问题
                Intent intent_problem = new Intent(getActivity(), WebViewActivity.class);
                intent_problem.putExtra(BundleKeys.WEBVIEW_TITLE, "常见问题");
                intent_problem.putExtra(BundleKeys.WEBVIEW_LOADURL, "http://m.baidu.com");
                startActivity(intent_problem);
                break;
            case R.id.tv_welcome:
                //产品导读
                Intent intent_welcome = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent_welcome);
                break;
            default:
                break;
        }
    }
}
