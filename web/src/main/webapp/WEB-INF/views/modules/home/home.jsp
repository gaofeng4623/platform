<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>冠通科技生态智慧一体化管理平台</title>
	<%@include file="/WEB-INF/views/include/platHead.jsp" %>
	<script type="text/javascript">
	$(document).ready(function() {
		//设置右侧显示内容页
		var src="${ctx}/home/right";
		$('#mainFrame1').attr('src',src);
	});
	</script>
	<style type="text/css">
		html,body{
			height: 100%;
		}
		.left_line_div {
			margin:auto;
			width:1px;
			height:12px;
			text-align:center;
			background-color:#b7c3bf;
		}
		.right_div {
			width: 100%;
			float: left;
			padding: 5px;
			height: 100%;
		}
		.div_shortcut {
			width:100%;
			height:60px;
			background-color:#fff;
			text-align:center;
			margin: auto;
			cursor: pointer;
		}
		.div_shortcut_icon {
			font-size:20px;
			margin-top:10px;
		}
		.row_first {
			height: 200px;
		}
		.row_second {
			height: 160px;
		}
		.row_three, .row_four {
			height: 230px;
		}
		.panel {
			margin-bottom: 15px;
		}
		.panel-primary {
			border-color: #fff;
		}
		.panel-primary>.panel-heading {
			font-weight: bold;
			color: #505050;
    		background-color: #fff;
    		border-color: #fff;
		}
		.row-offset {
			 margin-left: -10px;
		}
		a:link {
			color:#626262;
		}
		a:visited {
			color:#626262;
			text-decoration:none;
		}
		a:hover {
			color:#626262;
			text-decoration:none;
		}
		a:active {
			color:#626262;
			text-decoration:none;
		} 
		.a-more {
			font-size: 11px;
			font-weight: normal;
		}
	</style>
</head>
<body style="height: 100%">
	<div class="right_div">
		<iframe id="mainFrame1" name="mainFrame1"  src="" height="100%" scrolling="yes" frameborder="no" width="100%"></iframe>
	</div>
<script type="text/javascript">
$('#myTab a').click(function (e) {
  e.preventDefault();
  $(this).tab('show');
});

$('#loading-example-btn').click(function () {
     var btn = $(this);
     btn.button('loading');
     // $.ajax(...).always(function () {
     //   btn.button('reset');
     // });
});
function twoDigits(val) {  
	if (val < 10) return "0" + val; return val;  
}  
$(document).ready(function() {
	
});
</script>
</body>
</html>