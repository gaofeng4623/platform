<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>字典管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					//alert('')
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/sys/example/save",
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
		<li><a href="${ctx}/sys/example/list">例子列表</a></li>
		<li class="active">
			<a href="${ctx}/sys/example/form?id=${example.id}">例子
			<shiro:hasPermission name="sys:example:edit">${not empty example.id?'修改':'添加'}</shiro:hasPermission>
			<shiro:lacksPermission name="sys:example:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form id="inputForm" class="form-horizontal">
		<input type="hidden" name="id"/>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<input type="text" name="name" maxlength="50" class="required" value="${example.name }"/>
				<span class="help-inline"><font color="red">*</font>填写名字 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄:</label>
			<div class="controls">
				<input type="text" name="age" maxlength="50" class="required" value="${example.age }" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生日:</label>
			<div class="controls">
				<input name="birthDay" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${example.birthDay}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
				<select name="type" class="input-xlarge">
					<option value="">请选择</option>
					<c:forEach items="${fns:getDictList('sys_user_type')}" var="item">
						<option value="${item.value}">${item.label}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">复选:</label>
			<div class="controls">
				<c:forEach items="${lists}" var="item">
					<input name="Fruit" type="checkbox" value="${item.id }" />${item.name }
				</c:forEach>
				<span class="help-inline"><font color="red">*</font> 一些说明性文字 </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:example:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
	2列
	<form id="inputForm" class="form-inline">
		<div class="control-group">
			<label class="control-label">名称:</label>
			<input type="text" name="name" maxlength="50" class="required" value="${form.name }"/>
			<span class="help-inline"><font color="red">*</font>填写名称 </span>
			<label class="control-label">说明:</label>
			<input type="text" name="remark"  value="${form.remark }"/>
		</div>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<input type="text" name="name" maxlength="50" class="required" value="${form.name }"/>
			<span class="help-inline"><font color="red">*</font>填写名称 </span>
			<label class="control-label">说明:</label>
			<input type="text" name="remark"  value="${form.remark }"/>
		</div>
	</form>
</body>
</html>