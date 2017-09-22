<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highstock.js"></script>
	<script src="${ctx}/static/layer/layer.js" type="text/javascript"></script>
	<style type="text/css">
		.row-fluid {
			padding-top: 10px;
		}
		.container-fluid {
		    padding-right: 5px;
		    padding-left: 5px;
		}
		.exportDate1 {
			width: 35px;
			height: 30px;
			position:absolute;
			margin-left: 45%;
			margin-top: -285px;
			z-index: 9;
		}
		.exportDate2 {
		    width: 35px;
		    height: 30px;
		    position: absolute;
		    margin-left: 95%;
		    margin-top: -285px;
		    z-index: 9;
		}
		#td{
			text-align:center;  
		}
	</style>
<script type="text/javascript">
	//馆藏类型列表
	function openList1(){
		$.ajax({
		    type: "post",
		    url: "${ctx}/platDocStore/GrossDataCharts",
		    dataType: "json",
		    success: function (result) {
		    	var html = "<form action='' method='post' class='breadcrumb form-search'>"+
		    				"<input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList1()'>"+
		    				"</form><div style='overflow-y:scroll;height:330px'>"+
		    				"<table id='contentTable' class='table table-striped table-bordered table-condensed'>"+
		    				"<tr><th>档案类型</th><th>实物</th><th>电子</th></tr>";
				var personalHtml = "";
					for (var i=0;i<result.categories.length;i++) {
						personalHtml = personalHtml + "<tr><td id='td'>"+result.categories[i]+"</td><td id='td'>"+result.list2[i]+"</td><td id='td'>"+result.list3[i]+"</td></tr>";
					}
					
					html = html + personalHtml + "</table></div>";
					$.jBox(html, {
					    title: "馆藏类型列表",
					    width: 900,
					    top: 15,
					    height: 500,
					    buttons: {"关闭":true}
					});
		    	}
		})
	};
	function exportList1() {
		var download_file = $("#download_file");
		if (download_file.length == 0) {
			var i = '<iframe id="download_file" name="download_file"></iframe>';
			$("body").append(i);
			download_file = $("#download_file");
		}
		var url = "${ctx}/platDocStore/export";
		download_file.attr("src", url);
		download_file.css("display", "none");
	}
	
	//藏量与容量 列表
	function openList2(){
		$.ajax({
		    type: "post",
		    url: "${ctx}/platDocStore/getChartsCapacityData",
		    dataType: "json",
		    success: function (result) {
		    	var html = "<form action='' method='post' class='breadcrumb form-search'>"+
		    				"<input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList2()'>"+
		    				"</form><div style='overflow-y:scroll;height:330px'>"+
		    				"<table id='contentTable' class='table table-striped table-bordered table-condensed'>"+
		    				"<tr><th>档案馆</th><th>移交</th><th>未移交</th><th>总和</th></tr>";
				var personalHtml = "";
					for (var i=0;i<result.categories.length;i++) {
						personalHtml = personalHtml + "<tr><td id='td'>"+result.categories[i]+"</td><td id='td'>"+
						result.inLibrary[i]+"</td><td id='td'>"+result.issue[i]+"</td><td id='td'>"+(result.inLibrary[i]+result.issue[i])+"</td></tr>";
					}
					
					html = html + personalHtml + "</table></div>";
					$.jBox(html, {
					    title: "藏量容量列表",
					    width: 900,
					    top: 15,
					    height: 500,
					    buttons: {"关闭":true}
					});
		    	}
		})
	};
	function exportList2() {
		var download_file = $("#download_file");
		if (download_file.length == 0) {
			var i = '<iframe id="download_file" name="download_file"></iframe>';
			$("body").append(i);
			download_file = $("#download_file");
		}
		var url = "${ctx}/platDocStore/export2";
		download_file.attr("src", url);
		download_file.css("display", "none");
	}
	
	//历年新增量列表
	function openList3(){
		$.ajax({
		    type: "post",
		    url: "${ctx}/platDocStore/getChartsIncrementData",
		    dataType: "json",
		    success: function (result) {
		    	var html = "<form action='' method='post' class='breadcrumb form-search'>"+
		    				"<input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList3()'>"+
		    				"</form><div style='overflow-y:scroll;height:330px'>"+
		    				"<table id='contentTable' class='table table-striped table-bordered table-condensed'>"+
		    				"<tr><th>年份</th><th>新增实体数量</th><th>新增电子数量</th></tr>";
				var personalHtml = "";
					for (var i=0;i<result.categories.length;i++) {
						personalHtml = personalHtml + "<tr><td id='td'>"+result.categories[i]+"</td><td id='td'>"+result.list2[i]+"</td><td id='td'>"+result.list3[i]+"</td></tr>";
					}
					
					html = html + personalHtml + "</table></div>";
					$.jBox(html, {
					    title: "历年新增量列表",
					    width: 900,
					    top: 15,
					    height: 500,
					    buttons: {"关闭":true}
					});
		    	}
		})
	};
	function exportList3() {
		var download_file = $("#download_file");
		if (download_file.length == 0) {
			var i = '<iframe id="download_file" name="download_file"></iframe>';
			$("body").append(i);
			download_file = $("#download_file");
		}
		var url = "${ctx}/platDocStore/export3";
		download_file.attr("src", url);
		download_file.css("display", "none");
	}
	
	//新入库量列表
	function openList4(){
		$.ajax({
		    type: "post",
		    url: "${ctx}/platDocStore/getChartsStorageData",
		    dataType: "json",
		    success: function (result) {
		    	var html = "<form action='' method='post' class='breadcrumb form-search'>"+
		    				"<input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList4()'>"+
		    				"</form><div style='overflow-y:scroll;height:330px'>"+
		    				"<table id='contentTable' class='table table-striped table-bordered table-condensed'>"+
		    				"<tr><th>档案馆名称</th><th>今年入库量</th></tr>";
				var personalHtml = "";
					for (var i=0;i<result.categories.length;i++) {
						personalHtml = personalHtml + "<tr><td id='td'>"+result.categories[i]+"</td><td id='td'>"+result.list4[i]+"</td></tr>";
					}
					
					html = html + personalHtml + "</table></div>";
					$.jBox(html, {
					    title: "新入库量列表",
					    width: 900,
					    top: 15,
					    height: 500,
					    buttons: {"关闭":true}
					});
		    	}
		})
	};
	function exportList4() {
		var download_file = $("#download_file");
		if (download_file.length == 0) {
			var i = '<iframe id="download_file" name="download_file"></iframe>';
			$("body").append(i);
			download_file = $("#download_file");
		}
		var url = "${ctx}/platDocStore/export4";
		download_file.attr("src", url);
		download_file.css("display", "none");
	}
	
	
	//密级列表
	function openList5(){
		$.ajax({
		    type: "post",
		    url: "${ctx}/platDocStore/getStoragePeriod?type=2",
		    dataType: "json",
		    success: function (result) {
		    	var html = "<form action='' method='post' class='breadcrumb form-search'>"+
		    				"<input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList5()'>"+
		    				"</form><div style='overflow-y:scroll;height:330px'>"+
		    				"<table id='contentTable' class='table table-striped table-bordered table-condensed'>"+
		    				"<tr><th>密级</th><th>占比</th></tr>";
				var personalHtml = "";
					for (var i=0;i<result.length;i++) {
						personalHtml = personalHtml + "<tr><td id='td'>"+result[i].label+"</td><td id='td'>"+(result[i].value*100).toFixed(1)+"%"+"</td></tr>";
					}
					
					html = html + personalHtml + "</table></div>";
					$.jBox(html, {
					    title: "密级比例列表",
					    width: 900,
					    top: 15,
					    height: 500,
					    buttons: {"关闭":true}
					});
		    	}
		})
	};
	function exportList5() {
		var download_file = $("#download_file");
		if (download_file.length == 0) {
			var i = '<iframe id="download_file" name="download_file"></iframe>';
			$("body").append(i);
			download_file = $("#download_file");
		}
		var url = "${ctx}/platDocStore/export5?type=2";
		download_file.attr("src", url);
		download_file.css("display", "none");
	}
	
	
	//保管期限列表
	function openList6(){
		$.ajax({
		    type: "post",
		    url: "${ctx}/platDocStore/getStoragePeriod?type=1",
		    dataType: "json",
		    success: function (result) {
		    	var html = "<form action='' method='post' class='breadcrumb form-search'>"+
		    				"<input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList6()'>"+
		    				"</form><div style='overflow-y:scroll;height:330px'>"+
		    				"<table id='contentTable' class='table table-striped table-bordered table-condensed'>"+
		    				"<tr><th>保管期限</th><th>占比</th></tr>";
				var personalHtml = "";
					for (var i=0;i<result.length;i++) {
						personalHtml = personalHtml + "<tr><td id='td'>"+result[i].label+"</td><td id='td'>"+(result[i].value*100).toFixed(1)+"%"+"</td></tr>";
					}
					
					html = html + personalHtml + "</table></div>";
					$.jBox(html, {
					    title: "保管期限比例列表",
					    width: 900,
					    top: 15,
					    height: 500,
					    buttons: {"关闭":true}
					});
		    	}
		})
	};
	function exportList6() {
		var download_file = $("#download_file");
		if (download_file.length == 0) {
			var i = '<iframe id="download_file" name="download_file"></iframe>';
			$("body").append(i);
			download_file = $("#download_file");
		}
		var url = "${ctx}/platDocStore/export6?type=1";
		download_file.attr("src", url);
		download_file.css("display", "none");
	}
