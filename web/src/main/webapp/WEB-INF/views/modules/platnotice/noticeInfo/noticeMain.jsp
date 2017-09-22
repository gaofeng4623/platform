<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>.
<!DOCTYPE html>
<head>
<script src="${ctx}/js/menu/left-nav.js"></script>
	<title>列表</title>
</head>
<body>
<div class="nav" id="side-menu">
	
		<ul class="nav nav-second-level">
			<li><a href="#" target="menuFrame">进货报表</a></li>
			<li><a href="#" target="menuFrame">出货报表</a></li>
			<li><a href="#" target="menuFrame">毛利报表</a></li>
		</ul>
	
	
</div>
 <div id="page_content">
          <iframe id="menuFrame" name="menuFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="100%; float:left">
          </iframe>
 </div>
 
 </body>
</html>