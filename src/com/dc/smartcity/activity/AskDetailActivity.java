package com.dc.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.util.Utils;

/**
 * 问答详情
 * Created by vincent on 2015/8/9.
 */
public class AskDetailActivity extends BaseActionBarActivity {


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_ask_detail);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        initViews();
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle("详情");
        iv_actionbar_right.setImageResource(R.drawable.attu);
        iv_actionbar_right.setVisibility(View.VISIBLE);
        iv_actionbar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showToast("关注....", AskDetailActivity.this);
            }
        });
    }


    private void initViews() {
    }

}
