package com.dc.smartcity.fragment;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.litenet.Config;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.view.LoadingDialog;

/**
 * 城市新鲜事，新闻资讯
 * 
 * @author szsm_dyj
 *
 */
public class HomeNewsFragment extends BaseFragment {

	protected LoadingDialog mLoadingDialog;
	@ViewInject(R.id.my_webview)
	protected WebView my_webview;

	public HomeNewsFragment(ActionBar actionbar) {
		super(actionbar);
	}

	@Override
	protected int setContentView() {
		return R.layout.my_webview_layout;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			initActionBar();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		view = super.onCreateView(inflater, container, bundle);
		initActionBar();
		initViews();
		return view;
	}

	private void initViews() {
		 /*----设置WebView控件参数----*/
        WebSettings webSettings = my_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);// 设置响应JS
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        my_webview.setVerticalScrollBarEnabled(false);
        my_webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        my_webview.setWebViewClient(new WebViewClient() {// 设置WebView客户端对象
            /**
             * 重写方法，否则点击页面时WebView会重新启动系统浏览器
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                ULog.error(url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            	
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();// 让https的站点通过访问，设置为可通过证书。
            }
        });

        my_webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activity和Webview根据加载程度决定进度条的进度大小
                // 当加载到100%的时候 进度条自动消失
                if (progress == 100) {
                    if (null != mLoadingDialog) {
                        mLoadingDialog.dismiss();
                    }
                } else {
                    if (null != mLoadingDialog && !mLoadingDialog.isShowing()) {
                        mLoadingDialog.show();
                    }
                }
            }


            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        my_webview.loadUrl(Config.CITY_NEWS);
	}

	private void initActionBar() {
		
		iv_actionbar_left.setVisibility(View.GONE);
		tv_actionbar_left.setVisibility(View.GONE);
		et_actionbar_search.setVisibility(View.GONE);
		tv_actionbar_title.setVisibility(View.VISIBLE);
		tv_actionbar_title.setText("新闻资讯");
		iv_actionbar_right.setVisibility(View.GONE);
		tv_actionbar_right.setVisibility(View.GONE);
	}

}
