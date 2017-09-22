<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>添加收藏</title>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputsaveSCForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/platfavoritesitem/platFavoritesItem/saveSC",
				        dataType : "json",
				        timeout : 180000,
				        beforeSubmit : function() {
				        },
				        success : function(result, status) {
				        	if(result.success == true) {
				        		alertx(result.msg);
				        		$.jBox.close(true);
				        		
			 	            } else {
			 	            	alertx(result.msg);
			 	            }
				        },
				        error : function(result, status) {
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
	<form id="inputsaveSCForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" value="${id }" name="linkid"/>
		<input type="hidden" value="${type }" name="type"/>
		<table id="show_table" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr style="height: 50px;">
				<td align="right">收藏夹：</td>
				<td>
					<form:select path="favoritesItem.favoritesid" cssStyle="width: 150px">
						<option value="002">请选择</option>
						<form:options items="${map}"/>
					</form:select>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr style="height: 50px;">
				<td colspan="4" style="text-align: center;">
				<br>
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>