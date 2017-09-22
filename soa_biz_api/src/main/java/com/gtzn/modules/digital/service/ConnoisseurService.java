package com.gtzn.modules.digital.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.digital.entity.Connoisseur;
import java.util.List;

/**
 * describe TODO 知识库福安里模块，文件的操作Service
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/10 11:16
 **/
public interface ConnoisseurService {
	
	public abstract Pager<Connoisseur> findPage(Pager<Connoisseur> page, Connoisseur entity);
	
	public List<Connoisseur> findAllList(Connoisseur entity);
}
