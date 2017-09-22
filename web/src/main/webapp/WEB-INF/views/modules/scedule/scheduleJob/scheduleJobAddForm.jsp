<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<!DOCTYPE html>
<head>
	<title>scedule管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/scedule/scheduleJob/save",
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/scedule/scheduleJob/list">scedule列表</a></li>
		<li class="active"><a href="${ctx}/scedule/scheduleJob/form?id=${scheduleJob.id}">scedule添加</a></li>
	</ul><br/>
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<input type="hidden" name="isNewRecord" value="true"/>
		<div class="control-group">
			<label class="control-label">任务名称：</label>
			<div class="controls">
				<input name="jobname" value="${scheduleJob.jobname}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务分组：</label>
			<div class="controls">
				<input name="jobgroup" value="${scheduleJob.jobgroup}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务状态：</label>
			<div class="controls">
				<input name="jobstatus" value="${scheduleJob.jobstatus}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">时间表达式：</label>
			<div class="controls">
				<input name="cronexpression" value="${scheduleJob.cronexpression}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<input name="description" value="${scheduleJob.description}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务类：</label>
			<div class="controls">
				<input name="beanclass" value="${scheduleJob.beanclass}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前任务状态：</label>
			<div class="controls">
				<input name="isconcurrent" value="${scheduleJob.isconcurrent}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SpringId：</label>
			<div class="controls">
				<input name="springid" value="${scheduleJob.springid}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务调用的方法名：</label>
			<div class="controls">
				<input name="methodname" value="${scheduleJob.methodname}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务类型：</label>
			<div class="controls">
				<input name="jobtype" value="${scheduleJob.jobtype}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分钟：</label>
			<div class="controls">
				<input name="fenz" value="${scheduleJob.fenz}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小时：</label>
			<div class="controls">
				<input name="xiaos" value="${scheduleJob.xiaos}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">tians：</label>
			<div class="controls">
				<input name="tians" value="${scheduleJob.tians}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周数：</label>
			<div class="controls">
				<input name="zhous" value="${scheduleJob.zhous}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月数：</label>
			<div class="controls">
				<input name="yues" value="${scheduleJob.yues}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">时间表达式解释：</label>
			<div class="controls">
				<input name="crondesc" value="${scheduleJob.crondesc}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<table cellspacing="0" cellpadding="10" border="0">
			<tr><td><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/></td>
			<td><input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/></td></tr>
			</table>
		</div>
	</form>
</body>
</html>