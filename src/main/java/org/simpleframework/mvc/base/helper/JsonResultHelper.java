package org.simpleframework.mvc.base.helper;

import org.simpleframework.mvc.base.ResultConstant;
import org.simpleframework.mvc.base.bean.Result;
import org.simpleframework.mvc.bean.Data;

/**
 * JSON 结果封装助手类
 * Created by Why on 2017/3/21.
 */
public final class JsonResultHelper {
    /**
     * 创建 SUCCESS RESULT
     */
    public static Data<Result> success(Object data){
        return new Data(new Result(ResultConstant.SUCCESS,data));
    }

    /**
     * 创建 SUCCESS RESULT
     */
    public static Data<Result> success(String tip){
        return new Data(new Result(ResultConstant.SUCCESS,tip));
    }

    /**
     * 创建 ERROR RESULT
     */
    public static Data<Result> error(String error){
        return new Data(new Result(ResultConstant.FAILURE,error));
    }

    /**
     * 创建 RESULT
     */
    public static Data<Result> result(int code,Object data,String tip){
        return new Data(new Result(code,data,tip));
    }
}
