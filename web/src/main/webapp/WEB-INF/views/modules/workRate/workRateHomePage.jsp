<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
$(function(){
	$.ajax({
		url:"${ctx}/workRate/findWorkRateTopN",
		data:{
			topN: 5
		},
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	$("#workRate").empty();
	    	var html="";
	    	for(var i=0; i<resultData.length; i++){
	    		var item = resultData[i];
	    		var workType = item.workType;
	    		workType = encodeURIComponent(workType);
	    		var workTypeName = item.workTypeName;
	    		workTypeName = encodeURIComponent(workTypeName);
	    		var workUnit = item.workUnit;
	    		workUnit = encodeURIComponent(workUnit);
	    		var href = "${ctx}/workRate/infoForm?workType="+workType+"&workTypeName="+workTypeName+"&workNum="+item.workNum+"&workUnit="+workUnit;
				if(null != item.date){
					href += "&date="+item.date;
				}
	    		html += '<div class="div_class_1">';
	    		html += '<div class="home_text"><font class="home_text_point">●</font>&nbsp;';
	    		html += '<a href="'+href+'">'+item.workTypeName+':处理档案【'+item.workNum+item.workUnit+'】</a>';
	    		html += '</div><div class="home_text_time">'+item.date+'</div>';
	    		html += '</div>';
	    	}
	    	$("#workRate").html(html);
	    }
    });
});
</script>