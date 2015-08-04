package com.dc.smartcity.base;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.dcone.ut.ViewUtils;
import com.dc.smartcity.R;
import com.dc.smartcity.view.LoadingDialog;


public abstract class BaseActionBarActivity extends FragmentActivity {
    private ActionBar mActionBar;
    private ImageView actionbar_back;
    private ImageView actionbar_title_icon;
    private ImageView action_icon;
    private TextView actionbar_title, actionbar_icon;
    // 默认的actionBar资源文件id
    private final int DEFAULT_ACTIONBAR_RESOURCE_ID = R.layout.main_action_bar;
    private final int DEFALUT_ACTIONBAR_BG_ID = R.drawable.navibar_common_bg;
    private int actionbar_resource_id = DEFAULT_ACTIONBAR_RESOURCE_ID;
    private View mTitleView;

    public String TAG = BaseActionBarActivity.class.getSimpleName();
    public LoadingDialog mLoadingDialog;
    public LayoutInflater mLayoutInflater;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mLoadingDialog = LoadingDialog.create(mContext, mContext.getString(R.string.loading));
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initDefaultActionbar();
        setContentView();
        ViewUtils.inject(this);
    }

    /**
     * 设置contentview
     */
    protected abstract void setContentView();

    protected void onCreate(Bundle savedInstanceState, int actionbar_resource_id) {
        super.onCreate(savedInstanceState);
        this.actionbar_resource_id = actionbar_resource_id;
        initDefaultActionbar();
    }

    protected void initDefaultActionbar() {
        mTitleView = mLayoutInflater.inflate(actionbar_resource_id, null);
        mActionBar = this.getActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowCustomEnabled(true);// 可以自定义actionbar
            mActionBar.setDisplayShowTitleEnabled(false);// 不显示logo
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setBackgroundDrawable(getResources().getDrawable(DEFALUT_ACTIONBAR_BG_ID));
            if (actionbar_resource_id == DEFAULT_ACTIONBAR_RESOURCE_ID) {
                actionbar_back = (ImageView) mTitleView.findViewById(R.id.actionbar_back);
                actionbar_back.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                actionbar_icon = (TextView) mTitleView.findViewById(R.id.actionbar_icon);
                actionbar_icon.setVisibility(View.GONE);
                action_icon = (ImageView) mTitleView.findViewById(R.id.action_icon);
                action_icon.setVisibility(View.GONE);
                actionbar_title = (TextView) mTitleView.findViewById(R.id.actionbar_title);
                actionbar_title.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        actionbar_back.performClick();

                    }
                });
                actionbar_title_icon = (ImageView) mTitleView.findViewById(R.id.actionbar_title_icon);
            }
            ;
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            mActionBar.setCustomView(mTitleView, params);
        }
    }

    public void setActionBarTitle(String title) {
        if (actionbar_title != null) {
            actionbar_title.setText(title);
        }
    }

    public void setHomeBar(String text) {
        if (actionbar_icon != null && action_icon != null) {
            actionbar_back.setVisibility(View.GONE);
            actionbar_icon.setVisibility(View.VISIBLE);
            actionbar_icon.setText(text);
            action_icon.setVisibility(View.VISIBLE);
        }
    }

    public void setHomeBar() {
        if (actionbar_icon != null && action_icon != null) {
            actionbar_icon.setVisibility(View.VISIBLE);
            actionbar_back.setVisibility(View.GONE);
            action_icon.setVisibility(View.VISIBLE);
        }
    }

    public TextView getActionBarTitle() {
        return actionbar_title;
    }

    public ActionBar getmActionBar() {
        return mActionBar;
    }

    public View getActionBarTitleView() {
        return mTitleView;
    }

    public ImageView getActionbarTitleIcon() {
        return actionbar_title_icon;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
