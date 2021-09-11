package com.alecuba16.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CountableAspect {
    Map<String, Integer> counter = new HashMap<String, Integer>();
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Countable)")
    public void executeCount(){}

    @Before("executeCount()")
    public void logMethodCall(JoinPoint joinPoint){
        String methodName=joinPoint.getSignature().getName();
        int currentCount=1;
        if(counter.containsKey(methodName)) {
            currentCount=counter.get(methodName)+1;
        }
        counter.put(methodName,currentCount);

        StringBuilder message = new StringBuilder("Method counts: ");
        counter.forEach((method,count)->{
            message.append(method+":"+count+", ");
        });
        LOGGER.info(message.toString());
    }
}
