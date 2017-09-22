<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>目录检索</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %> 
	<script src="${ctx}/editor-app/libs/json3_3.2.6/lib/json3.js" type="text/ecmascript" ></script>
	<style>
		#jieguohuizong{width:100%;}
		#result{width:100%;}
		#result li{
		    border-bottom: 1px solid rgb(195, 188, 195);
   			border-bottom-style: dashed;
   			list-style-type:none;
		}
		#page{
		    padding: 0 0 0 21px;
  			margin: 30px 0 40px;
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
		    color:#0F7921;
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
		    display:block;
		}
		#page strong .pc {
		    border: 0;
		    width: 36px;
		    height: 36px;
		    line-height: 36px;
		}
	</style>
	<script>
	var count=0;
	var page=1;
	var totalRecord;
	$(document).ready(function(){
		$(".widget").addClass(localStorage.menu_color_name);
		$("#result").css("height",$("."+localStorage.menu_color_name).height()-40);
			$(window).resize(function(){
				$("."+localStorage.menu_color_name).css("height",$(window).height()-2);
				$("#result").css("height",$("."+localStorage.menu_color_name).height()-45);
			}).resize();
			//createPage();
			if("${titleCont}"!=""){
				$("#queryContent").val("${titleCont}");
				search2();
			}
			if($("#queryContent").val()!=""){
				search2();
			}
			
	});

	function pageclick(PageNumber){
		if(PageNumber=="3"){//下一页
			var total;
			if(totalRecord%6==0){
				total=totalRecord/6;
			}else{
				total=Math.ceil(totalRecord / 6) ;
			}
			if(total==page){
				return;
			}else{
				page=page+1;

			}
			search2(page);
		}else if(PageNumber=="2"){//上一页
			if(page==1){
				return;
			}else{
				page=page-1;
	
			}
			search2(page);
		}else if(PageNumber=="1"){//首页
			page=1;
			search2(page);
		}else if(PageNumber=="4"){//尾页
		if(totalRecord%6==0){
			page=totalRecord/6;
		}else{
			page=Math.ceil(totalRecord / 6) ;
		}
			search2(page);
		}
	}
	function search2(){
		var url;
		if($("#queryContent").val()!=''){
			var hyjsflag=$("#hyjs").is(":checked");
			var ggjsflag=$("#ggjs").is(":checked");
			if(hyjsflag==false&&ggjsflag==false){
				top.$.jBox.info('请选择检索对象!', '提示');
				return;
			}
			if(hyjsflag==true&&ggjsflag==true){
				url="${ctx}/plat/platInformation/whole";
			}else{
				if(hyjsflag==true){
					url="${ctx}/plat/platInformation/loadData";
				}else{
					url="${ctx}/plat/platInformation/loadNotice";
				}
			}
		}else{
			 top.$.jBox.info('请输入检索条件!', '提示');
			 return;
		}
		
		
		//var val_payPlatform = $('input[name="js"]:checked ').val();
		
		/* if(val_payPlatform=="2"){
			url="${ctx}/plat/platInformation/loadData";
		}else{
			url="${ctx}/plat/platInformation/loadNotice";
		}
		 if($("#queryContent").val()==""){
			 top.$.jBox.info('请输入检索条件!', '提示');
			 return;
		}  */
		$.jBox.tip("正在查询，请稍后...", 'loading',{opacity:0.5,persistent:true});
		 $("#result").empty();
		 
		$.ajax({
			 type: "POST",
			 url:url,
			  data:{
				  page:page,
				  rows:"6",
				  titleCont:$("#queryContent").val(),
			 }, 
			 dataType: "json",
			 success:  function(result) {
				 $.jBox.closeTip();
				 if(result.success == true){
					  totalRecord =result.msg.count;
					 $("#jieguohuizong").html("<span style='margin-left:20px;margin-right:10px;'>检索到的记录条数为：<font style='color:red;'>"+result.msg.count+"</font>条</span>"); 
					 var list = result.msg.list;
					 for(var i=0;i<list.length;i++){
						 var li ="<li style='margin-top:10px;margin-bottom:10px;'>";
							var span="<span style='margin-right:10px;color:#000000;overflow:hidden;height:62px;width:98%;display:block;text-align:left;'>"+list[i].content+"</span>";
							var span2="";
							var bt="";
							var dak=""; 
							 dak="<div style='font-size:18px;width:100%;text-align:left;color:#0F7921;cursor:pointer;' onclick='findId("+list[i].id+")'>"+list[i].title+"</div>";
							 $("#result").append(li+dak+span+"</li>");
							 createPage();
					 }
				 }
				
			},
			error : function() {
				alert('出现错误，稍后再试！');
			}
		});
	}
	
	function createPage(){
		
		if(totalRecord%6==0){
			total=totalRecord/6;
		}else{
			total=Math.ceil(totalRecord / 6) ;
		}
			div="<div class='n'  onclick='pageclick("+"1"+")'>&lt;&lt;首页</div>";
			div1="<div class='n'  onclick='pageclick("+"2"+")'>&lt;上一页</div>";
			div2="<div   >第"+page+"页</div>";
			div3="<div >共"+total+"页</div>";

			div4="<div class='n'  onclick='pageclick("+"3"+")'>下一页&gt;</div>";
			div5="<div class='n'  onclick='pageclick("+"4"+")'>尾页&gt;&gt;</div>";
			$("#page").empty();
			$("#page").append(div+div1+div2+div3+div4+div5);
		//}
		
	}
	
	/* $(document).ready(function(){
		$("#maindiv").style("height",$(window).height()-170);
	}); */
	function findId(id){
		var hyjsflag=$("#hyjs").is(":checked");
		var ggjsflag=$("#ggjs").is(":checked");
		var type;
		if(hyjsflag==false&&ggjsflag==false){
			top.$.jBox.info('请选择检索对象!', '提示');
			return;
		}
		if(hyjsflag==true&&ggjsflag==true){
			type="3";
		}else{
			if(hyjsflag==true){
				type="1";
			}else{
				type="2";
			}
		}
		window.location.href = "${ctx}/plat/platInformation/go?id="+id+"&type="+type; 

	}
	
