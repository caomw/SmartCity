package com.dc.smartcity.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.*;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.view.LoadingDialog;

/**
 * 统一的webview的activity
 */
public class WebViewActivity extends BaseActionBarActivity {

    protected LoadingDialog mLoadingDialog;
    @ViewInject(R.id.my_webview)
    protected WebView my_webview;
    protected String loadurl = "";
    protected String title = "";


    @Override
    protected void setContentView() {
        setContentView(R.layout.my_webview_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog = LoadingDialog.create(WebViewActivity.this, WebViewActivity.this.getString(R.string.loading));
//        my_webview = (WebView) findViewById(R.id.my_webview);
        loadurl = getIntent().getStringExtra(BundleKeys.WEBVIEW_LOADURL);
        title = getIntent().getStringExtra(BundleKeys.WEBVIEW_TITLE);
        initActionBar();

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
                ULog.debug(url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

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
        my_webview.loadUrl(loadurl);
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        tv_actionbar_title.setVisibility(View.VISIBLE);
        tv_actionbar_title.setText(title);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
