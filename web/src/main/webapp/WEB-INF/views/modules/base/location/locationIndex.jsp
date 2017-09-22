<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>位置管理</title>
	<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
	
	<link href="${ctxStatic}/common/gtzn.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
	<sys:message content="${message}"/>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">位置管理</a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="locationContent" src="${ctx}/base/location/list" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		var rootNode = {id:0,pId:-1,name:"中国工商银行",leaf:false,open:true,children:{}};
		var setting = {
				data:{
					simpleData:{
						enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'
					}
				},
				async: {
					autoParam:["id"],
                    enable: true,
                    type:'get',
                    url: "${ctx}/base/location/getChildData"
                },
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
					$('#locationContent').attr("src","${ctx}/base/location/list?id="+id);
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/base/location/treeData",function(data){
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(false);
			});
		}
		//refreshTree();
		
		$(document).ready(function(){
			createTree(0);
		});
		
		function getTreeList(nodeId){
			var nodeList = null;
			$.ajax({
				async : false,
				   cache : false,
				   type : "get",
				   dataType : "json",
				   url : "${ctx}/base/location/getChildData?id="+nodeId,
				   error : function() {
				   		alertx("请求失败");
				   },
				   success : function(data) {
					   nodeList = data;
				   }
			});
			return nodeList;
		}
		function createTree(rootId){
			var data = getTreeList(rootId);
			rootNode.children = data;
			$.fn.zTree.init($("#ztree"), setting,rootNode);
		}
		 
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
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>