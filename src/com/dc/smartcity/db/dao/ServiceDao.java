package com.dc.smartcity.db.dao;

import com.android.dcone.ut.DbUtils;
import com.android.dcone.ut.exception.DbException;
import com.dc.smartcity.db.tab.ServiceHistory;
import com.dc.smartcity.db.tab.ServiceObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2015/8/12.
 */
public class ServiceDao {

    private DbUtils mDbUtils;

    public ServiceDao(DbUtils dbUtils) {
        this.mDbUtils = dbUtils;
    }


    /**
     * 插入所有数据
     */
    public void insertServiceList(List<ServiceObj> list) throws DbException {
        removeAll();
        mDbUtils.saveAll(list);
    }


    /**
     * 获取所有数据
     *
     * @throws DbException
     */
    public List<ServiceObj> getAll() throws DbException {
        List<ServiceObj> list = mDbUtils.findAll(ServiceObj.class);
        return list;
    }

    /**
     * 通过关键字搜索
     *
     * @param key
     * @return
     * @throws DbException
     */
    public List<ServiceObj> getListByKey(String key) throws DbException {
        List<ServiceObj> l = mDbUtils.findAll(ServiceObj.class);
        List<ServiceObj> list = new ArrayList<>();
        for (ServiceObj obj : l) {
            if (obj.name.contains(key)) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * 删除所有数据
     *
     * @throws DbException
     */
    public void removeAll() {
        try {
            mDbUtils.deleteAll(ServiceObj.class);
        } catch (DbException e) {
        }
    }

}
