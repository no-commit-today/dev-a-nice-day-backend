package com.nocommittoday.techswipe.client.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ClientLogAspect {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ClientLogAspect.class);

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Around("@within(com.nocommittoday.techswipe.client.core.ClientLog)")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("클라이언트 실행시간 [{}] >> {}ms", joinPoint.getSignature(), executionTime);
        }
    }
}
