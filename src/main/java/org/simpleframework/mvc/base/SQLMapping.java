package org.simpleframework.mvc.base;

import org.simpleframework.mvc.base.helper.EntityHelper;
import org.simpleframework.mvc.base.helper.MySqlHelper;
import org.simpleframework.util.CollectionUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * Map 实体映射 SQL
 * Created by Administrator on 2017/4/18.
 */
public final class SQLMapping {
    /**
     * 创建实体映射 SQL 助手对象
     */
    public static MySqlHelper use(Class<? extends Serializable> entityClass) {
        return new MySqlHelper(entityClass);
    }

    /**
     * 创建实体映射 SQL 助手对象
     */
    public static MySqlHelper use(Class<? extends Serializable> entityClass, String alias) {
        return new MySqlHelper(entityClass, alias);
    }

    /**
     * 生成实体映射 insert SQL
     */
    public static String insert(Class<? extends Serializable> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            return insert(entityClass);
        } else {
            String[] fieldNames = fieldMap.keySet().toArray(new String[]{});
            return use(entityClass).insert(fieldNames).createSQL();
        }
    }

    /**
     * 生成实体映射 insert SQL
     */
    public static String insert(Class<? extends Serializable> entityClass) {
        return use(entityClass).insert().createSQL();
    }

    /**
     * 生成实体映射 update SQL
     */
    public static String update(Class<? extends Serializable> entityClass) {
        return use(entityClass).update().createSQL();
    }

    /**
     * 生成实体映射 update SQL
     */
    public static String update(Class<? extends Serializable> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            return update(entityClass);
        } else {
            String idFieldName = EntityHelper.getIdFieldName(entityClass);
            if (fieldMap.containsKey(idFieldName)) {
                fieldMap.remove(idFieldName);
                String[] fieldNames = fieldMap.keySet().toArray(new String[]{});
                MySqlHelper mySqlHelper = use(entityClass);
                return mySqlHelper.update(fieldNames).where(mySqlHelper.condition(idFieldName, SQLConditionType.EQ)).createSQL();
            } else {
                String[] fieldNames = fieldMap.keySet().toArray(new String[]{});
                return use(entityClass).update(fieldNames).createSQL();
            }
        }
    }

    /**
     * 生成实体映射 delete SQL
     */
    public static String deleteOne(Class<? extends Serializable> entityClass) {
        MySqlHelper mySqlHelper = use(entityClass);
        return mySqlHelper.delete().where(mySqlHelper.condition(EntityHelper.getIdFieldName(entityClass), SQLConditionType.EQ)).createSQL();
    }

    /**
     * 生成实体映射 delete SQL
     */
    public static String deleteAll(Class<? extends Serializable> entityClass) {
        return use(entityClass).delete().createSQL();
    }

    /**
     * 生成实体映射 delete SQL
     */
    public static String deleteBatch(Class<? extends Serializable> entityClass, int quantity) {
        MySqlHelper mySqlHelper = use(entityClass);
        return mySqlHelper.delete().where(mySqlHelper.condition(EntityHelper.getIdFieldName(entityClass), SQLConditionType.IN, quantity)).createSQL();
    }

    /**
     * 生成实体映射 select SQL
     */
    public static String selectOne(Class<? extends Serializable> entityClass) {
        MySqlHelper mySqlHelper = use(entityClass);
        return mySqlHelper.select()
                .where(mySqlHelper.and(mySqlHelper.condition(EntityHelper.getIdFieldName(entityClass), SQLConditionType.EQ))).createSQL();
    }

    public static String selectCount(Class<? extends Serializable> entityClass) {
        MySqlHelper mySqlHelper = use(entityClass);
        return mySqlHelper.select(mySqlHelper.count(EntityHelper.getIdFieldName(entityClass))).createSQL();
    }

    /**
     * 生成实体映射 select SQL
     */
    public static String selectList(Class<? extends Serializable> entityClass) {
        MySqlHelper mySqlHelper = use(entityClass);
        return mySqlHelper.select().createSQL();
    }

    /**
     * 生成实体映射 select SQL
     */
    public static String selectPageList(Class<? extends Serializable> entityClass) {
        MySqlHelper mySqlHelper = use(entityClass);
        return mySqlHelper.select().limit().createSQL();
    }
}

