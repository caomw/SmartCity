package com.dc.smartcity.db.tab;

import com.android.dcone.ut.orm.annotation.Column;
import com.android.dcone.ut.orm.annotation.Table;

/**
 * 搜索历史
 */
@Table(name = ServiceHistory.TABLE_NAME)
public class ServiceHistory extends EntityBase {

    public static final String TABLE_NAME = "service_history";

    public static final String FIELD_NAME = "name";
    public static final String FIELD_URL = "url";
    public static final String FIELD_IMAGE_URL = "image_url";


    @Column(column = FIELD_NAME)
    public String name;
    @Column(column = FIELD_URL)
    public String url;
    @Column(column = FIELD_IMAGE_URL)
    public String imageUrl;
}