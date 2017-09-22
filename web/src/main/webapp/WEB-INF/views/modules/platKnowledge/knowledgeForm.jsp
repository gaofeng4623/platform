<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<head>
    <title>知识库管理</title>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css"
          type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>

    <link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/common/gtzn.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.css" rel="stylesheet"/>
    <script src="${ctxStatic}/common/gtzn.js" type="text/javascript"></script>
    <%@ include file="/WEB-INF/views/include/kindeditor.jsp" %>
    <%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
    <script type="text/javascript">var ctx = '${ctx}', ctxStatic = '${ctxStatic}';</script>
    <%--<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>--%>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#title").focus();
            $("#inputForm").validate();

            $("#jqGrid").jqGrid({
                url: '${ctx}/knowledge/loadAttachment?id=${file.id}',
                mtype: "GET",
                datatype: "json",
                toolbar: [true, "top"],
                jsonReader: {
                    id: "id",
                    root: "list",
                    page: "page",
                    total: "total",
                    records: "records"//总条数
                },
                altRows: true,
                colModel: [
                    {label: '', name: 'id', key: true, hidden: true},
                    {label: '', name: 'attGroupId', hidden: true},
                    {
                        label: '', name: '', width: 25, align: 'right',
                        formatter: function (value, options, row) {
                            var img = "";
                            if (row.url.toLowerCase().indexOf("pdf") > 0) {
                                img = '<img src="${ctx}/static/images/pdf.png"/>';
                            } else if (row.url.toLowerCase().indexOf("doc") > 0 || row.url.toLowerCase().indexOf("docx") > 0) {
                                img = '<img src="${ctx}/static/images/word.png"/>';
                            } else if (row.url.toLowerCase().indexOf("png") > 0 || row.url.toLowerCase().indexOf("jpeg") > 0 || row.url.toLowerCase().indexOf("jpg") > 0) {
                                img = '<img src="${ctx}/static/images/pic.png"/>';
                            }
                            return img;
                        }
                    },
                    {label: '文件名', name: 'fileName', width: 150},
                    {label: '文件大小', name: 'fileSize', width: 25, align: 'center'},
                    {label: '更新时间', name: 'createDate', width: 80, align: 'center'},
                    {label: '编辑人', name: 'user.name', width: 100, align: 'center'},
                    {
                        label: '操作', name: 'url', width: 100, align: 'center',
                        formatter: function (value, options, row) {
                            var a = '<a href="javascript:void(0)" class="gt_a_click" _id="' + row.id + '" url="' + value + '" onclick="removeFile(this)">删除</a>';
                            if (value) {
                                a += ' | <a href="javascript:void(0)" class="gt_a_click" url="' + value + '" fileName="' + row.fileName + '" onclick="downFile(this)">下载</a>';
                                if (value.toLowerCase().indexOf("pdf") > 0) {
                                    a += ' | <a href="javascript:void(0)" class="gt_a_click" preview="true" url="' + value + '" fileName="' + row.title + '" onclick="downFile(this)">预览</a>';
                                }
                            } else {
                                //                                a += ' | <a href="javascript:void(0)" class="gt_a_click" _id="' + row.id + '" onclick="viewFile(this)">查看</a>';
                            }
                            return a;
                        }
                    }
                ],
                viewrecords: true,
                autowidth: true,
                autoheight: true,
//                    multiselect: true,
                //                height: $(window).height() - 140,
                rowNum: 20,
                pager: "#pager"

            });
        });

        function downFile(_this) {
            var url = $(_this).attr('url');
            var fileName = $(_this).attr('fileName');
            var preview = $(_this).attr('preview');
            if (preview) {
                window.open("${ctx}/knowledge/downFile?url=" + url + "&fileName=" + encodeURIComponent(encodeURIComponent(fileName)) + "&preview=true", '_blank');
            } else {
                window.location.href = "${ctx}/knowledge/downFile?url=" + url + "&fileName=" + encodeURIComponent(encodeURIComponent(fileName)) + "&preview=false";
            }
        }
        function removeFile(_this) {
            var id = $(_this).attr('_id');
            var url = $(_this).attr('url');
            var submit = function (v, h, f) {
                if (v == true) {
                    $.ajax({
                        url: '${ctx}/knowledge/deleteAttachment',
                        type: 'POST',
                        data: JSON.stringify({id: id, filePath: url}),
                        dataType: 'json',
                        contentType: 'application/json;charset=utf-8',
                        success: function (data) {
                            if (data.success) {
                                alertx(data.msg, function () {
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                            } else {
                                alertx(data.msg);
                            }
                        }
                    })
                }
                return true;
            };
            $.jBox.confirm("确认删除？", "提示", submit, {buttons: {'确定': true, '取消': false}});
        }


        KindEditor.ready(function (K) {
            var editor1 = K.create('textarea[name="content"]', {
                cssPath: '${ctx}/static/kindeditor/plugins/code/prettify.css',
                uploadJson: '${ctx}/static/kindeditor/jsp/upload_json.jsp',
                fileManagerJson: '${ctx}/static/kindeditor/jsp/file_manager_json.jsp',
                allowFileManager: true,
                afterBlur: function () {
                    this.sync();
                }
            });
            prettyPrint();
        });
        function toBack() {
            window.location.href='${ctx}/knowledge/list?id=${file.folder.id}';
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/knowledge/list?id=${file.folder.id}">知识列表</a></li>
    <li class="active"><a href="${ctx}/knowledge/form?folder.id=${file.folder.id}">知识添加</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="file" action="${ctx}/knowledge/saveKnowledge" method="post"
           class="form-horizontal" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${file.id}"/>
    <input type="hidden" name="folderId" value="${file.folder.id}"/>
    <div class="control-group">
        <label class="control-label">标题:</label>
        <div class="controls">
            <form:input id="title" path="title" htmlEscape="false" class="input-xxlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">关键字:</label>
        <div class="controls">
            <form:input path="keyWords" htmlEscape="false" class="input-xxlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">内容:</label>
        <div class="controls">
            <textarea name="content" rows="4"
                      style="width:600px;height:200px;visibility:hidden;display: block;">${file.content}</textarea>
        </div>
    </div>
    <div id="file_div" class="control-group">
        <label class="control-label">选择文件:</label>
        <div class="controls">
            <input type="file" name="files" multiple/>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="toBack()"/>
    </div>
</form:form>
<table id="jqGrid"></table>
<div id="pager" style="margin-top: 5px"></div>
</body>
</html>