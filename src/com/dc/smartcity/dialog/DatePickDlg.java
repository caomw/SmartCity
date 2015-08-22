package com.dc.smartcity.dialog;

import java.util.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.dc.smartcity.R;

public class DatePickDlg extends Dialog implements View.OnClickListener{

	private DatePicker dp_date;
	Calendar calendar;
	IDateChange listener;
	public DatePickDlg(Context context,final IDateChange listener) {
		super(context,R.style.DialogTheme);
		this.listener = listener;
		setContentView(R.layout.dlg_date_pick);
		
		setWindow();
		dp_date = (DatePicker)findViewById(R.id.dp_date);
		findViewById(R.id.tvConfirm).setOnClickListener(this);
		calendar = Calendar.getInstance();
		dp_date.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Log.e("DatePickDlg", year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
				listener.notifyDateChange(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
			}
		});
	}

	private void setWindow() {
		Window window = getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		WindowManager m = window.getWindowManager();
		Display d = m.getDefaultDisplay();
		LayoutParams p = window.getAttributes();
		p.width = d.getWidth();
		p.height = LayoutParams.WRAP_CONTENT;
		window.setAttributes(p);
	}
	

	@Override
	public void onClick(View v) {
		dismiss();
	}

	public interface IDateChange {
		public void notifyDateChange(String time);
	}
}
