<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<head>
    <title>目录检索</title>
    <%@include file="/WEB-INF/views/include/head.jsp" %>
    <script src="${ctx}/editor-app/libs/json3_3.2.6/lib/json3.js" type="text/ecmascript"></script>
    <style type="text/css">
        #jieguohuizong {
            width: 100%;
            text-align: right;
        }

        #result {
            width: 98%;
            margin: auto;
        }

        .result_list {
            margin: 8px 0px 5px 0px;
            border-bottom: 1px solid rgb(195, 188, 195);
            border-bottom-style: dashed;
            list-style-type: none;
        }

        .result_list_title {
            font-size: 14px;
            text-align: left;
            padding-left: 20px;
        }

        .result_list_content {
            margin: 5px;
            color: #000000;
            text-align: left;
            padding-left: 20px;
        }

        #page {
            padding-left: 20px;
            margin: 20px 0 20px 0px;
            text-align: left;
        }

        #page div, #page strong {
            display: inline-block;
            vertical-align: text-bottom;
            height: 30px;
            text-align: center;
            line-height: 0px;
            text-decoration: none;
            overflow: hidden;
            margin-right: 9px;
            background: #fff;
        }

        #page div {
            cursor: pointer;
            color: #0F7921;
        }

        #page div, #page strong {
            display: inline-block;
            vertical-align: text-bottom;
            height: 36px;
            text-align: center;
            line-height: 34px;
            text-decoration: none;
            overflow: hidden;
            margin-right: 9px;
            background: #fff;
        }

        #page .pc {
            width: 34px;
            height: 34px;
            border: 1px solid #e1e2e3;
            cursor: pointer;
            display: block;
        }

        #page strong .pc {
            border: 0;
            width: 36px;
            height: 36px;
            line-height: 36px;
        }
    </style>
    <script>
        var count = 0;
        var page = 1;
        var totalRecord = 0;
        var pageSize = 15;
        $(document).ready(function () {
            if ("${titleCont}") {
                $("#queryContent").val("${titleCont}");
                $("#jieguohuizong").html("<span style='margin-left:20px;margin-right:10px;'>检索到的记录条数为：<font style='color:red;'>" + "${count}" + "</font>条</span>");
            }
            if ($("#queryContent").val()) {
                search2();
            }

            onkeydown = function (event) {
                if (event.keyCode == 13) {
                    $("#unitcodeButton").click();
                }
            }
        });
        function doSearch() {
            count = 0;
            page = 1;
            totalRecord = 0;
            search2();
        }
        function pageClick(PageNumber) {
            if (PageNumber == "3") {//下一页
                var total;
                if (totalRecord % pageSize == 0) {
                    total = totalRecord / pageSize;
                } else {
                    total = Math.ceil(totalRecord / pageSize);
                }
                if (total == page) {
                    return;
                } else {
                    page = page + 1;
                }
                search2(page);
            } else if (PageNumber == "2") {//上一页
                if (page == 1) {
                    return;
                } else {
                    page = page - 1;
                }
                search2(page);
            } else if (PageNumber == "1") {//首页
                page = 1;
                search2(page);
            } else if (PageNumber == "4") {//尾页
                if (totalRecord % pageSize == 0) {
                    page = totalRecord / pageSize;
                } else {
                    page = Math.ceil(totalRecord / pageSize);
                }
                search2(page);
            }
        }
        function search2() {
            var url = '${ctx}/plat/platInformation/whole';
            var type = "0";//0:行业 1:公告 2:知识库 3:行业&公告 4: 行业&知识库 5:公告&知识库 6:行业&公告&知识库
            if ($("#queryContent").val()) {
                var hyjsflag = $("#hyjs").is(":checked");
                var ggjsflag = $("#ggjs").is(":checked");
                var zskjsflag = $("#zskjs").is(":checked");
                if (!hyjsflag && !ggjsflag && !zskjsflag) {
                    top.$.jBox.info('请选择检索对象!', '提示');
                    return;
                }
                if (hyjsflag && !ggjsflag && !zskjsflag) {
                    type = '0';
                } else if (!hyjsflag && ggjsflag && !zskjsflag) {
                    type = '1';
                } else if (!hyjsflag && !ggjsflag && zskjsflag) {
                    type = '2';
                } else if (hyjsflag && ggjsflag && !zskjsflag) {
                    type = '3';
                } else if (hyjsflag && !ggjsflag && zskjsflag) {
                    type = '4';
                } else if (!hyjsflag && ggjsflag && zskjsflag) {
                    type = '5';
                } else if (hyjsflag && ggjsflag && zskjsflag) {
                    type = '6';
                }
            } else {
                top.$.jBox.info('请输入检索条件!', '提示');
                return;
            }
            $.jBox.tip("正在查询，请稍后...", 'loading', {opacity: 0.5, persistent: true});
            $("#result").empty();
            $.ajax({
                type: "POST",
                url: url,
                data: {
                    page: page,
                    rows: pageSize,
                    titleCont: $("#queryContent").val(),
                    type: type
                },
                dataType: "json",
                success: function (result) {
                    $.jBox.closeTip();
                    if (result.success == true) {
                        totalRecord = result.msg.count;
                        $("#jieguohuizong").html("<span style='margin-left:20px;margin-right:10px;'>检索到的记录条数为：<font style='color:red;'>" + result.msg.count + "</font>条</span>");
                        var list = result.msg.list;
                        for (var i = 0; i < list.length; i++) {
                            var li = "<div class='result_list'>";
                            var content = list[i].content ? list[i].content : '';
                            var span = "<div class='result_list_content'><span>" + content + "</span></div>";
                            var dak = "";
                            var title = list[i].title;
                            if (title.lastIndexOf(".") != -1) {
                                title = title.substring(0, title.lastIndexOf("."));
                            }
                            dak = "<div class='result_list_title'><a href='javascript:void(0);' _type='" + list[i].type + "' _title='" + list[i].title + "' _id='" + list[i].id + "' onclick='findId23(this)'>" + list[i].tname + title + "</a></div>";
                            $("#result").append(li + dak + span + "</div>");
                        }
                        createPage();
                    } else {
                        totalRecord = 0;
                        count = 0;
                        page = 1;
                        createPage()
                    }
                },
                error: function () {
                    alert('出现错误，稍后再试！');
                }
            });
        }

        function createPage() {
            totalRecord = totalRecord == 0 ? 1 : totalRecord;
            var div = "<div class='n'  onclick='pageClick(" + "1" + ")'>&lt;&lt;首页</div>"
                + "<div class='n'  onclick='pageClick(" + "2" + ")'>&lt;上一页</div>"
                + "<div>第" + page + "页</div>"
                + "<div>共" + Math.ceil(totalRecord / pageSize) + "页</div>"
                + "<div class='n' onclick='pageClick(" + "3" + ")'>下一页&gt;</div>"
                + "<div class='n' onclick='pageClick(" + "4" + ")'>尾页&gt;&gt;</div>";
            $("#page").empty();
            $("#page").append(div);
        }

        function findId23(_this) {
            var id = $(_this).attr('_id');
            var title = $(_this).attr('_title');
            var type = $(_this).attr('_type');
            <%--if (url) {--%>
            <%--if (url.toLowerCase().indexOf('.pdf') > 0) {--%>
            <%--window.open("${ctx}/knowledge/downFile?url=" + url + "&fileName=" + encodeURIComponent(encodeURIComponent(title)) + "&preview=true", '_blank');--%>
            <%--} else {--%>
            <%--window.location.href = "${ctx}/knowledge/downFile?url=" + url + "&fileName=" + encodeURIComponent(encodeURIComponent(title)) + "&preview=false";--%>
            <%--}--%>
            <%--} else {--%>
            window.location.href = "${ctx}/plat/platInformation/go?id=" + id + "&type=" + type;
//            }
        }
    </script>
</head>
<body>
<div class="widget">
    <div class="widget-body">
        <div id="maindiv">
            <div style="font-size: 15px; height: 100%; width: 100%; line-height: 60px; text-align: center;">
                行业检索<input type="checkbox" checked=checked name="hyjs" id="hyjs">&nbsp;&nbsp;
                公告检索<input type="checkbox" checked=checked name="ggjs" id="ggjs">&nbsp;&nbsp;
                知识库检索<input type="checkbox" checked=checked name="zskjs" id="zskjs">
            </div>
        </div>
        <div class="input-append" style="width: 100%; text-align: center;">
            <input id="queryContent" name="unitcodes" style="width: 30%; height: 25px; line-height: 25px;" type="text"
                   placeholder="请输入检索条件" value=""/>
            <a id="unitcodeButton" href="javascript:void(0);" onclick="doSearch();" title="检索" class="btn"
               style="height: 25px; line-height: 25px;background: #24659D">
                <i class="icon-search"></i>&nbsp;
            </a>&nbsp;&nbsp;
        </div>
        <div id="jieguohuizong"></div>
        <div style="width: 100%; text-align: right;">
            <div id="result"></div>
            <div id="page"></div>
        </div>
        <input type="hidden" id="core" value=""/>
    </div>
</div>

</body>
</html>