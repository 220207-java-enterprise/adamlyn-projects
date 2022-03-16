package com.revature.spring.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    @Before("within(com.revature.spring.controllers..*)")
    public void logMethodStart(JoinPoint jp){
        String methodSig = extractMethodSignature(jp);
        String methodArgs = Arrays.toString(jp.getArgs());
        logger.info("{} invoked at {} with the provided arguments: {}", methodSig, LocalDateTime.now(), methodArgs);
    }

    @After("within(com.revature.spring.services..*)")
    public void logMethodEnd(JoinPoint jp){
        String methodSig = extractMethodSignature(jp);
        String methodArgs = Arrays.toString(jp.getArgs());
        logger.info("{} invoked at {} with the provided arguments: {}", methodSig, LocalDateTime.now(), methodArgs);
    }

    private String extractMethodSignature(JoinPoint jp){
//        System.out.println("Target Object = " + jp.getTarget().getClass());
//        System.out.println("Method Signature = " +jp.getSignature().getName());
        return jp.getTarget().getClass().toString() + "#" + jp.getSignature().getName();
    }

}
