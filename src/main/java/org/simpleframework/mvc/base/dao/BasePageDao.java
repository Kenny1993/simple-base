package org.simpleframework.mvc.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 带分页的 Base Dao
 * Created by Why on 2017/3/21.
 */
public interface BasePageDao<T extends Serializable> extends BaseDao<T> {
    long findTotalRows(Map<String, Object> fieldMap);

    List<T> findPage(int pageIndex, int pageRows, Map<String, Object> fieldMap);
}