</script>
</head>
<body>
	<div class="container-fluid" style="border: 1px solid #ddd;background-color: #ddd;">
		<div class="row-fluid" style="padding-top: 5px;">
			<div class="span6" >
				<div id="docStore"  style="height:300px;"></div>
				<a id="" href="javascript:void(0)" onclick="openList1()" class="exportDate1">列表</a>
			</div>
			<div class="span6" >
				<div id="quantity"  style="height:300px;"></div>  
				<a id="" href="javascript:void(0)" onclick="openList2()" class="exportDate1">列表</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12" >
				<div id="newly"  style="height:300px;"></div>
				<a id="" href="javascript:void(0)" onclick="openList3()" class="exportDate2">列表</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12" >
				<div id="deptDoc" style="height:300px;"></div>
				<a id="" href="javascript:void(0)" onclick="openList4()" class="exportDate2">列表</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6" >
				<div id="chartsDiv3" style="height:300px;"></div>
				<a id="" href="javascript:void(0)" onclick="openList5()" class="exportDate1">列表</a>
			</div>
			<div class="span6" >
				<div id="chartsDiv4" style="height:300px;"></div> 
				<a id="" href="javascript:void(0)" onclick="openList6()" class="exportDate1">列表</a> 
			</div>
		</div>
	</div>
	
