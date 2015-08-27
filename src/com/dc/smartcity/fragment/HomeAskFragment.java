package com.dc.smartcity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.activity.AskDetailActivity;
import com.dc.smartcity.activity.AuthAct;
import com.dc.smartcity.activity.LoginActivity;
import com.dc.smartcity.activity.PublicQuestAct;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.bean.AskObj;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.net.ImageLoader;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.util.Utils;
import com.dc.smartcity.view.gridview.BaseViewHolder;
import com.dc.smartcity.view.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.dc.smartcity.view.pullrefresh.PullToRefreshListView;

/**
 * 有问必答 Created by vincent on 2015/8/3.
 */
public class HomeAskFragment extends BaseFragment {
	private String TAG = HomeAskFragment.class.getSimpleName();

	@ViewInject(R.id.rg_login)
	RadioGroup rg_login;

	@ViewInject(R.id.rb_all)
	RadioButton rb_all;

	@ViewInject(R.id.rb_my)
	RadioButton rb_my;

	// @ViewInject(R.id.pullToRefreshListview)
	// private PullToRefreshListView pullToRefreshListview;

	@ViewInject(R.id.viewPager)
	ViewPager viewPager;

	private ActionBar actionBar;

	public HomeAskFragment(ActionBar actionBar) {
		super(actionBar);
		this.actionBar=actionBar;
	}

	public HomeAskFragment() {
	}

