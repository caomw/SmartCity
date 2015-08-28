package com.dc.smartcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.SHA1;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.util.MyCount;
import com.dc.smartcity.util.Utils;

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

	private MyCount mc;

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

	@OnClick(value = { R.id.tvGetVerify, R.id.tv_user_rules, R.id.btn_regist })
	public void onClk(View v) {
		switch (v.getId()) {
		case R.id.tvGetVerify:
			sendVerify();
			break;
		case R.id.tv_user_rules:
			startActivity(new Intent(this, RegistActProtocal.class));
			break;
		case R.id.btn_regist:
			senRegist();
			break;

		default:
			break;
		}
	}

	/**
	 * 注册
	 */
	private void senRegist() {
		String name = et_user_name.getText().toString().trim();
		String verifyCode = et_verify_code.getText().toString().trim();
		String pass = et_password.getText().toString().trim();
		String pass2 = et_confirm_password.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			Utils.showToast("用户名不能为空", RegistAct.this);
		} else if (TextUtils.isEmpty(verifyCode)) {
			Utils.showToast("验证码不能为空", RegistAct.this);
		} else if (TextUtils.isEmpty(pass)) {
			Utils.showToast("密码不能为空", RegistAct.this);
		} else if (TextUtils.isEmpty(pass2)) {
			Utils.showToast("密码不能为空", RegistAct.this);
		} else if (!pass.equals(pass2)) {
			Utils.showToast("两次输入的密码不一致", RegistAct.this);
		} else if (!cb_agree.isChecked()) {
			Utils.showToast("请先阅读注册协议", RegistAct.this);
		} else {
			SHA1 sha1 = new SHA1();
			String password = sha1.getDigestOfString(pass.getBytes());
			sendRequestWithDialog(
					RequestPool.registQuest(name, password, mobile, verifyCode),
					new DialogConfig.Builder().build(), new RequestProxy() {

						@Override
						public void onSuccess(String msg, String result) {
							Utils.showToast("注册成功", RegistAct.this);
							finish();
						}

						@Override
						public void onError(String code, String msg) {
							Utils.showToast(msg, RegistAct.this);
						}
					});
		}
	}

	String mobile;

	/**
	 * 发送验证码
	 */
	private void sendVerify() {
		mobile = et_mobile.getText().toString().trim();
		if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
			return;
		}
		if (null == mc) {
			mc = new MyCount(this, tvGetVerify);
		}
		mc.start();
		sendRequestWithNoDialog(RequestPool.getVerifyCode(mobile, "03"),
				new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						Utils.showToast("验证码发送成功", RegistAct.this);
					}

					@Override
					public void onError(String msg, String result) {
						Utils.showToast(result, RegistAct.this);
					}
				});
	}

}
