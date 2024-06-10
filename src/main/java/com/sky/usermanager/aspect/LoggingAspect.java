package com.sky.usermanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Component handling application-wide logging.
 * In order to evade code pollution and tangling, no other logging is implemented for now - this would definitely need to
 * be extended, so this class only server demonstration purposes.
 */
@Aspect
@Component
public class LoggingAspect extends AspectConfig {

    @Before("forAnyPackage()")
    public void before(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        getLogger().info(String.format("Entering %s", methodName));
    }

    @AfterReturning(pointcut = "forAnyPackage()")
    public void afterReturning(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        getLogger().info(String.format("Returning from %s", methodName));
    }

    @AfterThrowing(pointcut = "forAnyPackage()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().toShortString();
        getLogger().severe(String.format("Exception thrown in %s, %s", methodName, exception.getMessage()));
    }
}
