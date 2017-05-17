package org.simpleframework.mvc.base.exception;

/**
 * 业务逻辑异常
 * Created by Why on 2017/3/21.
 */
public class ServiceException extends RuntimeException{
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
