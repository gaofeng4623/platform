<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>门禁管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctxStatic}/jqGrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/js/trirand/i18n/grid.locale-cn.js" type="text/ecmascript" ></script>
	<link href="${ctxStatic}/jqGrid/css/jquery-ui.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${ctxStatic}/jqGrid/css/trirand/ui.jqgrid.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript">
		<shiro:hasPermission name="base:door:edit">
			var hasEditPermission = true;
		</shiro:hasPermission>
	</script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#grid").jqGrid({
	        url: '${ctx}/base/door/load',
	        mtype: "POST",
	        datatype: "json",
			jsonReader: {
				id: "id",
				root: "list",
				page: "page",
				total: "total",
				records: "records"//总条数
			},
	        colModel: [
	            { label: '主键', name: 'id', key: true, hidden: true},
	            { label: '门禁编号', name: 'doorId', width: 100 },
	            { label: '门禁名称', name: 'note', width: 100 },
	            { label: '门禁类型', name: 'doorType', width: 150,formatter: function(value, options, row) {
	            	if(value == '1'){
	            		return '大门';
	            	}else if(value == '0'){
	            		return '非大门';
	            	}else{
	            		return '';
	            	}
	            } },
	            { label: '门禁地址', name: 'ip', width: 150 },
	            { label:'创建人', name: 'createBy.name', width: 150 },
	            { label: '创建时间', name: 'createDate', width: 150 },
	            { label: '操作', name: 'delFlag', width: 100, formatter : function(value, options, row) {
	            	if (hasEditPermission) {
    					return '<a href="javascript:void(0)" onclick="editForm(\''+row.id+'\')">修改</a>';
	            	} else {
	            		return "";
	            	}
	            } }
	        ],
			viewrecords: true,
			autowidth: true,
			autoheight: true,
			multiselect: true,
			rownumbers: true,
	      	height: $(window).height() - 200,
	        rowNum: 20 ,
	        pager: "#pager",
	        postData : getParams()
	    });
	});

	function editForm(id){
			$.jBox.open("get:${ctx}/base/door/form?id="+id,
						"门禁信息修改", 615, 380,
						{id:'addAuth',top:'0%',zIndex:1900,
						 closed:function(){window.location.href=window.location.href;} ,buttons: {  }});
	}


	function getParams () {
		var serializeObj = {};
		$($("#searchForm").formToArray()).each(function() {
			serializeObj[this.name] = $.trim(this.value);
		});
		return serializeObj;
	}
	
	function doSearch() {
		var serializeObj = getParams();
		$("#grid").setGridParam({
			page: 1,
			postData: serializeObj,
	    }).trigger("reloadGrid");
	}
	
	function delForm(){
		//获取多选到的id集合  
		var guids = $("#grid").jqGrid("getGridParam", "selarrrow");
		if(guids.length == 0){
			alertx('请至少选择一条门禁记录！');
			return ;
		}
		var ids = guids.join(',');
		
		var doorIds = [];
		//遍历访问这个集合  
		$(guids).each(function (index, id){  
			var row = $("#grid").jqGrid('getRowData', id);
			doorIds.push(row.doorId);
		});
		$.ajax({
            type: "post",
            dataType: "json",
            url: "${ctx}/base/door/delDoors",
            data: {ids : ids, doorIds : doorIds.join(',')},
            timeout : 180000,
	        beforeSubmit : function() {
	        	//alert(ids);
	        },
	        success : function(result, status) {
	        	if(result.success == true) {
	        		var serializeObj = getParams();
	        		alertx(result.msg);
	        		$("#grid").setGridParam({
	        			page : 1,
	        			postData: serializeObj
				    }).trigger("reloadGrid");
 	            } else {
 	               alertx(result.msg);
 	            }
	        },
	        error : function() {
	        	alertx('出现错误，稍后再试！');
	        }
        }); 
	}
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/base/door/list">门禁列表</a></li>
		<shiro:hasPermission name="base:door:edit"><li><a href="${ctx}/base/door/form">门禁添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="door" method="post" class="breadcrumb form-search ">
		<ul class="ul-form">
			<li>
				<label>门禁编号：</label>
				<form:input path="doorId" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>门禁类型：</label>
				<select id="doorType" name="doorType" style="width: 100px">
					<option value="">全部</option>
					<option value="1">大门</option>
					<option value="0">非大门</option>
				</select>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="doSearch()"/>
				<shiro:hasPermission name="base:door:edit">
					<input id="btnDel" class="btn btn-primary" type="button" value="删除" onclick="delForm()"/>
				</shiro:hasPermission>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>