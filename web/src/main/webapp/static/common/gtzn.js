/*
 * 通用公共方法
 * @author gtzn
 * @version 2014-4-29
 */
$(document).ready(function() {
	try{
		// 链接去掉虚框
		$("a").bind("focus",function() {
			if(this.blur) {this.blur();};
		});
		//所有下拉框使用select2
		$("select").select2();
	}catch(e){
		// blank
	}
});

//引入js和css文件
function include(id, path, file){
	if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
	}
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}

// 恢复提示框显示
function resetTip(){
	top.$.jBox.tip.mess = null;
}

// 关闭提示框
function closeTip(){
	top.$.jBox.closeTip();
}

//显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		top.$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0, 
			timeout:  timeout == undefined ? 2000 : timeout});
	}, lazytime == undefined ? 500 : lazytime);
}

// 显示加载框
function loading(mess){
	if (mess == undefined || mess == ""){
		mess = "正在处理，请稍等...";
	}
	resetTip();
	top.$.jBox.tip(mess,'loading',{opacity:0});
}

// 关闭提示框
function closeLoading(){
	// 恢复提示框显示
	resetTip();
	// 关闭提示框
	closeTip();
}

// 警告对话框
function alertx(mess, closed){
	top.$.jBox.info(mess, '提示', {closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
}

// 确认对话框
function confirmx(mess, href, closed){
	top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
		if(v=='ok'){
			if (typeof href == 'function') {
				href();
			}else{
				resetTip(); //loading();
				location = href;
			}
		}
	},{buttonsFocus:1, closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
	return false;
}

// 提示输入对话框
function promptx(title, lable, href, closed){
	top.$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable + "：<textarea id='txt' name='txt' row='3'/></div>", {
			title: title, submit: function (v, h, f){
	    if (f.txt == '') {
	        top.$.jBox.tip("请输入" + lable + "。", 'error');
	        return false;
	    }
		if (typeof href == 'function') {
			href(v, h, f);
		}else{
			resetTip(); //loading();
			location = href + encodeURIComponent(f.txt);
		}
	},closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	return false;
}

// 添加TAB页面
function addTabPage(title, url, closeable, $this, refresh){
	top.$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: title,
        closeable: closeable == undefined,
        data: {
            dataType: 'iframe',
            dataLink: url
        }
    }).loadData(refresh != undefined);
}

// cookie操作
function cookie(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
}

// 数值前补零
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){  
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);  
}

//截取字符串，区别汉字和英文
function abbr(name, maxLength){  
 if(!maxLength){  
     maxLength = 20;  
 }  
 if(name==null||name.length<1){  
     return "";  
 }  
 var w = 0;//字符串长度，一个汉字长度为2   
 var s = 0;//汉字个数   
 var p = false;//判断字符串当前循环的前一个字符是否为汉字   
 var b = false;//判断字符串当前循环的字符是否为汉字   
 var nameSub;  
 for (var i=0; i<name.length; i++) {  
    if(i>1 && b==false){  
         p = false;  
    }  
    if(i>1 && b==true){  
         p = true;  
    }  
    var c = name.charCodeAt(i);  
    //单字节加1   
    if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
         w++;  
         b = false;  
    }else {  
         w+=2;  
         s++;  
         b = true;  
    }  
    if(w>maxLength && i<=name.length-1){  
         if(b==true && p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(b==false && p==false){  
             nameSub = name.substring(0,i-3)+"...";  
         }  
         if(b==true && p==false){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         break;  
    }  
 }  
 if(w<=maxLength){  
     return name;  
 }  
 return nameSub;  
}

var Share = {};
/**
 * 处理超时错误
 */
//全局的ajax访问，处理ajax清求时sesion超时
$.ajaxSetup({
	cache: false,
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XMLHttpRequest, textStatus) {
		var sessionstatus = XMLHttpRequest.getResponseHeader("session-status"); // 通过XMLHttpRequest取得响应头，sessionstatus，
		if ("timeout" == sessionstatus) {
			// 如果超时就处理 ，指定要跳转的页面
			$.messager.alert('错误', '登录超时，请重新登录！', 'error', function() {
				window.location.replace(ctx + "/login.jsp");
			});
		}
	}
}); 

