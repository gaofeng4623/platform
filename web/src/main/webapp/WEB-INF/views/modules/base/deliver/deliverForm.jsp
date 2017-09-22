<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>门禁管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
			    onfocusout: function(element) { $(element).valid(); },
				rules: {
                    workMachIp: {ip:true,remote:"${ctx}/base/deliver/checkExists?textType=workIp&id=${deliver.id}"},
                    deliverMachIp: {ip:true,remote:"${ctx}/base/deliver/checkExists?textType=deliverIp&id=${deliver.id}"}
				},
				messages: {
                    workMachIp: {remote: "工作机IP已存在"},
                    deliverMachIp: {remote: "交付机IP已存在"}
				},
				submitHandler: function(form){;
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

			changeName();
		});
        function changeName() {
            if ('${deliver.id}') {
                $("#saveOrEdit").text("配置修改");
            } else {
                $("#saveOrEdit").text("配置添加");
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/base/deliver/list">配置列表</a></li>
		<li class="active"><a href="${ctx}/base/deliver/form"><span id="saveOrEdit">配置添加</span></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="deliver" action="${ctx}/base/deliver/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="branchId"/>
		<div class="control-group">
			<label class="control-label">工作机IP:</label>
			<div class="controls">
				<form:input path="workMachIp" htmlEscape="false" maxlength="15" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交付机IP:</label>
			<div class="controls">
               	<form:input path="deliverMachIp" htmlEscape="false" maxlength="15" class="required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="base:deliver:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>