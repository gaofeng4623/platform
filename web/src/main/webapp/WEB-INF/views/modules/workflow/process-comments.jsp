<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style type="text/css">
table{
	font-family: "宋体";
	font-size: 12px;
}
</style>
<c:if test="${not empty comments}">
	<table>
		<tr>
			<th>任务名称</th>
			<th>审批时间</th>
			<th>审批人</th>
			<th>审批意见</th>
		</tr>
		<c:forEach items="${comments }" var="item">
			<tr class="${item.taskDefinitionKey}">
				<td>${item.taskName }</td>
				<td><fmt:formatDate value="${item.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${item.userName }</td>
				<td>${item.message }</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
