package com.gtzn.modules.workflow.service;

import java.util.List;

import com.gtzn.modules.workflow.domain.Form;

public interface FormDesignService {
	
	public void saveForm(Form form);
	
	public List<Form> findForm(Form form);
	
	public Form getForm(String id);
	
	
}
