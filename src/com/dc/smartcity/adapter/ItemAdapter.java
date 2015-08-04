package com.dc.smartcity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dc.smartcity.R;
import com.dc.smartcity.bean.AdItem;
/**
 * 列表项适配器
 * @author check_000
 *
 */
public class ItemAdapter extends
		AsyncListAdapter<AdItem, ItemAdapter.ViewHolder> {

	public ItemAdapter(Context context, int layout) {
		super(context, layout);
	}

	class ViewHolder {
		TextView content;
		TextView date;
		LinearLayout listbg;
	}

	@Override
	public ViewHolder getViewHolder() {
		return new ViewHolder();
	}

	@Override
	protected void setContent(ViewHolder holder, AdItem record, int position) {
		holder.content.setText(record.getTitle());
		holder.date.setText(record.getDate());
		switch (position % 6) {
		case 0:
			holder.listbg.setBackgroundResource(R.drawable.home_box1);
			break;
		case 1:
			holder.listbg.setBackgroundResource(R.drawable.home_box2);
			break;
		case 2:
			holder.listbg.setBackgroundResource(R.drawable.home_box3);
			break;
		case 3:
			holder.listbg.setBackgroundResource(R.drawable.home_box4);
			break;
		case 4:
			holder.listbg.setBackgroundResource(R.drawable.home_box5);
			break;
		case 5:
			holder.listbg.setBackgroundResource(R.drawable.home_box6);
			break;
		default:
			holder.listbg.setBackgroundResource(R.drawable.home_box1);
			break;
		}

	}

	@Override
	public void bindView(ViewHolder holder, View v) {
		holder.content = (TextView) v.findViewById(R.id.content);
		holder.date = (TextView) v.findViewById(R.id.date);
		holder.listbg = (LinearLayout) v.findViewById(R.id.listbg);
	}
}
