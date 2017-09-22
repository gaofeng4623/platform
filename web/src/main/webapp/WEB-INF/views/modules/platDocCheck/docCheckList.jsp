<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>文档审批列表</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/platDocCheck/getCheckListByPara',
		        mtype: "POST",
		        datatype: "json",
		        //postData:$("searchForm").serialize(),
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: '编号', name: 'taskId', hidden:true},
					{ label: '编号', name: 'formKey', hidden:true},
					{ label: '编号', name: 'processInstanceId', hidden:true},
					{ label: '流程类型', name: 'processDefinitionName', width: 200},
					{ label: '流程类型', name: 'processDefinitionKey', width: 200, hidden:true},
					{ label: '任务名称', name: 'taskName', width: 200},
					{ label: '任务创建日期', name: 'createTime', align: 'center', width: 150},
					{ label: '任务结束日期', name: 'endTime', align: 'center', width: 150},
					{ label: '发起人', name: 'author', width: 100},
					{ label: '任务状态', name: 'status', width: 50, hidden:true},
					{ label: '任务状态', width: 50, align: 'center', formatter:function(value, options, row){
	            		if(row.status == "0"){
							return '待审批';
	            		}else if(row.status == "1"){
							return '已同意';
	            		}else if(row.status == "2"){
	            			return '已拒绝';
	            		}else if(row.status == "3"){
	            			return '转审批';
	            		}else if(row.status == "4"){
	            			return '已完成';
	            		}else if(row.status == "5"){
	            			return '待审批';
	            		}else{
	            			return '';
	            		}
					}},
		            { label: '操作', width: 100, align: 'center', formatter : function(value, options, row) {
				    		var processDefinitionName = row.processDefinitionName;
				    		processDefinitionName = encodeURIComponent(processDefinitionName);
				    		var taskName = row.taskName;
				    		taskName = encodeURIComponent(taskName);
				    		
				    		var buttons = "";
				    		var callBackUrl = ""
				    		if(row.processDefinitionKey!='archivesuselook'&&row.processDefinitionKey!='archivesuseprint'&&row.processDefinitionKey!='archivesusecall'){
				    			if(row.callback=='1'){
				    				callBackUrl = "${docCheckCancel}"+row.taskId+"/"+row.processInstanceId+"/"+row.callback;
			    					buttons = ' | <a href="'+ callBackUrl +'">撤回记录</a>';
				    			}else{
				    				callBackUrl = "${docCheckCancel}"+row.taskId+"/"+row.processInstanceId+"/"+row.callback;
			    					buttons = ' | <a href="'+ callBackUrl +'">撤回</a>';
			    				}
			    			}
				    		
				    		var docCheckToDoUrl = "";
		            		if(row.status == "0"){//利用登记待审批
					    		docCheckToDoUrl = "${docCheckToDo}"+row.taskId+"/"+row.formKey+"/"+row.processDefinitionKey+"/"+row.processInstanceId;
								return '<a href="'+ docCheckToDoUrl +'">办理</a>';
		            		}else if(row.status == "1"){//已同意
					    		docCheckToDoUrl = "${docCheckShowHistory}"+row.processDefinitionKey+"/"+row.processInstanceId;
								return '<a href="'+ docCheckToDoUrl +'">详情</a>' + buttons;
		            		}else if(row.status == "2"){//已拒绝
					    		docCheckToDoUrl = "${docCheckShowHistory}"+row.processDefinitionKey+"/"+row.processInstanceId;
		            			return '<a href="'+ docCheckToDoUrl +'">详情</a>' + buttons;
		            		}else if(row.status == "3"){//转审批
					    		docCheckToDoUrl = "${docCheckShowHistory}"+row.processDefinitionKey+"/"+row.processInstanceId;
		            			return '<a href="'+ docCheckToDoUrl +'">详情</a>' + buttons;
		            		}else if(row.status == "4"){//已完成
					    		docCheckToDoUrl = "${docCheckShowHistory}"+row.processDefinitionKey+"/"+row.processInstanceId;
		            			return '<a href="'+ docCheckToDoUrl +'">详情</a>' + buttons;
		            		}else if(row.status == "5"){//待审批
					    		docCheckToDoUrl = "${docCheckToDo}"+row.taskId+"/"+row.formKey+"/"+row.processDefinitionKey+"/"+row.processInstanceId;
		            			return '<a href="'+ docCheckToDoUrl +'">办理</a>';
		            		}else{
		            			return '';
		            		}
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
			<li><label>流程类型：</label>
			    <select name="processDefinitionKey" id="processDefinitionKey" style="width:180px;">
			        <option value="">请选择</option>
			        <c:forEach items="${processDefinitionKeys}" var="item">
			            <option value="${item.key}">${item.value}</option>
			        </c:forEach>
			    </select>
			</li>
			<li><label>任务状态：</label>
			    <select name="status" id="status" style="width:180px;">
			        <option value="">请选择</option>
			        <c:forEach items="${workState}" var="item">
			            <option value="${item.key}">${item.value}</option>
			        </c:forEach>
			    </select>
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