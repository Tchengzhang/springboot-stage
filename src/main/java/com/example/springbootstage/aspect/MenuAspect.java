package com.example.springbootstage.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 面向切面 - 用于回显时固定菜单项
 *
 * @author WangHong
 * @create 2018/4/10
 */
@Aspect
@Component
public class MenuAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.example.springbootstage.controller..*.*(..))")
    public void menuId() {
    }

    @Before("menuId()")
    public void after() {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String menuId = request.getParameter("menuId");
        if (StringUtils.isNotBlank(menuId)) {
            request.getSession().setAttribute("menuId", menuId);
        }
    }
}
