<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title></title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#grid").jqGrid({
	        url: '${ctx}/sys/connoisseur/load',
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
	            { label: '姓名', name: 'name', width: 100 },
	            { label:'电话', name: 'phone', width: 150 },
	            { label:'邮件', name: 'email', width: 150 },
	            { label:'借阅时间', name: 'createtime', width: 150 },
	            { label:'借阅类型', name: 'processname', width: 150 },
	            { label: '操作', width: 100, align: 'center', formatter : function(value, options, row) {
		    		var docCheckToDoUrl = "";
		    		docCheckToDoUrl = "${docCheckShowHistory}"+row.workflowtype+"/"+row.id;
		    		return '<a href="${ctx}/sys/connoisseur/toBorrowInfo?hrefURL='+ docCheckToDoUrl +'">详情</a>';
            } }
	            
	        ],
			viewrecords: true,
			autowidth: true,
			autoheight: true,
	      	height: $(window).height() - 108,
	        rowNum: 15 ,
	        pager: "#pager",
	        rownumbers: true,
	        postData : getParams()
	    });
	});
	function getParams () {
		var serializeObj = {};
		$($("#searchForm").formToArray()).each(function() {
			serializeObj[this.name] = $.trim(this.value);
		});
		return serializeObj;
	}
	
	function searchForm() {
		//要件查询
		var serializeObj = getParams();
		$("#grid").setGridParam({
			page: 1,
			postData: serializeObj,
	    }).trigger("reloadGrid");
	}
	function windowOpenOfProc(docCheckToDoUrl) {
// 		window.open("${ctx}/sys/connoisseur/detail",'','width=800,height=400');
		$.jBox.open("get:${ctx}/sys/connoisseur/detail");
// 		$.jBox("iframe:${ctx}/sys/connoisseur/detail", {
// 		    title: "详情",
// 		    width: 950,
// 		    height: 350,
// 		    buttons: { '关闭': true }
// 		});
	}
// 		function page(n,s){
// 			$("#pageNo").val(n);
// 			$("#pageSize").val(s);
// 			$("#searchForm").submit();
// 	    	return false;
// 	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/connoisseur/toBorrowSupervision">列表</a></li>
	</ul>
<%-- 	<form:form id="searchForm" modelAttribute="connoisseur" action="${ctx}/sys/connoisseur/list" method="post" class="breadcrumb form-search"> --%>
<%-- 		<label>姓名 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/> --%>
<!-- 		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/> -->
<%-- 	</form:form> --%>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>