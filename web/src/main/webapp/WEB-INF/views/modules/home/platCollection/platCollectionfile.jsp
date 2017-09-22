<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>最新采集管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<%@ include file="/WEB-INF/views/include/kindeditor.jsp" %>
	<script type="text/javascript">
	
	</script>

</head>
<body>
	<div style="width:100%;text-align: center;margin: auto;">
		<div align="right" style="padding: 15px;">
			<input id="btnCancel" class="btn" type="button" value="返回" onclick="javascript:history.go(-1);"/>
		</div>
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" value="${yDataCollection.id}"/>
<table border="0" cellspacing="0" cellpadding="0" width="100%" style="align:center;">
    <tr bgcolor="#E0EEEE" style="font-weight:bold;height: 40px">
    <th>文件主题</th><th>文件名称</th><th>文件大小</th>
 <!--    <th width="100" colspan="2">操作</th> -->
    </tr>
    <c:forEach items="${list}" var="item" varStatus="idxStatus">
    	<tr style="height: 35px">
    		<td>${item.fileTitle}</td>
    		<td>${item.fileName}</td>
    		<td>${item.fileSize}</td>
    		<td>
<%--      		<a href="${ctx}/platcollection/platCollection/downloadDoc?fileName=${item.fileName}" class="gt_a_click">下载</a></td>
 --%>    	</tr>
    </c:forEach>
</table>
	</div>
</body>
</html>