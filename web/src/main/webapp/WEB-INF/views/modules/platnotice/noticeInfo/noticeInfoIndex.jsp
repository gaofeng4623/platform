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
		$("#myFrame").attr('src',"${ctx}/notice/noticeInfo/list?type="+id);
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
				<div class="div_type type_head">系 统 公 告</div>
				<div class="div_type type_active" onclick="changeType('all',this)">全部</div>
				<c:forEach items="${fns:getDictList('notice_type')}" var="item" varStatus="idxStatus">
				<div class="div_type" onclick="changeType('${item.value}',this)">${item.label}</div>
				</c:forEach>
				<div class="div_type type_back" onclick="toHome();">返回主页</div>
			</div>
		</div>
		<div id="div_grid" class="span10" style="height: 100%;">
			<iframe id="myFrame" src="${ctx}/notice/noticeInfo/list?type=all" height="100%" scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
</body>
</html>