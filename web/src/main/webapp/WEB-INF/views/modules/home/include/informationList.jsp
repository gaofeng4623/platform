<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:forEach items="${formationList}" var="item">

<div class="div_class_1">
	<div class="home_text">
		<font class="home_text_point">●</font>&nbsp;
	<%-- 	<c:if test="${item.tname=='bs'}">
		<a href="${ctx}/plat/platInformation/informationForm?id=${item.id}" title="${item.title}">
			<font color="#FF0000">${item.title}</font>
		</a>
		</c:if>
		<c:if test="${item.tname=='wbs'}">
		<a href="${ctx}/plat/platInformation/informationForm?id=${item.id}" title="${item.title}">
			${item.title}
		</a>
		</c:if> --%>
		 <a href="${ctx}/plat/platInformation/informationForm?id=${item.id}" title="${item.title}">
			${item.title}
		</a> 
	</div>
	<div class="home_text_time">
		${item.releaseDate}
 		<%-- <c:if test="${item.tname=='bs'}">
			<font color="#FF0000">${item.releaseDate}</font>

		</c:if>
		<c:if test="${item.tname=='wbs'}">
				${item.releaseDate}

		</c:if> --%>
	</div>
</div>
</c:forEach>
