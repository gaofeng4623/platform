package com.gtzn.modules.gen.service;

import com.gtzn.common.persistence.Page;
import com.gtzn.modules.gen.entity.GenScheme;

public interface GenSchemeService {

	GenScheme get(String id);

	Page<GenScheme> find(Page<GenScheme> page, GenScheme genScheme);

	String save(GenScheme genScheme);

	void delete(GenScheme genScheme);

}