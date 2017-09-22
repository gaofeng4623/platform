<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>工作进度跟踪管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
	<script type="text/javascript">
	$(function(){
		$.ajax({
			url:"${ctx}/workRate/getWorkRateInfoForm4",
			data:{
				workType : '${workRate.workType}',
				deptName : '${workRate.deptName}'  //里面保存了String的日期
			},
		    type: "post",
	        dataType: "json",
		    success:function(resultData){
    	    	Highcharts.chart('workRateCharts', {
    	    		credits: { enabled: false},
    	    	    chart: { backgroundColor: '#ffffff', type: 'column' },
    	    	    title: { text: '档案'+resultData.workRate.workType+'统计', align:'left'},
    	    	    xAxis: { categories:resultData.categories },
    	    	    yAxis: { title: {enabled: false} },
    	    	    legend: { align: 'right', x: -30, verticalAlign: 'top', y: 0, floating: true },
    	    	    tooltip: { crosshairs: true, shared: true },
    	    	    plotOptions: { spline: { marker: { radius: 4, lineColor: '#666666', lineWidth: 1 } } },
    	    	    series:[{
    	                name: resultData.workRate.workType,
    	                data: resultData.returnList
    	            }]
    	    	});
    	    },
		    error:function(){}
		});
	});
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