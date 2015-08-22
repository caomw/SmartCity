package com.dc.smartcity.view.gridview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.smartcity.R;
import com.dc.smartcity.bean.DCMenuItem;
import com.dc.smartcity.net.ImageLoader;

public class IconWithTextGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<DCMenuItem> list;

//    public String[] img_text = {"转账", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票",
//            "当面付", "更多"};
//    public int[] imgs = {R.drawable.app_transfer, R.drawable.app_fund,
//            R.drawable.app_phonecharge, R.drawable.app_creditcard,
//            R.drawable.app_movie, R.drawable.app_lottery,
//            R.drawable.app_facepay, R.drawable.more};

    public IconWithTextGridAdapter(Context mContext,List<DCMenuItem> list) {
        super();
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
    	if(null == list){
    		return 0;
    	}
        return list.size();
    }

    @Override
    public Object getItem(int position) {
    	if(null == list || list.size() < position){
    		return null;
    	}
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
                    R.layout.grid_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        ImageLoader.getInstance().displayImage(list.get(position).serviceUrl, iv);

        tv.setText(list.get(position).serviceName);
        return convertView;
    }

}
