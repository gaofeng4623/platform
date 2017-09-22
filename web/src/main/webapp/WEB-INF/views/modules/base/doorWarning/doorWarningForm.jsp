<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>门禁报警管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/base/doorWarning/save2",
				        dataType : "json",
				        timeout : 180000,
				        beforeSubmit : function() {
				        	//alert('bbb')
				        },
				        success : function(result, status) {
				        	if(result.success == true) {
				        		alertx(result.msg);
			 	            } else {
			 	               alertx(result.msg);
			 	            }
				        },
				        error : function() {
				        	alertx('出现错误，稍后再试！');
				        }
			        });
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/base/doorWarning/list">门禁报警列表</a></li>
		<li class="active"><a href="${ctx}/base/doorWarning/form?id=${doorWarning.id}">门禁报警<shiro:hasPermission name="base:doorWarning:edit">${not empty doorWarning.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="base:doorWarning:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" value="${doorWarning.id} "/>
		<div class="control-group">
			<label class="control-label">分行ID：</label>
			<div class="controls">
				<input name="branchId" value="${doorWarning.branchId}"  maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门禁号：</label>
			<div class="controls">
				<input name="doorId" value="${doorWarning.doorId}"  maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子标签号：</label>
			<div class="controls">
				<input name="rfid" value="${doorWarning.rfid}"  maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警时间：</label>
			<div class="controls">
				<input name="warnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${doorWarning.warnDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警原因：</label>
			<div class="controls">
				<input name="warnReason" value="${doorWarning.warnReason}"  maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理人：</label>
			<div class="controls">
				<input name="handler" value="${doorWarning.handler}"  maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理结果：</label>
			<div class="controls">
				<input name="handleResult" value="${doorWarning.handleResult}"  maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">handledate：</label>
			<div class="controls">
				<input name="handleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${doorWarning.handleDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="base:doorWarning:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>