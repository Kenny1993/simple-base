package org.simpleframework.mvc.base.helper;

import org.simpleframework.util.ArrayUtil;
import org.simpleframework.util.FieldUtil;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体注解助手类
 * Created by Why on 2017/3/22.
 */
public final class FieldAnnotationHelper {
    /**
     * 获取属性注解映射 Map
     */
    public static Map<String,List<? extends Annotation>> getMap(Class<? extends Serializable> entityClass){
        Map<String,List<? extends Annotation>> fieldAnnotationMap = new HashMap<String, List<? extends Annotation>>();
        Field[] fields = FieldUtil.getFields(entityClass);
        if(ArrayUtil.isNotEmpty(fields)){
            for(Field field:fields){
                Annotation[] annotations = field.getDeclaredAnnotations();
                fieldAnnotationMap.put(field.getName(), Arrays.asList(annotations));
            }
        }
        return fieldAnnotationMap;
    }
}
