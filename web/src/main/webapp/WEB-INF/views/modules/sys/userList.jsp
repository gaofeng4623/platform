<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>用户管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		var ctx = '${ctx}', ctxStatic='${ctxStatic}';
		<shiro:hasPermission name="sys:user:edit">
			var hasEditPermission = true;
		</shiro:hasPermission>
	</script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#grid").jqGrid({
	        url: '${ctx}/sys/user/load',
	        mtype: "POST",
	        datatype: "json",
			jsonReader: {
				id: "id",
				root: "list",
				page: "page",
				total: "total",
				records: "records"//总条数
			},
			altRows: true,
	        colModel: [
	            { label: '主键', name: 'id', key: true, hidden: true},
	            { label: '归属公司', name: 'company.name', width: 150 },
	            { label: '归属部门', name: 'office.name', width: 150 },
	            { label: '登录名', name: 'loginName', width: 100,formatter: function(value, options, row) {
	            	return "<a href=\"${ctx}/sys/user/form?id="+row.id+"\">"+value+"</a>";
	            } },
	            { label:'姓名', name: 'name', width: 100 },
	            { label: '角色', name: 'roleNames', width: 250 },
	            { label: '编号', name: 'no', width: 100 },
	            { label: '手机', name: 'mobile', width: 100 },
	            
	            { label: '操作', name: 'delFlag', width: 100, formatter : function(value, options, row) {
	            	if (hasEditPermission) {
    					return '<a href="${ctx}/sys/user/form?id='+row.id+'">修改</a>'+
						'&nbsp;|&nbsp;<a href="${ctx}/sys/user/delete?id='+row.id+'" onclick="return confirmx(\'确认要删除该用户吗？\', this.href)">删除</a>';
	            	} else {
	            		return "";
	            	}
	            } }
	        ],
			viewrecords: true,
			autowidth: true,
			autoheight: true,
	      	height: $(window).height() - 200,
	        rowNum: 20 ,
	        pager: "#pager",
	        //rowList: [20,30,40,50],
	        postData : getParams()
	    });
	});
	function getParams () {
		var serializeObj = {};
		$($("#searchForm").formToArray()).each(function() {
			serializeObj[this.name] = $.trim(this.value);
		});
		return serializeObj;
	}
	
	function doSearch() {
		var serializeObj = getParams();
		$("#grid").setGridParam({
			page: 1,
			postData: serializeObj,
	    }).trigger("reloadGrid");
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
		<shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form">用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="user" method="post" class="breadcrumb form-search ">
		<ul class="ul-form">
			<li><label>归属公司：</label>
				<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="clearfix"></li>
			<li><label>归属部门：</label><sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/></li>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="doSearch()"/>
				<!-- <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/> 
	<%-- <table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>归属公司</th><th>归属部门</th><th class="sort-column login_name">登录名</th><th class="sort-column name">姓名</th><th>电话</th><th>手机</th><th>角色</th><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.company.name}</td>
				<td>${user.office.name}</td>
				<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td>
				<td>${user.roleNames}</td>
				<shiro:hasPermission name="sys:user:edit"><td>
    				<a href="${ctx}/sys/user/form?id=${user.id}">修改</a>
					<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table> --%>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>