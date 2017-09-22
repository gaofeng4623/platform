<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>${fns:getConfig('productName')}</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<c:set var="tabmode" value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}"/>
	<c:if test="${tabmode eq '1'}"><link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css" />
	<script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script></c:if>
	<script type="text/javascript">
		$(document).ready(function() {
			$.fn.initJerichoTab({
				renderTo: '#right', uniqueId: 'jerichotab',
				contentCss: { 'height': $('#right').height() - tabTitleHeight },
				tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
			});
			// 绑定菜单单击事件
			$("#menu a.menu").click(function(){
				// 一级菜单焦点
				$("#menu li.menu").removeClass("active");
				$(this).parent().addClass("active");
				// 左侧区域隐藏
				if ($(this).attr("target") == "mainFrame"){
					$("#left,#openClose").hide();
					wSizeWidth();
					$(".jericho_tab").hide();
					$("#mainFrame").show();
					return true;
				}
				// 左侧区域显示
				$("#left,#openClose").show();
				if(!$("#openClose").hasClass("close")){
					$("#openClose").click();
				}
				// 显示二级菜单
				var menuId = "#menu-" + $(this).attr("data-id");
				if ($(menuId).length > 0){
					$("#left .accordion").hide();
					$(menuId).show();
					// 初始化点击第一个二级菜单
					if (!$(menuId + " .accordion-body:first").hasClass('in')){
						$(menuId + " .accordion-heading:first a").click();
					}
					if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")){
						$(menuId + " .accordion-body a:first i").click();
					}
					// 初始化点击第一个三级菜单
					$(menuId + " .accordion-body li:first li:first a:first i").click();
				}else{
					// 获取二级菜单数据
					$.get($(this).attr("data-href"), function(data){
						if (data.indexOf("id=\"loginForm\"") != -1){
							alert('未登录或登录超时。请重新登录，谢谢！');
							top.location = "${ctx}";
							return false;
						}
						$("#left .accordion").hide();
						$("#left").append(data);
						// 链接去掉虚框
						$(menuId + " a").bind("focus",function() {
							if(this.blur) {this.blur();};
						});
						// 二级标题
						$(menuId + " .accordion-heading a").click(function(){
							$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
							if(!$($(this).attr('data-href')).hasClass('in')){
								$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
							}
						});
						// 二级内容
						$(menuId + " .accordion-body a").click(function(){
							$(menuId + " li").removeClass("active");
							$(menuId + " li i").removeClass("icon-white");
							$(this).parent().addClass("active");
							$(this).children("i").addClass("icon-white");
						});
						// 展现三级
						$(menuId + " .accordion-inner a").click(function(){
							var href = $(this).attr("data-href");
							if($(href).length > 0){
								$(href).toggle().parent().toggle();
								return false;
							}
							return addTab($(this));
						});
						// 默认选中第一个菜单
						$(menuId + " .accordion-body a:first i").click();
						$(menuId + " .accordion-body li:first li:first a:first i").click();
					});
				}
				// 大小宽度调整
				wSizeWidth();
				return false;
			});
			// 初始化点击第一个一级菜单
			$("#menu a.menu:first span").click();
			$("#userInfo .dropdown-menu a").mouseup(function(){
				return addTab($(this), true);
			});
			// 鼠标移动到边界自动弹出左侧菜单
			$("#openClose").mouseover(function(){
				if($(this).hasClass("open")){
					$(this).click();
				}
			});
		});
		function addTab($this, refresh){
			$(".jericho_tab").show();
			$("#mainFrame").hide();
			$.fn.jerichoTab.addTab({
				tabFirer: $this,
				title: $this.text(),
				closeable: true,
				data: {
					dataType: 'iframe',
					dataLink: $this.attr('href')
				}
			}).loadData(refresh);
			return false;
		}
		function wholeQuery(){
			$("#left,#openClose").hide();
			wSizeWidth();
			$(".jericho_tab").hide();
			$("#mainFrame").show();
			$("#mainFrame").attr("src","${ctx}/plat/platInformation/wholeQuery?titleCont="+encodeURI(encodeURI($("#wholeQuery").val()))+"&page=1&rows=6&type=6");
			hideSearch();
		}
		function myFavorites(){
			$("#left,#openClose").hide();
			wSizeWidth();
			$(".jericho_tab").hide();
			$("#mainFrame").show();
			$("#mainFrame").attr('src',"${ctx}/platfavorites/platFavorites/index");
			
		}
		function notepad(){
			var html = "";
			$.jBox("iframe:${ctx}/home/memo/index", {
			    title: "便笺",
			    width: 1230,
			    top: 30,
			    height: 560,
			    buttons: {"关闭":true},
			    loaded : function(h) {   //隐藏滚动条  
	                $(".jbox-content", top.document).css( "overflow-y", "hidden");   
	            }  
			});
		}
		function showSearch(){
			$("#search_div").show();
			$("#wholeQuery").focus();
			$(".search_btn").hide();
		}
		function hideSearch(){
			$("#search_div").hide();
			$(".search_btn").show();
		}
	</script>
	<style type="text/css">
		#main {padding:0;margin:0;} #main .container-fluid{padding:10px;background-color: #EDEEF0;}
		#header {margin:0;position:static;} #header li {font-size:14px;_font-size:12px;border-left: 1px solid #24659D;}
		#header .brand {font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:22px;font-weight:bold;padding:0px 0px 0px 15px;}
		#header .productLogo {height: 50px;line-height: 50px;}
		#header .navbar-inner {filter: none;}
		#footer {margin:8px 0 0 0;padding:3px 0 0 0;font-size:11px;text-align:center;border-top:2px solid #0663A2;}
		#footer, #footer a {color:#999;} #left{overflow-x:hidden;overflow-y:auto;} #left .collapse{position:static;}
		#header_top {height: 40px;} #header_top .navbar-inner{background-color: #1B5A90;min-height: 20px;height:40px;filter: none;}
		#header_top .input-append {padding: 8px 0px 0px 20px;} #header_top .search_input {color: white;background-color: #07447A;border-color: #07447A;width: 400px;height:17px;padding-left: 25px;}
		#menu {background: url(${ctxStatic}/images/edge_t.jpg) center top repeat;}
		#userControl>li>a{padding-top: 12px;}
	</style>