	@Override
	protected int setContentView() {
		return R.layout.fragment_ask;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		view = super.onCreateView(inflater, container, bundle);
		initActionBar();
		initAdapter();

		initAllList();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (Utils.isLogon()) {
			rg_login.setVisibility(View.VISIBLE);
			if (tadapter.mListViews.size() < 2) {

				initMyList();
			}

		} else {
			rg_login.setVisibility(View.GONE);
			if (tadapter.mListViews.size() == 2) {
				tadapter.mListViews.remove(1);
				tadapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			initActionBar();
		}
	}

	@OnClick(value = { R.id.rb_all, R.id.rb_my })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.rb_all:
			viewPager.setCurrentItem(0);
			break;
		case R.id.rb_my:
			viewPager.setCurrentItem(1);
			break;
		default:
			break;
		}
	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.GONE);
		tv_actionbar_left.setVisibility(View.GONE);
		et_actionbar_search.setVisibility(View.GONE);
		tv_actionbar_title.setVisibility(View.VISIBLE);
		tv_actionbar_title.setText("有问必答");
		iv_actionbar_right.setVisibility(View.VISIBLE);
		iv_actionbar_right.setImageResource(R.drawable.pub);
		iv_actionbar_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!Utils.isLogon()) {
					startActivity(new Intent(getActivity(), LoginActivity.class));
				} else if ("01".equals(Utils.user.userBase.level)) {
					startActivity(new Intent(getActivity(), AuthAct.class));
				} else {
					startActivity(new Intent(getActivity(),
							PublicQuestAct.class));
				}
			}
		});
		tv_actionbar_right.setVisibility(View.GONE);
	}

	ListAdapter allAdapter;
	ListAdapter myAdapter;
	TabAdapter tadapter;

	private void initAdapter() {
		tadapter = new TabAdapter();
		viewPager.setAdapter(tadapter);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		allAdapter = new ListAdapter(getActivity());
		myAdapter = new ListAdapter(getActivity());

	}

	private void initAllList() {

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.view_pulltorefresh, null);
		final PullToRefreshListView pullToRefreshListview = (PullToRefreshListView) view
				.findViewById(R.id.pullToRefreshListview);

		pullToRefreshListview.setMode(PullToRefreshListView.MODE_BOTH);

		pullToRefreshListview.setAdapter(allAdapter);
		pullToRefreshListview.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public boolean onRefresh(int curMode) {
				if (curMode == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {
					allAdapter.pageNo = 1;
					queryAllAsk(allAdapter.pageNo, allAdapter,
							pullToRefreshListview);
				} else if (curMode == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {
					allAdapter.pageNo++;
					queryAllAsk(allAdapter.pageNo, allAdapter,
							pullToRefreshListview);
				}
				return false;
			}
		});
		pullToRefreshListview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getActivity(),
								AskDetailActivity.class);
						intent.putExtra(BundleKeys.QUESTION_BEAN,
								allAdapter.list.get(position));
						startActivity(intent);
					}
				});

		queryAllAsk(allAdapter.pageNo, allAdapter, pullToRefreshListview);
		tadapter.mListViews.add(view);
		tadapter.notifyDataSetChanged();
		viewPager.setCurrentItem(0);
	}

	TextView tv_mempty;

	private void initMyList() {

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.view_pulltorefresh, null);
		tv_mempty = (TextView) view.findViewById(R.id.tv_empty);
		final PullToRefreshListView pullToRefreshListview = (PullToRefreshListView) view
				.findViewById(R.id.pullToRefreshListview);

		pullToRefreshListview.setMode(PullToRefreshListView.MODE_BOTH);

		pullToRefreshListview.setAdapter(myAdapter);
		pullToRefreshListview.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public boolean onRefresh(int curMode) {
				if (curMode == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {
					myAdapter.pageNo = 1;
					queryMyAsk(myAdapter.pageNo, myAdapter,
							pullToRefreshListview);
				} else if (curMode == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {
					myAdapter.pageNo++;
					queryMyAsk(myAdapter.pageNo, myAdapter,
							pullToRefreshListview);
				}
				return false;
			}
		});
		pullToRefreshListview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getActivity(),
								AskDetailActivity.class);
						intent.putExtra(BundleKeys.QUESTION_BEAN,
								myAdapter.list.get(position));
						startActivity(intent);
					}
				});

		queryMyAsk(myAdapter.pageNo, myAdapter, pullToRefreshListview);
		tadapter.mListViews.add(view);
		tadapter.notifyDataSetChanged();
		viewPager.setCurrentItem(0);
	}

	/**
	 * 查询全部
	 * 
	 * @param pageNo
	 * @param adapter
	 * @param pullToRefreshListview
	 */
	private void queryAllAsk(final int pageNo, final ListAdapter adapter,
			final PullToRefreshListView pullToRefreshListview) {

		sendRequestWithNoDialog(RequestPool.requestQannAns(pageNo),
				new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						Log.e(TAG, "result:" + result + " \n pageNo:" + pageNo);
						JSONObject js = JSON.parseObject(result);
						List<AskObj> items = JSON.parseArray(
								js.getJSONObject("page").getString("result"),
								AskObj.class);

						if (pageNo == 1) {
							adapter.list.clear();
							pullToRefreshListview
									.setMode(PullToRefreshListView.MODE_BOTH);
						}
						if (items.size() > 0) {
							adapter.list.addAll(items);
						}

						if (items.size() < 8) {
							pullToRefreshListview
									.setMode(PullToRefreshListView.MODE_PULL_UP_TO_REFRESH);
						}
						adapter.notifyDataSetChanged();
						pullToRefreshListview.onRefreshComplete();
					}

					@Override
					public void onError(String code, String msg) {
						pullToRefreshListview.onRefreshComplete();
					}
				});

	}

	/**
	 * 查询我发表的
	 * 
	 * @param pageNo
	 * @param adapter
	 * @param pullToRefreshListview
	 */
	private void queryMyAsk(final int pageNo, final ListAdapter adapter,
			final PullToRefreshListView pullToRefreshListview) {
		sendRequestWithNoDialog(RequestPool.requestMQannAns(pageNo),
				new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						Log.e(TAG, "result:" + result);
						JSONObject js = JSON.parseObject(result);
						List<AskObj> items = JSON.parseArray(
								js.getJSONObject("page").getString("result"),
								AskObj.class);

						if (pageNo == 1) {
							if (items.size() == 0) {
								tv_mempty.setVisibility(View.VISIBLE);
							}else{
								tv_mempty.setVisibility(View.GONE);
							}
							adapter.list.clear();
						}
						if (items.size() > 0) {
							adapter.list.addAll(items);
						}
						adapter.notifyDataSetChanged();
						pullToRefreshListview.onRefreshComplete();
					}

					@Override
					public void onError(String code, String msg) {
						pullToRefreshListview.onRefreshComplete();
					}
				});

	}

	/**
	 * 列表适配器
	 * 
	 * @author check_000
	 *
	 */
	class ListAdapter extends BaseAdapter {
		ArrayList<AskObj> list;
		private Context mContext;
		int pageNo;

		public ListAdapter(Context mContext) {
			this.list = new ArrayList<AskObj>();
			this.mContext = mContext;
			pageNo = 1;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.ask_list_item, parent, false);
			}
			ImageView iv_head = BaseViewHolder.get(convertView, R.id.iv_head);
			TextView tv_name = BaseViewHolder.get(convertView, R.id.tv_name);
			TextView tv_title = BaseViewHolder.get(convertView, R.id.tv_title);
			TextView tv_status = BaseViewHolder
					.get(convertView, R.id.tv_status);
			TextView tv_content = BaseViewHolder.get(convertView,
					R.id.tv_content);
			TextView tv_ask_date = BaseViewHolder.get(convertView,
					R.id.tv_ask_date);
			TextView tv_ask_location = BaseViewHolder.get(convertView,
					R.id.tv_ask_location);

			if (list.size() >= position) {
				AskObj obj = list.get(position);
				ImageLoader.getInstance().displayImage(obj.photoUrl, iv_head);
				tv_name.setText(obj.userName);
				tv_title.setText(obj.title);
				tv_status.setText(obj.status);// 需处理
				tv_content.setText(obj.content);
				tv_ask_date.setText(obj.happenTime);
				tv_ask_location.setText(obj.location);
			}

			return convertView;
		}
	}

	// @Override
	// public boolean onRefresh(int curMode) {
	//
	// }

	// tab adapter
	class TabAdapter extends PagerAdapter {

		List<View> mListViews;

		public TabAdapter() {
			this.mListViews = new ArrayList<View>();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	int MODE = 1;

	/**
	 * 页面切换，更新标题
	 * 
	 * @author check_000
	 *
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageSelected(int postion) {
			if (postion == 0) {
				rb_all.setChecked(true);
				rb_my.setChecked(false);
			} else if (1 == postion) {
				rb_all.setChecked(false);
				rb_my.setChecked(true);
			}
		}

	}

}
