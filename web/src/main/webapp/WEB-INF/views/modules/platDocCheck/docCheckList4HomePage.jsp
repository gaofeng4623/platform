<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
$(function(){
	$.ajax({
		url:"${ctx}/platDocCheck/findDocCheckTopN",
		data:{
			topN: 5
		},
	    type: "post",
        dataType: "json",
	    success:function(resultData){
	    	$("#docCheck").empty();
	    	var html="";
	    	var docCheckList = resultData.docCheckList;
	    	for(var i=0; i<docCheckList.length; i++){
	    		var item = docCheckList[i];
	    		var statusName = "";
        		if(item.status == "0"){
        			statusName = '待审批';
        		}else if(item.status == "1"){
        			statusName = '已同意';
        		}else if(item.status == "2"){
        			statusName = '已拒绝';
        		}else if(item.status == "3"){
        			statusName = '转审批';
        		}else if(item.status == "4"){
        			statusName = '已完成';
        		}else if(item.status == "5"){
        			statusName = '待审批';
        		}
	    		var processDefinitionName = item.processDefinitionName;
	    		processDefinitionName = encodeURIComponent(processDefinitionName);
	    		var taskName = item.taskName;
	    		taskName = encodeURIComponent(taskName);

	    		var docCheckToDoUrl = "";
        		if(item.status == "0" || item.status == "5"){//利用登记待审批
		    		docCheckToDoUrl = resultData.docCheckToDo+item.taskId+"/"+item.formKey+"/"+item.processDefinitionKey+"/"+item.processInstanceId;
        		}else if(item.status == "1" || item.status == "2" || item.status == "3" || item.status == "4"){
		    		docCheckToDoUrl = resultData.docCheckShowHistory+item.processDefinitionKey+"/"+item.processInstanceId;
        		}
        		var href = "${ctx}/platDocCheck/toDocCheckInfoPage?hrefURL="+docCheckToDoUrl;
	    		html += '<div class="div_class_1">';
	    		html += '<div class="home_text">';
	    		html += '<font class="home_text_point">●</font>&nbsp;';
	    		html += '<a href="'+href+'">';
	    		html += '['+statusName+']&nbsp;'+item.processDefinitionName+'('+item.taskName+')';
	    		html += '</a>';
	    		html += '</div>';
	    		html += '<div class="home_text_time">'+item.createTime+'</div>';
	    		html += '</div>';
	    	}
	    	$("#docCheck").html(html);
	    }
    });
});
</script>