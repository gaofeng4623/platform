package com.gtzn.modules.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.sys.dao.ExampleDao;
import com.gtzn.modules.sys.entity.Example;
import com.gtzn.modules.sys.service.ExampleService;

@Service("exampleService")
@Transactional(readOnly = true)
public class ExampleServiceImpl extends CrudService<ExampleDao, Example> implements ExampleService {

	
	//@Autowired
	
	@Override
	public Pager<Example> findPage(Pager<Example> pager, Example example) {
		
		List<String> list1 = dao.findQ1();
		List<String> list2 = dao.findQ5();
		for (String l1 : list1) {
				if (!list2.contains(l1)) {
					System.out.println(l1);
				}
		}
		for (String l2 : list2) {
			if (!list1.contains(l2)) {
				System.out.println(l2);
			}
	}
		return null;
		//return super.findPage(pager, example);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Example example) {
		super.save(example);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(Example example) {
		return super.delete(example);
	}

	@Override
	public Example get(String id) {
		return super.get(id);
	}

	public Example load(String id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

}
