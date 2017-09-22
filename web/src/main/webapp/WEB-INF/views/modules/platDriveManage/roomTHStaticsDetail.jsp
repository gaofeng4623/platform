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
	    <div id="roomTHDetail1" class="span12" style="height: 240px;"></div>
    </div>
    <div class="row-fluid" >
	    <div id="roomTHDetail2" class="span12" style="height: 240px;"></div>
    </div>
</div>
<script type="text/javascript">
$(function(){

	 /**
	 * 温湿度每天平均值变化曲线
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getRoomTHStaticsByDay?category="+encodeURI('${category}'),
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	$('#roomTHDetail1').highcharts({
	    		credits: { enabled: false},
	    	    chart: { type: 'spline' },
	    	    title: { 
	    	    	text: '温湿度平均值变化曲线',
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
	    	    yAxis: { min: 0, title: {  text: '温湿度' } },
	    	    tooltip: {
	    	    	headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y:0.3f} </b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	    	    plotOptions: { spline: { dataLabels: {enabled:false } } },
	    	    series:[{
	    	    	name: '温度',
	    	    	data: resultData.temperature
	    	    },{
	    	    	name: '湿度',
	    	    	data: resultData.humidity
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	
	 /**
	 * 最近12个月的每月温湿度变化范围----柱形范围图
	 */
	 $.ajax({
		url:"${ctx}/platDriveManage/getRoomTHStaticsByMonth?category="+encodeURI('${category}'),
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('roomTHDetail2', {
	    		chart: {
	                type: 'columnrange'
	            },
	            title: {
	                text: '每月温湿度变化范围',
	    	    	align:'left' 
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	                categories: resultData.categories
	            },
	            yAxis: {
	                title: {
	                    text: '温湿度'
	                }
	            },
	            tooltip: {
	            	crosshairs: true,
	            	shared: true
	            },
	            plotOptions: {
	                columnrange: {
	                    dataLabels: {
	                        enabled: true,
	                        formatter: function () {
	                            return this.y;
	                        }
	                    }
	                }
	            },
	            legend: {
	                align: 'right',
	                verticalAlign: 'top'
	            },
	            series: [{
	                name: '温度',
	                data: resultData.temperature
	            },{
	                name: '湿度',
	                data: resultData.humidity
	            }]
	    	});
	    },
	    error:function(){}
	});
	 
	
});
</script>
</body>
</html>