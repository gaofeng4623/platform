<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
<title>安全报警信息</title>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
<style>
.leftDiv{
	float:left;
	border:1px solid #aaa;
	width:25%;
	height:30px;
	text-align:right;
	line-height:30px;
	font-size:12px;
	padding-right: 15px;
}
.rightDiv{
	float:left;
	border:1px solid #aaa;
	border-left:0;
	width:70%;
	height:30px;
	text-align:left;
	line-height:30px;
	font-size:12px;
	padding-left: 15px;
}
</style>
<script type="text/javascript">
</script>
</head>
<body>
<div style="width:100%;text-align: center;margin: auto;">
	<div align="right" style="padding: 15px;">
		<input id="btnCancel" class="btn" type="button" value="返回" onclick="javascript:history.go(-1);"/>
	</div>
	<div style="width:80%;margin: auto;">
		<div style="margin:15px 0 20px 0;font-size:25px;">安全报警信息</div>
		<div style="display:none;"><input type="text" id="checkDocId" value="<%-- ${dataInfo.id} --%>"/></div>
		<div class="leftDiv">报警时间</div><div class="rightDiv">${dataInfo.createTime}</div>
		<div class="leftDiv">报警设备</div><div class="rightDiv">${dataInfo.alarmDeviceName}</div>
		<div class="leftDiv">报警类型</div><div class="rightDiv">${dataInfo.alarmTypeName}</div>
		<div class="leftDiv">最后报警时间</div><div class="rightDiv">${dataInfo.lastAlarmTime}</div>
		<div class="leftDiv">异常发生地</div><div class="rightDiv">${dataInfo.roomName}</div>
		<div class="leftDiv">目前状态</div><div class="rightDiv">${dataInfo.mention}</div>
		<div class="leftDiv">报警级别</div><div class="rightDiv">${dataInfo.grades}</div>
		<div class="leftDiv">处理时间</div><div class="rightDiv">${dataInfo.dealTime}</div>
		<div class="leftDiv">应急预案</div><div class="rightDiv">${dataInfo.linkageAction}</div>
		<div style="height:0px;clear:both;"></div>
	</div>
</div>
</body>
</html>