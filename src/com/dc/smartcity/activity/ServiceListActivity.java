package com.dc.smartcity.activity;

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
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.bean.ScenceItem;
import com.dc.smartcity.bean.more.ServiceItem;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.net.ImageLoader;
import com.dc.smartcity.util.BundleKeys;
import com.dc.smartcity.view.gridview.BaseViewHolder;
import com.dc.smartcity.view.pullrefresh.PullToRefreshListView;

import java.util.List;

/**
 * 服务列表
 * Created by vincent on 2015/8/7.
 */
public class ServiceListActivity extends BaseActionBarActivity {

    @ViewInject(R.id.pullToRefreshListview)
    private PullToRefreshListView pullToRefreshListview;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_service_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        ScenceItem scenceItem = (ScenceItem) bundle.getSerializable(BundleKeys.SCENCEITEM);
        initActionBar(scenceItem.getColumnName());

        sendRequestWithDialog(RequestPool.getServiceList(scenceItem.getColumnId()), new DialogConfig.Builder().build(), new RequestProxy() {
            @Override
            public void onSuccess(String msg, String result) {
                final List<ServiceItem> list = JSON.parseArray(result, ServiceItem.class);
                ListAdapter adapter = new ListAdapter(mContext, list);
                pullToRefreshListview.setAdapter(adapter);
                pullToRefreshListview.setMode(PullToRefreshListView.MODE_NONE);
                pullToRefreshListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(mContext, CordovaWebwiewActivity.class);
                        intent.putExtra(BundleKeys.WEBVIEW_LOADURL, list.get(position).getServiceUrl());
                        intent.putExtra(BundleKeys.WEBVIEW_TITLE, list.get(position).getServiceName());
                        startActivity(intent);
                    }
                });
                View empty = mLayoutInflater.inflate(R.layout.view_empty, null);
                TextView tv_content = (TextView) empty.findViewById(R.id.tv_content);
                tv_content.setText("正在建设中");
                pullToRefreshListview.setEmptyView(empty);
            }
        });
    }

    private void initActionBar(String title) {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle(title);
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
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
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
