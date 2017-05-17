package org.simpleframework.mvc.base.service.impl;

import org.simpleframework.mvc.base.bean.Page;
import org.simpleframework.mvc.base.dao.BaseDao;
import org.simpleframework.mvc.base.dao.BasePageDao;
import org.simpleframework.mvc.base.exception.ServiceException;
import org.simpleframework.mvc.base.service.BasePageService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 带分页的 Base Service 实现类
 * Created by Why on 2017/3/21.
 */
public abstract class BasePageServiceImpl<T extends Serializable> extends BaseServiceImpl<T> implements BasePageService<T> {
    public abstract BasePageDao<T> getBasePageDao();

    public Page<T> findPage(int pageIndex, int pageRows, Map<String,Object> fieldMap) throws ServiceException {
        Page<T> page = new Page<T>(pageIndex, pageRows);
        long totalRows = getBasePageDao().findTotalRows(fieldMap);
        List<T> data = getBasePageDao().findPage(pageIndex, pageRows, fieldMap);
        page.setTotalRows(totalRows);
        page.setData(data);
        return page;
    }

    public BaseDao<T> getBaseDao() {
        return getBasePageDao();
    }
}
