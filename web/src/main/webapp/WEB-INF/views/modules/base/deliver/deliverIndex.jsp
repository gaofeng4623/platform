<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
    <title>交付机配置 top.$.jBox</title>
    <%-- <%@include file="/WEB-INF/views/include/head.jsp" %> --%>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>

    <link href="${ctxStatic}/common/gtzn.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
    <%--<script src="${ctxStatic}/jqGrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>--%>
    <%--<script src="${ctxStatic}/jqGrid/js/trirand/i18n/grid.locale-cn.js" type="text/ecmascript" ></script>--%>
    <%--<link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/jquery-ui.css" />--%>
    <%--<link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/jqGrid/css/trirand/ui.jqgrid.css" />--%>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
    <style type="text/css">
        .ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
    </style>
</head>
<body>
<sys:message content="${message}"/>
<div id="content" class="row-fluid">
    <div id="left" class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle">组织机构<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
        </div>
        <div id="ztree" class="ztree"></div>
    </div>
    <div id="openClose" class="close">&nbsp;</div>
    <div id="right">
        <iframe id="officeContent" src="${ctx}/base/deliver/list" width="100%" height="91%" frameborder="0"></iframe>
    </div>
</div>
<script type="text/javascript">
    var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
        callback:{onClick:function(event, treeId, treeNode){
            var id = treeNode.id == '0' ? '' :treeNode.id;
            if ('1' == treeNode.type) {
                $('#officeContent').attr("src","${ctx}/base/deliver/list?id="+id);
            }
        }
        }
    };

    function refreshTree(){
        $.getJSON("${ctx}/sys/office/treeData",function(data){
            $.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
        });
    }
    refreshTree();

    var leftWidth = 180; // 左侧窗口大小
    var htmlObj = $("html"), mainObj = $("#main");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize(){
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
        mainObj.css("width","auto");
        frameObj.height(strs[0] - 5);
        var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
        $("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
        $(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
    }
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>