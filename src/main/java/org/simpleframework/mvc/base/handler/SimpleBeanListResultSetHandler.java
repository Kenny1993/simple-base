package org.simpleframework.mvc.base.handler;

import org.apache.commons.dbutils.ResultSetHandler;
import org.simpleframework.util.ReflectionUtil;
import org.simpleframework.util.StringUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 Bean List 结果集处理器
 * Created by Administrator on 2017/4/20.
 */
public class SimpleBeanListResultSetHandler<T extends Serializable> implements ResultSetHandler<List<T>> {
    private Class<T> entityClass;

    public SimpleBeanListResultSetHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> handle(ResultSet rs) throws SQLException {
        List<T> beanList = new ArrayList<T>();
        while (rs.next()) {
            T bean = (T) ReflectionUtil.newInstance(entityClass);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsMetaData.getColumnName(i);
                Field field = org.simpleframework.util.FieldUtil.getField(entityClass, StringUtil.underlineToCamel(columnName));
                ReflectionUtil.setField(bean, field, rs.getObject(columnName));
            }
            beanList.add(bean);
        }
        return beanList;
    }
}
