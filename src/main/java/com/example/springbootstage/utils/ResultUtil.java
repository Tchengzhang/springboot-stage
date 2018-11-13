package com.example.springbootstage.utils;


import com.example.springbootstage.entity.Result;

public class ResultUtil {
    public static Result genJson(String code){
        return new Result(code,"",null);
    }

    public static Result genJson(String code,String message){
        return new Result(code,message,null);
    }

    public static Result genJson(String code,String message,Object data){
        return new Result(code,message,data);
    }

    public static Result ok(){
        return new Result(Constant.RESULT_SUCCESS,"",null);
    }

    public static Result ok(String msg){
        return new Result(Constant.RESULT_SUCCESS,msg,null);
    }

    public static Result ok(String msg,Object data){
        return new Result(Constant.RESULT_SUCCESS,msg,data);
    }

    public static Result error(String code ,String msg){
        return new Result(code,"",null);
    }

    public static Result error(String msg){
        return new Result(Constant.RESULT_EXCEPTION,msg,null);
    }
    public static Result error(){
        return new Result(Constant.RESULT_EXCEPTION,"位置错误，请联系管理员",null);
    }

}
