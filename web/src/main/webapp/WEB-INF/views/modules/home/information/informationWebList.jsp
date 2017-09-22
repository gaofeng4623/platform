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
				grid.jqGrid('setGridWidth', ss.WinW);
				grid.jqGrid('setGridHeight',ss.WinH - 60);
			}).resize();
		});
		
	</script>
</head>
<body>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>