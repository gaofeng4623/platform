/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.gtzn.modules.gen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Page;
import com.gtzn.common.service.BaseService;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.gen.dao.GenSchemeDao;
import com.gtzn.modules.gen.dao.BaseDao;
import com.gtzn.modules.gen.dao.IbatisSupportDao;
import com.gtzn.modules.gen.entity.GenConfig;
import com.gtzn.modules.gen.entity.GenScheme;
import com.gtzn.modules.gen.entity.GenTable;
import com.gtzn.modules.gen.entity.GenTableColumn;
import com.gtzn.modules.gen.entity.GenTemplate;
import com.gtzn.modules.gen.service.GenSchemeService;
import com.gtzn.modules.gen.util.GenUtils;

/**
 * 生成方案Service
 * @author gtzn
 * @version 2013-10-15
 */
@Service("genSchemeService")
@Transactional(readOnly = true)
public class GenSchemeServiceImpl extends BaseService implements GenSchemeService {

	@Autowired
	private GenSchemeDao genSchemeDao;
//	@Autowired
//	private GenTemplateDao genTemplateDao;
	@Autowired
	private IbatisSupportDao genTableDao;
	@Autowired
	private BaseDao genTableColumnDao;
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenSchemeService#get(java.lang.String)
	 */
	@Override
	public GenScheme get(String id) {
		return genSchemeDao.get(id);
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenSchemeService#find(com.gtzn.common.persistence.Page, com.gtzn.modules.gen.entity.GenScheme)
	 */
	@Override
	public Page<GenScheme> find(Page<GenScheme> page, GenScheme genScheme) {
		GenUtils.getTemplatePath();
		//genScheme.setPage(page);
		page.setList(genSchemeDao.findList(genScheme));
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenSchemeService#save(com.gtzn.modules.gen.entity.GenScheme)
	 */
	@Override
	@Transactional(readOnly = false)
	public String save(GenScheme genScheme) {
		if (StringUtils.isBlank(genScheme.getId())){
			genScheme.preInsert();
			genSchemeDao.insert(genScheme);
		}else{
			genScheme.preUpdate();
			genSchemeDao.update(genScheme);
		}
		// 生成代码
		if ("1".equals(genScheme.getFlag())){
			return generateCode(genScheme);
		}
		return "";
	}
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.gen.service.impl.GenSchemeService#delete(com.gtzn.modules.gen.entity.GenScheme)
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(GenScheme genScheme) {
		genSchemeDao.delete(genScheme);
	}
	
	private String generateCode(GenScheme genScheme){

		StringBuilder result = new StringBuilder();
		
		// 查询主表及字段列
		GenTable genTable = genTableDao.get(genScheme.getGenTable().getId());
		genTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
		// 初始化列属性字段
		GenUtils.initColumnJdbcType(genTable);
		// 获取所有代码模板
		GenConfig config = GenUtils.getConfig();
		
		// 获取模板列表
		List<GenTemplate> templateList = GenUtils.getTemplateList(config, genScheme.getCategory(), false);
		List<GenTemplate> childTableTemplateList = GenUtils.getTemplateList(config, genScheme.getCategory(), true);
		
		// 如果有子表模板，则需要获取子表列表
		if (childTableTemplateList.size() > 0){
			GenTable parentTable = new GenTable();
			parentTable.setParentTable(genTable.getName());
			genTable.setChildList(genTableDao.findList(parentTable));
		}
		
		// 生成子表模板代码
		for (GenTable childTable : genTable.getChildList()){
			childTable.setParent(genTable);
			childTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(childTable.getId()))));
			genScheme.setGenTable(childTable);
			Map<String, Object> childTableModel = GenUtils.getDataModel(genScheme);
			for (GenTemplate tpl : childTableTemplateList){
				result.append(GenUtils.generateToFile(tpl, childTableModel, genScheme.getReplaceFile()));
			}
		}
		
		// 生成主表模板代码
		genScheme.setGenTable(genTable);
		Map<String, Object> model = GenUtils.getDataModel(genScheme);
		for (GenTemplate tpl : templateList){
			result.append(GenUtils.generateToFile(tpl, model, genScheme.getReplaceFile()));
		}
		return result.toString();
	}
}
