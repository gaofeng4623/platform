<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnAdd").on('click', function() {
				var template = $('#tr_template tbody').html();
				var locationType = $("#inputForm select[name='locationType']");
			    var num = $("#inputForm input[name='num']");
			    if ('' == num.val() || null == num.val()) {
			    	alertx('请填写数量，再添加新位置类型！');
			    	return;
			    }
			    //alertx(locationType.find("option:selected").text());
			    $('#newLocationTable #location_tr').before(template);
			    var $field = $('#newLocationTable .del');
			    var $tr =  $field.parents('.tr');
			    //$tr.find("input[name='locationType']").val(locationType.val());
			})
			//删除位置类型和数量
			$('#newLocationTable').on('click', '.del', function() {
			    var $field = $(this);
			    var $tr =  $field.parents('.tr');
			    //删除
			    $tr.remove();
	    	});
			
			$("#inputForm").validate({
				rules: {
					num: {number:true}
				},
				messages: {
					num: {}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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
			
			$("#btnSubmit_save").bind('click',function(){
				if('0' == '${location.id}'){
	        		alertx("批量添加当前父节点不能为根节点！");
	        		return ;
	        	}
				var locationTypes=$("#inputForm select[name='locationType']").fieldValue();
			    var nums=$("#inputForm input[name='num']").fieldValue();
			    //获取下拉框文本
			    var typeNames = $("#inputForm select[name='locationType']").find("option:selected").text();
			    var names = typeNames.split('');
			    var outNames = "";
			    for(var i=0;i<locationTypes.length;i++){
			    	if(('' == locationTypes[i]) || ('' == nums[i])){
			    		alertx('请完成必填项后再点击保存！');
				    	return;
			    	}else{
			    		outNames += nums[i]+names[i]+','
			    	}
			    }
			    var confirmText = '<span style="font-size: 16px;font-weight: bold;">是否确定保存当前的位置结构：<font color="red">'+outNames.substr(0,outNames.length-1)+'</font></span>';
			    confirmx(confirmText,function(){
	    			if ($("#jbox-content #inputForm").valid()) {
		    			Share.Ajax({
							url: '${ctx}/base/location/saveBatchForm',
							params : $('#inputForm').serialize(),
							callback: function() {
								$.jBox.close();
								reloadGrid();
							}
						})
		    		} else {
		    			alertx('出现异常，稍后再试！');
		    			return ;
		    		}
			    });
			})
			
			$("#btnCancel_close").bind('click',function(){
				$.jBox.close();
			})
			
		});
		
		
		
	</script>

	<form id="inputForm"><!-- 父节点id -->
		<input type="hidden" id="id" name="id" value="${location.id}"/>
		<div class="control-group">
			<span style="font-size: 14px;font-weight: bold;padding-left: 50px">当前父节点为：${location.locationName}</span>
		</div>
		<table id="newLocationTable" style="padding-left:10px;padding-top:10px;width: 100%" >
			<tr class="tr">
				<td align="right">位置类型：</td>
				<td>	
					<select name="locationType" class="input-small ">
						<c:forEach items="${fns:getDictList('location_type')}" var="location_type">
							<option value="${location_type.value}">${location_type.label}</option>
						</c:forEach>
					</select>
				</td> 
				<td align="right">添加数量：</td>
				<td width="35%">
					<input type="text" name="num" maxlength="3" value="" style="width:50px" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td>
	  				<input id="btnAdd" class="btn btn-primary add" type="button" value="添加" />
	  			</td>
			</tr>
			<tr id="location_tr">
  			</tr>
  			
		</table>
		<div class="form-actions" align="right">
			<input id="btnSubmit_save" class="btn btn-primary " type="button" value="提交" />
			<input id="btnCancel_close" class="btn btn-primary " type="button" value="取消" />
		</div>
   </form>
       
	<table id="tr_template" style="display:none;" >
		<tr class="tr">
			<td align="right">位置类型：</td>
			<td>
				<select name="locationType" class="input-small" >
					<c:forEach items="${fns:getDictList('location_type')}" var="location_type">
						<option value="${location_type.value}">${location_type.label}</option>
					</c:forEach>
				</select>
			</td>
			<td align="right">添加数量：</td>
			<td width="35%">
				<input type="text" name="num" maxlength="3" value="" style="width:50px" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</td>
			<td>
				<input id="btnDel" class="btn btn-primary del" type="button" value="删除" />
			</td>
		</tr>
	<table>
	
	
   <sys:message content="${message}"/>    
