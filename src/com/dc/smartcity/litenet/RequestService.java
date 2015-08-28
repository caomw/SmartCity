package com.dc.smartcity.litenet;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.dc.smartcity.R;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.interf.IResPonseListener;
import com.dc.smartcity.litenet.response.LiteRequest;
import com.dc.smartcity.view.LoadingDialog;

/**
 * 网络请求
 * 
 * @author szsm
 *
 */
public class RequestService {

	protected Context context;
	private Api task;
	private LoadingDialog alertDialog;

	public RequestService(Context context) {
		this.context = context;
		alertDialog = LoadingDialog.create(context,
				context.getString(R.string.loading_public_default));
		task = new Api();
	}

	public void sendRequestWithDialog(LiteRequest request,
			DialogConfig dialogConfig, final IResPonseListener listener) {
		showLoadingDialog(dialogConfig.loadingMessage(),
				dialogConfig.cancelable());
		task.makeHttpPost(request, new IResPonseListener() {

			@Override
			public void onSuccess(String msg, String result) {
				Log.e("RequestService", "onSuccess:currTime:"+System.currentTimeMillis());
				dismissLoadingDialog();
				if (null != listener) {
					listener.onSuccess(msg, result);
				}
			}

			@Override
			public void onError(String code, String msg) {
				Log.e("RequestService", "on onError");
				dismissLoadingDialog();
				if (null != listener) {
					listener.onError(code, msg);
				}
			}

			@Override
			public void onCancel(String msg) {
				if (null != listener) {
					listener.onCancel(msg);
				}
			}
		});

	}

	public void sendRequestWithNoDialog(LiteRequest request,
			final IResPonseListener listener) {
		task.makeHttpPost(request, listener);
	}

	public void showLoadingDialog(int resId, boolean cancelable) {
		String title;
		if (resId <= 0) {
			title = context.getString(R.string.loading_public_default);
		} else {
			title = context.getString(resId);
		}
		if (alertDialog == null) {
			alertDialog = LoadingDialog.create(context, title);
		}

		// 可取消的dialog若存在则不启动新的dialog
		if (alertDialog.isShowing()) {
			if (alertDialog.getDialogCancelable()) {
				return;
			} else {
				alertDialog.dismiss();
			}
		}

		alertDialog.setCanceledOnTouchOutside(false);

		alertDialog.setCancelable(cancelable);
		// if (cancelable) {
		// final String tmpRequest = requestKey;
		// alertDialog.setOnDismissListener(new
		// DialogInterface.OnDismissListener() {
		// @Override
		// public void onDismiss(DialogInterface dialog) {
		// if (null != mHttpTaskHelper && null != tmpRequest) {
		// mHttpTaskHelper.cancelRequest(tmpRequest);
		// }
		// }
		// });
		// } else {
		// alertDialog.setOnDismissListener(null);
		// }
		if(!alertDialog.isShowing()){
			alertDialog.show();
		}
		Log.e("RequestService", "Dialog show:currTime:"+System.currentTimeMillis());
	}

	/**
	 * 显示弹框
	 * 
	 * @param resId
	 */
	public void showLoadingDialog(int resId) {
		showLoadingDialog(resId, false);
	}

	// public void cancelRequest(String requestKey) {
	// mHttpTaskHelper.cancelRequest(requestKey);
	// }

	public void dismissLoadingDialog() {
		if (alertDialog != null) {
			alertDialog.dismiss();
		}
	}

	// public void cancelAllRequest() {
	// mHttpTaskHelper.destroyRequests();
	// }
}
