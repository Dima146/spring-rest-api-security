package com.company.rest_security.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.company.rest_security.controller..*)")
    public void controllerMethods() {
    }

    @Before("controllerMethods()")
    public void logBeforeExecutionTime(JoinPoint joinPoint) {
        LOGGER.debug("Executing method: " + joinPoint.getSignature());
    }

    @AfterReturning(value = "controllerMethods()", returning = "object")
    public void logAfterReturningValue(JoinPoint joinPoint, Object object) {
        LOGGER.debug("Method " + joinPoint.getSignature() +
                " has successfully been executed. Value: [" + object + "]"
                + " has been returned");
    }

    @AfterThrowing(value = "controllerMethods()", throwing = "exception")
    public void logAfterThrowingException(JoinPoint jp, Exception exception) {
        LOGGER.error("Method " + jp.getSignature() + " throws " + exception);
    }
}