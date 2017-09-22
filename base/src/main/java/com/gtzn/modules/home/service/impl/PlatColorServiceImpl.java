/**
 *
 */
package com.gtzn.modules.home.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.home.dao.PlatColorDao;
import com.gtzn.modules.home.dao.PlatInformationDao;
import com.gtzn.modules.home.entity.PlatColor;
import com.gtzn.modules.home.entity.PlatInformation;
import com.gtzn.modules.home.service.PlatColorService;
import com.gtzn.modules.home.service.PlatInformationService;
@Service("platColor")
@Transactional(readOnly = false)
public class PlatColorServiceImpl extends CrudService<PlatColorDao, PlatColor> implements PlatColorService{
	  @Resource
	   private PlatColorDao platColorDao;
	@Transactional(readOnly = false)
	public void save(PlatColor platColor) {
		super.save(platColor);
	}
	@Override
	public PlatColor selectTypeId(Map m) {
		// TODO Auto-generated method stub
		return platColorDao.selectTypeId(m);
	}

	
}