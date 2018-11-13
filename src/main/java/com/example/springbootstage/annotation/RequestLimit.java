package com.example.springbootstage.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {

    int count() default 5;

    long time() default 60;
}
