package ru.gb.market.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class StatisticsAspect {

    private Long startTime;

    HashMap<String, Long> statMap = new HashMap<>();

    @Before(value = "execution(* ru.gb.market.services.*.*(..))")
    public void beforeAdvice() {
        startTime = System.currentTimeMillis();
    }

    @After(value = "execution(* ru.gb.market.services.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        Long endTime = System.currentTimeMillis();
        Long totalTime = endTime - startTime;
        String serviceName = joinPoint.getSignature().getDeclaringTypeName();
        serviceName = serviceName.substring(serviceName.lastIndexOf(".") + 1);
        if (!statMap.containsKey(serviceName)) {
            statMap.put(serviceName, totalTime);
        } else {
            statMap.replace(serviceName, statMap.get(serviceName) + totalTime);
        }
    }

    public HashMap<String, Long> getStatList() {
        return statMap;
    }
}