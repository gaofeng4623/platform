<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>日志管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
<script type="text/javascript">

<shiro:hasPermission name="sys:example:edit">
var hasEditPermission = true;
</shiro:hasPermission>
$(document).ready(function(){
	var grid = $("#jqGrid").jqGrid({
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
            { label: '名字', name: 'name', width: 120 },
            { label: '年龄', name: 'age', width: 50 },
            { label: '生日', name: 'birthDay', width: 80 },
            { label: '创建人', name: 'createBy.name', width: 150 },
            { label:'创建时间', name: 'createDate', width: 150 },
            { label: '更新人', name: 'updateBy', width: 150 },
            { label: '更新时间', name: 'updateDate', width: 150 },
            { label: 'mark', name: 'remarks', width: 150 },
            { label: '删除标记', name: 'delFlag', width: 150 },
            { label: '操作', name: 'delFlag', width: 150, formatter : function(value, options, row) {
            	if (hasEditPermission) {
					return '<a href="${ctx}/sys/example/form?id='+row.id+'">修改</a>'+
					'&nbsp;|&nbsp;<a href="${ctx}/sys/example/delete?id='+row.id+'" onclick="return confirmx(\'确认要删除该用户吗？\', this.href)">删除</a>';
            	} else {
            		return "";
            	}
            } }
        ],
		viewrecords: true,
		autowidth: true,
      	//height: $(window).height() - 170,
        rowNum: 20 ,
        pager: "#jqGridPager" 
    });
	$(window).resize(function(){
		grid.jqGrid('setGridWidth',$(window).width());
		grid.jqGrid('setGridHeight',$(window).height()-170);
	}).resize();
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

function openWin() {
	//html:、id:、get:、post:、iframe:
	//$.jBox.open(content, title, width, height, options);
	$.jBox.open("id:searchForm", "窗口模式", 800, 400);
	Share.Ajax({
		url: "",
		showMsg: false,
		param: {
			ids : '1231,123123,123123'
		},
		callback : function(result) {
			reload()
		}
	})
}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/example/list">例子列表</a></li>
		<li ><a href="${ctx}/sys/example/form">例子添加</a></li>
	</ul>
	<form:form id="searchForm" action="${ctx}/sys/log/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>姓名：</label><input id="name" name="name" type="text" maxlength="50" class="input-mini" value="${example.name}"/>
			<label>年龄：</label><input id="age" name="age" type="text" maxlength="50" class="input-mini" value="${example.age}"/>
			<label>生日：</label><input id="birthDay" name="birthDay" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${example.birthDay}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/>&nbsp;&nbsp;
			<input class="btn btn-primary" type="button" value="窗口加载" onclick="openWin()"/>&nbsp;&nbsp;
		</div>
	</form:form>
	<table id="jqGrid"></table>
	<div id="jqGridPager"></div>
</body>
</html>