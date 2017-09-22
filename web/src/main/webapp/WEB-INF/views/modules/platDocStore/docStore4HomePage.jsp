<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>

<div class="panel-body" id="docStore" style="width:100%;min-height:230px;"></div>


<script type="text/javascript">
/* $.ajax({
    type: "post",
    url: "${ctx}/platDocStore/GrossDataCharts",
    dataType: "json",
    success: function (result) {
    	$('#docStore').highcharts({
    		credits: { enabled: false},
    		chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: '库存总量:'+result.count+'卷'
            },
            xAxis: {
                categories: result.categories,
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
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
	error:function () {}
}); 
     */

     $.ajax({
    		type: "post",
    		url: "${ctx}/platDocStore/getChartsNumData",
    		dataType: "json",
    		success: function (result) {
    			var zlCount=result.zlCount;
    			var stCount=result.stCount;
    			var dzCount=result.dzCount;
    			var libraryCount=result.libraryCount;
    			$('#docStore').highcharts({
    		    	credits: { enabled: false},
    		    	chart: {
    		                type: 'line'
    		            },
    		            title: {
    		                text: '馆藏数量<span style="font-size:12px;font-weight:bold;"></span>',
    		               
    		            },
    		            subtitle: {
    		                text: ''
    		            },
    		            xAxis: {
    		                categories:result.categories
    		            },
    		    	    yAxis: { title: {enabled: false}, plotLines:[{ color:'red', dashStyle:'longdashdot', value:libraryCount, width:2 }] },

    		            plotOptions: {
    		                line: {
    		                    dataLabels: {
    		                        enabled: true          // 开启数据标签
    		                    },
    		                    enableMouseTracking: true // 关闭鼠标跟踪，对应的提示框、点击事卷会失效
    		                }
    		            },
    		            series: [
    		            	 {
    	    		                name: '预计档案总量',
    	    		                data: [{x: 4, y: result.zhyn}, {x: 5, y: zlCount*3}, {x: 6, y: zlCount*4}, {x: 7, y: zlCount*5}, {x: 8, y: zlCount*6}, {x:9, y: zlCount*7}],
    	    		                dashStyle: 'dash'
    	    		            },{
    		                name: '档案总量',
    		                data: result.zl
    		            } /* ,{
    		                name: '实体数量',
    		                data: result.st
    		            }, {
    		                name: '实体数量预计',
    		                data: [{x: 4, y: stCount*2}, {x: 5, y: stCount*3}, {x: 6, y: stCount*4}, {x: 7, y: stCount*5}, {x: 8, y: stCount*6}, {x:9, y: stCount*7}],
    		                dashStyle: 'dash'
    		            },{
    		                name: '电子数量',
    		                data: result.dz
    		            }, {
    		                name: '电子数量预计',
    		                data: [{x: 4, y: dzCount*2}, {x: 5, y: dzCount*3}, {x: 6, y: dzCount*4}, {x: 7, y: dzCount*5}, {x: 8, y: dzCount*6}, {x:9, y: dzCount*7}],
    		                dashStyle: 'dash'
    		            } */ ]
    		      });
    		},
    		error:function () {}
    	});  
</script>