</head>
<body style="height: 100%;">
<div id="main">
	<div id="header_top">
		<div class="navbar navbar-fixed-top">
		  	<div class="navbar-inner">
		    	<div class="row-fluid">
					<div class="span5">
						<ul id="userControl" class="nav pull-left">
							<li id="userInfo" class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息" style="background: transparent;">
									<i class="icon-user"></i>&nbsp;您好, ${fns:getUser().name}
								</a>
								<ul class="dropdown-menu">
									<li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
									<li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
								</ul>
							</li>
							<li>
								<a href="javascript:void(0)" title="个人收藏" onclick="myFavorites();" style="background: transparent;"><i class="icon-tag icon-white"></i>&nbsp;收藏夹&nbsp;</a>
							</li>
							<li>
								<a href="javascript:void(0)" title="便笺" onclick="notepad();" style="background: transparent;"><i class="icon-tags icon-white"></i>&nbsp;便笺&nbsp;</a>
							</li>
						</ul>
					</div>
					<div class="span7">
						<ul class="nav pull-right">
							<li>
								<div id="search_div" class="input-prepend input-append" style="display: none;">
								  	<span class="add-on" style="height: 19px;line-height:18px;background-color:#07447A;border:0px;margin-right: -24px">
										<i class="icon-search icon-black" onclick="wholeQuery()"></i>
									</span>
									<input type="text" id="wholeQuery" class="search_input" onblur="hideSearch();" onKeyDown="if(event.keyCode == 13) wholeQuery();"/>
									<span class="add-on" style="height: 19px;line-height:18px;background-color:#07447A;border:0px;margin-left: -24px">
										<div style="color: #fff;cursor: pointer;" onclick="hideSearch()">x</div>
									</span>
								</div>
								<div class="search_btn" style="padding: 10px 10px 0px 0px;cursor: pointer;"><i class="icon-search icon-black" onclick="showSearch()"></i></div>
							</li>
							<li>
								<a href="${ctx}/logout" title="退出登录" style="background: transparent;padding-top:12px;">退出</a>
							</li>
						</ul>
					</div>
				</div>
		  	</div>
		</div>
	</div>
	<div id="header" class="navbar navbar-fixed-top">
		<div class="navbar-inner" style="padding: 10px;">
			<div class="brand">
				<div class="productLogo">
					<img src="${ctx}/static/index/images/logo.png"/>
				</div>
			</div>
			<div class="nav-collapse">
				<ul id="menu" class="nav pull-right">
					<c:set var="firstMenu" value="true"/>
					<c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
						<c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
							<li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
								<c:if test="${empty menu.href}">
									<a class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" data-id="${menu.id}" style="text-align: center;min-width: 80px;padding: 5px 15px"><i class="icon-${not empty menu.icon ? menu.icon : 'circle-arrow-right'}" style="font-size: 20px"></i>&nbsp;<br/><span>${menu.name}</span></a>
								</c:if>
								<c:if test="${not empty menu.href}">
									<a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame" style="text-align: center;min-width: 80px;padding: 5px 15px"><i class="icon-${not empty menu.icon ? menu.icon : 'circle-arrow-right'}" style="font-size: 20px"></i>&nbsp;<br/><span>${menu.name}</span></a>
								</c:if>
							</li>
							<c:if test="${firstMenu}">
								<c:set var="firstMenuId" value="${menu.id}"/>
							</c:if>
							<c:set var="firstMenu" value="false"/>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div id="content" class="row-fluid">
			<div id="left"></div>
			<div id="openClose" class="close">&nbsp;</div>
			<div id="right">
				<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height=""></iframe>
			</div>
		</div>
		<div id="footer" class="row-fluid">
			Copyright &copy; 2012-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} - Powered By gtzn ${fns:getConfig('version')}
		</div>
	</div>
</div>
<script type="text/javascript">
	var leftWidth = 160; // 左侧窗口大小
	var tabTitleHeight = 33; // 页签的高度
	var htmlObj = $("html"), mainObj = $("#main");
	var headerObj = $("#header"), footerObj = $("#footer");
	var frameObj = $("#left, #openClose, #right, #right iframe");
	function wSize(){
		var minHeight = 500, minWidth = 980;
		var strs = getWindowSize().toString().split(",");
		htmlObj.css({"overflow-x":strs[1] < minWidth ? "auto" : "hidden", "overflow-y":strs[0] < minHeight ? "auto" : "hidden"});
		mainObj.css("width",strs[1] < minWidth ? minWidth - 10 : "auto");
		frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
		$("#openClose").height($("#openClose").height() - 5);// <c:if test="${tabmode eq '1'}">
		$(".jericho_tab iframe").height($("#right").height() - tabTitleHeight); // </c:if>
		wSizeWidth();
	}
	function wSizeWidth(){
		if (!$("#openClose").is(":hidden")){
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
		}else{
			$("#right").width("100%");
		}
	}
	function openCloseClickCallBack(b){
		$.fn.jerichoTab.resize();
	}
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>