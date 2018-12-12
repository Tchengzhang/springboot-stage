package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.Package;
import com.example.springbootstage.excel.ExcelUtil;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    /**
     * 导出 Excel（一个 sheet）
     */
    @GetMapping(value = "writeExcel")
    public void writeExcel(HttpServletResponse response) {
        List<Package> list = packageService.getAll();
        String fileName = "套餐列表" + DateUtil.format(new Date(), "yyyyMMddhhmmss");
        String sheetName = "套餐列表";

        ExcelUtil.writeExcel(response, list, fileName, sheetName, new Package());
    }


    /**
     * 读取 Excel（指定某个 sheet）
     */
    @RequestMapping(value = "readExcel", method = RequestMethod.POST)
    public String readExcel(MultipartFile excel, @RequestParam(defaultValue = "1") int sheetNo,
                            @RequestParam(defaultValue = "1") int headLineNum) {
        List<Object> list = ExcelUtil.readExcel(excel, new Package(), sheetNo, headLineNum);
        Package p;
        for (Object o : Objects.requireNonNull(list)) {
            p = (Package) o;
            packageService.save(p);
        }
        return "redirect:/package/";
    }

}
