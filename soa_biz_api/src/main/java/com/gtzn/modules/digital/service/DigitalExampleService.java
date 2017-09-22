package com.gtzn.modules.digital.service;

import com.gtzn.modules.digital.entity.YArchivestore;

import java.util.List;

/**
 * @info 定义数字化档案馆的业务接口
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-05-02
 * @version 1.0.0
 */
public interface DigitalExampleService{
    public List<YArchivestore> findAllList();
}
