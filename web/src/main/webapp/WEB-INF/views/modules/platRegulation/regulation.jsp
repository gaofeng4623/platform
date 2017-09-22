<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
<script src="${ctx}/static/plat/highCharts/highcharts-more.js"></script>

	<div class="panel-body" id="regulation" style="height:100%;"></div>

<script type="text/javascript">

$.ajax({
    type: "post",
    url: "${ctx}/regulation/DeptData",
    dataType: "json",
    success: function (result) {
        $('#regulation').highcharts({
        	credits: { enabled: false},
        	chart: {
                polar: true,
                type: 'line'
            },
            title: {
                text: null,
                x: 0
            },
            
            pane: {
                size: '85%'
            },
            xAxis: {
                labels: {
                   
                    style: {
                       color: '#000000',//颜色
                       fontSize:'11px'  //字体
                    }
                },
            	categories: result.categories,
                lineWidth: 1,
                lineColor:'#000000',
                gridLineColor:'#000000'
            },
            yAxis: {
                gridLineInterpolation: 'polygon',
                lineWidth: 0,
                min: 1
            },
            tooltip: {
                shared: true,
                pointFormat: '<span style="color:{series.color}">{series.name}: <b>{point.y:,.0f}</b><br/>'
            },
            legend: {
                align: 'right',
                verticalAlign: 'top',
                y: 70,
                layout: 'vertical'
            },
            series: result.data
        });
    },
	error:function () {}
}); 
    
</script>