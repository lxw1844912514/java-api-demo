package com.yksj.monitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

/**
 * 1.表明当前项目为springBoot 工程，这是一个启动类，也是应用程序的入口类
 * 2.启动类需要放在根包路径之下，因为他会默认扫描controller 和 service以及mapper 等相关组件
 * 扫描完了之后，会放到spring/springboot 的容器中
 */
@SpringBootApplication
@MapperScan("com.yksj.monitor.mapper")
//@ComponentScan("com.yksj.monitor.mapper")
public class MonitorApplication {

    public static void main(String[] args) {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(timeZone);
        SpringApplication.run(MonitorApplication.class, args);
    }

    void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        //TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
