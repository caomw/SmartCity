package com.dc.smartcity.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.webkit.*;
import com.dc.smartcity.R;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.view.LoadingDialog;

/**
 * ͳһ��webview��activity
 */
public class MyWebViewActivity extends Activity {

    protected LoadingDialog mLoadingDialog;
    protected WebView my_webview;
    protected String loadurl = "";
    protected String title = "";
    protected Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_webview_layout);

        mLoadingDialog = LoadingDialog.create(MyWebViewActivity.this, MyWebViewActivity.this.getString(R.string.loading));
        my_webview = (WebView) findViewById(R.id.my_webview);
        loadurl = getIntent().getStringExtra(BundleKeys.WEBVIEEW_LOADURL);
        title = getIntent().getStringExtra(BundleKeys.WEBVIEEW_TITLE);

        /*----����WebView�ؼ�����----*/
        WebSettings webSettings = my_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);// ������ӦJS
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        my_webview.setVerticalScrollBarEnabled(false);
        my_webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        my_webview.setWebViewClient(new WebViewClient() {// ����WebView�ͻ��˶���
            /**
             * ��д������������ҳ��ʱWebView����������ϵͳ�����
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
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();// ��https��վ��ͨ�����ʣ�����Ϊ��ͨ��֤�顣
            }
        });

        my_webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activity��Webview���ݼ��س̶Ⱦ����������Ľ��ȴ�С
                // �����ص�100%��ʱ�� �������Զ���ʧ
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}

