<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<head>
	<title>公告信息管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<%@ include file="/WEB-INF/views/include/kindeditor.jsp" %>
	<script type="text/javascript">
		function hiddengk(){
			var a = $("#gkflag").val()
			if(a=='0'){
				$("#keshi").show();
			}else{
				$("#keshi").hide();
			}
		}
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "${ctx}/notice/noticeInfo/save",
				        dataType : "json",
				        timeout : 180000,
				        beforeSubmit : function() {
				        	//alert('bbb')
				        },
				        success : function(result, status) {
				        	if(result.success == true) {
				        	   alertx(result.msg);
				        	   window.location.href ="${ctx}/notice/noticeInfo/editList";
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
			hiddengk();
		});
		
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="content"]', {
				cssPath : '${ctx}/static/kindeditor/plugins/code/prettify.css',
				uploadJson : '${ctx}/static/kindeditor/jsp/upload_json.jsp',
				fileManagerJson : '${ctx}/static/kindeditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterBlur: function () { this.sync(); }
			});
			prettyPrint();
		});
		

	</script>
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/notice/noticeInfo/editList">公告信息列表</a></li>
		<li class="active"><a href="${ctx}/notice/noticeInfo/editForm?id=${noticeInfo.id}">公告信息修改</a></li>
	</ul><br/> 
	<form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<input type="hidden" name="id" value="${noticeInfo.id}"/>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<input type="text" name="title" value="${noticeInfo.title}"  class="input-xxlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公告所属类别：</label>
			<div class="controls">
				<select name="type"  value="${noticeInfo.type}">
					<c:forEach items="${fns:getDictList('notice_type')}" var="item">
						<option value="${item.value}" <c:if test="${noticeInfo.type==item.value}"> selected=selected </c:if>>${item.label}</option>	
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公告内容：</label>
			<div class="controls">
				<textarea  name="content" rows="4" class="input-xxlarge " style="width:700px;height:200px;visibility:hidden;">${noticeInfo.content}</textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公告时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${noticeInfo.date}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<!-- 
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<input name="ext" value="${noticeInfo.ext}"  class="input-xlarge "/>
			</div>
		</div>
		 -->
 		<div class="control-group">
			<label class="control-label">是否公开</label>
			<div class="controls">
				<select name="gkflag" id="gkflag" class="required" onchange="hiddengk()">
						<option value="0" <c:if test="${noticeInfo.gkflag=='0'}"> selected=selected</c:if>>否</option>	
						<option value="1" <c:if test="${noticeInfo.gkflag=='1'}"> selected=selected</c:if>>是</option>
				</select>
			</div>
		</div>
		 
		 <div class="control-group" id="keshi">
			<label class="control-label">科室:</label>
			<div class="controls">
                <sys:treeselect id="office" name="office" checked="true" value="${noticeInfo.office}" labelName="office.name" labelValue="${noticeInfo.officeName}"
					title="科室" url="/sys/office/treeData?type=2" cssClass="required" dataMsgRequired="必填信息" notAllowSelectParent="true" hideBtn="true"/>
				<br>
				
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