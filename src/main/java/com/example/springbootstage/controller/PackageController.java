package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.Package;
import com.example.springbootstage.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/package")
public class PackageController {

    @Autowired
    private PackageService packageService;
    
    @GetMapping
    @WebLog(value = "跳转品套餐列表页")
    public String getPackageList(ModelMap map) {
        map.addAttribute("packageList", packageService.getAll());
        return "packageList";
    }

    @GetMapping("/create")
    @WebLog(value = "跳转品套餐页")
    public String createForm(ModelMap map) {
        Package p = new Package();
        map.addAttribute("package", p);
        map.addAttribute("action", "save");
        return "packageForm";

    }

    @PostMapping({"/save", "/update"})
    @WebLog(value = "插入或修改套餐信息")
    public String save(@ModelAttribute Package p, HttpServletRequest request) {
        String url = request.getRequestURI();
        if (url.contains("save")) {
            p.setCreateTime(new Date());
        } else {
            p.setUpdateTime(new Date());
        }
        packageService.save(p);
        return "redirect:/package/";
    }

    @GetMapping("/update/{id}")
    @WebLog(value = "跳转修改套餐页")
    public String getPackageForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("package", packageService.getById(id));
        map.addAttribute("action", "update");
        return "packageForm";
    }

    @GetMapping("/delete/{id}")
    @WebLog(value = "删除套餐")
    public String deleteModel(@PathVariable Long id) {
        packageService.delById(id);
        return "redirect:/package/";
    }
    

}
