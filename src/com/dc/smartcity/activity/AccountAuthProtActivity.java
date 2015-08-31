package com.dc.smartcity.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
/**
 * 实名认证注册协议
 * @author szsm_dyj
 *
 */
public class AccountAuthProtActivity extends BaseActionBarActivity {

	@ViewInject(R.id.tvhtml)
	TextView tvhtml;
	@Override
	protected void setContentView() {
		setContentView(R.layout.act_regist_pro);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
		setProtecText();
	}
	
	private void setProtecText() {
		tvhtml.setText(Html.fromHtml(getString(R.string.auth_string_html)));
	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle("用户实名认证服务协议");
	}

}
