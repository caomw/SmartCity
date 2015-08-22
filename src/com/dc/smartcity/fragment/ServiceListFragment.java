package com.dc.smartcity.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.db.tab.ServiceHistory;
import com.dc.smartcity.view.gridview.BaseViewHolder;
import com.dc.smartcity.view.pullrefresh.PullToRefreshListView;

/**
 * 服务超市
 * Created by vincent on 2015/8/9.
 */
public class ServiceListFragment extends BaseFragment {

    @ViewInject(R.id.pullToRefreshListview)
    private PullToRefreshListView pullToRefreshListview;

    @Override
    protected int setContentView() {
        return R.layout.fragment_service_list;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        view = super.onCreateView(inflater, container, bundle);
        initView();
        return view;
    }

    private void initView() {
        pullToRefreshListview.setMode(PullToRefreshListView.MODE_NONE);
//        lv_activity_center.setOnRefreshListener(this);
        ArrayList<ServiceHistory> list = new ArrayList<ServiceHistory>();
        for (int i = 0; i < 25; i++) {
            list.add(new ServiceHistory());
        }
        ListAdapter adapter = new ListAdapter(getActivity(), list);
        pullToRefreshListview.setAdapter(adapter);
    }


    class ListAdapter extends BaseAdapter {
        private ArrayList<ServiceHistory> list = new ArrayList<ServiceHistory>();
        private Context mContext;

        public ListAdapter(Context mContext, ArrayList<ServiceHistory> list) {
            this.list = list;
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
                        R.layout.service_list_item, parent, false);
            }
            TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
            ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
//            iv.setBackgroundResource();

//            tv.setText();
            return convertView;
        }
    }
}
