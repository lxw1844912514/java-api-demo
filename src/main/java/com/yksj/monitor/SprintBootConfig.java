package com.yksj.monitor;

import com.yksj.monitor.entity.Stu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration //为了说明当前类为配置类，加上这个注解后，会被容器扫描到
/**
 * @Bean
 * @Controller
 * @Service
 * @Repository
 * @Component
 * 这些组件注解也都能使用，根据场景以及类的业务去使用和定义即可
 */
public class SprintBootConfig implements WebMvcConfigurer {
    @Bean //将Stu 放入sprintboot 容器内
    public Stu stu() {
        // return new Stu(1,"lxw",18);
        return new Stu();
    }

    //允许跨域
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 对所有路径应用规则
                        .allowedOrigins("localhost", "192.168.1.247") // 允许任何域
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的方法
                        .allowedHeaders("*") // 允许的头
                        .allowCredentials(true); // 是否允许凭据
            }
        };
    }
}
