package com.gtzn.modules.sys.service;

import java.util.List;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.home.entity.Stati;
import com.gtzn.modules.sys.entity.Menu;
import com.gtzn.modules.sys.entity.Role;
import com.gtzn.modules.sys.entity.User;

public interface SystemService {

	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public abstract User getUser(String id);

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public abstract User getUserByLoginName(String loginName);

	/**
	 * 用户信息分页显示
	* @Title: findUser 
	* @Description: TODO
	* @param @param page
	* @param @param user
	* @param @param search
	* @param @return
	* @return Pager<User>
	* @throws
	 */
	public abstract Pager<User> findUser(Pager<User> page, User user, User search);

	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public abstract List<User> findUser(User user, User search);

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public abstract List<User> findUserByOfficeId(String officeId);
	/**
	 * 保存用户信息
	* @Title: saveUser 
	* @Description: TODO
	* @param @param user
	* @return void
	* @throws
	 */
	public abstract void saveUser(User user);
	/**
	 * 更新用户一些信息
	* @Title: updateUserInfo 
	* @Description: TODO
	* @param @param user
	* @return void
	* @throws
	 */
	public abstract void updateUserInfo(User user);
	/**
	 * 删除用户信息
	* @Title: deleteUser 
	* @Description: TODO
	* @param @param user
	* @return void
	* @throws
	 */
	public abstract void deleteUser(User user);
	/**
	 * 更改密码
	* @Title: updatePasswordById 
	* @Description: TODO
	* @param @param id
	* @param @param loginName
	* @param @param newPassword
	* @return void
	* @throws
	 */
	public abstract void updatePasswordById(String id, String loginName, String newPassword);
	/**
	 * 登录系统时，更新用户的登录信息
	* @Title: updateUserLoginInfo 
	* @Description: TODO
	* @param @param user
	* @return void
	* @throws
	 */
	public abstract void updateUserLoginInfo(User user);
	/**
	 * 获取角色
	* @Title: getRole 
	* @Description: TODO
	* @param @param id
	* @param @return
	* @return Role
	* @throws
	 */
	public abstract Role getRole(String id);
	/**
	 * 获取角色
	* @Title: getRoleByName 
	* @Description: TODO
	* @param @param role
	* @param @return
	* @return Role
	* @throws
	 */
	public abstract Role getRoleByName(Role role);
	/**
	 * 获取角色
	* @Title: getRoleByEnname 
	* @Description: TODO
	* @param @param role
	* @param @return
	* @return Role
	* @throws
	 */
	public abstract Role getRoleByEnname(Role role);
	/**
	 * 获取角色
	* @Title: findRole 
	* @Description: TODO
	* @param @param role
	* @param @return
	* @return List<Role>
	* @throws
	 */
	public abstract List<Role> findRole(Role role);
	/**
	 * 获取用户封分配的角色
	* @Title: findRole 
	* @Description: TODO
	* @param @param user
	* @param @return
	* @return List<Role>
	* @throws
	 */
	public List<Role> findRole(User user);
	/**
	 * 分页加载角色信息
	* @Title: findRole 
	* @Description: TODO
	* @param @param pager
	* @param @param role
	* @param @param user
	* @param @return
	* @return Pager<Role>
	* @throws
	 */
	public abstract Pager<Role> findRole(Pager<Role> pager, Role role, User user);
	
	/**
	 * 保存角色
	* @Title: saveRole 
	* @Description: TODO
	* @param @param role
	* @return void
	* @throws
	 */
	public abstract void saveRole(Role role);
	/**
	 * 删除橘角色
	* @Title: deleteRole 
	* @Description: TODO
	* @param @param role
	* @return void
	* @throws
	 */
	public abstract void deleteRole(Role role);
	/**
	 * 判断角色重复
	* @Title: outUserInRole 
	* @Description: TODO
	* @param @param role
	* @param @param user
	* @param @return
	* @return Boolean
	* @throws
	 */
	public abstract Boolean outUserInRole(Role role, User user);
	/**
	 * 角色分配用户
	* @Title: assignUserToRole 
	* @Description: TODO
	* @param @param role
	* @param @param user
	* @param @return
	* @return User
	* @throws
	 */
	public abstract User assignUserToRole(Role role, User user);
	/**
	 * 获取菜单
	* @Title: getMenu 
	* @Description: TODO
	* @param @param id
	* @param @return
	* @return Menu
	* @throws
	 */
	public abstract Menu getMenu(String id);
	/**
	 * 获取用户分配的菜单
	* @Title: findMenu 
	* @Description: TODO
	* @param @param user
	* @param @return
	* @return List<Menu>
	* @throws
	 */
	public abstract List<Menu> findMenu(User user);
	/**
	 * 获取角色分配的菜单
	* @Title: findMenu 
	* @Description: TODO
	* @param @param role
	* @param @return
	* @return List<Menu>
	* @throws
	 */
	public abstract List<Menu> findMenu(Role role);
	/**
	 * 保存菜单
	* @Title: saveMenu 
	* @Description: TODO
	* @param @param menu
	* @return void
	* @throws
	 */
	public abstract void saveMenu(Menu menu);
	/**
	 * 更新菜单的序号
	* @Title: updateMenuSort 
	* @Description: TODO
	* @param @param menu
	* @return void
	* @throws
	 */
	public abstract void updateMenuSort(Menu menu);
	/**
	 * 删除菜单
	* @Title: deleteMenu 
	* @Description: TODO
	* @param @param menu
	* @return void
	* @throws
	 */
	public abstract void deleteMenu(Menu menu);
	
	/**
	 * 获取统计列表信息
	 * @return
	 */
	public abstract List<Stati> findStati();
	/**
	 * 获取角色配置的统计列表ids
	 * @param roleId
	 * @return
	 */
	public String findStatiIds(String roleId);
	/**
	 * 
	* @Title: loadRoleMenuToCache 
	* @Description: TODO
	* @param 
	* @return void
	* @throws
	 */
	public void loadRoleMenuToCache();
	
	//gtzn 20161227 wangfy ADD START
	/**
	 * 获取角色为档案验证的用户列表
	 * @return
	 */
	public List<User> findVerificationUser();
	//gtzn 20161227 wangfy ADD END
}