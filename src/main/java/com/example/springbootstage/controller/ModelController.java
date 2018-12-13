package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.work.Brand;
import com.example.springbootstage.entity.work.Model;
import com.example.springbootstage.entity.work.Package;
import com.example.springbootstage.entity.excel.ModelInfo;
import com.example.springbootstage.excel.ExcelUtil;
import com.example.springbootstage.service.work.BrandService;
import com.example.springbootstage.service.work.ModelService;
import com.example.springbootstage.service.work.PackageService;
import com.example.springbootstage.utils.DateUtil;
import com.google.common.collect.Sets;
import net.sf.ehcache.util.SetAsList;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

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
    @WebLog(value = "跳转机型列表页")
    @RequiresPermissions("work_user:view")
    public String getModelList(ModelMap map) {
        map.addAttribute("list", modelService.getAll());
        return "work/model/list";
    }

    @GetMapping("/add")
    @WebLog(value = "跳转机型页")
    @RequiresPermissions("work_user:create")
    public String createForm(ModelMap map, HttpSession session) {
        Model model = new Model();
        model.setBrand(new Brand()); //此处必须为brand赋值，不然会报错
        model.setPackageList(Sets.newLinkedHashSet());
        map.addAttribute("entity", model);
        map.addAttribute("list", brandService.getAll());
        map.addAttribute("packages", packageService.getAll());
        return "work/model/form";

    }

    @PostMapping("/save")
    @WebLog(value = "存储机型信息")
    public String save(@Valid @ModelAttribute("entity") Model model, HttpServletRequest request) {
        modelService.save(model);
        return "redirect:/model/";
    }

    @GetMapping("/edit/{id}")
    @WebLog(value = "跳转修改机型页")
    @RequiresPermissions("work_user:edit")
    public String getModelForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("entity", modelService.getById(id));
        map.addAttribute("list", brandService.getAll());
        map.addAttribute("packages", packageService.getAll());
        return "work/model/form";
    }

    @PostMapping("/delete")
    @WebLog(value = "删除机型")
    @RequiresPermissions("work_user:delete")
    public String deleteModel(Long id) {
        modelService.delById(id);
        return "redirect:/model/";
    }


    @GetMapping("/package/{id}")
    @WebLog(value = "进入套餐绑定页面")
    public String goPackages(@PathVariable Long id, ModelMap map) {
        Model model = modelService.getById(id);
        Set<Package> packageList = model.getPackageList();
        if (packageList == null) {
            packageList = Sets.newLinkedHashSet();
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
    @WebLog(value = "添加绑定套餐")
    public String addPackageToModel(String[] id, String modelId) {
        Model model = modelService.getById(Long.valueOf(modelId));
        Set<Package> packages = model.getPackageList();
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
    @WebLog(value = "删除绑定套餐")
    public String deleteFromModel(Long modelId, Long packageId) {
        Model model = modelService.getById(modelId);
        Set<Package> packages = model.getPackageList();
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


    /**
     * 导出 Excel（一个 sheet）
     */
    @GetMapping(value = "writeExcel")
    public void writeExcel(HttpServletResponse response) {
        List<Model> list = modelService.getAll();
        List<ModelInfo> list1 = new LinkedList<>();
        ModelInfo modelInfo;
        for (Model model : list) {
            modelInfo = new ModelInfo();
            modelInfo.setId(model.getId());
            modelInfo.setBrand(model.getBrand().getName());
            modelInfo.setModelCode(model.getModelCode());
            modelInfo.setName(model.getName());
            modelInfo.setPrice(model.getPrice());
            modelInfo.setType(model.getType());
            list1.add(modelInfo);
        }
        String fileName = "机型列表" + DateUtil.format(new Date(), "yyyyMMddhhmmss");
        String sheetName = "机型列表";

        ExcelUtil.writeExcel(response, list1, fileName, sheetName, new ModelInfo());
    }


    /**
     * 读取 Excel（指定某个 sheet）
     */
    @RequestMapping(value = "readExcel", method = RequestMethod.POST)
    public String readExcel(MultipartFile excel, @RequestParam(defaultValue = "1") int sheetNo,
                            @RequestParam(defaultValue = "1") int headLineNum) {
        List<Object> list = ExcelUtil.readExcel(excel, new ModelInfo(), sheetNo, headLineNum);
        ModelInfo modelInfo;
        Model model;
        for (Object o : Objects.requireNonNull(list)) {
            modelInfo = (ModelInfo) o;
            model = new Model();
            model.setName(modelInfo.getName());
            model.setPrice(modelInfo.getPrice());
            model.setModelCode(modelInfo.getModelCode());
            model.setType(modelInfo.getType());
            Brand brand = brandService.getByName(modelInfo.getBrand());
            model.setBrand(brand);
            modelService.save(model);
        }
        return "redirect:/model/";
    }


}
