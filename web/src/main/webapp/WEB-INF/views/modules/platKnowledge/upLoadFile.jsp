<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <!-- Force latest IE rendering engine or ChromeFrame if installed -->
    <!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <![endif]-->
    <!--<meta charset="utf-8">-->
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>首页</title>
    <!-- Bootstrap Core CSS -->
    <%--<link href="${ctxStatic}/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctxStatic}/jQuery-File-Upload-9.18.0/css/style.css" rel="stylesheet" type="text/css"/>
    <!-- blueimp Gallery styles -->
    <%--<link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">--%>
    <link href="${ctxStatic}/jQuery-File-Upload-9.18.0/css/blueimp-gallery.min.css" rel="stylesheet" type="text/css"/>
    <!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
    <link href="${ctxStatic}/jQuery-File-Upload-9.18.0/css/jquery.fileupload.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/jQuery-File-Upload-9.18.0/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css"/>
    <!-- CSS adjustments for browsers with JavaScript disabled -->
    <noscript>
        <link href="${ctxStatic}/jQuery-File-Upload-9.18.0/css/jquery.fileupload-noscript.css" rel="stylesheet"
              type="text/css"/>
    </noscript>
    <noscript>
        <link href="${ctxStatic}/jQuery-File-Upload-9.18.0/css/jquery.fileupload-ui-noscript.css" rel="stylesheet"
              type="text/css"/>
    </noscript>

    <style>
        body {
            font-family: "Arial", "Microsoft YaHei", "黑体", "宋体", sans-serif;
            background-color: #EBF5F3;
        }

        * {
            margin: 0;
        }

        html, body {
            height: 100%;
        }

        .navbar-default .navbar-nav > li > a {
            color: #ffffff;
        }

        .navbar-default .navbar-nav > li > a:hover {
            color: #175A94;
        }

        hr {
            border-bottom: 1px solid #bbb;
        }

        @media screen and (min-width: 800px) {
            .container {
                width: 800px;
            }
        }

        @media screen and (min-width: 800px) {
            .center_toaster {
                right: 35%;
                width: 30%;
            }
        }

        @media screen and (min-width: 400px) and (max-width: 799px) {
            .center_toaster {
                right: 25%;
                width: 50%;
            }
        }

        @media screen and (min-width: 200px ) and (max-width: 399px) {
            .center_toaster {
                right: 10%;
                width: 80%;
            }
        }

        .row a {
            text-decoration: none;
        }

        .row a:hover {
            text-decoration: none;
        }

        .wrapper {
            min-height: 100%;
            height: auto !important;
            height: 100%;
            margin: 0 auto -6em;
        }

        .push {
            height: 6em;
        }


    </style>


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <!--<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>-->
    <!--<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>-->
    <%--<![endif]-->--%>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/tmpl.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/load-image.all.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/canvas-to-blob.min.js" type="text/javascript"></script>
    <%--<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>--%>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/jquery.blueimp-gallery.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/jquery.iframe-transport.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/jquery.fileupload.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/jquery.fileupload-process.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/jquery.fileupload-image.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/cors/jquery.xdr-transport.js" type="text/javascript"></script>


    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/jquery.fileupload-validate.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/jquery.fileupload-ui.js" type="text/javascript"></script>
    <%--<script src="${ctxStatic}/jQuery-File-Upload-9.18.0/js/main.js"></script>--%>
    <!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
    <!--[if (gte IE 8)&(lt IE 10)]>
    <script src="js/cors/jquery.xdr-transport.js"></script>
    <![endif]-->

</head>

<body style="padding-top:1px">
<div class="wrapper">
    <!-- Page Content -->

    <div class="container kv-main" style="width:800px">
        <form id="fileupload" action="#" method="POST" enctype="multipart/form-data">
            <!-- Redirect browsers with JavaScript disabled to the origin page -->
            <noscript><input type="hidden" name="redirect" value="#">
            </noscript>
            <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
            <div class="row fileupload-buttonbar">
                <div class="col-lg-7">
                    <span class="btn btn-success fileinput-button">
                        <i class="glyphicon glyphicon-plus"></i>
                        <span>选择文件...</span>
                        <input type="file" name="file" multiple>
                    </span>
                    <button type="submit" class="btn btn-primary start">
                        <i class="glyphicon glyphicon-upload"></i>
                        <span>上传</span>
                    </button>
                    <button type="reset" class="btn btn-warning cancel">
                        <i class="glyphicon glyphicon-ban-circle"></i>
                        <span>取消</span>
                    </button>
                    <span>(接收类型：gif|jpeg|jpg|png|doc|docx|pdf|txt)</span>
                    <%--<button type="button" class="btn btn-danger delete">--%>
                    <%--<i class="glyphicon glyphicon-trash"></i>--%>
                    <%--<span>删除</span>--%>
                    <%--</button>--%>
                    <%--<div id="dropzone" class="col-lg-7 fade well">拖拽文件到此区域</div>--%>
                    <!-- The global file processing state -->
                    <span class="fileupload-process"></span>


                </div>


                <!-- The global progress state -->
                <div class="col-lg-5 fileupload-progress fade">
                    <!-- The global progress bar -->
                    <div class="progress progress-striped active" role="progressbar" aria-valuemin="0"
                         aria-valuemax="100">
                        <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                    </div>
                    <!-- The extended global progress state -->
                    <div class="progress-extended">&nbsp;</div>
                </div>
            </div>
            <!-- The table listing the files available for upload/download -->
            <table role="presentation" class="table table-striped">
                <tbody class="files"></tbody>
            </table>
        </form>
    </div>
    <div class="push" style="width:730px"></div>
</div>

<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>上传</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}





</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.filePath%}" title="{%=file.fileName%}" download="{%=file.fileName%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.filePath) { %}
                    <a href="{%=file.filePath%}" title="{%=file.fileName%}" download="{%=file.fileName%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.fileName%}</a>
                {% } else { %}
                    <span>{%=file.fileName%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}



</script>

</body>
<!-- jQuery -->


<script type="text/javascript">
    $(document).ready(function(){
        $("#isEdit").bind('click', function () {
            if ($(this).attr("checked") == "checked") {
                $("#edit_div").show();
            } else {
                $("#edit_div").hide();
            }
        })
    });
    $('#fileupload').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: 'upload/${folderId}',
        autoSubmit: "false",  //取消自动上传
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png|doc|docx|pdf|txt)$/i, //限制上传文件格式
        extErrorStr: "上传文件格式不对",
        maxFileCount: 5,       //上传文件数量
        maxFileSize: 1024 * 1024 * 30, //大小限制1M
        sizeErrorStr: "上传文件不能大于10M",
        returnType: "json"  //返回数据格式为json
    });
</script>

</html>