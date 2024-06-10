package com.sky.usermanager.aspect;

import org.aspectj.lang.annotation.Pointcut;

import java.util.logging.Logger;

/**
 * Default layer defining common methods used by AOP components
 */
public abstract class AspectConfig {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.sky.usermanager.controller.*.*(..))")
    public void forControllerPackage() {
    }

    @Pointcut("execution(* com.sky.usermanager.service.*.*(..))")
    public void forServicePackage() {
    }

    @Pointcut("execution(* com.sky.usermanager.dao.*.*(..))")
    public void forDaoPackage() {
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    public void forAnyPackage() {
    }

    protected Logger getLogger() {
        return logger;
    }
}
