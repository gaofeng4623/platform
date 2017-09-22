<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>行业信息查询</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/plat/platInformation/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: '标题', width: 250, name: 'title', formatter : function(value, options, row) {
						return '<a href="${ctx}/plat/platInformation/informationForm?id='+row.id+'">'+row.title+'</a>';
		            }},
					{ label: '发布时间', width: 50, align: 'center', name: 'releaseDate'},
					{ label: 'url', name: 'url',"hidden":true},
					{ label: 'id', name: 'id',"hidden":true},
		            { label: '查看', width: 50, align: 'center', formatter : function(value, options, row) {
						return '<a href="${ctx}/plat/platInformation/informationForm?id='+row.id+'">详情</a>';
		            }}
		        ],
				viewrecords: true,
				autowidth: true,
				multiselect: false,
				rownumbers: true,
		        rowNum: 20 ,
		        pager: "#pager" 
		    });
			$(window).resize(function(){
				var ss = pageSize();
				grid.jqGrid('setGridWidth',$("#div_grid").width() - 2);
				grid.jqGrid('setGridHeight',ss.WinH - 60);
			}).resize();
		});
		
		
		function changeType() {
			$("#myFrame").attr('src',"${ctx}/plat/platInformation/weblist");
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
	<div class="row-fluid" style="height: 98%;">
		<div class="span2">
			<div style="width: 100%">
				<div class="div_type type_head">行 业 资 讯</div>
				<div class="div_type type_active" onclick="changeType();">资讯列表</div>
				<div class="div_type type_back" onclick="toHome();">返回主页</div>
			</div>
		</div>
		<div id="div_grid" class="span10" style="height: 100%;">
			<iframe id="myFrame" src="${ctx}/plat/platInformation/weblist" height="100%" scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
</body>
</html>