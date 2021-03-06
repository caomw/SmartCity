package com.dc.smartcity.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.smartcity.R;

/**
 * 进度条对话框
 */
public class LoadingDialog extends Dialog {

    private static LoadingDialog mLoadingDialog;
    private boolean flag;

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public LoadingDialog(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param message
     * @param cancelable
     * @return
     */
    private static LoadingDialog create(Context context, String message, boolean cancelable) {
        if (null == mLoadingDialog){
        	mLoadingDialog = new LoadingDialog(context, R.style.CustomProgressDialog);
        }
        mLoadingDialog.setContentView(R.layout.myprogressdialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        TextView tv_message = (TextView) mLoadingDialog.findViewById(R.id.tv_message);
        if (TextUtils.isEmpty(message))
            tv_message.setVisibility(View.GONE);
        else
            tv_message.setText(message);
        mLoadingDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return mLoadingDialog;
    }

    /**
     * 不可取消
     *
     * @param context
     * @param message
     * @return
     */
    public static LoadingDialog create(Context context, String title, String message) {
        return create(context, message, false);
    }

    /**
     * 不可取消
     *
     * @param context
     * @param message
     * @return
     */
    public static LoadingDialog create(Context context, String message) {
        return create(context, message, false);
    }

    /**
     * 不可取消
     *
     * @param context
     * @return
     */
    public static LoadingDialog create(Context context) {
        return create(context, null, false);
    }

    @Override
    public void cancel() {
        super.cancel();
        mLoadingDialog = null;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mLoadingDialog = null;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mLoadingDialog != null) {
            ImageView imageView = (ImageView) mLoadingDialog.findViewById(R.id.loadingImageView);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
            animationDrawable.start();
        }
    }
    
    @Override
    public void setCancelable(boolean flag) {
        this.flag = flag;
        super.setCancelable(flag);
    }

    public boolean getDialogCancelable() {
        return flag;
    }
}