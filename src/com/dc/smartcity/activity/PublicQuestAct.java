package com.dc.smartcity.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.util.Utils;

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
	EditText tv_gps;

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

	String isPublic = "1";

	@OnClick(value = { R.id.btn_submit, R.id.tv_ispublic, R.id.iv_pic1,
			R.id.iv_pic2, R.id.iv_pic3, R.id.iv_pic4, R.id.iv_pic5 })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			submitWg();
			break;
		case R.id.tv_ispublic:
			// 公开1；不公开0
			if ("1".equals(isPublic)) {
				isPublic = "0";
				tv_ispublic.setText("不公开");
			} else {
				isPublic = "1";
				tv_ispublic.setText("公开");
			}
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

	private void submitWg() {
		String title = et_title.getText().toString().trim();
		if (TextUtils.isEmpty(title)) {
			Utils.showToast("标题不能为空", this);
			return;
		}
		String content = et_content.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			Utils.showToast("内容不能为空", this);
			return;
		}
		String loc = tv_gps.getText().toString().trim();
		if (TextUtils.isEmpty(loc)) {
			Utils.showToast("地址不能为空", this);
			return;
		}

		sendRequestWithDialog(
				RequestPool.submitWeiGuan(title, content, isPublic, loc),
				new DialogConfig.Builder().build(), new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						Utils.showToast("发布成功", PublicQuestAct.this);
						finish();
					}
					@Override
					public void onError(String code, String msg) {
						Utils.showToast("发布失败", PublicQuestAct.this);
						finish();
					}
				});
	}
}
