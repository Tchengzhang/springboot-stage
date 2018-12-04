package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.Brand;
import com.example.springbootstage.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    @WebLog(value = "跳转品牌列表页")
    public String getBrandList(ModelMap map) {
        map.addAttribute("brandList", brandService.getAll());
        return "brandList";
    }

    @GetMapping("/create")
    @WebLog(value = "跳转品牌页")
    public String createForm(ModelMap map) {
        map.addAttribute("brand", new Brand());
        map.addAttribute("action", "save");
        return "brandForm";

    }

    @PostMapping({"/save", "/update"})
    @WebLog(value = "插入或修改品牌信息")
    public String save(@ModelAttribute Brand brand, HttpServletRequest request) {
        String url = request.getRequestURI();
        if (url.contains("save")) {
            brand.setCreateTime(new Date());
        } else {
            brand.setUpdateTime(new Date());
        }
        brandService.save(brand);
        return "redirect:/brand/";
    }

    @GetMapping("/update/{id}")
    @WebLog(value = "跳转修改品牌页")
    public String getBrandForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("brand", brandService.getById(id));
        map.addAttribute("action", "update");
        return "brandForm";
    }

    @GetMapping("/delete/{id}")
    @WebLog(value = "删除品牌")
    public String deleteBrand(@PathVariable Long id) {
        brandService.delById(id);
        return "redirect:/brand/";
    }

}
