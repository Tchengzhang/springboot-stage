package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.work.Store;
import com.example.springbootstage.excel.ExcelUtil;
import com.example.springbootstage.service.work.StoreService;
import com.example.springbootstage.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    @WebLog(value = "跳转门店列表页")
    public String getStoreList(ModelMap map) {
        map.addAttribute("storeList", storeService.getAll());
        return "storeList";
    }

    @GetMapping("/create")
    @WebLog(value = "跳转门店页")
    public String createForm(ModelMap map) {
        Store store = new Store();
        map.addAttribute("store", store);
        map.addAttribute("action", "save");
        return "storeForm";

    }

    @PostMapping({"/save", "/update"})
    @WebLog(value = "插入或修改门店信息")
    public String save(@ModelAttribute Store store, HttpServletRequest request) {
        String url = request.getRequestURI();
        if (url.contains("save")) {
            store.setCreateTime(new Date());
        } else {
            store.setUpdateTime(new Date());
        }
        storeService.save(store);
        return "redirect:/store/";
    }

    @GetMapping("/update/{id}")
    @WebLog(value = "跳转修改门店页")
    public String getStoreForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("store", storeService.getById(id));
        map.addAttribute("action", "update");
        return "storeForm";
    }

    @GetMapping("/delete/{id}")
    @WebLog(value = "删除门店")
    public String deleteStore(@PathVariable Long id) {
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
