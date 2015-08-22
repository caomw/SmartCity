package com.dc.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.util.Utils;
/**
 * 更改绑定手机号码1
 * szsm_dyj 2015/08/20
 *
 */
public class ChangeBindMobileAct extends BaseActionBarActivity {

	@ViewInject(R.id.tv_mobile)
	TextView tv_mobile;
	
	@Override
	protected void setContentView() {
		setContentView(R.layout.act_bindmobile_one);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initActionBar();
		initUserInfo();

	}

	private void initUserInfo() {
		tv_mobile.setText(Utils.mixPhone(Utils.user.userAuth.mobilenum));
	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle("更改手机号码");
	}
	
}
