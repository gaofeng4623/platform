<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<!DOCTYPE html>
<head>
	<title>收藏条目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/platfavoritesitem/platFavoritesItem/save",
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
		<li><a href="${ctx}/platfavoritesitem/platFavoritesItem/list">收藏条目列表</a></li>
		<li class="active"><a href="${ctx}/platfavoritesitem/platFavoritesItem/form?id=${platFavoritesItem.id}">收藏条目添加</a></li>
	</ul><br/>
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<input type="hidden" name="isNewRecord" value="true"/>
		<div class="control-group">
			<label class="control-label">收藏夹ID：</label>
			<div class="controls">
				<input name="favoritesid" value="${platFavoritesItem.favoritesid}"  class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">type：</label>
			<div class="controls">
				<input name="type" value="${platFavoritesItem.type}"  class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">linkid：</label>
			<div class="controls">
				<input name="linkid" value="${platFavoritesItem.linkid}"  class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">title：</label>
			<div class="controls">
				<input name="title" value="${platFavoritesItem.title}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks：</label>
			<div class="controls">
				<textarea name="remarks" rows="4" class="input-xxlarge ">${platFavoritesItem.remarks}</textarea>
			</div>
		</div>
		<div class="form-actions">
			<table cellspacing="0" cellpadding="10" border="0">
			<tr><td><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/></td>
			<td><input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/></td></tr>
			</table>
		</div>
	</form>
</body>
</html>