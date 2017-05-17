package org.simpleframework.mvc.base.service;

import org.simpleframework.mvc.base.bean.Page;
import org.simpleframework.mvc.base.exception.ServiceException;

import java.io.Serializable;
import java.util.Map;

/**
 * 带分页的 Base Service
 * Created by Why on 2017/3/21.
 */
public interface BasePageService<T extends Serializable> extends BaseService<T> {
    Page<T> findPage(int pageIndex,int pageRows,Map<String,Object> fieldMap) throws ServiceException;
}
