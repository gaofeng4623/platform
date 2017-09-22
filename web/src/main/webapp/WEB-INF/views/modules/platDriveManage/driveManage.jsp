<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>驾驶舱管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
	<script src="${ctx}/static/plat/highCharts/highcharts-more.js"></script>
	<script src="${ctx}/static/plat/d3/d3.min.js"></script>
	<script src="${ctx}/static/plat/d3/venn.js"></script>
	<script src="${ctx}/static/layer/layer.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-smartMenu/js/jquery-smartMenu.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${ctx}/static/jquery-smartMenu/css/smartMenu.css" type="text/css" />
	<style type="text/css">
		.row-fluid {
			padding-top: 10px;
		}
		.container-fluid {
		    padding-right: 5px;
		    padding-left: 5px;
		}
		.driver-data {
			color: white;
			font-family:"微软雅黑";
			padding: 0px 15px;
		}
		.driver-content {
			font-size: 16px;
			color: white;
			font-family:"微软雅黑";
			padding: 0px 15px;
		}
		.driver-img{
			padding: 8px 15px 0 45px;
		}
	</style>
</head>
<body>
<div id="toWindow" style="display:none; position: absolute;top: 0px;right: 15px;font-size: 16px;font-weight: bold;cursor: pointer;">最大化</div>
    <div class="container-fluid" style="background-color:#dddddd">
        <div class="row-fluid">
	        <div class="span2" style="background-color:#27a659;height: 100px;">
	        	<div class="row-fluid" style="padding-top:18px;">
		        	<div class="span6">
		        		<h2><span id="driveDiv1" class="driver-data"> </span></h2>
			        	<span class="driver-content">电子借阅</span>
		        	</div>
		        	<div class="span6">
		        		<img class="driver-img" src="${ctx}/static/images/manage_digital.png"/>
		        	</div>
	        	</div>
	        </div>
		    <div class="span2" style="background-color:#2dbff0;min-height: 100px;">
		    	<div class="row-fluid" style="padding-top:18px;">
		    		<div class="span6">
				    	<h2><span id="driveDiv2" class="driver-data"> </span></h2>
			        	<span class="driver-content">实物借阅</span>
		        	</div>
		        	<div class="span6">
		        		<img class="driver-img" src="${ctx}/static/images/manage_borrow.png"/>
		        	</div>
		    	</div>
		    </div>
		    <div class="span2" style="background-color:#f09b0a;min-height: 100px;">
		    	<div class="row-fluid" style="padding-top:18px;">
		        	<div class="span6">
				    	<h2><span id="driveDiv3" class="driver-data"> </span></h2>
			        	<span class="driver-content">档案接收</span>
		        	</div>
		        	<div class="span6">
		        		<img class="driver-img" src="${ctx}/static/images/manage_receive.png"/>
		        	</div>
		    	</div>
		    </div>
		    <div class="span2" style="background-color:#d84c3b ;min-height: 100px;">
		    	<div class="row-fluid" style="padding-top:18px;">
		        	<div class="span6">
				    	<h2><span id="driveDiv4" class="driver-data"> </span></h2>
			        	<span class="driver-content">档案征集</span>
			        </div>
		        	<div class="span6">
		        		<img class="driver-img" src="${ctx}/static/images/manage_collect.png"/>
		        	</div>
		    	</div>
		    </div>
	    	<div class="span2" style="background-color:#64cbca;min-height: 100px;">
	    		<div class="row-fluid" style="padding-top:18px;">
		        	<div class="span6">
			    		<h2><span id="driveDiv5" class="driver-data"> </span></h2>
			        	<span class="driver-content">档案鉴定</span>
		        	</div>
		        	<div class="span6">
		        		<img class="driver-img" src="${ctx}/static/images/manage_auth.png"/>
		        	</div>
	    		</div>
	    	</div>
	   	 	<div id="maxWindow" class="span2" style="background-color:#7266ba;min-height: 100px;">
	   	 		<div class="row-fluid" style="padding-top:18px;">
		        	<div class="span6">
			   	 		<h2><span id="driveDiv6" class="driver-data"></span></h2>
			        	<span class="driver-content">安全报警</span>
			        </div>
		        	<div class="span6">
	        			<img class="driver-img" src="${ctx}/static/images/manage_warn.png"/>
	        		</div>
	        	</div>
	   	 	</div>
        </div>
        <div class="row-fluid">
        	<div id="chartsDiv1" class="span6" style="min-height: 240px;"></div>
        	<div id="chartsDiv2" class="span6" style="min-height: 240px;"></div>
        </div>
        <div class="row-fluid">
        	<div id="chartsDiv3" class="span6" style="min-height: 240px;"></div>
        	<div id="chartsDiv4" class="span3" style="min-height: 240px;"></div>
        	<div id="chartsDiv5" class="span3" style="min-height: 240px;"></div>
        </div>
        <div class="row-fluid">
        	<div id="chartsDiv6" class="span6" style="min-height: 240px;"></div>
        	<div id="chartsDiv8" class="span3" style="min-height: 240px;"></div>
	   		<div id="chartsDiv9" class="span3" style="min-height: 240px;"></div>
        </div>
        <div class="row-fluid">
        	<div id="chartsDiv10" class="span6" style="min-height: 300px;"></div>
        	<div id="chartsDiv11" class="span6" style="min-height: 300px;"></div>
        </div>
    </div>

