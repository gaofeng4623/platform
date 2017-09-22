<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>角色管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		var ctx = '${ctx}', ctxStatic='${ctxStatic}';
		<shiro:hasPermission name="sys:role:edit">
			var hasEditPermission = true;
		</shiro:hasPermission>
	</script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#grid").jqGrid({
	        url: '${ctx}/sys/role/load',
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
	            { label: '角色名称', name: 'name', width: 200,formatter: function(value, options, row) {
	            	return "<a href=\"${ctx}/sys/role/form?id="+row.id+"\">"+value+"</a>";
	            }  },
	            { label: '英文名称', name: 'enname', width: 120,formatter: function(value, options, row) {
	            	return "<a href=\"${ctx}/sys/role/form?id="+row.id+"\">"+value+"</a>";
	            }  },
	            { label: '归属机构', name: 'office.name', width: 150},
	            { label:'数据范围', name: 'dataScopeName', width: 150 },
	            { label: '操作', name: 'delFlag', width: 150, formatter : function(value, options, row) {
	            	if (hasEditPermission) {
    					return  '<a href="${ctx}/sys/role/assign?id='+row.id+'">分配</a>&nbsp;|&nbsp;'+
    							'<a href="${ctx}/sys/role/form?id='+row.id+'">修改</a>&nbsp;|&nbsp;'+
								'<a href="${ctx}/sys/role/delete?id='+row.id+'" onclick="return confirmx(\'确认要删除该角色吗？\', this.href)">删除</a>';
	            	} else {
	            		return "";
	            	}
	            } }
	        ],
			viewrecords: true,
			autowidth: true,
			autoheight: true,
			altRows: true,
			rownumbers: true,
	      	height: $(window).height() - 167,
	        rowNum: 20,
	        pager: "#pager",
	        postData: {
	        	'office.id' : '${role.office.id}'
	        }
	    });
	});
	function getParams () {
		var serializeObj = {};
		$($("#searchForm").formToArray()).each(function() {
			serializeObj[this.name] = $.trim(this.value);
		});
		return serializeObj;
	}
	
	function searchForm() {
		//要件查询
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
		<li class="active"><a href="${ctx}/sys/role/list?office.id=${role.office.id}&office.name=${role.office.name}">角色列表</a></li>
		<shiro:hasPermission name="sys:role:edit">
			<li><a href="${ctx}/sys/role/form?office.id=${role.office.id}&office.name=${role.office.name}">角色添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form id="searchForm"  method="post" class=" form-search ">
		<ul class="ul-form">
			<li><label>归属机构：</label><sys:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name" labelValue="${role.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/></li>
			<li><label>角色名称：</label><input type="text" name="name" maxlength="50" class="input-medium"/></li>
			<li><label>英文名称：</label><input type="text" name="enname" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/>
				<!-- <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}" />
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>