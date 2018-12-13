package com.example.springbootstage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制台
 * Created by WangHong on 2018/3/19.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(name = "控制台")
    public String home(Model model) {
        logger.info("home().........");
        model.addAttribute("name", "WangHong");
        return "index";
    }
}
