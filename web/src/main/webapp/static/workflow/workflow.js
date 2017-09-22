function graphTrace(options) {
	 var _defaults = {
        srcEle: this,
        processInstanceId: $(this).attr('processInstanceId'),
	    processDefinitionId: $(this).attr('processDefinitionId'),
	    w: 1100,
	    h: 600
    };
    var opts = $.extend(true, _defaults, options);
	var url = ctx + '/diagram-viewer/index.html?processDefinitionId=' + opts.processDefinitionId + '&processInstanceId=' + opts.processInstanceId;
	
	top.$.jBox("iframe:"+url, { 
	    title: "绿色标记当前的节点", 
	    width: 1200, 
	    height: 600,
	    buttons: {}
	});
}
