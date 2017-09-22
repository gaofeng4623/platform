<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>例子</title>
	<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.2.1.js" type="text/javascript"></script>
	
	<script src="${ctxStatic}/jquery/jquery.form.min.js" type="text/javascript"></script>
	
	<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

	<script src="${ctxStatic}/jqGrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/js/trirand/i18n/grid.locale-cn.js" type="text/ecmascript" ></script>
	<link href="${ctxStatic}/jqGrid/css/jquery-ui.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${ctxStatic}/jqGrid/css/trirand/ui.jqgrid.css" rel="stylesheet" type="text/css" media="screen"/>

<script type="text/javascript">
$(document).ready(function(){
	$("#jqGrid").jqGrid({
        url: '${ctx}/sys/example/load',
        mtype: "POST",
        datatype: "json",
		jsonReader: {
			id: "id",
			root: "list",
			page: "page",
			total: "total",
			records: "records"//总条数
		},
        colModel: [
            { label: '主键', name: 'id', key: true, hidden: true},
            { label: '名字', name: 'name', width: 100 },
            { label: '生日', name: 'birthDay', width: 100 },
            { label: '创建人', name: 'createBy.name', width: 150 },
            { label:'创建时间', name: 'createDate', width: 150 },
            { label: '更新人', name: 'updateBy', width: 150 },
            { label: '更新时间', name: 'updateDate', width: 150 },
            { label: 'mark', name: 'remarks', width: 150 },
            { label: '删除标记', name: 'delFlag', width: 150 }
        ],
		viewrecords: true,
		autowidth: true,
		autoheight: true,
       // width: 780,
      	height: $(window).height() - 200,
        rowNum: 20 ,
        pager: "#jqGridPager" 
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="#tab1" data-toggle="tab">例子列表</a></li>
		<li ><a href="#tab2" data-toggle="tab">例子添加</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane  active" id="tab1">
			<form:form id="searchForm" action="${ctx}/sys/log/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div>
					<label>姓名：</label><input id="name" name="name" type="text" maxlength="50" class="input-mini" value="${example.name}"/>
					<label>年龄：</label><input id="age" name="age" type="text" maxlength="50" class="input-mini" value="${example.age}"/>
					<label>生日：</label><input id="birthDay" name="birthDay" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
						value="<fmt:formatDate value="${example.birthDay}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onc/>&nbsp;&nbsp;
				</div>
			</form:form>
			<table id="jqGrid"></table>
			<div id="jqGridPager"></div>
		</div>
		<div class="tab-pane" id="tab2">
			<form:form id="inputForm" modelAttribute="example" action="${ctx}/sys/example/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="control-group">
					<label class="control-label">姓名:</label>
					<div class="controls">
						<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">年龄:</label>
					<div class="controls">
						<form:input path="age" htmlEscape="false" maxlength="50" class="required" value="" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">生日:</label>
					<div class="controls">
						<input name="birthDay" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
						value="<fmt:formatDate value="${example.birthDay}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</div>
				</div>
				<div class="form-actions">
					<shiro:hasPermission name="sys:example:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>