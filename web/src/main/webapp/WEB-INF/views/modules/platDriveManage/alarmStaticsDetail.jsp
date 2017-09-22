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
			padding-bottom: 10px;
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
	    <div id="alarmDetail1" class="span12" style="height: 240px;"></div>
    </div>
    <div class="row-fluid" >
	    <div id="alarmDetail2" class="span12" style="height: 240px;"></div>
    </div>
    <div class="row-fluid" >
    	<div id="alarmDetail3" class="span12" style="height: 240px;"></div>
    </div>
</div>
<script type="text/javascript">
$(function(){

	/**
	 * 统计最近12个月的报警次数
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getAlarmStaticsByMonth?category="+encodeURI('${category}'),
		type: "post",
        dataType: "json",
	    success:function(result){
	    	$('#alarmDetail1').highcharts({
	    		credits: { enabled: false},
	    	    chart: { type: 'column' },
	    	    title: { text: '最近12个月报警分布' ,align:'left'},
	    	    subtitle: { text: '' },
	    	    xAxis: {
	    	    	categories:result.categories
	    	    },
	    	    legend: {
	                align: 'right',
	                verticalAlign: 'top'
	            },
	    	    yAxis: { 
	    	    	min: 0,
	    	    	title: {  text: '次数' },
			        plotLines: [{
			            value: 0,
			            width: 1
			        }] },
	    	    tooltip: { crosshairs: true, shared: true },
	    	    plotOptions: { spline: { dataLabels:{ enabled:true } } },
	    	    series:[{
	    	    	name: '报警次数',
	       	        data: result.data
	       	    }]
	    	});
	    },
	    error:function(){}
	});
	
	 /**
	 * 该类型的报警在各个库房的分布
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getAlarmStaticsByRoom?category="+encodeURI('${category}'),
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	$('#alarmDetail2').highcharts({
	    		credits: { enabled: false},
	    	    chart: { type: 'spline' },
	    	    title: { 
	    	    	text: '库房报警分布',
	    	    	align:'left' 
	    	    },
	    	    subtitle: { text: '' },
	    	    legend: {
	                align: 'right',
	                verticalAlign: 'top'
	            },
	    	    xAxis: {
	    	        categories: resultData.categories,
	    	        crosshair: true
	    	    },
	    	    yAxis: { min: 0, title: {  text: '次数' } },
	    	    tooltip: {
	    	    	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y} </b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	    	    plotOptions: { spline: { dataLabels: {enabled:false } } },
	    	    series:[{
	    	    	name: '报警次数',
	    	    	data: resultData.data
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	
	 /**
	 * 该类报警在各个设备上的分布
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getAlarmStaticsByDevice?category="+encodeURI('${category}'),
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('alarmDetail3', {
	    		chart: {
	    			zoomType: 'xy'
	            },
	            title: {
	                text: '设备报警分布',
	    	    	align:'left' 
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	            	max: 15,
	                categories: resultData.categories,
	    	        labels: {
	    	            useHTML: true,
	    	            enabled: true,
	    	            style :{
	    	                'display':'inline-block',
	    	                'max-width':'80px',
	    	                'overflow':'hidden',
	    	                'text-overflow':'ellipsis',
	    	                'white-space':'nowrap',
	    	                'padding-right':'15px'
	    	            }
	    	        }
	            },
	            scrollbar: {enabled:true},
	            yAxis: [{ // Primary yAxis
	                labels: {
	                    format: '{value}次',
	                    style: {
	                        color: Highcharts.getOptions().colors[0]
	                    }
	                },
	                title: {
	                    text: '次数',
	                    style: {
	                        color: Highcharts.getOptions().colors[0]
	                    }
	                }
	            }, { // Secondary yAxis
	                title: {
	                    text: '时长',
	                    style: {
	                        color: Highcharts.getOptions().colors[1]
	                    }
	                },
	                labels: {
	                    format: '{value} 秒',
	                    style: {
	                        color: Highcharts.getOptions().colors[1]
	                    }
	                },
	                opposite: true
	            }],
	            tooltip: {
	            	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y} </b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    dataLabels: {
	                        enabled: true,
	                        align: 'left',
	                        y: -20,
	                        allowOverlap:true,
	                        formatter: function () {
	                            return '<span style="font-size:10px;color:{series.color}">'+this.y+'次</span>';
	                        }
	                    }
	                }
	            },
	            legend: {
	                align: 'right',
	                verticalAlign: 'top'
	            },
	            series: [{
	                name: '报警次数',
	                type: 'column',
	                data: resultData.data
	            },{
	                name: '报警时长',
	                type: 'spline',
	                data: resultData.duration,
	                yAxis: 1
	            }]
	    	});
	    },
	    error:function(){}
	});
	 
	
});
</script>
</body>
</html>