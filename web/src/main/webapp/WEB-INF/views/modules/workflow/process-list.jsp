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
    	 $('#deploy').bind('click', function(){
   	    	$('#deploy_form').ajaxForm({
   	        	type : "post",
   		        url : "${ctx }/workflow/deploy.do",
   		        dataType : "json",
   		        timeout : 180000,
   		        beforeSubmit : function() {
   		        	if($("input[name='name']").val() == '') {
   		        		alertx('请填写名称！');
   		        		return false;
   		        	}
   		        	
   		        	//return $('#form').form('enableValidation').form('validate');
   		        },
   		        success : function(result, status) {
   		        	if(result.success == true){
   		        		reload();
   	 	            } else {
   	 	               alertx(result.msg);
   	 	            }
   		        },
   		        error : function() {
   		        	alertx('出现错误，稍后再试！');
   		        }
   	        }).submit();
   	    });
    	
    	
    		var grid = $("#jqGrid").jqGrid({
	            url: '${ctx}/workflow/loadProcessList.do',
	            mtype: "POST",
	            datatype: "json",
	    		jsonReader: {
	    			id: "id",
	    			root: "list"
	    		},
	            colModel: [
	                { label: 'tenantId', name: 'tenantId', width: 80},
	                { label: '名称', name: 'name', width: 150 },
	                { label: 'key', name: 'key', width: 150 },
	                { label:'版本号', name: 'version', width: 50 },
	                { label: 'xml', name: 'a', width: 150, formatter : function(value, options, row) {
	                	return formatterXml(value, options, row);
	                }},
	                { label: '图片', name: 'a', width: 150, formatter : function(value, options, row) {
	                	return formatterImage(value, options, row);
	                }},
	                { label: '部署时间', name: 'deploymentTime', width: 150 },
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
	    		grid.jqGrid('setGridHeight',$(window).height()-180);
	    	}).resize();
    	 
    });
    function formatterXml(value, options, row) {
    	return '<a class="gt_a_click" href="${ctx }/workflow/resource/read.do?resourceType=xml&processDefinitionId='+row.id+'" target="_blank">'+row.resourceName+'</a>';
    }
    function formatterImage(value, options, row) {
    	//image  diagramResourceName
    	return '<a class="gt_a_click" href="${ctx }/workflow/resource/read.do?resourceType=image&processDefinitionId='+row.id+'" target="_blank">'+row.diagramResourceName+'</a>';
    }
    function formatterAction(value, options, row) {
    	return '<a class="gt_a_click" onclick="del(\''+row.deploymentId+'\')" href="javascript:;">删除</a>&nbsp;|&nbsp;'+
         '<a class="gt_a_click" href="${ctx }/workflow/process/convert-to-model/'+row.id+'.do" href="javascript:;">转换为Model</a>';
    }
    function del(deploymentId) {
    	confirmx('确定删除？', function() {
	    	Share.Ajax({
	    		url : '${ctx}/workflow/process/delete',
	    		showMsg: false,
	    		params: {
	    			deploymentId: deploymentId
	    		},
	    		callback: function() {
	    			reload();
	    		}
	    	})
    	})
    }
   
    function reload() {
    	$("#jqGrid").setGridParam({
    		page: 1
        }).trigger("reloadGrid");
    }
    </script>
</head>
<body>

	<fieldset id="deployFieldset" >
		<div>部署新流程</div>
		<div><b>支持文件格式：</b>zip、bar、bpmn、bpmn20.xml</div>
		<form id="deploy_form"  method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<%-- <td>分行code：</td>
				<td>
					<select name="tenantId" editable='false' style="width:200px">
						<c:forEach var="item" items="${orgs}" varStatus="status">
							<option value="${item.orgCode}">${item.orgName}</option>
						</c:forEach>
				    </select>
				</td> --%>
				<td>名称：</td>
				<td>
					<input type="text" name="name" />
				</td>
				<td>文件：</td>
				<td>
					<input type="file" name="file" />
				</td>
				<td>
					<input id="deploy" type="button" value="提交" />
				</td>
			</tr>
			</table>
		</form>
	</fieldset>
	
	 <table id="jqGrid"></table>
	<div id="jqGridPager"></div>
		
</body>
</html>