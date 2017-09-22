package com.gtzn.modules.sys.service;

import java.util.List;

import com.gtzn.modules.sys.entity.Office;
import com.gtzn.modules.sys.entity.User;

public interface OfficeService {

	public abstract List<Office> findAll();
	
	public abstract List<Office> findList(User user);

	public abstract List<Office> findList(Office office, User user);

	public abstract void save(Office office);

	public abstract int delete(Office office);
	
	public Office get(String id);
}