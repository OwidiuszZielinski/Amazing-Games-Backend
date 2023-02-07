package com.example.amazinggamesbackend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class Logger {
    @Pointcut("execution(* com.example.amazinggamesbackend.*(..))")
    private void anyPublicMethod() {
    }

    @Around("execution(* com.example.amazinggamesbackend.app.UtilController.*(..))")
    public Object aroundController(ProceedingJoinPoint point) throws Throwable {
        Object proceed = point.proceed();
        long start = System.nanoTime();
        log.info("::Time:: = " + (System.nanoTime() - start) + " nanoSec" + " QR HAS BEEN DOWNLOADED");
        return proceed;
    }

}
