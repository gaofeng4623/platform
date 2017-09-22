<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highstock.js"></script>
	<script src="${ctx}/static/plat/highCharts/highcharts-more.js"></script>
	<script src="${ctx}/static/plat/d3/d3.min.js"></script>
	<script src="${ctx}/static/plat/d3/venn.js"></script>
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
      <div class="row-fluid" >
      		<div id="useModeDetail1" class="span12" style="height: 240px;"></div>
      </div>
      <div class="row-fluid" >
      		<div id="useModeDetail2" class="span12" style="height: 260px;"></div>
      </div>
      <div class="row-fluid" >
      		<div id="useModeDetail3" class="span12" style="height: 200px;"></div>
      </div>
      <div class="row-fluid" >
      		<div id="useModeDetail4" class="span12" style="height: 200px;"></div>
      </div>
</div>
 
<script type="text/javascript">
$(function(){

	/**
	 * 利用方式明细----显示最近12个月档案利用数量----折线图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getUseModeStaticsByMonths?useMode=${dict.value}",
	    type: "post",
        dataType: "json",
	    success:function(result){
	    	$('#useModeDetail1').highcharts({
	    		credits: { enabled: false},
	    	    title: { text: '月利用档案统计' ,align:'left'},
	    	    subtitle: { text: '' },
	    	    xAxis: {
	    	    	categories:result.categories
	    	    },
	    	    legend: {
	    	    	enabled: false,
	                align: 'right',
	                verticalAlign: 'top'
	            },
	    	    yAxis: { min: 0, title: {  text: '卷' } },
	    	    tooltip: { enabled: false },
	    	    plotOptions: { spline: { marker: { radius: 4, lineColor: '#666666', lineWidth: 1 },dataLabels:{ enabled:true } } },
	    	    series:[{
	    	    	 name: '月利用档案量',
		    	    	type:"column",
		    	    	data:result.columnData
	    	    	},{
	    	    		name: '月利用档案量',
		    	    	type:"spline",
		    	    	data:result.splineData
	    	    	}]
	    	});
	    },
	    error:function(){}
	});
	
	 /**
	 * 利用方式明细----显示档案利用全宗分布----柱状图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getUseModeStaticsByUnit?useMode=${dict.value}",
	    type: "post",
        dataType: "json",
	    success:function(result){
	    	$('#useModeDetail2').highcharts({
	    		credits: { enabled: false},
	    	    chart: { type: 'column' },
	    	    title: { text: '按档案全宗统计' ,align:'left'},
	    	    subtitle: { text: '' },
	    	    xAxis: {
	    	    	max: 20,
	    	    	categories:result.categories
	    	    },
	    	    legend: {
	                align: 'right',
	                verticalAlign: 'top'
	            },
	            scrollbar: {enabled:true},
	    	    yAxis: { min: 0, title: {  text: '卷' } },
	    	    tooltip: { crosshairs: true, shared: true },
	    	    plotOptions: { spline: { dataLabels:{ enabled:true } } },
	    	    series:[{
	    	    	name: '档案全宗',
	       	        data: result.data
	       	    }]
	    	});
	    },
	    error:function(){}
	});
	
	 /**
	 * 利用方式明细----显示按档案类型统计利用量----折线图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getUseModeStaticsByArchiveType?useMode=${dict.value}",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	$('#useModeDetail3').highcharts({
	    		credits: { enabled: false},
	    	    chart: { type: 'spline' },
	    	    title: { text: '按档案类型统计',align:'left' },
	    	    subtitle: { text: '' },
	    	    legend: {
	                align: 'right',
	                verticalAlign: 'top'
	            },
	    	    xAxis: {
	    	        categories: resultData.categories,
	    	        crosshair: true
	    	    },
	    	    yAxis: { min: 0, title: {  text: '卷' } },
	    	    tooltip: { crosshairs: true, shared: true },
	    	    plotOptions: { spline: { dataLabels: {enabled:true } } },
	    	    series:[{
	    	    	name: '档案类型',
	    	    	data: resultData.data
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	 
	 /**
	 * 利用方式明细----显示利用主体分布----饼图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getUseModeStaticsByUser?useMode=${dict.value}",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('useModeDetail4', {
	    		credits: {enabled: false},
	    	    chart: { backgroundColor: '#ffffff', type: 'pie'},
	    	    title: {text: '利用主体分布',align:'left'},
	    	    subtitle: {text: ''},
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true, cursor: 'pointer',
	                    dataLabels: {
	                        enabled: false,
	                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                        style: {
	                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                        }
	                    },
                        showInLegend: true
	                }
	            },
	            legend: { 
	    			layout: 'vertical',
	                align: 'right',
	                verticalAlign: 'top',
	                floating: true,
	                navigation: {
	                    activeColor: '#3E576F',
	                    animation: true,
	                    arrowSize: 12,
	                    inactiveColor: '#CCC',
	                    style: {
	                        fontWeight: 'bold',
	                        color: '#333',
	                        fontSize: '12px'
	                    }
	                }
	            },
	    	    tooltip: {
	    	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	    	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b>'
	    	    },
	    	    series: [{
	    	        name: '利用主体',
	    	        colorByPoint: true,
	    	        data:resultData.data
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	 
	
});
</script>
</body>
</html>