<script type="text/javascript">
$(function(){
	var bodyRightMenu = [[
		{ 
			text: "最大化" ,
        	func: function() {
           		maxWin();
        	}
		},{
			text: "关闭",
			func: function(){
				closeWin();
			}
		}]];

	$("body").smartMenu(bodyRightMenu, {
	    name: "body"    
	});
	
	//禁止页面缓存
    $.ajaxSetup({
    	cache: false
    }); 
	
    /**
	 * 驾驶舱管理---利用方式---饼图
	 */
   	Highcharts.chart('chartsDiv4', {
   		credits : {
   			enabled : false
   		},
   		chart : {
   			backgroundColor : '#ffffff',
   			plotBorderWidth : null,
   			plotShadow : false,
   			type : 'pie'
   		},
   		title : {
   			text : '利用方式',
   			align: 'left'
   		},
   		tooltip : {
   			pointFormat : '<b>{point.percentage:.2f}%</b>'
   		},
   		plotOptions : {
   			pie : {
   				allowPointSelect : true,
   				cursor : 'pointer',
   				dataLabels : {
   					enabled : false
   				},
   				showInLegend : true
   			},
   			series: {
   	            cursor: 'pointer',
   	            events: {
   	                click: function(event) {
   	                    var url = "${ctx}/platDriveManage/toUseModeStaticsDetail?label="+event.point.name;
   	                    var title = '<font style="font-size:15px;">【'+event.point.name+'】档案利用明细</font>';
   		    	    	openDetail(url,title);
   	                }
   	            }
   	        }
   		},
   		series : [ {
   			colorByPoint : true,
   			data : []
   		} ]
   	});
	   
	
    /**
	 * 驾驶舱管理---利用目的---饼图
	 */
   	Highcharts.chart('chartsDiv5', {
		credits : {
			enabled : false
		},
		chart : {
			backgroundColor : '#ffffff',
			plotBorderWidth : null,
			plotShadow : false,
			type : 'pie'
		},
		title : {
			text : '利用目的',
			align: 'left'
		},
		tooltip : {
			pointFormat : '<b>{point.percentage:.2f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels: {
                    enabled: false,
                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                },
                showInLegend: true
			},
			series: {
	            cursor: 'pointer',
	            events: {
	                click: function(event) {
	                    var url = "${ctx}/platDriveManage/toUsePurposeStaticsDetail?label="+event.point.name;
	                    var title = '<font style="font-size:15px;">【'+event.point.name+'】档案利用明细</font>';
		    	    	openDetail(url,title);
	                }
	            }
	        }
		},
		legend: { 
			enabled : false,
			layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            floating: true,
            y: 0,
            navigation: {
                activeColor: '#3E576F',
                animation: true,
                arrowSize: 12,
                inactiveColor: '#CCC',
                style: {
                    fontWeight: 'bold',
                    color: '#333',
                    fontSize: '12px'
                }
            }
        },
		series : [ {
			colorByPoint : true,
			data : []
		} ]
	});
	    
    
	/**
	 * 驾驶舱管理---部门公告---饼图
	 */
	$.ajax({
		url:"${ctx}/platDriveManage/getDeptNotice",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	Highcharts.chart('chartsDiv7', {
	    		credits: {enabled: false},
	    	    chart: { backgroundColor: '#ffffff', type: 'pie'},
	    	    title: {text: '部门公告',align:'left'},
	    	    subtitle: {text: ''},
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true, cursor: 'pointer',
	                    dataLabels: {
	                        enabled: true,
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
	    	        name: '部门公告',
	    	        colorByPoint: true,
	    	        data:resultData.data
	    	    }]
	    	});
	    },
	    error:function(){}
	});
	

	/**
	 * 驾驶舱管理---行业资讯---折线图
	 */
   	Highcharts.chart('chartsDiv9', {
   		credits: { enabled: false},
   	    chart: { type: 'spline', inverted: false },
   	    title: { text: '行业资讯月统计量',align:'left'},
   	    xAxis: { categories:[] },
   	    yAxis: { title: { text:'条' } },
   	    legend: { enabled: false },
   	    plotOptions: {
               line: {
                   dataLabels: {
                       enabled: true          // 开启数据标签
                   },
                   enableMouseTracking: false // 关闭鼠标跟踪，对应的提示框、点击事件会失效
               }
           },
   	    tooltip: { crosshairs: true, shared: true},
   	    series: [{
   	        name: '行业资讯',
   	        data:[]
   	    }]
   	});

	/**
	 * 驾驶舱管理---年移交档案量---混合图
	 */
   	Highcharts.chart('chartsDiv8', {
   		credits: { enabled: false},
   	    title: { text: '年接收档案量' ,align:'left'},
   	    xAxis: { categories: []  },
   	    yAxis: { title: { text: '卷/件' }},
   	    plotOptions: { column:{ dataLabels:{ enabled:true } },series: {
  	            cursor: 'pointer',
  	            events: {
  	                click: function(event) {
  	                    //alert(this.name+"-----"+event.point.category+":"+event.point.y);
  	                    var url = "${ctx}/platDriveManage/toTransferStaticsDetail?category="+event.point.category+"&y="+event.point.y;
  	                    var title = '<font style="font-size:15px;">【'+event.point.category+'年】移交量明细</font>';
	    	    	openDetail(url,title);
  	                }
  	            }
  	        } },
   	    legend: { enabled: false},
   	    tooltip: {enabled: false },
   	    series:[{
   	    	 name: '年接收档案量',
   	    	type:"column",
   	    	data:[]
  	    	},{
  	    		name: '年接收档案量',
   	    	type:"spline",
   	    	data:[]
  	    	}]
   	});

	/**
	 * 驾驶舱管理---年鉴定档案量---柱状图
	 */
   	$('#chartsDiv6').highcharts({
   		credits: { enabled: false},
   		chart: {
               type: 'column'
           },
           title: {
               text: '年鉴定档案量',align:'left'
           },
           xAxis: {
               categories: [],
               crosshair: true
           },
           yAxis: {
               min: 0,
               title: {
                   text: '卷/件'
               }
           },
           tooltip: {
               headerFormat: '<span style="font-size:10px">{point.key}年</span><table>',
               pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
               '<td style="padding:0"><b>{point.y} </b></td></tr>',
               footerFormat: '</table>',
               shared: true,
               useHTML: true
           },
           plotOptions: {
               column: {
                   pointPadding: 0.2,
                   borderWidth: 0
               },series: {
   	            cursor: 'pointer',
   	            events: {
   	                click: function(event) {
   	                    //alert(this.name+"-----"+event.point.category+":"+event.point.y);
   	                    var url = "${ctx}/platDriveManage/toAuthStaticsDetail?category="+event.point.category+"&type="+this.name;
   	                    var title = '<font style="font-size:15px;">【'+event.point.category+'年-'+this.name+'】鉴定量明细</font>';
		    	    	openDetail(url,title);
   	                }
   	            }
   	        }
           },
           series: [{
     	    	 name: '濒危',
        	     data:[]
       	    	},{
       	    		name: '划控',
        	    	data:[]
       	    	},{
       	    		name: '开放',
        	    	data:[]
       	    	},{
       	    		name: '密级',
        	    	data:[]
       	    	},{
       	    		name: '销毁',
        	    	data:[]
       	    	},{
       	    		name: '延期',
        	    	data:[]
       	    	},{
       	    		name: '遗失',
        	    	data:[]
       	    	}]
       });
    
	
	/**
	 * 驾驶舱管理---月征集档案量---面积图
	 */
   	Highcharts.chart('chartsDiv3', {
   		credits: { enabled: false},
   	    chart: { 
   	    	backgroundColor: '#ffffff',
   	    	type: 'area' 
   		},
   	    title: { text: '月征集档案量', align: 'left' },
   	    xAxis: { categories: [] },
   	    yAxis: { title: { text: '卷/件' } },
   	    legend: { align: 'right', x: -30, verticalAlign: 'top', y: 0, floating: true },
   	    tooltip: { crosshairs: true, shared: true},
   	    plotOptions: {
   	        area: {
   	            stacking: 'normal', lineColor: '#666666',
   	            lineWidth: 1, marker: { lineWidth: 1, lineColor: '#666666' }
   	        },series: {
   	            cursor: 'pointer',
   	            events: {
   	                click: function(event) {
   	                    /* top.$.jBox("iframe:${ctx}/platDriveManage/toCollectStaticsDetail?category="+event.point.category+"&type="+this.name, {
   	        			    title: event.point.category+"征集量明细",
   	        			    width: 880,
   	        			    height: 660,
   	        			    buttons: {},
   	        			});  */
   	        			var url = "${ctx}/platDriveManage/toCollectStaticsDetail?category="+event.point.category+"&type="+this.name;
   	        			var title = '<font style="font-size:15px;">【'+event.point.category+'-'+this.name+'】征集量明细</font>';
		    	    	openDetail(url,title);
   	                }
   	            }
   	        }
   	    },
   	    series: [
   	    	{
   	    		name: '捐赠',
    	    	data:[]
   	    	},{
   	    		name: '寄存',
    	    	data:[]
   	    	},{
   	    		name: '交换',
    	    	data:[]
   	    	},{
   	    		name: '征购',
    	    	data:[]
   	    	}
   	    ]
   	});
	

	/**
	 * 驾驶舱管理---借阅利用---折线图
	 */
   	$('#chartsDiv2').highcharts({
   		credits: { enabled: false},
   	    chart: { 
   	    	type: 'spline',
   	    	events: {
   	    		click: function(event) {
	    	    	var url = "${ctx}/platDriveManage/toBorrowUseStaticsDetail";
	    	    	var title = '<font style="font-size:15px;">档案借阅利用明细</font>';
	    	    	openDetail(url,title);
   	    		} 
   	    	}
   		},
   	    title: { text: '档案借阅利用',align:'left'},
   	    subtitle: { text: '' },
   	    xAxis: {
   	        categories: [],
   	        crosshair: true
   	    },
   	    yAxis: { min: 0, title: {  text: '卷/件' } },
   	    tooltip: { crosshairs: true, shared: true },
   	    plotOptions: { spline: { marker: { radius: 4, lineColor: '#666666', lineWidth: 1 } } },
   	    series:[{
      	        name: '利用量',
      	        data: []
      	    }, {
      	        name: '借阅量',
      	        data: []
      	    }, {
      	        name: '总量',
      	        data: []
      	    }]
   	});
	
	/**
	 * 驾驶舱管理---馆藏---柱状图
	 */
	 $.ajax({
 		type: "post",
 		url: "${ctx}/platDocStore/getChartsNumData",
 		dataType: "json",
 		success: function (result) {
 			var arr_data = result.zl;
 			var zlCount=result.zlCount;
 			var stCount=result.stCount;
 			var dzCount=result.dzCount;
 			var libraryCount=result.libraryCount;
		  	$('#chartsDiv1').highcharts({
		  		credits: { enabled: false},
		  		chart: {
		            type: 'line',
		  	    	events: {
		  	    		click: function(event) {
			    	    	var url = "${ctx}/platDriveManage/toDocStoreStaticsDetail";
			    	    	var title = '<font style="font-size:15px;">档案馆藏统计明细</font>';
			    	    	openDetail(url,title);
		  	    		} 
		  	    	}
	          },
	          title: {
	              text: '馆藏统计', align: 'left'
	          },
	          subtitle: {
	              text: ''
	          },
	          xAxis: {
	              categories: result.categories,
	              crosshair: true
	          },
	          yAxis: {
	        	  max: Number(libraryCount)+1000,
	              title: {
	            	  enabled: false
	              },
	              plotLines:[{ color:'red', dashStyle:'longdashdot',label:{text:'<span style="color:red;font-size:16px">馆藏容量：'+libraryCount+'</span>',align:'center'}, value:libraryCount, width:2 }]
	          },
	          tooltip: {
	              headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	              pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	              '<td style="padding:0"><b>{point.y} </b></td></tr>',
	              footerFormat: '</table>',
	              shared: true,
	              useHTML: true
	          },
	          plotOptions: {
	        	  line: {
	                  pointPadding: 0.2,
	                  borderWidth: 0
	              }
	          },
	          series: [{
			      name: '档案总量',
			      data: [{x: 0, y: arr_data[0]}, {x: 1, y: arr_data[1]}, {x: 2, y: arr_data[2]},
			    	  {x: 3, y: arr_data[3]},{x: 4, y: result.zhyn}, {x: 5, y: zlCount*3},
			    	  {x: 6, y: zlCount*4}, {x: 7, y: zlCount*5}, {x: 8, y: zlCount*6},
			    	  {x: 9, y: zlCount*7}],
			      pointStart: 0,
	    	      zoneAxis:'x',
	              zones: [{
	                  value: 4,
	                  dashStyle: 'solid',
	                  color: 'black'
	              }, {
	                  dashStyle: 'dot'
	              }]
		  	  }]
	      });
 		},
		error:function () {}
	});  
	
	/**
	 * 驾驶舱管理---库房温湿度---混合图
	 */
   	$('#chartsDiv10').highcharts({
   		credits: { enabled: false},
   	    chart: { 
   	    	zoomType: 'xy'
   		},
   	    title: { text: '库房温湿度',align:'left'},
   	    subtitle: { text: '' },
   	    xAxis: [{
   	        categories: [],
   	        crosshair: true
   	    }],
   	    yAxis: [{ // Primary yAxis
               labels: {
                   format: '{value}°C',
                   style: {
                       color: Highcharts.getOptions().colors[0]
                   }
               },
               title: {
                   text: '温度',
                   style: {
                       color: Highcharts.getOptions().colors[0]
                   }
               }
           }, { // Secondary yAxis
               title: {
                   text: '湿度',
                   style: {
                       color: Highcharts.getOptions().colors[1]
                   }
               },
               labels: {
                   format: '{value} %rh',
                   style: {
                       color: Highcharts.getOptions().colors[1]
                   }
               },
               opposite: true
           }],
   	    legend: {
               align: 'right',
               verticalAlign: 'top'	        
           },
   	    tooltip: { crosshairs: true, shared: true },
   	    plotOptions: { 
   	    	column: {
   	            stacking: 'normal', lineColor: '#dedede',
   	            lineWidth: 1, marker: { lineWidth: 1, lineColor: '#f7ca2e' }
   	        },
   	    	series: {
   	            cursor: 'pointer',
   	            events: {
   	                click: function(event) {
   	        			var url = "${ctx}/platDriveManage/toRoomTHStaticsDetail?category="+event.point.category;
		    	    	var title = '<font style="font-size:15px;">【'+event.point.category+'】温湿度明细</font>';
		    	    	openDetail(url,title);
   	                }
   	            }
  	        	}
   	    },
   	    series:[{
      	        name: '温度',
      	     	type: 'column',
      	        data: [],
	       	    tooltip: {
	                valueSuffix: ' °C'
	            }
      	    }, {
      	        name: '湿度',
      	     	type: 'spline',
      	        data: [],
	       	    tooltip: {
	                valueSuffix: ' %rh'
	            },
	            yAxis: 1
      	    }]
   	});
	
	 /**
	 * 驾驶舱管理---报警统计--
	 */
   	$('#chartsDiv11').highcharts({
   		chart: {
               type: 'column'
           },
           title: {
               text: '报警统计',
			align: 'left'
           },
           subtitle: {
               text: ''
           },
           xAxis: {
               categories: [],
               crosshair: true
           },
           yAxis: {
               min: 0,
               title: {
                   text: '次数'
               }
           },
           legend: {
               enabled: false	        
           },
           tooltip: {
               headerFormat: '<span style="font-size:10px">{point.key}报警</span><table>',
               pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
               '<td style="padding:0"><b>{point.y} 次</b></td></tr>',
               footerFormat: '</table>',
               shared: true,
               useHTML: true
           },
           plotOptions: {
               column: {
                   pointPadding: 0.2,
                   borderWidth: 0
               },
   	    	series: {
   	            cursor: 'pointer',
   	            events: {
   	                click: function(event) {
   	        			var url = "${ctx}/platDriveManage/toAlarmStaticsDetail?category="+event.point.category;
		    	    	var title = '<font style="font-size:15px;">【'+event.point.category+'】报警分布明细</font>';
		    	    	openDetail(url,title)
   	                }
   	            }
  	        	}
           },
           series: [{
               name: '报警次数',
               data: []
           }]
   	});
	 
	//首次进入页面异步加载数据
	function firstLoadData(){
		var chartsDiv3 = $('#chartsDiv3').highcharts();
		$.ajax({
		    type: "post",
		    url: "${ctx}/platDriveManage/getDocInByMonth",
		    dataType: "json",
		    success: function (result) {	
		    	chartsDiv3.xAxis[0].setCategories(result.categories);
		    	for(var i = 0; i < result.data.length; i++){
		    		var seriesO = {}
		    		seriesO.name = result.data[i].name;
		    		seriesO.data = result.data[i].data;
		    		chartsDiv3.addSeries(seriesO,false);
		    	}
		    	chartsDiv3.redraw();
		    }
		});
	}
	
	//首次进入加载数据
	getData();
	//定时刷新图表数据
	setInterval("getData()", 1*60*1000);
});

