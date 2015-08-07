package com.dc.smartcity.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.base.BaseApplication;

/**
 * Created by vincent on 2015/8/3.
 */
public class LoginActivity extends BaseActionBarActivity {
	
	@ViewInject(R.id.name)
	private EditText name;
	@ViewInject(R.id.pass)
	private EditText pass;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }
    
    @OnClick(R.id.btnLogin)
    private void onClick(View v){
    	String n = name.getText().toString().trim();
    	String p = pass.getText().toString().trim();
    	if(!TextUtils.isEmpty(n) && !TextUtils.isEmpty(p)){
    		BaseApplication.user.userName = n;
    		finish();
    	}
    }

}
