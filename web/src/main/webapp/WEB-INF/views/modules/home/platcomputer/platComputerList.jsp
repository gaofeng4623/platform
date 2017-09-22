<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>计算机设备管理</title>
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
		<shiro:hasPermission name="platcomputer:platComputer:edit">
			hasEditPermission = true;
		</shiro:hasPermission>
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/platcomputer/platComputer/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: 'id', name: 'id', width: 50 ,hidden:true},
					{ label: '设备IP', name: 'ip', width: 100 },
					{ label: '用户名', name: 'name', width: 100 },
/* 					{ label: 'bmcname', name: 'bmcname', width: 50 },
					{ label: 'bmcpw', name: 'bmcpw', width: 50 }, */
					{ label: '操心系统类型', name: 'ostype', width: 100 },
					{ label: '操心系统版本', name: 'osversion', width: 100 },
					{ label: '设备描述', name: 'descripe', width: 100 },
					{ label: '设备是否在线', name: 'onlineflag', width: 100},
				//	{ label: '社保是否连接', name: 'conflag', width: 50 },
				//	{ label: '是否收集性能数据', name: 'flag', width: 50 },
		            { label: '操作', name: 'oo', width: 150, formatter : function(value, options, row) {
		            	if (hasEditPermission) {
							return '<a href="#" onclick="openWin(\'' + '${ctx}/platcomputer/platComputer/editForm?id='+row.id+'\')">修改</a>'+
							'&nbsp;|&nbsp;<a href="#" onclick="return confirmx(\'确认要删除该计算机设备吗？\', function() {deleteById(\'' + row.id + '\');})">删除</a>'+
							'&nbsp;|&nbsp;<a href="${ctx}/platcomputer/platComputer/index?ip='+row.ip+'">查看</a>';
		            	} else {
		            		return "";
		            	}
		            } }
		        ],
		        loadComplete: function () {  
		        		 var carinfo = "";
		                 var ids = $('#grid').getDataIDs(); 
		                 var len = ids.length;
		                 for (var i = 0; i < len; i++) {
		                	 var getRow = $('#grid').getRowData(ids[i]);
		                  	$.ajax({
		        				type: "POST",
		        				url:'${ctx}/platcomputer/platComputer/checkIpOnline?ip='+getRow.ip+'&rownum='+i,
		        				dataType: "json",
		        				async:true,
		        				success:  function(result,status) {
		        						  var rownum=result.o*1;
		        						  var ids = $('#grid').getDataIDs(); 
		        						 $("#grid").setCell(ids[rownum],'onlineflag',result.msg)
		        				},
		        				error : function() {
		        					//$.jBox.info('出现错误，稍后再试！', '提示');
		        					 $("#grid").setCell(ids[i],'onlineflag','未知')
		        				}
		        			});
		                  	 
		                  	 
		                 }
		        },
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
				url:'${ctx}/platcomputer/platComputer/delete',
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
				url:'${ctx}/platcomputer/platComputer/multiDel',
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
		<li class="active"><a href="${ctx}/platcomputer/platComputer/list">计算机设备列表</a></li>
		<shiro:hasPermission name="platcomputer:platComputer:edit"><li><a href="${ctx}/platcomputer/platComputer/addForm">计算机设备添加</a></li></shiro:hasPermission>
	</ul>
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>设备IP：</label>
				<input name="ip"  class="input-medium" type="text"/>
			</li>
			<li><label>操作系统：</label>
				<select id="ostype" name="ostype" class="required" style="width:100px;">
					<option value="" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
					<c:forEach items="${fns:getDictList('ostype')}" var="item">
						<option value="${item.value}" >${item.label}</option>	
					</c:forEach>
				</select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/></li>
			<shiro:hasPermission name="platcomputer:platComputer:edit">
			<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="删除" onclick="return confirmx('确认要删除选中计算机设备吗？',multipleDelete)"/></li>
			</shiro:hasPermission>
<!-- 			<li class="btns"><input class="btn btn-primary" type="button" value="重置" onclick="reset()"/>&nbsp;&nbsp;</li>
 -->			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>