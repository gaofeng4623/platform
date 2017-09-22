package com.gtzn.modules.workflow.domain;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gtzn.common.utils.JsonUtil;

public class MM {

	
	/*{"label":"姓名","field_type":"text","required":true,
		 * "field_options":{"size":"small","description":"请输入名"},"cid":"c18"},{"label":"年龄","field_type":"number","required":true,"field_options":{"description"
		:"请输入年龄"},"cid":"c26"}*/
	public static void main(String[] args) {
		/*String s = "[{\"label\":\"姓名\",\"field_type\":\"text\",\"required\":true,"+
				"\"field_options\":{\"size\":\"small\",\"description\":\"请输入名\"},\"cid\":\"c18\"},{\"label\":\"年龄\",\"field_type\":\"number\",\"required\":true,\"field_options\":{\"description\""+
				":\"请输入年龄\"},\"cid\":\"c26\"}]";*/
		
		String s = "[{\"label\":\"姓名\",\"field_type\":\"text\",\"required\":true,"+
		 " \"field_options\":{\"size\":\"small\",\"description\":\"请输"+
		"入名\"},\"cid\":\"c18\"},"+
		"{\"label\":\"年龄\",\"field_type\":\"number\",\"required\":true,\"field_options\":{\"description\""+
		":\"请输入年龄\"},\"cid\":\"c26\"},"+
		"{\"label\":\"生日\",\"field_type\":\"date\",\"required\":true,\"field_options\":{\"description\""+
		":\"请输入生日\"},\"cid\":\"c30\"},"+
		"{\"label\":\"爱好\",\"field_type\":\"checkboxes\",\"required\":true,\"field_options\":{\"options\""+
		":[{\"label\":\"游泳\",\"checked\":false},{\"label\":\"跑步\",\"checked\":false}],\"description\":\"请选择爱好\"},\"cid\":\"c34\"}"+
		",{\"label\":\"性别1\",\"field_type\":\"radio\",\"required\":true,\"field_options\":{\"options\":[{\"label\":\"男\",\"checked\""+
		":true},{\"label\":\"女\",\"checked\":false}],\"description\":\"请选择性别\"},\"cid\":\"c38\"},"+
		"{\"label\":\"职业\",\"field_type\""+
		":\"dropdown\",\"required\":true,\"field_options\":{\"options\":[{\"label\":\"教师\",\"checked\":true},{\"label\":\"工人\",\"checked\""+
		":false}],\"include_blank_option\":true,\"description\":\"请选择职业1\"},\"cid\":\"c42\"}]";
		System.out.println(s);
		List<Field> list = JsonUtil.fromJson(s, new TypeReference<List<Field>>() {});
		
		int a= 0;

	}

}
