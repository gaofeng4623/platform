/**
 * 
 */
package com.gtzn.modules.home;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gtzn.common.web.BaseController;
import com.gtzn.modules.digital.entity.ArchiveClass;
import com.gtzn.modules.digital.entity.DStatus;
import com.gtzn.modules.digital.entity.Dictionaries;
import com.gtzn.modules.digital.entity.PlatStatistics;
import com.gtzn.modules.digital.entity.PlatUnit;
import com.gtzn.modules.digital.entity.Retention;
import com.gtzn.modules.digital.service.ArchiveClassService;
import com.gtzn.modules.digital.service.DStatusService;
import com.gtzn.modules.digital.service.DictionariesService;
import com.gtzn.modules.digital.service.PlatStatisticsService;
import com.gtzn.modules.digital.service.PlatUnitService;
import com.gtzn.modules.digital.service.RetentionService;
@Controller
@RequestMapping(value = "/usingStatistics")
public class UsingStatisticsController extends BaseController {
	@Autowired
	private  ArchiveClassService archiveClassService;
	@Autowired
	private  PlatStatisticsService platStatisticsService;
	@Autowired
	private DictionariesService dictionariesService;
	@Autowired
	private PlatUnitService platUnitService;
	@Autowired   
	private DStatusService dStatusService;
	@Autowired   
	private RetentionService retentionService;
/**
 * 首页数据导入
 */
/*	@RequestMapping(value = "homePage")
*/	public void homePage() {
		// 初始化数据
		dictionariesService.updateList();
		// 查询所有大类 如婴儿出生证明，设备档案 等等
		List<ArchiveClass> arc = archiveClassService.queryArchiveClassList();
		if (arc != null && !"".equals(arc)) {
			for (int i = 0; i < arc.size(); i++) {
				// 根据大类id查询出那些表存放实体档案
				Map<String, Object> map = new HashMap<>();
				map.put("classId", arc.get(i).getId());
				map.put("fieldpro", "实体份数");
				// 根据实体份数去统计实体档案的数量
				List<ArchiveClass> tore = archiveClassService.toreList(map);
				if (tore != null && !"".equals(tore)) {
					for (int z = 0; z < tore.size(); z++) {
						Map m2 = new HashMap();
						// 得到表名
						m2.put("d1", tore.get(z).getToreTblname());
						m2.put("st", "st");
						// 判断是否为空
						int count = archiveClassService.countIsNull(m2);
						if (count != 0) {
							Map m = new HashMap();
							m.put("fieldname", tore.get(z).getFieldname());
							m.put("d", tore.get(z).getToreTblname());
							String count2 = archiveClassService.countD(m);
							if (count2 != null) {
								this.saveAndUpdat(arc.get(i).getName(), count2,"st");
							}

						}
					}
				}
				Map classMap = new HashMap();
				classMap.put("classId",arc.get(i).getId());
				List<ArchiveClass> toreIdea = archiveClassService.toreIdeaList(classMap);
				if (toreIdea != null && !"".equals(toreIdea)) {
					for (int y = 0; y < toreIdea.size(); y++) {
						Map m2 = new HashMap();
						// 得到表名
						m2.put("d", toreIdea.get(y).getToreTblname());
						m2.put("d1", toreIdea.get(y).getToreTblname() + "_FILE");
						
						int count = archiveClassService.counNd(m2);
						if (count != 0) {
							saveAndUpdat(arc.get(i).getName(),count+"","dz");

						}
					}
				}
			}
		}

	}
	public void saveAndUpdat(String name,String count2,String type){
		Dictionaries diction=dictionariesService.selectByName(name);
			Dictionaries dic=new Dictionaries();
			if(diction==null){
				dic.setTitle(name);
				dic.setEntitiesNo(0);
				dic.setElectronicsNo(0);
				dic.setIsNewRecord(true);
				dictionariesService.save(dic);
			}else{
				if("st".equals(type)){
					int st=diction.getEntitiesNo();
					int count=st+Integer.parseInt(count2);
					dic.setEntitiesNo(count);
				}else{
					int dz=diction.getElectronicsNo();
					int count=dz+Integer.parseInt(count2);
					dic.setElectronicsNo(count);
				}
			
				dic.setTitle(name);
				dictionariesService.updateDic(dic);
			}
			
	}
/**
 * 馆藏数量(年度)
 */
	    @RequestMapping(value = "yearStatistics")
		public void yearStatistics(){
	    	//先得到大类
	    	platStatisticsService.updateList();
			List<ArchiveClass> arc=archiveClassService.queryArchiveClassList();
			if(arc!=null&&!"".equals(arc)){
				for(int i=0;i<arc.size();i++){
					Map<String, Object> map = new HashMap<>();
					map.put("classId", arc.get(i).getId());
					map.put("nd","年度");
					map.put("stfs","实体份数");
					//得到所有d表
					List<ArchiveClass> tore=archiveClassService.toreUnitList(map);
					if(tore!=null&&!"".equals(tore)){
						for(int z=0;z<tore.size();z++){
								Map m2 = new HashMap();
								//得到表名
								m2.put("d1", tore.get(z).getToreTblname());
								//判断是否为空
								m2.put("st","st");
								int count=archiveClassService.countIsNull(m2);
								if(count!=0){
									if( "实体份数".equals(tore.get(z).getFieldpro())){
										Map m = new HashMap();
										m.put("fieldname", tore.get(z).getFieldname());
										m.put("d", tore.get(z).getToreTblname());
										m.put("entityNo","实体");
										//根据年度分组得到一个总数
										List<ArchiveClass> nd=archiveClassService.ListNd(m);
										 if(nd.size()>0){
											 for(int f=0;f<nd.size();f++){
												 if(nd.get(f)!=null){
													 if(!"".equals(nd.get(f).getNd())){
														 if("".equals(nd.get(f).getCountEntityNo())||null==nd.get(f).getCountEntityNo()){
															 count=0; 
														 }else{
															 count=nd.get(f).getCountEntityNo();
														 }
														 if(nd.get(f).getNd()!=null&&!"".equals(nd.get(f).getNd())){
															 saveAndUpdatNd(nd.get(f).getNd(),arc.get(i).getName(),count,"st");
														 }
													 }
												 }
												
											 }
										 }
									}
									
									}
						}
					}
					
					map.put("datype","一文一件");
					//得到所有d表电子表
					List<ArchiveClass> toreIdea=archiveClassService.toreUnitList(map);
					if(toreIdea!=null&&!"".equals(toreIdea)){
						
						for(int y=0;y<toreIdea.size();y++){
							if("实体份数".equals(tore.get(y).getFieldpro())){
								Map m2 = new HashMap();
								m2.put("dz","dz");
								m2.put("d", toreIdea.get(y).getToreTblname());
								m2.put("d1", toreIdea.get(y).getToreTblname() + "_FILE");
								int count = archiveClassService.counNd(m2);
								if(count>0){
									Map mz = new HashMap();
									mz.put("startName", arc.get(i).getName());
									List<PlatStatistics> plat=platStatisticsService.queryNdList(mz);//得到大类有那些年度
									if(plat.size()>0){
										for(int tt=0;tt<plat.size();tt++){
											m2.put("nd",plat.get(tt).getYear());
											 int countNd=archiveClassService.counNd(m2);
											 PlatStatistics p=new PlatStatistics();
											    p.setYear(plat.get(tt).getYear());
												p.setStartName(plat.get(tt).getStartName());
												p.setElectronicsNo(countNd+plat.get(tt).getElectronicsNo());
												platStatisticsService.updateSt(p);
										}
									}
								}
							}
						}	
					}
				}
			}
		}
		public void saveAndUpdatNd(String year,String StartName,int count,String type){
			 Map<String, Object> mapNd = new HashMap<>();
			 mapNd.put("startName",StartName);
			 mapNd.put("year",year);
			 PlatStatistics staitc=platStatisticsService.queryNd(mapNd);
			 PlatStatistics plat=new PlatStatistics();
			 if(staitc!=null){
				plat.setYear(year);
				plat.setStartName(StartName);
				plat.setEntityNo(staitc.getEntityNo()+count);
				platStatisticsService.updateSt(plat);
				 
			 }else{
				    plat.setYear(year);
					plat.setStartName(StartName);
					plat.setEntityNo(count);
					plat.setIsNewRecord(true);
					 platStatisticsService.save(plat);
				 
				 
			 }
		 }
				
		
/**
 * 根据立档单位导入数据  
 */
	    @RequestMapping(value = "archivesFonds") 
		public void archivesFonds(){
	    	//先得到大类
	    	platUnitService.updateList();
	    	Map<String, Object> map = new HashMap<>();
			map.put("nd","年度");
			map.put("qzh","全宗号");
			map.put("stfs","实体份数");
	    	//根据年度全宗号，实体份数得到所有d 表
			List<ArchiveClass> tore=archiveClassService.toreUnitList(map);
			
			if(tore!=null&&!"".equals(tore)){
				Map<String, Object> mNd = new HashMap<>();
				for(int i=0;i<tore.size();i++){
					if("年度".equals(tore.get(i).getFieldpro())){
						mNd.put("nd",tore.get(i).getFieldname());
					}
					if("实体份数".equals(tore.get(i).getFieldpro())){
						mNd.put("ztsl",tore.get(i).getFieldname());
						mNd.put("d",tore.get(i).getToreTblname());
						//查询所有立档单位
						List<ArchiveClass> unitList=archiveClassService.queryUnitList();
						for(int z=0;z<unitList.size();z++){
							mNd.put("qzh",unitList.get(z).getUnitCode());
							mNd.put("datype","st");
							//按照年度分组得到分组的年度和总合
							List<ArchiveClass> arc=archiveClassService.countUnitList(mNd);
							if(arc!=null||!"".equals(arc)){
								 for(int x=0;x<arc.size();x++){
									 if(arc.get(x)!=null){
										 saveAndUpdatFond(arc.get(x).getNd(),unitList.get(z).getUnitName(),arc.get(x).getDcount(),unitList.get(z).getUnitCode(),"st");

										
									 }
									
								 }
							}
							 
						}
						
					}
					

				}
			
			}
			//根据年度全宗号，年度，
			map.put("datype","电子");
			List<ArchiveClass> tore2=archiveClassService.toreUnitList(map);
			if(tore2!=null&&!"".equals(tore2)){
				for(int y=0;y<tore2.size();y++){
					if("实体份数".equals(tore.get(y).getFieldpro())){
						List<PlatUnit> platu=platUnitService.platList();
						for(PlatUnit p: platu){
							Map m2 = new HashMap();
							m2.put("d", tore2.get(y).getToreTblname());
							m2.put("d1", tore2.get(y).getToreTblname()+"_FILE");
							m2.put("nd",p.getYear());
							m2.put("qzh",p.getUnitCode());
							int count=archiveClassService.counNd(m2);
							 saveAndUpdatFond(p.getYear(),p.getUnitName(),count+"",p.getUnitCode(),"dz");

						}
						
					}
				}
				
				
				
			}
			 
		}
	    public void saveAndUpdatFond(String year,String unitName,String count,String unitCode,String type){
	    	 PlatUnit plat=new  PlatUnit();
			 plat.setYear(year);
			 plat.setUnitName(unitName);
			 Map<String, Object> platMap = new HashMap<>();
			 platMap.put("unitName",unitName);
			 platMap.put("year",year);
			 if(year!=null&&!"".equals(year)){
				 PlatUnit unit=platUnitService.selectByName(platMap);
				 if(unit!=null){
					 if(count!=null){
						 if("dz".equals(type)){
							 plat.setElectronicsNo(unit.getElectronicsNo()+Integer.parseInt(count));
						 }else{
							 plat.setEntityNo(unit.getEntityNo()+Integer.parseInt(count));
						 }
						
						 platUnitService.updateSt(plat);

					 }
				 } else{
					 plat.setEntityNo(0);
					 plat.setIsNewRecord(true);
					 plat.setUnitCode(unitCode);
					 platUnitService.save(plat);
				 }
			 }
			
		 }
	    /**
	     * 统计上架未上架
	     * @return
	     */
	    @RequestMapping(value = "inLibrary")
	    public void inLibrary(){
	    	dStatusService.updateAll();
	    	//查询所有d表
	    	List<ArchiveClass> toreIdea=archiveClassService.toreIdeaList(new HashMap<>());
	    	//查询所有立档单
			List<ArchiveClass> unitList=archiveClassService.queryUnitList();
	    	if(toreIdea!=null){
	    		for(int i=0;i<toreIdea.size();i++){
	    			Map isNull = new HashMap();
	    			isNull.put("d1",toreIdea.get(i).getToreTblname());
					//判断是否为空
	    			isNull.put("st","st");
					int countNull=archiveClassService.countIsNull(isNull);
					if(countNull!=0){
	    			for(ArchiveClass str:unitList){
							int count=dstatus(toreIdea.get(i).getToreTblname(),str,"zk");
		    				int count2=dstatus(toreIdea.get(i).getToreTblname(),str,"wzk");
		    				DStatus ds=new DStatus();
			    			ds.setUnitName(str.getUnitName());
			    			DStatus dst=dStatusService.selectByName(str.getUnitName());
			    			if(dst!=null){
			    				int library=dst.getInLibrary();
			    				int issue=dst.getIssue();
			    				ds.setInLibrary(library+count);
			    				ds.setIssue(issue+count2);
			    				dStatusService.updateDstatus(ds);
			    			}else{
			    				if(count!=0||count2!=0){
			    					ds.setInLibrary(count);
					    			ds.setIssue(count2);
					    			ds.setIsNewRecord(true);
				    				dStatusService.save(ds);
			    				}
			    				
			    			}
						}
	    			}
	    			
	    		}
	    	}
	    }
	    
