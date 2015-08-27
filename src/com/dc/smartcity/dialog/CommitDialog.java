package com.dc.smartcity.dialog;

import com.dc.smartcity.R;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
/**
 * 
 * @author check_000
 *
 */
public class CommitDialog extends Dialog implements View.OnClickListener{

	private EditText et_content;
	OnSendMessage listener;
	public CommitDialog(Context context,OnSendMessage listener) {
		super(context,R.style.DialogTheme);
		setContentView(R.layout.dlg_comment_wg);
		et_content = (EditText)findViewById(R.id.et_content);
		this.listener = listener;
		setWindow();
		findViewById(R.id.btn_submit).setOnClickListener(this);
	}
	
	@Override
	public void show() {
		et_content.setText(null);
		super.show();
	}

	@Override
	public void onClick(View arg0) {
		String comment = et_content.getText().toString().trim();
		if(!TextUtils.isEmpty(comment)){
			listener.sendComment(comment);
			dismiss();
		}
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

	public interface OnSendMessage{
		void sendComment(String comment);
	}
}
