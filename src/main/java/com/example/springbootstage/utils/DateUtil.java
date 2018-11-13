package com.example.springbootstage.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

    private static final ThreadLocal<Map<String, SimpleDateFormat>>

            messageFormat =  new ThreadLocal<Map<String, SimpleDateFormat>>(){

        public Map<String, SimpleDateFormat> initialValue(){
            return new HashMap<>();
        }
    };
    /**
     * 获取线程局部变量
     * @param dateFormat
     * @return
     */
    private static  SimpleDateFormat getDateFormat(String dateFormat) {

        Map<String, SimpleDateFormat> formatters = messageFormat.get();
        SimpleDateFormat formatter = formatters.get(dateFormat);
        if (formatter == null)
        {
            formatter = new SimpleDateFormat(dateFormat);
            formatters.put(dateFormat, formatter);
        }
        return formatter;
    }

    public static String format(Date date,String pattern){
        return getDateFormat(pattern).format(date);
    }

    public static Date parse(String date,String pattern) throws ParseException {
        return getDateFormat(pattern).parse(date);
    }

}