	    public int dstatus(String tabName,ArchiveClass arc,String zk){
	    	Map m2 = new HashMap();
			m2.put("d",tabName);
			m2.put("qzh", arc.getUnitCode());
			m2.put("zk",zk);
			int count=archiveClassService.Librarycoun(m2);
			return count;
	    	
	    }
	    /**
	     * 统计保管期限
	     * @return
	     */
	    @RequestMapping(value = "storagePeriod")
	    public void storagePeriod(){
	    	retentionService.updateAll(1);
	    	Map<String, Object> map = new HashMap<>();
			map.put("bgqx","保管期限");
			map.put("stfs","实体份数");
			Map<String, Object> map2 = new HashMap<>();
			Map<String, Object> mapbgqx = new HashMap<>();
			Map<String, Object> mapCount = new HashMap<>();
			mapbgqx.put("type","bgqx");
	    	//查询所有d表
	    	List<ArchiveClass> toreIdea=archiveClassService.toreUnitList(map);
			List<ArchiveClass> dic=archiveClassService.dict(mapbgqx);
	    	if(toreIdea!=null){
	    		for(int i=0;i<toreIdea.size();i++){
	    			if(dic.size()>0){
	    				map2.put("d1",toreIdea.get(i).getToreTblname());
    					int count=archiveClassService.countIsNull(map2);
    					if(count>0){
    						
    							if(dic.size()>0){
    								if("保管期限".equals(toreIdea.get(i).getFieldpro())){
    									mapCount.put("file",toreIdea.get(i).getFieldname());
    								}else{
    									for(int y=0;y<dic.size();y++){
    									mapCount.put("ztsl",toreIdea.get(i).getFieldname());
    									mapCount.put("d",toreIdea.get(i).getToreTblname());
        								mapCount.put("fileName",dic.get(y).getLable());
        								String count2=archiveClassService.bgqxCount(mapCount);
        								if(count2!=null){
        									Map<String, Object> map3 = new HashMap<>();
                							map3.put("file","retention");
                							map3.put("fileName",dic.get(y).getLable());
                							map3.put("type","1");
                							Retention re=retentionService.queryName(map3);
                							Retention reten=new Retention();
                							if(re!=null){
                								reten.setRetention(dic.get(y).getLable());
                								reten.setType(1);
                								reten.setEntitiesNo(Integer.parseInt(count2)+re.getEntitiesNo());
                								retentionService.updateName(reten);
                							}else{
                							
                								reten.setRetention(dic.get(y).getLable());
                								reten.setEntitiesNo(Integer.parseInt(count2));
                								reten.setType(1);
                								reten.setIsNewRecord(true);
                								retentionService.save(reten);
                							}	
        								}
        									
    								}
    								
        							
    							}
   							 
    	    				}
    						
    						
    					}
	    				
	    				
	    			}
	    		
	    			
	    		}
	    	}
	    	map.put("datype","dz");
	    	//查询所有d表
	    	List<ArchiveClass> tordz=archiveClassService.toreUnitList(map);
	    	for(int y=0;y<tordz.size();y++){
	    		Map<String, Object> mbqqx = new HashMap<>();
	    		mbqqx.put("d",tordz.get(y).getToreTblname());
	    		mbqqx.put("d1",tordz.get(y).getToreTblname()+"_FILE");
	    		mbqqx.put("file",tordz.get(y).getFieldname());
	    		for(int z=0;z<dic.size();z++){
	    			if("保管期限".equals(toreIdea.get(y).getFieldpro())){
						mbqqx.put("fileName",dic.get(z).getLable());
			    		String dzCount=archiveClassService.bgqxDzCount(mbqqx);
			    		if(dzCount!=null){
			    			Map<String, Object> map3 = new HashMap<>();
							map3.put("file","retention");
							map3.put("fileName",dic.get(z).getLable());
							map3.put("type","1");
							Retention re=retentionService.queryName(map3);
							Retention reten=new Retention();
							if(re!=null){
								reten.setRetention(dic.get(z).getLable());
								reten.setType(1);
								reten.setElectronicsNo(Integer.parseInt(dzCount)+re.getElectronicsNo());
								retentionService.updateName(reten);
							}else{
							
								reten.setRetention(dic.get(z).getLable());
								reten.setElectronicsNo(Integer.parseInt(dzCount));
								reten.setType(1);
								reten.setIsNewRecord(true);
								retentionService.save(reten);
							}	
			    		}
					}
		    	
	    		}
	    		
	    	}
	    }
	    /**
	     * 密集
	     * @return
	     */
	    @RequestMapping(value = "dense")
	    public void dense(){
	    	
	    	retentionService.updateAll(2);
	    	Map<String, Object> map = new HashMap<>();
			//map.put("bgqx","密级");
			map.put("stfs","实体份数");
			Map<String, Object> map2 = new HashMap<>();
			Map<String, Object> mapbgqx = new HashMap<>();
			Map<String, Object> mapCount = new HashMap<>();
			mapbgqx.put("type","mj");
	    	//查询所有d表
	    	List<ArchiveClass> toreIdea=archiveClassService.toreUnitList(map);
			List<ArchiveClass> dic=archiveClassService.dict(mapbgqx);
	    	if(toreIdea!=null){
	    		for(int i=0;i<toreIdea.size();i++){
	    			if(dic.size()>0){
	    				map2.put("d1",toreIdea.get(i).getToreTblname());
    					int count=archiveClassService.countIsNull(map2);
    					if(count>0){
    						
    							if(dic.size()>0){
    									for(int y=0;y<dic.size();y++){
    									mapCount.put("file","mj");
    									mapCount.put("ztsl",toreIdea.get(i).getFieldname());
    									mapCount.put("d",toreIdea.get(i).getToreTblname());
        								mapCount.put("fileName",dic.get(y).getLable());
        								String count2=archiveClassService.bgqxCount(mapCount);
        								if(count2!=null){
        									Map<String, Object> map3 = new HashMap<>();
                							map3.put("file","retention");
                							map3.put("fileName",dic.get(y).getLable());
                							map3.put("type","2");
                							Retention re=retentionService.queryName(map3);
                							Retention reten=new Retention();
                							if(re!=null){
                								reten.setRetention(dic.get(y).getLable());
                								reten.setType(2);
                								reten.setEntitiesNo(Integer.parseInt(count2)+re.getEntitiesNo());
                								retentionService.updateName(reten);
                							}else{
                							
                								reten.setRetention(dic.get(y).getLable());
                								reten.setEntitiesNo(Integer.parseInt(count2));
                								reten.setType(2);
                								reten.setIsNewRecord(true);
                								retentionService.save(reten);
                							}	
        								}
        									
    								}
    									
    	    				}
    						
    						
    					}
	    				
	    				
	    			}
	    		
	    			
	    		}
	    	}
	    	map.put("datype","dz");
	    	//查询所有d表
	    	List<ArchiveClass> tordz=archiveClassService.toreUnitList(map);
	    	for(int y=0;y<tordz.size();y++){
	    		Map<String, Object> mbqqx = new HashMap<>();
	    		mbqqx.put("d",tordz.get(y).getToreTblname());
	    		mbqqx.put("d1",tordz.get(y).getToreTblname()+"_FILE");
	    		mbqqx.put("file","mj");
	    		for(int z=0;z<dic.size();z++){
						mbqqx.put("fileName",dic.get(z).getLable());
			    		String dzCount=archiveClassService.bgqxDzCount(mbqqx);
			    		if(dzCount!=null){
			    			Map<String, Object> map3 = new HashMap<>();
							map3.put("file","retention");
							map3.put("fileName",dic.get(z).getLable());
							map3.put("type","2");
							Retention re=retentionService.queryName(map3);
							Retention reten=new Retention();
							if(re!=null){
								reten.setRetention(dic.get(z).getLable());
								reten.setType(2);
								reten.setElectronicsNo(Integer.parseInt(dzCount)+re.getElectronicsNo());
								retentionService.updateName(reten);
							}else{
							
								reten.setRetention(dic.get(z).getLable());
								reten.setElectronicsNo(Integer.parseInt(dzCount));
								reten.setType(2);
								reten.setIsNewRecord(true);
								retentionService.save(reten);
							}	
			    		}
		    	
	    		}
	    		
	    	}
	    }
	    @RequestMapping(value = "yj")
	    public void yj(){
	    	List<ArchiveClass> tordz=archiveClassService.transfer();
			if(tordz.size()>0){
	    		for(ArchiveClass tran:tordz){
	    			DStatus dstatus=new DStatus();
	    			dstatus.setUnitName(tran.getOutunit());
	    			dstatus.setInLibrary(tran.getOutCount());
	    			DStatus dsName=dStatusService.selectByName(tran.getOutunit());
	    			if(dsName!=null){
	    				dStatusService.updateDstatus(dstatus);
	    			}else{
	    				dstatus.setIsNewRecord(true);
	    				dStatusService.save(dstatus);
	    			}
	    		}
	    	}
	    }
	    
}