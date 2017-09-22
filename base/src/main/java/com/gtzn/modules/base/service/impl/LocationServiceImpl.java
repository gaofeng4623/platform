package com.gtzn.modules.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.service.CrudService;
import com.gtzn.common.utils.IdGen;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.modules.base.dao.LocationDao;
import com.gtzn.modules.base.entity.Location;
import com.gtzn.modules.base.service.LocationService;
import com.gtzn.modules.sys.entity.User; 

/**
 * 位置管理Service
* @ClassName: LocationServiceImpl 
* @Description: TODO
* @author guant
* @date 2016年12月19日 上午11:46:31 
*
 */
@Service("location")
@Transactional(readOnly = true)
public class LocationServiceImpl extends CrudService<LocationDao, Location> implements LocationService{
	
	@Autowired
	LocationDao dao;

	/**
	 * 根据id获取location位置对象信息
	* @Title: get 
	* @Description: TODO
	* @param @param id
	* @param @return
	* @return Location
	* @throws
	 */
	public Location get(String id) {
		return super.get(id);
	}
	
	/**
	 * 获取位置列表
	* @Title: findList 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return List<Location>
	* @throws
	 */
	public List<Location> findList(Location location) {
		return super.findList(location);
	}
	
	/**
	 * 获取位置信息列表分页
	* @Title: findPage 
	* @Description: TODO
	* @param @param pager
	* @param @param location
	* @param @param user
	* @param @return
	* @return Pager<Location>
	* @throws
	 */
	public Pager<Location> findPage(Pager<Location> pager, Location location, User user) {
		location.getSqlMap().put("dsf", dataScopeFilter(user, "o", ""));
		location.setPager(pager);
		pager.setList(dao.findPage(location));
		pager.setRecords(dao.findCount(location));
		return pager;
	}
	
	/**
	 * 保存位置对象信息
	* @Title: save 
	* @Description: TODO
	* @param @param location
	* @return void
	* @throws
	 */
	@Transactional(readOnly = false)
	public void save(Location location) {
		//获取当前位置的最大序号
		int num = dao.getSerialNoByLocation(location);
		location.setSerialNo(num+1);
		//处理中文全路径、序号全路径
		String locationName = location.getLocationName().trim();//去空格
		//取当前节点的父节点的父节点             如果 不是根节点拼接位置全路径
		Location loc = this.get(location.getParentid());
		if(!loc.getParentid().equals("0")  && !loc.getParentid().equals("-1")){
			location.setLocationPath(loc.getLocationPath() + ">" + locationName);
			location.setSerialNoPath(loc.getSerialNoPath() + StringUtils.numberToFormatString(num + 1, 3));
		}else{
			location.setLocationPath(locationName);
			location.setSerialNoPath(StringUtils.numberToFormatString(num + 1, 3));
		}
		super.save(location);
	}
	
	/**
	 * 删除位置信息
	* @Title: delete 
	* @Description: TODO
	* @param @param location
	* @return void
	* @throws
	 */
	@Transactional(readOnly = false)
	public int delete(Location location) {
		return super.delete(location);
	}
	
	/**
	 * 获取所有的位置信息
	* @Title: findAllList 
	* @Description: TODO
	* @param @param location
	* @param @param user
	* @param @return
	* @return List<Location>
	* @throws
	 */
	public List<Location> findAllList(Location location, User user){
		location.getSqlMap().put("dsf", dataScopeFilter(user, "o", ""));
		return dao.findAllList(location);
	}

	/**
	 * 保存档案中心对象信息
	* @Title: saveDept 
	* @Description: TODO
	* @param @param location
	* @return void
	* @throws
	 */
	@Override
	@Transactional(readOnly = false)
	public void saveDept(Location location) {
		super.save(location);		
	}	
	
	/**
	 * 获取位置上的档案数量
	* @Title: getArchiveCountByLocaton 
	* @Description: TODO
	* @param @param location
	* @param @return
	* @return long
	* @throws
	 */
	public long getArchiveCountByLocaton(Location location){
		long count = 0L;
		count = dao.getPArcCountByLocaton(location) + dao.getPWarCountByLocaton(location) 
		+ dao.getEArcCountByLocaton(location) + dao.getEWarCountByLocaton(location);
		return count;
	}

