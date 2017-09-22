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
	    <div id="transferDetail1" class="span12" style="height: 240px;"></div>
    </div>
    <div class="row-fluid" >
	    <div id="transferDetail2" class="span12" style="height: 240px;"></div>
    </div>
    <div class="row-fluid" >
    	<div id="transferDetail3" class="span6" style="height: 240px;"></div>
	    <div id="transferDetail4" class="span6" style="height: 240px;"></div>
    </div>
</div>
<script type="text/javascript">
$(function(){

	/**
	 * 移交明细----显示移交量全宗分布----柱状图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getTransferStaticsByUnit?category=${category}",
	    type: "post",
        dataType: "json",
	    success:function(result){
	    	$('#transferDetail1').highcharts({
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
	 * 移交明细----显示按档案类型统计移交量----折线图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getTransferStaticsByArchiveType?category=${category}",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	$('#transferDetail2').highcharts({
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
	 * 移交明细----显示移交保管期限分布----饼图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getTransferStaticsByPeriod?category=${category}&ySum=${ySum}",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('transferDetail3', {
	    		credits: {enabled: false},
	    	    chart: { backgroundColor: '#ffffff', type: 'pie'},
	    	    title: {text: '按保管期限统计',align:'left'},
	    	    subtitle: {text: ''},
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true, cursor: 'pointer',
	                    dataLabels: {
	                        enabled: true,
	                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                        style: {
	                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                        }
	                    },
	                    showInLegend: true
	                }
	            },
	    	    tooltip: {
	    	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	    	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b>'
	    	    },
	    	    series: [{
	    	        name: '保管期限',
	    	        colorByPoint: true,
	    	        data:resultData.data
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	 
	 /**
	 * 移交明细----显示移交交接性质分布----饼图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getTransferStaticsByType?category=${category}",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('transferDetail4', {
	    		credits: {enabled: false},
	    	    chart: { backgroundColor: '#ffffff', type: 'pie'},
	    	    title: {text: '按交接性质统计',align:'left'},
	    	    subtitle: {text: ''},
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true, cursor: 'pointer',
	                    dataLabels: {
	                        enabled: true,
	                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                        style: {
	                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                        }
	                    },
	                    showInLegend: true
	                }
	            },
	    	    tooltip: {
	    	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	    	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b>'
	    	    },
	    	    series: [{
	    	        name: '交接性质',
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