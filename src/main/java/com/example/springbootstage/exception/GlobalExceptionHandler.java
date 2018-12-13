package com.example.springbootstage.exception;

import com.example.springbootstage.entity.Result;
import com.example.springbootstage.utils.Constant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

/*    @ExceptionHandler(value = Exception.class)
    public Result jsonExceptionHandler(HttpServletRequest request, RedirectAttributes redirectAttributes, Exception e) {
        Result result = new Result();
        result.setCode(Constant.RESULT_EXCEPTION);
        result.setMsg(e.getMessage());
        return result;
    }*/
}
