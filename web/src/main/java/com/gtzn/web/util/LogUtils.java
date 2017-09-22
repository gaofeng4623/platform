/**
 *
 */
package com.gtzn.web.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gtzn.common.utils.SpringContextHolder;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.base.entity.Constant.DeviceType;
import com.gtzn.modules.base.entity.Constant.ModuleType;
import com.gtzn.modules.base.entity.Constant.OperationType;
import com.gtzn.modules.sys.entity.Operate;
import com.gtzn.modules.sys.entity.User;
import com.gtzn.modules.sys.service.OperateService;

/**
 * 日志工具类
 *
 * @author gtzn
 * @version 2014-11-7
 */
public class LogUtils {


    private static OperateService operateService = SpringContextHolder.getBean(OperateService.class);


    /**
     * 保存操作日志
     *
     * @param operationRecord 操作记录
     * @param moduleType      模块名称枚举
     * @param operationType   操作类型名称枚举
     * @param deviceType      设备类型名称枚举
     */
    public static void addLog(String operationRecord, ModuleType moduleType, OperationType operationType, DeviceType deviceType) {
        User user = WebUtil.getUser();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = StringUtils.getRemoteAddr(request);
        Operate operate = new Operate();
        operate.setUserNo(user.getNo());
        operate.setUserName(user.getName());
        operate.setOperationRecord(operationRecord);
        operate.setModule(moduleType.getName());
        operate.setOperationType(operationType.getName());
        operate.setIp(ip);
        operate.setEqType(deviceType.getName());
        operate.setBranchId(user.getBranchId());
        operate.setCreateDate(new Date());
        operateService.save(operate);
    }
}
