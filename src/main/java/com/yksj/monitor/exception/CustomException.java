package com.yksj.monitor.exception;

/**
 * 自定义异常：
 * 目的：统一处理异常信息，便于解耦，可以在拦截器、控制层、业务层去使用
 */
public class CustomException extends RuntimeException {
    public CustomException(String errorMsg) {
        super(errorMsg);
    }
}
