<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>冠通科技生态智慧一体化管理平台</title>
	<%@include file="/WEB-INF/views/include/platHead.jsp" %>
	<script src="${ctx}/static/plat/highCharts/highstock.js"></script>
	
	<script type="text/javascript">
	$(function(){
	});
	</script>
	<style type="text/css">
		.row_first {
			height: 200px;
		}
		.row_second {
			height: 160px;
		}
		.row_three, .row_four {
			height: 230px;
		}
		.panel {
			margin-bottom: 15px;
			border: 0px;
		}
		.panel-primary {
			border-color: #fff;
		}
		.panel-primary>.panel-heading {
			font-weight: bold;
			color: #505050;
			background: url(${ctxStatic}/images/edge_z.jpg) center top repeat;
    		border-radius:4px 4px 0px 0px;
    		border-style:solid; border-width:0.5px; border-color:#CBCBCB;
    		
		}
		.row-offset {
			 margin-left: -10px;
		}
		a:link {
			color:#626262;
		}
		a:visited {
			color: #939393;
			text-decoration:none;
		}
		a:hover {
			color:#626262;
		}
		a:active {
			color:#626262;
			text-decoration:none;
		} 
		.a-more {
			font-size: 11px;
			font-weight: normal;
		}
		.bgcolor {
			border-radius:0px 0px 4px 4px;
			border-style:solid; border-width:1px; border-color:#CBCBCB;
		}
		.div_bgcolor {
			border-radius:0px 0px 4px 4px;
			border-style:solid; border-width:1px; border-color:#CBCBCB;
		}
	</style>
</head>
<body>
	<div class="row-fluid">
		<div class="span4">
			<!-- 照片墙 -->
			<div class="panel panel-primary">
				<div class="panel-heading">
					<img src="${ctxStatic}/images/home/gnyl.png" class="home_title_img">馆内一览
				</div>
				<div class="panel-body row_first div_bgcolor" id="photoList" style="width:100%;">
					<%@ include file="../platPhoto/photoSlider4HomePage.jsp"%>
				</div>
			</div>
		</div>
		<div class="span4">
			<!--通知公告部分-->
			<div class="panel panel-primary row-offset">
				<div class="panel-heading">
					<img src="${ctxStatic}/images/home/gg.png" class="home_title_img">公告 
					<span style="float: right;">
						<a href="${ctx}/notice/noticeInfo/index" class="a-more">更多》</a>
					</span>
				</div>
				<div class="panel-body row_first bgcolor">
				 	<%@ include file="include/noticeList.jsp"%>
				</div>
			</div>
		</div>
		<div class="span4">
			<!-- 行业资讯 -->
			<div class="panel panel-primary row-offset">
				<div class="panel-heading">
					<img src="${ctxStatic}/images/home/hyzx.png" class="home_title_img">行业资讯 
					<span style="float: right;">
						<a href="${ctx}/plat/platInformation/webIndex" class="a-more">更多》</a>
					</span>
				</div>
				<div class="panel-body row_first bgcolor">
					<%@ include file="include/informationList.jsp"%>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span4">
			<!-- 档案利用指南 -->
			<div class="panel panel-primary">
				<div class="panel-heading">
					<img src="${ctxStatic}/images/home/gzjd.png" class="home_title_img">工作进度 
					<span style="float: right;">
						<a href="${ctx}/platDocCheck/toDocCheckIndex" class="a-more">更多》</a>
					</span>
				</div>
				<div class="panel-body row_second bgcolor" id="docCheck">
					<%@ include file="../platDocCheck/docCheckList4HomePage.jsp"%>
				</div>
			</div>
		</div>
		<div class="span4">
			<!-- 工作进度跟踪 -->
			<div class="panel panel-primary row-offset">
				<div class="panel-heading">
					<img src="${ctxStatic}/images/home/xzjg.png" class="home_title_img">工作监督 
					<span style="float: right;">
						<a href="${ctx}/workRate/chartsIndex" class="a-more">更多》</a>
					</span>
				</div>
				<div class="panel-body row_second bgcolor" id="workRate">
					<%@ include file="../workRate/workRateHomePage.jsp"%>
				</div>
			</div>
		</div>
		<div class="span4">
			<!-- 安全报警 -->
			<div class="panel panel-primary row-offset">
				<div class="panel-heading">
					<img src="${ctxStatic}/images/home/aqbj.png" class="home_title_img">安全报警 
					<span style="float: right;">
						<a href="${ctx}/platAlarm/toAlarmIndex" class="a-more">更多》</a>
					</span>
				</div>
				<div class="panel-body row_second bgcolor" id="alarm">
					<%@ include file="../platAlarm/alarmList4HomePage.jsp"%>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span8">
			<!-- 档案利用 -->
			<div class="panel panel-primary">
				<div class="panel-heading ">
					<img src="${ctxStatic}/images/home/daly.png" class="home_title_img">档案利用 
					<span style="float: right;">
						<a href="${ctx}/platDocUse/toDocUseIndex" class="a-more">更多》</a>
					</span>
				</div>
				<div class="panel-body row_three div_bgcolor">
					<%@ include file="../platDocUse/docUse4HomePage.jsp"%>
				</div>
			</div>
			
		</div>
		<div class="span4">
			<!-- 最新采集 -->
			<div class="panel panel-primary row-offset">
				<div class="panel-heading">
					<img src="${ctxStatic}/images/home/zxcj.png" class="home_title_img">最新采集
					<span style="float: right;">
						<a href="${ctx}/platcollection/platCollection/index" class="a-more">更多》</a>
					</span>
				</div>
				<div class="panel-body row_three bgcolor">
					<%@ include file="include/collection.jsp"%>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span8">
			<!-- 馆藏 -->
			<div class="panel panel-primary">
				<div class="panel-heading">
					<img src="${ctxStatic}/images/home/gcdasl.png" class="home_title_img">档案馆藏数量
					<span style="float: right;">
						<a href="${ctx}/platDocStore/toDocStoreIndex" class="a-more">更多》</a>
					</span>
				</div>
				<div class="panel-body row_four div_bgcolor">
					<%@ include file="../platDocStore/docStore4HomePage.jsp"%>
				</div>
			</div>
		</div>
		<div class="span4">
			<!-- 行政监管 -->
			<div class="panel panel-primary row-offset">
				<div class="panel-heading">
					<img src="${ctxStatic}/images/home/xzjg.png" class="home_title_img">行政监管
					<span style="float: right; font-size: 12px">
						人社厅行政执法检查结果汇总&nbsp;&nbsp;<a href="#" class="a-more" onclick="alert('馆室一体化系统暂时不可用')">更多》</a>
					</span>
				</div>
				<div class="panel-body row_four div_bgcolor">
					<%@ include file="../platRegulation/regulation.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>