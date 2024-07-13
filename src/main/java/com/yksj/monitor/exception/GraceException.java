package com.yksj.monitor.exception;

/**
 * 优雅的处理异常
 */
public class GraceException {

    public  static void display(String errMsg){
        throw new CustomException(errMsg);
    }
}
