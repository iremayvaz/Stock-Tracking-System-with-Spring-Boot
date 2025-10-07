package com.iremayvaz.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import org.aspectj.lang.reflect.MethodSignature;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *) || within(com.iremayvaz..*Service*)")
    public void serviceLayer() {}

    @Around("serviceLayer()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        var sig   = (MethodSignature) pjp.getSignature();
        var clazz = sig.getDeclaringType().getSimpleName();
        var method= sig.getName();

        long t0 = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();
            long ms = System.currentTimeMillis() - t0;
            log.info("✅ {}.{} executed in {} ms", clazz, method, ms);
            return result;
        } catch (Throwable ex) {
            long ms = System.currentTimeMillis() - t0;
            log.error("💥 {}.{} failed in {} ms: {}", clazz, method, ms, ex.toString(), ex);
            throw ex;
        }
    }
}
