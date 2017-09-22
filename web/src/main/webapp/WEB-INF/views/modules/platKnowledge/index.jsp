<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<head>
    <title>知识库管理</title>
    <%-- <%@include file="/WEB-INF/views/include/head.jsp" %> --%>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css"
          type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>

    <link href="${ctxStatic}/common/gtzn.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">var ctx = '${ctx}', ctxStatic = '${ctxStatic}';</script>
    <%--<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>--%>
    <link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.css" rel="stylesheet"/>
    <style type="text/css">
        .ztree {
            overflow: auto;
            margin: 0;
            _margin-top: 10px;
            padding: 10px 0 0 10px;
        }
    </style>
</head>
<body>
<sys:message content="${message}"/>
<div id="content" class="row-fluid">
    <div id="left" class="accordion-group">
        <div class="accordion-heading">
            <label class="accordion-toggle" style="background-color:#24659D;margin-bottom: 0px;color:#fff;">文档管理
                <i style="margin-left:5px;" class="icon-white icon-refresh pull-right" onclick="refreshTree();"
                   title='刷新'></i>
                <i style="margin-left:5px;" class="icon-white icon-minus-sign  pull-right" onclick="removeNode23()"
                   title='删除'></i>
                <i style="margin-left:5px;" class="icon-white icon-edit pull-right" onclick="modifyNode()"
                   title='修改'></i>
                <i style="margin-left:5px;" class="icon-white icon-plus-sign  pull-right" onclick="addNode()"
                   title='新增'></i>
            </label>
        </div>
        <div id="ztree" class="ztree"></div>
    </div>
    <div id="openClose" class="close">&nbsp;</div>
    <div id="right">
        <iframe id="docContent" src="" width="100%" height="91%" frameborder="0"></iframe>
    </div>
</div>
<script type="text/javascript">
    var setting = {
        async: {
            enable: true,
            url: "${ctx}/knowledge/treeData",
            autoParam: ["id"]
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: '-1'
            }
        },
        view: {
            selectedMulti: false
        },
        callback: {
            onClick: zTreeOnClick,
            onAsyncSuccess: onAsyncSuccess
        }
    };

    function zTreeOnClick(event, treeId, treeNode) {
        $('#docContent').attr("src", "${ctx}/knowledge/list?id=" + treeNode.id);
    }
    function onAsyncSuccess(event, treeId, treeNode, msg) {
        if (treeNode.id == 0) {
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            zTree.selectNode(treeNode);
            zTree.setting.callback.onClick(null, zTree.setting.treeId, treeNode);//调用事件
        }
    }
    function addNode() {
        var html = "<div style='padding:10px;'>请输入档案类型：<input type='text' id='docType' name='docType' /></div>";
        var submit = function (v, h, f) {
            if (f.docType == '') {
                $.jBox.tip("请输入档案类型。", 'error', {focusId: "docType"}); // 关闭设置 yourname 为焦点
                return false;
            }
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            var selectedNode = zTree.getSelectedNodes()[0];
            $.post('${ctx}/knowledge/save', {parentId: selectedNode.id, name: f.docType}, function (data) {
                var newID = data.id; //获取新添加的节点Id
                zTree.addNodes(selectedNode, {id: newID, parentId: selectedNode.id, name: f.docType});
                var node = zTree.getNodeByParam("id", newID, null); //根据新的id找到新添加的节点
                zTree.selectNode(node); //让新添加的节点处于选中状态
                zTree.setting.callback.onClick(null, zTree.setting.treeId, node);//调用事件
            });
        };
        $.jBox(html, {title: "请输入档案类型", submit: submit});
    }
    function modifyNode() {
        var html = "<div style='padding:10px;'>请输入新名称：<input type='text' id='docType' name='docType' /></div>";
        var submit = function (v1, h1, f1) {
            if (f1.docType == '') {
                $.jBox.tip("请输入新名称。", 'error', {focusId: "docType"}); // 关闭设置 yourname 为焦点
                return false;
            }
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            var selectedNode = zTree.getSelectedNodes()[0];
            $.post('${ctx}/knowledge/updateFolder', {id: selectedNode.id, name: f1.docType}, function (data) {
                if (data.success) {
                    var submit2 = function (v, h, f) {
                        if (v) {
                            var zTree = $.fn.zTree.getZTreeObj("ztree");
                            selectedNode.name = f1.docType;
                            zTree.updateNode(selectedNode);
                        }
                        return true;
                    };
                    $.jBox.confirm("修改成功！", "提示", submit2, {buttons: {'确定': true}});
                }
            });
            return true;
        };
        $.jBox(html, {title: "请输入新名称", submit: submit});
    }
    function removeNode23() {
        var zTree = $.fn.zTree.getZTreeObj("ztree");
        var selectedNode = zTree.getSelectedNodes()[0];
        var submit = function (v, h, f) {
            if (v) {
                $.post('${ctx}/knowledge/deleteFolder', {id: selectedNode.id}, function (data) {
                    if (data.success) {
                        var submit2 = function (v, h, f) {
                            if (v) {
                                var zTree = $.fn.zTree.getZTreeObj("ztree");
                                zTree.removeNode(selectedNode);
                                var pNode = selectedNode.getParentNode();
                                zTree.selectNode(pNode);
                                zTree.setting.callback.onClick(null, zTree.setting.treeId, pNode);//调用事件
                            }
                            return true;
                        };
                        $.jBox.confirm("删除成功！", "提示", submit2, {buttons: {'确定': true}});
                    }
                });
            }
            return true;
        };
        $.jBox.confirm("确认删除 节点 -- " + selectedNode.name + " 吗？", "提示", submit, {buttons: {'确定': true, '取消': false}});
    }
    function refreshTree() {
        var data = [{id: 0, pId: -1, name: "知识库管理", isParent: true}];
        $.fn.zTree.init($("#ztree"), setting, data);
        var zTree = $.fn.zTree.getZTreeObj("ztree");
        var rootNode = zTree.getNodeByParam('id', 0);
        zTree.expandNode(rootNode);
    }
    refreshTree();

    var leftWidth = 230; // 左侧窗口大小
    var htmlObj = $("html"), mainObj = $("#main");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize() {
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({"overflow-x": "hidden", "overflow-y": "hidden"});
        mainObj.css("width", "auto");
        frameObj.height(strs[0] - 5);
        var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
        $("#right").width($("#content").width() - leftWidth - $("#openClose").width() - 5);
        $(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
    }

    function selectFolderNode(_id) {
        var zTree = $.fn.zTree.getZTreeObj("ztree");
        var node = zTree.getNodeByParam('id', _id);
        zTree.selectNode(node);
    }
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>