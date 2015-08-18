package com.dc.smartcity.util;

import com.dc.smartcity.R;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 
 * @author szsm_dyj
 *
 */
public class MyCount extends CountDownTimer {

	private TextView verifyBtn;

	private Context context;

	private static  long MILLS_IN_FUTURE = 60000;
	

	private  long COUNT_MILLS = 0;

	private final static int COUNT_DOWN_INTERVAL = 1000;

	public MyCount(final Context context, final TextView verifyBtn) {
		super(MILLS_IN_FUTURE, COUNT_DOWN_INTERVAL);
		this.verifyBtn = verifyBtn;
		this.context = context;
	}

	public MyCount(final Context context, final TextView verifyBtn, final long MILLS_IN_FUTURE) {
		super(MILLS_IN_FUTURE, COUNT_DOWN_INTERVAL);
		this.verifyBtn = verifyBtn;
		this.context = context;
	}

	public boolean getMills() {
		if (MILLS_IN_FUTURE == 0 || COUNT_MILLS == MILLS_IN_FUTURE) {
			return false;
		}
		return true;
	}

	public long getCurrMill() {
		return COUNT_MILLS;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		COUNT_MILLS = millisUntilFinished;
		verifyBtn.setEnabled(false);
		verifyBtn.setText(context.getString(R.string.tv_send_verifycode_count, millisUntilFinished / 1000));
	}

	@Override
	public void onFinish() {
		COUNT_MILLS = 60000;
		MILLS_IN_FUTURE = 60000;
		verifyBtn.setEnabled(true);
		verifyBtn.setText(R.string.tv_send_verifycode);
	}

}
