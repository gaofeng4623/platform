/**
 * 
 */
package com.gtzn.modules.sys.security;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtzn.common.utils.SpringContextHolder;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.Servlets;
import com.gtzn.modules.base.entity.Constant;
import com.gtzn.modules.sys.entity.Menu;
import com.gtzn.modules.sys.entity.Role;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.gtzn.modules.sys.service.SystemService;
import com.gtzn.modules.tag.util.HomeUtils;
import com.gtzn.web.util.LogUtils;
import com.gtzn.web.util.WebUtil;

/**
 * 系统安全认证实现类
 * 	
 * @author gtzn
 * @version 2014-7-5
 */
@Service
public class CasAuthorizingRealm extends CasRealm {

	public static final int HASH_INTERATIONS = 1024;
	public static final String HASH_ALGORITHM = "SHA-1";
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SystemService systemService;

	//private SessionDAO sessionDAO;

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        CasToken casToken = (CasToken) token;
        if (token == null) {
            return null;
        }
        
        String ticket = (String)casToken.getCredentials();
		logger.info("ticket====" + ticket);
        if (!org.apache.shiro.util.StringUtils.hasText(ticket)) {
            return null;
        }
        
        TicketValidator ticketValidator = ensureTicketValidator();

        try {
            // contact CAS server to validate service ticket
            Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
            // get principal, user id and attributes
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String userId = casPrincipal.getName();
            User user = getSystemService().getUserByLoginName(casPrincipal.getName());
            logger.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{
                    ticket, getCasServerUrlPrefix(), userId
            });

            Map<String, Object> attributes = casPrincipal.getAttributes();
            // refresh authentication token (user id + remember me)
            casToken.setUserId(userId);
            String rememberMeAttributeName = getRememberMeAttributeName();
            String rememberMeStringValue = (String)attributes.get(rememberMeAttributeName);
            boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
            if (isRemembered) {
                casToken.setRememberMe(true);
            }
            // create simple authentication info
            List<Object> principals = CollectionUtils.asList(new Principal(user, false), attributes);
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
            return new SimpleAuthenticationInfo(principalCollection, ticket);
        } catch (TicketValidationException e) { 
            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
        }
    }
	

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		User user = getSystemService().getUserByLoginName(principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = getSystemService().findMenu(user);
			for (Menu menu : list) {
				if (StringUtils.isNotBlank(menu.getPermission())) {
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(), ",")) {
						info.addStringPermission(permission);
					}
				}
			}
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()) {
				info.addRole(role.getEnname());
			}
			// 更新登录IP和时间
			user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
			user.setLoginDate(new Date());
			getSystemService().updateUserLoginInfo(user);
			//user.setMeunList(list);
			WebUtil.put(user);
			// 记录登录日志
			LogUtils.addLog("登录系统", Constant.ModuleType.LOGIN_OUT, Constant.OperationType.LOGIN, Constant.DeviceType.PC);
			
			return info;
		} else {
			return null;
		}
	}





	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null) {
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
	/*public SessionDAO getSessionDAO() {
		if (sessionDAO == null) {
			sessionDAO = SpringContextHolder.getBean(SessionDAO.class);
		}
		return sessionDAO;
	}*/
}
