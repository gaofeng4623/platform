<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld"%>
<!DOCTYPE html>
<head>
	<title>公告信息管理</title>
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
		<shiro:hasPermission name="notice:noticeInfo:edit">
			hasEditPermission = true;
		</shiro:hasPermission>
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/notice/noticeInfo/load?type=edit',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: '标题', name: 'title', align: 'left', width: 150 ,formatter : function(value, options, row) {
						return '<a href="${ctx}/notice/noticeInfo/DetailForm?id='+row.id+'">'+value+'</a>';}},
					{ label: '公告所属类别', name: 'ext', width: 100 },
					{ label: '公告时间', name: 'date', width: 100 },
					{ label: '是否公开', name: 'gkflag', width: 50,formatter : function(value){
						if(value=="1"){
							return "公开";
						}else{
							return "不公开";
						}
					} },
					{ label: '可见部门', name: 'officeName', width: 50 },
		            { label: '操作', name: 'oo', width: 50, formatter : function(value, options, row) {
		            	if (hasEditPermission) {
							return '<a href="${ctx}/notice/noticeInfo/editForm?id='+row.id+'">修改</a>'+
							'&nbsp;&nbsp;<a href="#" onclick="return confirmx(\'确认要删除该公告信息吗？\', function() {deleteById(\'' + row.id + '\');})">删除</a>';
		            	} else {
		            		return "";
		            	}
		            } }
		        ],
				viewrecords: true,
				autowidth: true,
				multiselect: true,
				rownumbers: true,
		        rowNum: 20 ,
		        pager: "#pager" 
		    });
			$(window).resize(function(){
				var ss = pageSize();
				grid.jqGrid('setGridWidth',ss.WinW);
				grid.jqGrid('setGridHeight',ss.WinH - 160);
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

			var value=true;
			$.ajax({
				type: "GET",
				url:'${ctx}/notice/noticeInfo/ifFavorites',
				data:{
					id: id,
				},
				dataType: "json",
				success:  function(result, status) {
					
					if(result.success == true) {
						value=result.o;
						if(value){
							$.jBox.confirm('该公告已经被收藏，请再次确认要删除该信息？','系统提示',function(v,h,f){
								if(v=='ok'){
									$.ajax({
										type: "POST",
										url:'${ctx}/notice/noticeInfo/delete',
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
							});
						}else{
							$.ajax({
								type: "POST",
								url:'${ctx}/notice/noticeInfo/delete',
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
						
					} else {
						$.jBox.tip(result.msg, 'error');
					}
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
			
			var value=true;
			$.ajax({
				type: "GET",
				url:'${ctx}/notice/noticeInfo/ifListFavorites',
				data:{
					idList:ids.join(","),
				},
				dataType: "json",
				success:  function(result, status) {
					
					if(result.success == true) {
						value=result.o;
						if(value){
							$.jBox.confirm('该批量数据中存在被收藏条目，请再次确认要删除该信息？','系统提示',function(v,h,f){
								if(v=='ok'){
									$.ajax({
										type: "POST",
										url:'${ctx}/notice/noticeInfo/multiDel',
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
							});
						}else{
							$.ajax({
								type: "POST",
								url:'${ctx}/notice/noticeInfo/multiDel',
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
						
					} else {
						$.jBox.tip(result.msg, 'error');
					}
				},
				error : function() {
					$.jBox.info('出现错误，稍后再试！', '提示');
				}
			});

			
			/**
			$.ajax({
				type: "POST",
				url:'${ctx}/notice/noticeInfo/multiDel',
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
			**/
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/notice/noticeInfo/editList">公告信息列表</a></li>
		<shiro:hasPermission name="notice:noticeInfo:edit"><li><a href="${ctx}/notice/noticeInfo/addForm">公告信息添加</a></li></shiro:hasPermission>
	</ul>
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>标题：</label>
				<input name="title"  class="input-medium" type="text"/>
			</li>
			<li><label>公告时间：</label>
			 <input name="stime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${noticeInfo.stime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> - 
				<input name="etime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${noticeInfo.etime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
					</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/></li>
			<shiro:hasPermission name="notice:noticeInfo:edit">
			<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="删除" onclick="return confirmx('确认要删除选中公告信息吗？',multipleDelete)"/></li>
			</shiro:hasPermission>
			<!-- <li class="btns"><input class="btn btn-primary" type="button" value="重置" onclick="reset()"/>&nbsp;&nbsp;</li> -->
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>