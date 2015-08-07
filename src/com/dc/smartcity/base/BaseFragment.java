package com.dc.smartcity.base;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.dcone.ut.ViewUtils;
import com.dc.smartcity.R;
import com.dc.smartcity.view.LoadingDialog;

public abstract class BaseFragment extends Fragment {

    protected ImageView iv_actionbar_left;
    protected TextView tv_actionbar_left;
    protected TextView tv_actionbar_title;
    protected EditText et_actionbar_search;
    protected TextView tv_actionbar_right;
    protected ImageView iv_actionbar_right;

    protected View view;

    public LoadingDialog mLoadingDialog;
    public FragmentManager mFragmentManager;
    public LayoutInflater mLayoutInflater;
    private ActionBar mActionbar;

    public BaseFragment(ActionBar actionBar) {
        this.mActionbar = actionBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mLoadingDialog = LoadingDialog.create(getActivity(), getActivity().getString(R.string.loading));
        mFragmentManager = getActivity().getSupportFragmentManager();
        mLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initActionBar();
        view = inflater.inflate(setContentView(), null);
        try {
            ViewUtils.inject(this, view); //注入view和事件
            return view;
        } catch (Exception e) {
        }
        return null;
    }


    protected abstract int setContentView();

    private void initActionBar() {
        if (mActionbar != null) {
            View actionView = mActionbar.getCustomView();
            iv_actionbar_left = (ImageView) actionView.findViewById(R.id.iv_actionbar_left);
            tv_actionbar_left = (TextView) actionView.findViewById(R.id.tv_actionbar_left);
            tv_actionbar_title = (TextView) actionView.findViewById(R.id.tv_actionbar_title);
            et_actionbar_search = (EditText) actionView.findViewById(R.id.et_actionbar_search);
            tv_actionbar_right = (TextView) actionView.findViewById(R.id.tv_actionbar_right);
            iv_actionbar_right = (ImageView) actionView.findViewById(R.id.iv_actionbar_right);
            mActionbar.setDisplayShowCustomEnabled(true);// 可以自定义actionbar
            mActionbar.setDisplayShowTitleEnabled(false);// 不显示logo
            mActionbar.setDisplayShowHomeEnabled(false);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mActionbar.setCustomView(actionView, params);
        }
    }


}
