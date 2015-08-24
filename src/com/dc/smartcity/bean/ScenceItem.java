package com.dc.smartcity.bean;

import java.io.Serializable;

/**
 * 固定服务
 *
 * @author szsm
 */
public class ScenceItem implements Serializable {

    /**
     * 栏目id
     */
    private String columnId;

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnPicUrl() {
        return columnPicUrl;
    }

    public void setColumnPicUrl(String columnPicUrl) {
        this.columnPicUrl = columnPicUrl;
    }

    /**
     * 场景名称
     */
    private String columnName;
    /**
     * 场景名称
     */
    private String columnPicUrl;
}
