package com.dc.smartcity.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.util.ULog;

/**
 * 搜索服务
 * Created by vincent on 2015/8/7.
 */
public class SearchServiceActivity extends BaseActionBarActivity {

    private InputMethodManager imm;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_serach_service);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initActionBar();
        showSoftInput();
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        et_actionbar_search.setVisibility(View.VISIBLE);
        et_actionbar_search.setHint("请输入服务名称");

        et_actionbar_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 响应键盘上的搜索键
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    // search();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftInput();
    }

    @Override
    public void onBackPressed() {
        hideSoftInput();
        super.onBackPressed();
    }

    /**
     * 显示键盘
     */
    private void showSoftInput() {
        ULog.error("----showSoftInput()");
        if (imm != null && imm.isActive()) {
            imm.showSoftInput(et_actionbar_search, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    /**
     * 隐藏键盘
     */
    private void hideSoftInput() {
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(et_actionbar_search.getWindowToken(), 0);
        }
    }
}
