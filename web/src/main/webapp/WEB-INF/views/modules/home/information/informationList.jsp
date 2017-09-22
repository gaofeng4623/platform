<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>行业信息查询</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
/* 		var hasEditPermission = false;
 */		/* <shiro:hasPermission name="notice:noticeInfo:edit">
			hasEditPermission = true;
		</shiro:hasPermission> */
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/plat/platInformation/load',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: '标题', width: 150, name: 'title', formatter : function(value, options, row){
						return '<a href="${ctx}/plat/platInformation/informationForm?id='+row.id+'">'+row.title+'</a>';
					}},
					{ label: '发布时间', width: 50, align: 'center', name: 'releaseDate'},
					{ label: 'url', name: 'url',"hidden":true},
					{ label: 'id', name: 'id',"hidden":true},
		            { label: '查看', width: 50, align: 'center', formatter : function(value, options, row) {
							return '<a href="${ctx}/plat/platInformation/informationForm?id='+row.id+'">详情</a>'+
							'&nbsp;&nbsp;<a href="${ctx}/plat/platInformation/editForm?id='+row.id+'">修改</a>'+
							'&nbsp;&nbsp;<a href="javascript:void(0)" onclick="del(\''+row.id+'\')">删除</a>';
							//return '&nbsp;&nbsp;<a href="javascript:void(0)" onclick="editForm(\''+row.id+'\')">修改</a>';
		            } }
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
				grid.jqGrid('setGridHeight', ss.WinH-160);
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
		
		function editForm(id) {
			$.jBox.open("get:${ctx}/plat/platInformation/editForm?id="+id, "行业信息修改", 850, 500,{closed:function(){window.location.href=window.location.href;} ,buttons: {  }});
		}
		function del(id){
			if(confirm('确认要删除该信息吗？')){
				$.ajax({
					type: "POST",
					url:'${ctx}/plat/platInformation/delete?id='+id,
					dataType: "json",
					success:  function(result, status) {
						if(result.success == true) {
							alertx(result.msg);
							$("#grid").trigger("reloadGrid");
							window.parent.refreshTree();
						} else {
							alertx(result.msg);
						}
					},
					error : function() {
						alert('出现错误，稍后再试！');
					}
				});
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/plat/platInformation/list">资讯列表</a></li>
		<li><a href="${ctx}/plat/platInformation/manual">资讯导入</a></li>
	</ul>
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>标题：</label>
				<input type="text" name="title"  class="input-medium"/>
			</li>
			<li><label>发布时间：</label>
			 <input name="stime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${platInformation.stime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> - 
				<input name="etime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${platInformation.etime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
					</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置" onclick="reset()"/>&nbsp;&nbsp;</li>
			<li class="clearfix"></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>