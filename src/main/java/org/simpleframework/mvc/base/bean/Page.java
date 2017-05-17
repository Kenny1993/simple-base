package org.simpleframework.mvc.base.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 封装分页
 * Created by Why on 2017/3/21.
 */
public class Page<T> implements Serializable{
    // 当前页数
    private int pageIndex;
    // 每页结果行数
    private int pageRows;
    // 结果总行数
    private long totalRows;
    // 结果数据
    private List<T> data;
    // 条件
    private Map<String,Object> fieldMap;

    public Page(int pageIndex, int pageRows) {
        this.pageIndex = pageIndex;
        this.pageRows = pageRows;
    }

    public Page(int pageIndex, int pageRows,Map<String,Object> fieldMap) {
        this(pageIndex,pageRows);
        this.fieldMap = fieldMap;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }
}
