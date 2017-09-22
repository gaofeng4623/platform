package com.gtzn.modules.base.dao;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.annotation.MyBatisDao;
import com.gtzn.modules.base.entity.DeliverBind;

import java.util.List;

@MyBatisDao
public interface DeliverBindDao extends CrudDao<DeliverBind>{

	List<DeliverBind> findPageList(DeliverBind deliverBind);
	
	int findPageCount(DeliverBind deliverBind);

	/**
	 * 获取该workMachIp的数量
	 * @param deliverBind 交付机配置信息
	 */
	int findCountByWorkIp(DeliverBind deliverBind);

	/**
	 * 获取该deliverMachIp的数量
	 * @param deliverBind 交付机配置信息
	 */
	int findCountByDeliverIp(DeliverBind deliverBind);
}
