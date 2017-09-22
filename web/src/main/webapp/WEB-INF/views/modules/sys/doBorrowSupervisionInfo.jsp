<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>档案审核</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
	<script type="text/javascript">
	$(function(){
		var src= "${hrefURL}";
		$('#docCheckInfoFrame1').attr('src',src);
	});
	</script>
	<style type="text/css">
		html,body {
			height: 100%;
		}
	</style>
</head>
<body>
	<div align="right" style="padding: 15px;">
		<input id="btnCancel" class="btn" type="button" value="返回" onclick="javascript:history.go(-1);"/>
	</div>
	<div style="width:99.5%;height:80%;border: 1px solid #ddd;background-color: #ddd;margin: auto;">
		<sys:message content="${message}"/>
		<iframe id="docCheckInfoFrame1" name="docCheckInfoFrame1" src="" min-height="500px" height="100%" scrolling="yes" frameborder="no" width="100%"></iframe>
	</div>
</body>
</html>