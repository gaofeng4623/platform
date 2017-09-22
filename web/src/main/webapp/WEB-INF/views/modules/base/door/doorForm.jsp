<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#inputForm").validate({
				rules: {
					doorId: {remote: "${ctx}/base/door/checkDoorId?oldDoorId=" + encodeURIComponent('${door.doorId}')},
					ip: {ip:true}
				},
				messages: {
					doorId: {remote: "门禁编号已存在"},
					ip: {}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
	<form id="inputForm" modelAttribute="door" action="${ctx}/base/door/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">门禁编号:</label>
			<div class="controls">
				<input path="doorId" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门禁名称:</label>
			<div class="controls">
               	<form:input path="note" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门禁类型:</label>
			<div class="controls">
               <!-- <select id="doorType" name="doorType" style="width:206px" >
					<option value="">全部</option>
					<option value="1">大门</option>
					<option value="0">非大门</option>
				</select> -->
				<form:select path="doorType" items="${doorTypeMap}" class="input-large"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门禁地址IP:</label>
			<div class="controls">
				<form:input path="ip" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<c:if test="${not empty door.id}">
			<div class="control-group">
				<label class="control-label">创建人:</label>
				<div class="controls">
					<label class="lbl">${door.createBy.name}</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${door.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>

		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="base:door:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>