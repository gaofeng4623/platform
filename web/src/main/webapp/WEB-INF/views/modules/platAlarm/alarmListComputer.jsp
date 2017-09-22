<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>设备性能告警</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/platAlarm/loadComputer',
		        mtype: "POST",
		        datatype: "json",
		       // postData:$("form").serialize(),
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: '报警编号', name: 'id',hidden:true},
					{ label: '报警时间', name: 'createTime',  align: "center" },
					{ label: '设备IP', name: 'alarmDevicename',align: "center"},
					{ label: '报警类型', name: 'alarmType',  align: "center" },
					{ label: '报警内容', name: 'content', width: 300, align: "center"},
		            { label: '操作 ',  width: 50, align: 'center', formatter : function(value, options, row) {
							return '<a href="#" onclick="return confirmx(\'确认要处理该告警信息吗？\', function() {deleteById(\'' + row.id + '\');})">处理</a>';
		            } }
		        ],
				viewrecords: true,
				autowidth: true,
				multiselect: false,
				rownumbers: true,
		        rowNum: 20,
		        pager: "#pager" 
		    });
			$(window).resize(function(){
				var ss = pageSize(); 
				grid.jqGrid('setGridWidth',ss.WinW);
				grid.jqGrid('setGridHeight', ss.WinH-110);
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
		
		
		/**
		 * 删除数据
		 */
		function deleteById(id) {
			$.ajax({
				type: "POST",
				url:'${ctx}/platAlarm/deleteComputerAlarm',
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
		
	</script>
</head>
<body style="overflow-x:hidden;">
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>报警设备IP</label>
				<input type="text" name ="alarmDevicename" class="input-medium">
			</li>
			<li><label>报警时间起</label>
				<input name="startDate" type="text" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${dataInfo.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			</li>
			<li><label>报警时间止</label>
				<input name="endDate" type="text" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${dataInfo.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查 询" onclick="searchForm()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>