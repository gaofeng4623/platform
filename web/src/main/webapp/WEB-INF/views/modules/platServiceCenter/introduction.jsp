<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
<script type="text/javascript">  
function importCustomInfo() {
	
	 //首先验证文件格式
   var fileName = $('#file').val();
   if (fileName === '') {
	   alertx('请选择文件')
       return false;
   }
   var fileType = (fileName.substring(fileName
           .lastIndexOf(".") + 1, fileName.length))
           .toLowerCase();
   if (fileType !== 'xls' && fileType !== 'xlsx') {
   	alertx('文件格式不正确，excel文件！');
       return false;
   }
   $("#file_form").ajaxSubmit({
       url:'${ctx}/platServiceCenter/platServiceCenterSave',
	   dataType : "json",
       error: function() {  
          alertx('error');  
       },
       success : function(data) {
          alertx(data.msg);
          searchForm();
       }
   });
};

</script>  
</head>  
<body>  
	<form id="file_from" action="${ctx}/platServiceCenter/platServiceCenterSave" method="post" enctype="multipart/form-data">
		<ul class="ul-form">
			<li style="list-style-type:none;">
				上传文件：
				<input  class="projectfile" type="file" id="file" name="file">
    			<input class="btn btn-primary returnBtn" type="submit" onclick='importCustomInfo()' value="提交">
			</li>
			<li style="list-style-type:none;"> &nbsp</li>
			<li style="list-style-type:none;">
				<a href="${ctx}/excel/Visittempdata/Visitdata.xlsx" >下载模板</a>
			</li>
			<li style="list-style-type:none;">
				模板说明:<br>
				1:非热点模块不写模块名称,其他属性必须填写;<br>
				2:访问类型请写(IP或PV),PV表示浏览量,IP表示访问IP量;<br>
				3:访问时间请写(2016/12/12或2016-12-12)格式;
			</li>
		</ul>
	</form>
	<sys:message content="${message}"/>
</body>  
</html>