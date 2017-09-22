<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>用户管理 top.$.jBox</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
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
		    	<a class="accordion-toggle">系统角色<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
				<!-- 导航区 -->
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#home" role="tab" data-toggle="tab"><i class="icon-user"></i>&nbsp;角色成员</a></li>
					<li role="presentation"><a href="#profile" role="tab" data-toggle="tab"><i class="icon-th-large"></i>&nbsp;权限分配</a></li>
					<li role="presentation"><a href="#settings" role="tab" data-toggle="tab"><i class="icon-lock"></i>&nbsp;其他设置</a></li>
				</ul>

				<!-- 面板区 -->
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="home">...</div>
					<div role="tabpanel" class="tab-pane" id="profile">...</div>
					<div role="tabpanel" class="tab-pane" id="settings">...</div>
				</div>
		</div>
	</div>
	<script type="text/javascript">
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
					$('#contentIframe').attr("src","${ctx}/sys/role/list?office.id="+id+"&office.name="+treeNode.name);
					/* if('2' == treeNode.type){//部门
						$('#contentIframe').attr("src","${ctx}/sys/role/list2?office.id="+id+"&office.name="+treeNode.name);
					}else{//公司
						$('#contentIframe').attr("src","${ctx}/sys/role/list2?company.id="+id+"&company.name="+treeNode.name);
					} */
				}
			}
		};

		function refreshTree(){
			$.getJSON("${ctx}/sys/office/treeData",function(data){
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
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>