package com.example.springbootstage.exception;

import com.example.springbootstage.entity.Result;
import com.example.springbootstage.utils.Constant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result jsonExceptionHandler(HttpServletRequest request, Exception e){
        Result result = new Result();
        result.setCode(Constant.RESULT_EXCEPTION);
        result.setMsg(e.getMessage());
        return result;
    }
}
