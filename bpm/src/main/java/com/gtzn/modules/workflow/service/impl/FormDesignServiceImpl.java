package com.gtzn.modules.workflow.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.utils.IdGen;
import com.gtzn.modules.workflow.dao.FormDesignDao;
import com.gtzn.modules.workflow.domain.Form;
import com.gtzn.modules.workflow.service.FormDesignService;

@Service("formDesignService")
@Transactional(readOnly=true)
public class FormDesignServiceImpl implements FormDesignService {

	@Autowired
	FormDesignDao formDesignDao;

	@Override
	@Transactional(readOnly=false)
	public void saveForm(Form form) {
		if (StringUtils.isEmpty(form.getId())) {
			form.setId(IdGen.uuid());
			formDesignDao.insertForm(form);
		} else {
			formDesignDao.updateFormFields(form);
		}
	}

	@Override
	public List<Form> findForm(Form form) {
		return formDesignDao.findForm(form);
	}
	
	
	public Form getForm(String id) {
		return formDesignDao.getForm(id);
	}
	
	
}
