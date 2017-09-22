package com.gtzn.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @info 数据源切换注解标记
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-05-05
 * @version 1.0.0
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface Source {
    public String value() default "";
}
