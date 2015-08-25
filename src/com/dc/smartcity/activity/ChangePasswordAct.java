package com.dc.smartcity.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.SHA1;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.util.Utils;

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

	@OnClick(R.id.btn_auth)
	private void onClick(View v) {
		String opass = et_prepassword.getText().toString().trim();
		if (TextUtils.isEmpty(opass)) {
			Utils.showToast("请输入原密码", this);
			return;
		}
		String npass = et_new_pass.getText().toString().trim();
		if (TextUtils.isEmpty(npass)) {
			Utils.showToast("请输入新密码", this);
			return;
		}
		String npass2 = et_confirm_pass.getText().toString().trim();
		if (!npass2.equals(npass)) {
			Utils.showToast("新密码不一致", this);
			return;
		}
		SHA1 sha1 = new SHA1();
		sendRequestWithDialog(RequestPool.changePass(
				sha1.getDigestOfString(opass.getBytes()),
				sha1.getDigestOfString(npass.getBytes())),
				new DialogConfig.Builder().build(), new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						Utils.showToast("修改成功", ChangePasswordAct.this);
						finish();
					}

					@Override
					public void onError(String code, String msg) {
						Utils.showToast("修改失败", ChangePasswordAct.this);
						finish();
					}
				});

	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle("更改登录密码");
	}

}
