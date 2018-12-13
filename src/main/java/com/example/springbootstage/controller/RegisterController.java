package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.system.Role;
import com.example.springbootstage.entity.system.User;
import com.example.springbootstage.service.system.UserService;
import com.google.common.collect.Sets;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private UserService userService;

    @GetMapping
    @WebLog(value = "跳转注册页面")
    public String goRegister(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "");
        return "register";
    }

    @PostMapping
    @WebLog(value = "注册")
    public String register(HttpServletRequest request, RedirectAttributes redirectAttributes, @ModelAttribute User user) {
        String userName = user.getUsername();
        User user_old = userService.findByUsername(userName);
        if (user_old != null) {
            redirectAttributes.addFlashAttribute("message", "该用户名已存在");
            return "register";
        }
        String password = user.getPassword();
        String nickname = user.getNickname();
        String salt = UUID.randomUUID().toString().replace("-", "");
        User user_current = new User();
        user_current.setPassword(password);
        user_current.setSalt(salt);
        user_current.setNickname(nickname);
        user_current.setUsername(userName);
        Set roles = Sets.newLinkedHashSet();
        Role role = new Role();
        role.setName("普通用户");
        roles.add(role);
        user_current.setRoles(roles);
        userService.save(user_current);
        return "index";
    }
}
