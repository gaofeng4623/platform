<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>最新采集管理</title>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<%@ include file="/WEB-INF/views/include/kindeditor.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$(form).ajaxSubmit({
			        	type : "post",
				        url : "",
				        dataType : "json",
				        timeout : 180000,
				        beforeSubmit : function() {
				        	//alert('bbb')
				        },
				        success : function(result, status) {
				        	if(result.success == true) {
				        	   alertx(result.msg);
				        	   window.history.back(-1);
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
		.leftDiv{
			float:left;
			border:1px solid #aaa;
			width:25%;
			height:30px;
			text-align:right;
			line-height:30px;
			font-size:12px;
			padding-right: 15px;
		}
		.rightDiv{
			float:left;
			border:1px solid #aaa;
			border-left:0;
			width:70%;
			height:30px;
			text-align:left;
			line-height:30px;
			font-size:12px;
			padding-left: 15px;
		}
	</style>
</head>
<body>
	<div style="width:100%;text-align: center;margin: auto;">
		<div align="right" style="padding: 15px;">
		<!-- 	<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/> -->
			<input id="btnCancel" class="btn" type="button" value="返回" onclick="javascript:history.go(-1);"/>
		</div>
		<sys:message content="${message}"/>	
		<%-- <input type="hidden" name="id" value="${yDataCollection.id}"/> --%>
<table border="0" cellspacing="0" cellpadding="0" width="100%" style="align:center;">
    <tr bgcolor="#E0EEEE" style="font-weight:bold; height: 40px">
    <th>主题词</th><th>全宗名称</th><th>案卷题名</th><th>来源</th>
    <th>文件件数</th><th>目录号</th><th>案卷号</th><th>全宗号</th><th>保管期限</th>
    <th width="100" colspan="2">查看</th>
    </tr>
    <c:forEach items="${list}" var="item" varStatus="idxStatus">
    	<tr style="height: 35px">
    		<td>${item.subject1}</td>
    		<td>${item.genName1}</td>
    		<td>${item.autograph}</td>
    		<td>${item.docNum1}</td>
    		<td>${item.fileNum1}</td>
    		<td>${item.catalog1}</td>
			<td>${item.filesID1}</td>
    		<td>${item.recordNum1}</td>
    		<td>${item.retainDate1}</td>
    		<td><a href="${ctx}/platcollection/platCollection/save?aid=${item.aid}">文件目录</a></td>
    	</tr>
    </c:forEach>
</table>
	</div>
</body>
</html>