package com.dc.smartcity.activity;

import android.os.Bundle;
import android.widget.TextView;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;

/**
 * Created by vincent on 2015/8/3.
 */
public class WelcomeActivity extends BaseActionBarActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_welcome);
    }

    @ViewInject(R.id.tvTest)
    private TextView tvTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTest.setText("产品导读");
    }
}
