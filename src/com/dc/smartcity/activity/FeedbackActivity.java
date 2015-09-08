package com.dc.smartcity.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.util.Utils;

/**
 * 反馈意见
 * Created by vincent on 2015/8/17.
 */
public class FeedbackActivity extends BaseActionBarActivity {

    @ViewInject(R.id.et_feedback)
    private EditText et_feedback;

    @ViewInject(R.id.btn_submit)
    private Button btn_submit;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_feedback);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();

        et_feedback.setOnFocusChangeListener(onFocusAutoClearHintListener);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_feedback.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Utils.showToast("请输入反馈内容", mContext);
                } else {
                    sendRequestWithDialog(RequestPool.feedback(content), new DialogConfig.Builder().build(), new RequestProxy() {

                        @Override
                        public void onSuccess(String msg, String result) {
                            Utils.showToast("提交反馈成功", mContext);
                            et_feedback.setText("");
                        }
                    });
                }
            }
        });
    }

    EditText.OnFocusChangeListener onFocusAutoClearHintListener = new EditText.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editview = (EditText) v;
            String hint;
            if (hasFocus) {
                hint = editview.getHint().toString();
                editview.setTag(hint);
                editview.setHint("");
            } else {
                hint = editview.getTag().toString();
                editview.setHint(hint);
            }
        }
    };

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle("意见反馈");
    }

}
