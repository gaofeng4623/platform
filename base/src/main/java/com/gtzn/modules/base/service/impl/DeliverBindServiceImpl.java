package com.gtzn.modules.base.service.impl;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.base.dao.DeliverBindDao;
import com.gtzn.modules.base.entity.DeliverBind;
import com.gtzn.modules.base.service.DeliverBindService;
import com.gtzn.modules.sys.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DeliverBindServiceImpl extends CrudService<DeliverBindDao, DeliverBind> implements DeliverBindService {

    @Autowired
    private DeliverBindDao deliverBindDao;

    @Override
    public Pager<DeliverBind> findPage(Pager<DeliverBind> page, DeliverBind deliverBind, User user) {
        deliverBind.setPager(page);
        deliverBind.getSqlMap().put("dsf", dataScopeFilter(user, "o", ""));
        page.setList(deliverBindDao.findPage(deliverBind));
        page.setRecords(deliverBindDao.findCount(deliverBind));
        return page;
    }


    @Override
    @Transactional(readOnly = false)
    public int update(DeliverBind deliverBind) {
        return deliverBindDao.update(deliverBind);
    }

    @Override
    public boolean checkExists(String textType, DeliverBind deliverBind) {
        int count = 0;
        if ("workIp".equals(textType)) {
            count = deliverBindDao.findCountByWorkIp(deliverBind);
        }
        if ("deliverIp".equals(textType)) {
            count = deliverBindDao.findCountByDeliverIp(deliverBind);
        }
        return count == 0;
    }

    @Override
    public String getDeliverBindIpByLocalIp(String clientIp) {
        String deliverMachIp = "";
        //根据本机ip查询匹配的交付机地址
//		Deliver Deliver = new Deliver();
//		Deliver.setWorkMachIp(clientIp);
//		List<Deliver> list = deliverDao.queryDeliverBind(Deliver,true);
//		if (list != null && list.size() > 0) {
//			Deliver sysDeliverTmp = list.get(0);
//			deliverMachIp = sysDeliverTmp.getDeliverMachIp();
//		}
        return deliverMachIp;
    }

}
