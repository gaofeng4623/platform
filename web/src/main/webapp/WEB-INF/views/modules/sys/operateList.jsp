<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>日志管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctxStatic}/jqGrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/js/trirand/i18n/grid.locale-cn.js" type="text/ecmascript" ></script>
	<link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/jquery-ui.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/trirand/ui.jqgrid.css" />
	<script type="text/javascript">
	$(document).ready(function(){
		var grid = $("#jqGrid").jqGrid({
	        url: '${ctx}/sys/operate/load',
	        mtype: "POST",
	        datatype: "json",
			jsonReader: {
				id: "id",
				root: "list"
			},
	        colModel: [
	        	{ label: 'id', name: 'id', key: true, hidden: true },
	            { label: '用户工号', name: 'userNo', width: 50 },
	            { label: '用户姓名', name: 'userName', width: 60 },
	            { label: '操作内容', name: 'operationRecord', width: 180 },
	            { label:'操作类型', name: 'operationType', width: 80 },
	            { label: '菜单模块', name: 'module', width: 30 },
	            { label: '用户IP', name: 'ip', width: 40 },
	            { label: '操作时间', name: 'createDate', width: 150 },
	            { label: '设备类型', name: 'eqType', width: 150 }
	        ],
			viewrecords: true,
			autowidth: true,
			autoheight: true,
	       // width: 780,
	      	height: $(window).height() - 170,
	        rowNum: 20 ,
	        pager: "#jqGridPager" 
	    });
		
		$('#btnSubmit').click(function(event) {
			searchForm();
		});
	});

	function searchForm() {
		//要件查询
		var serializeObj = {};
		$($("#searchForm").formToArray()).each(function() {
			serializeObj[this.name] = $.trim(this.value);
		});
		$("#jqGrid").setGridParam({
			page: 1,
			postData: serializeObj,
	    }).trigger("reloadGrid");
	}
</script>
</head>
<body>
	<form id="searchForm" class="breadcrumb form-search">
		<div>
			<label>用户工号：</label><input name="userNo" type="text" maxlength="50" class="input-mini" />
			<label>用户姓名：</label><input name="userName" type="text" maxlength="50" class="input-mini"/>
			<label>操作类型：</label><input name="operationType" type="text" maxlength="50" class="input-mini"/>
			<label>菜单模块：</label><input name="module" type="text" maxlength="50" class="input-mini"/>
		</div><div style="margin-top:8px;">
			<label>设备类型：</label><input name="eqType" type="text" maxlength="50" class="input-mini"/>
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>&nbsp;&nbsp;
		</div>
	</form>
	<table id="jqGrid"></table>
	<div id="jqGridPager"></div>
</body>
</html>