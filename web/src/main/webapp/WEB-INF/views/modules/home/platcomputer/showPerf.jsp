<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld"%>
<%@ taglib prefix="home" uri="/WEB-INF/tlds/home.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js"></script>
<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
<script src="${ctx}/static/plat/highCharts/highcharts-more.js"></script>
<script src="${ctx}/static/index/js/bootstrap.min.js"></script>
<script src="${ctx}/static/plat/d3/d3.min.js"></script>
<script src="${ctx}/static/plat/d3/venn.js"></script>
<script src="${ctx}/static/layer/layer.js" type="text/javascript"></script>
<script src="${ctx}/static/plat/highCharts/drilldown.js"
	type="text/JavaScript" charset="UTF-8"></script>

<link href="${ctx}/static/bootstrap/2.3.1/css_cerulean/bootstrap.css"
	type="text/css" rel="stylesheet" />
<%-- <script src="${ctx}/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<link href="${ctx}/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.css" rel="stylesheet" /> --%>
<title>性能展示</title>
</head>
<body>
	<input type="hidden" name="ip">
	
	<div
		style="width: 100%; padding-top: 10px; margin: 0;">
		<div id="chartsDiv1"
			style="min-width: 1100px; height: 400px; float: left; margin: 10px 10px 10px 10px;"></div>
		<div id="chartsDiv2"
			style="min-width: 1100px; height: 400px; float: left; margin: 10px 10px 10px 10px;"></div>
		<div id="chartsDiv3"
			style="min-width: 1100px; height: 400px; float: left; margin: 10px 10px 10px 10px;"></div>	
	</div>
	<script type="text/javascript">
	
	
	Highcharts.setOptions({
	    global: {
	        useUTC: false
	    }
	});
	function activeLastPointToolip(chart) {
	  /*   var points = chart.series[0].points;
	    chart.tooltip.refresh(points[points.length -1]); */
	}
	
	var dataCpu = [];
	var dataMem = [];
	var seriesDisk = [];
    var returnData;
  	$.ajax({
			async:false,
  		url : "${ctx}/platcomputer/platComputer/getCpuPerfAll?ip=${ip}",
			type : "post",
			dataType : "json",
			success : function(result) {
				returnData = result;
			},
			error : function() {
			}
		});
  	 var diskSize;
  	 var diskName = [];
  	 var dataNm;
	 var dataAll = [];
   	 jQuery.each(returnData, function(i,item){   
   		dataNm = i+1; 
		if(i==0){
			 jQuery.each(item.disk,function(j,item){
				diskSize = j+1;
				diskName.push(item.name);
				dataAll.push([]);
			 });
		}
		var dateTemp = item.cpu.collecttime
		
   		dataCpu.push({
          	x:(new Date(dateTemp.replace(/-/g, "/"))).getTime(),
          	y:item.cpu.percent*1
          });
   		
		dateTemp = item.mem.collectdate;
		
   		dataMem.push({
          	x:(new Date(dateTemp.replace(/-/g, "/"))).getTime(),
          	y:item.mem.percent*1
          });
   		
 	   	 jQuery.each(item.disk,function(j,item){
 	   		var dateTemp = item.collectdate;
				dataAll[j].push({x:(new Date(dateTemp.replace(/-/g, "/"))).getTime(),
								y:item.percent*1
							});
		}); 
   		
       }); 
		
		
		
		for (var i = 0;i< diskSize;i++) {
			
			var nametemp = diskName[i];
			var datatemp = dataAll[i];
			
			seriesDisk.push({"name":nametemp,"data":datatemp});
			
        }
		
	
