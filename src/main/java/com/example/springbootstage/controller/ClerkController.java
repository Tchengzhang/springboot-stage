package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.Store;
import com.example.springbootstage.entity.Clerk;
import com.example.springbootstage.excel.ExcelUtil;
import com.example.springbootstage.service.StoreService;
import com.example.springbootstage.service.ClerkService;
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
    public String getClerkList(ModelMap map) {
        map.addAttribute("clerkList", clerkService.getAll());
        return "clerkList";
    }

    @GetMapping("/create")
    @WebLog(value = "跳转店员页")
    public String createForm(ModelMap map, HttpSession session) {
        Clerk clerk = new Clerk();
        clerk.setStore(new Store()); //此处必须为store赋值，不然会报错
        map.addAttribute("clerk", clerk);
        map.addAttribute("storeList", storeService.getAll());
        map.addAttribute("action", "save");
        return "clerkForm";

    }

    @PostMapping({"/save", "/update"})
    @WebLog(value = "插入或修改店员信息")
    public String save(@ModelAttribute Clerk clerk, HttpServletRequest request) {
        String url = request.getRequestURI();
        if (url.contains("save")) {
            clerk.setCreateTime(new Date());
        } else {
            clerk.setUpdateTime(new Date());
        }
        clerkService.save(clerk);
        return "redirect:/clerk/";
    }

    @GetMapping("/update/{id}")
    @WebLog(value = "跳转修改店员页")
    public String getClerkForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("clerk", clerkService.getById(id));
        map.addAttribute("action", "update");
        map.addAttribute("storeList", storeService.getAll());
        return "clerkForm";
    }

    @GetMapping("/delete/{id}")
    @WebLog(value = "删除店员")
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
