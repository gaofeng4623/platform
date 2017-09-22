<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>便签</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctx}/sticker/css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/sticker/demo-css/highlight-railscasts.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/sticker/css/color-sticker.css">
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
	<script type="text/javascript" src="${ctx}/sticker/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/sticker/js/colorsticker.js"></script>
	<script type="text/javascript" src="${ctx}/sticker/demo-js/highlight.pack.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
	<script type="text/javascript">
	var counter = 0;
	var line = 0;
	$(document).ready(function(){
		
		$.ajax({
		    type: "post",
		    url: "${ctx}/home/memo/allList",
		    dataType: "json",
		    success: function (result) {
		    	var stickers = [];
		    	if (result.success) {
		    		for (var i = 0;i <result.msg.length;i++) {
		    			counter++;
			    		var str = {stickerId:result.msg[i].id,left:result.msg[i].mleft, top:result.msg[i].mtop, content:result.msg[i].content};
			    		stickers.push(str);
			    	};
		    	}
		    	$('body').sticker({
// 		    		color:'pink', //便签默认是黄色，可以选择pink,green,blue,purple
		    		width:'200px',  //便签的宽度
		    		height:'240px', //便签的高度
		    		saveStickerCallback: function(sticker){   //保存便签的回调方法，参数是sticker对象，包括便签的位置和内容信息
		    			var url = encodeURI(encodeURI("${ctx}/home/memo/save?stickerid="+sticker.stickerId+"&left="+sticker.left+"&top="+sticker.top+"&content="+sticker.content)); 
		    			$.ajax({
						    type: "post",
						    url: url,
						    dataType: "json",
						    success: function (result) {
						    	counter++;
						    		alert("保存成功");
						    	}
						});
		    		},
		    		closeStickerCallback: function(stickerId){  //删除便签的回调方法，参数是便签的stickerId
		    			$.ajax({
						    type: "post",
						    url: "${ctx}/home/memo/delete?stickerid="+stickerId,
						    dataType: "json",
						    success: function (result) {
						    		alert("删除成功");
						    	}
						});
		    		}
		    	},stickers);//将导入的便签数组作为插件的第二个参数
		    }
		})
	})
	</script>
</body>
</html>