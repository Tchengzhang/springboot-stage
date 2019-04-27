/**
 * 版权所有(C)，上海***股份有限公司，2018，所有权利保留。
 * 
 * 项目名：	springboot
 * 文件名：	TargetDateSource.java
 * 模块说明：	
 * 修改历史：
 * 2018年10月21日 - Administrator - 创建。
 */
package com.example.springbootstage.annotation;

import java.lang.annotation.*;

/**
 * @author Administrator
 *
 */
@Target({
    ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDateSource {
  String dataSource() default "";// 数据源
}
