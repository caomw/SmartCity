package com.dc.smartcity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.dcone.ut.DbUtils;
import com.android.dcone.ut.exception.DbException;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.db.DbFactory;
import com.dc.smartcity.db.dao.ServiceDao;
import com.dc.smartcity.db.dao.ServiceHistoryDao;
import com.dc.smartcity.db.tab.ServiceHistory;
import com.dc.smartcity.db.tab.ServiceObj;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.util.Utils;
import com.dc.smartcity.view.gridview.BaseViewHolder;
import com.dc.smartcity.view.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索服务 Created by vincent on 2015/8/7.
 */
public class SearchServiceActivity extends BaseActionBarActivity {
	private String TAG = SearchServiceActivity.class.getSimpleName();

	private InputMethodManager imm;

	@ViewInject(R.id.lv_search_history)
	private PullToRefreshListView lv_search_history;
	@ViewInject(R.id.lv_search_result)
	private PullToRefreshListView lv_search_result;

	private DbUtils dbUtils;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_serach_service);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		initActionBar();
		dbUtils = DbFactory.getDBUtils(mContext);

		// 模拟搜索历史数据
		ServiceHistoryDao dao = new ServiceHistoryDao(dbUtils);
		ArrayList<ServiceHistory> l = new ArrayList<ServiceHistory>();
		for (int i = 0; i < 4; i++) {
			ServiceHistory serviceHistory = new ServiceHistory();
			serviceHistory.name = "公交查询" + i;
			serviceHistory.imageUrl = "http://img3.3lian.com/2014/s5/35/d/94.jpg";
			serviceHistory.url = "http://baike.baidu.com/";
			l.add(serviceHistory);
		}
		try {
			dao.insertServiceList(l);
		} catch (DbException e) {
			e.printStackTrace();
			Utils.showToast("插入数据库异常", mContext);
		}
		// 模拟结束。。。。。

		// 模拟搜索历史数据
		ServiceDao dao1 = new ServiceDao(dbUtils);
		ArrayList<ServiceObj> ll = new ArrayList<ServiceObj>();
		for (int i = 0; i < 4; i++) {
			ServiceObj service = new ServiceObj();
			service.name = "公交查询地图导航----" + i;
			service.imageUrl = "http://img3.3lian.com/2014/s5/35/d/94.jpg";
			service.url = "http://baike.baidu.com/";
			ll.add(service);
		}
		try {
			dao1.insertServiceList(ll);
		} catch (DbException e) {
			e.printStackTrace();
			Utils.showToast("插入数据库异常", mContext);
		}
		// 模拟结束。。。。。

		showHistory();
	}

	/**
	 * 显示历史记录
	 */
	private void showHistory() {
		final ServiceHistoryDao dao = new ServiceHistoryDao(dbUtils);
		try {
			final List<ServiceHistory> list = dao.getAll();
			if (null != list && list.size() > 0) {
				final HistoryListAdapter adapter = new HistoryListAdapter(list);
				lv_search_history.setAdapter(adapter);

				final View footerView = mLayoutInflater.inflate(
						R.layout.search_history_list_footerview, null);
				footerView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// 删除历史记录
						dao.removeAll();
						list.clear();
						adapter.notifyDataSetChanged();
						lv_search_history.removeFooterView(footerView);
					}
				});
				lv_search_history.addFooterView(footerView);
				lv_search_history
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								startWebViewActivity(list.get(position).url,
										list.get(position).name);
							}
						});
			}
		} catch (DbException e) {
			e.printStackTrace();
			Utils.showToast("查询数据库异常", mContext);
		}
	}

	private void startWebViewActivity(String url, String title) {
		Intent intent = new Intent(SearchServiceActivity.this,
				WebViewActivity.class);
		intent.putExtra(BundleKeys.WEBVIEW_LOADURL, url);
		intent.putExtra(BundleKeys.WEBVIEW_TITLE, title);
		startActivity(intent);
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
		et_actionbar_search.addTextChangedListener(watcher);
		et_actionbar_search.requestFocusFromTouch();
	}

	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
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
				lv_search_history.setVisibility(View.GONE);
				lv_search_result.setVisibility(View.VISIBLE);
				doSearch(keyWord);
			} else {
				lv_search_history.setVisibility(View.VISIBLE);
				showHistory();
				lv_search_result.setVisibility(View.GONE);
			}
		}
	};

	private void doSearch(String keyWord) {
		ServiceDao dao = new ServiceDao(dbUtils);
		try {
			final List<ServiceObj> list = dao.getListByKey(keyWord);
			ListAdapter adapter = new ListAdapter(list);
			lv_search_result.setAdapter(adapter);
			lv_search_result
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							startWebViewActivity(list.get(position).url,
									list.get(position).name);
						}
					});
		} catch (DbException e) {

		}
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
			imm.showSoftInput(et_actionbar_search,
					InputMethodManager.RESULT_SHOWN);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
					InputMethodManager.HIDE_IMPLICIT_ONLY);
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
		private List<ServiceObj> list = new ArrayList<ServiceObj>();

		public ListAdapter(List<ServiceObj> list) {
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
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.search_result_list_item, parent, false);
			}
			TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
			ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
			// iv.setBackgroundResource();

			ServiceObj obj = list.get(position);
			tv.setText(obj.name);
			return convertView;
		}
	}

	/**
	 * 搜索历史记录
	 */
	class HistoryListAdapter extends BaseAdapter {
		private List<ServiceHistory> list = new ArrayList<ServiceHistory>();

		public HistoryListAdapter(List<ServiceHistory> list) {
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
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.search_history_list_item, parent, false);
			}
			TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
			ServiceHistory obj = list.get(position);
			tv.setText(obj.name);
			return convertView;
		}
	}
}
