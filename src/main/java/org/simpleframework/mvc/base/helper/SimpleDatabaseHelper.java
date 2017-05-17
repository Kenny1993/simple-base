package org.simpleframework.mvc.base.helper;

import org.apache.commons.dbutils.ResultSetHandler;
import org.simpleframework.mvc.helper.DatabaseHelper;
import org.simpleframework.mvc.base.handler.SimpleBeanListResultSetHandler;
import org.simpleframework.mvc.base.handler.SimpleBeanResultSetHandler;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 使用自定义结果集处理器
 * Created by Administrator on 2017/4/20.
 */
public final class SimpleDatabaseHelper {
    /**
     * 查询实体列表
     */
    public static <T extends Serializable> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        return DatabaseHelper.queryEntityList(entityClass, new SimpleBeanListResultSetHandler<T>(entityClass), sql, params);
    }

    /**
     * 查询实体
     */
    public static <T extends Serializable> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        return DatabaseHelper.queryEntity(entityClass, new SimpleBeanResultSetHandler<T>(entityClass), sql, params);
    }

    /**
     * 查询实体列表
     */
    public static <T extends Serializable> List<T> queryEntityList(Class<T> entityClass, ResultSetHandler<List<T>> handler, String sql, Object... params) {
        return DatabaseHelper.queryEntityList(entityClass, handler, sql, params);
    }

    /**
     * 记录数查询
     */
    public static long queryCount(String sql, Object... params) {
        return DatabaseHelper.queryCount(sql, params);
    }

    /**
     * 查询实体
     */
    public static <T extends Serializable> T queryEntity(Class<T> entityClass, ResultSetHandler<T> handler, String sql, Object... params) {
        return DatabaseHelper.queryEntity(entityClass, handler, sql, params);
    }

    /**
     * 查询 Map List
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params) {
        return DatabaseHelper.queryMapList(sql, params);
    }

    /**
     * 执行查询语句
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        return DatabaseHelper.executeQuery(sql, params);
    }

    /**
     * 执行 SQL 语句（包括update、insert、delete）
     */
    public static int executeUpdate(String sql, Object... params) {
        return DatabaseHelper.executeUpdate(sql, params);
    }

    /**
     * 执行批量 SQL 语句（包括update、insert、delete）
     */
    public static int[] executeBatch(String sql, Object[][] params) {
        return DatabaseHelper.executeBatch(sql, params);
    }
}

