<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld"%>
<%@ taglib prefix="home" uri="/WEB-INF/tlds/home.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js"></script>
<script src="${ctx}/static/plat/highCharts/highstock.js"></script>
<script src="${ctx}/static/plat/highCharts/highcharts-more.js"></script>
<script src="${ctx}/static/plat/data.js"></script>

<script type="text/javascript">
$(function() {
	//热点模块访问统计图
	$.ajax({
		type : "post",
		url : "${ctx}/platServiceCenter/getVisitData",
		dataType : "json",
		success : function(result){
			$('#container1').highcharts({
		        chart: {
		            type: 'column',
		            backgroundColor: '#dddddd',
		        },
		        title: {
		            text: '热点模块访问统计'
		        },
		        subtitle: {
		            text: null
		        },
		        xAxis: {
		        	
		        	categories: result.categories,
		        	crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '浏览量 (PV)'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		            '<td style="padding:0"><b>{point.y:.0f} 次</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: result.data
		    });
		},
		error : function() {
		}
	});
	//档案馆网站访问统计
	$.ajax({
		type : "post",
		url : "${ctx}/platServiceCenter/getSitesData",
		dataType : "json",
		success : function(result){
			var chart = Highcharts.chart('container', {
		        chart: {
		            type: 'line',
		            backgroundColor: '#dddddd',
		        },title: {
		            text: '档案馆网站访问统计'
		        },
		        subtitle: {
		            text: '最近一年'
		        },
		        yAxis: {
		        	plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }],
		            title: {
		                text: null
		            }
		        },
		        xAxis: {
		            categories: result.ycategories
		        },
		        series: result.ydata
		    });
		   $('#year').click(function () {
		        chart.update({
		            subtitle: {
		                text: '最近一年'
		            },
		            xAxis: {
		                categories: result.ycategories
		            },
		            series: result.ydata
		        });
		    }); 
		    
		    $('#moth').click(function () {
		        // chart.update 支持全部属性动态更新
		        chart.update({
		            subtitle: {
		                text: '最近一月'
		            },
		            xAxis: {
		                categories: result.mcategories
		            },
		            series: result.mdata
		        });
		    });
		    
		},
		error : function(){
		}
	});
	
})
	
	
</script>
</head>
<body>
	<div>
		<div style="margin:20px 0 0 20px;">
		    <button type="button" id="year">最近一年</button>  
	     	<button type="button" id="moth">最近一月</button> 
     	</div>
		<div id="container" style="min-width:400px;height:400px; margin:0 10px 10px 20px;"></div>
		<div id="container1" style="min-width:400px;height:400px; margin:20px 10px 10px 20px;"></div>
	</div>
	
</body>
</html>