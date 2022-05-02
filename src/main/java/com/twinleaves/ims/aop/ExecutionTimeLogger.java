package com.twinleaves.ims.aop;

import com.twinleaves.ims.annotation.LogExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class ExecutionTimeLogger {

    private static final Logger log = LoggerFactory.getLogger(ExecutionTimeLogger.class);

    @Around("execution(* *(..)) && @annotation(com.twinleaves.ims.annotation.LogExecutionTime)")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        LogExecutionTime logExecutionTime = method.getAnnotation(LogExecutionTime.class);
        String taskDesc = logExecutionTime.value();
        String className = MethodSignature.class.cast(point.getSignature()).getDeclaringTypeName();
        String methodName = method.getName();
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        long endTime = System.currentTimeMillis() - start;
        log.info("Time taken for execution of {} {} [{} ExecutionTime: {} sec]", className, methodName, taskDesc, (endTime / 1000.0));
        return result;
    }
}