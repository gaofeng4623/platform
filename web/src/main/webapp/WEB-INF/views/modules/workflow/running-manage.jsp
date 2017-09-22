<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>流程列表</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctxStatic}/jqGrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/js/trirand/i18n/grid.locale-cn.js" type="text/ecmascript" ></script>
	<link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/jquery-ui.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/trirand/ui.jqgrid.css" />

	<script type="text/javascript">
		var ctx = "${ctx}";
	</script>
	<script src="${ctxStatic }/workflow/workflow.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
		 	// 跟踪
			$(document).on( 'click', '.trace', graphTrace);
			var grid = $("#jqGrid").jqGrid({
	            url: '${ctx}/workflow/loadRunning.do',
	            mtype: "POST",
	            datatype: "json",
	    		jsonReader: {
	    			id: "id",
	    			root: "list"
	    		},
	            colModel: [
	                { label: 'tenantId', name: 'tenantId'},
	                { label: '流程名称', name: 'processDefinitionName', width: 80 },
	                { label: 'key', name: 'processDefinitionKey', width: 150 },
	                { label:'版本号', name: 'processDefinitionVersion', width: 150 },
	                { label: '当前节点', name: 'a', width: 150, formatter : function(value, options, row) {
	                	return formatterAction(value, options, row);
	                }}
	            ],
	    		viewrecords: true,
	    		autowidth: true,
	          	//height: $(window).height() - 170,
	            rowNum: 20 ,
	            pager: "#jqGridPager" 
	        });
	    	$(window).resize(function(){
	    		grid.jqGrid('setGridWidth',$(window).width());
	    		grid.jqGrid('setGridHeight',$(window).height()-90);
	    	}).resize();
		});
	    function formatterAction(value, options, row) {
	    	return "<a class='trace gt_a_click' href='javascript:void(0);' processInstanceId='"+row.processInstanceId+"' processDefinitionId='"+row.processDefinitionId+"' title='点击查看流程图'>"+row.currentTask+"</a>";
	    }
	</script>
</head>

<body>
	
	 <table id="jqGrid"></table>
	<div id="jqGridPager"></div>
</body>
</html>
