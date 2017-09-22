/**
 *
 */
package com.gtzn.modules.base.service.impl;

import com.gtzn.modules.base.service.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 车辆管理Service
 * @author gf
 * @version 2017-02-28
 */
@Service("car2")
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {


	@Override
	public String getResult() {
		return "猫咪快跑";
	}
}