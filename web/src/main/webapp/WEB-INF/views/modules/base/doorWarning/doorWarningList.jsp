<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>门禁报警管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script src="${ctxStatic}/jqGrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jqGrid/js/trirand/i18n/grid.locale-cn.js" type="text/ecmascript" ></script>
	<link href="${ctxStatic}/jqGrid/css/jquery-ui.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${ctxStatic}/jqGrid/css/trirand/ui.jqgrid.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript">
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/base/doorWarning/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: 'id', name: 'id', key: true, hidden: true },
					{ label: '门禁号', name: 'door.doorId', width: 30 },
					{ label: '门禁名称', name: 'door.note', width: 50 },
		            { label: '门禁类型', name: 'door.doorType', width: 30,formatter: function(value, options, row) {
		            	if(value == '1'){
		            		return '大门';
		            	}else if(value == '0'){
		            		return '非大门';
		            	}else{
		            		return '';
		            	}
		            } },
		            { label: '门禁地址', name: 'door.ip', width: 30 },
					{ label: '电子标签号', name: 'rfid', width: 50 },
					{ label: '报警时间', name: 'warnDate', width: 50 },
					{ label: '报警原因', name: 'warnReason', width: 50 },
					{ label: '处理人', name: 'handlerName', width: 30 },
					{ label: '处理时间', name: 'handleDate', width: 50 },
					{ label: '处理结果', name: 'handleResult', width: 100 }
		        ],
				viewrecords: true,
				autowidth: true,
				autoheight: true,
				altRows: true,
				multiselect: true,
				rownumbers: true,
		      	//height: $(window).height() - 170,
		        rowNum: 20 ,
		        pager: "#pager" 
		    });
			$(window).resize(function(){
				grid.jqGrid('setGridWidth',$(window).width()-2);
				grid.jqGrid('setGridHeight',$(window).height()-170);
			}).resize();
		});
		//获取查询参数
		function getQueryParams() {
			var serializeObj = {};
			$($("#searchForm").formToArray()).each(function() {
				serializeObj[this.name] = $.trim(this.value);
			});
			return serializeObj;
		}
		//查询
		function searchForm() {
			var serializeObj = getQueryParams();
			$("#grid").setGridParam({
				page: 1,
				postData: serializeObj,
		    }).trigger("reloadGrid");
		}
		//处理门禁报警记录
		function doHandle() {
			//获取多选到的id集合  
			var ids = $("#grid").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				alertx('请至少选择一条门禁报警记录！');
				return ;
			}
			
			var doorWarnings = [];
			//遍历访问这个集合  
			$(ids).each(function (index, id){  
				var row = $("#grid").jqGrid('getRowData', id);
				doorWarnings.push('{'+row["door.doorId"]+','+row.rfid+','+row.warnDate+'}');
			});
			var doorWarningHandled = doorWarnings.join(",");
			
			$.jBox("id:handleDialog", { 
    		    title: "处理门禁报警", 
    		    width: 400, 
    		    height: 200, 
    		    buttons: {"提交":'ok',"取消":'cancel'},
    		    submit: function(v,h,f) {
    		    	if (v == 'ok') {
    		    		if ($("#jbox-content #handleForm").valid()) {
    		    			handleWarning(ids, doorWarningHandled, f.handleResult);
    		    		} else {
    		    			return false;
    		    		}
    		    	}
    		    }
    		});
		}
		
		function handleWarning(ids, doorWarningHandled, handleResult) {
			$.ajax({
	            type: "post",
	            dataType: "json",
	            url: "${ctx}/base/doorWarning/handleWarning",
	            data: {ids:ids, doorWarnings:doorWarningHandled, handleResult:handleResult},
	            timeout : 180000,
		        beforeSubmit : function() {
		        	//alert(ids);
		        },
		        success : function(result, status) {
		        	if(result.success == true) {
		        		alertx(result.msg);
		        		var serializeObj = getQueryParams();
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
		//窗口测试，不用可删除
		function openWin() {
			//html:、id:、get:、post:、iframe:
			//$.jBox.open(content, title, width, height, options);
			$.jBox.open("get:${ctx}/base/doorWarning/form", "窗口模式", 800, 400);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/base/doorWarning/list">门禁报警列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="door" method="post" class="breadcrumb form-search ">
		<ul class="ul-form">
			<li>
				<label>门禁编号：</label>
				<input id="doorId" name="door.doorId" type="text" maxlength="50" class="input-mini"/>
			</li>
			<li><label>门禁类型：</label>
				<select id="doorType" name="door.doorType" class="input-small">
					<option value="">全部</option>
					<option value="1">大门</option>
					<option value="0">非大门</option>
				</select>
			</li>
			<li>
				<label>报警时间：</label>
				<input id="warningTimeStart" name="warningTimeStart" type="text" class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
				至：
				<input id="warningTimeEnd" name="warningTimeEnd" type="text" class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			
			<li class="btns">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/>
			<shiro:hasPermission name="base:doorWarning:handle">
				<input id="btnHandle" class="btn btn-primary" type="button" value="处理" onclick="doHandle()"/>
			</shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
	<div id="handleDialog" style="display: none;">
        <form id="handleForm" class="breadcrumb form-search">
			<table>
				<tr>
					<td>处理结果：</td>
					<td>
						<textarea name="handleResult" rows="3" class="input-xlarge" class="required"></textarea>
					</td>
				</tr>
			</table>
        </form>
		</div>
</body>
</html>