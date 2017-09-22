<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
$(function(){
	setTimeout(function(){
		var f = arguments.callee;
		$.ajax({
			url:"${ctx}/platAlarm/findAlarmTopN",
			data:{
				topN: 5
			},
		    type: "post",
	        dataType: "json",
		    success:function(resultData){
		    	$("#alarm").empty();
	    		var html = "", fontColor = "", imgSrc = "";
	    		var divCss = "float:left;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;";
		    	for(var i=0; i<resultData.length; i++){
		    		var item = resultData[i];
		    		var href = "${ctx}/platAlarm/toAlarmInfoPage?alarmId="+ item.alarmId;
		    		if(item.grades == "严重"){
		    			fontColor = "#FF0000";
		    			imgSrc = "${ctx}/static/plat/alarm1.png";
		    		}else if(item.grades == "一般"){
		    			fontColor = "#F47C30";
		    			imgSrc = "${ctx}/static/plat/alarm2.png";
		    		}else{
		    			fontColor = "#2892BD";
		    			imgSrc = "${ctx}/static/plat/alarm3.png";
		    		}
		    		html += '<div class="div_class_1">';
		    		html += '<div class="home_text" style="width:100%;">';
		    		html += '<div style="width:46%;'+divCss+'"><img src="'+imgSrc+'" style="margin-bottom:3px;"/>&nbsp;&nbsp;'+item.alarmDeviceName + item.alarmTypeName+'</div>';
		    		html += '<div style="width:12%;text-align:center;color:'+fontColor+';'+divCss+'">'+item.grades+'</div>';
		    		html += '<div style="width:28%;text-align:center;'+divCss+'">'+item.createTime+'</div>';
		    		html += '<div style="width:14%;text-align:center;'+divCss+'"><a href="'+href+'" style="color:#228B22;">应急预案</a></div>';
		    		html += '</div></div>';
		    	}
		    	$("#alarm").html(html);
		    }
		});
		setTimeout(f,100000);
	},0);
});
</script>