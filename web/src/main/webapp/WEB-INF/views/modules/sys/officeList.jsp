<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>机构管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/list?id=${office.id}&parentIds=${office.parentIds}">机构列表</a></li>
		<shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/form?parent.id=${office.id}">机构添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>机构名称</th><th>机构编码</th><th>机构类型</th><th>备注</th><shiro:hasPermission name="sys:office:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
			<c:forEach items="${list}" var="row">
				<tr>
					<td><a href="${ctx}/sys/office/form?id=${row.id}">${row.name}</a></td>
					<td>${row.code}</td>
					<td>${fns:getDictLabel(row.type, "sys_office_type", "")}</td>
					<td>${row.remarks}</td>
					<shiro:hasPermission name="sys:office:edit"><td>
						<a href="${ctx}/sys/office/form?id=${row.id}">修改</a>&nbsp;
						<a href="${ctx}/sys/office/delete?id=${row.id}" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)">删除</a>&nbsp;
						<a href="${ctx}/sys/office/form?parent.id=${row.id}">添加下级机构</a> 
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
		
		</tbody>
	</table>
</body>
</html>