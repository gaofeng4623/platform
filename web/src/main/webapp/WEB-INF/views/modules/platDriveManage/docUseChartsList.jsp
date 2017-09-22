<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
<title>档案利用</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highstock.js"></script>
	<script src="${ctx}/static/plat/highCharts/highcharts-3d.js"></script>
	<script src="${ctx}/static/plat/highCharts/data.js"></script>
	<script src="${ctx}/static/plat/highCharts/drilldown.js" type="text/JavaScript" charset="UTF-8"></script>
	<script src="${ctx}/static/layer/layer.js"></script>
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
<div class="container-fluid"  style="border: 1px solid #ddd;background-color: #ddd;">
	<div class="row-fluid" >
		<div id="chartsDiv1" class="span12" style="height: 260px;"></div>
	</div>
	<div class="row-fluid" >
		<div id="chartsDiv2" class="span12" style="height: 260px;"></div>
	</div>
	<div class="row-fluid" >
		<div id="chartsDiv3" class="span12" style="height: 260px;"></div>
	</div>
	<div class="row-fluid" >
		<div id="chartsDiv4" class="span12" style="height: 260px;"></div>
	</div>
	<div class="row-fluid" >
		<div id="chartsDiv5" class="span12" style="height: 260px;"></div>
	</div>
	<div class="row-fluid" >
		<div id="chartsDiv6" class="span12" style="height: 260px;"></div>
	</div>
	<div class="row-fluid" >
		<div id="chartsDiv7" class="span12" style="height: 260px;"></div>
	</div>
</div>
<!-- <div style="width:100%;background-color:#dddddd;min-height:1650px;padding-top:10px;margin:0;">
    <div id="chartsDiv1" style="min-width: 600px; height: 400px; float:left; margin:0 0 0 10px;"></div>  
    <div id="chartsDiv2" style="min-width: 600px; height: 400px; float:left; margin:0 0 0 10px;"></div>  
    <div id="chartsDiv3" style="min-width: 600px; height: 400px; float:left; margin:10px 0 0 10px;"></div>  
    <div id="chartsDiv4" style="min-width: 600px; height: 400px; float:left; margin:10px 0 0 10px;"></div>
    <div id="chartsDiv5" style="min-width: 1210px; height: 400px; float:left; margin:10px 0 10px 10px;"></div>
    <div id="chartsDiv6" style="min-width: 600px; height: 400px; float:left; margin:10px 0 10px 10px;"></div>
    <div id="chartsDiv7" style="min-width: 600px; height: 400px; float:left; margin:10px 0 10px 10px;"></div>
