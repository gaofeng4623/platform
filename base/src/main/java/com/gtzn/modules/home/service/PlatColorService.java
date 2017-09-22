/**
 *
 */
package com.gtzn.modules.home.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gtzn.modules.home.entity.PlatColor;

public interface PlatColorService {
	public void save(PlatColor platColor);
	PlatColor selectTypeId(@Param("m") Map m);
}