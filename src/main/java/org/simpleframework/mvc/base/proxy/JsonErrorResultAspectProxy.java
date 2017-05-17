package org.simpleframework.mvc.base.proxy;

import org.simpleframework.mvc.annotation.Aspect;
import org.simpleframework.mvc.annotation.Controller;
import org.simpleframework.mvc.base.annotation.JsonErrorResult;
import org.simpleframework.mvc.base.exception.ServiceException;
import org.simpleframework.mvc.base.helper.JsonResultHelper;
import org.simpleframework.mvc.proxy.Proxy;
import org.simpleframework.mvc.proxy.ProxyChain;
import org.simpleframework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * JSON error 结果切面类，配合 JsonErrorResult 注解使用，抛出业务异常时自动返回 error message
 * Created by Administrator on 2017/3/27.
 */
@Aspect(Controller.class)
public class JsonErrorResultAspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonErrorResultAspectProxy.class);

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        JsonErrorResult JsonErrorResult = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();

        if (method.isAnnotationPresent(JsonErrorResult.class)) {
            JsonErrorResult = method.getAnnotation(JsonErrorResult.class);
        }else if (cls.isAnnotationPresent(JsonErrorResult.class)) {
            JsonErrorResult = cls.getAnnotation(JsonErrorResult.class);
        }

        if (JsonErrorResult != null) {
            LOGGER.debug("json error result proxy...");
            try {
                result = proxyChain.doProxyChain();
            } catch (Throwable throwable) {
                String tip = JsonErrorResult.tip();
                if (StringUtil.isEmpty(tip)) {
                    if(throwable instanceof ServiceException) {
                        result = JsonResultHelper.error(((ServiceException)throwable).getMessage());
                        LOGGER.debug("json service exception message: " + ((ServiceException)throwable).getMessage());
                    }else{
                        throw throwable;
                    }
                } else {
                    result = JsonResultHelper.error(tip);
                    LOGGER.debug("json error message: " + throwable.getMessage());
                }
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
