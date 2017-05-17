package org.simpleframework.mvc.base.service;

import org.simpleframework.mvc.base.exception.ServiceException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Base Service 接口
 * Created by Why on 2017/3/21.
 */
public interface BaseService<T extends Serializable> {
    void add(Map<String, Object> fieldMap) throws ServiceException;

    void addWithUUID(Map<String, Object> fieldMap) throws ServiceException;

    void delete(Serializable id) throws ServiceException;

    void update(Map<String, Object> fieldMap) throws ServiceException;

    T findOne(Serializable id) throws ServiceException;

    List<T> findList() throws ServiceException;

    void deleteAll() throws ServiceException;

    void deleteBatch(Serializable[] ids) throws ServiceException;

    long findTotalRows() throws ServiceException;
}
