package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.Brand;
import com.example.springbootstage.entity.Model;
import com.example.springbootstage.entity.Store;
import com.example.springbootstage.service.BrandService;
import com.example.springbootstage.service.ModelService;
import com.example.springbootstage.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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


}
