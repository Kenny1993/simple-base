package org.simpleframework.mvc.base;

/**
 * SQL 关联方式常量
 */
public interface SQLJoinType {
    String INNER_JOIN = "inner join";
    String LEFT_JOIN = "left join";
    String RIGHT_JOIN = "right join";
    String FULL_JOIN = "full join";
}