package com.dc.smartcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.bean.user.UserAuthBean;
import com.dc.smartcity.bean.user.UserBaseBean;
import com.dc.smartcity.bean.user.UserLocalBean;
import com.dc.smartcity.bean.user.UserObj;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.SHA1;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.util.Utils;

/**
 * 登陆 
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		hideActionBar();
	}

	@OnClick(value = { R.id.btnLogin, R.id.tv_forgetpass, R.id.tv_regist,
			R.id.ivClose })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			String n = name.getText().toString().trim();
			String p = pass.getText().toString().trim();
			if (!TextUtils.isEmpty(n) && !TextUtils.isEmpty(p)) {
				SHA1 sha1 = new SHA1();
				String password = sha1.getDigestOfString(p.getBytes());
				doLogin(n, password);
			}
			break;
		case R.id.tv_forgetpass:

			break;
		case R.id.tv_regist:
			startActivity(new Intent(this, RegistAct.class));
			break;
		case R.id.ivClose:
			finish();
			break;
		default:
			break;
		}

	}

	private void doLogin(String n, String p) {
		sendRequestWithDialog(RequestPool.requestLogin(n, p),
				new DialogConfig.Builder().build(), new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {

						Utils.setAccessTicket(JSON.parseObject(result)
								.getString("accessTicket"));
						queryUserInfo();
					}

					@Override
					public void onError(String code, String msg) {
						Utils.showToast(msg, LoginActivity.this);
					}
				});
	}

	/**
	 * 查询用户信息
	 */
	private void queryUserInfo() {
		sendRequestWithDialog(RequestPool.requestUserInfo(),
				new DialogConfig.Builder().build(), new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						JSONObject obj = JSON.parseObject(result);
						UserObj user = new UserObj();
						user.userBase = JSON.parseObject(
								obj.getString("USERBASIC"), UserBaseBean.class);
						user.userAuth = JSON.parseObject(
								obj.getString("USERAUTH"), UserAuthBean.class);
						user.userLocal = JSON.parseObject(obj.getString("LOCALUSER"), UserLocalBean.class);
						
						if (null != user.userBase) {
							Utils.user = user;
						}
						finish();
					}
				});
	}
}
