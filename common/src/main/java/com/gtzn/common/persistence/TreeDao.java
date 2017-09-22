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
public interface TreeDao<T extends TreeEntity<T>> extends CrudDao<T> {

	/**
	 * 找到所有子节点
	 *
	 * @param entity
	 * @return
	 */
	public List<T> findByParentIdsLike(T entity);

	/**
	 * 更新所有父节点字段
	 *
	 * @param entity
	 * @return
	 */
	public int updateParentIds(T entity);

	/**
	 * 通过父节点id查询该父节点下的子节点（即查询此节点的所有兄弟节点）
	 *
	 * @param parentId 节点的父id
	 */
	List<T> getListByParentId(@Param("parentId") String parentId);

	/**
	 * 通过节点id查询此节点下面的所有的子节点
	 *
	 * @param id 节点id
	 */
	List<T> getChildListById(@Param("id") String id);


}