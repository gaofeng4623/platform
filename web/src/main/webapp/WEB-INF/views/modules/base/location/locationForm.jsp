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
					if('0' == '${location.parent.id}'){
		        		alertx("当前父节点不能为根节点！");
		        		return ;
		        	}else{
		        		$(form).ajaxSubmit({
				        	type : "post",
					        url : "${ctx}/base/location/save",
					        dataType : "json",
					        timeout : 180000,
					        beforeSubmit : function() {
					        	
					        },
					        success : function(result, status) {
					        	if(result.success == true) {
					        		alertx(result.msg);
					        		//location.href ="${ctx}/base/location/list?id=${location.parent.id}";
				 	            } else {
				 	               alertx(result.msg);
				 	            }
					        },
					        error : function() {
					        	alertx('出现错误，稍后再试！');
					        }
				        });
		        	}
					
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
		
		function writeRfid(){
			alertx('${location.id}');
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/base/location/list">位置列表</a></li>
		<li class="active"><a href="${ctx}/base/location/form?id=${location.id}">位置<shiro:hasPermission name="base:location:edit">${not empty location.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="base:location:edit">查看</shiro:lacksPermission></a></li>
		<shiro:hasPermission name="base:location:edit"><li><a href="${ctx}/base/location/deptForm?parent.id=0">档案中心添加</a></li></shiro:hasPermission>
	</ul><br/>
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" value="${location.id} "/>
		<input type="hidden" name="parent.id" value="${location.parent.id} "/>
		<div class="control-group">
			<span style="font-size: 14px;font-weight: bold;padding-left: 50px">当前父节点为：${parent.locationName}</span><br></br>
		</div>
		<div class="control-group">
			<label class="control-label">位置名称：</label>
			<div class="controls">
				<input name="locationName" value="${location.locationName}"  maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<shiro:hasPermission name="base:location:edit">
		<c:if test="${location.locationType == 8 || location.locationType == 9}">
			<div class="control-group">
				<label class="control-label">电子标签：</label>
				<div class="controls">
					<input name="rfid" value="${location.rfid}"  maxlength="50" class="input-xlarge "/>
					<input id="btnWrite" class="btn btn-primary" type="button" value="写入标签" onclick="writeRfid()"/>
				</div>
			</div>
		</c:if>
		</shiro:hasPermission>
		<div class="control-group">
			<label class="control-label">位置类型：</label>
			<div class="controls">
				<select name="locationType" class="input-medium ">
					<c:forEach items="${fns:getDictList('location_type')}" var="location_type">
						<option value="${location_type.value}" <c:if test='${location_type.value == location.locationType}'> selected='selected'  </c:if>>${location_type.label}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">存放档案类型：</label>
			<div class="controls">
				<select name="fileType" class="input-medium " maxlength="1">
					<option value="" <c:if test='${"" eq location.fileType}'> selected='selected'  </c:if>>--请选择--</option>
					<option value="1" <c:if test='${"1" eq location.fileType}'> selected='selected'  </c:if>>要件</option>
					<option value="0" <c:if test='${"0" eq location.fileType}'> selected='selected'  </c:if>>权证</option>
				</select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="base:location:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>