package org.simpleframework.mvc.base.helper;

import org.simpleframework.mvc.helper.FieldHelper;
import org.simpleframework.mvc.base.annotation.Entity;
import org.simpleframework.mvc.base.annotation.Id;
import org.simpleframework.util.ArrayUtil;
import org.simpleframework.util.FieldUtil;
import org.simpleframework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体类注解助手类
 * Created by Why on 2017/3/21.
 */
public final class EntityHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityHelper.class);

    /**
     * 获取目标类中带有 ID 注解的成员名
     */
    public static String getIdFieldName(Class<? extends Serializable> entityClass) {
        Field idField = FieldHelper.getFieldByAnnotation(entityClass, Id.class);
        if (idField == null) {
            LOGGER.error(entityClass + " must be present a id field by Id anntation");
            throw new RuntimeException(entityClass + " must be present a id field by Id anntation");
        }
        return idField.getName();
    }

    /**
     * 获取实体类注解中的数据库表名
     */
    public static String getTableName(Class<? extends Serializable> entityClass) {
        Entity entity = getAnnotationFromClass(entityClass);
        String tableName = entity.name();
        if (StringUtil.isEmpty(tableName)) {
            return entityClass.getSimpleName().toLowerCase();
        }
        return tableName;
    }

    /**
     * 获取 Class 中的 Entity 注解
     */
    private static Entity getAnnotationFromClass(Class<? extends Serializable> entityClass) {
        Entity entity = entityClass.getAnnotation(Entity.class);
        if (entity == null) {
            LOGGER.error(entityClass + "must be present by Entity anntation");
            throw new RuntimeException(entityClass + "must be present by Entity anntation");
        }
        return entity;
    }


    /**
     * 获取实体类中的所有字段名
     */
    public static String[] getAllFieldNames(Class<? extends Serializable> entityClass) {
        List<String> fieldNameList = new ArrayList<String>();
        Field[] fields = FieldUtil.getFields(entityClass);
        if(ArrayUtil.isNotEmpty(fields)){
            for (Field field:fields) {
                fieldNameList.add(StringUtil.camelToUnderline(field.getName()));
            }
        }
        return fieldNameList.toArray(new String[]{});
    }
}
