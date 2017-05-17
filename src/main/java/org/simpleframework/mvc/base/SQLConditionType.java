package org.simpleframework.mvc.base;

/**
 * SQL 条件常量
 */
public interface SQLConditionType {
    String LT = "<";
    String RT = ">";
    String EQ = "=";
    String LT_EQ = "<=";
    String RT_EQ = ">=";
    String NOT_EQ = "!=";
    String IN = "in";
    String LIKE = "like";
}