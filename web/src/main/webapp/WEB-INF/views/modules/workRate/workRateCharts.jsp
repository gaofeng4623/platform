<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highcharts.js"></script>
	<script src="${ctx}/static/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		/**
		 * 驾驶舱管理---月征集档案量---面积图
		 */
	   	Highcharts.chart('workRate1', {
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

		//月征集档案量
		var chartsDiv3 = $('#workRate1').highcharts();
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
	
		/**
		 * 驾驶舱管理---借阅利用---折线图
		 */
	   	$('#workRate2').highcharts({
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
		//借阅利用
		var chartsDiv2 = $('#workRate2').highcharts();
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

		
		/**
		 * 驾驶舱管理---年鉴定档案量---柱状图
		 */
	   	$('#workRate3').highcharts({
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

		//年鉴定档案量
		var chartsDiv6 = $('#workRate3').highcharts();
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

		/**
		 * 驾驶舱管理---年移交档案量---混合图
		 */
	   	Highcharts.chart('workRate4', {
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

		//年接收档案量
		var chartsDiv8 = $('#workRate4').highcharts();
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
		
	});
	
	function goBack(){
		location.href = "${ctx}/home/right";
	}
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
	</script>
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
			<div class="span6" >
				<div id="workRate1" style="height: 300px;"></div>
			</div>
			<div class="span6" >
				<div id="workRate2" style="height: 300px;"></div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6" >
				<div id="workRate3" style="height: 300px;"></div>
			</div>
			<div class="span6" >
				<div id="workRate4" style="height: 300px;"></div>
			</div>
		</div>
	</div>
</body>
</html>