package com.example.springbootstage.aspect;


import com.example.springbootstage.annotation.RequestLimit;
import com.example.springbootstage.utils.IpUtil;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Log
public class RequestLimitAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Before("@annotation(limit)")
    public void requestLimit(JoinPoint joinPoint, RequestLimit limit){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = IpUtil.getUserIP(request);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = method.getDeclaringClass().getName();
        String name = method.getName();
        String params = Arrays.toString(method.getParameters());
        String ipKey = String .format("%s#%s#%s",className,name,params);
        int hashCode = Math.abs(ipKey.hashCode());
        String key = String.format("%s_%d",ip,hashCode);

        Long count = redisTemplate.opsForValue().increment(key,1);
        if(count ==1){
            redisTemplate.expire(key,limit.time(),TimeUnit.SECONDS);
        }
        if(count>limit.count()){
            log.info("用户IP["+ip+"]访问超过了限定的次数["+limit.count()+"]");
            throw new RuntimeException("超出访问的次数限制");
        }

    }
}
