package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.example.demo.service.QuestionService.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            Object result = joinPoint.proceed(); // 원래 메서드 실행
            return result;
        } finally {
            stopWatch.stop();
            String methodName = joinPoint.getSignature().toShortString();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            log.info(methodName + " 수행 시간: " + totalTimeMillis + "ms");
        }
    }
}