</script>
</head>
<body>
<div id="list_box" class="row-fluid">
        <div class="span12"> 
          <!-- BEGIN ALERTS PORTLET-->
          <div class="widget">
            <div class="widget-title">
              <h4><i class="icon-reorder"></i> 查询检索列表</h4>
            </div>
            <div class="widget-body" style="height: 100%">
<div style="overflow-y:scroll;overflow-x:hidden;" id="maindiv">
	<!-- <div style="font-size: 40px;
    height: 60px;
    width: 100%;
    line-height: 60px;
    text-align: center;"> 
		全文检索
	</div> -->
	<div style="font-size: 18px;height: 100%;width: 100%;line-height: 60px;text-align: center;"> 
		行业检索<input type="checkbox" checked=checked name="hyjs" id="hyjs">&nbsp;&nbsp;
		公告检索<input type="checkbox" name="ggjs" id="ggjs">
	</div>
	</div>
	<div class="input-append" style="width:100%;text-align:center;">
		<input id="queryContent" name="unitcodes" style="width:400px;height:30px;line-height:30px;" type="text" placeholder="请输入检索条件" value=""/>
		<a id="unitcodeButton" href="javascript:" onclick="search2()" title="检索" class="btn  " style="height:30px;line-height:30px;">&nbsp;
		<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
	</div>
	<div id="jieguohuizong">
		
	</div>
	<div style="height: 60%;width: 100%;text-align: center">
		<ul id="result" style="width: 500px;text-align: center">
		</ul>
	</div>
	<div id="page" style="height: 100%;">
	    
	</div>
	<input type="hidden" id="core" value=""/>
</div>
			</div>
          </div>
          <!-- END ALERTS PORTLET--> 
        </div>
      </div>

</body>
</html>