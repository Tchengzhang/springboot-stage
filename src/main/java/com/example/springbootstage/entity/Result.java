package com.example.springbootstage.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {
    private String code;
    private String msg;
    private Object data;

    public Result() {
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }
    @XmlElement
    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }
    @XmlElement
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }
    @XmlElement
    public void setData(Object data) {
        this.data = data;
    }

}
