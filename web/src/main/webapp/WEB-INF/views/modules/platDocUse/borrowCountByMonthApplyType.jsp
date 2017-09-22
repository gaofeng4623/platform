<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>档案利用</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highstock.js"></script>
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
				<div id="chartsDiv1" style="min-width: 600px; height: 300px;"></div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12" >
				<div id="chartsDiv2" style="min-width: 600px; height: 300px;"></div>  
			</div>
		</div>
	</div>
    
	<script type="text/javascript">
	$(function(){
		var chartsDiv1 = Highcharts.chart('chartsDiv1', {
			title: {
		        text: '${applytype}按天统计',
		        align: 'left'
		    },
		    chart : {
		    	events: {
		    		load: function() {
		    			$.ajax({
		    			    type: "post",
		    			    url: "${ctx}/platDocUse/findBorrowCountByMonthApplyTypeEq",
		    			    data: {
		    			    	month: '${month}',
		    			    	applytype: '${applytype}'
		    			    },
		    			    dataType: "json",
		    			    success: function (result) {
		    			    	chartsDiv1.xAxis[0].setCategories(result.months);
		    			    	chartsDiv1.series[0].setData(result.uses);
		    			    }
		    			})
		    		}
		    	}
			},
		    xAxis: {
		        //categories:  ['6月', '7月', '8月', '9月', '10月', '11月', '12月', '1月', '2月', '3月', '4月', '5月']
		    	categories:[]
		    },
		    yAxis: {
		        title: {
		            text: '借阅数量'
		        },
		        plotLines: [{
		            value: 0,
		            width: 1
		        }]
		    },
		    tooltip: {
		        valueSuffix: '卷'
		    },
		    series: [{
	   	        name: '${applytype}',
	   	     	data:[]
	   	        //data: [117, 126, 96, 87, 120, 100, 78, 82, 142, 89, 63, 56]
	   	    }]
	   	});
		var chartsDiv2 = Highcharts.chart('chartsDiv2', {
			title: {
		        text: '${usertype}按档案类型统计',
		        align: 'left'
		    },
		    chart : {
		    	type: 'column',
		    	events: {
		    		load: function() {
		    			$.ajax({
		    			    type: "post",
		    			    url: "${ctx}/platDocUse/findBorrowCountByClassEq",
		    			    data: {
		    			    	month: '${month}',
		    			    	applytype: '${applytype}'
		    			    },
		    			    dataType: "json",
		    			    success: function (result) {
		    			    	chartsDiv2.xAxis[0].setCategories(result.names);
		    			    	chartsDiv2.series[0].setData(result.uses);
		    			    }
		    			})
		    		}
		    	}
			},
		    xAxis: {
		        //categories:  ['6月', '7月', '8月', '9月', '10月', '11月', '12月', '1月', '2月', '3月', '4月', '5月']
		    	categories:[]
		    },
		    yAxis: {
		        title: {
		            text: '利用数量'
		        },
		        plotLines: [{
		            value: 0,
		            width: 1
		        }]
		    },
		    plotOptions: {
		    	series: {
		    		cursor: 'pointer'
		    	}
		    },
		    tooltip: {
		        valueSuffix: '卷'
		    },
		    series: [{
	   	        name: '数量',
	   	     	data:[]
	   	        //data: [117, 126, 96, 87, 120, 100, 78, 82, 142, 89, 63, 56]
	   	    }]
	   	});
	});
	</script>
</body>
</html>