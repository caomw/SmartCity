package com.dc.smartcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;

/**
 * Created by vincent on 2015/8/3.
 */
public class SettingActivity extends BaseActionBarActivity {

    @ViewInject(R.id.tv_about)
    private TextView tv_about;
    @ViewInject(R.id.tv_share)
    private TextView tv_share;
    @ViewInject(R.id.tv_feedback)
    private TextView tv_feedback;
    @ViewInject(R.id.tv_problem)
    private TextView tv_problem;
    @ViewInject(R.id.tv_welcome)
    private TextView tv_welcome;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_setting);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle("设置");
    }


    @OnClick(value = {R.id.tv_about, R.id.tv_share, R.id.tv_feedback, R.id.tv_problem, R.id.tv_welcome})
    private void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_about:
                Intent i = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(i);
                break;
            case R.id.tv_share:

                break;
            case R.id.tv_feedback:

                break;
            case R.id.tv_problem:
                Intent m = new Intent(SettingActivity.this, MessageActivity.class);
                startActivity(m);
                break;
            case R.id.tv_welcome:


                break;
            default:
                break;
        }
    }
}