package com.example.springbootstage.controller;

import com.example.springbootstage.entity.Store;
import com.example.springbootstage.entity.Clerk;
import com.example.springbootstage.service.StoreService;
import com.example.springbootstage.service.ClerkService;
import com.example.springbootstage.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/clerk")
public class ClerkController {

    @Autowired
    private ClerkService clerkService;

    @Autowired
    private StoreService storeService;

    @GetMapping
    public String getClerkList(ModelMap map) {
        map.addAttribute("clerkList", clerkService.getAll());
        return "clerkList";
    }

    @GetMapping("/create")
    public String createForm(ModelMap map, HttpSession session) {
        Clerk clerk = new Clerk();
        clerk.setStore(new Store()); //此处必须为store赋值，不然会报错
        map.addAttribute("clerk", clerk);
        map.addAttribute("storeList", storeService.getAll());
        map.addAttribute("action", "save");
        return "clerkForm";

    }

    @PostMapping({"/save", "/update"})
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
    public String getClerkForm(@PathVariable Long id, ModelMap map) {
        map.addAttribute("clerk", clerkService.getById(id));
        map.addAttribute("action", "update");
        map.addAttribute("storeList", storeService.getAll());
        return "clerkForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteClerk(@PathVariable Long id) {
        clerkService.delById(id);
        return "redirect:/clerk/";
    }


}
