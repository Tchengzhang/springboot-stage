package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.work.Store;
import com.example.springbootstage.excel.ExcelUtil;
import com.example.springbootstage.service.work.ModelService;
import com.example.springbootstage.service.work.PackageService;
import com.example.springbootstage.service.work.StoreService;
import com.example.springbootstage.utils.DateUtil;
import com.google.common.collect.Sets;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private PackageService packageService;

    @GetMapping
    @WebLog(value = "跳转门店列表页")
    @RequiresPermissions("work_user:view")
    public String getStoreList(ModelMap map) {
        map.addAttribute("list", storeService.getAll());
        return "work/store/list";
    }

    @GetMapping("/add")
    @WebLog(value = "跳转门店页")
    @RequiresPermissions("work_user:create")
    public String createForm(ModelMap map) {
        Store store = new Store();
        map.addAttribute("entity", store);
        map.addAttribute("packages", packageService.getAll());
        return "work/store/form";

    }

    @PostMapping("/save")
    @WebLog(value = "插入或修改门店信息")
    public String save(@Valid @ModelAttribute("entity") Store store, HttpServletRequest request) {
        storeService.save(store);
        return "redirect:/store/";
    }

    @GetMapping("/edit/{id}")
    @WebLog(value = "跳转修改门店页")
    @RequiresPermissions("work_user:edit")
    public String getStoreForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("entity", storeService.getById(id));
        map.addAttribute("packages", packageService.getAll());
        return "work/store/form";
    }

    @PostMapping("/delete")
    @WebLog(value = "删除门店")
    @RequiresPermissions("work_user:delete")
    public String deleteStore(Long id) {
        storeService.delById(id);
        return "redirect:/store/";
    }

    /**
     * 导出 Excel（一个 sheet）
     */
    @GetMapping(value = "writeExcel")
    public void writeExcel(HttpServletResponse response) {
        List<Store> list = storeService.getAll();
        String fileName = "门店列表" + DateUtil.format(new Date(), "yyyyMMddhhmmss");
        String sheetName = "门店列表";

        ExcelUtil.writeExcel(response, list, fileName, sheetName, new Store());
    }


    /**
     * 读取 Excel（指定某个 sheet）
     */
    @RequestMapping(value = "readExcel", method = RequestMethod.POST)
    public String readExcel(MultipartFile excel, @RequestParam(defaultValue = "1") int sheetNo,
                            @RequestParam(defaultValue = "1") int headLineNum) {
        List<Object> list = ExcelUtil.readExcel(excel, new Store(), sheetNo, headLineNum);
        Store store;
        for (Object o : Objects.requireNonNull(list)) {
            store = (Store) o;
            storeService.save(store);
        }
        return "redirect:/store/";
    }
}
