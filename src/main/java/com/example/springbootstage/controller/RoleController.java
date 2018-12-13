package com.example.springbootstage.controller;


import com.example.springbootstage.entity.system.Permission;
import com.example.springbootstage.entity.system.Role;
import com.example.springbootstage.service.system.PermissionService;
import com.example.springbootstage.service.system.RoleService;
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
import java.util.List;

/**
 * 角色Controller
 * Created by WangHong on 2018/3/19.
 */
@Controller
@RequestMapping("/role/")
public class RoleController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;

    @RequiresPermissions("system_role:view")
    @GetMapping(value = "list", name = "角色列表")
    public String list(Model model){
        model.addAttribute("list", roleService.findByState(0));
        return "/system/role/list";
    }

    @RequiresPermissions("system_role:view")
    @GetMapping(value = "view/{id}", name = "详情")
    public String view(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        Role role = roleService.findByIdAndState(id, 0);
        if(role == null){
            redirectAttributes.addFlashAttribute("message", Message.THERE_IS_NO);
            return "redirect:/role/list";
        }
        model.addAttribute("entity", role);
        return "/system/role/view";
    }

    @RequiresPermissions("system_role:create")
    @GetMapping(value = "add", name = "新增")
    public String add(Model model){
        model.addAttribute("entity", new Role());
        return "/system/role/form";
    }

    @RequiresPermissions("system_role:edit")
    @GetMapping(value = "edit/{id}", name = "编辑")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        Role role = roleService.findOne(id);
        if(role.getState() != 0){
            redirectAttributes.addFlashAttribute("message", Message.THERE_IS_NO);
            return "redirect:/role/list";
        }
        model.addAttribute("entity", role);
        List<Permission> permissionList = permissionService.findByParentIsNull();
        model.addAttribute("permissionList", permissionList);
        return "/system/role/form";
    }

    @PostMapping(value = "save", name = "保存")
    public String save(@Valid@ModelAttribute("entity") Role role, RedirectAttributes redirectAttributes){
        try {
            roleService.save(role);
            redirectAttributes.addFlashAttribute("message", Message.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", Message.ERROR);
        }
        return "redirect:/role/list";
    }

    @RequiresPermissions("system_role:delete")
    @PostMapping(value = "delete", name = "删除")
    public String delete(Long id, RedirectAttributes redirectAttributes){
        try {
            roleService.changeStatus(id);
            redirectAttributes.addFlashAttribute("message", Message.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", Message.ERROR);
        }
        return "redirect:/role/list";
    }
}
