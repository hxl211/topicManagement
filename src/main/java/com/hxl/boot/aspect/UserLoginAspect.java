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
}
