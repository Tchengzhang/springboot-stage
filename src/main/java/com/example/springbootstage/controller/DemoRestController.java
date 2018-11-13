package com.example.springbootstage.controller;

import com.example.springbootstage.annotation.RequestLimit;
import com.example.springbootstage.annotation.WebLog;
import com.example.springbootstage.entity.Result;
import com.example.springbootstage.utils.Constant;
import com.example.springbootstage.utils.ResultUtil;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/rest")
@Log
public class DemoRestController {

    @RequestMapping(value = "/getJson",produces = {"application/json;charset=UTF-8"})
    @WebLog(value = "json格式")
    @RequestLimit(time = 3,count = 2)
    public Result getJson(){
        log.info("这是返回json格式的请求");
        return ResultUtil.genJson(Constant.RESULT_SUCCESS,"这是返回json格式的请求１");
    }

    @RequestMapping(value = "/getXml",produces = {"application/xml;charset=UTF-8"})
    @WebLog(value = "xml格式")
    public Result getXml(){
        log.info("这是返回xml格式的请求");
        return ResultUtil.genJson(Constant.RESULT_SUCCESS,"这是返回xml格式的请求");
    }

    @GetMapping(value = "/getTest",produces = {"application/json;charset=UTF-8"})
    @WebLog(value = "get请求测试")
    public Result getTest(HttpServletRequest request){
        String param = request.getQueryString();
        log.info("这是get请求测试");
        return ResultUtil.genJson(Constant.RESULT_SUCCESS,"这是get请求测试",param);
    }

    @PostMapping("/postTest")
    @WebLog(value = "post请求测试")
    public Result postTest(HttpServletRequest request){
        String type = request.getContentType();
        log.info("这是post请求测试");
        return ResultUtil.genJson(Constant.RESULT_SUCCESS,"这是post请求测试","请求类型："+type);
    }

    @PostMapping("/postTest1")
    @WebLog(value = "post请求测试")
    public Result postTest1(@RequestBody String json,HttpServletRequest request){
        String type = request.getContentType();
        log.info("这是post请求测试");
        return ResultUtil.genJson(Constant.RESULT_SUCCESS,"这是post请求测试","请求类型："+type);
    }

}
