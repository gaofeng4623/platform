package com.gtzn.modules.sys.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gtzn.common.persistence.DataEntity;

public class Example extends DataEntity<Example> {

	/**
	 * 例子
	 */
	private static final long serialVersionUID = 1L;
	
	private String guid;
	
	private String name;//姓名
	
	private int age;//年龄
	
	private Date birthDay;//生日

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
}
