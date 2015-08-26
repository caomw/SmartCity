package com.dc.smartcity.db.dao;

import com.android.dcone.ut.DbUtils;
import com.android.dcone.ut.exception.DbException;
import com.dc.smartcity.db.tab.ServiceHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索服务--历史记录相关操作
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
     * 更新数据
     */
    public void saveService(ServiceHistory obj) throws DbException {
        List<ServiceHistory> list = mDbUtils.findAll(ServiceHistory.class);
        if (null == list || list.size() == 0) {
            mDbUtils.save(obj);
        } else {
            boolean isSave = false;
            for (ServiceHistory history : list) {
                if (history.serviceName.equals(obj.serviceName)) {
                    isSave = true;
                    break;
                }
            }
            if (isSave) {
                mDbUtils.update(obj);
            } else {
                mDbUtils.save(obj);
            }
        }
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
     * 获取指定条数的数据
     *
     * @throws DbException
     */
    public List<ServiceHistory> getAllByNum(int num) throws DbException {
        List<ServiceHistory> list = mDbUtils.findAll(ServiceHistory.class);
        if (null != list && list.size() >= num) {
            return list.subList(0, num - 1);
        }
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
        List<ServiceHistory> list = new ArrayList<ServiceHistory>();
        for (ServiceHistory obj : l) {
            if (obj.serviceName.contains(key)) {
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
