package com.gtzn.modules.workflow.dao;

import java.util.List;

import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.workflow.domain.Form;
import com.gtzn.modules.workflow.domain.FormGroup;
import com.gtzn.modules.workflow.domain.FormRuValue;
@MyBatisDao
public interface FormDesignDao {

	public Form getForm(String id);

	public void insertForm(Form form);

	public void updateForm(Form form);

	public void updateFormFields(Form form);
	
	public List<Form> findForm(Form form);
	
	public void insertFormGroup(FormGroup fg);
	
	public void insertFormModel(String form_id, String model_id);
	
	public void insertFormRuValue(FormRuValue frv);
}
