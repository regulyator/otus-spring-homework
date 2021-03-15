package ru.otus.questions.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingOutputAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingOutputAspect.class);

    @Before("@annotation(ru.otus.questions.anotation.LoggingMethod)")
    public void logBeforeOutput(JoinPoint joinPoint) {
        logger.info("Call method {} with params {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }
}
