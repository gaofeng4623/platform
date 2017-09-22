<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title></title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<%@ include file="/WEB-INF/views/include/jqgrid.jsp"%>
	<script type="text/javascript">
		var hasEditPermission = false;
		<shiro:hasPermission name="notice:noticeInfo:edit">
			hasEditPermission = true;
		</shiro:hasPermission>
		$(document).ready(function() {
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/notice/noticeInfo/load?type=${type}',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: '分类', name: 'ext', align:'center', width: 50 },
					{ label: '标题', name: 'title', align:'left', width: 150, formatter : function(value, options, row) {
						return '<a href="${ctx}/notice/noticeInfo/DetailForm?id='+row.id+'">'+value+'</a>';
					}},
					{ label: '公告时间', align:'center', name: 'date', width: 50 },
		            { label: '查看', width: 30, align:'center', formatter : function(value, options, row) {
							return '<a href="${ctx}/notice/noticeInfo/DetailForm?id='+row.id+'">详情</a>';
		            } }
		        ],
				viewrecords: true,
				autowidth: true,
				multiselect: false,
				rownumbers: true,
		        rowNum: 30,
		        pager: "#pager" 
		    });
			$(window).resize(function(){
				var ss = pageSize();
				grid.jqGrid('setGridWidth',ss.WinW);
				grid.jqGrid('setGridHeight',ss.WinH - 60);
			}).resize();
		});

		//查询
		function searchForm() {
			var serializeObj = {};
			$($("#searchForm").formToArray()).each(function() {
				serializeObj[this.name] = $.trim(this.value);
			});
			
		}
		//窗口测试，不用可删除
		function openWin(url) {
			//html:、id:、get:、post:、iframe:
			//$.jBox.open(content, title, width, height, options);
			$.jBox.open("get:" + url, "修改条目", 615, 380,{id:'recForm',top:'10%',left:'0%',zIndex:1900,buttons: {  },closed:function(){$("#grid").trigger("reloadGrid");} });
		}

		/**
		 * 删除数据
		 */
		function deleteById(id) {
			$.ajax({
				type: "POST",
				url:'${ctx}/notice/noticeInfo/delete',
				data:{
					id: id,
				},
				dataType: "json",
				success:  function(result, status) {
					if(result.success == true) {
						$.jBox.tip(result.msg, 'success');
					} else {
						$.jBox.tip(result.msg, 'error');
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
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>