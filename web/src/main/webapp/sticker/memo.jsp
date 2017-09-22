<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>便签</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctx}/WEB-INF/views/modules/sticker/css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/WEB-INF/views/modules/sticker/demo-css/highlight-railscasts.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/WEB-INF/views/modules/sticker/css/color-sticker.css">
	<style type="text/css">
		body{
			margin: 0;
			padding: 0;
			font-family: 'Microsoft Yahei';
		}
		.demo-head{
			width: 100%;
			height: 240px;
			background-color: pink;
			color: #333;
			padding-top: 120px;
			
			background-image: url(img/bg.jpg);
		}
		.demo-head .title{
			font-size: 40px;
			margin-left: 100px;
			font-weight: 700;
		}
		.demo-head .sub-title{
			font-size: 24px;
    		line-height: 76px;
    		margin-left: 100px;
		}
		.demo-container{
			width: 900px;
			min-height: 1000px;
			margin: 40px auto;
			
			padding:5px 10px;
			
			background-color: #fff;
		}
		
		.demo-container h1{
			text-align: center;
			color: #a25b85;
		}
		ul{
			color: #a25b85;
    		line-height: 27px;
		}
		.demo-head .download{
			display: block;
			width: 120px;
			height: 46px;
			background-color: #0cd3ec;
			color: #fff;
			text-decoration: none;
			font-size: 22px;
			text-align: center;
			border-radius: 5px;
			line-height: 46px;
			margin-left: 100px;
			-moz-box-shadow: 3px 3px 5px rgba(33,33,33,0.7);
			-webkit-box-shadow: 3px 3px 5px rgba(33,33,33,0.7);
			box-shadow: 3px 3px 5px rgba(33,33,33,0.7);
		}
		.demo-head .download:hover{
			background: #0badc1;
		}
	</style>
	
</head>
<body>
	<script type="text/javascript" src="${ctx}/WEB-INF/views/modules/sticker/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/WEB-INF/views/modules/sticker/js/colorsticker.js"></script>
	<script type="text/javascript" src="${ctx}/WEB-INF/views/modules/sticker/demo-js/highlight.pack.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
		    type: "post",
		    url: "${ctx}/home/memo/allList",
		    dataType: "json",
		    success: function (result) {
		    	var stickers = [];
		    	for (var i = 0;i <result.length;i++) {
		    		var str = "{stickerId:'"+result[i].stickerId+"',left:'"+result[i].left+"', top:'"++"', content:'"+result[i].content+"'}"
		    		stickers.push(str);
		    	}
				$('body').sticker({
					saveStickerCallback: function(sticker){
						$.ajax({
						    type: "post",
						    url: "${ctx}/home/memo/save?left="+sticker.left+"&top="+sticker.top+"&content"+sticker.content,
						    dataType: "json",
						    success: function (result) {
						    		alert("成功保存");
						    	}
						})
					},
					closeStickerCallback： function(stickerId){  //删除便签的回调方法，参数是便签的stickerId
						$.ajax({
						    type: "post",
						    url: "${ctx}/home/memo/delete?stickerId="+stickerId,
						    dataType: "json",
						    success: function (result) {
						    		alert("删除成功");
						    	}
						})
					}
				},stickers);
		    	})
		    }
	})
	</script>
</body>
</html>