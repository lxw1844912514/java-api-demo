package com.yksj.monitor.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;

@Configuration
@EnableAsync
@Slf4j
public class MyAsyncTask {

    @Async
    public void pulishAsyncMsg(){
        try {
            Thread.sleep(5000);
            log.warn("开始异步任务: "+ LocalDateTime.now());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
