package com.gtzn.modules.base.web;


import com.gtzn.common.persistence.Pager;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.base.entity.Constant.DeviceType;
import com.gtzn.modules.base.entity.Constant.ModuleType;
import com.gtzn.modules.base.entity.Constant.OperationType;
import com.gtzn.modules.base.entity.DeliverBind;
import com.gtzn.modules.base.service.DeliverBindService;
import com.gtzn.web.util.LogUtils;
import com.gtzn.web.util.WebUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/base/deliver")
public class DeliverBindController extends BaseController {

    @Autowired
    private DeliverBindService deliverBindService;


    @RequiresPermissions("base:deliver:view")
    @RequestMapping(value = "index")
    public String index() {
        return "/modules/base/deliver/deliverIndex";
    }

    /**
     * 交付机配置首页
     */
    @RequiresPermissions("base:deliver:view")
    @RequestMapping(value = "list")
    public String list() {
        return "/modules/base/deliver/deliverList";
    }

    /**
     * 根据登录用户权限，加载交付机配置数据，分页显示
     *
     * @param page        分页数据
     * @param deliverBind 请求条件
     */
    @RequiresPermissions("base:deliver:view")
    @RequestMapping(value = {"load"})
    @ResponseBody
    public Pager<DeliverBind> load(Pager<DeliverBind> page, DeliverBind deliverBind) {
        page = deliverBindService.findPage(page, deliverBind, WebUtil.getUser());
        return page;
    }

    /**
     * 新增or修改交付机信息
     *
     * @param deliverBind 交付机信息
     */
    @RequiresPermissions("base:deliver:edit")
    @RequestMapping(value = "save")
    public String save(DeliverBind deliverBind, RedirectAttributes redirectAttributes) {
        if (StringUtils.isBlank(deliverBind.getId())) {
            deliverBind.setBranchId(WebUtil.getUser().getBranchId());
            deliverBindService.save(deliverBind);
            LogUtils.addLog("保存" + deliverBind.getBranchId() + "分行交付机配置数据",
                    ModuleType.DELIVER_DEVICE_CONFIG, OperationType.SAVE, DeviceType.PC);
            addMessage(redirectAttributes, "保存交付机配置信息成功");
        } else {
            deliverBindService.update(deliverBind);
            LogUtils.addLog("修改" + deliverBind.getId() + "交付机配置数据",
                    ModuleType.DELIVER_DEVICE_CONFIG, OperationType.UPDATE, DeviceType.PC);
            addMessage(redirectAttributes, "修改交付机配置信息成功");
        }
        return "redirect:" + "/base/deliver/list";
    }

    /**
     * 通过主键删除一条交付机配置信息
     *
     * @param deliverBind 要删除的交付机信息
     */
    @RequiresPermissions("base:deliver:delete")
    @RequestMapping(value = "delete")
    public String delete(DeliverBind deliverBind, RedirectAttributes redirectAttributes) {
        deliverBindService.delete(deliverBind);
        LogUtils.addLog("删除" + deliverBind.getId() + "的交付机配置数据",
                ModuleType.DELIVER_DEVICE_CONFIG, OperationType.DELETE, DeviceType.PC);
        addMessage(redirectAttributes, "删除交付机配置信息成功");
        return "redirect:" + "/base/deliver/list";
    }

    /**
     * 交付机添加/修改页面
     *
     * @param deliverBind 请求条件
     */
    @RequiresPermissions("base:deliver:view")
    @RequestMapping(value = "form")
    public String form(DeliverBind deliverBind, Model model) {
        if (StringUtils.isNotBlank(deliverBind.getId())) {
            deliverBind = deliverBindService.get(deliverBind.getId());
        }
        model.addAttribute("deliver", deliverBind);
        return "modules/base/deliver/deliverForm";
    }

    /**
     * 工作机IP、交付机IP唯一性检查
     * 新增时： true: 不存在  false： 已存在
     * 修改时： Id存在, true: 不存在  false： 已存在
     *
     * @param textType    工作机IP/交付机IP的区别 "workIp : 工作机IP   deliverIp：交付机IP"
     * @param deliverBind 交付机配置信息
     */
    @RequiresPermissions("base:deliver:view")
    @RequestMapping(value = "checkExists")
    @ResponseBody
    public boolean checkExists(String textType, DeliverBind deliverBind) {
        deliverBind.setBranchId(WebUtil.getUser().getBranchId());
        return deliverBindService.checkExists(textType, deliverBind);
    }

}
