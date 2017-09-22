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
		<div class="row-fluid" style="padding-top: 5px;">
			<div class="span12" >
				<div id="authDetail1" style="height: 300px;"></div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12" >
				<div id="authDetail2" style="height: 300px;"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(function(){
		/**
		 * 鉴定明细----按全宗显示年鉴定档案量----柱图
		 */
		 $.ajax({
			url:"${ctx}/platDriveManage/getAuthStaticsByUnit?category=${category}&type="+encodeURI('${type}'),
		    type: "post",
	        dataType: "json",
		    success:function(result){
		    	$('#authDetail1').highcharts({
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
		 * 鉴定明细----显示档案类型统计鉴定量----折线图
		 */
		 $.ajax({
			url:"${ctx}/platDriveManage/getAuthStaticsByStoreId?category=${category}&type="+encodeURI('${type}'),
		    type: "post",
	        dataType: "json",
		    success:function(resultData){
		    	$('#authDetail2').highcharts({
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
	});
	</script>
</body>
</html>