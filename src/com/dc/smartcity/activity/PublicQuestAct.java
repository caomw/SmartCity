package com.dc.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;

/**
 * 我说说
 * 
 * @author szsm_dyj
 *
 */
public class PublicQuestAct extends BaseActionBarActivity {

	// 问答，标题栏
	@ViewInject(R.id.et_title)
	EditText et_title;

	// 内容
	@ViewInject(R.id.et_content)
	EditText et_content;

	// 邮箱
	@ViewInject(R.id.tv_email)
	TextView tv_email;

	// 定位
	@ViewInject(R.id.tv_gps)
	TextView tv_gps;

	// 是否公开
	@ViewInject(R.id.tv_ispublic)
	TextView tv_ispublic;

	// 图片一
	@ViewInject(R.id.iv_pic1)
	ImageView iv_pic1;

	// 图片二
	@ViewInject(R.id.iv_pic2)
	ImageView iv_pic2;

	// 图片三
	@ViewInject(R.id.iv_pic3)
	ImageView iv_pic3;

	// 图片四
	@ViewInject(R.id.iv_pic4)
	ImageView iv_pic4;

	// 图片五
	@ViewInject(R.id.iv_pic5)
	ImageView iv_pic5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
		updateUI();
	}

	/**
	 * 更新个人信息，定位信息
	 */
	private void updateUI() {

	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle("我说说");
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_publish_question);
	}

	@OnClick(value = { R.id.btn_submit, R.id.tv_ispublic, R.id.iv_pic1,
			R.id.iv_pic2, R.id.iv_pic3, R.id.iv_pic4, R.id.iv_pic5 })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:

			break;
		case R.id.tv_ispublic:

			break;
		case R.id.iv_pic1:
			break;
		case R.id.iv_pic2:
			break;
		case R.id.iv_pic3:
			break;
		case R.id.iv_pic4:
			break;
		case R.id.iv_pic5:
			break;
		default:
			break;
		}
	}
}
