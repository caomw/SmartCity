package com.dc.smartcity.bean.more;

import java.util.List;

/**
 * 更多
 *
 * @author szsm_dyj
 */
public class MoreObj {

    public List<ColumnItem> columnList;
    public List<ServiceItem> serviceList;

    public void setColumnList(List<ColumnItem> columnList) {
        this.columnList = columnList;
    }

    public void setServiceList(List<ServiceItem> serviceList) {
        this.serviceList = serviceList;
    }
}
