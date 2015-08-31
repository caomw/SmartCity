package com.dc.smartcity.base;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcone.ut.ViewUtils;
import com.dc.smartcity.R;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestService;
import com.dc.smartcity.litenet.interf.IResPonseListener;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.litenet.response.LiteRequest;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.view.LoadingDialog;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActionBarActivity extends FragmentActivity {
	private String TAG = BaseActionBarActivity.class.getSimpleName();

	protected ImageView iv_actionbar_left;
	protected TextView tv_actionbar_left;
	protected TextView tv_actionbar_title;
	protected EditText et_actionbar_search;
	protected TextView tv_actionbar_right;
	protected ImageView iv_actionbar_right;

	protected ActionBar mActionBar;
	public LoadingDialog mLoadingDialog;
	public LayoutInflater mLayoutInflater;
	public Context mContext;

	RequestService baseService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BaseApplication.getInstance().onActCreate(this);
		mContext = this;
		mLoadingDialog = LoadingDialog.create(mContext,
				mContext.getString(R.string.loading));
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setContentView();
		initDefaultActionbar();
		ViewUtils.inject(this);

		baseService = new RequestService(this);

	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		baseService.dismissLoadingDialog();
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BaseApplication.getInstance().onActDestroy(this);
		// baseService.cancelAllRequest();
	}

	/**
	 * 设置contentview
	 */
	protected abstract void setContentView();

	protected void initDefaultActionbar() {
		View actionView = mLayoutInflater.inflate(R.layout.main_action_bar,
				null);
		iv_actionbar_left = (ImageView) actionView
				.findViewById(R.id.iv_actionbar_left);
		tv_actionbar_left = (TextView) actionView
				.findViewById(R.id.tv_actionbar_left);
		tv_actionbar_title = (TextView) actionView
				.findViewById(R.id.tv_actionbar_title);
		et_actionbar_search = (EditText) actionView
				.findViewById(R.id.et_actionbar_search);
		tv_actionbar_right = (TextView) actionView
				.findViewById(R.id.tv_actionbar_right);
		iv_actionbar_right = (ImageView) actionView
				.findViewById(R.id.iv_actionbar_right);

		iv_actionbar_left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ULog.debug("%s----iv_actionbar_left.onClick", TAG);
				onBackPressed();
			}
		});
		mActionBar = this.getActionBar();
		if (mActionBar != null) {
			mActionBar.setDisplayShowCustomEnabled(true);// 可以自定义actionbar
			mActionBar.setDisplayShowTitleEnabled(false);// 不显示logo
			mActionBar.setDisplayShowHomeEnabled(false);
			ActionBar.LayoutParams params = new ActionBar.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			mActionBar.setCustomView(actionView, params);
		}
	}

	public void setActionBarTitle(String title) {
		if (tv_actionbar_title != null) {
			tv_actionbar_title.setVisibility(View.VISIBLE);
			tv_actionbar_title.setText(title);
		}
	}

	public void hideActionBar() {
		if (null == mActionBar)
			mActionBar = this.getActionBar();
		mActionBar.hide();
	}

	public void showActionBar() {
		if (null == mActionBar)
			mActionBar = this.getActionBar();
		mActionBar.show();
	}

	public void sendRequestWithNoDialog(LiteRequest request,
			final IResPonseListener listener) {
		baseService.sendRequestWithNoDialog(request, listener);
	}

	public void sendRequestWithNoDialog(LiteRequest request,
			final RequestProxy callback) {
		baseService.sendRequestWithNoDialog(request, callback);
	}

	public void sendRequestWithDialog(LiteRequest request,
			DialogConfig dialogConfig, final IResPonseListener listener) {
		baseService.sendRequestWithDialog(request, dialogConfig, listener);
	}

	public void sendRequestWithDialog(LiteRequest request,
			DialogConfig dialogConfig, final RequestProxy callback) {
		baseService.sendRequestWithDialog(request, dialogConfig, callback);
	}

	// public void cancelRequest(String requestKey) {
	// baseService.cancelRequest(requestKey);
	// }
}
