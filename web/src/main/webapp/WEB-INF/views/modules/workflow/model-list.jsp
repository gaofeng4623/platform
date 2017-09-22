<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>流程列表</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctxStatic}/jqGrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/js/trirand/i18n/grid.locale-cn.js" type="text/ecmascript" ></script>
	<link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/jquery-ui.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/trirand/ui.jqgrid.css" />

	<script type="text/javascript">
	    $(function() {
	    	$('#create').click(function() {
	    		$.jBox("id:createModelTemplate", { 
	    		    title: "新增模型", 
	    		    width: 500, 
	    		    height: 350, 
	    		    buttons:{"提交":'ok',"取消":'cancel'},
	    		    submit:function(v,h,f){
	    		    	if (v == 'ok') {
		    		    	if (f.name == '') {
	    						alert('请填写名称！');
	    						return false;
	    					}
	    					if (f.key == '') {
	    						alert('请填写key！');
	    						return false;
	    					}
	    					Share.Ajax({
	    						url: '${ctx}/workflow/model/create',
	    						params : {
	    							name: f.name,
    								key: f.key,
    								description: f.description
	    						},
	    						callback: function() {
	    							reloadGrid();
	    						}
	    					})
	    		    	}
	    		    }
	    		});
	    	});
	    	
	    	
	    	var grid = $("#jqGrid").jqGrid({
	            url: '${ctx}/workflow/model/loadList.do',
	            mtype: "POST",
	            datatype: "json",
	    		jsonReader: {
	    			id: "id",
	    			root: "list"
	    		},
	            colModel: [
	                { label: 'tenantId', name: 'tenantId', width: 50},
	                { label: 'key', name: 'key', width: 80 },
	                { label: '名称', name: 'name', width: 80 },
	                { label: '修订版本', name: 'revision', width: 40 },
	                { label:'创建时间', name: 'createTime', width: 140 },
	                { label: '最后更新时间', name: 'lastUpdateTime', width: 150 },
	                { label: '操作', name: 'a', width: 150, formatter : function(value, options, row) {
	                	return formatterAction(value, options, row);
	                }}
	            ],
	    		viewrecords: true,
	    		autowidth: true,
	            rowNum: 20 ,
	            pager: "#jqGridPager" 
	        });
	    	$(window).resize(function(){
	    		grid.jqGrid('setGridWidth',$(window).width());
	    		grid.jqGrid('setGridHeight',$(window).height()-120);
	    	}).resize();
	    	
	    });
	   
	    
	    function deploy(modelId) {
	    	Share.Ajax({
	    		url : '${ctx}/workflow/model/deploy',
	    		showMsg: true,
	    		showWaiting: true,
	    		params: {
	    			modelId: modelId
	    		},
	    		callback: function() {
	    			//reload();
	    		}
	    	})
	    }
	    function del(modelId) {
	    	Share.Ajax({
	    		url : '${ctx}/workflow/model/delete',
	    		showMsg: false,
	    		params: {
	    			modelId: modelId
	    		},
	    		callback: function() {
	    			searchForm();
	    		}
	    	})
	    }
	    function formatterAction(value, options, row) {
	    	return '<a class="gt_a_click" href="${ctx}/modeler.html?modelId='+row.id+'" target="_blank">编辑</a>&nbsp;|&nbsp;'+
			'<a class="gt_a_click" href="#" onclick="deploy(\''+row.id+'\')">部署</a>&nbsp;|&nbsp;'+
			'导出(<a class="gt_a_click" href="${ctx}/workflow/model/export/'+row.id+'/bpmn.do" target="_blank">BPMN</a>)&nbsp;|&nbsp;'+
            '<a class="gt_a_click" href="#" onclick="del(\''+row.id+'\')">删除</a>';
	    }
	    function searchForm() {
	    	$("#jqGrid").setGridParam({
				page: 1,
				postData : {
					tenantId : $("select[name='tenantId']").val(),
					key : $("input[name='key']").val(),
					name : $("input[name='name']").val()
				}
		    }).trigger("reloadGrid");
	    }
	</script>
</head>
<body>
	<form id="search-form" method="post" style="margin: 0">
    	key:
    	<input name="key"  type="text" value="${key}"/>
    	名称:
 				<input name="name" type="text" value="${name}"/>
		<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/>&nbsp;&nbsp;
		<input id="create" class="btn btn-primary" type="button" value="添加" />&nbsp;&nbsp;
    </form>
    <table id="jqGrid"></table>
	<div id="jqGridPager"></div>
	<div id="createModelTemplate" style="display: none;">
        <form id="modelForm" title="创建模型">
		<table>
			<tr>
				<td>名称：</td>
				<td>
					<input id="form_name" name="name" type="text" />
				</td>
			</tr>
			<tr>
				<td>KEY：</td>
				<td>
					<input id="form_key" name="key" type="text" />
				</td>
			</tr>
			
			<tr>
				<td>描述：</td>
				<td>
					<textarea id="form_description" name="description" style="width:300px;height: 50px;"></textarea>
				</td>
			</tr>
		</table>
        </form>
	</div>
</body>
</html>