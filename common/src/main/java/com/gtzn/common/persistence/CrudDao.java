/**
 *
 */
package com.gtzn.common.persistence;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DAO支持类实现
 *
 * @param <T>
 * @author gtzn
 * @version 2014-05-16
 */
public interface CrudDao<T> extends BaseDao {

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public T get(@Param("id") String id);

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    public T get(T entity);


    /**
     * 插入数据
     * @param entity
     * @return
     */
    public int insert(T entity);

    /**
     * 有条件插入
     * @param entity
     * @return
     */
    public int insertSelective(T entity);

    /**
     * 更新数据
     * @param entity
     * @return
     */
    public int update(T entity);

    /**
     * 有条件更新
     * @param entity
     * @return
     */
    public int updateSelective(T entity);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param entity
     * @return
     */
    public int delete(T entity);

    /**
     * 删除单条数据
     * @param id
     * @return
     */
    public int delete(@Param("id") String id);

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<T> findList(T entity);

    /**
     * 分页查询数据
     * @param entity
     * @return
     */
    public List<T> findPage(T entity);



    public int findCount(T entity);


    /**
     * 查询所有数据列表
     * @param entity
     * @return
     */
    public List<T> findAllList(T entity);


}