package com.sky.usermanager.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Default component controlling monitoring.
 * Currently, only logging of execution time is supported for demonstration purposes.
 * <p>
 * Note: Extending AspectConfig is currently not used, but it is expected that once the monitoring would be extended,
 * some configuration properties might be needed.
 */
@Aspect
@Component
public class MonitoringAspect extends AspectConfig {

    @Bean
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor(false);
    }

    @Bean
    public Advisor performanceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.sky.usermanager.aspect.AspectConfig.forAnyPackage()");
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
    }
}
