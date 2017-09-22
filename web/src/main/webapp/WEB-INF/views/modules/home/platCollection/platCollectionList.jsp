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
					{ label: '主题词', name: 'subject',align: 'center', width: 100 },
					{ label: '全宗名称', name: 'genName',align: 'center', width: 100 },
					{ label: '题名', name: 'booksTitle',align: 'center', width: 100 },
					{ label: '来源', name: 'docNum', align: 'center', width: 50 },
//					{ label: '保管期限', name: 'retainDate', align: 'center', width: 50 },
//					{ label: '起止时间', name: 'enthesis', align: 'center', width: 100 },
					{ label: '目录号', name: 'catalog', align: 'center', width: 50 },
					{ label: '案卷号', name: 'filesID', align: 'center', width: 50 },
					{ label: '全宗号', name: 'recordNum', align: 'center', width: 50 },
//					{ label: '文件件数', name: 'fileNum', align: 'center', width: 50 },
					
		            { label: '查看', name: 'oo', width: 50, align: 'center', formatter : function(value, options, row) {
		            	if (hasEditPermission) {
							return '<a href="${ctx}/platcollection/platCollection/editForm?id='+row.id+'">卷内详情</a>'
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
				grid.jqGrid('setGridWidth',ss.WinW);
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

		
	</script>
</head>
<body style="overflow-x:hidden;">
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>主题词：</label>
				<input type="text" name="subject" class="input-medium"/>
			</li>
			<li><label>全宗名称：</label>
				<input type="text" name="genName" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/>
			</li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>