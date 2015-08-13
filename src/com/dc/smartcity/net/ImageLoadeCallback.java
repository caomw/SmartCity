package com.dc.smartcity.net;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import com.android.dcone.ut.picasso.Callback;

/**
 * 图片下载状态回调扩展类。用于统一处理进度条
 */
public class ImageLoadeCallback implements Callback {

    ProgressBar progressBar;
    Handler handler;

    public ImageLoadeCallback(ProgressBar progressBar, Handler handler) {
        this.progressBar = progressBar;
        this.handler = handler;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onChangeProgress(int progress) {
        if (progressBar != null && handler != null) {
            Message mes = Message.obtain();
            mes.what = progress;
            mes.obj = progressBar;
            handler.sendMessage(mes);
        }
    }

}
