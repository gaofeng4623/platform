package com.gtzn.modules.home.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.service.BaseService;
import com.gtzn.modules.home.entity.Stati;
import com.gtzn.modules.home.dao.HomeDao;
import com.gtzn.modules.home.service.HomeService;
import com.gtzn.modules.sys.entity.User;

/**
 * 桌面接口实现类
* @ClassName: HomeServiceImpl 
* @Description: TODO
* @author guant
* @date 2016年12月19日 上午9:56:48 
*
 */
@Service("homeService")
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private HomeDao homeDao;
	
	/**
	 * 获取用户首页统计模块列表
	* <p>Title: getStaticList</p> 
	* <p>Description: </p> 
	* @param user
	* @return 
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Stati> findStaticCount(User user) {
		// TODO Auto-generated method stub
		//获取用户所具有的统计模块key
		List<String> staticKeyList = this.getStaticKeyList(user);
		List<Stati> statiList = new ArrayList<Stati>();
		if(staticKeyList.size() > 0){
			statiList = homeDao.findUserStatiList(staticKeyList);
		}
		return statiList;
	}
	
	/**
	 * 获取用户首页统计模块列表
	* <p>Title: getStaticList</p> 
	* <p>Description: </p> 
	* @param user
	* @return 
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Stati> findStaticList(User user) {
		// TODO Auto-generated method stub
		List<String> staticKeyList = this.getStaticKeyList(user);
		List<Stati> statiList = new ArrayList<Stati>();
		if(staticKeyList.size() > 0){
			//获取用户所具有的统计模块信息列表
			statiList = homeDao.findUserStatiList(staticKeyList);
			for (Stati stati : statiList) {
				if(stati.getStatiKey().equals("curStore")){//当前库存
					stati.setCount(homeDao.findLibArchiveCount(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("borrowNum")){//借阅数量
					stati.setCount(homeDao.findBorrowArchiveCount(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("transferNum")){//移交数量
					stati.setCount(homeDao.findTransArchiveCount(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("destroyNum")){//销毁数量
					stati.setCount(homeDao.findDestroyCount(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("waitUpShelfNum")){//库管待上架
					stati.setCount(homeDao.findReadyShelfCount(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("storeRoomReadyReceiveNum")){//库管待接收
					stati.setCount(homeDao.findStoreReadyReceiveCount(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("borrowWaitOutNum")){//借阅待出库
					stati.setCount(homeDao.findWaitOutCount(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("borrowWaitDeliveryNum")){//借阅待交付
					stati.setCount(homeDao.findWaitDeliveryCount(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("receiveArcNum")){//接收档案数量
					stati.setCount(homeDao.findReceiveArcNum(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("receiveBackArcNum")){//接收退回档案数量
					stati.setCount(homeDao.findReceiveBackNum(BaseService.dataScopeFilter(user, "o", ""))); 
				} else if(stati.getStatiKey().equals("verifyArcNum")){//审核通过档案数量
					stati.setCount(homeDao.findVerifyPassArcNum(BaseService.dataScopeFilter(user, "o", ""),user.getId())); 
				} else if(stati.getStatiKey().equals("verifyBackArcNum")){//审核退回档案数量
					stati.setCount(homeDao.findVerifyBackArcNum(BaseService.dataScopeFilter(user, "o", ""),user.getId())); 
				}
			}
		}
		return statiList;
	}

	/**
	 * 获取用户所具有的统计模块key列表
	* @Title: getStatiKeyList 
	* @Description: TODO
	* @param @param user
	* @param @return
	* @return List<String>
	* @throws
	 */
	public List<String> getStaticKeyList(User user){
		//获取用户所具有的统计模块key
		List<String> staticKeyList = new ArrayList<String>();
		if(null!=user &&null!=user.getRoleIdList() &&user.getRoleIdList().size() > 0){
			List<String> staticIds = homeDao.findUserStatiIds(user.getRoleIdList());
			for (String str : staticIds) {
				String[] stati = str.split(",");
				for (String s : stati) {
					if(!s.equals("")){
						if(!staticKeyList.contains(s)){
							staticKeyList.add(s);
						}
					}
				}
			}
		}
		return staticKeyList;
	}
}
