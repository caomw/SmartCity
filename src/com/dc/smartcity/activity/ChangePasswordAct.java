package com.dc.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
/**
 * 
 * szsm_dyj 2015/08/20
 *
 */
public class ChangePasswordAct extends BaseActionBarActivity {

	@ViewInject(R.id.et_prepassword)
	EditText et_prepassword;
	@ViewInject(R.id.et_new_pass)
	EditText et_new_pass;
	@ViewInject(R.id.et_confirm_pass)
	EditText et_confirm_pass;
	
	
	@Override
	protected void setContentView() {
		setContentView(R.layout.act_change_password);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initActionBar();

	}

	

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle("更改登陆密码");
	}

}