/**
 * Ajax请求方法
 * @param {Object} settings.params 参数对象，必须
 * @param {String} settings.url 请求地址，必须
 * @param {Function} settings.callback 成功后回调方法，必须
 * @param {boolean} settings.showMsg 处理成功时，是否显示提示信息 true:显示 false:不显示
 * @param {boolean} settings.showWaiting 是否显示等待条 true:显示 false:不显示
 * @param {Number} settings.timeout 超时时间
 * @param {String} settings.successMsg 成功消息
 * @param {String} settings.failureMsg 失败消息
 */
Share.Ajax = function (settings) {
    // 参数对象
    var params = settings.params === undefined ? {} : settings.params,
     showWaiting = settings.showWaiting === undefined ? false : settings.showWaiting,
     showMsg = settings.showMsg === undefined ? true : settings.showMsg,
     timeout = settings.timeout === undefined ? 60 * 1000 : settings.timeout,
    async = settings.async === undefined ? true : settings.async,
    dataType = settings.dataType === undefined ? "json" : settings.async,
    // 发送请求
     waiting = null;
    if (showWaiting) {
        waiting = loading();
    }
    $.ajax({
    	type: "post",
        url: settings.url,
        data: params,
        timeout: timeout,
        async: async,
        dataType:dataType,
        success: function (result, status) {
            closeLoading();
            if (result.success == true) {
                if (showMsg == true) { // 显示提示信息
                    // 请求成功时的提示文字
                    var successMsg = '操作成功.';
                    if (result.msg && result.msg != '') {
                        successMsg = result.msg;
                    } else if (settings.successMsg && settings.successMsg != '') {
                        successMsg = settings.successMsg;
                    }
                    alertx(successMsg, function () {
                        if (settings.callback) { // 回调方法
                            settings.callback(result);
                        }
                    });
                } else {
                    if (settings.callback) { // 回调方法
                        settings.callback(result);
                    }
                }
            } else if (result.success == false) {
                var message = '发生异常.';
                if (result.msg && result.msg != '') { // 后台设定的业务消息
                    message = result.msg;
                }
                alertx(message);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        	closeLoading();
        	alertx(XMLHttpRequest.responseText);
        }
    });
};

/**
 * map转数组。
 * 
 * @param {Map}map
 *            map对象
 * @return 数组
 */
Share.map2Ary = function (map) {
    var list = [];
    for (var key in map) {
        list.push([key, map[key]]);
    }
    return list;
};
/**
 * 获取map中的值。
 * 
 * @param {String}value
 *            要渲染的值
 * @param {Map}mapping
 *            Map对象
 * @param {String}defaultText
 *            没有对应的key时的默认值
 * @return {String}对应的value值
 */
Share.map = function (value, mapping, defaultText) {
    return mapping[value] || (defaultText === undefined || defaultText === null ? value : defaultText);
};


/** 问候 */
Share.sayHello = function () {
    var hour = new Date().getHours(),
     hello = '';
    if (hour < 6) {
        hello = '凌晨好';
    } else if (hour < 9) {
        hello = '早上好';
    } else if (hour < 12) {
        hello = '上午好';
    } else if (hour < 14) {
        hello = '中午好';
    } else if (hour < 17) {
        hello = '下午好';
    } else if (hour < 19) {
        hello = '傍晚好';
    } else if (hour < 22) {
        hello = '晚上好';
    } else {
        hello = '夜里好';
    }
    return hello + '！';
};

/**
 * 扩展基础类 判断以什么结尾
 */
String.prototype.endsWith = function (suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

/**
 * 扩展基础类 得到字符串的长度，包括中文和英文
 */
String.prototype.charlen = function () {
    var arr = this.match(/[^\x00-\xff]/ig);
    return this.length + (arr == null ? 0 : arr.length);
};

/**
 * 扩展基础类 字符串首尾去空格
 */
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 扩展基础类 字符串包含字符串判断
 */
String.prototype.contains = function (sub) {
    return this.indexOf(sub) != -1;
};

/**
 * 档案明细查看窗口 
 */
function showArchiveInfo(id,category,hostLinkNo){
	var id = id + "";
	var category = category + "";
	var hostLinkNo = hostLinkNo + "";
	var param = "";
	if (id != undefined && id != '') {
		param = "id="+id;
		if (category != undefined && category != '') {
			param += "&category="+category;
		}else{
			param += "&category=1";
		}
	}else{
		if (hostLinkNo != undefined && hostLinkNo != '') {
			param = "hostLinkNo="+hostLinkNo;
		}
	}
	top.$.jBox("iframe:"+ctx+"/ploan/common/toArchiveInfoPage?"+param, {
		id: "archiveInfoBox",
	    title: "档案明细",
	    //top: '30px',
	    width: 1100,
	    height: 550,
	    buttons: {}
	});
}
/**
 * 查看档案生命轨迹
 * @param id
 */
function showTimeLineInfo(id){
	top.$.jBox("iframe:"+ctx+"/ploan/search/timeLine/form?id="+id, {
		id: "timeLineInfoBox",
	    title: "档案轨迹",
	    //top: '30px',
	    width: 1100,
	    height: 550,
	    buttons: {}
	});
}

/**
 * 查看移交单明细
 * @param id 移交单Id
 */
function showTransferDetail(id) {
	top.$.jBox("iframe:"+ctx+"/ploan/transfer/transferList/showDetail?id="+id,{
		id: "showTransferDetail",
		title: "移交单明细",
		width: 1400,
		height: 650,
		buttons: {}
	});
}

/**
 * 标签写入窗口 
 */
function showRfidWrite(id,category,hostLinkNo){
	var id = id + "";
	var category = category + "";
	var hostLinkNo = hostLinkNo + "";
	var param = "";
	if (id != undefined && id != '') {
		param = "id="+id;
		if (category != undefined && category != '') {
			param += "&category="+category;
		}else{
			param += "&category=1";
		}
	}else{
		if (hostLinkNo != undefined && hostLinkNo != '') {
			param = "hostLinkNo="+hostLinkNo;
		}
	}
	top.$.jBox("iframe:"+ctx+"/ploan/common/toRfidWritePage?"+param, {
		id: "rfieWriteBox",
	    title: "标签写入",
	    //top: '30px',
	    width: 1100,
	    height: 580,
	    buttons: {}
	});
}

/**
 * 位置标签写入
 */
function locationRfidWrite(rfid,locationId,locationType){
	var rfid = rfid + "";
	var locationId = locationId + "";
	var locationType = locationType + "";
	var result = {};
	result.success = false;
	if (rfid != undefined && rfid != '') {//已有标签号，重新写入
		if(writeRfid(rfid)){//写入成功
			result.success = true;
			result.updateFlag = 0;
		}else{
			result.success = false;
		}
	}else if (locationId != undefined && locationId != '' && locationType != undefined && locationType != ''){
		Share.Ajax({
    		url: ctx+"/base/location/doLocationRfidWrite",
    		showMsg: false,
    		async: false,//使用同步的方式,true为异步方式
    		params: {
    			id: locationId,
    			type: locationType
    		},
    		callback : function(json) {
    			if(json.success){
    				if(writeRfid(json.o.rfid)){
    					result.success = true;
    					result.updateFlag = 1;
    					result.rfid = json.o.rfid;
    				}else{
    					result.success = false;
    				}
    			}else{
    				result.success = false;
    				alertx(result.msg);
    			}
    		}
    	});
	}
	return result;
}

//-------------标签写入相关
var g_ctrl = null;//rfid连接设备的全局变量
var g_com = "COM1";
var g_baudRate = 115200;
//写rfid
function writeRfid(rfid){
	var flag = false;
	if(connectRfid(16)){
		var r = g_ctrl.RFID_GT_TagInfoWrite(g_com, rfid);
		if(r === 0){
			flag = true;
			alertx('写入标签成功！');
		}else{
			flag = false;
			alertx('写入标签异常!');
		}
		disconnectRfid();
	}
	return flag;
}


function onError(h, errCode){
	alert("Error: " + errCode);
}
//开启连接rfid设备
function connectRfid(rate){
	if (g_ctrl == null){
		g_ctrl = document.getElementById("TEST_ACTIVEX");
		if (g_ctrl.attachEvent){
			var status = g_ctrl.attachEvent("onError", onError);
			status = g_ctrl.attachEvent("onInventoryResult", onInventoryResult);
		}else if(g_ctrl.addEventListener){
			var status = g_ctrl.addEventListener("onError", onError, false);
			status = g_ctrl.addEventListener("onInventoryResult", onInventoryResult, false);
		}
	}
    //停止盘点，断开连接
	g_ctrl.RFID_StopInventory(g_com);
	g_ctrl.RFID_DisconnectRadio(g_com);
	//重新连接设备
	var result = g_ctrl.RFID_ConnectRadio(g_com, g_baudRate);
	if (result === 0){//连接成功
		g_ctrl.RFID_SetAntennaPortConfiguration(g_com, 0, rate, 2000, 8192);
		return true;
	}else{
		result = g_ctrl.RFID_ConnectRadio(g_com, g_baudRate);
		if (result === 0){//连接成功
			g_ctrl.RFID_SetAntennaPortConfiguration(g_com, 0, rate, 2000, 8192);
			return true;
		}else{
			alertx('设备连接异常!');
			return false;
		}
	}
}
//断开rfid设备连接
function disconnectRfid(){
	g_ctrl.RFID_DisconnectRadio(g_com);
}
//停止盘点
function stopInventory(){
	g_ctrl.RFID_StopInventory(g_com);
}
//读取一个标签
function readRfid(){
	var r = "";
	if(connectRfid(16)){
		r = g_ctrl.RFID_GT_TagInfoRead(g_com);
		g_ctrl.RFID_DisconnectRadio(g_com);
	}else{
		r = "-1";
	}
	return r;
}
//开始盘点-连续
function StartInventory(){
	if(connectRfid(16)){
		var r = g_ctrl.RFID_StartInventory(g_com, 1, 1000);
		if(r === 0){//启动盘点成功
			return true;
		}else{
			g_ctrl.RFID_DisconnectRadio(g_com);
			return false;
		}
	}else{
		return false;
	}
}

//停止盘点-连续
function StopInventory(){
	var r = g_ctrl.RFID_StopInventory(g_com);
	//g_ctrl.RFID_DisconnectRadio(g_com);
	if(r === 0){
		return true;
	}else{
		alertx('操作失败!');
		return false;
	}
}

function onInventoryResult(h, jsonTagInfo){}

$(function(){
	//统一绑定所有查询条件中输入框的回车事件来执行查询操作
	$(".form-search input[type='text']").bind('keydown', function(e){
		if (e.keyCode == 13){
			searchForm();
		}
	});
});
//获取窗口尺寸
function pageSize() { 
	var winW, winH;
	if(window.innerHeight) {// all except IE
		winW = window.innerWidth;
		winH = window.innerHeight;
	} else if (document.documentElement && document.documentElement.clientHeight) {// IE 6 Strict Mode
		winW = document.documentElement.clientWidth;
		winH = document.documentElement.clientHeight;
	} else if (document.body) { // other
		winW = document.body.clientWidth;
		winH = document.body.clientHeight;
	}  // for small pages with total size less then the viewport
	return {WinW:winW-3, WinH:winH-5};
}