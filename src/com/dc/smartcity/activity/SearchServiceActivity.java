package com.dc.smartcity.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.bean.ServiceObj;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.view.gridview.BaseViewHolder;
import com.dc.smartcity.view.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;

/**
 * 搜索服务
 * Created by vincent on 2015/8/7.
 */
public class SearchServiceActivity extends BaseActionBarActivity {
    private String TAG = SearchServiceActivity.class.getSimpleName();

    private InputMethodManager imm;

    @ViewInject(R.id.lv_search_history)
    private PullToRefreshListView lv_search_history;
    @ViewInject(R.id.lv_search_result)
    private PullToRefreshListView lv_search_result;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_serach_service);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initActionBar();
        updateHistory();
    }

    /**
     * 更新历史记录，需要实时查询数据库
     */
    private void updateHistory() {
        ArrayList<ServiceObj> list = new ArrayList<ServiceObj>();
        for (int i = 0; i < 4; i++) {
            ServiceObj serviceObj = new ServiceObj();
            serviceObj.name = "公交查询";
            list.add(serviceObj);
        }
        HistoryListAdapter adapter = new HistoryListAdapter(list);
        lv_search_history.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        et_actionbar_search.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            String keyWord = et_actionbar_search.getText().toString();
            if (!TextUtils.isEmpty(keyWord)) {
                doSearch(keyWord);
            } else {
                lv_search_history.setVisibility(View.VISIBLE);
                updateHistory();
                lv_search_result.setVisibility(View.GONE);
            }
        }
    };

    private void doSearch(String keyWord) {
        ArrayList<ServiceObj> list = new ArrayList<ServiceObj>();
        for (int i = 0; i < 4; i++) {
            ServiceObj serviceObj = new ServiceObj();
            serviceObj.name = keyWord;
            list.add(serviceObj);
        }
        ListAdapter adapter = new ListAdapter(list);
        lv_search_result.setAdapter(adapter);
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


    /**
     * 搜索结果
     */
    class ListAdapter extends BaseAdapter {
        private ArrayList<ServiceObj> list = new ArrayList<ServiceObj>();

        public ListAdapter(ArrayList<ServiceObj> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.search_result_list_item, parent, false);
            }
            TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
            ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
//            iv.setBackgroundResource();

            ServiceObj obj = list.get(position);
            tv.setText(obj.name);
            return convertView;
        }
    }


    /**
     * 搜索历史记录
     */
    class HistoryListAdapter extends BaseAdapter {
        private ArrayList<ServiceObj> list = new ArrayList<ServiceObj>();

        public HistoryListAdapter(ArrayList<ServiceObj> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.search_history_list_item, parent, false);
            }
            TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
            ServiceObj obj = list.get(position);
            tv.setText(obj.name);
            return convertView;
        }
    }
}
