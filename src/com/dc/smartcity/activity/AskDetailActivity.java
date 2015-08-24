package com.dc.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.bean.AskObj;
import com.dc.smartcity.net.ImageLoader;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.Utils;

/**
 * 问答详情 Created by vincent on 2015/8/9.
 */
public class AskDetailActivity extends BaseActionBarActivity {

	// 问答详情
	AskObj obj;

	@ViewInject(R.id.iv_head)
	ImageView iv_head;
	
	@ViewInject(R.id.tv_name)
	TextView tv_name;
	
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	
	@ViewInject(R.id.tv_status)
	TextView tv_status;
	
	@ViewInject(R.id.tv_content)
	TextView tv_content;
	
	@ViewInject(R.id.tv_ask_date)
	TextView tv_ask_date;
	
	@ViewInject(R.id.tv_ask_location)
	TextView tv_ask_location;
	
	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_ask_detail);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		obj = (AskObj) getIntent().getSerializableExtra(
				BundleKeys.QUESTION_BEAN);
		initActionBar();
		updateUIDatas();
	}

	private void initActionBar() {

		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle("详情");
		iv_actionbar_right.setImageResource(R.drawable.attu);
		iv_actionbar_right.setVisibility(View.VISIBLE);
		iv_actionbar_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Utils.showToast("关注....", AskDetailActivity.this);
			}
		});
	}

	private void updateUIDatas() {
		tv_name.setText(obj.userName);
		tv_title.setText(obj.title);
		tv_status.setText(obj.status);// 需处理
		tv_content.setText(obj.content);
		tv_ask_date.setText(obj.happenTime);
		tv_ask_location.setText(obj.location);
		ImageLoader.getInstance().displayImage(obj.photoUrl, iv_head);
	}

}
