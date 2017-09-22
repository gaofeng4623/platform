package com.gtzn.modules.base.entity;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义mybatis处理枚举类型的handler
 * Created by fusu on 2016/12/29.
 */
public final class SimpleEnumTypeHandler<T extends BaseEnum> extends BaseTypeHandler<T> {

    private final Class<T> type;
    private T[] enums;

    public SimpleEnumTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = this.type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    /**
     * 枚举类型转换
     *
     * @param value 数据库中存储的自定义value属性 ,枚举类中的value值
     * @return value对应的枚举类
     */
    private T convert(Integer value) {
        for (T t : enums) {
            if (t.getValue() == value) {
                return t;
            }
        }
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int columnIndex, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(columnIndex, parameter.getValue());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(rs.getInt(columnName));
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(rs.getInt(columnIndex));
        }
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.wasNull()) {
            return null;
        } else {
            return convert(cs.getInt(columnIndex));
        }
    }
}
