package com.gtzn.modules.digital.service.impl;

import com.gtzn.common.annotation.Source;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.digital.dao.YArchivestoreDao;
import com.gtzn.modules.digital.entity.YArchivestore;
import com.gtzn.modules.digital.service.DigitalExampleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源配置例子
 */
@Service
@Source("platform")
public class DigitalExampleServiceImpl extends CrudService<YArchivestoreDao, YArchivestore> implements DigitalExampleService{
    @Resource
    private YArchivestoreDao yArchivestoreDao;

    @Source("digital")
    public List<YArchivestore> findAllList() {
        return yArchivestoreDao.findAllList(new YArchivestore());
    }
}
