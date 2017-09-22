package com.gtzn.modules.base.service;

import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.base.entity.DeliverBind;
import com.gtzn.modules.sys.entity.User;

public interface DeliverBindService {

    Pager<DeliverBind> findPage(Pager<DeliverBind> page, DeliverBind deliverBind, User user);


    void save(DeliverBind deliverBind);

    int update(DeliverBind deliverBind);

    int delete(DeliverBind deliverBind);

    boolean checkExists(String textType, DeliverBind deliverBind);

    String getDeliverBindIpByLocalIp(String clientIp);

    DeliverBind get(String id);
}
