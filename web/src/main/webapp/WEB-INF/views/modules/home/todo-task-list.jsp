<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<c:if test="${not empty tasks}">
		<table style="width: 90%;">
			<tr style="height: 30px">
				<th width="20%" align="left">流程信息</th>
				<th width="20%" align="left">流程名称</th>
				<th width="20%" align="left">任务名称</th>
				<th width="20%" align="center">任务创建日期</th>
				<th width="15%" align="center">流程图</th>
				<th width="10%" align="center">操作</th>
			</tr>
			<c:forEach items="${tasks }" var="task">
				<tr style="height: 22px" class="searchTr">
					<td class="search" align="left">${task.description }</td>
					<td class="search" align="left">${task.processDefinitionName }</td>
					<td class="search" align="left">${task.taskName }</td>
					<td class="search" align="center">${task.createTime }</td>
					<td align="center"><a class='trace gt_a_click' href='javascript:void(0);' processInstanceId='${task.processInstanceId }' processDefinitionId='${task.processDefinitionId }' title='点击查看流程图'>跟踪</a></td>
					<td align="center">
						<c:if test="${empty task.assignee }">
							<a class="claim gt_a_click" href='javascript:void(0);' taskId="${task.taskId}">签收</a>
						</c:if>
						<c:if test="${not empty task.assignee }">
							<a class="handle gt_a_click" taskName='${task.taskName }' taskId='${task.taskId }' processInstanceId="${task.processInstanceId }" formKey="${task.formKey}" href="javascript:void(0);">办理</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
<c:if test="${empty tasks}">
    没有任务！
</c:if>
