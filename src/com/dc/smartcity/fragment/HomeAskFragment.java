package com.dc.smartcity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.activity.AskDetailActivity;
import com.dc.smartcity.activity.LoginActivity;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.bean.AskObj;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.net.ImageLoader;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.util.Utils;
import com.dc.smartcity.view.gridview.BaseViewHolder;
import com.dc.smartcity.view.pullrefresh.PullToRefreshListView;

/**
 * 有问必答
 * Created by vincent on 2015/8/3.
 */
public class HomeAskFragment extends BaseFragment {
    private String TAG = HomeAskFragment.class.getSimpleName();

    @ViewInject(R.id.pullToRefreshListview)
    private PullToRefreshListView pullToRefreshListview;

    int pageNo=1;

    public HomeAskFragment(ActionBar actionBar) {
        super(actionBar);
    }

    public HomeAskFragment() {
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_ask;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        view = super.onCreateView(inflater, container, bundle);
        initActionBar();
        initList();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ULog.error("---%s.hidden=%s", TAG, hidden);
        if (!hidden) {
            initActionBar();
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
            	if(Utils.isLogon()){
            		startActivity(new Intent(getActivity(),LoginActivity.class));
            	}else if("01".equals(Utils.user.userBase.level)){
            		Utils.showToast("需要实名认证", getActivity());
            	}
            }
        });
        tv_actionbar_right.setVisibility(View.GONE);
    }

    ListAdapter adapter;
    private void initList() {
        pullToRefreshListview.setMode(PullToRefreshListView.MODE_NONE);
//        lv_activity_center.setOnRefreshListener(this);
        adapter = new ListAdapter(getActivity());
        
        pullToRefreshListview.setAdapter(adapter);
        pullToRefreshListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), AskDetailActivity.class));
            }
        });
        
        queryAsk(pageNo);
    }


    private void queryAsk(int pageNo) {
		sendRequestWithNoDialog(RequestPool.requestQannAns(pageNo), new RequestProxy() {
			
			@Override
			public void onSuccess(String msg, String result) {
				Log.e(TAG, "result:"+result);
				JSONObject js = JSON.parseObject(result);
				List<AskObj> items= JSON.parseArray(js.getJSONObject("page").getString("result"),AskObj.class);
				if(items.size()>0){
					adapter.list.addAll(items);
					adapter.notifyDataSetChanged();
				}
			}
		});
	}


	class ListAdapter extends BaseAdapter {
        ArrayList<AskObj> list;
        private Context mContext;

        public ListAdapter(Context mContext) {
            this.list = new ArrayList<AskObj>();
            this.mContext = mContext;
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
                        R.layout.ask_list_item, parent, false);
            }
            ImageView iv_head = BaseViewHolder.get(convertView, R.id.iv_head);
            TextView tv_name = BaseViewHolder.get(convertView, R.id.tv_name);
            TextView tv_title = BaseViewHolder.get(convertView, R.id.tv_title);
            TextView tv_status = BaseViewHolder.get(convertView, R.id.tv_status);
            TextView tv_content = BaseViewHolder.get(convertView, R.id.tv_content);
            TextView tv_ask_date = BaseViewHolder.get(convertView, R.id.tv_ask_date);
            TextView tv_ask_location = BaseViewHolder.get(convertView, R.id.tv_ask_location);
            
            if(list.size()>= position){
            	AskObj obj = list.get(position);
            	ImageLoader.getInstance().displayImage(obj.photoUrl, iv_head);
            	tv_name.setText(obj.userName);
            	tv_title.setText(obj.title);
            	tv_status.setText(obj.status);//需处理
            	tv_content.setText(obj.content);
            	tv_ask_date.setText(obj.happenTime);
            	tv_ask_location.setText(obj.location);
            }
            
            return convertView;
        }
    }

}
