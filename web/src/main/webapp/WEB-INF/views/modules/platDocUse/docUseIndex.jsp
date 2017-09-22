<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title></title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="${ctx}/static/layer/layer.js"></script>
	<script type="text/javascript">
	function changeType(type,obj) {
		$(".div_type").removeClass("type_active");
		$(obj).addClass("type_active");
		if(type == "1"){
			$("#myFrame").attr('src',"${ctx}/platDocUse/toDocUseChartsListPage");
		}else if(type == "2"){
			$("#myFrame").attr('src',"${ctx}/platDocUse/toDocUseNetListPage");
		}
		
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
				<div class="div_type type_head">档 案 利 用</div>
				<div class="div_type type_active" onclick="changeType('1',this)">借阅利用</div>
				<!-- <div class="div_type" onclick="changeType('2',this)">网络利用</div> -->
				<div class="div_type type_back" onclick="toHome();">返回主页</div>
			</div>
		</div>
		<div id="div_grid" class="span10" style="height: 100%;">
			<iframe id="myFrame" src="${ctx}/platDocUse/toDocUseChartsListPage" height="100%" scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
</body>
</html>