package com.example.springbootstage.controller;

import com.example.springbootstage.entity.SysRole;
import com.example.springbootstage.entity.UserInfo;
import com.example.springbootstage.service.UserInfoService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping({"/", "/index"})
    public String index() {
        return "/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {
        System.out.println("HomeController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> " + exception;
                System.out.println("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "index";
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "403";
    }

    @GetMapping("/register")
    public String goRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserInfo userInfo) {
        String userName = userInfo.getUsername();
        String password = userInfo.getPassword();
        String salt = UUID.randomUUID().toString().replace("-", "");
        ByteSource byteSource = ByteSource.Util.bytes(userName + salt);
        Object obj = new SimpleHash("MD5", password, byteSource, 2);
        userInfo.setPassword(obj.toString());
        userInfo.setSalt(salt);
        userInfo.setState((byte) 1);
        List<SysRole> roles = new LinkedList<>();
        SysRole role = new SysRole();
        role.setId(2);
        roles.add(role);
        userInfo.setRoleList(roles);
        userInfoService.save(userInfo);
        return "login";
    }
}