<script type="text/javascript">

//藏量 
$.ajax({
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
                text: '馆藏类型分布图<span style="font-size:12px;font-weight:bold;"></span>',
                align: 'left'
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
    


//藏量与容量 
$.ajax({
    type: "post",
    url: "${ctx}/platDocStore/getChartsCapacityData",
    dataType: "json",
    success: function (result) {
    	
    	$('#quantity').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '馆藏容量<span style="font-size:12px;font-weight:bold;"></span>',
                align: 'left'
            },
            scrollbar: {enabled:true},
            xAxis: {
            	 min:0, //别忘了这里
                 max:10,
                categories:result.categories
            },
            scrollbar: {
                enabled: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                stackLabels: {
                    enabled: true
                }
            },
            legend: { 
            	//layout: 'vertical', 
            	 align: 'center', 
            	 verticalAlign: 'top'
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                        this.series.name +': '+ this.y +'<br/>'+
                        '总共: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal'
                }
            },
            series: [{
                name: '移交',
                color: '#b1e3ff',
                data: result.inLibrary
            }, {
                name: '未移交',
                color: '#01a75f',
                data:result.issue
            }]
        });
        /* $('#quantity').highcharts({
        	credits: { enabled: false},
        	chart: {
                type: 'line'
            },
            title: {
                text: '馆藏容量'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: result.categories
            },
            yAxis: {
                title: {
                    text: '数量(/卷)'
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true          // 开启数据标签
                    },
                    enableMouseTracking: true // 关闭鼠标跟踪，对应的提示框、点击事卷会失效
                }
            },
            series:result.data
        }); */	
    },
	error:function () {}
}); 

