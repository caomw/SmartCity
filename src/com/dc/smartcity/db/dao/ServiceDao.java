package com.dc.smartcity.db.dao;

import com.android.dcone.ut.DbUtils;
import com.android.dcone.ut.exception.DbException;
import com.dc.smartcity.db.tab.SearchServiceObj;
import com.dc.smartcity.db.tab.ServiceHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地保存服务
 * <p>
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
    public void insertServiceList(List<SearchServiceObj> list) throws DbException {
        removeAll();
        mDbUtils.saveAll(list);
    }


    /**
     * 更新数据
     */
    public void saveService(ServiceHistory obj) throws DbException {
        mDbUtils.saveOrUpdate(obj);
    }

    /**
     * 获取所有数据
     *
     * @throws DbException
     */
    public List<SearchServiceObj> getAll() throws DbException {
        List<SearchServiceObj> list = mDbUtils.findAll(SearchServiceObj.class);
        return list;
    }

    /**
     * 通过关键字搜索
     *
     * @param key
     * @return
     * @throws DbException
     */
    public List<SearchServiceObj> getListByKey(String key) throws DbException {
        List<SearchServiceObj> l = mDbUtils.findAll(SearchServiceObj.class);
        List<SearchServiceObj> list = new ArrayList<SearchServiceObj>();
        for (SearchServiceObj obj : l) {
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
            mDbUtils.deleteAll(SearchServiceObj.class);
        } catch (DbException e) {
        }
    }

}
