<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:forEach items="${home:findTopN()}" var="item" varStatus="idxStatus">
<div class="div_class_1">
	<div class="home_text">
		<font class="home_text_point">●</font>&nbsp;
		<a href="${ctx}/notice/noticeInfo/DetailForm?id=${item.id}">
			${item.title}
		</a>
	</div>
	<div class="home_text_time">
		<fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd"/>
	</div>
</div>
</c:forEach>