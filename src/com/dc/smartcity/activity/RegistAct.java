package com.dc.smartcity.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;

/**
 * 注册Activity
 * 
 * @author szsm_dyj
 *
 */
public class RegistAct extends BaseActionBarActivity {

	@ViewInject(R.id.et_user_name)
	private EditText et_user_name;
	
	@ViewInject(R.id.et_mobile)
	private EditText et_mobile;
	
	@ViewInject(R.id.tvGetVerify)
	private TextView tvGetVerify;
	
	@ViewInject(R.id.et_verify_code)
	private EditText et_verify_code;
	
	@ViewInject(R.id.et_password)
	private EditText et_password;
	
	@ViewInject(R.id.et_confirm_password)
	private EditText et_confirm_password;
	
	@ViewInject(R.id.cb_agree)
	private CheckBox cb_agree;
	
	/**
	 * 当前是否在60秒内
	 */
	private boolean isVerify;
	@Override
	protected void setContentView() {
		setContentView(R.layout.act_regist);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle(getString(R.string.tv_regist));
	}

	@OnClick(value={R.id.tvGetVerify, R.id.tv_user_rules, R.id.btn_regist})
	public void onClk(View v){
		switch (v.getId()) {
		case R.id.tvGetVerify:
			tvGetVerify.setText("");
			
			break;
		case R.id.tv_user_rules:
			
			break;
		case R.id.btn_regist:
			
			break;

		default:
			break;
		}
	}
	
}
