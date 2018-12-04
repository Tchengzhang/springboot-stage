package com.example.springbootstage.aspect;


import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.RequestLog;
import com.example.springbootstage.entity.UserInfo;
import com.example.springbootstage.service.RequestLogService;
import com.example.springbootstage.utils.IpUtil;
import com.example.springbootstage.utils.JsonUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class WebLogAspect {

    private static final String CLZ_NAME = WebLogAspect.class.getSimpleName();

    @Autowired
    private RequestLogService requestLogService;

    @Pointcut("@annotation(com.example.springbootstage.annotation.WebLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveWebLog(point, time);
        return result;
    }

    private void saveWebLog(ProceedingJoinPoint joinPoint, long time) throws IOException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        RequestLog requestLog = new RequestLog();
        WebLog webLog = method.getAnnotation(WebLog.class);
        if (webLog != null) {
            //注解上的描述
            requestLog.setOperation(webLog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        requestLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //设置IP地址
        requestLog.setIp(IpUtil.getUserIP(request));

        String httpMethod = request.getMethod();
        String params = "";
        String type = request.getContentType();
        if ("POST".equals(httpMethod) && MediaType.APPLICATION_JSON_VALUE.equals(type)) {
            params = Arrays.toString(args);
        } else {
            Map<String, String[]> paramsMap = request.getParameterMap();
            Map<String, String> paramsTmpMap = new HashMap<>();
            paramsMap.keySet().forEach((e) -> paramsTmpMap.put(e, paramsMap.get(e)[0]));
            params = JsonUtils.toJson(paramsTmpMap);
        }
        requestLog.setParams(params);
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        requestLog.setUsername(username);

        requestLog.setTime(time);
        requestLog.setCreateDate(new Date());
        //保存系统日志
        requestLogService.save(requestLog);
    }

}