</div> -->
<script type="text/javascript">
$(function(){
	
	
	var chartsDiv1 = Highcharts.chart('chartsDiv1', {
		title: {
	        text: '档案利用<span style="font-size:12px;font-weight:bold;">[利用总量3130]</span>',
	        align:'left'
	    },
	    chart : {
	    	events: {
	    		load: function() {
	    			$.ajax({
	    			    type: "post",
	    			    url: "${ctx}/platDocUse/findUseCountByMonthUserType",
	    			    dataType: "json",
	    			    success: function (result) {
	    			    	chartsDiv1.xAxis[0].setCategories(result.months);
	    			    	chartsDiv1.series[0].setData(result.use01);
	    			    	chartsDiv1.series[1].setData(result.use02);
	    			    	chartsDiv1.title.update({ text: '档案利用<span id="useInfo" style="font-size:12px;font-weight:bold;">[利用总量'+result.total+']</span>' });
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
	    },
	    tooltip: {
	        valueSuffix: '卷'
	    },
	    series: [{
   	        name: '个人利用',
   	     	data:[]
   	        //data: [117, 126, 96, 87, 120, 100, 78, 82, 142, 89, 63, 56]
   	    }, {
   	        name: '单位利用',
   	     	data:[]
   	        //data: [217, 226, 196, 187, 220, 130, 178, 182, 172, 189, 163, 156]
   	    }]
   	});
	
	var chartsDiv2 = Highcharts.chart('chartsDiv2', {
		
		title: {
	        text: '档案借阅<span style="font-size:12px;font-weight:bold;">[借阅总量6271]</span>',
	        align:'left'
	    },
	    chart : {
	    	events: {
	    		load: function() {
	    			$.ajax({
	    			    type: "post",
	    			    url: "${ctx}/platDocUse/findBorrowCountByMonthApplyType",
	    			    dataType: "json",
	    			    success: function (result) {
	    			    	chartsDiv2.xAxis[0].setCategories(result.months);
	    			    	chartsDiv2.series[0].setData(result.apply1);
	    			    	chartsDiv2.series[1].setData(result.apply2);
	    			    	chartsDiv2.title.update({ text: '档案借阅<span id="useInfo" style="font-size:12px;font-weight:bold;">[借阅总量'+result.total+']</span>' });
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
	    plotOptions: {
	    },
	    tooltip: {
	        valueSuffix: '卷'
	    },
	    series: [{
   	        name: '电子档案',
   	     	data:[]
   	        //data: [117, 126, 96, 87, 120, 100, 78, 82, 142, 89, 63, 56]
   	    }, {
   	        name: '实物档案',
   	     	data:[]
   	        //data: [217, 226, 196, 187, 220, 130, 178, 182, 172, 189, 163, 156]
   	    }]
   	});

	var chartsDiv3 = Highcharts.chart('chartsDiv3', {
		credits : {
			enabled : false
		},
		chart : {
			backgroundColor : '#ffffff',
			plotBorderWidth : null,
			plotShadow : false,
			type : 'pie',
	    	events: {
	    		load: function() {
	    			$.ajax({
	    			    type: "post",
	    			    url: "${ctx}/platDocUse/findUseCountByUsepurpose",
	    			    dataType: "json",
	    			    success: function (result) {
	    			    	var data = new Array();
	    			    	for (var i = 0; i < result.length; i++) {
	    			    		data.push([result[i].label, result[i].value]);
	    			    	}
	    			    	//console.log(data);
	    			    	chartsDiv3.series[0].setData(data);
	    			    }
	    			})
	    		}
	    	}
		},
		title : {
			text : '利用目的',align:'left'
		},
		tooltip : {
			pointFormat : '<b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : false
				},
				showInLegend : true
			}
		},
		series : [ {
			colorByPoint : true,
			data:[]
			/* data : [ [ '学术研究', 45.0 ], [ '编史修志', 26.8 ], [ '工作察考', 26.8 ],
					[ '经济建设', 8.5 ], [ '宣传教育', 8.5 ], [ '审核工龄', 8.5 ],
					[ '知青回津', 8.5 ], [ '办理公证', 8.5 ], [ '出国签证旅游', 6.2 ],
					[ '补证/离婚/房屋买卖过户/申办公租房/廉租房/限价房等', 0.7 ],
					[ '其他/社险减免/办理低保/提取公积金/失独补助等', 0.7 ] ] */
		} ]
	});
	var chartsDiv4 = Highcharts.chart('chartsDiv4', {
		credits : {
			enabled : false
		},
		chart : {
			backgroundColor : '#ffffff',
			plotBorderWidth : null,
			plotShadow : false,
			type : 'pie',
			events: {
	    		load: function() {
	    			$.ajax({
	    			    type: "post",
	    			    url: "${ctx}/platDocUse/findBorrowCountByUsepurpose",
	    			    dataType: "json",
	    			    success: function (result) {
	    			    	var data = new Array();
	    			    	for (var i = 0; i < result.length; i++) {
	    			    		data.push([result[i].label, result[i].value]);
	    			    	}
	    			    	chartsDiv4.series[0].setData(data);
	    			    }
	    			})
	    		}
	    	}
		},
		title : {
			text : '借阅目的',align:'left'
		},
		tooltip : {
			pointFormat : '<b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : false
				},
				showInLegend : true
			}
		},
		series : [ {
			colorByPoint : true,
			data:[]
			/* data : [ [ '学术研究', 45.0 ], [ '编史修志', 26.8 ], [ '工作察考', 26.8 ],
					[ '经济建设', 8.5 ], [ '宣传教育', 8.5 ], [ '其他', 8.5 ]] */
		} ]
	});
	
	
	var chartsDiv5 = Highcharts.chart('chartsDiv5', {
		chart: {
            type: 'column',
           	events: {
   	    		load: function() {
   	    			$.ajax({
   	    			    type: "post",
   	    			    url: "${ctx}/platDocUse/findUnitCount",
   	    			    dataType: "json",
   	    			    success: function (result) {
   	    			    	chartsDiv5.xAxis[0].setCategories(result.units);
   	    			    	chartsDiv5.series[0].setData(result.uses);
   	    			    	chartsDiv5.series[1].setData(result.borrows);
   	    			    }
   	    			})
   	    		}
   	    	}
        },
        scrollbar: {enabled:true},
        title: {
            text: '全宗查阅统计',align:'left'
        },
        xAxis: {
        	max: 20,
        	showEmpty: false,
        	labels: {
        		style: {
        			color: "#000000",
        			fontSize: "12px"
        		}
        	},
            categories: []
        },
        yAxis: {
            min: 0,
            title: {
                text: '卷数'
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    fontColor: '#000000'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'top',
            y: 25,
            floating: true,
            borderWidth: 1,
            shadow: false
        },
       /*  tooltip: {
            formatter: function () {
                return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + this.y + '<br/>' +
                    '总量: ' + this.point.stackTotal;
            }
        }, */
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    style: {
                        textShadow: '0 0 3px black'
                    }
                }
            }
        },
        series: [{
            name: '利用',
            color: '#b1e3ff',
            data:[]
            //data: [5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1]
        }, {
            name: '借阅',
            color: '#01a75f',
            data:[]
            //data: [5, 3,4,5,6,7,8,9,9,9,9,2,3,4,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1]
        }]
    });
	
	var chartsDiv6 = Highcharts.chart('chartsDiv6', {
		title: {
	        text: '年龄段统计',
	        align:'left'
	    },
	    chart : {
	    	type: 'column',
	    	events: {
	    		load: function() {
	    			$.ajax({
	    			    type: "post",
	    			    url: "${ctx}/platDocUse/findUseAgeRange",
	    			    dataType: "json",
	    			    success: function (result) {
	    			    	chartsDiv6.xAxis[0].setCategories(result.ageRange);
	    			    	chartsDiv6.series[0].setData(result.uses);
	    			    }
	    			})
	    		}
	    	}
		},
	    xAxis: {
	        categories:  []
	    },
	    yAxis: {
	        title: {
	            text: '借阅人数',align:'left'
	        },
	        plotLines: [{
	            value: 0,
	            width: 1
	        }]
	    },
	    tooltip: {
	        valueSuffix: '人'
	    },
	    series: [{
   	        name: '人数',
   	     	data:[]
   	       // data: [117, 126, 96, 87, 120, 100, 78]
   	    }]
	});
	
	
	var chartsDiv7 = Highcharts.chart('chartsDiv7', {
		chart: {
            type: 'column',
           	events: {
   	    		load: function() {
   	    			$.ajax({
   	    			    type: "post",
   	    			    url: "${ctx}/platDocUse/findUseAllCountByYear",
   	    			    dataType: "json",
   	    			    success: function (result) {
   	    			    	chartsDiv7.xAxis[0].setCategories(result.years);
   	    			    	chartsDiv7.series[0].setData(result.uses);
   	    			    	chartsDiv7.series[1].setData(result.borrows);
   	    			    }
   	    			})
   	    		}
   	    	}
        },
        title: {
            text: '借阅利用按年统计',align:'left'
        },
        xAxis: {
        	labels: {
        		style: {
        			color: "#000000",
        			fontSize: "12px"
        		}
        	},
            categories: []
        },
        yAxis: {
            min: 0,
            title: {
                text: '卷数'
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    fontColor: '#000000'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'top',
            y: 25,
            floating: true,
            borderWidth: 1,
            shadow: false
        },
       /*  tooltip: {
            formatter: function () {
                return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + this.y + '<br/>' +
                    '总量: ' + this.point.stackTotal;
            }
        }, */
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    style: {
                        textShadow: '0 0 3px black'
                    }
                }
            }
        },
        series: [{
            name: '利用',
            color: '#b1e3ff',
            data:[]
            //data: [5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1]
        }, {
            name: '借阅',
            color: '#01a75f',
            data:[]
            //data: [5, 3,4,5,6,7,8,9,9,9,9,2,3,4,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1,5, 3,4,5,6,7,8,9,9,9,9,2,0,0,2,5,3,4,1,1]
        }]
    });
});
function openWin(url) {
	layer.open({
  	  type: 2, 
  	  area: ['880px', '600px'],
  	  title: '',
  	  closeBtn: 1,
  	  shadeClose: false,
  	  shade: [0.5 , '#000' , true],
  	  content: url
  	}); 
}
</script>
</body>
</html>