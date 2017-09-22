/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.gtzn.modules.gen.service.impl;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Page;
import com.gtzn.common.service.BaseService;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.gen.dao.GenTemplateDao;
import com.gtzn.modules.gen.entity.GenTemplate;
import com.gtzn.modules.gen.service.GenTemplateService;

/**
 * 代码模板Service
 * @author gtzn
 * @version 2013-10-15
 */
@Service("genTemplateService")
@Transactional(readOnly = true)
public class GenTemplateServiceImpl extends BaseService implements GenTemplateService {

	@Autowired
	private GenTemplateDao genTemplateDao;
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTemplateService#get(java.lang.String)
	 */
	@Override
	public GenTemplate get(String id) {
		return genTemplateDao.get(id);
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTemplateService#find(com.gtzn.common.persistence.Page, com.gtzn.modules.gen.entity.GenTemplate)
	 */
	@Override
	public Page<GenTemplate> find(Page<GenTemplate> page, GenTemplate genTemplate) {
		//genTemplate.setPage(page);
		page.setList(genTemplateDao.findList(genTemplate));
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTemplateService#save(com.gtzn.modules.gen.entity.GenTemplate)
	 */
	@Override
	@Transactional(readOnly = false)
	public void save(GenTemplate genTemplate) {
		if (genTemplate.getContent()!=null){
			genTemplate.setContent(StringEscapeUtils.unescapeHtml4(genTemplate.getContent()));
		}
		if (StringUtils.isBlank(genTemplate.getId())){
			genTemplate.preInsert();
			genTemplateDao.insert(genTemplate);
		}else{
			genTemplate.preUpdate();
			genTemplateDao.update(genTemplate);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenTemplateService#delete(com.gtzn.modules.gen.entity.GenTemplate)
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(GenTemplate genTemplate) {
		genTemplateDao.delete(genTemplate);
	}
	
}
