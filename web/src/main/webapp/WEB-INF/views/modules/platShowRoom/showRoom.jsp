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
<title>展厅</title>
</head>
<body>
	<div
		style="width: 100%; padding-top: 10px; margin: 0;">
		<div id="chartsDiv1"
			style="min-width: 1167px; height: 400px; float: left; margin: 10px 10px 10px 10px;"></div>
		<div id="chartsDiv2"
			style="min-width: 1167px; height: 400px; float: left; margin: 10px 10px 10px 10px;"></div>
		<div id="chartsDiv3"
			style="min-width: 1167px; height: 438px; float: left; margin: 10px 10px 10px 10px;"></div>	
	</div>
	<script type="text/javascript">
		$(function() {
			/**
			 * 展厅管理---折线图
			 */
			$.ajax({
				url : "${ctx}/home/platShowroom/getShowRoomByMonth",
				type : "post",
				dataType : "json",
				success : function(result) {
					Highcharts.chart('chartsDiv1', {
						credits : {
							enabled : false
						},
						chart : {
							backgroundColor : '#dddddd',
							type : 'column'
						},
						title : {
							text : '展厅访问月统计量',
							align : 'left'
						},
						xAxis : {
							type : 'category'
						},
						yAxis : [{
									title : {
										text : '展厅访问人次'
									}
							   	}],
						subtitle : {
							text : ''
						},
						legend : {
							//enabled : true
						   layout: 'vertical',
				            align: 'left',
				            x: 120,
				            verticalAlign: 'top',
				            y: 100,
				            floating: true,
				            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
						},
						plotOptions : {
							series : {
								borderWidth : 0,
								dataLabels : {
									enabled : true,
									format : '{point.y:.0f}'
								}
							}
						},
						tooltip : {
							crosshairs : true,
							shared : true
						},
						series : [ {
							name : '按月访问人次',
							type : "column",
							data : result.data
						}, 
						{
							name : '累计访问人次',
							type : "line",
							data : result.datatotal
						}
						],
						drilldown : {
							series : result.drilldown
						}
					});
				},
				error : function() {
				}
			});

			/**
			 * 
			 */
			$
					.ajax({
						type : "post",
						url : "${ctx}/home/platShowroom/getShowRoomByPlat",
						dataType : "json",
						success : function(result) {
							console.log(result);
							$('#chartsDiv2')
									.highcharts(
											{
												credits : {
													enabled : false
												},
												chart : {
													backgroundColor: '#dddddd',
													type : 'column'
												},
												title : {
													text : '展台访问量',
													align : 'left'
												},
												xAxis : {
													categories : result.categories,
													crosshair : true
												},
												yAxis : {
													min : 0,
													title : {
														text : '人次'
													}
												},
												plotOptions: {
											            series: {
											                colorByPoint: true 
											            }
											    },
												tooltip : {
													headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
													pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
															+ '<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
													footerFormat : '</table>',
													shared : true,
													useHTML : true
												},
												series : [{
													name : '展台访问人次',
													type : "column",
													data : result.data
												}]
											});
						},
						error : function() {
						}
					});

		});
		$(function () {
		    $('#chartsDiv3').highcharts({
		        chart: {
		            type: 'scatter',
		            zoomType: 'xy',
		            backgroundColor: '#dddddd',
		            plotBackgroundImage:'${ctx}/static/images/danganguan.png',
		        },
		        title: {
		            text: '展厅人员分布图',
		            align : 'left'
		        },
		        subtitle: {
		            text: ''
		        },
		        xAxis: {
		            title: {
		                enabled: true,
		                text: '展厅横向长度(m)'
		            },
		            startOnTick: false,
		            endOnTick: false,
		            showLastLabel: false
		        },
		        yAxis: {
		            title: {
		                text: '展厅纵向长度(m)'
		            }
		        },
 		        legend: {
 		        	enabled : false
						//enabled : true
		            /* layout: 'vertical',
		            align: 'left',
		            verticalAlign: 'top',
		            x: 100,
		            y: 70,
		            floating: true,
		            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
		            borderWidth: 1 */
		        }, 
		        plotOptions: {

		            series: {
		                colorByPoint: true 
		            },
		        	scatter: {
		                marker: {
		                    radius: 3,
		                    states: {
		                        hover: {
		                            enabled: true,
		                            lineColor: 'rgb(100,100,100)'
		                        }
		                    }
		                },
		                states: {
		                    hover: {
		                        marker: {
		                            enabled: false
		                        }
		                    }
		                },
		                tooltip: {
		                    headerFormat: '<b>{series.name}</b><br>',
		                    pointFormat: '位置：{point.x} m, {point.y} m'
		                }
		            }
		        },
		        series: [{
		            name: '女',
		            data: [[6.2, 5.6], [6.5, 5.0], [5.5, 4.2], [5.0, 6.0], [5.8, 5.6],
		            	[7.0, 5.0], [5.1, 4.6], [6.0, 6.8], [7.2, 6.8], [6.2, 7.2],
		            	[7.5, 5.2], [7.9, 5.2], [7.9, 6.5], [5.4, 4.0], [6.0, 5.0],
		            	[4.2, 4.8], [6.2, 4.2], [7.0, 7.2], [5.0, 4.8], [6.6, 6.8],
		            	[5.5, 5.6], [7.0, 8.5], [6.8, 5.2], [7.5, 8.8], [7.2, 7.8],
		            	[7.0, 5.5], [7.0, 5.8], [7.9, 6.3], [7.5, 6.8], [6.0, 4.0],
		            	[5.4, 4.2], [6.0, 5.0], [7.5, 8.0], [6.0, 5.4], [5.0, 4.8],
		            	[6.1, 5.6], [7.0, 7.2], [6.2, 5.1], [6.3, 6.9], [6.4, 5.6],
		            	[6.9, 6.3], [6.8, 5.5], [6.6, 5.5], [6.0, 5.2], [6.3, 6.3],
		            	[6.6, 5.3], [6.1, 5.2], [6.0, 5.2], [7.0, 7.9], [5.5, 5.8],
		            	[6.6, 6.0], [6.7, 6.1], [6.2, 5.9], [5.4, 4.5], [5.5, 5.3],
		            	[6.3, 5.8], [8.3, 6.7], [6.5, 6.0], [6.0, 6.0], [6.5, 6.3],
		            	[5.0, 5.7], [6.0, 7.3], [6.0, 6.0], [6.7, 7.1], [6.0, 8.0],
		            	[6.0, 5.7], [6.0, 5.2], [7.0, 7.7], [7.7, 6.1], [6.6, 5.7],
		            	[5.1, 4.7], [6.5, 5.3], [6.5, 5.0], [5.0, 5.3], [6.0, 6.5],
		            	[6.0, 5.7], [6.2, 5.8], [5.0, 4.9], [7.0, 7.6], [7.2, 6.2],
		            	[7.0, 6.4], [6.5, 5.2], [7.3, 6.8], [6.1, 7.6], [6.5, 5.8],
		            	[6.2, 5.8], [5.5, 4.0], [5.8, 5.0], [7.2, 6.2], [7.0, 5.9],
		            	[6.4, 6.4], [6.0, 5.2], [6.2, 5.6], [5.4, 4.7], [6.5, 5.2],
		            	[5.0, 4.6], [6.8, 5.8], [5.0, 5.6], [7.8, 6.8], [6.9, 5.4],
		            	[6.0, 5.6], [5.1, 7.2], [6.2, 5.4], [6.9, 6.0], [7.2, 5.4],
		            	[7.8, 5.2], [7.0, 7.6], [6.3, 5.8], [6.0, 7.0], [6.5, 6.2],
		            	[5.5, 4.6], [6.6, 6.0], [6.2, 5.2], [6.0, 4.0], [6.2, 5.0],
		            	[6.2, 5.2], [6.3, 6.2], [4.5, 4.8], [5.5, 5.8], [6.2, 5.4],
		            	[7.7, 6.0], [5.0, 4.2], [5.5, 6.2], [6.0, 5.8], [6.9, 5.4],
		            	[6.8, 5.0], [6.0, 5.8], [6.0, 5.8], [6.0, 4.2], [6.9, 6.5],
		            	[5.2, 4.4], [5.0, 6.4], [6.0, 4.8], [6.1, 6.2], [5.0, 5.5],
		            	[6.6, 5.8], [5.0, 5.6], [6.1, 5.2], [7.4, 5.7], [5.8, 5.2],
		            	[7.5, 6.5], [5.2, 5.8], [5.5, 5.0], [6.3, 6.6], [6.6, 6.2],
		            	[6.0, 5.5], [6.9, 5.8], [6.1, 6.1], [6.6, 5.0], [6.1, 7.3],
		            	[6.4, 5.0], [6.0, 5.9], [5.4, 6.4], [7.2, 6.1], [6.6, 8.5],
		            	[7.2, 5.9], [5.8, 5.5], [7.7, 6.5], [6.6, 7.4], [6.6, 6.4],
		            	[6.6, 6.9], [5.2, 5.6], [7.2, 6.8], [7.1, 5.6], [6.6, 5.6],
		            	[6.0, 5.9], [6.1, 5.1], [8.9, 8.8], [6.4, 7.7], [6.1, 5.8],
		            	[7.8, 6.0], [6.1, 5.2], [7.3, 7.7], [5.9, 5.1], [5.8, 4.1],
		            	[7.7, 7.9], [6.9, 5.0], [6.3, 5.3], [6.6, 5.0], [6.1, 6.5],
		            	[7.3, 6.5], [5.5, 4.6], [6.8, 5.6], [6.6, 6.6], [6.1, 5.2],
		            	[6.1, 6.7], [6.9, 5.6], [6.6, 5.9], [6.5, 6.2], [7.5, 7.6],
		            	[6.9, 6.0], [7.3, 6.6], [5.4, 5.2], [6.0, 5.4], [7.2, 5.0],
		            	[6.6, 7.5], [6.6, 5.5], [6.6, 5.5], [6.7, 5.9], [6.0, 5.0],
		            	[5.5, 6.6], [6.6, 5.5], [5.4, 4.3], [7.2, 6.7], [6.1, 8.9],
		            	[7.7, 7.5], [6.1, 6.9], [7.2, 6.6], [7.2, 5.5], [7.2, 5.1],
		            	[6.3, 7.5], [6.6, 5.7], [6.6, 6.7], [6.1, 8.3], [6.6, 6.4],
		            	[5.4, 6.3], [6.9, 6.0], [7.2, 7.6], [7.2, 6.3], [7.2, 5.7],
		            	[6.6, 6.5], [6.6, 7.3], [6.6, 6.4], [5.9, 5.2], [6.6, 8.8],
		            	[7.3, 6.6], [7.4, 5.4], [5.5, 5.5], [6.1, 5.6], [6.0, 6.0],
		            	[7.0, 7.6], [6.6, 6.4], [7.0, 5.5], [6.6, 6.6], [6.3, 6.9],
		            	[5.2, 6.0], [4.9, 4.8], [6.5, 5.3], [6.0, 6.1], [7.3, 6.6],
		            	[6.5, 6.3], [6.0, 7.5], [7.7, 6.2], [6.6, 6.4], [5.5, 7.8],
		            	[7.5, 7.8], [6.4, 5.5], [6.7, 4.6], [7.0, 6.4], [6.8, 6.3]]
		        }, {
		            name: '男',
		            data: [[7.0, 6.6], [7.3, 7.8], [9.5, 8.7], [8.5, 7.6], [8.2, 7.8],
		            	[8.5, 7.8], [8.0, 8.4], [8.5, 7.4], [7.0, 6.0], [8.0, 8.6],
		            	[8.0, 7.6], [7.8, 8.6], [9.0, 9.0], [7.0, 7.6], [7.0, 7.0],
		            	[8.0, 7.6], [9.7, 9.8], [7.5, 7.0], [7.0, 7.4], [7.0, 8.9],
		            	[7.0, 7.8], [8.5, 7.8], [7.7, 6.2], [7.0, 8.4], [7.5, 8.8],
		            	[7.0, 8.6], [8.3, 8.8], [8.3, 7.4], [6.5, 6.2], [7.0, 6.9],
		            	[8.5, 7.8], [7.5, 7.0], [8.0, 7.4], [8.2, 8.1], [7.8, 6.1],
		            	[7.0, 5.5], [8.0, 6.2], [7.0, 6.3], [7.8, 6.6], [8.2, 8.1],
		            	[8.7, 8.8], [7.4, 8.7], [7.7, 7.4], [7.3, 7.1], [8.3, 8.6],
		            	[8.9, 8.7], [8.0, 8.1], [7.2, 9.1], [7.1, 7.9], [6.0, 5.1],
		            	[6.5, 7.6], [7.0, 8.2], [7.7, 7.3], [8.2, 8.1], [6.1, 5.2],
		            	[6.0, 5.0], [7.5, 6.4], [8.2, 7.8], [7.0, 8.8], [7.0, 7.2],
		            	[7.0, 7.6], [8.0, 8.8], [6.0, 6.2], [7.8, 6.1], [8.0, 7.0],
		            	[7.2, 7.1], [8.6, 7.9], [6.4, 6.7], [8.1, 6.0], [7.0, 6.2],
		            	[7.5, 6.9], [7.5, 7.0], [7.5, 5.8], [8.4, 7.5], [9.1, 9.9],
		            	[8.1, 9.0], [7.5, 8.9], [8.6, 7.7], [8.4, 6.0], [7.5, 7.9],
		            	[8.6, 7.5], [7.0, 7.5], [7.1, 8.4], [8.6, 7.5], [7.5, 7.0],
		            	[7.5, 6.4], [6.4, 6.9], [8.1, 7.7], [7.8, 8.5], [7.3, 8.7],
		            	[8.9, 8.4], [7.3, 7.2], [6.4, 5.9], [7.1, 7.0], [6.9, 5.5],
		            	[5.2, 5.4], [8.3, 8.2], [7.2, 7.7], [7.8, 6.1], [7.7, 7.3],
		            	[6.1, 6.0], [8.7, 8.4], [6.1, 6.0], [7.0, 8.6], [7.3, 8.1],
		            	[8.4, 6.8], [7.8, 7.5], [8.3, 9.2], [8.3, 8.7], [7.8, 5.0],
		            	[8.0, 8.6], [9.1, 8.5], [7.3, 9.9], [6.4, 8.9], [9.5, 8.1],
		            	[6.4, 7.0], [7.8, 7.7], [7.7, 8.4], [7.7, 9.9], [9.5, 7.6],
		            	[8.4, 7.4], [6.9, 6.1], [6.6, 8.5], [7.3, 6.5], [7.2, 6.1],
		            	[7.5, 8.2], [7.8, 7.0], [8.3, 7.4], [7.4, 7.7], [7.7, 8.1],
		            	[7.7, 7.8], [7.8, 6.6], [7.8, 8.9], [8.9, 8.9], [7.2, 8.5],
		            	[7.7, 9.9], [9.5, 8.1], [7.1, 8.3], [7.3, 7.8], [7.2, 6.9],
		            	[9.0, 9.9], [7.4, 9.4], [7.8, 8.8], [7.8, 9.8], [6.6, 6.1],
		            	[6.6, 8.7], [8.3, 7.5], [8.9, 7.5], [7.5, 7.6], [8.7, 9.8],
		            	[8.0, 8.1], [8.0, 8.9], [7.8, 8.8], [7.0, 8.5], [7.8, 8.5],
		            	[7.4, 7.0], [8.4, 8.8], [8.4, 8.1], [8.0, 9.5], [8.0, 9.4],
		            	[8.9, 8.1], [7.5, 8.0], [7.3, 6.1], [7.3, 7.6], [8.0, 8.5],
		            	[8.0, 8.7], [7.3, 8.4], [7.5, 6.7], [7.1, 9.7], [7.8, 9.6],
		            	[7.3, 7.9], [8.9, 7.0], [7.8, 9.2], [8.0, 9.2], [8.3, 7.7],
		            	[7.8, 6.4], [8.4, 9.1], [6.9, 7.0], [8.4, 8.6], [8.3, 8.5],
		            	[7.0, 7.9], [6.6, 6.8], [8.9, 8.3], [6.0, 7.3], [8.3, 8.6],
		            	[7.3, 8.8], [7.1, 7.5], [8.6, 8.5], [7.8, 7.6], [8.9, 8.0],
		            	[7.0, 7.9], [6.6, 6.5], [7.2, 7.3], [6.6, 7.3], [8.0, 8.3],
		            	[7.0, 8.0], [7.5, 8.3], [8.3, 7.6], [6.6, 7.1], [8.0, 8.9],
		            	[8.3, 7.2], [6.6, 7.3], [8.0, 6.9], [8.0, 9.9], [7.1, 8.1],
		            	[7.2, 6.3], [7.8, 8.7], [7.1, 7.1], [9.5, 9.2], [7.8, 8.1],
		            	[8.3, 8.2], [8.3, 8.2]]
		        }]
		    });
		});

	</script>
</body>
</html>