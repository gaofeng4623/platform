<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
				        	   $.jBox.close(true);
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
	<!--
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/platfavoritesitem/platFavoritesItem/list">收藏条目列表</a></li>
		<li class="active"><a href="${ctx}/platfavoritesitem/platFavoritesItem/form?id=${platFavoritesItem.id}">收藏条目修改</a></li>
	</ul><br/> -->
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" value="${platFavoritesItem.id}"/>
		<div class="control-group">
			<label class="control-label">收藏夹：</label>
			<div class="controls">
			      <select name="favoritesid" id="favoritesid">
			        <option value="002" <c:if test="${'002'==platFavoritesItem.favoritesid}">selected=selected</c:if> >默认收藏夹</option>
			        <c:forEach items="${list}" var="item">
			          <option value="${item.id}" <c:if test="${item.id==platFavoritesItem.favoritesid}">selected=selected</c:if> >${item.favName}</option>
			        </c:forEach>
			      </select>
				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display:none">
			<label class="control-label">类型：</label>
			<div class="controls">
				<input name="type"  readonly="readonly" value="${platFavoritesItem.type}"  class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display:none">
			<label class="control-label">关联ID：</label>
			<div class="controls">
				<input name="linkid"  readonly="readonly" value="${platFavoritesItem.linkid}"  class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display:none">
			<label class="control-label">标题：</label>
			<div class="controls">
				<input name="title"  readonly="readonly" value="${platFavoritesItem.title}"  class="input-xlarge "/>
			</div>
		</div>
		<!-- 
		<div class="control-group">
			<label class="control-label">remarks：</label>
			<div class="controls">
				<textarea name="remarks"  readonly="readonly" rows="4" class="input-xxlarge ">${platFavoritesItem.remarks}</textarea>
			</div>
		</div>
		 -->
		<div class="form-actions">
			<table cellspacing="0" cellpadding="10" border="0">
			<tr><td><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/></td>
			<td><input id="btnCancel" class="btn" type="button" value="返 回" onclick="$.jBox.close(true)"/></td></tr>
			</table>
		</div>
	</form>
</body>
</html>