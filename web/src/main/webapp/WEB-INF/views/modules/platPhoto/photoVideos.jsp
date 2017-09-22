<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title></title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		var vlc = document.getElementById("id_vlc");
		//屏蔽右键
		document.oncontextmenu=function(e){return false;};
		// 停止播放
		function stopPlay(){
		 	vlc.playlist.stop();
		}
	</script>
	<style type="text/css">
		html,body{
			height: 100%;
		}
	</style>
</head>
<body>
   <object width="96%" height="96%" type='application/x-vlc-plugin' id='id_vlc' events='True' 
       classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' codebase='axvlc.cab'> 
          <param name='mrl' value='rtsp://admin:admin@192.168.13.213:554/cam/realmonitor?channel=1&subtype=0' /> 
          <param name='autoplay' value='true' /> 
          <param name='loop' value='false' /> 
          <param name='fullscreen' value='true' /> 
          <param name='toolbar' value='false' />
          <param name='bgcolor' value='#555' /> 
          <embed type='application/x-vlc-plugin' pluginspage='http://www.videolan.org'     
                width="96%" height="96%" src='rtsp://admin:admin@192.168.13.213:554/cam/realmonitor?channel=1&subtype=0' id='vlc' toolbar='false'>   
          </embed>
    </object>
</body>
</html>