<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>


<div id="docUse4HomePage" style="height: 200px"></div>

<script type="text/javascript">
$(function () {
	var docUse4HomePage = Highcharts.chart('docUse4HomePage', {
		credits: { enabled: false},
	    chart: { 
	    	type: 'spline',
	    	events: {
	    		load: function() {
	    			$.ajax({
	    			    type: "post",
	    			    url: "${ctx}/platDocUse/findUseAllCountByMonth",
	    			    dataType: "json",
	    			    success: function (result) {
	    					docUse4HomePage.xAxis[0].setCategories(result.months);
	    					docUse4HomePage.series[0].setData(result.uses);
	    					docUse4HomePage.series[1].setData(result.borrows);
	    					docUse4HomePage.series[2].setData(result.totals);
	    			    }
	    			})
	    		}
	    	}
	    },
	    title: { text: '借阅利用' },
	    subtitle: { text: '' },
	    xAxis: {
	    	categories:[],
	        //categories:['6月', '7月', '8月', '9月', '10月', '11月', '12月', '1月', '2月', '3月', '4月', '5月'],
	        crosshair: true
	    },
	    yAxis: { min: 0, title: {  text: '卷' } },
	    tooltip: { crosshairs: true, shared: true },
	    plotOptions: { spline: { marker: { radius: 4, lineColor: '#666666', lineWidth: 1 } } },
	    series:[{
   	        name: '利用量',
   	     	data:[]
   	        //data: [117, 126, 96, 87, 120, 100, 78, 82, 142, 89, 63, 56]
   	    }, {
   	        name: '借阅量',
   	    	data:[]
   	        //data: [217, 226, 196, 187, 220, 130, 178, 182, 172, 189, 163, 156]
   	    }, {
   	        name: '总量',
   	     	data:[]
   	        //data: [334, 352, 292, 274, 340, 230, 256, 264, 214, 278, 226, 212]
   	    }]
	});
	/**
	 * 档案查阅时间分布图---折线图
	 */
	
	
});
</script>