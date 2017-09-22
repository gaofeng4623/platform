package com.gtzn.web.util;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.gtzn.common.utils.SpringContextHolder;
import com.gtzn.modules.sys.entity.Menu;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.gtzn.modules.sys.service.SystemService;

public class WebUtil {

	private static SystemService systemService;

	static {
		systemService = SpringContextHolder.getBean(SystemService.class);
	}
	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";

	public static Session session() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
		} catch (InvalidSessionException e) {

		}
		return null;
	}

	public static Object getSession(String key, Object defaultValue) {
		Object obj = session().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putSession(String key, Object value) {
		session().setAttribute(key, value);
	}

	public static void removeSession(String key) {
		session().removeAttribute(key);
	}

	public static Object getSession(String key) {
		return getSession(key, null);
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal() {

		Subject subject = SecurityUtils.getSubject();
		Principal principal = (Principal) subject.getPrincipal();
		if (principal != null) {
			return principal;
		}

		return null;
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 null
	 */
	public static User getUser() {
		Principal principal = getPrincipal();
		if (principal != null) {
			User user = get(principal.getId());
			return user;
		}
		return null;
	}

	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache() {
		removeSession(CACHE_ROLE_LIST);
		removeSession(CACHE_MENU_LIST);
		removeSession(CACHE_AREA_LIST);
		removeSession(CACHE_OFFICE_LIST);
		removeSession(CACHE_OFFICE_ALL_LIST);
		clearCache(getUser());
	}

	/**
	 * 清除指定用户缓存
	 * 
	 * @param user
	 */
	public static void clearCache(User user) {
		removeSession(USER_CACHE_ID_ + user.getId());
		if (user.getOffice() != null && user.getOffice().getId() != null) {
			removeSession(USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
		}
	}

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id) {
		// 将用户信息放入自己的session
		User user = (User) getSession(USER_CACHE_ID_ + id);
		if (user == null) {
			//return null;
			user = systemService.getUser(id);
			if (user == null) {
				return null;
			}
			putSession(USER_CACHE_ID_ + user.getId(), user);
		}
		return user;
	}
	public static void put(User user) {
		// 将用户信息放入自己的session
		putSession(USER_CACHE_ID_ + user.getId(), user);
	}
	public static List<Menu> getMenuList() {
		//return getUser().getMeunList();
		return systemService.findMenu(getUser());
	}

	public static void loadRoleMenuToCache() {
		systemService.loadRoleMenuToCache();
	}
}
