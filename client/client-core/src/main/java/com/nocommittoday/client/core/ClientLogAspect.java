package com.nocommittoday.client.core;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


@Slf4j
@Aspect
public class ClientLogAspect {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Around("@within(com.nocommittoday.client.core.ClientLog)")
    public Object doLog(final ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("클라이언트 실행시간 [{}] >> {}ms", joinPoint.getSignature(), executionTime);
        }
    }
}
