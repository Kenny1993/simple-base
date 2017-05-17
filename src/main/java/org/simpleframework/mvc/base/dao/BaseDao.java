package org.simpleframework.mvc.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Base Dao 接口，增删改查
 * Created by Why on 2017/3/21.
 */
public interface BaseDao<T extends Serializable> {
    void add(Map<String, Object> fieldMap);

    void addWithUUID(Map<String, Object> fieldMap);

    void delete(Serializable id);

    void update(Map<String, Object> fieldMap);

    T findOne(Serializable id);

    List<T> findList();

    void deleteAll();

    void deleteBatch(Serializable[] ids);

    long findTotalRows();
}
