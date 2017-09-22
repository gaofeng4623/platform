package com.gtzn.modules.workflow.web;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.workflow.domain.Node;
import com.gtzn.modules.workflow.domain.WFModel;
import com.gtzn.modules.workflow.service.WorkflowDefinitionService;


/**
 * 流程模型控制器
 *
 */
@Controller
@RequestMapping(value = "/workflow/model")
public class ModelController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    WorkflowDefinitionService workflowDefinitionService;
    
    @RequestMapping(value = "list")

    public ModelAndView toModelList(String tenantId, String key, String name) {
        ModelAndView mav = new ModelAndView("modules/workflow/model-list");
        //List<SysOrg> orgs = sysOrgService.findBranchOrg("0");
       // mav.addObject("orgs", orgs);
        return mav;
    }
    
    /**
     * 模型列表
     */
    @RequestMapping(value = "loadList")
    @ResponseBody
    public Pager<WFModel> modelList(int page, int rows, String tenantId, String key, String name) {
        ModelQuery query = repositoryService.createModelQuery().orderByTenantId().asc().orderByModelKey().asc().orderByModelVersion().asc();
        if (StringUtils.isNoneBlank(tenantId)) {
        	query.modelTenantId(tenantId);
        }
        if (StringUtils.isNoneBlank(key)) {
        	query.modelKey(key);
        }
        if (StringUtils.isNoneBlank(name)) {
        	query.modelName(name);
        }
        List<WFModel> result = new ArrayList<>();
        List<Model> list = query.listPage((page-1)*rows, rows);
        Long total = query.count();
        for (Model mo : list) {
        	ModelEntity m = (ModelEntity) mo;
        	
        	WFModel model = new WFModel();
        	
        	model.setId(m.getId());
        	model.setKey(m.getKey());
        	model.setName(m.getName());
        	model.setTenantId(m.getTenantId());
        	model.setCreateTime(m.getCreateTime());
        	model.setLastUpdateTime(m.getLastUpdateTime());
        	model.setVersion(m.getVersion());
        	model.setRevision(m.getRevision());
        	//SysOrg org = sysOrgService.findSysOrgByCode(m.getTenantId());
        	//model.setTenantName(org.getOrgName());
        	result.add(model);
        }
        Pager<WFModel> pager = new Pager<>();
        pager.setRecords(total.intValue());
        pager.setList(result);
        return pager;
    }

    /**
     * 创建模型
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Ajax create(String name, String key, String description,
    		String tenantId, HttpServletRequest request, HttpServletResponse response) {
        try {
        	tenantId= "gtzn";
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(key));
            modelData.setTenantId(tenantId);
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            return new Ajax(true, "保存成功");
            //response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        } catch (Exception e) {
            logger.error("创建模型失败：", e);
            return new Ajax(false, "保存失败");
        }
    }

    /**
     * 根据Model部署流程
     */
    @RequestMapping(value = "deploy")
    @ResponseBody
    public Ajax deploy(String modelId) {
    	Ajax ajax = null;
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().tenantId(modelData.getTenantId()).name(modelData.getName()).addString(processName, new String(bpmnBytes, "utf-8")).deploy();
            ajax = new Ajax(true, deployment.getName()+"部署成功!");
        } catch (Exception e) {
        	 ajax = new Ajax(false, "出现异常:" + e.getMessage());
        	 logger.error("根据模型部署流程失败：modelId={}", modelId, e);
        }
        return ajax;
    }

    /**
     * 导出model对象为指定类型
     *
     * @param modelId 模型ID
     * @param type    导出文件类型(bpmn\json)
     */
    @RequestMapping(value = "export/{modelId}/{type}")
    public void export(@PathVariable("modelId") String modelId,
                       @PathVariable("type") String type,
                       HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.getOutputStream().println("no main process, can't export for type: " + type);
                response.flushBuffer();
                return;
            }

            String filename = "";
            byte[] exportBytes = null;

            String mainProcessId = bpmnModel.getMainProcess().getId();

            if (type.equals("bpmn")) {

                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                exportBytes = xmlConverter.convertToXML(bpmnModel);

                filename = mainProcessId + ".bpmn20.xml";
            } else if (type.equals("json")) {

                exportBytes = modelEditorSource;
                filename = mainProcessId + ".json";

            }

            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
            IOUtils.copy(in, response.getOutputStream());

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId={}, type={}", modelId, type, e);
        }
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Ajax delete(String modelId) {
    	Ajax ajax = null;
        try {
        	repositoryService.deleteModel(modelId);
        	 ajax = new Ajax(true, "删除成功!");
        } catch (Exception e) {
       	 	ajax = new Ajax(false, "出现异常:" + e.getMessage());
       	 	logger.error("根据模型部署流程失败：modelId={}", modelId, e);
       }
       return ajax;
    }

    
    @RequestMapping(value = "assignee")
    @ResponseBody
    public List<Node> assignee() {
    	return workflowDefinitionService.assignee();
    }
    @RequestMapping(value = "candidateUsers")
    @ResponseBody
    public List<Node> candidateUsers() {
    	return workflowDefinitionService.candidateUsers();
    }
    @RequestMapping(value = "candidateGroups")
    @ResponseBody
    public List<Node> candidateGroups() {
    	return workflowDefinitionService.candidateGroups();
    }
}
