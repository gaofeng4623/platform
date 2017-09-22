package com.gtzn.modules.base.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2017/5/19.
 */
public class TaskExample {

    public void doTask() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(new Date()) + " todo..................");
    }
}
