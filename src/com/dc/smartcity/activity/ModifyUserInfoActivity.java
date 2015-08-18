package com.dc.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;

/**
 * 修改用户资料
 * Created by vincent on 2015/8/18.
 */
public class ModifyUserInfoActivity extends BaseActionBarActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_modify_user_info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle("个人资料");
    }


}
