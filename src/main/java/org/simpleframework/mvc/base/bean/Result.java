package org.simpleframework.mvc.base.bean;

import java.io.Serializable;

/**
 * JSON 结果封装
 * Created by Why on 2017/3/21.
 */
public class Result implements Serializable {
    private int code;
    private Object data;
    private String tip;

    public Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String tip) {
        this.code = code;
        this.tip = tip;
    }

    public Result(int code, Object data, String tip) {
        this.code = code;
        this.data = data;
        this.tip = tip;
    }

    public int getcode() {
        return code;
    }

    public void setcode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
