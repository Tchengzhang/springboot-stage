package com.example.springbootstage.controller;

import com.example.springbootstage.entity.Brand;
import com.example.springbootstage.entity.Model;
import com.example.springbootstage.entity.Package;
import com.example.springbootstage.service.BrandService;
import com.example.springbootstage.service.ModelService;
import com.example.springbootstage.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private PackageService packageService;

    @GetMapping
    public String getModelList(ModelMap map) {
        map.addAttribute("modelList", modelService.getAll());
        return "modelList";
    }

    @GetMapping("/create")
    public String createForm(ModelMap map, HttpSession session) {
        Model model = new Model();
        model.setBrand(new Brand()); //此处必须为brand赋值，不然会报错
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


    @GetMapping("/package/{id}")
    public String goPackages(@PathVariable Long id, ModelMap map) {
        Model model = modelService.getById(id);
        List<Package> packageList = model.getPackageList();
        if (packageList == null) {
            packageList = new LinkedList<>();
            model.setPackageList(packageList);
        }
        map.addAttribute("model", model);
        List<Package> packages = packageService.getAll();
        if (packages == null) {
            packages = new LinkedList<>();
            map.addAttribute("packageList", packages);
        } else {
            Iterator<Package> pList = packages.iterator();
            while ((pList.hasNext())) {
                Package p = pList.next();
                for (Package pp : packageList) {
                    if (pp.getId().equals(p.getId())) {
                        pList.remove();
                    }
                }
            }
            map.addAttribute("packageList", packages);
        }

        return "modelPackage";
    }

    @PostMapping("/addPackageToModel")
    public String addPackageToModel(String[] id, String modelId) {
        Model model = modelService.getById(Long.valueOf(modelId));
        List<Package> packages = model.getPackageList();
        for (String idd : id) {
            Package p = packageService.getById(Long.valueOf(idd));
            if (!packages.contains(p)) {
                packages.add(p);
            }

        }
        model.setPackageList(packages);
        modelService.save(model);
        return "redirect:/model/package/" + Long.valueOf(modelId);
    }

    @GetMapping("/deleteFromModel")
    public String deleteFromModel(Long modelId,Long packageId) {
        Model model = modelService.getById(modelId);
        List<Package> packages = model.getPackageList();
/*        for (Package p : packages) {
            if (p.getId().equals(packageId)) {
                packages.remove(p);
            }
        }*/
        Iterator<Package> pList = packages.iterator();
        while ((pList.hasNext())) {
            Package p = pList.next();
            if (p.getId().equals(packageId)) {
                pList.remove();
            }
        }
        model.setPackageList(packages);
        modelService.save(model);
        return "redirect:/model/package/" + modelId;
    }


}
