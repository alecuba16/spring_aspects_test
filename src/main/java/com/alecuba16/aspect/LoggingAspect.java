package com.alecuba16.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Loggable)")
    public void executeLogging(){}

    @Before("executeLogging()")
    public void logMethodCall(JoinPoint joinPoint){
        StringBuilder message = new StringBuilder("Before Method: ");
        message.append(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (null!=args && args.length>0){
            message.append(" args=[ | ");
            Arrays.asList(args).forEach(arg->{
                message.append(arg).append(" | ");
            });
            message.append("]");
        }
        LOGGER.info(message.toString());
    }

    @AfterReturning(value = "executeLogging()",returning = "returnValue")
    public void logMethodCall(JoinPoint joinPoint,Object returnValue){
        StringBuilder message2 = new StringBuilder("After Returning Method: ");
        message2.append(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (null!=args && args.length>0){
            message2.append(" args=[ | ");
            Arrays.asList(args).forEach(arg->{
                message2.append(arg).append(" | ");
            });
            message2.append("]");
        }
        if(returnValue instanceof Collection){
            message2.append(", returning: ").append(((Collection) returnValue).size()).append(" instance(s)");
        }else{
            message2.append(", returning: ").append(returnValue.toString());
        }
        LOGGER.info(message2.toString());
    }

    @Around(value = "executeLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime= System.currentTimeMillis();
        Object returnValue = joinPoint.proceed(); // Can expose throwable
        long totalTime = System.currentTimeMillis()-startTime;
        StringBuilder message3 = new StringBuilder("Around with throwable Method: ");
        message3.append(joinPoint.getSignature().getName());
        message3.append(" totalTime: ").append(totalTime).append("ms");

        Object[] args2 = joinPoint.getArgs();
        if (null!=args2 && args2.length>0){
            message3.append(" args2=[ | ");
            Arrays.asList(args2).forEach(arg->{
                message3.append(arg).append(" | ");
            });
            message3.append("]");
        }
        if(returnValue instanceof Collection){
            message3.append(", returning: ").append(((Collection) returnValue).size()).append(" instance(s)");
        }else{
            message3.append(", returning: ").append(returnValue.toString());
        }
        LOGGER.info(message3.toString());
        return returnValue;
    }

}
