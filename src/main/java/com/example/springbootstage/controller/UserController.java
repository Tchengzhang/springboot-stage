package com.example.springbootstage.controller;


import com.example.springbootstage.entity.system.User;
import com.example.springbootstage.service.system.RoleService;
import com.example.springbootstage.service.system.UserService;
import com.example.springbootstage.utils.Message;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by WangHong on 2018/3/19.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @RequiresPermissions("system_user:view")
    @GetMapping(value = "list", name = "用户列表")
    public String list(Model model){
        model.addAttribute("list", userService.findByState(0));
        return "system/user/list";
    }

    @RequiresPermissions("system_user:create")
    @GetMapping(value = "add", name = "新增")
    public String add(Model model){
        model.addAttribute("entity", new User());
        model.addAttribute("roles", roleService.findByState(0));
        return "system/user/form";
    }

    @RequiresPermissions("system_user:edit")
    @GetMapping(value = "edit/{id}", name = "编辑")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            model.addAttribute("entity", userService.findById(id));
            model.addAttribute("roles", roleService.findByState(0));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "system/user/form";
    }

    @PostMapping(value = "save", name = "保存")
    public String save(@Valid@ModelAttribute("entity") User entity, RedirectAttributes redirectAttributes){
        try {
            userService.save(entity);
            redirectAttributes.addFlashAttribute("message", Message.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", Message.ERROR);
        }
        return "redirect:/user/list";
    }

    @RequiresPermissions("system_user:delete")
    @PostMapping(value = "delete", name = "删除")
    public String delete(Long id, RedirectAttributes redirectAttributes){
        try {
            userService.changeState(id);
            redirectAttributes.addFlashAttribute("message", Message.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", Message.ERROR);
        }
        return "redirect:/user/list";
    }

    @RequiresPermissions("system_user:edit")
    @PostMapping(value = "change_password", name = "重置用户密码")
    public String changePassword(Long id, RedirectAttributes redirectAttributes){
        try {
            userService.changePassword(id);
            redirectAttributes.addFlashAttribute("message", Message.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", Message.ERROR);
        }
        return "redirect:/user/list";
    }

    @PostMapping(value = "ajax_check_username", name = "异步校验账号重复")
    public @ResponseBody
    Boolean  ajaxCheckUsername(Long id, String username){
        User user = userService.findByUsernameAndIdNot(username, id);
        if(user == null || user.getState() != 0){
            return true;
        }
        return false;
    }
}
