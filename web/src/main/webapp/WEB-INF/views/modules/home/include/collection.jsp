<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	function Change15s(no) {
		if (!no)
			no = 1;
		for (var i = 1; i <= 2; i++) {
			document.getElementById("indexsrh" + i).style.display = (i == no ? "block": "none");
		}
		no++;
		setTimeout("Change15s(" + (no == 3 ? 1 : no) + ");", 5000);
		
	}
	window.onload = function() {
		Change15s();
	}
</script>

	<c:forEach items="${home:findCollect()}" var="item"
		varStatus="idxStatus">
		<div width="100%" id="indexsrh${idxStatus.index+1}" style="display: none">
			<div style="float: left;  width: 40%;">
				<img alt="" src="${ctx}/static/images/${item.fileName}" onerror="this.src='${ctx}/static/images/test.jpg'" width="150px" >
			</div>
			<div style="float: left; width: 5%">&nbsp;</div>
			<a href="${ctx}/platcollection/platCollection/editForm?id=${item.id}">
				<div style="float: left" width="50%">
				<table border="0" cellspacing="0" cellpadding="0" width="100%" style="align:center;">
					<tr><th>主题词：</th><td>${item.subject}</td></tr>
					<tr><th>全宗名称：</th><td>${item.genName}</td></tr>
					<tr><th>题名：</th><td>${item.booksTitle}</td></tr>
					<tr><th>来源：</th><td>${item.docNum}</td></tr>
					<tr><th>采集时间：</th><td><fmt:formatDate value="${item.updateDate}" pattern="yyyy年MM月dd日" /></td></tr>
					<tr><th>文件名称：</th><td>${item.fileName}</td></tr>
				</table>
				</div>
			</a>
		</div>
	</c:forEach>
</div>