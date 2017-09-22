<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var b =true;
					var a=$("#jobType").val();
					if(a=='0'){
						$.jBox.info('请选择任务类型', '提示');
						b=false;
						return;
					}
					if(a=='1'){
						if($("#fenz").val()==0){
							$.jBox.info('请选择分钟数据', '提示');
							b=false;
							return;
						}
					}else if(a=='2'){
						if($("#fenz").val()==0){
							$.jBox.info('请选择分钟数据', '提示');
							b=false;
							return;
						}
						if($("#xiaos").val()==0){
							$.jBox.info('请选择小时数据', '提示');
							b=false;
							return;
						}
					}if(a=='3'){
						if($("#fenz").val()==0){
							$.jBox.info('请选择分钟数据', '提示');
							b=false;
							return;
						}
						if($("#xiaos").val()==0){
							$.jBox.info('请选择小时数据', '提示');
							b=false;
							return;
						}
						if($("#tians").val()==0){
							$.jBox.info('请选择哪天', '提示');
							b=false;
							return;
						}
					}
					if(!existJobName($("#jobName").val())){
						$.jBox.info('任务名称重复！', '提示');
						b=false;
						return;
					}
					//loading('正在提交，请稍等...');
					if(b){
						$(form).ajaxSubmit({
				        	type : "post",
					        url : "${ctx}/sys/scheduleJob/save2",
					        dataType : "json",
					        timeout : 180000,
					        beforeSubmit : function() {
					        	//alert('bbb')
					        },
					        success : function(result, status) {
					        	if(result.success == true) {
					        		alertx(result.msg);
				 	            } else {
				 	               alertx(result.msg);
				 	            }
					        },
					        error : function() {
					        	alertx('出现错误，稍后再试！');
					        }
				        });
					}
					
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
		<c:if test="${scheduleJob.jobType=='1'}">
			$(".fz").show();
		</c:if>
		<c:if test="${scheduleJob.jobType=='2'}">
			$(".fz").show();
			$(".xs").show();
		</c:if>
		<c:if test="${scheduleJob.jobType=='3'}">
			$(".fz").show();
			$(".xs").show();
			$(".ts").show();
		</c:if>
		function xssr(){
			$(".fz").hide();
			$(".xs").hide();
			$(".ts").hide();
			$(".zs").hide();
		//	$(".ys").hide();
			var a=$("#jobType").val();
			if(a=='1'){
				$(".fz").show();
			}else if(a=='2'){
				$(".fz").show();
				$(".xs").show();
			}if(a=='3'){
				$(".fz").show();
				$(".xs").show();
				$(".ts").show();
			}/* if(a=='4'){
				$(".fz").show();
				$(".xs").show();
			//	$(".ts").show();
				$(".zs").show();
			} *//* if(a=='5'){
				$(".fz").show();
				$(".xs").show();
				$(".ts").show();
			//	$(".zs").show();
			//	$(".ys").show();
			} */
		}
		
		function existJobName(name){
			var b =true;
			$.ajax({
				 type: "POST",
				 url:'${ctx}/sys/scheduleJob/existJobName',
				 dataType: "json",
				 async:false,
				 data:{
					name:name,
					id:$("#id").val()
				 },
				 success:  function(result, status) {
					if(result.success == true) {
						
					} else {
					  b=false;
					}
				},
				error : function() {
					b=false;
				}
			});
			return b;
		}
	</script>

	<style>
		.control-group{float:left;height:30px !important;width:50%;}
		.form-actions{float:left;width:100%;}
		.controls{margin-left:10px !important;}
		.control-label{width:80px !important;}
		.changwenben{width:92%;}
		.ztree{position: relative;border: 1px solid #D8D4D4;background-color: #FFFFFF;height: 200px;overflow-y: scroll;}
	</style>

	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" id="id" value="${scheduleJob.id} "/>
		<div class="control-group">
			<label class="control-label">任务名称：</label>
			<div class="controls">
				<input name="jobName" id="jobName" value="${scheduleJob.jobName}"  maxlength="60" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务分组：</label>
			<div class="controls">
				<input name="jobGroup" value="${scheduleJob.jobGroup}"  maxlength="60" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="">
			<label class="control-label">任务类型：</label>
			<div class="controls">
				<select name="jobType" onchange="xssr()" id="jobType">
					<option value="0">请选择</option>
					<option value="1" <c:if test="${scheduleJob.jobType=='1'}"> selected=selected</c:if>>每x分钟执行一次</option>
					<option value="2" <c:if test="${scheduleJob.jobType=='2'}"> selected=selected</c:if>>每天x时x分执行一次</option>
					<option value="3" <c:if test="${scheduleJob.jobType=='3'}"> selected=selected</c:if>>每月x号x时x分执行</option>
					<%-- <option value="4" <c:if test="${scheduleJob.jobType=='4'}"> selected=selected</c:if>>每周</option>
					<option value="5" <c:if test="${scheduleJob.jobType=='5'}"> selected=selected</c:if>>每月</option> --%>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div  class="control-group" style="">
			<div class="controls">
				
			<%-- 	<label class="control-label ys" style="width:20px !important;float:left;display:none;">月：</label>
				<select name="yues" style="width:80px;float:left;display:none;" class="required ys">
					<c:forEach begin="1" end="12" var="item">
						<option value="${item}" <c:if test="${scheduleJob.yues==item}"> selected=selected</c:if>>${item}</option>
					</c:forEach>
				</select> --%>
				<label class="control-label zs" style="width:20px !important;float:left;display:none;">周：</label>
				<select name="zhous" style="width:80px;float:left;display:none;" class="required zs">
					<option value="0">请选择</option>
					<option value="1" <c:if test="${scheduleJob.zhous=='1'}"> selected=selected</c:if>>周一</option>
					<option value="2" <c:if test="${scheduleJob.zhous=='2'}"> selected=selected</c:if>>周二</option>
					<option value="3" <c:if test="${scheduleJob.zhous=='3'}"> selected=selected</c:if>>周三</option>
					<option value="4" <c:if test="${scheduleJob.zhous=='4'}"> selected=selected</c:if>>周四</option>
					<option value="5" <c:if test="${scheduleJob.zhous=='5'}"> selected=selected</c:if>>周五</option>
					<option value="6" <c:if test="${scheduleJob.zhous=='5'}"> selected=selected</c:if>>周六</option>
					<option value="7" <c:if test="${scheduleJob.zhous=='5'}"> selected=selected</c:if>>周日</option>
				</select>
				
				<label class="control-label ts" style="width:20px !important;float:left;display:none;">天：</label>
				<select name="tians" id="tians" style="width:80px;float:left;display:none;" class="required ts">
					<option value="0">请选择</option>
					<c:forEach begin="1" end="31" var="item">
						<option value="${item}" <c:if test="${scheduleJob.tians==item}"> selected=selected</c:if>>${item}</option>
					</c:forEach>
				</select>
				
				<label class="control-label xs" style="width:40px !important;float:left;display:none;">小时：</label>
				<select name="xiaos" id="xiaos" style="width:80px;float:left;display:none;" class="required xs">
					<option value="0">请选择</option>
					<c:forEach begin="1" end="24" var="item">
						<option value="${item}" <c:if test="${scheduleJob.xiaos==item}"> selected=selected</c:if>>${item}</option>
					</c:forEach>
				</select>
				<label class="control-label fz" style="width:40px !important;float:left;display:none;">分钟：</label>
				<select name="fenz" id="fenz" style="width:80px;float:left;display:none;" class="required fz">
					<option value="0">请选择</option>
					<c:forEach begin="1" end="60" var="item">
						<option value="${item}" <c:if test="${scheduleJob.fenz==item}"> selected=selected</c:if>>${item}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">启用状态：</label>
			<div class="controls">
				<%-- <input name="jobStatus" value="${scheduleJob.jobStatus}"  maxlength="1" class="input-xlarge required "/> --%>
				<select name="jobStatus" id="jobStatus" class="required ">
					<option value="1" <c:if test="${scheduleJob.jobStatus=='1'}"> selected=selected</c:if>>启用</option>
					<option value="0" <c:if test="${scheduleJob.jobStatus=='0'}"> selected=selected</c:if>>未启用</option>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">时间表达式：</label>
			<div class="controls">
				<input name="cronExpression" value="${scheduleJob.cronExpression}"  maxlength="60" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<input name="description" value="${scheduleJob.description}"  maxlength="200" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务类：</label>
			<div class="controls">
				<input name="beanClass" value="${scheduleJob.beanClass}"  maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执行方式：</label>
			<div class="controls">
				<select name="isConcurrent" id="isConcurrent" class="required ">
					<option value="0" <c:if test="${scheduleJob.isConcurrent==0}"> selected=selected</c:if>>执行完才能执行下一次任务</option>
					<option value="1" <c:if test="${scheduleJob.isConcurrent==1}"> selected=selected</c:if>>执行不完，可以执行下一次任务</option>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SpringId：</label>
			<div class="controls">
				<input name="springId" value="${scheduleJob.springId}"  maxlength="60" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务调用的方法名：</label>
			<div class="controls">
				<input name="methodName" value="${scheduleJob.methodName}"  maxlength="30" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="$.jBox.close(true)"/>
		</div>
	</form>