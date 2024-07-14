package com.yksj.monitor.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

//@Configuration // 2、标记配置类，使得springboot容器可以扫描到
//@EnableScheduling // 1、开启定时任务
@Slf4j
public class MyTask {
    //    3、添加一个任务，并且注明任务的运行表达式
//    @Scheduled(cron = "*/5 * * * * ? ")
    public void pulishMsg() {
        log.warn("开始执行任务：" + LocalDateTime.now());
    }


//  常用定时任务表达式
//  每隔5秒执行一次：*/5 * * * * ?
//  每隔1分钟执行一次：0 */1 * * * ?
// 每天23点执行一次： 0 0 23 * * ？
// 每天凌晨1点执行1次： 0 0 1 * * ？
//每月1号凌晨1点执行1次： 0 0 1 1 * ？
//每月最后一天凌晨23点执行一次：0 0 23 L * ?
//每周星期天凌晨1点执行一次：0 0 1 ？* L
// 在 23，26，29分执行一次：0 23，26，29 * * * ？

}
