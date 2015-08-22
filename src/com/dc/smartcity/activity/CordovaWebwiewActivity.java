package com.dc.smartcity.activity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.ULog;

/**
 * 使用cordova引擎的webview
 * 
 * @author check_000
 *
 */
public class CordovaWebwiewActivity extends BaseActionBarActivity implements
		CordovaInterface {

	protected String loadurl = "";
	protected String title = "";

	CordovaWebView wb_cordvo;
	WebSettings webSettings;
	private final ExecutorService threadPool = Executors.newCachedThreadPool();

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadurl = getIntent().getStringExtra(BundleKeys.WEBVIEW_LOADURL);
		title = getIntent().getStringExtra(BundleKeys.WEBVIEW_TITLE);
		initActionBar();

		wb_cordvo = (CordovaWebView) findViewById(R.id.wb_cordvo);
		Log.e(title, loadurl);

		webSettings = wb_cordvo.getSettings();
		webSettings.setJavaScriptEnabled(true);// 设置响应JS
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		wb_cordvo.setVerticalScrollBarEnabled(false);
		wb_cordvo.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//		wb_cordvo.loadUrl(loadurl);
		wb_cordvo.loadUrl("file:///android_asset/www/hospital/index.html");
	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle(title);
	}

	@Override
	public Activity getActivity() {

		return this;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (null != wb_cordvo) {
			wb_cordvo.handleDestroy();
		}
	}

	@Override
	public ExecutorService getThreadPool() {

		return threadPool;
	}

	@Override
	public Object onMessage(String arg0, Object arg1) {

		return null;
	}

	@Override
	public void setActivityResultCallback(CordovaPlugin arg0) {

	}

	@Override
	public void startActivityForResult(CordovaPlugin arg0, Intent arg1, int arg2) {

	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.act_cordvo_webwiew);
	}
}