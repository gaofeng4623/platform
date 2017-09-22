<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>安全异常信息管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/platAlarm/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: '报警编号', name: 'alarmId',hidden:true},
					{ label: '报警时间', name: 'createTime', width: 120, align: "center" },
					{ label: '设备编号', name: 'alarmDeviceId',hidden:true},
					{ label: '异常发生地', name: 'roomName', width: 100, align: "center" },
					{ label: '设备名称', name: 'alarmDeviceName', width: 150, align: "center" },
					{ label: '报警类型', name: 'alarmTypeName', width: 120, align: "center" },
					{ label: '级别', name: 'grades', width: 50, align: "center"},
					{ label: '最后报警时间', name: 'lastAlarmTime', width: 150, align: "center"},
					{ label: '处理人员', name: 'userName', width: 80, align: "center"},
					{ label: '目前状态', name: 'mention', width: 50, align: "center"},
					{ label: '是否解决', name: 'isDeal',hidden:true},
					{ label: '解决时间', name: 'dealTime', width: 150, align: "center"},
					{ label: '报警类型编号', name: 'alarmTypeId', width: 50, hidden:true},
		            { label: '应急预案 ',  width: 50, align: 'center', formatter : function(value, options, row) {
							return '<a href="${ctx}/platAlarm/toAlarmInfoPage?alarmId='+row.alarmId+'">查看</a>';
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
	</script>
</head>
<body style="overflow-x:hidden;">
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>报警级别</label>
				 <select name="grades" id="grades" class="input-medium">
		            <option value="">请选择</option>
		            <option value="1">严重</option>
		            <option value="2">一般</option>
		            <option value="3">轻度</option>
			    </select>
			</li>
			<li><label>是否处理</label>
				 <select name="mention" id="mention" class="input-medium">
		            <option value="">请选择</option>
		            <option value="已处理">已处理</option>
		            <option value="未处理">未处理</option>
			    </select>
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