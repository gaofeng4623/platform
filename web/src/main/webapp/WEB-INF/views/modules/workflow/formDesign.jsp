<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>流程列表</title>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<%@include file="/WEB-INF/views/include/bootstrap.jsp" %>
 	<link href="${ctxStatic}/formbuilder/vendor/css/vendor.css" rel="stylesheet" />
  	<link href="${ctxStatic}/formbuilder/dist/formbuilder.css" rel="stylesheet"/>
  	<script src="${ctxStatic}/formbuilder/vendor/js/vendor.js"></script>
  	<script src="${ctxStatic}/formbuilder/dist/formbuilder.js"></script>
	<script type="text/javascript">
	var fb = null;;
	$(function(){
		fb = new Formbuilder({
        	selector: '.fb-main',
        	bootstrapData: ${form.fields}
		});
		fb.on('save', function(fields){
			 $.ajax({
			    type: "post",
   	       		url: '${ctx}/workflow/formDesign/saveForm',
   	       		data: {
   	       			id: '${form.id}',
   	       			fields: fields
   	       		}
   	       	})
   	     })
	});
	function save() {
		//fb.trigger('save');
	 }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/workflow/formDesign/formList">表单列表</a></li>
		<li class="active"><a href="${ctx}${ctx}/workflow/formDesign/formEdit">设计表单</a></li>
	</ul>
	<div class='fb-main'></div>
</body>
</html>