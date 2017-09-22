package com.gtzn.modules.digital.service;

import java.util.List;
import java.util.Map;

import com.gtzn.modules.digital.entity.Dictionaries;

public interface DictionariesService  {
	Map<String, Object> queryDictionariesList();
	public void save(Dictionaries platInformation);//往mysql 导入大类
	public Dictionaries selectByName(String name);//根据大类查询
	int updateDic(Dictionaries dic);
	/**
	 * 初始化数据
	 * @return
	 */
	int updateList();
}
