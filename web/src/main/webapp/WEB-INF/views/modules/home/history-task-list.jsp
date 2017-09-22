<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<c:if test="${not empty tasks}">
		<table>
			<tr>
				<th>流程名称</th>
				<th>任务名称</th>
				<th>处理时间</th>
				<!-- <th>任务描述</th> -->
				<th>流程图</th>
			</tr>
	
			<c:forEach items="${tasks }" var="task">
			<tr>
				<td>${task.processDefinitionName }</td>
				<td>${task.taskName }</td>
				<td>${task.endTime }</td>
				<%-- <td>${task.description }</td> --%>
				<td><a class='trace gt_a_click' href='javascript:void(0);' processInstanceId='${task.processInstanceId }' processDefinitionId='${task.processDefinitionId }' title='点击查看流程图'>跟踪</a></td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
