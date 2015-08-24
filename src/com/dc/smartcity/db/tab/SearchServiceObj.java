package com.dc.smartcity.db.tab;

import com.android.dcone.ut.orm.annotation.Column;
import com.android.dcone.ut.orm.annotation.Table;

/**
 * 搜索
 * Created by vincent on 2015/8/24.
 */
@Table(name = SearchServiceObj.TABLE_NAME)
public class SearchServiceObj extends EntityBase {

    public static final String TABLE_NAME = "search_service_history";

    public static final String FIELD_SERVICE_ID = "service_id";
    public static final String FIELD_SERVICE_NAME = "service_name";
    public static final String FIELD_COLUMN_ID = "column_id";
    public static final String FIELD_COLUMN_NAME = "column_name";
    public static final String FIELD_SITE_ID = "site_id";
    public static final String FIELD_SERVICE_URL = "service_url";
    public static final String FIELD_SERVICE_PIC_URL = "service_picUrl";
    public static final String FIELD_IS_RECOMMEND = "is_recommend";
    public static final String FIELD_LEVEL = "level";


    @Column(column = FIELD_SERVICE_ID)
    public String serviceId;
    @Column(column = FIELD_SERVICE_NAME)
    public String serviceName;
    @Column(column = FIELD_COLUMN_ID)
    public String columnId;
    @Column(column = FIELD_COLUMN_NAME)
    public String columnName;
    @Column(column = FIELD_SITE_ID)
    public String siteId;
    @Column(column = FIELD_SERVICE_URL)
    public String serviceUrl;
    @Column(column = FIELD_SERVICE_PIC_URL)
    public String servicePicUrl;
    @Column(column = FIELD_IS_RECOMMEND)
    public String isRecommend;
    @Column(column = FIELD_LEVEL)
    public String level;

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServicePicUrl(String servicePicUrl) {
        this.servicePicUrl = servicePicUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
