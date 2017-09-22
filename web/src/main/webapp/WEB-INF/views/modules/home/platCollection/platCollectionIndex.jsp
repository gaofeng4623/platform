<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>最新采集管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		var hasEditPermission = false;
		<shiro:hasPermission name="platcollection:platCollection:view">
			hasEditPermission = true;
		</shiro:hasPermission>
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/platcollection/platCollection/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: 'id', name: 'id', width: 50 ,hidden:true},
					{ label: '主题词', name: 'subject', width: 100 },
					{ label: '全宗名称', name: 'genName', width: 100 },
					{ label: '案卷题名', name: 'autograph', width: 100 },
					{ label: '保管期限', name: 'retainDate', align: 'center', width: 50 },
					{ label: '文件件数', name: 'fileNum', align: 'center', width: 50 },
					{ label: '来源', name: 'docNum', align: 'center', width: 50 },
					{ label: '目录号', name: 'catalog', align: 'center', width: 50 },
					{ label: '案卷号', name: 'filesID', align: 'center', width: 50 },
					{ label: '全宗号', name: 'recordNum', align: 'center', width: 50 },
					{ label: '起止时间', name: 'enthesis', align: 'center', width: 100 },
/* 					{ label: 'ext1', name: 'ext1', width: 50 },
					{ label: 'ext2', name: 'ext2', width: 50 },
					{ label: 'ext3', name: 'ext3', width: 50 }, */
		            { label: '操作', name: 'oo', width: 50, align: 'center', formatter : function(value, options, row) {
		            	if (hasEditPermission) {
							return '<a href="${ctx}/platcollection/platCollection/editForm?id='+row.id+'">详情</a>'
		            	} else {
		            		return "";
		            	}
		            } }
		        ],
				viewrecords: true,
				autowidth: true,
				multiselect: false,
				rownumbers: true,
		      	//height: $(window).height() - 170,
		        rowNum: 20 ,
		        pager: "#pager" 
		    });
			$(window).resize(function(){
				var ss = pageSize();
				grid.jqGrid('setGridWidth',$("#div_grid").width() - 2);
				grid.jqGrid('setGridHeight',ss.WinH - 110);
			}).resize();
		});
		function searchForm() {
			//查询
			var serializeObj = {};
			$($("#searchForm").formToArray()).each(function() {
				serializeObj[this.name] = $.trim(this.value);
			});
			$("#grid").setGridParam({
				page: 1,
				postData: serializeObj,
		    }).trigger("reloadGrid");
		}

		function changeType() {
			$("#myFrame").attr('src',"${ctx}/platcollection/platCollection/list");
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
				<div class="div_type type_head">最 新 采 集</div>
				<div class="div_type type_active" onclick="changeType();">采集列表</div>
				<div class="div_type type_back" onclick="toHome();">返回主页</div>
			</div>
		</div>
		<div id="div_grid" class="span10" style="height: 100%;">
			<iframe id="myFrame" src="${ctx}/platcollection/platCollection/list" height="100%" scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
</body>
</html>