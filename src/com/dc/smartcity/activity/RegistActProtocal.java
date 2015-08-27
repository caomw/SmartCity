package com.dc.smartcity.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
/**
 * 用户协议
 * @author check_000
 *
 */
public class RegistActProtocal extends BaseActionBarActivity {

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
		tvhtml.setText(Html.fromHtml(getString(R.string.regist_protecal_html)));
	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle("用户注册协议");
	}

}
