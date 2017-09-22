<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>收藏夹管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<style>
		.ui-jqgrid tr.jqgrow td {
			text-overflow : ellipsis;
			text-align: center;
		}
	</style>
	<script type="text/javascript">
		var hasEditPermission = false;
		<shiro:hasPermission name="platfavorites:platFavorites:edit">
			hasEditPermission = true;
		</shiro:hasPermission>
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/platfavorites/platFavorites/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: 'id', name: 'id', width: 50 },
					{ label: 'fav_name', name: 'favName', width: 50 },
					{ label: 'create_by', name: 'createBy.id', width: 50 },
					{ label: 'create_date', name: 'createDate', width: 50 },
					{ label: 'update_by', name: 'updateBy.id', width: 50 },
					{ label: 'update_date', name: 'updateDate', width: 50 },
					{ label: 'remarks', name: 'remarks', width: 50 },
					{ label: 'del_flag', name: 'delFlag', width: 50 },
		            { label: '操作', name: 'oo', width: 150, formatter : function(value, options, row) {
		            	if (hasEditPermission) {
							return '<a href="#" onclick="openWin(\'' + '${ctx}/platfavorites/platFavorites/editForm?id='+row.id+'\')">修改</a>'+
							'&nbsp;|&nbsp;<a href="#" onclick="return confirmx(\'确认要删除该收藏夹吗？\', function() {deleteById(\'' + row.id + '\');})">删除</a>';
		            	} else {
		            		return "";
		            	}
		            } }
		        ],
				viewrecords: true,
				autowidth: true,
				multiselect: true,
				rownumbers: true,
		      	//height: $(window).height() - 170,
		        rowNum: 20 ,
		        pager: "#pager" 
		    });
			$(window).resize(function(){
				grid.jqGrid('setGridWidth',$(window).width());
				grid.jqGrid('setGridHeight',$(window).height()-170);
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
		//窗口测试，不用可删除
		function openWin(url) {
			//html:、id:、get:、post:、iframe:
			//$.jBox.open(content, title, width, height, options);
			$.jBox.open("get:" + url, "修改条目", 615, 380,{id:'recForm',top:'10%',left:'0%',zIndex:1900,buttons: {  },closed:function(){$("#grid").trigger("reloadGrid");} });
		}

		/**
		 * 删除数据
		 */
		function deleteById(id) {
			$.ajax({
				type: "POST",
				url:'${ctx}/platfavorites/platFavorites/delete',
				data:{
					id: id,
				},
				dataType: "json",
				success:  function(result, status) {
					if(result.success == true) {
						$.jBox.tip(result.msg, 'success');
					} else {
						$.jBox.tip(result.msg, 'error');
					}
					$("#grid").trigger("reloadGrid");
				},
				error : function() {
					$.jBox.info('出现错误，稍后再试！', '提示');
				}
			});
		}

		/**
		 * 批量删除数据
		 */
		function multipleDelete() {
			var ids=$("#grid").jqGrid('getGridParam','selarrrow');
			//将ids 转换为字符串
			if(ids==null || ids=='' || ids ==undefined){
				alert("请选择要删除的数据");
				return;
			}
			$.ajax({
				type: "POST",
				url:'${ctx}/platfavorites/platFavorites/multiDel',
				data:{
					idList:ids.join(","),
				},
				dataType: "json",
				success:  function(result, status) {
					if(result.success == true) {
						$.jBox.tip(result.msg, 'success');
					} else {
						$.jBox.tip(result.msg, 'error');
					}
					$("#grid").trigger("reloadGrid");
				},
				error : function() {
					$.jBox.info('出现错误，稍后再试！', '提示');
				}
			});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/platfavorites/platFavorites/list">收藏夹列表</a></li>
		<shiro:hasPermission name="platfavorites:platFavorites:edit"><li><a href="${ctx}/platfavorites/platFavorites/addForm">收藏夹添加</a></li></shiro:hasPermission>
	</ul>
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/></li>
			<shiro:hasPermission name="platfavorites:platFavorites:edit">
			<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="删除" onclick="return confirmx('确认要删除选中收藏夹吗？',multipleDelete)"/></li>
			</shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>