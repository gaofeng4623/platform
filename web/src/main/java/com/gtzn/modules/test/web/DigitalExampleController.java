package com.gtzn.modules.test.web;

import com.gtzn.modules.digital.entity.YArchivestore;
import com.gtzn.modules.digital.service.DigitalExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 数据源接口测试
 */
@Controller
@RequestMapping(value = "/test")
public class DigitalExampleController {

    @Autowired
    DigitalExampleService digitalExampleService;

    @ResponseBody
    @RequestMapping(value = "list")
    public List<YArchivestore> test() {
        try {
            return digitalExampleService.findAllList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
