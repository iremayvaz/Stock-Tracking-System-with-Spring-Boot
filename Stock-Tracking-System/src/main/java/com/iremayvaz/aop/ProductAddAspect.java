package com.iremayvaz.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductAddAspect {

    @Pointcut("execution(public * com.iremayvaz..*Service.add*(..))")
    public void productAdd() {}

    @Before("productAdd()")
    public void beforeAdd(JoinPoint jp) {
        var user = currentUser();
        var args = jp.getArgs();
        String name = read(args, "getProductName");
        String sku  = read(args, "getBarcode");
        String qty  = read(args, "getStockQuantity");

        log.info("➕ [PRE-ADD] user={} method={} name={} sku={} qty={}",
                user, jp.getSignature().toShortString(), name, sku, qty);
    }

    @AfterReturning(pointcut = "productAdd()", returning = "result")
    public void afterAdd(JoinPoint jp, Object result) {
        var user = currentUser();
        Long id = readLong(result, "getId");
        String name = read(result, "getProductName");

        log.info("✅ [POST-ADD] user={} method={} createdId={} name={}",
                user, jp.getSignature().toShortString(), id, name);

    }

    @AfterThrowing(pointcut = "productAdd()", throwing = "ex")
    public void onError(JoinPoint jp, Throwable ex) {
        var user = currentUser();
        log.error("❌ [ADD-ERROR] user={} method={} ex={}",
                user, jp.getSignature().toShortString(), ex.toString());
    }

    private String currentUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return a != null ? a.getName() : "anonymous";
    }

    private String read(Object target, String getter) {
        return Optional.ofNullable(invoke(target, getter)).map(Object::toString).orElse(null);
    }
    private String read(Object[] args, String getter) {
        for (Object a : args) {
            String v = read(a, getter);
            if (v != null) return v;
        }
        return null;
    }
    private Long readLong(Object target, String getter) {
        Object v = invoke(target, getter);
        return (v instanceof Number) ? ((Number) v).longValue() : null;
    }
    private Object invoke(Object target, String getter) {
        if (target == null) return null;
        try {
            Method m = target.getClass().getMethod(getter);
            return m.invoke(target);
        } catch (Exception ignore) {
            return null;
        }
    }
}
