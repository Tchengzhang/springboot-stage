package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.Brand;
import com.example.springbootstage.entity.Model;
import com.example.springbootstage.entity.Package;
import com.example.springbootstage.entity.excel.ModelInfo;
import com.example.springbootstage.excel.ExcelUtil;
import com.example.springbootstage.service.BrandService;
import com.example.springbootstage.service.ModelService;
import com.example.springbootstage.service.PackageService;
import com.example.springbootstage.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String getModelList(ModelMap map) {
        map.addAttribute("modelList", modelService.getAll());
        return "modelList";
    }

    @GetMapping("/create")
    @WebLog(value = "跳转机型页")
    public String createForm(ModelMap map, HttpSession session) {
        Model model = new Model();
        model.setBrand(new Brand()); //此处必须为brand赋值，不然会报错
        map.addAttribute("model", model);
        map.addAttribute("brandList", brandService.getAll());
        map.addAttribute("action", "save");
        return "modelForm";

    }

    @PostMapping({"/save", "/update"})
    @WebLog(value = "插入或修改机型信息")
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
    @WebLog(value = "跳转修改机型页")
    public String getModelForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("model", modelService.getById(id));
        map.addAttribute("action", "update");
        map.addAttribute("brandList", brandService.getAll());
        return "modelForm";
    }

    @GetMapping("/delete/{id}")
    @WebLog(value = "删除机型")
    public String deleteModel(@PathVariable Long id) {
        modelService.delById(id);
        return "redirect:/model/";
    }


    @GetMapping("/package/{id}")
    @WebLog(value = "进入套餐绑定页面")
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
    @WebLog(value = "添加绑定套餐")
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
    @WebLog(value = "删除绑定套餐")
    public String deleteFromModel(Long modelId, Long packageId) {
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
            modelInfo.setStatus(model.getStatus());
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
            model.setStatus(modelInfo.getStatus());
            model.setType(modelInfo.getType());
            Brand brand = brandService.getByName(modelInfo.getBrand());
            model.setBrand(brand);
            modelService.save(model);
        }
        return "redirect:/model/";
    }


}
