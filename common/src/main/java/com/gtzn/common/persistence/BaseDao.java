/**
 *
 */
package com.gtzn.common.persistence;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DAO支持类实现,提供mybatis注解支持
 * @author gtzn
 * @version 2014-05-16
 */
public interface BaseDao {

    /**
     * 批量删除数据
     * @param idList
     * @return
     */
    public int batchDelete(@Param("idList") String[] idList);


    /**
     * 批量插入数据
     * @param list
     * @return
     */
    public int batchInsert(@Param("list") List list);


    /**
     * 执行sql语句
     * @param sql
     * @return
     */
    public int executeUpdate(@Param("sql") String sql);




}