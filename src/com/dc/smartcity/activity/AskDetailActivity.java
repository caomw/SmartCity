package com.dc.smartcity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.bean.AskObj;
import com.dc.smartcity.bean.askwg.WeiguanBean;
import com.dc.smartcity.dialog.CommitDialog;
import com.dc.smartcity.dialog.CommitDialog.OnSendMessage;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.net.ImageLoader;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.util.DateTools;
import com.dc.smartcity.util.Utils;
import com.dc.smartcity.view.gridview.BaseViewHolder;
import com.dc.smartcity.view.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.dc.smartcity.view.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 问答详情 Created by vincent on 2015/8/9.
 */
public class AskDetailActivity extends BaseActionBarActivity implements OnRefreshListener, OnSendMessage {

	// 问答详情
	AskObj					obj;

	@ViewInject(R.id.iv_head)
	ImageView				iv_head;

	@ViewInject(R.id.tv_name)
	TextView				tv_name;

	@ViewInject(R.id.tv_title)
	TextView				tv_title;

	@ViewInject(R.id.tv_status)
	TextView				tv_status;

	@ViewInject(R.id.tv_content)
	TextView				tv_content;

	@ViewInject(R.id.tv_ask_date)
	TextView				tv_ask_date;

	@ViewInject(R.id.tv_ask_location)
	TextView				tv_ask_location;

	@ViewInject(R.id.pullToRefreshListView)
	PullToRefreshListView	pullToRefreshListView;

	// 适配器
	ListAdapter				adapter;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_ask_detail);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		obj = (AskObj) getIntent().getSerializableExtra(BundleKeys.QUESTION_BEAN);
		initActionBar();
		updateUIDatas();
		queryComment();
	}

	private void queryComment() {
		sendRequestWithDialog(RequestPool.getCommentList(obj.observeId, adapter.pageNo), new DialogConfig.Builder().build(), new RequestProxy() {

			@Override
			public void onSuccess(String msg, String result) {
				JSONObject js = JSON.parseObject(result);
				boolean hasMore = js.getBooleanValue("hasMore");
				if (!hasMore) {
					pullToRefreshListView.setMode(PullToRefreshListView.MODE_PULL_UP_TO_REFRESH);
				}
				List<WeiguanBean> items = JSON.parseArray(js.getString("moCommentList"), WeiguanBean.class);

				
				if (adapter.pageNo == 1) {
					adapter.list.clear();
					pullToRefreshListView.setMode(PullToRefreshListView.MODE_BOTH);
				}
				if (items.size() > 0) {
					adapter.list.addAll(items);
				}

				if (items.size() == 0) {
					pullToRefreshListView.setMode(PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH);
				}
				adapter.notifyDataSetChanged();
				pullToRefreshListView.onRefreshComplete();
				
			}

			@Override
			public void onError(String code, String msg) {
				pullToRefreshListView.onRefreshComplete();
			}
		});
	}

	CommitDialog	dlgCommit;

	private void initActionBar() {

		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle("详情");
		iv_actionbar_right.setImageResource(R.drawable.speak);
		iv_actionbar_right.setVisibility(View.VISIBLE);
		iv_actionbar_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showCommentDlg();
			}
		});
	}

	private void showCommentDlg() {
		if (!Utils.isLogon()) {
			startActivity(new Intent(this, LoginActivity.class));
		}
		else if (!Utils.isRealName()) {
			startActivity(new Intent(this, AuthAct.class));
		}
		else {

			if (null == dlgCommit) {
				dlgCommit = new CommitDialog(this, this);
			}
			if (!dlgCommit.isShowing()) {
				dlgCommit.show();
			}
		}

	}

	private void updateUIDatas() {
		tv_name.setText(obj.userName);
		tv_title.setText(obj.title);
		tv_status.setText(getString(R.string.tv_comment_num, obj.commentNum));// 需处理
		tv_content.setText(obj.content);
		tv_ask_date.setText(obj.happenTime);
		tv_ask_location.setText(obj.location);
		ImageLoader.getInstance().displayImage(obj.photoUrl, iv_head, R.drawable.head);

		adapter = new ListAdapter(this);
		pullToRefreshListView.setMode(PullToRefreshListView.MODE_BOTH);
		pullToRefreshListView.setAdapter(adapter);
		pullToRefreshListView.setOnRefreshListener(this);

	}

	/**
	 * 评论列表适配器
	 *
	 * @author check_000
	 */
	class ListAdapter extends BaseAdapter {
		ArrayList<WeiguanBean>	list;
		private Context			mContext;
		int						pageNo;

		public ListAdapter(Context mContext) {
			this.list = new ArrayList<WeiguanBean>();
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
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ask_detail, parent, false);
			}
			// ImageView ivHead = BaseViewHolder.get(convertView, R.id.iv_head);
			// TextView tvName = BaseViewHolder.get(convertView, R.id.tv_name);
			// TextView tvComment = BaseViewHolder.get(convertView,
			// R.id.tv_comment);
			// TextView tvTime = BaseViewHolder.get(convertView, R.id.tv_time);
			TextView tv_reply_title = BaseViewHolder.get(convertView, R.id.tv_reply_title);
			TextView tv_reply_content = BaseViewHolder.get(convertView, R.id.tv_reply_content);
			TextView tv_reply_time = BaseViewHolder.get(convertView, R.id.tv_reply_time);

			if (list.size() >= position) {
				WeiguanBean obj = list.get(position);
				// ImageLoader.getInstance()
				// .displayImage(obj.userPhotoUrl, ivHead);
				if ("01".equals(obj.commentId)) {
					tv_reply_title.setText(obj.userName + "(相关委办局回复)");
				}
				else {
					tv_reply_title.setText(obj.userName);
				}
				tv_reply_content.setText(obj.citizenComment);
				tv_reply_time.setText(DateTools.formatDateTime(obj.createTime));
			}

			return convertView;
		}
	}

	@Override
	public boolean onRefresh(int curMode) {
		switch (curMode) {
		case PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH:// refresh
			adapter.pageNo = 1;
			queryComment();
			break;
		case PullToRefreshListView.MODE_PULL_UP_TO_REFRESH:// more
			adapter.pageNo++;
			queryComment();
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void sendComment(String comment) {
		sendRequestWithDialog(RequestPool.commentWG(comment, obj.observeId), new DialogConfig.Builder().build(), new RequestProxy() {

			@Override
			public void onSuccess(String msg, String result) {
				Utils.showToast(msg, AskDetailActivity.this);
				adapter.pageNo = 1;
				queryComment();
			}
		});
	}

}
