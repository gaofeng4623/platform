<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>scedule管理</title>
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
		<shiro:hasPermission name="scedule:scheduleJob:edit">
			hasEditPermission = true;
		</shiro:hasPermission>
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/scedule/scheduleJob/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: 'id', name: 'id', width: 50 },
					{ label: '任务名称', name: 'jobname', width: 50 },
					{ label: '任务分组', name: 'jobgroup', width: 50 },
					{ label: '任务状态', name: 'jobstatus', width: 50 },
					{ label: '时间表达式', name: 'cronexpression', width: 50 },
					{ label: '描述', name: 'description', width: 50 },
					{ label: '任务类', name: 'beanclass', width: 50 },
					{ label: '当前任务状态', name: 'isconcurrent', width: 50 },
					{ label: 'SpringId', name: 'springid', width: 50 },
					{ label: '任务调用的方法名', name: 'methodname', width: 50 },
					{ label: '任务类型', name: 'jobtype', width: 50 },
					{ label: '分钟', name: 'fenz', width: 50 },
					{ label: '小时', name: 'xiaos', width: 50 },
					{ label: 'tians', name: 'tians', width: 50 },
					{ label: '周数', name: 'zhous', width: 50 },
					{ label: '月数', name: 'yues', width: 50 },
					{ label: '时间表达式解释', name: 'crondesc', width: 50 },
		            { label: '操作', name: 'oo', width: 150, formatter : function(value, options, row) {
		            	if (hasEditPermission) {
							return '<a href="#" onclick="openWin(\'' + '${ctx}/scedule/scheduleJob/editForm?id='+row.id+'\')">修改</a>'+
							'&nbsp;|&nbsp;<a href="#" onclick="return confirmx(\'确认要删除该scedule吗？\', function() {deleteById(\'' + row.id + '\');})">删除</a>';
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
				url:'${ctx}/scedule/scheduleJob/delete',
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
				url:'${ctx}/scedule/scheduleJob/multiDel',
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
		<li class="active"><a href="${ctx}/scedule/scheduleJob/list">scedule列表</a></li>
		<shiro:hasPermission name="scedule:scheduleJob:edit"><li><a href="${ctx}/scedule/scheduleJob/addForm">scedule添加</a></li></shiro:hasPermission>
	</ul>
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/></li>
			<shiro:hasPermission name="scedule:scheduleJob:edit">
			<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="删除" onclick="return confirmx('确认要删除选中scedule吗？',multipleDelete)"/></li>
			</shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>