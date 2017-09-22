<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>交付机配置</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctxStatic}/jqGrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/js/trirand/i18n/grid.locale-cn.js" type="text/ecmascript" ></script>
	<link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/jquery-ui.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/trirand/ui.jqgrid.css" />
    <script type="text/javascript">
        var ctx = '${ctx}', ctxStatic='${ctxStatic}';
        <shiro:hasPermission name="base:deliver:view">
        var hasEditPermission = true;
        </shiro:hasPermission>
    </script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#jqGrid").jqGrid({
		        url: '${ctx}/base/deliver/load',
		        mtype: "POST",
		        datatype: "json",
		        jsonReader: {
					//id: "guid",
					root: "list",
					//page: "page",
					//total: "total",
					//records: "records"//总条数
				},
		        colModel: [
		            { label: 'id', name: 'id', hidden: true},
                    { label: '分行id', name: 'branchId', hidden: true},
                    { label: '分行名称', name: 'office.name', width: 100},
		            { label: '工作机IP', name: 'workMachIp', width: 100},
		            { label: '交付机IP', name: 'deliverMachIp', width: 100},
		            { label: '操作', name: 'action', width: 150, formatter : function(value, options, row) {
		            	return  '<a href="${ctx}/base/deliver/form?id='+row.id+'">修改</a>&nbsp;|&nbsp;'+
						'<a href="${ctx}/base/deliver/delete?id='+row.id+'" onclick="return confirmx(\'确认要删除该配置吗？\', this.href)">删除</a>';
		            } }
		        ],
				viewrecords: true,
				autowidth: true,
		      	height: $(window).height() - 200,
		      	rowNum: 20,
				rownumbers: true,
		      	pager: "#jqGridPager"
		    });
		});
		function searchForm() {
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/base/deliver/list">配置列表</a></li>
		<shiro:hasPermission name="base:deliver:edit"><li><a href="${ctx}/base/deliver/form">配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" class="breadcrumb form-search">
		<div>
			<label>工作机IP：</label><input id="workMachIp" name="workMachIp" type="text" maxlength="50" class="input-small" value="${deliver.workMachIp}"/>
			<label>交付机IP：</label><input id="deliverMachIp" name="deliverMachIp" type="text" maxlength="50" class="input-small" value="${deliver.deliverMachIp}"/>
			<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm();"/>&nbsp;&nbsp;
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="jqGrid"></table>
	<div id="jqGridPager"></div>
</body>
</html>