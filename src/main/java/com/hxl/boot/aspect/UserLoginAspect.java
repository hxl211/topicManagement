package com.hxl.boot.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class UserLoginAspect {

    @Before("execution(* com.hxl.boot.service..*(..))")
    public void a(JoinPoint joinPoint) throws Throwable {
        System.out.println(joinPoint.getSignature().getName());
    }

    @Around("execution(* com.hxl.boot.service.CacheFileService..*(..))")
    public  Object b(ProceedingJoinPoint joinPoint) throws Throwable {
        long l1 = System.currentTimeMillis();
        Object o = joinPoint.proceed();
        long l2 = System.currentTimeMillis();
        System.out.println(l2-l1);
        return o;
    }

}
