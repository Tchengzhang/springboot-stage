package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.work.Brand;
import com.example.springbootstage.excel.ExcelUtil;
import com.example.springbootstage.service.work.BrandService;
import com.example.springbootstage.utils.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    @WebLog(value = "跳转品牌列表页")
    @RequiresPermissions("work_user:view")
    public String getBrandList(ModelMap map) {
        map.addAttribute("list", brandService.getAll());
        return "work/brand/list";
    }

    @GetMapping("/add")
    @WebLog(value = "新增")
    @RequiresPermissions("work_user:create")
    public String createForm(ModelMap map) {
        map.addAttribute("entity", new Brand());

        return "work/brand/form";

    }

    @PostMapping(value = "/save")
    @WebLog(value = "保存品牌信息")
    public String save(@ModelAttribute Brand brand, HttpServletRequest request) {
        brandService.save(brand);
        return "redirect:/brand/";
    }

    @GetMapping("/edit/{id}")
    @WebLog(value = "跳转修改品牌页")
    @RequiresPermissions("work_user:edit")
    public String getBrandForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("entity", brandService.getById(id));
        return "work/brand/form";
    }

    @PostMapping("/delete")
    @WebLog(value = "删除品牌")
    @RequiresPermissions("work_user:delete")
    public String deleteBrand(Long id) {
        brandService.delById(id);
        return "redirect:/brand/";
    }


    /**
     * 导出 Excel（一个 sheet）
     */
    @GetMapping(value = "writeExcel")
    public void writeExcel(HttpServletResponse response) {
        List<Brand> list = brandService.getAll();
        String fileName = "品牌列表" + DateUtil.format(new Date(), "yyyyMMddhhmmss");
        String sheetName = "品牌列表";

        ExcelUtil.writeExcel(response, list, fileName, sheetName, new Brand());
    }


    /**
     * 读取 Excel（指定某个 sheet）
     */
    @RequestMapping(value = "readExcel", method = RequestMethod.POST)
    public String readExcel(MultipartFile excel, @RequestParam(defaultValue = "1") int sheetNo,
                            @RequestParam(defaultValue = "1") int headLineNum) {
        List<Object> list = ExcelUtil.readExcel(excel, new Brand(), sheetNo, headLineNum);
        Brand brand;
        for (Object o : list) {
            brand = (Brand) o;
            brandService.save(brand);
        }
        return "redirect:/brand/";
    }
}
