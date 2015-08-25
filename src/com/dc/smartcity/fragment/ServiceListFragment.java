package com.dc.smartcity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.dc.smartcity.R;
import com.dc.smartcity.activity.CordovaWebwiewActivity;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.bean.more.ColumnItem;
import com.dc.smartcity.bean.more.ServiceItem;
import com.dc.smartcity.bean.more.ServiceList;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.net.ImageLoader;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.view.gridview.BaseViewHolder;
import com.dc.smartcity.view.pullrefresh.PullToRefreshListView;

import java.util.List;

/**
 * 服务超市
 * Created by vincent on 2015/8/9.
 */
public class ServiceListFragment extends BaseFragment {

    private ColumnItem mColumnItem = new ColumnItem();
    public List<ServiceItem> mServiceList;


    @ViewInject(R.id.pullToRefreshListview)
    private PullToRefreshListView pullToRefreshListview;


    @Override
    protected int setContentView() {
        return R.layout.fragment_service_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        view = super.onCreateView(inflater, container, bundle);
        initData();
        return view;
    }

    private void initData() {
        pullToRefreshListview.setMode(PullToRefreshListView.MODE_NONE);
//        lv_activity_center.setOnRefreshListener(this);
        ListAdapter adapter = null;
        if (null == adapter) {
            if (null != mServiceList) {
                adapter = new ListAdapter(getActivity(), mServiceList);
                pullToRefreshListview.setAdapter(adapter);
            } else {
                sendRequestWithDialog(RequestPool.GetMoreServiceItem(mColumnItem.getColumnId(), mColumnItem.getColumnName()), new DialogConfig.Builder().build(), new RequestProxy() {
                    @Override
                    public void onSuccess(String msg, String result) {
                        ServiceList list = JSON.parseObject(result, ServiceList.class);
                        mServiceList = list.serviceList;
                        ListAdapter adapter = new ListAdapter(getActivity(), mServiceList);
                        pullToRefreshListview.setAdapter(adapter);
                    }
                });
            }

            pullToRefreshListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), CordovaWebwiewActivity.class);
                    intent.putExtra(BundleKeys.WEBVIEW_LOADURL, mServiceList.get(position).getServiceUrl());
                    intent.putExtra(BundleKeys.WEBVIEW_TITLE, mServiceList.get(position).getServiceName());
                    startActivity(intent);
                }
            });
        }
    }


    public void setData(ColumnItem columnItem, List<ServiceItem> serviceList) {
        mColumnItem = columnItem;
        mServiceList = serviceList;
    }

    public void setData(ColumnItem columnItem) {
        mColumnItem = columnItem;
    }


    class ListAdapter extends BaseAdapter {
        private List<ServiceItem> list;
        private Context mContext;

        public ListAdapter(Context mContext, List<ServiceItem> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @Override
        public int getCount() {
            if (null != list)
                return list.size();
            return 0;
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.service_list_item, parent, false);
            }
            ServiceItem serviceItem = list.get(position);

            TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
            ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
            ImageLoader.getInstance().displayImage(serviceItem.getServicePicUrl(), iv);
            tv.setText(serviceItem.getServiceName());
            return convertView;
        }
    }
}
