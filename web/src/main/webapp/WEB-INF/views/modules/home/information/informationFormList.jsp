<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>公告信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
			function infor(){
				window.location.href = "${ctx}/plat/platInformation/load"; 
				

			}
	</script>
</head>
<body>
	<!--
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/notice/noticeInfo/list">公告信息列表</a></li>
		<li class="active"><a href="${ctx}/notice/noticeInfo/form?id=${noticeInfo.id}">公告信息修改</a></li>
	</ul><br/> -->
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" value="${platInformation.id}"/>
		<div class="control-group" align="center">
			<label class="control-label">${platInformation.title}</label>
		</div>
		
		
		
		<div class="control-group" align="center">
			<div class="controls" align="center">
				<label/>
				<label class="control-label" /> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				${platInformation.releaseDate}
				</label>
			</div>
		</div>
		<br>
		<div>
			${platInformation.content}
			
		</div>
		<div class="form-actions">
			<table cellspacing="0" cellpadding="10" border="0" align="center">
			<tr>
			<td> 
			
			
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="javascript:history.go(-1);"/>
			
			 
			</td></tr>
			</table>
		</div>
	</form>
</body>
</html>