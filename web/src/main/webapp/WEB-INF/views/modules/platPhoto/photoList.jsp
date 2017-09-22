<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>馆内一览</title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="${ctx}/static/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			$.ajax({
				url:"${ctx}/platPhoto/getCameraImages",
			    type: "post",
		        dataType: "json",
			    success:function(resultData){
			    	if(null != resultData){
				    	$("#imgList").empty();
			    		var html = "";
				    	for(var i=0; i<resultData.length; i++){
				    		var item = resultData[i];
				    		html += '<div class="imgDiv" onclick="toPhotoList2Page(\'' + item.cameraId + '\',\'' +item.createTime + '\')"><img src="' + item.imageUrl + '"></div>';
				    	}
				    	$("#imgList").html(html);
			    	}
			    }
			});
		});
		function toPhotoList2Page(cameraId,dateStr){
			window.location.href = "${ctx}/platPhoto/toPhotoList2Page?cameraId="+cameraId+"&dateStr="+dateStr;
		}
	</script>
	<style type="text/css">
		html,body{
			height: 100%;
		}
		.imgDiv {
			margin: 10px;
			width: 45%;
			height: 320px;
			transition: all .2s ease;
			cursor: pointer;
			float:left;
		}
		.imgDiv:hover{
			-webkit-transform: scale(1.02);
		   	-moz-transform: scale(1.02);
			transform: scale(1.02);
			-webkit-box-shadow: 0 -1px 10px rgba(0,0,0,.5);
		   	-moz-box-shadow: 0 -1px 10px rgba(0,0,0,.5);
			box-shadow: 0 -1px 10px rgba(0,0,0,.5);
		}
		img {
			max-height:100%;
			width:100%;
		}
	</style>
</head>
<body>
	<div id="imgList">
		暂无数据
	</div>
</body>
</html>