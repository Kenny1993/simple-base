package org.simpleframework.mvc.base.service.impl;

import org.simpleframework.mvc.base.dao.BaseDao;
import org.simpleframework.mvc.base.exception.ServiceException;
import org.simpleframework.mvc.base.service.BaseService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Base Service 接口实现类
 * Created by Why on 2017/3/21.
 */
public abstract class BaseServiceImpl<T extends Serializable> implements BaseService<T> {
    public abstract BaseDao<T> getBaseDao();

    public void add(Map<String, Object> fieldMap) throws ServiceException {
        getBaseDao().add(fieldMap);
    }

    public void addWithUUID(Map<String, Object> fieldMap) throws ServiceException {
        getBaseDao().addWithUUID(fieldMap);
    }

    public void delete(Serializable id) throws ServiceException {
        getBaseDao().delete(id);
    }

    public void update(Map<String, Object> fieldMap) throws ServiceException {
        getBaseDao().update(fieldMap);
    }

    public T findOne(Serializable id) throws ServiceException {
        return getBaseDao().findOne(id);
    }

    public List<T> findList() throws ServiceException {
        return getBaseDao().findList();
    }

    public void deleteAll() throws ServiceException {
        getBaseDao().deleteAll();
    }

    public void deleteBatch(Serializable[] ids) throws ServiceException {
        getBaseDao().deleteBatch(ids);
    }

    public long findTotalRows() throws ServiceException {
        return getBaseDao().findTotalRows();
    }
}
