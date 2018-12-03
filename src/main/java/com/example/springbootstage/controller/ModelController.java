package com.example.springbootstage.controller;

import com.example.springbootstage.entity.Brand;
import com.example.springbootstage.entity.Model;
import com.example.springbootstage.service.BrandService;
import com.example.springbootstage.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private BrandService brandService;

    @GetMapping
    public String getModelList(ModelMap map) {
        map.addAttribute("modelList", modelService.getAll());
        return "modelList";
    }

    @GetMapping("/create")
    public String createForm(ModelMap map, HttpSession session) {
        Model model = new Model();
        model.setBrand(new Brand());
        map.addAttribute("model", model);
        map.addAttribute("brandList", brandService.getAll());
        map.addAttribute("action", "save");
        return "modelForm";

    }

    @PostMapping({"/save", "/update"})
    public String save(@ModelAttribute Model model, HttpServletRequest request) {
        String url = request.getRequestURI();
        if (url.contains("save")) {
            model.setCreateTime(new Date());
        } else {
            model.setUpdateTime(new Date());
        }
        modelService.save(model);
        return "redirect:/model/";
    }

    @GetMapping("/update/{id}")
    public String getModelForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("model", modelService.getById(id));
        map.addAttribute("action", "update");
        map.addAttribute("brandList", brandService.getAll());
        return "modelForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteModel(@PathVariable Long id) {
        modelService.delById(id);
        return "redirect:/model/";
    }

    @GetMapping("/getBrandList")
    @ResponseBody
    public List<Brand> deleteModel() {
        return brandService.getAll();
    }


}
