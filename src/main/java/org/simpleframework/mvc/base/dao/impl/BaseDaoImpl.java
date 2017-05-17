package org.simpleframework.mvc.base.dao.impl;

import org.simpleframework.mvc.base.SQLMapping;
import org.simpleframework.mvc.base.dao.BaseDao;
import org.simpleframework.mvc.base.helper.EntityHelper;
import org.simpleframework.mvc.base.helper.SimpleDatabaseHelper;
import org.simpleframework.mvc.helper.UUIDHelper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Base Dao 接口实现类
 * Created by Why on 2017/3/21.
 */
public class BaseDaoImpl<T extends Serializable> implements BaseDao<T> {
    protected Class<T> entityClass;
    protected String idFieldName;

    public BaseDaoImpl() {
        entityClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        idFieldName = EntityHelper.getIdFieldName(entityClass);
    }

    public void add(Map<String, Object> fieldMap) {
        String sql = SQLMapping.insert(entityClass, fieldMap);
        int result = SimpleDatabaseHelper.executeUpdate(sql, fieldMap.values().toArray());
        if (result == 0) {
            throw new RuntimeException("dao execute add failure");
        }
    }

    public void addWithUUID(Map<String, Object> fieldMap) {
        fieldMap.put(idFieldName, UUIDHelper.getString());
        String sql = SQLMapping.insert(entityClass, fieldMap);
        int result = SimpleDatabaseHelper.executeUpdate(sql, fieldMap.values().toArray());
        if (result == 0) {
            throw new RuntimeException("dao execute add with uuid failure");
        }
    }

    public void delete(Serializable id) {
        String sql = SQLMapping.deleteOne(entityClass);
        int result = SimpleDatabaseHelper.executeUpdate(sql, id);
        if (result == 0) {
            throw new RuntimeException("dao execute delete failure");
        }
    }

    public void deleteAll() {
        String sql = SQLMapping.deleteAll(entityClass);
        int result = SimpleDatabaseHelper.executeUpdate(sql);
        if (result == 0) {
            throw new RuntimeException("dao execute delete all failure");
        }
    }

    public void deleteBatch(Serializable[] ids) {
        String sql = SQLMapping.deleteBatch(entityClass, ids.length);
        int result = SimpleDatabaseHelper.executeUpdate(sql, ids);
        if (result == 0) {
            throw new RuntimeException("dao execute delete batch failure");
        }
    }

    public void update(Map<String, Object> fieldMap) {
        Object id = fieldMap.get(idFieldName);
        String sql = SQLMapping.update(entityClass, fieldMap);
        ArrayList<Object> params = new ArrayList<Object>();
        params.addAll(fieldMap.values());
        params.add(id);
        int result = SimpleDatabaseHelper.executeUpdate(sql, params.toArray());
        if (result == 0) {
            throw new RuntimeException("dao execute update failure");
        }
    }

    public T findOne(Serializable id) {
        String sql = SQLMapping.selectOne(entityClass);
        return (T) SimpleDatabaseHelper.queryEntity(entityClass, sql, id);
    }

    public List<T> findList() {
        String sql = SQLMapping.selectList(entityClass);
        return (List<T>) SimpleDatabaseHelper.queryEntityList(entityClass, sql);
    }

    public long findTotalRows() {
        String sql = SQLMapping.selectCount(entityClass);
        return SimpleDatabaseHelper.queryCount(sql);
    }
}
