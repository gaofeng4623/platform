<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>位置管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
	var hasEditPermission = false;
		<shiro:hasPermission name="base:location:edit">
			hasEditPermission = true;
		</shiro:hasPermission>
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/base/location/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
				postData:{
					id: '${location.id}'
				},
				altRow: true,
		        colModel: [
					{ label: '主键', name: 'id', hidden:true, width: 50 },
					{ label: '分行编码', name: 'branchId', hidden:true, width: 100 },
					{ label: '位置名称', name: 'locationName', width: 100 },
					{ label: '全路径', name: 'locationPath', width: 150 },
					{ label: '位置类型', name: 'locationType', hidden:true, width: 100 },
					{ label: '位置类型', name: 'locationTypeName', width: 100 },
					{ label: '序号', name: 'serialNo', width: 50 },
					{ label: '电子标签', name: 'rfid', width: 100 },
					{ label: '序号全路径', name: 'serialNoPath', width: 100 },
					{ label: '存放档案类型', name: 'fileType', width: 100 , formatter : function(value, options, row) {
						if('1' == value){
							return "要件";
						}else if('0' == value){
							return "权证";
						}else{
							return "";
						}
					}},
		            { label: '操作', name: 'oo', width: 120, formatter : function(value, options, row) {
		            	if (hasEditPermission && '0' == row.parent.id ) {
							return '<a href="${ctx}/base/location/deptForm?id='+row.id+'">修改</a>'
		            	} else if (hasEditPermission && '0' != row.parent.id ) {
							return '<a href="${ctx}/base/location/form?id='+row.id+'">修改</a>'+
							'&nbsp;|&nbsp;<a href="${ctx}/base/location/delete?id='+row.id+'&parent.id='+row.parent.id+'" onclick="return confirmx(\'确认要删除该位置吗？\', this.href)">删除</a>'+
							'&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="writeLocationRfid(\''+row.id+'\',\''+row.locationType+'\',\''+row.rfid+'\')">标签写入</a>';
		            	} else {
		            		return "";
		            	}
		            } }
		        ],
				viewrecords: true,
				autowidth: true,
				height: $(window).height() - 170,
		        rowNum: 20 ,
		        rownumbers: true,
		        pager: "#pager" 
		    });
		});
		
		function searchForm() {
			var serializeObj = {};
			$($("#searchForm").formToArray()).each(function() {
				serializeObj[this.name] = $.trim(this.value);
			});
			$("#grid").setGridParam({
				page: 1,
				postData: serializeObj,
		    }).trigger("reloadGrid");
		}
		
		function batchAddLocation(){
			$.jBox("get:${ctx}/base/location/openBatchAddForm?id=${location.id}", {
			    title: "批量插入",
			    width: 600,
			    height: 400,
			    buttons: {},
			    });
		}		
		
		function reloadGrid() {
	    	$("#grid").setGridParam({
	    		page: 1
	        }).trigger("reloadGrid");
	    }
		
		function writeLocationRfid(id, type, rfid){
			var locationType = "";
			if(type == '8'){
				locationType = 'B1';
			}else if(type == '9'){
				locationType = 'C0';
			}else{
				alertx("位置不适合写入标签！");
				return;
			}
			var result = locationRfidWrite(rfid,id,locationType);
			if(result.success && result.updateFlag == 1){
				$("#grid").jqGrid('setCell',id,'rfid',result.rfid);
			}
		}
		
	</script>
</head>
<body>
<OBJECT ID="TEST_ACTIVEX" CLASSID="CLSID:adb90c45-65db-4f90-81ae-cb579e46d107" style="display: none;" width="0" height="0" codebase='rfidCtrl.CAB#version=1,0,0,1'></OBJECT>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/base/location/list">位置列表</a></li>
		<shiro:hasPermission name="base:location:edit"><li><a href="${ctx}/base/location/form?parent.id=${location.id}">位置添加</a></li></shiro:hasPermission>
		<shiro:hasPermission name="base:location:edit"><li><a href="${ctx}/base/location/deptForm?parent.id=0">档案中心添加</a></li></shiro:hasPermission>
	</ul>
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			
			<%-- <li>
				<input name="id" type="text" maxlength="50" class="input-medium" value="${location.id}"/>
			</li> --%>
			<shiro:hasPermission name="base:location:edit">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="批量插入位置" onclick="batchAddLocation()"/></li>
				<li class="clearfix"></li>
			</shiro:hasPermission>
		</ul>
	</form> 
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
	
</body>
</html>