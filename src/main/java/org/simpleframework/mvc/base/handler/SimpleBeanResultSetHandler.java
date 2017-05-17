package org.simpleframework.mvc.base.handler;

import org.apache.commons.dbutils.ResultSetHandler;
import org.simpleframework.util.FieldUtil;
import org.simpleframework.util.ReflectionUtil;
import org.simpleframework.util.StringUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 自定义 Bean 结果集处理器
 * Created by Administrator on 2017/4/20.
 */
public class SimpleBeanResultSetHandler<T extends Serializable> implements ResultSetHandler<T> {
    private Class<T> entityClass;

    public SimpleBeanResultSetHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T handle(ResultSet rs) throws SQLException {
        T bean = null;
        if(rs.next()){
            bean = (T) ReflectionUtil.newInstance(entityClass);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsMetaData.getColumnName(i);
                Field field = FieldUtil.getField(entityClass, StringUtil.underlineToCamel(columnName));
                ReflectionUtil.setField(bean, field, rs.getObject(columnName));
            }
        }
        return bean;
    }
}
