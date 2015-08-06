package com.dc.smartcity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.dcone.ut.ViewUtils;
import com.dc.smartcity.R;
import com.dc.smartcity.view.LoadingDialog;

public abstract class BaseFragment extends Fragment {

    protected View view;

    public LoadingDialog mLoadingDialog;
    public FragmentManager mFragmentManager;
    public LayoutInflater mLayoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mLoadingDialog = LoadingDialog.create(getActivity(), getActivity().getString(R.string.loading));
        mFragmentManager = getActivity().getSupportFragmentManager();
        mLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(setContentView(), null);
        try {
            ViewUtils.inject(this, view); //注入view和事件
            return view;
        } catch (Exception e) {
        }
        return null;
    }


    protected abstract int setContentView();


}
