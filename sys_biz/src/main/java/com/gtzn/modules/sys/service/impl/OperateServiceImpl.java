/**
 *
 */
package com.gtzn.modules.sys.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.modules.sys.dao.OperateDao;
import com.gtzn.modules.sys.entity.Operate;
import com.gtzn.modules.sys.service.OperateService;

/**
 * 操作日志Service
 * 
 * @author 姜帅
 * @version 2016-11-29
 */
@Service("operateService")
@Transactional(readOnly = true)
public class OperateServiceImpl extends CrudService<OperateDao, Operate> implements OperateService {

	@Autowired
	private OperateDao operateDao;

	ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	@Override
	public Pager<Operate> findPage(Pager<Operate> pager, Operate operate) {
		return super.findPage(pager, operate);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Operate operate) {
		// 异步保存日志
		cachedThreadPool.execute(new SaveThread(operate, operateDao));
	}

	/**
	 * 保存日志线程
	 */
	public static class SaveThread implements Runnable {

		private Operate operate;

		private OperateDao operateDao;
		
		public SaveThread(Operate operate, OperateDao operateDao) {
			this.operate = operate;
			this.operateDao = operateDao;
		}

		@Override
		public void run() {
			this.operate.preInsert();
			this.operateDao.insert(this.operate);
		}
	}

}