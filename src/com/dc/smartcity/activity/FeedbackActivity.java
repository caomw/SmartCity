package com.dc.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;

/**
 * 反馈意见
 * Created by vincent on 2015/8/17.
 */
public class FeedbackActivity extends BaseActionBarActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_feedback);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle("意见反馈");
    }


    @OnClick(value = {R.id.tv_feedback})
    private void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedback:
                //提交反馈
                break;
            default:
                break;
        }
    }
}
