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
				url:"${ctx}/platPhoto/getRelatedImages",
				data:{
					cameraId:'${cameraId}',
					dateStr:'${dateStr}'
				},
			    type: "post",
		        dataType: "json",
			    success:function(resultData){
			    	if(null != resultData){
				    	$("#imgList").empty();
			    		var html = "";
				    	for(var i=0; i<resultData.length; i++){
				    		var item = resultData[i];
				    		html += '<div class="imgDiv" onclick="imgDivClick(\'' + item.rtspUrl + '\')"><img src="' + item.imageUrl + '"></div>';
				    	}
				    	$("#imgList").html(html);
			    	}
			    }
			});
		});
	 	function imgDivClick(videoUrl){
  			layer.open({
 				id: 'photoVideos',
				type: 2, 
				area: [pageSize().WinW - 400+'px', pageSize().WinH - 100 + 'px'],
				title: "馆内实况",
				closeBtn: 1,
				shadeClose: false,
				scrollbar: false,
				shade: [0.5 , '#000' , true],
				content: "${ctx}/platPhoto/toPhotoVideosPage?videoUrl="+videoUrl,
				yes: function(index){
					//closeVideos();
					layer.close(index); //如果设定了yes回调，需进行手工关闭
	 		  	}
 		  	});
 		}
		function closeVideos(){
			var id_vlc = document.getElementById('photoVideos').getElementsByTagName("id_vlc")[0].id;
			$('#'+id_vlc)[0].contentWindow.stopPlay();
		}
		function toPhotoListPage(){
			window.location.href = "${ctx}/platPhoto/toPhotoListPage";
		}
	</script>
	<style type="text/css">
		html,body{
			height: 100%;
		}
		.imgDiv {
			margin: 10px;
			width: 30%;
			height: 240px;
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
	<div align="right" style="padding: 15px;">
		<input id="btnCancel" class="btn" type="button" value="返回" onclick="toPhotoListPage()"/>
	</div>
	<div id="imgList">
		暂无数据
	</div>
</body>
</html>