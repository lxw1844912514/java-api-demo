package com.yksj.monitor;

import com.yksj.monitor.utils.JSONResult;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义的助手类：统一异常拦截处理
 * 可以针对异常自定义去捕获去处理，返回指定的类型（json 类型）到前端
 */
@ControllerAdvice  //为controller服务的
public class GraceExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

//    @ExceptionHandler(FileSizeLimitExceededException.class) //这里拦截异常，如果不想拦截，注释掉即可
    @ResponseBody
    public JSONResult ReturnFileSizeLimit(FileSizeLimitExceededException e) {
        return JSONResult.errorMsg("文件大小不能超出" + maxFileSize);
    }
}
