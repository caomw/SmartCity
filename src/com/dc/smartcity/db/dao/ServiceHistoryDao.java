package com.dc.smartcity.db.dao;

import com.android.dcone.ut.DbUtils;
import com.android.dcone.ut.exception.DbException;
import com.dc.smartcity.db.tab.ServiceHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2015/8/12.
 */
public class ServiceHistoryDao {

    private DbUtils mDbUtils;

    public ServiceHistoryDao(DbUtils dbUtils) {
        this.mDbUtils = dbUtils;
    }


    /**
     * 插入所有数据
     */
    public void insertServiceList(List<ServiceHistory> list) throws DbException {
        removeAll();
        mDbUtils.saveAll(list);
    }


    /**
     * 获取所有数据
     *
     * @throws DbException
     */
    public List<ServiceHistory> getAll() throws DbException {
        List<ServiceHistory> list = mDbUtils.findAll(ServiceHistory.class);
        return list;
    }

    /**
     * 通过关键字搜索
     *
     * @param key
     * @return
     * @throws DbException
     */
    public List<ServiceHistory> getListByKey(String key) throws DbException {
        List<ServiceHistory> l = mDbUtils.findAll(ServiceHistory.class);
        List<ServiceHistory> list = new ArrayList<>();
        for (ServiceHistory obj : l) {
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
            mDbUtils.deleteAll(ServiceHistory.class);
        } catch (DbException e) {
        }
    }

}