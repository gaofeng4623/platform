package com.gtzn.modules.base.entity;


import com.gtzn.common.utils.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * spring Convert 自定义
 * 处理字符串转换到枚举类型的 Convert
 * Created by fusu on 2016/12/29.
 */
public final class ValueToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    @Override
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new ValueToEnum<>(targetType);
    }

    private class ValueToEnum<T extends Enum<?>> implements Converter<String, T> {

        private final Class<T> enumType;

        ValueToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            int value = Integer.valueOf(source);
            if (value == Integer.MIN_VALUE) {
                return null;
            }
            T temp = null;
            try {
                Method getValue = enumType.getMethod("getValue");
                T[] objects = enumType.getEnumConstants();
                for (T ob : objects) {
                    Integer temps = (Integer) getValue.invoke(ob);
                    if (temps == value) {
                        temp = ob;
                        break;
                    }
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return temp;
        }
    }
}
