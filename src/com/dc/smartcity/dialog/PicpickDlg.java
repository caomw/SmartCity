package com.dc.smartcity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.dc.smartcity.R;
/**
 * 照片选择dlg
 * @author szsm_dyj
 *
 */
public class PicpickDlg extends Dialog implements android.view.View.OnClickListener{

	IPictureListener listener;
	public PicpickDlg(Context context,IPictureListener listener) {
		super(context,R.style.DialogTheme);
		setContentView(R.layout.dlg_pick_pic);
		
		setWindow();
		this.listener = listener;
		findViewById(R.id.btn_take_pic).setOnClickListener(this);
		findViewById(R.id.btn_album).setOnClickListener(this);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
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
		switch (v.getId()) {
		case R.id.btn_take_pic:
			dismiss();
			listener.notifyCamera();
			break;
		case R.id.btn_album:
			dismiss();
			listener.notifyAlbum();
			break;
		case R.id.btn_cancel:
			dismiss();
			break;
		default:
			break;
		}
	}
	
	public interface IPictureListener{
		public void notifyCamera();
		public void notifyAlbum();
	}
}
