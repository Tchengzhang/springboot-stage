package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.work.Store;
import com.example.springbootstage.entity.work.Clerk;
import com.example.springbootstage.excel.ExcelUtil;
import com.example.springbootstage.service.work.ClerkService;
import com.example.springbootstage.service.work.StoreService;
import com.example.springbootstage.utils.DateUtil;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/clerk")
public class ClerkController {

    @Autowired
    private ClerkService clerkService;

    @Autowired
    private StoreService storeService;

    @GetMapping
    @WebLog(value = "跳转店员列表页")
    @RequiresPermissions("work_user:view")
    public String getClerkList(ModelMap map) {
        map.addAttribute("list", clerkService.getAll());
        return "work/clerk/list";
    }

    @GetMapping("/add")
    @WebLog(value = "跳转店员页")
    @RequiresPermissions("work_user:create")
    public String createForm(ModelMap map, HttpSession session) {
        Clerk clerk = new Clerk();
        clerk.setStore(new Store()); //此处必须为store赋值，不然会报错
        map.addAttribute("entity", clerk);
        map.addAttribute("list", storeService.getAll());
        return "work/clerk/form";

    }

    @PostMapping("/save")
    @WebLog(value = "插入或修改店员信息")
    public String save(@Valid @ModelAttribute Clerk clerk, HttpServletRequest request) {
        clerkService.save(clerk);
        return "redirect:/clerk/";
    }

    @GetMapping("/edit/{id}")
    @WebLog(value = "跳转修改店员页")
    @RequiresPermissions("work_user:edit")
    public String getClerkForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("entity", clerkService.getById(id));
        map.addAttribute("list", storeService.getAll());
        return "work/clerk/form";
    }

    @PostMapping("/delete")
    @WebLog(value = "删除店员")
    @RequiresPermissions("work_user:delete")
    public String deleteClerk(@PathVariable Long id) {
        clerkService.delById(id);
        return "redirect:/clerk/";
    }

    /**
     * 导出 Excel（一个 sheet）
     */
    @GetMapping(value = "writeExcel")
    public void writeExcel(HttpServletResponse response) {
        List<Clerk> list = clerkService.getAll();
        String fileName = "店员列表" + DateUtil.format(new Date(), "yyyyMMddhhmmss");
        String sheetName = "店员列表";

        ExcelUtil.writeExcel(response, list, fileName, sheetName, new Clerk());
    }


    /**
     * 读取 Excel（指定某个 sheet）
     */
    @RequestMapping(value = "readExcel", method = RequestMethod.POST)
    public String readExcel(MultipartFile excel, @RequestParam(defaultValue = "1") int sheetNo,
                            @RequestParam(defaultValue = "1") int headLineNum) {
        List<Object> list = ExcelUtil.readExcel(excel, new Clerk(), sheetNo, headLineNum);
        Clerk clerk;
        for (Object o : Objects.requireNonNull(list)) {
            clerk = (Clerk) o;
            clerkService.save(clerk);
        }
        return "redirect:/clerk/";
    }
}