//历年新增数量 
$.ajax({
    type: "post",
    url: "${ctx}/platDocStore/getChartsIncrementData",
    dataType: "json",
    success: function (result) {
        $('#newly').highcharts({
        	credits: { enabled: false},
        	chart: {
                type: 'line'
            },
            title: {
                text: '历年新增量<span style="font-size:12px;font-weight:bold;"></span>',
                align: 'left'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: result.categories,
                crosshair: true
            },
            yAxis: {
                title: {
                    text: '数量(/卷)'
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true          // 开启数据标签
                    },
                    enableMouseTracking: true // 关闭鼠标跟踪，对应的提示框、点击事件会失效
                }
            },
            series:result.data
        });	
    },
	error:function () {}
}); 

//新入库量
 $.ajax({
    type: "post",
    url: "${ctx}/platDocStore/getChartsStorageData",
    dataType: "json",
    success: function (result) {
        $('#deptDoc').highcharts({
        	chart: {
                type: 'column'
            },
            scrollbar: {enabled:true},
            title: {
                text: '新入库数量<span style="font-size:12px;font-weight:bold;"></span>',
                align: 'left'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
            	 min:0, 
                 max:10,
            	categories:result.categories ,
            },
            scrollbar: {
                enabled: true
            },

            yAxis: {
                min: 0,
                title: {
                    text: '数量 (/卷)'
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} 卷</b></td></tr>',
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
	    			    url: "${ctx}/platDocStore/getStoragePeriod?type=1",
	    			    dataType: "json",
	    			    success: function (result) {
	    			    	//console.log(result);
	    			    	var data = new Array();
	    			    	for (var i = 0; i < result.length; i++) {
	    			    		data.push([result[i].label, result[i].value]);
	    			    	}
	    			    	//alert(bb);
	    			    	chartsDiv4.series[0].setData(data);
	    			    }
	    			})
	    		}
	    	}
		},
		title : {
			text : '保管期限<span style="font-size:12px;font-weight:bold;"></span>',
			align: 'left'
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
	    			    url: "${ctx}/platDocStore/getStoragePeriod?type=2",
	    			    dataType: "json",
	    			    success: function (result) {
	    			    	//console.log(result);
	    			    	var data = new Array();
	    			    	for (var i = 0; i < result.length; i++) {
	    			    		data.push([result[i].label, result[i].value]);
	    			    	}
	    			    	//alert(bb);
	    			    	chartsDiv3.series[0].setData(data);
	    			    }
	    			})
	    		}
	    	}
		},
		title : {
			text : '密级<span style="font-size:12px;font-weight:bold;"></span>',
			align: 'left'
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
/* Highcharts.chart('deptDoc', {
	chart: {
        type: 'column'
       
    },
    scrollbar: {enabled:true},
    title: {
        text: '新入库数量'
    },
    xAxis: {
    	max: 20,
        categories:result.categories ,
    },
    yAxis: {
        min: 0,
        title: {
            text: '数量 (/卷)'
        },
        stackLabels: {
            enabled: false,
            style: {
                fontWeight: 'bold'
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
    tooltip: {
        formatter: function () {
            return '<b>' + this.x + '</b><br/>' +
                this.series.name + ': ' + this.y + '<br/>' ;
        }
    },
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
        name: '新入库数量',
        data: result.data
    }]
}); */
</script>

</body>
</html>