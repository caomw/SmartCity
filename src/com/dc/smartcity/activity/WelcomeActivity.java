package com.dc.smartcity.activity;

import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;

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
    protected void test(){
    	
    	sendRequestWithDialog(RequestPool.getRegistVerifyCode("15251446176"), new  DialogConfig.Builder().build(), new RequestProxy() {

			@Override
			public void onSuccess(String msg, String result) {
				tvTest.setText(result);
			}
			
		});
    }
}
