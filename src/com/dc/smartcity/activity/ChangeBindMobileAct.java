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
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.util.MyCount;
import com.dc.smartcity.util.Utils;

/**
 * 更改绑定手机号码1 szsm_dyj 2015/08/20
 *
 */
public class ChangeBindMobileAct extends BaseActionBarActivity {

	@ViewInject(R.id.et_mobile)
	EditText et_mobile;

	@ViewInject(R.id.tvGetVerify)
	TextView tvGetVerify;

	@ViewInject(R.id.et_verify_code)
	EditText et_verify_code;

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

	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle("更改手机号码");
	}

	@OnClick(value = { R.id.btn_auth, R.id.tvGetVerify })
	private void onCk(View v) {
		switch (v.getId()) {
		case R.id.btn_auth:
			sendChange();
			break;
		case R.id.tvGetVerify:
			sendVerify();
			break;
		default:
			break;
		}
	}

	// 更改手机
	private void sendChange() {
		if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
			Utils.showToast("请输入手机号", ChangeBindMobileAct.this);
			return;
		}
		String code = et_verify_code.getText().toString().trim();
		if (TextUtils.isEmpty(code)) {
			Utils.showToast("请输入验证码", ChangeBindMobileAct.this);
			return;
		}
		sendRequestWithDialog(RequestPool.changeBindMobile(mobile, code),
				new DialogConfig.Builder().build(), new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						Utils.showToast("更改手机号码绑定成功", ChangeBindMobileAct.this);
						finish();
					}

					@Override
					public void onError(String code, String result) {
						Utils.showToast(result, ChangeBindMobileAct.this);
					}
				});
	}

	String mobile;
	MyCount mc;

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
		sendRequestWithDialog(RequestPool.getVerifyCode(mobile, "01"),
				new DialogConfig.Builder().build(), new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						Utils.showToast("验证码发送成功", ChangeBindMobileAct.this);
					}

					@Override
					public void onError(String msg, String result) {
						Utils.showToast(result, ChangeBindMobileAct.this);
					}
				});
	}
}
