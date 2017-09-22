package com.gtzn.modules.sys.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.sys.entity.Example;

public interface ExampleService {

	public Pager<Example> findPage(Pager<Example> pager, Example example);
	
	public void save(Example example);

	public int delete(Example example);
	
	public Example get(String id);
	
	
	public Example load(String id);
	
}
