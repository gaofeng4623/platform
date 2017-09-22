package com.gtzn.modules.sys.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.sys.entity.Operate;

public interface OperateService {

	Pager<Operate> findPage(Pager<Operate> pager, Operate operate);

	void save(Operate operate);

}