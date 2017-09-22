<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<head>
	<title>公告信息管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<%@ include file="/WEB-INF/views/include/kindeditor.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/plat/platInformation/updatePlat",
				        dataType : "json",
				        timeout : 180000,
				        beforeSubmit : function() {
				        	//alert('bbb')
				        },
				        success : function(result, status) {
				        	if(result.success == true) {
				        	   window.location.href ="${ctx}/plat/platInformation/list";
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
		
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="content"]', {
				cssPath : '${ctx}/static/kindeditor/plugins/code/prettify.css',
				uploadJson : '${ctx}/static/kindeditor/jsp/upload_json.jsp',
				fileManagerJson : '${ctx}/static/kindeditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterBlur: function () { this.sync(); }
			});
			prettyPrint();
		});
	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/plat/platInformation/list">资讯列表</a></li>
		<li class="active"><a href="${ctx}/plat/platInformation/editForm?id=${pit.id}">资讯编辑</a></li>
	</ul>
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<input type="hidden" name="isNewRecord" value="true"/>
		<div class="control-group">
		<input name="id" value="${pit.id}" type="hidden" class="input-xlarge required"/>
			<label class="control-label">标题：</label>
			<div class="controls">
				<input type="text" name="title" value="${pit.title}"  class="input-xxlarge required"/>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">公告内容：</label>
			<div class="controls">
				<textarea name="content" rows="4" style="width:700px;height:200px;visibility:hidden;display: block;">${pit.content}</textarea>
			</div>
		</div>
		
		
		<!-- 
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<input name="ext" value="${noticeInfo.ext}"  class="input-xlarge "/>
			</div>
		</div>
		 -->
		<div class="form-actions">
			<table cellspacing="0" cellpadding="10" border="0">
			<tr><td><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/></td>
			<td><input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/></td></tr>
			</table>
		</div>
	</form>
</body>
</html>