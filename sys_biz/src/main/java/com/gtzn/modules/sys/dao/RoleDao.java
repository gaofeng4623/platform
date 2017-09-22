/**
 * 
 */
package com.gtzn.modules.sys.dao;

import java.util.List;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.sys.entity.Role;
import com.gtzn.modules.sys.entity.User;

/**
 * 角色DAO接口
 * @author gtzn
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);
	
	public List<Role> findRoleByUser(User user);

	/**
	 * 更新角色配置的统计信息
	 * @param role
	 * @return
	 */
	public int replaceRoleStatis(Role role);
}
