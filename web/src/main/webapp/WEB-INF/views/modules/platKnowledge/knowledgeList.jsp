<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<head>
    <title></title>
    <%@include file="/WEB-INF/views/include/head.jsp" %>
    <%@include file="/WEB-INF/views/include/jqgrid.jsp" %>
    <script type="text/javascript">
        var ctx = '${ctx}', ctxStatic = '${ctxStatic}';
        <%--<shiro:hasPermission name="knowledge:knowledge:edit">--%>
        <%--var hasEditPermission = true;--%>
        <%--</shiro:hasPermission>--%>
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            parent.selectFolderNode('${folderId}');
            $("#jqGrid").jqGrid({
                url: '${ctx}/knowledge/load',
                mtype: "POST",
                postData: {
                    'folder.id':${folderId},
                    title: $('#queryContent').val()
                },
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
                    {label: '', name: 'fileName', hidden: true},
                    {
                        label: '', name: '', width: 7,
                        formatter: function (value, options, row) {
                            var img = "";
                            var title = row.title.toLowerCase();
                            if (title.indexOf("pdf") > 0) {
                                img = '<img src="${ctx}/static/images/pdf.png"/>';
                            } else if (title.indexOf("doc") > 0 || title.indexOf("docx") > 0) {
                                img = '<img src="${ctx}/static/images/word.png"/>';
                            } else if (title.indexOf("png") > 0 || title.indexOf("jpeg") > 0 || title.indexOf("jpg") > 0) {
                                img = '<img src="${ctx}/static/images/pic.png"/>';
                            }
                            return img;
                        }
                    },
                    {
                        label: '标题', name: 'title', width: 120,
                        formatter: function (value, options, row) {
                            var title = value.toLowerCase();
                            return title.lastIndexOf(".") != -1 ? title.substring(0, title.lastIndexOf(".")) : value;
                        }
                    },
                    {label: '关键字', name: 'keyWords', width: 100},
                    {label: '更新时间', name: 'createDate', align: 'center', width: 50},
                    {label: '编辑人', name: 'user.name', align: 'center', width: 50},
                    {
                        label: '操作', name: 'url', width: 50, align: 'center',
                        formatter: function (value, options, row) {
                            var a = '<a href="javascript:void(0)" class="gt_a_click" _id="' + row.id + '" onclick="editFile(this)">编辑</a>';
                            a += ' | <a href="javascript:void(0)" class="gt_a_click" _id="' + row.id + '" onclick="downFile(this)">查看附件</a>';
                            return a;
                        }
                    }
                ],
                viewrecords: true,
                autowidth: true,
                multiselect: true,
                height: $(window).height() - 160,
                rowNum: 20,
                pager: "#pager"
            });
            $("#t_jqGrid").append($("#descTable"));

            onkeydown = function (event) {
                if (event.keyCode == 13) {
                    $("#queryBtn").click();
                }
            }
        });

        function upFile() {
            $.jBox("iframe:${ctx}/knowledge/upLoadJsp?folderId=${folderId}", {
                title: "文档上传",
                width: 800,
                height: 400,
                buttons: {'关闭': true},
                closed: function () {
                    $("#jqGrid").trigger("reloadGrid");
                },
                loaded: function (h) {   //隐藏滚动条
                    $(".jbox-content").css("overflow-y", "hidden");
                }
            });
        }
        function downFile(_this) {
            var id = $(_this).attr('_id');
            $.jBox("iframe:${ctx}/knowledge/downAndPreView?folder.id=${folderId}&id=" + id, {
                title: "查看附件",
                width: 800,
                height: 400,
                buttons: {'关闭': true},
                closed: function () {
//                    $("#jqGrid").trigger("reloadGrid");
                },
                loaded: function (h) {   //隐藏滚动条
                    $(".jbox-content").css("overflow-y", "hidden");
                }
            });
        }
        function removeFile() {
            var ids = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
            if (ids.length == 0) {
                alertx("请选择要删除的文件！");
                return;
            }
            var submit = function (v, h, f) {
                if (v == true) {
                    var array = [];
                    for (var i = 0; i < ids.length; i++) {
                        var rowData = $("#jqGrid").jqGrid('getRowData', ids[i]);
                        var obj = {
                            id: rowData.id,
                            attGroupId: rowData.attGroupId,
                            url: $(rowData.url).attr("url")
                        };
                        array.push(obj);
                    }
                    $.ajax({
                        url: '${ctx}/knowledge/deleteFile',
                        type: 'POST',
                        data: JSON.stringify(array),
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
        function editFile(_this) {
            var id = $(_this).attr('_id');
            console.log('${folderId}');
            $(_this).attr('href', '${ctx}/knowledge/form?folder.id=${folderId}&id=' + id);
        }
        function doSearch() {
            var title = $('#queryContent').val();
            $("#jqGrid").setGridParam({
                page: 1,
                postData: {title: title}
            }).trigger("reloadGrid");
        }
    </script>
    <style type="text/css">
        .ui-jqgrid .ui-userdata {
            height: 50px;
            line-height: 50px;
        }

        .toolbarDiv {
            height: 50px;
            line-height: 50px;
            padding: 0px 10px 0px 10px;
            margin: auto;
            background-color: #F5F5F5;
        }

        .toolbarDiv .left {
            height: 50px;
            line-height: 50px;
            float: left;
            margin: auto;
            width: 50%;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/knowledge/list?folder.id=${folderId}">知识列表</a></li>
    <li><a id="addKnowledge" href="${ctx}/knowledge/form?folder.id=${folderId}">知识添加</a></li>
</ul>

<table id="jqGrid"></table>
<div id="pager"></div>
<div id="descTable" class="toolbarDiv"><!-- 工具栏显示内容，包括按钮、统计信息、说明信息 -->
    <div class="left">
        <%--<button id="addFile" class="btn btn-small btn-primary" type="button" onclick="addFile()">新增</button>--%>
        <div style="display: inline-block;height: 26px;">
            <input id="queryContent" name="name" class="btn"
                   style="width: 200px; line-height: 20px;text-align: left;margin-bottom: 0px;"
                   type="text"
                   placeholder="请输入检索条件" value=""/>
            <a id="queryBtn" href="javascript:void(0);" class="btn btn-small btn-primary" onclick="doSearch()"
               style="">
                <i class="icon-search"></i>&nbsp;
            </a>&nbsp;&nbsp;
        </div>
        <button id="upFile" class="btn btn-small btn-primary" type="button" onclick="upFile()">上传</button>
        <button id="removeFile" class="btn btn-small btn-primary" type="button" onclick="removeFile()">删除</button>
    </div>
</div>
</body>
</html>