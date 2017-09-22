/**
 * 
 */
package com.gtzn.modules.sys.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.BaseService;
import com.gtzn.common.service.ServiceException;
import com.gtzn.common.utils.Encodes;
import com.gtzn.common.utils.JedisUtils;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.home.entity.Stati;
import com.gtzn.modules.sys.dao.MenuDao;
import com.gtzn.modules.sys.dao.RoleDao;
import com.gtzn.modules.sys.dao.StatiDao;
import com.gtzn.modules.sys.dao.UserDao;
import com.gtzn.modules.sys.entity.Menu;
import com.gtzn.modules.sys.entity.Office;
import com.gtzn.modules.sys.entity.Role;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.sys.service.SystemService;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * 
 * @author gtzn
 * @version 2013-12-05
 */
@Service("SystemServiceImpl")
@Transactional(readOnly = true)
public class SystemServiceImpl extends BaseService implements SystemService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private StatiDao statiDao;
	
	// -- User Service --//
	@Override
	public User getUser(String id) {
		User user = userDao.get(id);
		if (user == null) {
			return null;
		}
		user.setRoleList(roleDao.findList(new Role(user)));
		return user;
	}

	@Override
	public User getUserByLoginName(String loginName) {
		return getByLoginName(loginName);
	}

	@Override
	public Pager<User> findUser(Pager<User> page, User user, User search) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		search.getSqlMap().put("dsf", dataScopeFilter(user, "o", "a"));
		// 设置分页参数
		search.setPager(page);
		if (user.isAdmin()) {
			search.setId("admin");
		}
		// 执行分页查询
		List<User> list = userDao.findList(search);
		for (User temp : list) {
			List<Role> roleList = Lists.newArrayList();
			roleList = findRoleByUser(temp);
			temp.setRoleList(roleList);
		}
		page.setList(list);
		page.setRecords(userDao.findCount(search));
		return page;
	}

	@Override
	public List<User> findUser(User user, User search) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		search.getSqlMap().put("dsf", dataScopeFilter(user, "o", "a"));
		List<User> list = userDao.findList(search);
		return list;
	}

	/**
	 * 根据用户获取用户的角色列表 @Title: findRoleByUser @Description: TODO @param @return
	 * List<Role> @throws
	 */
	public List<Role> findRoleByUser(User user) {
		return roleDao.findRoleByUser(user);
	}

	@Override
	public List<User> findUserByOfficeId(String officeId) {
		User user = new User();
		user.setOffice(new Office(officeId));
		List<User> list = userDao.findUserByOfficeId(user);
		return list;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())) {
			user.preInsert();
			userDao.insert(user);
		} else {
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
	}

	@Transactional(readOnly = false)
	public void saveUserRole(User user) {
		if (StringUtils.isNotBlank(user.getId())) {
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0) {
				userDao.insertUserRole(user);
			} else {
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.delete(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(Encodes.entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		userDao.updateLoginInfo(user);
	}


	// -- Role Service --//

	@Override
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	@Override
	public Role getRoleByName(Role role) {
		return roleDao.getByName(role);
	}

	@Override
	public Role getRoleByEnname(Role role) {
		return roleDao.getByEnname(role);
	}

	@Override
	public List<Role> findRole(Role role) {
		return roleDao.findList(role);
	}

	public Pager<Role> findRole(Pager<Role> pager, Role role, User user) {
		role.setPager(pager);
		role.getSqlMap().put("dsf", dataScopeFilter(user, "o", ""));
		pager.setList(roleDao.findPage(role));
		pager.setRecords(roleDao.findCount(role));
		return pager;
	}

	/*@Override
	public List<Role> findAllRole(User user) {
		return getRoleList(user);
	}*/

	@Override
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())) {
			role.preInsert();
			roleDao.insert(role);
		} else {
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0) {
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0) {
			roleDao.insertRoleOffice(role);
		}
		//更新角色与统计信息关联表
		roleDao.replaceRoleStatis(role);
		//重新加载角色缓存
		loadRoleCache(role);
		
		// UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		// 删除角色（逻辑删除）
		roleDao.delete(role);
		// 删除角色与菜单关联
		roleDao.deleteRoleMenu(role);
		// 删除角色与部门关联
		roleDao.deleteRoleOffice(role);
		removeRoleCache(role);
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles) {
			if (e.getId().equals(role.getId())) {
				roles.remove(e);
				saveUserRole(user);
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null) {
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUserRole(user);
		return user;
	}

	// -- Menu Service --//
	@Override
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	/**
	 * 获取当前用户授权菜单
	 * 
	 * @return
	 */
	@Override
	public List<Menu> findMenu(User user) {
		List<Menu> menuList = Lists.newArrayList();
		if (user.isAdmin()) {
			menuList = menuDao.findAllList(new Menu());
		} else {
			/*Menu m = new Menu();
			m.setUserId(user.getId());
			menuList = menuDao.findByUserId(m);*/
			Set<Menu> sets = new HashSet<>();
			for (Role role : user.getRoleList()) {
				Object o = JedisUtils.getObject(role.getId());
				if (null != o) {
					@SuppressWarnings("unchecked")
					List<Menu> menus = (List<Menu>) JedisUtils.getObject(role.getId());
					sets.addAll(menus);
				}
			}
			menuList.addAll(sets);
		}
		//按照序号排序
		Collections.sort(menuList); 
		return menuList;
	}
	@Override
	public List<Menu> findMenu(Role role) {
		Menu m = new Menu();
		m.setRoleId(role.getId());
		return menuDao.findByRoleId(m);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {

		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds();

		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())) {
			menu.preInsert();
			menuDao.insert(menu);
		} else {
			menu.preUpdate();
			menuDao.update(menu);
		}

		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%," + menu.getId() + ",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		//操作菜单需要重新加载缓存
		loadRoleMenuToCache();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		// UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		// JedisUtils.del(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		//删除菜单需要重新加载缓存
		this.loadRoleMenuToCache();
	}

	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public User getByLoginName(String loginName) {

		User user = userDao.getByLoginName(new User(null, loginName));
		if (user == null) {
			return null;
		}
		user.setRoleList(roleDao.findList(new Role(user)));
		return user;
	}

	/**
	 * 获取当前用户角色列表
	 * 
	 * @return
	 */
	public List<Role> findRole(User user) {
		List<Role> roleList = null;
		if (user.isAdmin()) {
			roleList = roleDao.findAllList(new Role());
		} else {
			Role role = new Role();
			role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "o", "u"));
			roleList = roleDao.findList(role);
		}
		return roleList;
	}

	
	public List<Stati> findStati() {
		List<Stati> list = Lists.newArrayList();
		List<Stati> statis = statiDao.findList(new Stati());
		List<Stati> groups = statiDao.findGroup();
		for (int i = 0; i < groups.size(); i++) {
			Stati group = groups.get(i);
			group.setStatiKey("id"+i);
			group.setStatiName(group.getGroupFlag());
			group.setNocheck(true);
			list.add(group);
			for (Stati s : statis) {
				if (group.getGroupFlag().equals(s.getGroupFlag())) {
					s.setpId("id"+i);
					s.setNocheck(false);
					list.add(s);
				}
			}
		}
		return list;
	}
	public String findStatiIds(String roleId) {
		
		return statiDao.findStatiIds(roleId);
	}
	
	/*缓存相关
	 */
	public void loadRoleMenuToCache() {
		List<Role> roles = findRole(new Role());
		for (Role role : roles) {
			List<Menu> menus = this.findMenu(role);
			if (null != menus && !menus.isEmpty()) {
				JedisUtils.putObject(role.getId(), menus);
			}
		}
	}
	public void removeRoleCache(Role role) {
		JedisUtils.del(role.getId());
	}
	public void loadRoleCache(Role role) {
		List<Menu> menus = this.findMenu(role);
		if (null != menus && !menus.isEmpty()) {
			JedisUtils.putObject(role.getId(), menus);
		}
	} 
	/*缓存相关
	 */
	//gtzn 20161227 wangfy ADD START
	/**
	 * 获取角色为档案验证的用户列表
	 * @return
	 */
	public List<User> findVerificationUser(){
		return userDao.findVerificationUser();
	};
	//gtzn 20161227 wangfy ADD END
	
	
	
	
	
}