/* 	 jQuery.each(returnData, function(i,item){ 
		 
		 jQuery.each(item.disk,function(j,item){
				alert(item.name); 
				alert(item.percent);
				alert(item.collectdate)
				dataAll[j].push({x:item.collectdate},y:item.percent});
			 });
	 }); */
 	
	//cpu 
	$('#chartsDiv1').highcharts({
	    chart: {
	        type: 'spline',
	    	backgroundColor : '#dddddd',
	        animation: Highcharts.svg, // don't animate in old IE
	        marginRight: 10,
	        events: {
	            load: function () {
	                // set up the updating of the chart each second
	                var series = this.series[0],
	                    chart = this;
	                setInterval(function () {
	                	var returnData;
	                	$.ajax({
	        				async:false,
	                		url : "${ctx}/platcomputer/platComputer/getCpuPerf?ip=${ip}",
	        				type : "post",
	        				dataType : "json",
	        				success : function(result) {
	        					returnData = result;
	        				},
	        				error : function() {
	        				}
	        			});
	                	 jQuery.each(returnData, function(i,item){   
	                         var x =  (new Date(item.collecttime.replace(/-/g, "/"))).getTime(),//(new Date(item.collecttime)).getTime(), // current time
		                        y = item.percent*1;
		                    series.addPoint([x, y], true, true);
		                    
	                     }); 
	                	 activeLastPointToolip(chart);
	                  
	                }, 30000);
	            }
	        }
	    },
	    title: {
	        text: 'CPU实时数据'
	    },
	    xAxis: {
	        type: 'datetime',
	        tickPixelInterval: 150
	    },
	    yAxis: {
	        title: {
	            text: '利用率%'
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }]
	    },
	    tooltip: {
	        formatter: function () {
	            return '<b>' + this.series.name + '</b><br/>' +
	                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
	                Highcharts.numberFormat(this.y, 2)+"%";
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    exporting: {
	        enabled: false
	    },
	    series: [{
	        name: 'CPU利用率',
	        data: (function () {
	          	return dataCpu;
	        }()),
	        zones: [{ value: 90 },{ color: '#ff0000' }]
	    }]
	}, function(c) {
	    activeLastPointToolip(c)
	});
	
	
	//mem
	$('#chartsDiv2').highcharts({
	    chart: {
	        type: 'spline',
	    	backgroundColor : '#dddddd',
	        animation: Highcharts.svg, // don't animate in old IE
	        marginRight: 10,
	        events: {
	            load: function () {
	                // set up the updating of the chart each second
	                var series = this.series[0],
	                    chart = this;
	                setInterval(function () {
	                	var returnData;
	                	$.ajax({
	        				async:false,
	                		url : "${ctx}/platcomputer/platComputer/getMemPerf?ip=${ip}",
	        				type : "post",
	        				dataType : "json",
	        				success : function(result) {
	        					returnData = result;
	        				},
	        				error : function() {
	        				}
	        			});
	                	 jQuery.each(returnData, function(i,item){ 
	                         var x =  (new Date(item.collectdate.replace(/-/g, "/"))).getTime(),//(new Date(item.collectdate)).getTime(), // current time
		                        y = item.percent*1;
		                    series.addPoint([x, y], true, true);
		                    
	                     }); 
	                	 activeLastPointToolip(chart);
	                  
	                }, 30000);
	            }
	        }
	    },
	    title: {
	        text: '内存实时数据'
	    },
	    xAxis: {
	        type: 'datetime',
	        tickPixelInterval: 150
	    },
	    yAxis: {
	        title: {
	            text: '利用率%'
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }]
	    },
	    tooltip: {
	        formatter: function () {
	            return '<b>' + this.series.name + '</b><br/>' +
	                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
	                Highcharts.numberFormat(this.y, 2)+"%";
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    exporting: {
	        enabled: false
	    },
	    series: [{
	        name: '内存利用率',
	        data: (function () {
	          	return dataMem;
	        }()),
	        zones: [{ value: 90 },{ color: '#ff0000' }]
	    }]
	}, function(c) {
	    activeLastPointToolip(c)
	});
	
	
	//disk 
	
	$('#chartsDiv3').highcharts({
	    chart: {
	        type: 'spline',
	    	backgroundColor : '#dddddd',
	        animation: Highcharts.svg, // don't animate in old IE
	        marginRight: 10,
	        events: {
	            load: function () {
	                // set up the updating of the chart each second
	                var series = this.series[0],
	                    chart = this;
	                setInterval(function () {
	                	var returnData;
	                	$.ajax({
	        				async:false,
	                		url : "${ctx}/platcomputer/platComputer/getDiskPerf?ip=${ip}",
	        				type : "post",
	        				dataType : "json",
	        				success : function(result) {
	        					returnData = result;
	        				},
	        				error : function() {
	        				}
	        			});
	                	
	                	 jQuery.each(returnData, function(i,item){ 
	                		 var series = chart.series[i];
	                         var x = (new Date(item.collectdate.replace(/-/g, "/"))).getTime(),//(new Date(item.collectdate)).getTime(), // current time
		                        y = item.percent*1;
	                        
	                         series.addPoint([x, y], true, true);
	                     }); 
	                	 activeLastPointToolip(chart);
	                  
	                }, 30000);
	            }
	        }
	    },
	    title: {
	        text: '磁盘实时数据'
	    },
	    xAxis: {
	        type: 'datetime',
	        tickPixelInterval: 150
	    },
	    yAxis: {
	        title: {
	            text: '利用率%'
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }]
	    },
	    tooltip: {
	        formatter: function () {
	            return '<b>' + this.series.name + '</b><br/>' +
	                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
	                Highcharts.numberFormat(this.y, 2)+'%';
	        }
	    },
	    legend: {
	        enabled: true
	    },
	    exporting: {
	        enabled: false
	    },
	    series:( function () {
	    	return seriesDisk;	
		}())
	}, function(c) {
	    activeLastPointToolip(c)
	});
	
	
	
	</script>
</body>
</html>