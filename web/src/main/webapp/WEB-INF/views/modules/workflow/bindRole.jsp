<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>流程列表</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		var settingTree = {
			async:{  
		        enable:true,  
		        type:"post",  
		        url:"${ctx}/workflow/findGrupRadioTree",
		        otherParam: {
		        	//assignee: $scope.assignment.assignee
		        }
		    },  
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {  
				/* onCheck: function (event, treeId, treeNode) {  
					if (treeNode.checked) {
						$scope.$apply(function () {  
							ngModel.$setViewValue(treeNode.id);  
						}); 
						alert(treeNode.name);
					} else {
						//alert('请选择一个用户！');
						$scope.$apply(function () {  
							ngModel.$setViewValue('');  
						}); 
					}
                 }   */
             }  
		};
		var settingForm = {
				async:{  
			        enable:true,  
			        type:"post",  
			        url:"${ctx}/workflow/formDesign/formTree?id=${form.id}",
			        otherParam: {
			        	//assignee: $scope.assignment.assignee
			        }
			    },  
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {  
					/* onCheck: function (event, treeId, treeNode) {  
						if (treeNode.checked) {
							$scope.$apply(function () {  
								ngModel.$setViewValue(treeNode.id);  
							}); 
							alert(treeNode.name);
						} else {
							//alert('请选择一个用户！');
							$scope.$apply(function () {  
								ngModel.$setViewValue('');  
							}); 
						}
	                 }   */
	             }  
			};
		var fields = ${form.fields};
		
		var formView = {
			text: function(field) {
				
				return '<label class="control-label">'+field.label+':</label>'+
				'<input type="text" name="name" minlength="'+field.field_options.minlength+'" maxlength="'+field.field_options.maxlength+'" class="'+formView.required(field)+'" />'+
				'<span class="help-inline">'+formView.requiredFlag(field)+'填写名称 </span>';
			},
			paragraph: function() {
				
			},
			checkboxes: function() {
				
			},
			date: function() {
				
			},
			dropdown: function() {
				
			},
			email: function() {
				
			},
			number: function() {
				
			},
			radio: function() {
				
			},
			time: function() {
				
			},
			required: function(field) {
				var clas = '';
				if (field.field_options.required == true) {
					clas = 'required';
				}
				return clas;
			},
			requiredFlag(field) {
				var p = '';
				if (field.field_options.required == true) {
					p = '<font color="red">*</font>';
				}
				return p;
			}
		}
		$(function(){
	    	$.fn.zTree.init($("#treeRole"), settingTree);  
	    	$.fn.zTree.init($("#treeForm"), settingForm);
	    	for (var i = 0; i < fields.length; i++) {
	    		var field = fields[i];
	    		if ('text' == field.field_type) {
	    			formView.text(field);
	    		}
	    		
	    		
	    	}
	    	
	    	
		})
	</script>
</head>
<body>

<ul class="nav nav-tabs">
	<li><a href="${ctx}/workflow/formDesign/formList">表单列表</a></li>
	<li class="active"><a href="${ctx}${ctx}/workflow/formDesign/bindRole">绑定角色</a></li>
</ul>
<div class="container-fluid">
  <div class="row">
    <div class="span3" style="height: 500px; overflow: auto;">
    	 <h4 >角色</h4>
	     <ul id="treeRole" class="ztree" ></ul>
    </div>
    <div class="span2">
    	 <h4 >表单字段</h4>
    	<ul id="treeForm" class="ztree" ></ul>
    </div>
	<div class="span10">
		<h4 >表单预览</h4>
		<form id="inputForm" class="form-inline">
			<div class="control-group">
				<div class="controls">
					<label class="control-label">名称:</label>
					<input type="text" name="name" maxlength="50" class="required" value="${form.name }"/>
					<span class="help-inline"><font color="red">*</font>填写名称 </span>
					<label class="control-label">说明:</label>
					<input type="text" name="remark"  value="${form.remark }"/>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="control-label">名称:</label>
					<input type="text" name="name" maxlength="50" class="required" value="${form.name }"/>
					<span class="help-inline"><font color="red">*</font>填写名称 </span>
					<label class="control-label">说明:</label>
					<input type="text" name="remark"  value="${form.remark }"/>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="control-label">名称:</label>
					<input type="text" name="name" maxlength="50" class="required" value="${form.name }"/>
					<span class="help-inline"><font color="red">*</font>填写名称 </span>
					<label class="control-label">说明:</label>
					<input type="text" name="remark"  value="${form.remark }"/>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="control-label">名称:</label>
					<input type="text" name="name" maxlength="50" class="required" value="${form.name }"/>
					<span class="help-inline"><font color="red">*</font>填写名称 </span>
					<label class="control-label">说明:</label>
					<input type="text" name="remark"  value="${form.remark }"/>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="control-label">名称:</label>
					<input type="text" name="name" maxlength="50" class="required" value="${form.name }"/>
					<span class="help-inline"><font color="red">*</font>填写名称 </span>
					<label class="control-label">说明:</label>
					<input type="text" name="remark"  value="${form.remark }"/>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="control-label">名称:</label>
					<input type="text" name="name" maxlength="50" class="required" value="${form.name }"/>
					<span class="help-inline"><font color="red">*</font>填写名称 </span>
					<label class="control-label">说明:</label>
					<input type="text" name="remark"  value="${form.remark }"/>
				</div>
			</div>
		</form>

    </div>
  </div>
</div>
</body>
</html>