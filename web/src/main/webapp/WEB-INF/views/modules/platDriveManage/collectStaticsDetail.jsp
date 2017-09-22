<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
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
	<div class="container-fluid"  style="border: 1px solid #ddd;background-color: #ddd;">
		<div class="row-fluid" >
			<div id="collectDetail1" class="span12" style="height: 260px;"></div>
		</div>
		<div class="row-fluid" >
			<div id="collectDetail3" class="span4" style="height: 260px;"></div>
			<div id="collectDetail4" class="span4" style="height: 260px;"></div>
			<div id="collectDetail5" class="span4" style="height: 260px;"></div>
		</div>
	</div>
<script type="text/javascript">
$(function(){

	/**
	 * 征集明细----显示每天档案数量----折线图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getCollectStaticsByDays?category=${category}&type="+encodeURI('${type}'),
	    type: "post",
        dataType: "json",
	    success:function(result){
	    	$('#collectDetail1').highcharts({
	    		credits: { enabled: false},
	    	    chart: { type: 'spline' },
	    	    title: { text: '每天征集档案量' ,align:'left'},
	    	    subtitle: { text: '' },
	    	    xAxis: {
	    	    	categories:result.categories
	    	    },
	    	    legend: {
	                align: 'right',
	                verticalAlign: 'top'
	            },
	    	    yAxis: { min: 0, title: {  text: '卷' } },
	    	    tooltip: { crosshairs: true, shared: true },
	    	    plotOptions: { spline: { marker: { radius: 4, lineColor: '#666666', lineWidth: 1 },dataLabels:{ enabled:true } } },
	    	    series:[{
	       	        name: '${type}',
	       	        data: result.data
	       	    }]
	    	});
	    },
	    error:function(){}
	});
	
	 /**
	 * 征集明细----显示月征集价值量分布----气泡图
	 */
	 /* $.ajax({
		url:"${ctx}/platDriveManage/getCollectCostStatics?category=${category}&type="+encodeURI('${type}'),
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	$('#collectDetail2').highcharts({
	            chart: {
	            	 type: 'bubble',
	                 zoomType: 'xy'
	            },
	            xAxis:{title:{text:'预估价值'}},
	            yAxis:{title:{text:'最终价值'}},
	            title: {
	                text: '月征集价值量分布',align:'left'
	            },
	            legend: {
	                align: 'right',
	                verticalAlign: 'top'
	            },
	            series: [{
	                name:'档案价值',
	                data: resultData.data
	            }]
	        });
	    },
	    error:function(){}
	}); */
	
	 /**
	 * 征集明细----显示征集类型分布----饼图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getCollectTypeStatics?category=${category}&type="+encodeURI('${type}'),
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('collectDetail3', {
	    		credits: {enabled: false},
	    	    chart: { backgroundColor: '#ffffff', type: 'pie'},
	    	    title: {text: '月征集类型分布',align:'left'},
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
	    	    tooltip: {
	    	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	    	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b>'
	    	    },
	    	    series: [{
	    	        name: '征集类型',
	    	        colorByPoint: true,
	    	        data:resultData.data
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	 
	 /**
	 * 征集明细----显示征集来源分布----饼图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getCollectSourceStatics?category=${category}&type="+encodeURI('${type}'),
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('collectDetail4', {
	    		credits: {enabled: false},
	    	    chart: { backgroundColor: '#ffffff', type: 'pie'},
	    	    title: {text: '月征集来源分布',align:'left'},
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
	    	    tooltip: {
	    	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	    	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b>'
	    	    },
	    	    series: [{
	    	        name: '征集来源',
	    	        colorByPoint: true,
	    	        data:resultData.data
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	 
	 /**
	 * 征集明细----显示征集来源机构性质分布----饼图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getCollectSourceOfDeptStatics?category=${category}&type="+encodeURI('${type}'),
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('collectDetail5', {
	    		credits: {enabled: false},
	    	    chart: { backgroundColor: '#ffffff', type: 'pie'},
	    	    title: {text: '月征集来源机构性质分布',align:'left'},
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
	    	    tooltip: {
	    	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	    	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b>'
	    	    },
	    	    series: [{
	    	        name: '机构性质',
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