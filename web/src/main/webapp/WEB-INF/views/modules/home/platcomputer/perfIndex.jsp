<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title></title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
	function changeType(id,obj) {
		$(".div_type").removeClass("type_active");
		$(obj).addClass("type_active");
		$("#myFrame").attr('src',"${ctx}/platcomputer/platComputer/view?ip=${ip}&id="+id);
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
				<div class="div_type type_head">时间范围</div>
					<div class="div_type type_active" onclick="changeType('all',this)">实时性能数据</div>
					<div class="div_type" onclick="changeType('day',this)">天性能数据</div>
					<div class="div_type" onclick="changeType('month',this)">月性能数据</div>
					<div class="div_type" onclick="changeType('year',this)">年性能数据</div>
					<div class="div_type type_back" onclick="history.go(-1)">
						返回
					</div>
			</div>
		</div>
		<div id="div_grid" class="span10" style="height: 100%;">
			<iframe id="myFrame" src="${ctx}/platcomputer/platComputer/view?ip=${ip}" height="100%" scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
</body>
</html>