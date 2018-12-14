package com.example.springbootstage.controller;



import com.example.springbootstage.utils.UserHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;


/**
 * 登录
 * Created by WangHong on 2018/3/19.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String login() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            return "redirect:/home";
        } else {
            return "login";
        }
    }

    @PostMapping
    public String fail(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(UserHelper.getCurrentUser() != null){
            return "redirect:/home";
        }
        String message = null;
        String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (loginFailure != null) {
            switch (loginFailure){
                case "org.apache.shiro.authc.pam.UnsupportedTokenException":
                    message = "验证码错误!";//验证码错误
                    break;
                case "org.apache.shiro.authc.UnknownAccountException":
                    message = "用户名或密码错误!";//用户名或密码错误
                    break;
                case "org.apache.shiro.authc.pam.DisabledAccountException":
                    message = "此账号已被禁用!";//此账号已被禁用
                    break;
                case "org.apache.shiro.authc.ExcessiveAttemptsException":
                    message = "此账号密码输入错误已达5次被锁定，10分钟后解锁!";//此账号密码输入错误已达5次被锁定，30分钟后解锁
                    break;
                case "org.apache.shiro.authc.AuthenticationException":
                    message = "账号认证失败!";//账号认证失败
                    break;
                case "org.apache.shiro.authc.IncorrectCredentialsException":
                    message = "密码错误";//验证码错误
                    break;
            }
            redirectAttributes.addFlashAttribute("message", message);
        }
        return "redirect:/login";
    }



}
