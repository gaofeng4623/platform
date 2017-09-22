/**
 * 
 */
package com.gtzn.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.gtzn.common.service.BaseService;
import com.gtzn.common.service.TreeService;
import com.gtzn.modules.sys.dao.OfficeDao;
import com.gtzn.modules.sys.entity.Office;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.sys.service.OfficeService;

/**
 * 机构Service
 * @author gtzn
 * @version 2014-05-16
 */
@Service("officeService")
@Transactional(readOnly = true)
public class OfficeServiceImpl extends TreeService<OfficeDao, Office> implements OfficeService {

	/*@Override
	public List<Office> findAll(User user) {
		return getOfficeList(user);
	}*/

	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public List<Office> findList(User user){
		List<Office> officeList = null;
		if (user.isAdmin()) {
			officeList = dao.findAllList(new Office());
		} else {
			Office office = new Office();
			office.getSqlMap().put("dsf", dataScopeFilter(user, "a", ""));
			officeList = dao.findList(office);
		}
		return officeList;
	}
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public List<Office> findAll(){
		return dao.findAllList(new Office());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Office> findList(Office office, User user) {
		office.getSqlMap().put("dsf", dataScopeFilter(user, "a", ""));
		return dao.findByParentId(office);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(Office office) {
		return super.delete(office);
	}

	
}
