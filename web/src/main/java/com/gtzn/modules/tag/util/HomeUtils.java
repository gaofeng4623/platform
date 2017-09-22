/**
 *
 */
package com.gtzn.modules.tag.util;

import java.util.List;

import com.gtzn.common.config.Global;
import com.gtzn.common.utils.SpringContextHolder;
import com.gtzn.modules.digital.entity.WorkRate;
import com.gtzn.modules.digital.entity.YDataCollection;
import com.gtzn.modules.digital.service.DataCollectionService;
import com.gtzn.modules.digital.service.WorkProcessService;
import com.gtzn.modules.digital.service.WorkRateService;
import com.gtzn.modules.home.entity.NoticeInfo;
import com.gtzn.modules.home.entity.PlatCollection;
import com.gtzn.modules.home.service.NoticeInfoService;
import com.gtzn.modules.home.service.PlatCollectionService;
import com.gtzn.modules.monitor.entity.Alarm;
import com.gtzn.modules.monitor.entity.LoginResult;
import com.gtzn.modules.platAlarm.web.AlarmController;
import com.gtzn.soa.service.WebService;
import com.gtzn.web.util.WebUtil;

/**
 * @info 平台首页工具
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-04-21 15:11:00
 * @version $Id$
 */
public class HomeUtils {
    private static WebService loginService = SpringContextHolder.getBean("loginService"); //登录服务
    private static NoticeInfoService noticeInfoService = SpringContextHolder.getBean(NoticeInfoService.class);
    private static DataCollectionService dataCollectionService = SpringContextHolder.getBean(DataCollectionService.class);


    /*调用第三方系统登录服务*/
    public static LoginResult login(boolean isDesigner) throws Exception {
        return loginService.invokeResult("login", new Object[]{Global.getConfig("wsUser"),
                Global.getConfig("wsPassword"), isDesigner}, LoginResult.class, false, false);
    }

    public static List<NoticeInfo> findTopN() {
    	NoticeInfo info = new NoticeInfo();
    	info.setLongInUser(WebUtil.getUser());
        return noticeInfoService.findTopN(info, 6);
    }

    public static List<WorkRate> findWorkRate() {
        return null;
    }
    
    public static List<YDataCollection> findCollect() {
        return dataCollectionService.findTopN(2);
    }
}
