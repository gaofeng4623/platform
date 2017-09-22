<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>公告信息管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		function shoucang(id){
			$.jBox.open("get:${ctx}/platfavoritesitem/platFavoritesItem/formSC?id="+id+"&type=2", "加入收藏", 330, 150,{top:'20%',left:'0%',zIndex:2500,buttons: {  },closed:function(){window.location.href=window.location.href;} });
		}
// 		function go(){
// 			window.location.href="${ctx}/plat/platInformation/informationForm?title=${title}";
// 		}
	</script>
</head>
<body>
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" value="${platInformation.id}"/>
		<label style="color:red;">
			<c:if test="${not empty deletInfo}">${deletInfo}</c:if>
		</label>
		<div align="right">
		<%if((Boolean)request.getAttribute("needsc")){ %>
		<input type="button" class="btn" value="收藏" onclick="shoucang('${platInformation.id}')">
		<% }%>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="javascript:history.go(-1);"/>
		</div>
		<div class="control-group" align="center">
			<label style="font-size: 16px;font-weight: bold;">${platInformation.title}</label>
		</div>
		<div class="control-group" align="center">
			<label> 
				${platInformation.releaseDate}
			</label>
		</div>
		<div>
			${platInformation.content}
		</div>
	</form>
</body>
</html>