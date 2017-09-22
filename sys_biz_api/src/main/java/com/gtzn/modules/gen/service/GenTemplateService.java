package com.gtzn.modules.gen.service;

import com.gtzn.common.persistence.Page;
import com.gtzn.modules.gen.entity.GenTemplate;

public interface GenTemplateService {

	GenTemplate get(String id);

	Page<GenTemplate> find(Page<GenTemplate> page, GenTemplate genTemplate);

	void save(GenTemplate genTemplate);

	void delete(GenTemplate genTemplate);

}