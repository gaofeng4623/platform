<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html">
<head>
<title>安全异常信息管理</title>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
<script type="text/javascript">
$(function(){
	searchForm();
});
function searchForm() {
	var roomId = $("#roomId").val();
	getAjaxView(roomId);
}
function getAjaxView(roomId){
	
	//安全报警---今日温度变化
	$.getJSON('${ctx}/platAlarm/getTodayT',{roomId:roomId}, function (data) {
        $('#alarmView1').highcharts({
            chart: { zoomType: 'x' },
            title: { text: '今日温度曲线', align:'left' },
            subtitle: { text: document.ontouchstart === undefined ? '鼠标拖动可以进行缩放' : '手势操作进行缩放' },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: {
                    millisecond: '%H:%M:%S.%L',
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%m-%d',
                    week: '%m-%d',
                    month: '%Y-%m',
                    year: '%Y'
                }
            },
            tooltip: {
                dateTimeLabelFormats: {
                    millisecond: '%H:%M:%S.%L',
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%Y-%m-%d',
                    week: '%m-%d',
                    month: '%Y-%m',
                    year: '%Y'
                }
            },
            yAxis: { title: { text: '摄氏温度' } },
            legend: { enabled: false },
            plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        ]
                    },
                    marker: { radius: 1 },
                    lineWidth: 1,
                    states: { hover: { lineWidth: 1 } },
                    threshold: null
                }
            },
            series: [{
                type: 'area',
                name: '摄氏温度',
                data: data.data
            }]
        });
    });

	//安全报警---今日湿度度曲线
	$.getJSON('${ctx}/platAlarm/getTodayH', {roomId:roomId},function (data) {
        $('#alarmView4').highcharts({
            chart: { zoomType: 'x' },
            title: { text: '今日湿度度曲线', align:'left' },
            subtitle: { text: document.ontouchstart === undefined ? '鼠标拖动可以进行缩放' : '手势操作进行缩放' },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: {
                    millisecond: '%H:%M:%S.%L',
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%m-%d',
                    week: '%m-%d',
                    month: '%Y-%m',
                    year: '%Y'
                }
            },
            tooltip: {
                dateTimeLabelFormats: {
                    millisecond: '%H:%M:%S.%L',
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%Y-%m-%d',
                    week: '%m-%d',
                    month: '%Y-%m',
                    year: '%Y'
                }
            },
            yAxis: { title: { text: '湿度%RH' } },
            legend: { enabled: false },
            plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        ]
                    },
                    marker: { radius: 2 },
                    lineWidth: 1,
                    states: { hover: { lineWidth: 1 } },
                    threshold: null
                }
            },
            series: [{
                type: 'area',
                name: '湿度',
                data: data.data
            }]
        });
    });
	
	//安全报警---温度调节时长统计图
	$.ajax({
		url:"${ctx}/platAlarm/getRunTime",
		data:{roomId:roomId},
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('alarmView2', {
	    		credits: { enabled: false},
	    	    chart: { type: 'spline', inverted: false },
	    	    title: { text: '温度调节时长', align:'left' },
	    	    xAxis: { title: { enabled: true, text: '最近30天数据' } },
	    	    yAxis: { title: { enabled: false } },
	    	    legend: { enabled: false },
	    	    tooltip: { headerFormat: '<b>{series.name}</b><br/>', pointFormat: '{point.x}日: {point.y}小时' },
	    	    series: [{
	    	        name: '温度调节时长',
	    	        data:resultData.data
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	
	//安全报警---湿度调节时长统计
	$.ajax({
		url:"${ctx}/platAlarm/getHumidityCtrl",
		data:{roomId:roomId},
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('alarmView5', {
	    		credits: { enabled: false},
	    	    chart: { type: 'column', inverted: false },
	    	    title: { text: '干湿度调节时长', align:'left' },
	    	    xAxis: { title: { enabled: true, text: '最近30天数据，正数表示加湿，负数表示除湿' } },
	    	    yAxis: { title: { enabled: false } },
	    	    legend: { enabled: false },
	    	    tooltip: { headerFormat: '<b>{series.name}</b><br/>', pointFormat: '{point.x}日: {point.y}小时' },
	    	    series: [{
	    	        name: '干湿度调节时长',
	    	        data:resultData.data
	    	        /* zones: [{ value: -8, color: '#ff0000' },{value: 8},{ color: '#ff0000' }] */
	    	    }]
	    	});
	    },
	    error:function(){}
	});

	//安全报警---历年今日空调运行时长
	$.ajax({
		url:"${ctx}/platAlarm/getOldRunTime",
		data:{roomId:roomId},
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('alarmView3', {
	    		credits: { enabled: false},
	    	    chart: { backgroundColor: '#ffffff', type: 'spline' },
	    	    title: { text: '历年同时期空调运行时长', align:'left'},
	    	    xAxis: { categories:resultData.categories },
	    	    yAxis: { title: {enabled: false}, plotLines:[{ color:'red', dashStyle:'longdashdot', value:8, width:2 }] },
	    	    legend: { align: 'right', x: -30, verticalAlign: 'top', y: 0, floating: true },
	    	    tooltip: { crosshairs: true, shared: true },
	    	    plotOptions: { spline: { marker: { radius: 4, lineColor: '#666666', lineWidth: 1 } } },
	    	    series:[{
	    	        name: "今日", data: resultData.data1
	    	    },{
	    	        name: "明日", data: resultData.data2
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	
	//安全报警---历年今日湿度调节时长
	$.ajax({
		url:"${ctx}/platAlarm/getOldHumidityCtrl",
		data:{roomId:roomId},
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('alarmView6', {
	    		credits: { enabled: false},
	    	    chart: { backgroundColor: '#ffffff', type: 'spline' },
	    	    title: { text: '历年今日湿度调节时长', align:'left'},
	    	    xAxis: { categories:resultData.categories },
	    	    yAxis: { title: {enabled: false} },
	    	    legend: { align: 'right', x: -30, verticalAlign: 'top', y: 0, floating: true },
	    	    tooltip: { crosshairs: true, shared: true },
	    	    plotOptions: { spline: { marker: { radius: 4, lineColor: '#666666', lineWidth: 1 } } },
	    	    series:[{
	    	        name: "今日", data: resultData.data1
	    	    },{
	    	        name: "明日", data: resultData.data2
    	        	/* zoneAxis:"x", zones: [{ value: 3}, { dashStyle: 'ShortDash' }] */
	    	    }]
	    	});
	    },
	    error:function(){}
	});

	//安全报警---报警次数按月统计图
	$.ajax({
		url:"${ctx}/platAlarm/getAlarmCountByMonth",
		data:{roomId:roomId},
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('alarmView7', {
	    		credits: { enabled: false},
	    	    chart: { type: 'column' },
	    	    title: { text: '本年度报警次数按月统计图', align:'left' },
	    	    xAxis: { categories:resultData.categories },
	    	    yAxis: {
	    	        min: 0, title: { enabled: false },
	    	        stackLabels: { enabled: true}
	    	    },
	    	    tooltip: { crosshairs: true, shared: true },
	    	    plotOptions: { column: { stacking: 'normal' } },
	    	    series:[{
	    	        name: "严重", data: resultData.data1
	    	    },{
	    	        name: "一般", data: resultData.data2,
	    	    },{
	    	        name: "轻度", data: resultData.data3,
	    	    }]
	    	});
	    },
	    error:function(){}
	});

	//安全报警---历年同月安全报警次数统计
	$.ajax({
		url:"${ctx}/platAlarm/getAlarmCountByOldMonth",
		data:{roomId:roomId},
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('alarmView8', {
	    		credits: { enabled: false},
	    	    chart: { type: 'column' },
	    	    title: { text: '历年同月安全报警次数统计', align:'left' },
	    	    xAxis: { categories:resultData.categories },
	    	    yAxis: {
	    	        min: 0, title: { enabled: false },
	    	        stackLabels: { enabled: true }
	    	    },
	    	    tooltip: { crosshairs: true, shared: true },
	    	    plotOptions: {
	    	        column: {
	    	            stacking: 'normal',
	    	            dataLabels: { enabled: true, color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white' }
	    	        }
	    	    },
	    	    series:[{
	    	        name: "严重", data: resultData.data1
	    	    },{
	    	        name: "一般", data: resultData.data2,
	    	    },{
	    	        name: "轻度", data: resultData.data3,
	    	    }]
	    	});
	    },
	    error:function(){}
	});
}
</script>
<style type="text/css">
	.row-fluid {
		padding-top: 10px;
	}
	.container-fluid {
	    padding-right: 5px;
	    padding-left: 5px;
	}
</style>
</head>
<body>
	<form class="breadcrumb form-search row-fluid" style="position:fixed;z-index:99;background-color: #ddd;">
		<ul class="ul-form">
			<li><label>地点</label>
				 <select name="roomId" id="roomId" class="input-medium" style="width:220px;">
			        <c:forEach items="${roomInfo}" var="item">
			            <option value="${item.fId}">${item.fName}</option>
			        </c:forEach>
			    </select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查 询" onclick="searchForm()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form>
	<div class="container-fluid" style="border: 1px solid #ddd;background-color: #ddd;">
	<div class="row-fluid" style="padding-top: 60px;">
		<div class="span12" >
			<div id="alarmView1" style="height: 300px;"></div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6" >
			<div id="alarmView2" style="height: 300px;"></div>
		</div>
		<div class="span6" >
			<div id="alarmView3" style="height: 300px;"></div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div id="alarmView4" style="height: 300px;"></div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div id="alarmView5" style="height: 300px;"></div>
		</div>
		<div class="span6">
			<div id="alarmView6" style="height: 300px;"></div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div id="alarmView7" style="height: 300px;"></div>
		</div>
		<div class="span6">
			<div id="alarmView8" style="height: 300px;"></div>
		</div>
	</div>
	</div>
</body>
</html>