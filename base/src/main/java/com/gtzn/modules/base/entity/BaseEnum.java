package com.gtzn.modules.base.entity;

/**
 * 自定义mybatismybatis自带的枚举类处理的枚举类处理
 * Created by fusu on 2016/12/29.
 */
public interface BaseEnum<E extends Enum<?>, T> {

    T getValue();
    String getName();
    String getEnumName();

}
