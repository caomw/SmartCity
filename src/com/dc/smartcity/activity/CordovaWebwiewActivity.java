package com.dc.smartcity.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.ULog;

import org.apache.cordova.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用cordova引擎的webview
 *
 * @author check_000
 */
public class CordovaWebwiewActivity extends BaseActionBarActivity implements
        CordovaInterface {

    protected String loadurl = "";
    protected String title = "";

    CordovaWebView wb_cordvo;
    WebSettings webSettings;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    protected CordovaPreferences prefs = new CordovaPreferences();
    
    protected Whitelist internalWhitelist = new Whitelist();
    protected Whitelist externalWhitelist = new Whitelist();
    protected ArrayList<PluginEntry> pluginEntries;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadurl = getIntent().getStringExtra(BundleKeys.WEBVIEW_LOADURL);
        title = getIntent().getStringExtra(BundleKeys.WEBVIEW_TITLE);
        ULog.error("title=" + title + ",loadurl=" + loadurl);
        initActionBar();

        wb_cordvo = (CordovaWebView) findViewById(R.id.wb_cordvo);
        webSettings = wb_cordvo.getSettings();
        webSettings.setJavaScriptEnabled(true);// 设置响应JS
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        wb_cordvo.setVerticalScrollBarEnabled(false);
        wb_cordvo.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        internalWhitelist.addWhiteListEntry("*", false);
        externalWhitelist.addWhiteListEntry("tel:*", false);
        externalWhitelist.addWhiteListEntry("sms:*", false);
        prefs.set("loglevel", "DEBUG");

//        wb_cordvo.init(this, makeWebViewClient(wb_cordvo), cordovaChromeClient,
//                pluginEntries, internalWhitelist, externalWhitelist, prefs);
        wb_cordvo.loadUrlIntoView(loadurl);
    }

//    protected CordovaWebViewClient makeWebViewClient(CordovaWebView webView) {
//        return webView.makeWebViewClient(this);
//    }

    protected CordovaChromeClient makeChromeClient(CordovaWebView webView) {
        return webView.makeWebChromeClient(this);
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle(title);
        if(!TextUtils.isEmpty(loadurl)){
        	hideActionBar();
        }
    }

    private CordovaWebViewClient mWebC = new CordovaWebViewClient(this, wb_cordvo) {// 设置WebView客户端对象
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
            ULog.error("----onPageFinished");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            ULog.error("----onPageStarted");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();// 让https的站点通过访问，设置为可通过证书。
        }
    };

    private CordovaChromeClient cordovaChromeClient = new CordovaChromeClient(this, wb_cordvo) {

        public void onProgressChanged(WebView view, int progress) {

            ULog.error("----progress=" + progress);

            // Activity和Webview根据加载程度决定进度条的进度大小
            // 当加载到100%的时候 进度条自动消失
            if (progress == 100) {
                if (null != mLoadingDialog) {
                    mLoadingDialog.dismiss();
                    hideActionBar();
                }
            } else {
                if (null != mLoadingDialog && !mLoadingDialog.isShowing()) {
                    mLoadingDialog.show();
                }
            }
        }
    };

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
