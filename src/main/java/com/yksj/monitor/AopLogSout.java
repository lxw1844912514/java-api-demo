package com.yksj.monitor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

/**
 * 系统日志切面
 */
@Slf4j
@Aspect // 使用@Aspect注解声明一个切面
@Component
public class AopLogSout {

    /**
     * AOP 通知类型
     * 1. 前置通知
     * 2.后置通知
     * 3.环绕通知
     * 4.异常通知
     * 5.最终通知
     */

    /**
     * 这里我们使用注解的形式
     * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     * 切点表达式:   execution(...)
     * execution(public * *(..)) 任意的公共方法
     * execution（* set*（..）） 以set开头的所有的方法
     * execution（* com.LoggerApply.*（..））com.LoggerApply这个类里的所有的方法
     * execution（* com.annotation.*.*（..））com.annotation包下的所有的类的所有的方法
     * execution（* com.annotation..*.*（..））com.annotation包及子包下所有的类的所有的方法
     * execution(* com.annotation..*.*(String,?,Long)) com.annotation包及子包下所有的类的有三个参数，第一个参数为String类型，第二个参数为任意类型，第三个参数为Long类型的方法
     * execution(@annotation(com.lingyejun.annotation.Lingyejun))
     */
//    @Pointcut("@annotation(com.space.aspect.anno.SysLog)")
//    public void logPointCut() {}

    /**
     * 环绕通知 @Around， 当然也可以使用 @Before (前置通知)  @After (后置通知)
     * @param joinPoint
     * @return
     * @throws Throwable
     */
//    @Around("execution( * com.yksj.monitor.service..*.*(..))")
    @Around("execution( * com.yksj.monitor.service..*.*(..))")
    public Object printLogTimeOfService(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("正在执行:类名:{}, 方法名：{}", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long excTimes = endTime - startTime;

        log.info("开始时间: {}, 结束时间: {}", startTime,endTime);

        if (excTimes > 3000) {
            log.error("-当前执行耗时: {}ms", excTimes);
        } else if (excTimes > 10) {
            log.warn("--当前执行耗时: {}ms", excTimes);
        } else {
            log.info("---当前执行耗时: {}ms", excTimes);
        }

        return result;
    }
}
