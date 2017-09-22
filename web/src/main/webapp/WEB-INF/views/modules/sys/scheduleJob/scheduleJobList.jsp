<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>任务管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctxStatic}/jqGrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/js/trirand/i18n/grid.locale-cn.js" type="text/ecmascript" ></script>
	<link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/jquery-ui.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/trirand/ui.jqgrid.css" />
	<script type="text/javascript">
	
		<%--<shiro:hasPermission name="sys:scheduleJob:edit">--%>
			var hasEditPermission = true;
		<%--</shiro:hasPermission>--%>
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/sys/scheduleJob/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: 'id', name: 'id', width: 50,key:true,hidden:true },
					{ label: '任务名称', name: 'jobName', width: 50, align: "center"},
					{ label: '任务分组', name: 'jobGroup', width: 50, align: "center"},
					{ label: '启用状态', name: 'jobStatus', width: 50, align: "center", formatter : function(value, options, row) {if(value==1){return "启用";}else{return "未启用";}} },
					{ label: '执行时间', name: 'cronDesc', width: 100, align: "center"},
					{ label: '描述', name: 'description', width: 50, align: "center"},
					{ label: '任务类', name: 'beanClass', width: 150, align: "center"},
					{ label: '当前任务状态', name: 'jobRunStatus', width: 50 , formatter : function(value, options, row) {
						if(value=='NORMAL'){
							return "计划任务正常";
						}else if(value=='COMPLETE'){
							return "计划任务执行结束";
						}else if(value=='PAUSED'){
							return "计划任务已暂停";
						}else if (value=='ERROR' || value=='BLOCKED' || value=='NONE'){
							return "计划任务异常";
						}else{return "未加入计划";}
						} },
				//	{ label: 'SpringId', name: 'springId', width: 50 },
					{ label: '任务调用的方法名', name: 'methodName', width: 100, align: "center"},
		            { label: '操作', name: 'oo', width: 150, align: "center", formatter : function(value, options, row) {
		            	
		            	var addjob='&nbsp;|&nbsp;<a href="javascript:void(0)"  onclick="addJob(\''+row.id+'\')">加入计划</a>';
		            	
		            	var sgstartJob='';
		            		'&nbsp;|&nbsp;<a href="javascript:void(0)"  onclick="sgstartJob(\''+row.id+'\')">手工执行</a>';
		            	var pauseJob='';
		            	var resumeJob='';
		            	if(row.jobRunStatus=='PAUSED'){
		            		resumeJob='&nbsp;|&nbsp;<a href="javascript:void(0)"  onclick="resumeJob(\''+row.id+'\')">恢复计划</a>';
		            	}else if(row.jobRunStatus=='ERROR' || row.jobRunStatus=='BLOCKED' || row.jobRunStatus=='NONE' || row.jobRunStatus=='NORMAL'){
		            		pauseJob='&nbsp;|&nbsp;<a href="javascript:void(0)"  onclick="pauseJob(\''+row.id+'\')">暂停计划</a>';
		            	}
		            	var yc='';
		            	if(row.jobRunStatus=='ERROR' || row.jobRunStatus=='BLOCKED' || row.jobRunStatus=='NONE' || row.jobRunStatus=='NORMAL' || row.jobRunStatus=='PAUSED'){
		            		yc='&nbsp;|&nbsp;<a href="javascript:void(0)"  onclick="deleteJob(\''+row.id+'\')">移除计划</a>';
		            		sgstartJob='&nbsp;|&nbsp;<a href="javascript:void(0)"  onclick="sgstartJob(\''+row.id+'\')">手工执行</a>';
		            	}
		            	var xiug='<a href="javascript:void(0)" onclick="add(\''+row.id+'\')">修改</a>';
		            	var sc='&nbsp;|&nbsp;<a href="${ctx}/sys/scheduleJob/delete?id='+row.id+'" onclick="return confirmx(\'确认要删除该任务吗？\', this.href)">删除</a>';
		            	if (hasEditPermission) {  
							return xiug+sc+addjob+sgstartJob+pauseJob+yc+resumeJob;
		            	} else {
		            		return "";
		            	}
		            } }
		        ],
				viewrecords: true,
				autowidth: true,
				rownumbers:true,
		      	//height: $(window).height() - 170,
		        rowNum: 20 ,
		        pager: "#pager" 
		    });
			$(window).resize(function(){
				grid.jqGrid('setGridWidth',$(window).width()-20);
				grid.jqGrid('setGridHeight',$(window).height()-170);
			}).resize();
		});
		function searchForm() {
			//要件查询
			var serializeObj = {};
			$($("#searchForm").formToArray()).each(function() {
				serializeObj[this.name] = $.trim(this.value);
			});
			$("#grid").setGridParam({
				page: 1,
				postData: serializeObj,
		    }).trigger("reloadGrid");
		}
		function add(id){
			if(id=='0'){
				$.jBox.open("get:${ctx}/sys/scheduleJob/form", "新增任务", 900, 400,{closed:function(){  $("#grid").trigger("reloadGrid");} ,buttons: {  }});
			}else{
				$.jBox.open("get:${ctx}/sys/scheduleJob/form?id="+id, "修改任务", 900, 400,{closed:function(){$("#grid").trigger("reloadGrid");} ,buttons: {  }});
			}
		}
		function addJob(id){
			$.ajax({
				 type: "POST",
				 url:'${ctx}/sys/scheduleJob/addJob?id='+id,
				 dataType: "json",
				 success:  function(result, status) {
					if(result.success == true) {
						$.jBox.info(result.msg, '提示');
					} else {
					   $.jBox.info(result.msg, '提示');
					}
					$("#grid").trigger("reloadGrid");
				},
				error : function() {
					$.jBox.info('出现错误，稍后再试！', '提示');
					
				}
			});
		}
		function pauseJob(id){
			$.ajax({
				 type: "POST",
				 url:'${ctx}/sys/scheduleJob/pauseJob?id='+id,
				 dataType: "json",
				 success:  function(result, status) {
					if(result.success == true) {
						$.jBox.info(result.msg, '提示');
					} else {
					   $.jBox.info(result.msg, '提示');
					}
					$("#grid").trigger("reloadGrid");
				},
				error : function() {
					$.jBox.info('出现错误，稍后再试！', '提示');
					
				}
			});
		}
		function resumeJob(id){
			$.ajax({
				 type: "POST",
				 url:'${ctx}/sys/scheduleJob/resumeJob?id='+id,
				 dataType: "json",
				 success:  function(result, status) {
					if(result.success == true) {
						$.jBox.info(result.msg, '提示');
					} else {
					   $.jBox.info(result.msg, '提示');
					}
					$("#grid").trigger("reloadGrid");
				},
				error : function() {
					$.jBox.info('出现错误，稍后再试！', '提示');
					
				}
			});
		}
		
		function deleteJob(id){
			$.ajax({
				 type: "POST",
				 url:'${ctx}/sys/scheduleJob/deleteJob?id='+id,
				 dataType: "json",
				 success:  function(result, status) {
					if(result.success == true) {
						$.jBox.info(result.msg, '提示');
					} else {
					   $.jBox.info(result.msg, '提示');
					}
					$("#grid").trigger("reloadGrid");
				},
				error : function() {
					$.jBox.info('出现错误，稍后再试！', '提示');
					
				}
			});
		}
		
		function sgstartJob(id){
			$.ajax({
				 type: "POST",
				 url:'${ctx}/sys/scheduleJob/runAJobNow?id='+id,
				 dataType: "json",
				 success:  function(result, status) {
					if(result.success == true) {
						$.jBox.info(result.msg, '提示');
					} else {
					   $.jBox.info(result.msg, '提示');
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
	<form id="searchForm" method="post" class="breadcrumb form-search">
		 <ul class="ul-form">
			<!--<li><label>任务名称：</label>
				<input name="jobName"  maxlength="60" class="input-medium"/>
			</li>
			<li><label>任务状态：</label>
				<input name="jobStatus"  maxlength="1" class="input-medium"/>
			</li>
			<li><label>当前任务状态：</label>
				<input name="isConcurrent"  maxlength="1" class="input-medium"/>
			</li>
			<li><label>SpringId：</label>
				<input name="springId"  maxlength="60" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/></li>
			 -->
			<li class="btns"><input class="btn btn-primary" type="button" value="新增" onclick="add('0')"/></li>
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>