//package com.dc.smartcity.fragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.android.dcone.ut.view.annotation.ViewInject;
//import com.dc.smartcity.R;
//import com.dc.smartcity.activity.AskDetailActivity;
//import com.dc.smartcity.activity.CordovaWebwiewActivity;
//import com.dc.smartcity.base.BaseFragment;
//import com.dc.smartcity.bean.AskObj;
//import com.dc.smartcity.bean.more.ServiceItem;
//import com.dc.smartcity.bean.more.ServiceList;
//import com.dc.smartcity.dialog.DialogConfig;
//import com.dc.smartcity.fragment.ServiceListFragment.ListAdapter;
//import com.dc.smartcity.litenet.RequestPool;
//import com.dc.smartcity.litenet.interf.RequestProxy;
//import com.dc.smartcity.net.ImageLoader;
//import com.dc.smartcity.util.BundleKeys;
//import com.dc.smartcity.view.gridview.BaseViewHolder;
//import com.dc.smartcity.view.pullrefresh.PullToRefreshListView;
//import com.dc.smartcity.view.pullrefresh.PullToRefreshBase.OnRefreshListener;
//
///**
// * 问答列表
// * 
// * @author szsm_dyj
// *
// */
//public class QuestanswerFragment extends BaseFragment implements OnRefreshListener{
//
//	@ViewInject(R.id.pullToRefreshListview)
//	private PullToRefreshListView pullToRefreshListview;
//
//	@Override
//	protected int setContentView() {
//
//		return R.layout.fragment_service_list;
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle bundle) {
//		view = super.onCreateView(inflater, container, bundle);
//		initData();
//		return view;
//	}
//
//	private void queryAsk(final int pageNo) {
//		sendRequestWithNoDialog(RequestPool.requestQannAns(pageNo),
//				new RequestProxy() {
//
//					@Override
//					public void onSuccess(String msg, String result) {
//						Log.e(TAG, "result:" + result);
//						JSONObject js = JSON.parseObject(result);
//						List<AskObj> items = JSON.parseArray(
//								js.getJSONObject("page").getString("result"),
//								AskObj.class);
//
//						if (pageNo == 1) {
//							adapter.list.clear();
//						}
//						if (items.size() > 0) {
//							adapter.list.addAll(items);
//						}
//						adapter.notifyDataSetChanged();
//						pullToRefreshListview.onRefreshComplete();
//					}
//
//					@Override
//					public void onError(String code, String msg) {
//						pullToRefreshListview.onRefreshComplete();
//					}
//				});
//	}
//	
//	ListAdapter adapter;
//	/**
//	 * 初始化设置
//	 */
//	private void initData() {
//		pullToRefreshListview.setMode(PullToRefreshListView.MODE_BOTH);
//		// lv_activity_center.setOnRefreshListener(this);
//		adapter = new ListAdapter(getActivity());
//
//		pullToRefreshListview.setAdapter(adapter);
//		pullToRefreshListview.setOnRefreshListener(this);
//		pullToRefreshListview
//				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view,
//							int position, long id) {
//						Intent intent = new Intent(getActivity(),
//								AskDetailActivity.class);
//						intent.putExtra(BundleKeys.QUESTION_BEAN,
//								adapter.list.get(position));
//						startActivity(intent);
//					}
//				});
//	}
//	
//	class ListAdapter extends BaseAdapter {
//		ArrayList<AskObj> list;
//		private Context mContext;
//
//		public ListAdapter(Context mContext) {
//			this.list = new ArrayList<AskObj>();
//			this.mContext = mContext;
//		}
//
//		@Override
//		public int getCount() {
//			return list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return list.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			if (convertView == null) {
//				convertView = LayoutInflater.from(mContext).inflate(
//						R.layout.ask_list_item, parent, false);
//			}
//			ImageView iv_head = BaseViewHolder.get(convertView, R.id.iv_head);
//			TextView tv_name = BaseViewHolder.get(convertView, R.id.tv_name);
//			TextView tv_title = BaseViewHolder.get(convertView, R.id.tv_title);
//			TextView tv_status = BaseViewHolder
//					.get(convertView, R.id.tv_status);
//			TextView tv_content = BaseViewHolder.get(convertView,
//					R.id.tv_content);
//			TextView tv_ask_date = BaseViewHolder.get(convertView,
//					R.id.tv_ask_date);
//			TextView tv_ask_location = BaseViewHolder.get(convertView,
//					R.id.tv_ask_location);
//
//			if (list.size() >= position) {
//				AskObj obj = list.get(position);
//				ImageLoader.getInstance().displayImage(obj.photoUrl, iv_head);
//				tv_name.setText(obj.userName);
//				tv_title.setText(obj.title);
//				tv_status.setText(obj.status);// 需处理
//				tv_content.setText(obj.content);
//				tv_ask_date.setText(obj.happenTime);
//				tv_ask_location.setText(obj.location);
//			}
//
//			return convertView;
//		}
//	}
//	
//}
