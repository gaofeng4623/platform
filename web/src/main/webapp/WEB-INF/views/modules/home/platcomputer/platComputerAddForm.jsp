<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>计算机设备管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		jQuery.validator.addMethod("checkIP", function(value, element) {  
		    var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;  
		    return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));  
		}, "IP地址格式错误");  
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				
				rules: {
					ip: {remote: "${ctx}/platcomputer/platComputer/checkIp?oldIp=" + encodeURIComponent('${platComputer.ip}'),
						 checkIP:true,	
						}
				},
				messages: {
					ip: {remote: "IP设备已经存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/platcomputer/platComputer/save",
				        dataType : "json",
				        timeout : 180000,
				        beforeSubmit : function() {
				        	//alert('bbb')
				        },
				        success : function(result, status) {
				        	if(result.success == true) {
				        	   alertx(result.msg);
				        	   window.location.href ="${ctx}/platcomputer/platComputer/list";
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
		function check(){
			var name = $("#name").val();
			var password = $("#password").val();
			var ip =$("#ip").val();
			var type=$("#ostype").val();
			if(type=="1"){
				return;
			}
			$.ajax({
				type: "POST",
				url:'${ctx}/platcomputer/platComputer/checkcon?name='+name+'&password='+password+'&ip='+ip,
				dataType: "json",
				success:  function(result, status) {
					if(result.success == true) {
						$.jBox.tip(result.msg, 'success');
					} else {
						$.jBox.tip(result.msg, 'error');
					}
				},
				error : function() {
					$.jBox.info('出现错误，稍后再试！', '提示');
				}
			});
			
			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/platcomputer/platComputer/list">计算机设备列表</a></li>
		<li class="active"><a href="#">计算机设备添加</a></li>
	</ul><br/>
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<input type="hidden" name="isNewRecord" value="false"/>
		<div class="control-group">
			<label class="control-label">设备IP：</label>
			<div class="controls">
				<input type="text" id="ip" name="ip" value="${platComputer.ip}"  class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名：</label>
			<div class="controls">
				<input type="text" id="name" name="name" value="${platComputer.name}"  class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码：</label>
			<div class="controls">
				<input id="password" name="password" type="password" value="${platComputer.password}"  class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value=""  equalTo="#password"  class="input-xlarge required"/>
			</div>
		</div>
		
	<%-- 	<div class="control-group">
			<label class="control-label">BMC用户名名：</label>
			<div class="controls">
				<input name="bmcname" value="${platComputer.bmcname}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">BMC密码：</label>
			<div class="controls">
				<input name="bmcpw" value="${platComputer.bmcpw}"  class="input-xlarge "/>
			</div>
		</div> --%>
		
		<div class="control-group">
			<label class="control-label">操作系统类型：</label>
			<div class="controls">
		<%-- 		<input name="ostype" value="${platComputer.ostype}"  class="input-xlarge required"/>
				--%>
				
				<select id="ostype" name="ostype" class="required">
					<c:forEach items="${fns:getDictList('ostype')}" var="item">
						<option value="${item.value}" >${item.label}</option>	
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作系统版本：</label>
			<div class="controls">
				<input type="text" name="osversion" value="${platComputer.osversion}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<input type="text" name="descripe" value="${platComputer.descripe}"  class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">0不在线1在线：</label>
			<div class="controls">
				<input name="onlineflag" value="${platComputer.onlineflag}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">0:可连接；1不可连接：</label>
			<div class="controls">
				<input name="conflag" value="${platComputer.conflag}"  class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">createdate：</label>
			<div class="controls">
				<input name="createdate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${platComputer.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">updatedate：</label>
			<div class="controls">
				<input name="updatedate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${platComputer.updatedate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">是否收集性能数据：</label>
			<div class="controls">
				<input name="flag" value="${platComputer.flag}"  class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="form-actions">
			<table cellspacing="0" cellpadding="10" border="0">
			<tr><td><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/></td>
<!-- 			<td><input id="btnTest" class="btn " type="button" value="检测" onclick="check()"/></td>
 -->			<td><input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/></td></tr>
			</table>
		</div>
	</form>
</body>
</html>