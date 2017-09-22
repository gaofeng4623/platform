<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
	<title>档案利用-网络</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script src="${ctx}/static/jquery/jquery-1.9.1.min.js"></script>
	<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
	<script src="${ctx}/static/plat/highCharts/data.js"></script>
	<script src="${ctx}/static/plat/highCharts/drilldown.js" type="text/JavaScript" charset="UTF-8"></script>
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
	<div class="container-fluid" style="border: 1px solid #ddd;background-color: #ddd;">
		<div class="row-fluid" style="padding-top: 5px;">
			<div class="span12" >
				<div id="chartsDiv1" style="height: 500px;"></div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6" >
				<div id="chartsDiv2" style="height: 400px;"></div>
			</div>
			<div class="span6" >
				<div id="chartsDiv3" style="height: 400px;"></div> 
			</div>
		</div>
	</div>
    
<script type="text/javascript">
$(function(){
	/**
	 * 网络途径档案利用-按地区统计---基础饼图
	 */
	$.ajax({
		url:"${ctx}/platDocUse/getNetDocUseArea",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('chartsDiv3', {
	    		 credits: { enabled: false},
	    	     chart: { plotBorderWidth: null, plotShadow: false, type: 'pie' },
	    	     title: { text: '网络途径档案利用-按地区统计',align: 'left' },
	    	     tooltip: { pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' },
	    	     plotOptions: {
	    	         pie: {
	    	             allowPointSelect: true, cursor: 'pointer',
	    	             dataLabels: {
	    	                 enabled: true, format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	    	                 style: { color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black' }
	    	             }
	    	         }
	    	     },
	    	     series: [{ name: '地区', colorByPoint: true, data:resultData }]
	    	 });
	    },
	    error:function(){}
	});

	/**
	 * 网络途径档案利用-按时间点统计---显示点值的折线图
	 */
	$.ajax({
		url:"${ctx}/platDocUse/getNetDocUseTime",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('chartsDiv2', {
	    		 credits: { enabled: false},
	    	     chart: { type: 'line' },
	    	     title: { text: '网络途径档案利用-按时间点统计',align: 'left' },
	    	     subtitle: { text: '' },
	    	     xAxis: { categories:resultData.categories },
	    	     yAxis: { title: { text: '千人次' } },
	    	     plotOptions: { line: { dataLabels: { enabled: true }, enableMouseTracking: false } },
	    	     series:resultData.data
 			});
	    },
	    error:function(){}
	});

	/**
	 * 网络途径档案利用-按时间统计---可以下钻的柱状图
	 */
	$.ajax({
		url:"${ctx}/platDocUse/getNetDocUseWay",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('chartsDiv1', {
	    		credits: { enabled: false},
	    	    chart: { type: 'column' },
	    	    title: { text: '网络途径档案利用-按月份统计',align: 'left' },
	    	    subtitle: { text: '' },
	    	    xAxis: { type: 'category' },
	    	    yAxis: { title: { text: '万人次' } },
	    	    legend: { enabled: false },
	    	    plotOptions: { series: { borderWidth: 0, dataLabels: { enabled: true, format: '{point.y:.1f}' } } },
	    	    tooltip: {
	    	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	    	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}万人次</b><br/>'
	    	    },
	    	    series: [{ name: '按月份统计', colorByPoint: true, data:resultData.data }],
	    	    drilldown: { series:resultData.drilldown }
	    	});
	    },
	    error:function(){}
	});
});
</script>
</body>
</html>