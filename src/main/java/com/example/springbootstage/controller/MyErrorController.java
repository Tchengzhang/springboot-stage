package com.example.springbootstage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误页面配置
 * Created by WangHong on 2018/4/3.
 */
@Controller
public class MyErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String handleError403() {
        return "common/403";
    }

    @RequestMapping(value = "/404")
    public String handleError404() {
        return "common/404";
    }

    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return ERROR_PATH;
    }
}
