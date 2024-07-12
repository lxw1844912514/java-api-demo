package com.yksj.monitor.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component //把本配置放入到sprintboot 容器中，使其被扫描到
@ConfigurationProperties(prefix = "user")  //映射
@PropertySource(value = "classpath:MyConfig.properties",encoding = "utf-8")
@Data
public class MyConfig {
    private String username;
    private Integer age;
    private String sex;

}
