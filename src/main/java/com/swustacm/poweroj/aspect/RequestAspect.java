package com.swustacm.poweroj.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RequestAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestAspect.class);

    /**
     * 定义sysadmin.controller包下的所有请求为切入点
     */
    @Pointcut("execution(*  *..*.*.controller..*.*(..))")
    public void LogRecord() {

    }

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("LogRecord()")
    public void doBefore(JoinPoint joinPoint)  {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取执行的函数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        if(attributes == null){
            return;
        }
        HttpServletRequest request = attributes.getRequest();

        try {
            logger.info("========================================== Start ==========================================");
            logger.info("Class & Method     : {} ", joinPoint.getTarget().getClass().getName());
            logger.info("Method             : {} ", signature.getName());
            logger.info("请求URL             : {} ", request.getRequestURI());
            logger.info("HTTP Method        : {} ", request.getMethod());
            for (int i= 0; i<joinPoint.getArgs().length;i++) {
                if(joinPoint.getArgs()[i] instanceof ServletResponse){
                    joinPoint.getArgs()[i] = null;
                }
                if(joinPoint.getArgs()[i] instanceof ServletRequest){
                    joinPoint.getArgs()[i] = null;
                }
            }
            logger.info("Web请求参数          : {} ", new Gson().toJson(joinPoint.getArgs()));

        } catch (Exception e) {
            logger.info("Gson序列化异常        :{} ", e.getMessage());
        }

    }

    /**
     * 在切点之后织入
     * @throws Throwable
     */
    @After("LogRecord()")
    public void doAfter() throws Throwable {
        logger.info("=========================================== End ===========================================");
        // 每个请求之间空一行
        logger.info("");
    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("LogRecord()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        try {
            // 打印出参
            logger.info("返回参数       : {}", new Gson().toJson(result));
            // 执行耗时
            logger.info("执行耗时       : {} ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            logger.info("返回参数解析出错,无法解析文件流");
        }
        return result;
    }
}
