package com.dc.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;

/**
 * 安全设置
 * Created by vincent on 2015/8/18.
 */
public class AccountSettingActivity extends BaseActionBarActivity {

    @Override
    protected void setContentView() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle("安全设置");
    }


}
