<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title></title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<%@ include file="/WEB-INF/views/include/jqgrid.jsp"%>
	<script type="text/javascript">
		function changeType(id,obj) {
			$(".div_type").removeClass("type_active");
			$(obj).addClass("type_active");
			if(id == "1"){
				$("#alarmFrame").attr('src',"${ctx}/workRate/charts");
			}else if(id == "2"){
				$("#alarmFrame").attr('src',"${ctx}/workRate/list");
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
<body style="height: 100%">
	<div class="row-fluid" style="height: 99%">
		<div class="span2">
			<div style="width: 100%">
				<div class="div_type type_head">工 作 监 督</div>
				<div class="div_type type_active" onclick="changeType('2',this)">监督数据列表</div>
				<div class="div_type" onclick="changeType('1',this)">监督图形展示</div>
				<div class="div_type type_back" onclick="toHome();">返回主页</div>
			</div>
		</div>
		<div id="div_grid" class="span10" style="height: 100%;">
			<iframe id="alarmFrame" src="${ctx}/workRate/list" height="100%" scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
</body>
</html>