	/**
	 * 批量插入保存位置信息
	* @Title: saveLocationList 
	* @Description: TODO
	* @param @param parentLocation 父节点
	* @param @param types
	* @param @param typeNames
	* @param @param nums
	* @return void
	* @throws
	 */
	@Override
	@Transactional(readOnly=false)
	public void saveLocationList(Location parentLocation, List<String> types, List<String> nums, List<String> typeNames) {
		List<Location> parents = Lists.newArrayList();
		List<Location> locationList = Lists.newArrayList(); 
		for(int i = 0; i < types.size(); i++){//架，行，列
			if(i == 0){//第一层节点位置单独处理
				for(int j = 1; j <= Integer.parseInt(nums.get(i)); j++){//3，5，6
					Location location = new Location();
					location.setId(IdGen.nextLongId()+"");
					location.setParent(parentLocation);
					location.setBranchId(parentLocation.getBranchId());
					location.setLocationType(types.get(i));
					//获取当前位置的最大序号
					int serialno = dao.getSerialNoByLocation(location) + j;
					location.setSerialNo(serialno);
					//处理中文全路径、序号全路径
					String locationName = serialno+typeNames.get(i);//去空格
					location.setLocationName(locationName);
					//取当前节点的父节点的父节点             如果 不是根节点拼接位置全路径
					Location loc = this.get(location.getParentid());
					if(!loc.getParentid().equals("0") && !loc.getParentid().equals("-1")){
						location.setLocationPath(loc.getLocationPath() + ">" + locationName);
						location.setSerialNoPath(loc.getSerialNoPath() + StringUtils.numberToFormatString(serialno, 3));
					}else{
						location.setLocationPath(locationName);
						location.setSerialNoPath(StringUtils.numberToFormatString(serialno, 3));
					}
					//加入父节点
					parents.add(location);
					locationList.add(location);
				}
			}else{
				List<Location> tempList = Lists.newArrayList();
				tempList = batchInsert(parents, types.get(i), Integer.parseInt(nums.get(i)), typeNames.get(i));
				locationList.addAll(tempList);
				//刚产生的节点同时为下一次循环的父节点
				parents = tempList;
			}
		}
		for(Location location : locationList){
			dao.insert(location);
			//System.out.println(location.getLocationName()+",全路径="+location.getLocationPath()+"，序列号="+location.getSerialNoPath()+",父节点="+location.getParentid());
		}
		//System.out.println(locationList.size());
	}
	
	/**
	 * 根据父节点位置构造下级节点位置
	* @Title: batchInsert 
	* @Description: TODO
	* @param @param parents
	* @param @param type
	* @param @param num
	* @param @param typeName
	* @param @return
	* @return List<Location>
	* @throws
	 */
	private List<Location> batchInsert(List<Location> parents, String type, int num, String typeName){
		List<Location> list = Lists.newArrayList();
		for(int i=0;i<parents.size();i++){
			for(int j=1;j<=num;j++ ){
				Location l = new Location();
				l.setId(IdGen.nextLongId()+"");
				l.setParent(parents.get(i));
				l.setLocationType(type);
				l.setBranchId(parents.get(i).getBranchId());
				l.setSerialNo(j);
				l.setLocationName(j+typeName);
				l.setLocationPath(parents.get(i).getLocationPath() + ">" + j+typeName);
				l.setSerialNoPath(parents.get(i).getSerialNoPath() + StringUtils.numberToFormatString(j, 3));
				list.add(l);
			}
		}
		return list;
	}
	
	/**
	 * 查询当前节点的孩子节点数据
	* @Title: findChildDataList 
	* @Description: TODO
	* @param @param location
	* @param @param user
	* @param @return
	* @return List<Location>
	* @throws
	 */
	public List<Location> findChildDataList(Location location, User user){
		location.getSqlMap().put("dsf", dataScopeFilter(user, "o", ""));
		List<Location> list =  dao.findChildDataList(location);
		for(Location temp : list){
			temp.setIsParent(true);
		}
		return list;
		
	}
	
	/**
	 * 更新位置rfid标签
	* <p>Title: doLocationRfidWrite</p> 
	* <p>Description: </p> 
	* @param id
	* @param type
	* @return
	 */
	@Override
	@Transactional(readOnly = false)
	public String doLocationRfidWrite(String id, String type) {
		// TODO Auto-generated method stub
		String rfid = type + IdGen.rfid() + "0000";
		dao.updateLocationRfidById(id, rfid);
		return rfid;
	}

}