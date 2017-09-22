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
		.exportDate {
			width: 35px;
			height: 30px;
			position:absolute;
			margin-left: 45%;
			margin-top: -385px;
			z-index: 9;
		}
		.exportDate5 {
			width: 35px;
			height: 30px;
			position:absolute;
			margin-left: 95%;
			margin-top: -385px;
			z-index: 9;
		}
	</style>
	<script type="text/javascript">
		function exportDate1(){
			$.ajax({
			    type: "post",
			    url: "${ctx}/platDocUse/findUseCountByMonthUserType",
			    dataType: "json",
			    success: function (result) {
			    	var html = "<form action='' method='post' class='breadcrumb form-search'><input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList1()'></form><div style='overflow-y:scroll;height:330px'><table id='contentTable' class='table table-striped table-bordered table-condensed'><thead><tr><th>类型</th><th>利用数量</th><th>日期</th></tr></thead><tbody>";
					var personalHtml = "";
					var companyHtml = "";
						for (var i=0;i<result.use01.length;i++) {
							personalHtml = personalHtml + "<tr><td>个人利用</td><td>"+result.use01[i]+"</td><td>"+result.months[i]+"</td></tr>";
						}
						for (var i=0;i<result.use02.length;i++) {
							companyHtml = companyHtml + "<tr><td>单位利用</td><td>"+result.use02[i]+"</td><td>"+result.months[i]+"</td></tr>";
						}
						html = html + personalHtml + companyHtml + "</tbody></table></div>";
						$.jBox(html, {
						    title: "档案利用列表",
						    width: 900,
						    top: 15,
						    height: 465,
						    buttons: { '关闭': true}
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
			var url = "${ctx}/platDocUse/export";
			download_file.attr("src", url);
			download_file.css("display", "none");
		}
		function exportDate2(){
			$.ajax({
			    type: "post",
			    url: "${ctx}/platDocUse/findBorrowCountByMonthApplyType",
			    dataType: "json",
			    success: function (result) {
			    	var html = "<form action='' method='post' class='breadcrumb form-search'><input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList2()'></form><div style='overflow-y:scroll;height:330px'><table id='contentTable' class='table table-striped table-bordered table-condensed'><thead><tr><th>类型</th><th>借阅数量</th><th>日期</th></tr></thead><tbody>";
					var personalHtml = "";
					var companyHtml = "";
						for (var i=0;i<result.apply1.length;i++) {
							personalHtml = personalHtml + "<tr><td>电子档案</td><td>"+result.apply1[i]+"</td><td>"+result.months[i]+"</td></tr>";
						}
						for (var i=0;i<result.apply2.length;i++) {
							companyHtml = companyHtml + "<tr><td>实物档案</td><td>"+result.apply2[i]+"</td><td>"+result.months[i]+"</td></tr>";
						}
						html = html + personalHtml + companyHtml + "</tbody></table></div>";
						$.jBox(html, {
						    title: "档案借阅列表",
						    width: 900,
						    top: 15,
						    height: 465,
						    buttons: { '关闭': true}
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
			var url = "${ctx}/platDocUse/export2";
			download_file.attr("src", url);
			download_file.css("display", "none");
		}
		function exportDate3(){
			$.ajax({
			    type: "post",
			    url: "${ctx}/platDocUse/findUseCountByUsepurpose",
			    dataType: "json",
			    success: function (result) {
			    		var html = "<form action='' method='post' class='breadcrumb form-search'><input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList3()'></form><div style='overflow-y:scroll;height:330px'><table id='contentTable' class='table table-striped table-bordered table-condensed'><thead><tr><th>类型</th><th>所占比率</th></tr></thead><tbody>";
						var personalHtml = "";
						for (var i=0;i<result.length;i++) {
							personalHtml = personalHtml + "<tr><td>"+result[i].label+"</td><td>"+result[i].value+"</td></tr>";
						}
						html = html + personalHtml + "</tbody></table></div>";
						$.jBox(html, {
						    title: "利用目的列表",
						    width: 900,
						    top: 15,
						    height: 465,
						    buttons: { '关闭': true}
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
			var url = "${ctx}/platDocUse/export3";
			download_file.attr("src", url);
			download_file.css("display", "none");
		}
		function exportDate4(){
			$.ajax({
			    type: "post",
			    url: "${ctx}/platDocUse/findBorrowCountByUsepurpose",
			    dataType: "json",
			    success: function (result) {
			    		var html = "<form action='' method='post' class='breadcrumb form-search'><input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList4()'></form><div style='overflow-y:scroll;height:330px'><table id='contentTable' class='table table-striped table-bordered table-condensed'><thead><tr><th>类型</th><th>所占比率</th></tr></thead><tbody>";
						var personalHtml = "";
						for (var i=0;i<result.length;i++) {
							personalHtml = personalHtml + "<tr><td>"+result[i].label+"</td><td>"+result[i].value+"</td></tr>";
						}
						html = html + personalHtml + "</tbody></table></div>";
						$.jBox(html, {
						    title: "借阅目的列表",
						    width: 900,
						    top: 15,
						    height: 465,
						    buttons: { '关闭': true}
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
			var url = "${ctx}/platDocUse/export4";
			download_file.attr("src", url);
			download_file.css("display", "none");
		}
		function exportDate5(){
			$.ajax({
			    type: "post",
			    url: "${ctx}/platDocUse/findUnitCount",
			    dataType: "json",
			    success: function (result) {
			    		var html = "<form action='' method='post' class='breadcrumb form-search'><input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList5()'></form><div style='overflow-y:scroll;height:330px'><table id='contentTable' class='table table-striped table-bordered table-condensed'><thead><tr><th>类型</th><th>卷数</th><th>统计单位名称</th></tr></thead><tbody>";
						var useHtml = "";
						var borrowHtml = "";
						for (var i=0;i<result.uses.length;i++) {
							useHtml = useHtml + "<tr><td>利用</td><td>"+result.uses[i]+"</td><td>"+result.units[i]+"</td></tr>";
						}
						for (var i=0;i<result.borrows.length;i++) {
							borrowHtml = borrowHtml + "<tr><td>借阅</td><td>"+result.borrows[i]+"</td><td>"+result.units[i]+"</td></tr>";
						}
						html = html + useHtml + borrowHtml + "</tbody></table></div>";
						$.jBox(html, {
						    title: "全宗查阅统计列表",
						    width: 900,
						    top: 15,
						    height: 465,
						    buttons: { '关闭': true}
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
			var url = "${ctx}/platDocUse/export5";
			download_file.attr("src", url);
			download_file.css("display", "none");
		}
		function exportDate6(){
			$.ajax({
			    type: "post",
			    url: "${ctx}/platDocUse/findUseAgeRange",
			    dataType: "json",
			    success: function (result) {
			    		var html = "<form action='' method='post' class='breadcrumb form-search'><input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList6()'></form><div style='overflow-y:scroll;height:330px'><table id='contentTable' class='table table-striped table-bordered table-condensed'><thead><tr><th>借阅人数</th><th>借阅人年龄</th></tr></thead><tbody>";
						var useHtml = "";
						for (var i=0;i<result.uses.length;i++) {
							useHtml = useHtml + "<tr><td>"+result.uses[i]+"</td><td>"+result.ageRange[i]+"</td></tr>";
						}
						html = html + useHtml + "</tbody></table></div>";
						$.jBox(html, {
						    title: "年龄段统计列表",
						    width: 900,
						    top: 15,
						    height: 465,
						    buttons: { '关闭': true}
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
			var url = "${ctx}/platDocUse/export6";
			download_file.attr("src", url);
			download_file.css("display", "none");
		}
		function exportDate7(){
			$.ajax({
			    type: "post",
			    url: "${ctx}/platDocUse/findUseAllCountByYear",
			    dataType: "json",
			    success: function (result) {
			    		var html = "<form action='' method='post' class='breadcrumb form-search'><input type='button' value='导出' id='upFile-btn' class='btn btn-primary' onclick='exportList7()'></form><div style='overflow-y:scroll;height:330px'><table id='contentTable' class='table table-striped table-bordered table-condensed'><thead><tr><th>类型</th><th>卷数</th><th>日期</th></tr></thead><tbody>";
						var useHtml = "";
						var borrowHtml = "";
						for (var i=0;i<result.uses.length;i++) {
							useHtml = useHtml + "<tr><td>利用</td><td>"+result.uses[i]+"</td><td>"+result.years[i]+"</td></tr>";
						}
						for (var i=0;i<result.borrows.length;i++) {
							borrowHtml = borrowHtml + "<tr><td>借阅</td><td>"+result.borrows[i]+"</td><td>"+result.years[i]+"</td></tr>";
						}
						html = html + useHtml + borrowHtml + "</tbody></table></div>";
						$.jBox(html, {
						    title: "借阅利用按年统计列表",
						    width: 900,
						    top: 15,
						    height: 465,
						    buttons: { '关闭': true}
						});
			    	}
			})
	    };
		function exportList7() {
			var download_file = $("#download_file");
			if (download_file.length == 0) {
				var i = '<iframe id="download_file" name="download_file"></iframe>';
				$("body").append(i);
				download_file = $("#download_file");
			}
			var url = "${ctx}/platDocUse/export7";
			download_file.attr("src", url);
			download_file.css("display", "none");
		}
	</script>
</head>
<body>
	<div class="container-fluid" style="border: 1px solid #ddd;background-color: #ddd;">
		<div class="row-fluid" style="padding-top: 5px;">
			<div class="span6" >
				<div id="chartsDiv1" style="height: 400px;"></div>
				<a id="exportDate1" href="javascript:void(0)" onclick="exportDate1()" class="exportDate">列表</a>
			</div>
			<div class="span6" >
				<div id="chartsDiv2" style="height: 400px;"></div>
				<a id="exportDate2" href="javascript:void(0)" onclick="exportDate2()" class="exportDate">列表</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6" >
				<div id="chartsDiv3" style="height: 400px;"></div>
				<a id="exportDate3" href="javascript:void(0)" onclick="exportDate3()" class="exportDate">列表</a>
			</div>
			<div class="span6" >
				<div id="chartsDiv4" style="height: 400px;"></div>
				<a id="exportDate4" href="javascript:void(0)" onclick="exportDate4()" class="exportDate">列表</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12" >
				<div id="chartsDiv5" style="height: 400px;"></div>
				<a id="exportDate5" href="javascript:void(0)" onclick="exportDate5()" class="exportDate5">列表</a>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6" >
				<div id="chartsDiv6" style="height: 400px;"></div>
				<a id="exportDate6" href="javascript:void(0)" onclick="exportDate6()" class="exportDate">列表</a>
			</div>
			<div class="span6" >
				<div id="chartsDiv7" style="height: 400px;"></div>
				<a id="exportDate7" href="javascript:void(0)" onclick="exportDate7()" class="exportDate">列表</a>
			</div>
		</div>
	</div>
<script type="text/javascript">
$(function(){
	var chartsDiv1 = Highcharts.chart('chartsDiv1', {
		title: {
	        text: '档案利用<span style="font-size:12px;font-weight:bold;">[利用总量3130]</span>',
	        align: 'left'
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
	    	series: {
	    		cursor: 'pointer',
	    		events: {
	    			click:function(e) {
	    				var userType = e.point.series.name;
	    				openWin("档案利用统计明细","${ctx}/platDocUse/toUseCountByMonthUserType?month="+e.point.category + "&usertype=" + encodeURI(userType));
	    			}
	    		}
	    	}
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
	        align: 'left'
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
	    	series: {
	    		cursor: 'pointer',
	    		events: {
	    			click:function(e) {
	    				var applytype = e.point.series.name;
	    				openWin("档案借阅统计明细","${ctx}/platDocUse/toBorrowCountByMonthApplyType?month="+e.point.category + "&applytype=" + encodeURI(applytype));
	    			}
	    		}
	    	}
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
	    			    	console.log(data);
	    			    	chartsDiv3.series[0].setData(data);
	    			    }
	    			})
	    		}
	    	}
		},
		title : {
			text : '利用目的',
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
			text : '借阅目的',
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
            text: '全宗查阅统计',
			align: 'left'
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
			align: 'left'
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
	            text: '借阅人数'
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
            text: '借阅利用按年统计',
			align: 'left'
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
function openWin(title,url) {
	parent.layer.open({
  	  type: 2, 
  	  area: [pageSize().WinW - 400+'px', pageSize().WinH - 100 + 'px'],
  	  title: title,
  	  closeBtn: 1,
  	  shadeClose: false,
  	  shade: [0.5 , '#000' , true],
  	  content: url
  	}); 
}
</script>
</body>
</html>