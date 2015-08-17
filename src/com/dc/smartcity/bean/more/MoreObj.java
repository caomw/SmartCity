package com.dc.smartcity.bean.more;

import java.util.List;
/**
 * 更多
 * @author szsm_dyj
 *
 */
public class MoreObj {

	public List<ColumnItem> columnList;
	public void setColumnList(List<ColumnItem> columnList) {
		this.columnList = columnList;
	}
	public void setServiceItem(List<ColumnItem> serviceItem) {
		ServiceItem = serviceItem;
	}
	public List<ColumnItem> ServiceItem;
}
