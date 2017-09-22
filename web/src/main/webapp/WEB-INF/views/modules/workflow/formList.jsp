<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>流程列表</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		var hasEditPermission = true;
	    $(function() {
    		var grid = $("#jqGrid").jqGrid({
    	        url: '${ctx}/workflow/formDesign/loadFormList',
    	        mtype: "POST",
    	        datatype: "json",
    			jsonReader: {
    				root: "list"
    			},
    	        colModel: [
    	            { label: '主键', name: 'id', key: true, hidden: true},
    	            { label: '名称', name: 'name', width: 120 },
    	            { label: '说明', name: 'remarks', width: 180 },
    	            { label: '操作', name: 'a', width: 150, formatter : function(value, options, row) {
    	            	//if (hasEditPermission) {
    						return formatterAction(value, options, row);
    	            	//} else {
    	            		//return "";
    	            	//}
    	            } }
    	        ],
    			viewrecords: true,
    			autowidth: true,
    	        rowNum: 20 ,
    	        pager: "#jqGridPager" 
    	    });
    		$(window).resize(function(){
    			grid.jqGrid('setGridWidth',$(window).width());
    			grid.jqGrid('setGridHeight',$(window).height()-170);
    		}).resize();
    		
    		$('#create').click(function() {
	    		$.jBox("id:createTemplate", { 
	    		    title: "新增表单", 
	    		    width: 500, 
	    		    height: 350, 
	    		    buttons: {"提交":'ok',"取消":'cancel'},
	    		    submit: function(v,h,f) {
	    		    	if (v == 'ok') {
	    		    		if ($("#jbox-content #inputForm").valid()) {
	    		    			Share.Ajax({
		    						url: '${ctx}/workflow/formDesign/saveForm',
		    						params : {
		    							name: f.name,
	    								remark: f.remark
		    						},
		    						callback: function() {
		    							reloadGrid();
		    						}
		    					})
	    		    		} else {
	    		    			return false;
	    		    		}
	    		    	}
	    		    }
	    		});
	    	});
	    	
	    	
	    	
	    	
	    });
	    function doSearch() {
	    	var serializeObj = {};
	    	$($("#searchForm").formToArray()).each(function() {
	    		serializeObj[this.name] = $.trim(this.value);
	    	});
	    	$("#jqGrid").setGridParam({
	    		page: 1,
	    		postData: serializeObj,
	        }).trigger("reloadGrid");
		}
	    function reloadGrid() {
	    	$("#jqGrid").setGridParam({
	    		page: 1
	        }).trigger("reloadGrid");
	    }
	    function formatterAction(value, options, row) {
	    	return '<a href="${ctx}/sys/example/form?id='+row.id+'">修改</a>&nbsp;|&nbsp;'+
			'<a href="${ctx}/workflow/formDesign/formDesign?id='+row.id+'" )">设计</a>&nbsp;|&nbsp;'+
			'<a href="${ctx}/workflow/formDesign/bindRole?id='+row.id+'" )">绑定角色</a>';
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/workflow/formDesign/formList">表单列表</a></li>
	</ul>

	<form id="searchForm" action="" method="post" class="breadcrumb form-search">
		<div>
			<label>名称：</label><input id="name" name="name" type="text" maxlength="50" class="input-mini" value="${example.name}"/>
			<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="doSearch()"/>&nbsp;&nbsp;
			<input class="btn btn-primary" type="button" value="添加" id="create" />&nbsp;&nbsp;
		</div>
	</form>
	<table id="jqGrid"></table>
	<div id="jqGridPager"></div>

	
	<div id="createTemplate" style="display: none;">
        <form id="inputForm">
			<input type="hidden" id="id" name="id" value=""/>
			<table>
				<tr>
					<td>名称：</td>
					<td>
						<input type="text" id="name" name="name" maxlength="50" class="required" value=""/>
					</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td>
						<textarea id="remark" name="remark" style="width:300px;height: 50px;"></textarea>
					</td>
				</tr>
			</table>
        </form>
	</div>
</body>
</html>