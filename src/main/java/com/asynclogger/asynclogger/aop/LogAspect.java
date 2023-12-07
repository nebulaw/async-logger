package com.asynclogger.asynclogger.aop;

import com.asynclogger.asynclogger.entity.Log;
import com.asynclogger.asynclogger.service.ILogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LogAspect {

  private ILogService logService;

  @Autowired
  public LogAspect(ILogService logService) {
    this.logService = logService;
  }

  @Pointcut("@annotation(com.asynclogger.asynclogger.annotation.Loggable)")
  public void loggableMethod() {}

  @Before("loggableMethod()")
  public void loggableBeforeAdvice(JoinPoint joinPoint) {
    // TODO: implement some logic
    MethodSignature sig = (MethodSignature) joinPoint.getSignature();
    System.out.printf("Before execution of %s::%s\n",
        sig.getDeclaringType().getSimpleName(), sig.getName());
  }

  @Around("loggableMethod()")
  public Object loggableAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    StopWatch sw = new StopWatch();
    MethodSignature sig = (MethodSignature) joinPoint.getSignature();
    String className = sig.getDeclaringType().getSimpleName();
    String methodName = sig.getName();
    ObjectMapper mapper = new ObjectMapper();
    String args = mapper.writeValueAsString(joinPoint.getArgs());

    sw.start();
    try {
      return joinPoint.proceed();
    } finally {
      sw.stop();
      Double executionTime = sw.getTotalTimeSeconds();
      System.out.printf("%s:%s executed in %-6.6f\n",
          className, methodName, executionTime);
      System.out.println("Saving log to the database...");
      logService.saveLog(new Log(methodName, className, args, executionTime));
    }

  }

  @After("loggableMethod()")
  public void loggableAfterAdvice(JoinPoint joinPoint) {
    // TODO: implement some logic
    MethodSignature sig = (MethodSignature) joinPoint.getSignature();
    System.out.printf("After execution of %s::%s\n",
        sig.getDeclaringType().getSimpleName(), sig.getName());
  }

}
