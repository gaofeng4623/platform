<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>公告信息管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm1").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/notice/noticeInfo/save",
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
		function shoucang(id){
			$.jBox.open("get:${ctx}/platfavoritesitem/platFavoritesItem/formSC?id="+id+"&type=1", "加入收藏", 330, 150,{top:'20%',left:'0%',zIndex:2500,buttons: {  },closed:function(){window.location.href=window.location.href;} });
		}
	</script>
</head>
<body>
	<form id="inputForm1"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<input type="hidden" name="id" value="${noticeInfo.id}"/>
		<c:if test="${not empty deletInfo}">
			<div align="right" style="padding: 15px;">
				<input id="btnCancel" class="btn" type="button" value="返回" onclick="javascript:history.go(-1);"/>
			</div>
			<div style="padding: 15px;color: red;font-size: 16px;">
				该信息已被删除！
			</div>
		</c:if>
		<c:if test="${empty deletInfo}">
			<div align="right" style="padding: 15px;">
			<%if((Boolean)request.getAttribute("needsc")){ %>
				<input class="btn" type="button" value="收藏" onclick="shoucang('${noticeInfo.id}')">
			<% }%>
				<input id="btnCancel" class="btn" type="button" value="返回" onclick="history.go(-1)"/>
			</div>
			<div class="control-group" align="center">
				<label style="font-size: 16px;font-weight: bold;">${noticeInfo.title}</label>
			</div>
			<div class="control-group" align="center">
				<label> 
					<fmt:formatDate value="${noticeInfo.date}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</label>
			</div>
			<div>
				${noticeInfo.content}
			</div>
		</c:if>
	</form>
</body>
</html>