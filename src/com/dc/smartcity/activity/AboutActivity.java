package com.dc.smartcity.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.base.BaseApplication;

/**
 * 关于
 * Created by vincent on 2015/8/14.
 */
public class AboutActivity extends BaseActionBarActivity {

    @ViewInject(R.id.tv_about)
    private TextView tv_about;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle("关于");
        tv_about.setText("关于关于关于关于关于关于关于关于关于关于关于关于关于关于关于关于关于关于关于关于");
    }
}
