<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>工作监督</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/workRate/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: '工作类型', name: 'workType',hidden:true },
					{ label: '工作类型', name: 'workTypeName', align: 'center', width: 50 },
					{ label: '工作内容', name: 'content', align: 'center',width: 150,formatter : function(value, options, row) {
						return '处理档案【'+row.workNum+row.workUnit+'】';
	            	} },
					{ label: '更新时间', name: 'date', align: 'center', width: 50 },
		            { label: '操作', name: 'oo', width: 50, align: 'center', formatter : function(value, options, row) {
				    		var workType = row.workType;
				    		workType = encodeURIComponent(workType);
				    		var workTypeName = row.workTypeName;
				    		workTypeName = encodeURIComponent(workTypeName);
				    		var workUnit = row.workUnit;
				    		workUnit = encodeURIComponent(workUnit);
				    		var href = "${ctx}/workRate/infoForm?workType="+workType+"&workTypeName="+workTypeName+"&workNum="+row.workNum+"&workUnit="+workUnit;
							if(null != row.date){
								href += "&date="+row.date;
							}
							return '<a href="'+href+'">趋势</a>'
		            	}
		            } 
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
		
		function goBack(){
			location.href = "${ctx}/home/right";
		}
		
	</script>
</head>
<body style="overflow-x:hidden;">
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>工作类型：</label>
				 <select name="workType" id="workType" class="input-large">
			        <c:forEach items="${work_type}" var="item">
			            <option value="${item.key}">${item.value}</option>
			        </c:forEach>
			    </select>
			</li>
			<li><label>更新时间</label>
				<input name="date" type="text" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${workRate.date}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM'});"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/>
			</li>
		</ul>
	</form>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>