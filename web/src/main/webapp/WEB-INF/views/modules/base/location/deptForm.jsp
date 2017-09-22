<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>位置管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/base/location/saveDept",
				        dataType : "json",
				        timeout : 180000,
				        beforeSubmit : function() {
				        	//alert('bbb')
				        },
				        success : function(result, status) {
				        	if(result.success == true) {
				        		alertx(result.msg);
				        		location.href ="${ctx}/base/location/list?id=${location.parent.id}";
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
		<li><a href="${ctx}/base/location/list">位置列表</a></li>
		<shiro:hasPermission name="base:location:edit"><li><a href="${ctx}/base/location/form?parent.id=${location.id}">位置添加</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/base/location/deptForm?parent.id=0">档案中心<shiro:hasPermission name="base:location:edit">${not empty location.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="base:location:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" value="${location.id} "/>
		<input type="hidden" name="parent.id" value="${location.parent.id} "/>
		
		<div class="control-group">
			<label class="control-label">位置名称：</label>
			<div class="controls">
				<input name="locationName" value="${location.locationName}"  maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="base:location:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>