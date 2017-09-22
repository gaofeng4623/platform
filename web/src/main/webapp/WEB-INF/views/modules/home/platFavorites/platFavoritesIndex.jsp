<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>我的收藏夹</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
    <script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
    <sys:message content="${message}"/>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading" >
		    	<label class="accordion-toggle" style="background-color:#24659D;margin-bottom: 0px;color:#fff;">收藏夹
		    	<i style="margin-left:5px;" class="icon-white icon-refresh pull-right" onclick="refreshTree();" title='刷新'></i>
		    	<i style="margin-left:5px;" class="icon-white icon-minus-sign  pull-right" onclick="delFav()" title='删除'></i>
		    	<i style="margin-left:5px;" class="icon-white icon-edit pull-right" onclick="add('1')" title='修改'></i>
		    	<i style="margin-left:5px;" class="icon-white icon-plus-sign  pull-right" onclick="add('0')" title='新增'></i>
		    	</label>
		    </div>
			<div id="ztree" class="ztree" style="overflow:auto;"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="treeContents" src="${ctx}/platfavoritesitem/platFavoritesItem/list" width="100%" height="100%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
					$('#treeContents').attr("src","${ctx}/platfavoritesitem/platFavoritesItem/list?favoritesid="+id);
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/platfavorites/platFavorites/treeData",function(data){
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
			});
		}
		refreshTree();
		 
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
		
		function add(id){
			if(id=='0'){
				$.jBox.open("get:${ctx}/platfavorites/platFavorites/form", "新增收藏夹", 800, 340,{closed:function(){ refreshTree();} ,buttons: {  }});
			}else{
				if($.fn.zTree.getZTreeObj('ztree').getSelectedNodes()[0]==undefined){
					alert("请选择需要修改的收藏夹!");
					return;
				}
				id=$.fn.zTree.getZTreeObj('ztree').getSelectedNodes()[0].id;
				if(id==null || id==undefined || id==''){
					alert("请选择需要修改的收藏夹!");
					return;
				}
				if(id=='002' || id=='001'){
					alert("系统默认收藏夹不允许修改!");
					return;
				}
				$.jBox.open("get:${ctx}/platfavorites/platFavorites/form?id="+id, "修改收藏夹", 800, 340,{closed:function(){refreshTree();} ,buttons: {  }});
			}
		} 
		function delFav(){
			if($.fn.zTree.getZTreeObj('ztree').getSelectedNodes()[0]==undefined){
				alert("请选择需要删除的收藏夹!");
				return;
			}
			var id=$.fn.zTree.getZTreeObj('ztree').getSelectedNodes()[0].id;
			if(id==null || id==undefined || id==''){
				alert("请选择需要删除的收藏夹!");
				return;
			}
			if(id=='002' || id=='001'){
				alert("系统默认收藏夹不允许删除!");
				return;
			}
			/* if(confirmx('删除收藏夹会删除该收藏夹下的所有收藏内容，确认要删除该收藏夹么？')){
			} */
			confirmx('删除收藏夹会删除该收藏夹下的所有收藏内容，确认要删除该收藏夹么？',function(){

				$.ajax({
					 type: "POST",
					 url:'${ctx}/platfavorites/platFavorites/delete?id='+id,
					 dataType: "json",
					 success:  function(result, status) {
			        	if(result.success == true) {
			        		$.jBox.info(result.msg, '提示');
		 	            } else {
		 	            	$.jBox.info(result.msg, '提示');
		 	            }
			        	refreshTree();
			        	$('#treeContents').attr("src","${ctx}//platfavoritesitem/platFavoritesItem/list");
				    },
			        error : function() {
			        	$.jBox.info(result.msg, '提示');
			        }
				});
			});
			
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>