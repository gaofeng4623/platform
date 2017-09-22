<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>收藏条目管理</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			var grid = $("#grid").jqGrid({
		        url: '${ctx}/platfavoritesitem/platFavoritesItem/load?favoritesid=${favoritesid}',
		        mtype: "POST",
		        datatype: "json",
				jsonReader: {
					id: "id",
					root: "list"
				},
		        colModel: [
					{ label: 'id', name: 'id', hidden:true},
					{ label: '收藏夹', name: 'remarks', align: 'center', width: 30 },
					{ label: '收藏类别', name: 'type', align: 'center', width: 30 },
					{ label: '标题', name: 'title', align: 'left', width: 150, formatter : function(value, options, row){
						var tail='';
						if(row.type=='公告信息'){
							tail='<a href="${ctx}/notice/noticeInfo/DetailForm?id='+row.linkid+'">'+row.title+'</a>'
						}else if(row.type=='行业资讯'){
							tail='<a href="${ctx}/plat/platInformation/informationForm?id='+row.linkid+'">'+row.title+'</a>'
						} else if (row.type == '知识库') {
                            tail='<a href="${ctx}/plat/platInformation/go?type=3&id='+row.linkid+'">'+row.title+'</a>'
                        }
						return tail;
					}},
					{ label: '收藏时间', name: 'createDate', align: 'center', width: 50 },
		            { label: '操作', name: 'oo', width: 50, align: 'center', formatter : function(value, options, row) {
		            	var tail='';
						if(row.type=='公告信息'){
							tail='&nbsp;&nbsp;<a href="${ctx}/notice/noticeInfo/DetailForm?id='+row.linkid+'">详情</a>'
						}else if(row.type=='行业资讯'){
							tail='&nbsp;&nbsp;<a href="${ctx}/plat/platInformation/informationForm?id='+row.linkid+'">详情</a>'
						}
	            		return '<a href="#" onclick="openWin(\'' + '${ctx}/platfavoritesitem/platFavoritesItem/editForm?id='+row.id+'\')">修改</a>'+
						'&nbsp;&nbsp;<a href="#" onclick="return confirmx(\'确认要取消该收藏条目吗？\', function() {deleteById(\'' + row.id + '\');})">取消收藏</a>'+
						tail;
		            }}
		        ],
				viewrecords: true,
				autowidth: true,
				multiselect: true,
				rownumbers: true,
		        rowNum: 20 ,
		        pager: "#pager" 
		    });
			$(window).resize(function(){
				var ss = pageSize();
				grid.jqGrid('setGridWidth',ss.WinW);
				grid.jqGrid('setGridHeight', ss.WinH-120);
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
				url:'${ctx}/platfavoritesitem/platFavoritesItem/delete',
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

		/**
		 * 批量删除数据
		 */
		function multipleDelete() {
			var ids=$("#grid").jqGrid('getGridParam','selarrrow');
			//将ids 转换为字符串
			if(ids==null || ids=='' || ids ==undefined){
				alert("请选择要取消收藏的数据");
				return;
			}
			$.ajax({
				type: "POST",
				url:'${ctx}/platfavoritesitem/platFavoritesItem/multiDel',
				data:{
					idList:ids.join(","),
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
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li class="btns"><input id="btnDelete" class="btn btn-primary" type="button" value="批量取消收藏" onclick="return confirmx('确认要取消收藏选中条目吗？',multipleDelete)"/></li>
		</ul>
	</form>
	<sys:message content="${message}"/>
	<table id="grid"></table>
	<div id="pager"></div>
</body>
</html>