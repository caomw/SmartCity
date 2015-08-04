package com.dc.smartcity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.smartcity.R;
public abstract class AsyncListAdapter<T, V> extends BaseAdapter {

	public int pageNo = 1;
	public List<T> mList;
	
	/**
	 * 下拉刷新标签
	 */
	public boolean refreshFlag = false;
	/**
	 * 加载更多标签
	 */
	public boolean loadMoreFlag = false;

	protected Context context;

	protected LayoutInflater inflater;

	private int layout;

	/**
	 * 列表为空
	 */
	public boolean isEmpty = false;

	/**
	 * 列表没有更多
	 */
	public boolean noMore = false;

	private int emptyString = R.string.listEmpty;
	private int emptyIco = R.drawable.search_empty;

	public int PAGE_SIZE = 15;

	protected int position;

	/**
	 * 列表ItemView
	 */
	public AsyncListAdapter(Context context, int layout) {
		super();
		this.context = context;
		mList = new ArrayList<T>();
		this.layout = layout;
		this.isEmpty = false;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		int size = mList.size();
		if (size == 0 && isEmpty) {
			return 1;
		}
		return mList.size();
	}

	@Override
	public T getItem(int position) {
		if (position < mList.size()) {
			return mList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int item) {
		return item;
	}

	// protected abstract void setViewHolder(V holder,View v);

	public abstract V getViewHolder();

	protected abstract void setContent(V holder, T record, int position);

	public abstract void bindView(V holder, View v);

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		this.position = position;
		View view = null;
		V holder = null;
		int size = mList.size();
		if (size == 0 && isEmpty) {
			view = inflater.inflate(R.layout.list_empty, null);
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			int height = wm.getDefaultDisplay().getHeight()/3*2;
			LayoutParams para = new LayoutParams(LayoutParams.MATCH_PARENT, height);
			view.setLayoutParams(para);
			((TextView) view.findViewById(R.id.tv_text)).setText(emptyString);
			((ImageView) view.findViewById(R.id.ivEmpty)).setImageResource(emptyIco);
			return view;
		}

		T record = getItem(position);
		if (v == null || v.getTag() == null) {
			v = inflater.inflate(layout, null);
			holder = getViewHolder();
			bindView(holder, v);
			v.setTag(holder);
		} else {
			holder = (V) v.getTag();
		}

		view = v;
		setContent(holder, record, position);
		
		return view;
	}

	public void setEmptyString(int emptyString) {
		this.emptyString = emptyString;
	}
	
	public void setEmptyIco(int emptyIco){
		this.emptyIco = emptyIco;
	}

}
