<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>文档审核信息</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
 	<script type="text/javascript">  
    function mu(){
    	if($("#file").val()==""){
    		$.jBox.info('文件路径不能等于空！', '提示');
    		return;
    	}
    	$.ajax({
			type: "POST",
			url:'${ctx}/plat/platInformation/platInformationSave?url='+$("#file").val(),
			dataType: "json",
			success:  function(result) {
				if(result.success == true) {
					$.jBox.tip(result.msg, 'success');
					var file = $("#file") ;
					file.after(file.clone().val(""));      
					file.remove();  
				} else {
					$.jBox.tip(result.msg, 'error');
				}
			},
			error : function() {
				$.jBox.info('操作失败！', '提示');
			}
		});
    }
    </script>  
</head>  
<body>  
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/plat/platInformation/list">资讯列表</a></li>
		<li class="active"><a href="${ctx}/plat/platInformation/manual">资讯导入</a></li>
	</ul>
	<form id="searchForm" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li>
				<label>上传文件：</label>
				<input  class="projectfile" type="file" id="file" name="file1">
    			<input class="btn btn-primary returnBtn" type="button" onclick="mu()" value="提交">
			</li>
		</ul>
	</form>
</body>  
</html>