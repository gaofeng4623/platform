<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>工作进度跟踪管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
	<script type="text/javascript">
	</script>
	<style type="text/css">
		html,body {
			height: 100%;
		}
		.control-group {
			margin: 5px;
		}
	</style>
</head>
<body>
	<div align="right" style="padding: 15px;">
		<input id="btnCancel" class="btn" type="button" value="返回" onclick="javascript:history.go(-1);"/>
	</div>
	<div style="width:99.5%;border: 1px solid #ddd;background-color: #ddd;margin: auto;">
		<sys:message content="${message}"/>
		<input type="hidden" name="isNewRecord" value="true"/>
		<form class="form-horizontal">
			<div class="control-group">
				<div class="control-label">工作类型：</div>
				<div class="controls">
					${workRate.workTypeName}
				</div>
			</div>
			<div class="control-group">
				<div class="control-label">工作内容：</div>
				<div class="controls">处理档案【${workRate.workNum}${workRate.workUnit}】</div>
			</div>
			<div class="control-group">
				<div class="control-label">更新时间：</div>
				<div class="controls"><fmt:formatDate value="${workRate.date}" type="date" dateStyle="default"/>
			</div>
		</form>
		<div id="workRateCharts" style="width: 100%;height:300px"></div>
	</div>
</body>
</html>