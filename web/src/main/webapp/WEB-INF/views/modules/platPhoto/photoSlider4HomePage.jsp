<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script src="${ctx}/static/plat/jquery.slides.min.js"></script>
<link rel="stylesheet" href="${ctx}/static/css/photoSlider4HomePage.css">
<script type="text/javascript">
$(function() {
	$.ajax({
		url:"${ctx}/platPhoto/getCameraImages",
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	$("#slides").empty();
    		var html = "";
	    	for(var i=0; i<resultData.length; i++){
	    		var item = resultData[i];
	    		html += '<img src="'+ item.imageUrl +'" style="height:100%;cursor: pointer;" onclick="toPhotoListPage();">';
	    	}
	    	$("#slides").html(html);
	    	$('#slides').slidesjs({
	    		height: 380,
	    	    play: {
	    	      active: true,
	    	      auto: true,
	    		  swap: false,
	    	      interval: 3000
	    	    }
	    	});
	    }
	});
});
function toPhotoListPage(){
	window.location.href = "${ctx}/platPhoto/toPhotoIndex";
}
</script>
<div id="slides">
	<div>暂无数据</div>
</div>