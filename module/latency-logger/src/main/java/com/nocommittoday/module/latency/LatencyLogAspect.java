package com.nocommittoday.module.latency;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Aspect
public class LatencyLogAspect {

    private static final Logger log = LoggerFactory.getLogger(LatencyLogAspect.class);

    @Order(1)
    @Around("@within(com.nocommittoday.module.latency.LatencyLog)")
    public Object doLog(final ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("Execution executionTime of [{}] >> {}ms", joinPoint.getSignature(), executionTime);
        }
    }
}
