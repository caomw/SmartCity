package com.dc.smartcity.activity;

import android.os.Bundle;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;

/**
 * Created by vincent on 2015/8/7.
 */
public class ServiceMarketActivity extends BaseActionBarActivity {


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_service_market);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBarTitle("服务超市");
    }
}
