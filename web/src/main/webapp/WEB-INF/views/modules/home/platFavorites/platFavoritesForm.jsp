<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/platfavorites/platFavorites/save",
				        dataType : "json",
				        timeout : 180000,
				        beforeSubmit : function() {
				        	//alert('bbb')
				        },
				        success : function(result, status) {
				        	if(result.success == true) {
				        		$.jBox.info(result.msg, '提示', {closed:function(){
				        			$.jBox.close(true);
				        		}});
			 	            } else {
			 	               alertx(result.msg);
			 	            }
				        },
				        error : function() {
				        	alertx('出现错误，稍后再试！');
				        }
			        });
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
    <style>
	label {
		display: inline-block;
	}
	</style>
	<form id="inputForm"  method="post" class="form-horizontal" style="margin-top:20px;">
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" value="${platFavorites.id} "/>
		<div class="control-group">
			<label class="control-label">收藏夹名称：</label>
			<div class="controls" style="text-align: left;">
				<input name="favName" data-trigger="hover" value="${platFavorites.favName}"  maxlength="64" class="span4 required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<textarea name="remarks" rows="4" maxlength="255" class="input-xxlarge ">${platFavorites.remarks}</textarea>
			</div>
		</div>
		<div class="form-actions" style="padding-left:20px;text-align:center;">
			<input id="btnSubmit" class="btn btn-primary" type="submit" style="width:150px;" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" style="width:150px;" value="返 回" onclick="$.jBox.close(true)"/>
		</div>
	</form>