function openDetail(url,titleName){
	layer.open({
  	  type: 2, 
  	  area: [pageSize().WinW - 400+'px', pageSize().WinH - 100 + 'px'],
  	  title: titleName,
  	  closeBtn: 1,
  	  shadeClose: false,
  	  shade: [0.5 , '#000' , true],
  	  content: url
  	});  
}

//定时刷新数据
function getData(){
	//电子借阅
    $.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getDrive1",
	    dataType: "json",
	    success: function (result) {	
	    	$('#driveDiv1').html(result.data);
	    }
	});
    //实物借阅
    $.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getDrive2",
	    dataType: "json",
	    success: function (result) {	
	    	$('#driveDiv2').html(result.data);
	    }
	});
    //档案接收
    $.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getDrive3",
	    dataType: "json",
	    success: function (result) {	
	    	$('#driveDiv3').html(result.data);
	    }
	});
  	//档案征集
    $.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getDrive4",
	    dataType: "json",
	    success: function (result) {	
	    	$('#driveDiv4').html(result.data);
	    }
	});
  	//档案鉴定
    $.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getDrive5",
	    dataType: "json",
	    success: function (result) {	
	    	$('#driveDiv5').html(result.data);
	    }
	});
    //安全报警次数
    $.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getDrive6",
	    dataType: "json",
	    success: function (result) {	
	    	$('#driveDiv6').html(result.data);
	    }
	});
	//利用方式
	var chartsDiv4 = $('#chartsDiv4').highcharts();
	$.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getUseStatics",
	    dataType: "json",
	    success: function (result) {	    	 
	    	chartsDiv4.series[0].setData(result.data);
	    }
	});
	//利用目的
	var chartsDiv5 = $('#chartsDiv5').highcharts();
	$.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/findUseCountByUsepurpose",
	    dataType: "json",
	    success: function (result) {	    	 
	    	chartsDiv5.series[0].setData(result.data);
	    }
	});
	//行业资讯
	var chartsDiv9 = $('#chartsDiv9').highcharts();
	$.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getInformationStatics",
	    dataType: "json",
	    success: function (result) {	
	    	chartsDiv9.xAxis[0].setCategories(result.categories);
	    	chartsDiv9.series[0].setData(result.data);
	    }
	});
	//年接收档案量
	var chartsDiv8 = $('#chartsDiv8').highcharts();
	$.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getTransferInByYear",
	    dataType: "json",
	    success: function (result) {	
	    	chartsDiv8.xAxis[0].setCategories(result.categories);
	    	chartsDiv8.series[0].setData(result.columnData);
	    	chartsDiv8.series[1].setData(result.splineData);
	    }
	});
	//年鉴定档案量
	var chartsDiv6 = $('#chartsDiv6').highcharts();
	$.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getAuthStatics",
	    dataType: "json",
	    success: function (result) {	
	    	chartsDiv6.xAxis[0].setCategories(result.categories);
	    	var series = chartsDiv6.series;                  
	    	for(var i = 0; i < series.length; i++) {  
	    	     for(var j = 0; j< result.data.length; j++){
	    	    	 var name = series[i].name
	    	    	 if(result.data[j].name == name){
	    	    		 chartsDiv6.series[i].setData(result.data[j].data);
	    	    	 }
	    	     }
	    	}  
	    }
	}); 
	//月征集档案量
	var chartsDiv3 = $('#chartsDiv3').highcharts();
	$.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getDocInByMonth",
	    dataType: "json",
	    success: function (result) {	
	    	chartsDiv3.xAxis[0].setCategories(result.categories);
	    	var series = chartsDiv3.series;                  
	    	for(var i = 0; i < series.length; i++) {  
	    	     for(var j = 0; j< result.data.length; j++){
	    	    	 var name = series[i].name
	    	    	 if(result.data[j].name == name){
	    	    		 chartsDiv3.series[i].setData(result.data[j].data);
	    	    	 }
	    	     }
	    	}
	    }
	});
	//借阅利用
	var chartsDiv2 = $('#chartsDiv2').highcharts();
	$.ajax({
	    type: "post",
	    url: "${ctx}/platDocUse/findUseAllCountByMonth",
	    dataType: "json",
	    success: function (result) {	
	    	chartsDiv2.xAxis[0].setCategories(result.months);
	    	chartsDiv2.series[0].setData(result.uses);
	    	chartsDiv2.series[1].setData(result.borrows);
	    	chartsDiv2.series[2].setData(result.totals);
	    }
	});
	
	//库房温湿度
	var chartsDiv10 = $('#chartsDiv10').highcharts();
	chartsDiv10.showLoading('正在加载，请稍后...');
	$.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getRoomTHStatics",
	    dataType: "json",
	    success: function (result) {
	    	chartsDiv10.hideLoading();
	    	chartsDiv10.xAxis[0].setCategories(result.categories);
	    	chartsDiv10.series[0].setData(result.temperature);
	    	chartsDiv10.series[1].setData(result.humidity);
	    }
	});
	//报警统计
	var chartsDiv11 = $('#chartsDiv11').highcharts();
	$.ajax({
	    type: "post",
	    url: "${ctx}/platDriveManage/getAlarmStatics",
	    dataType: "json",
	    success: function (result) {	
	    	chartsDiv11.xAxis[0].setCategories(result.categories);
	    	chartsDiv11.series[0].setData(result.data);
	    }
	});
	
}


function maxWin(){
	var u = "${ctx}/platDriveManage/toDriveManage";
	window.open(u, '驾驶舱','width='+(window.screen.availWidth-500)+',height='+(window.screen.availHeight-100)+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no,fullscreen=yes',false);
}

function closeWin(){
	window.close();
}

</script>
</body>
</html>