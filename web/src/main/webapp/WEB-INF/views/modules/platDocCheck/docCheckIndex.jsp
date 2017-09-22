<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title></title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<%@ include file="/WEB-INF/views/include/jqgrid.jsp"%>
	<script type="text/javascript">
		function changeType() {
			$("#checkFrame").attr('src',"${ctx}/platDocCheck/toDocCheckListPage");
		}
		
		function toHome(){
			parent.location.href = "${ctx}/home/home";
		}
	</script>
	<style type="text/css">
		html,body{
			height: 100%;
		}
	</style>
</head>
<body>
	<div class="row-fluid" style="height: 98%">
		<div class="span2">
			<div style="width: 100%">
				<div class="div_type type_head">工 作 进 度</div>
				<div class="div_type type_active" onclick="changeType();">进度列表</div>
				<div class="div_type type_back" onclick="toHome();">返回主页</div>
			</div>
		</div>
		<div id="div_grid" class="span10" style="height: 100%;">
			<iframe id="checkFrame" src="${ctx}/platDocCheck/toDocCheckListPage" height="100%" scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
</body>
</html>