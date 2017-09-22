package com.gtzn.modules.platAlarm.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.digital.service.AlarmService;
import com.gtzn.modules.home.entity.PlatAlarm;
import com.gtzn.modules.home.service.PlatAlarmService;
import com.gtzn.modules.monitor.entity.Alarm;

/**
 * 安全异常信息Controller
 * @author wangzhao
 * @version 2017-04-17
 */
@Controller
@RequestMapping(value = "/platAlarm")
public class AlarmController extends BaseController {

	@Autowired
	private AlarmService alarmService;
	
    //private WebService alarmService = SpringContextHolder.getBean("alarmService"); //报警服务
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat monthDay = new SimpleDateFormat("MM-dd");
    private SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
    private SimpleDateFormat yearMonth = new SimpleDateFormat("yyyy-MM");
    
    @Autowired
    private PlatAlarmService platAlarmService;
	/**
	 * 页面跳转---安全报警列表页
	 */
	@RequestMapping(value = {"toAlarmView"})
	public String toAlarmView(Model model) {
		List<Map<String, Object>> roomInfo = alarmService.getRoomInfo();
		model.addAttribute("roomInfo", roomInfo);
		return "modules/platAlarm/alarmView";
	}
	
	/**
	 * 页面跳转---安全报警首页
	 */
	@RequestMapping(value = {"toAlarmIndex"})
	public String toAlarmView2() {
		return "modules/platAlarm/alarmIndex";
	}
	
	/**
	 * 页面跳转---安全报警列表页
	 */
	@RequestMapping(value = {"list"})
	public String list(Alarm alarm, Model model) {
		model.addAttribute("dataInfo", alarm);
		return "modules/platAlarm/alarmList";
	}
	
	@RequestMapping(value = {"listComputer"})
	public String listComputer(PlatAlarm alarm, Model model) {
		model.addAttribute("dataInfo", alarm);
		return "modules/platAlarm/alarmListComputer";
	}
	
	/**
	 * 页面跳转---安全报警详情页
	 */
	@RequestMapping(value = {"toAlarmInfoPage"})
	public String toAlarmInfoPage(Pager<Alarm> pager,Alarm alarm, Model model) {
		List<Alarm> list = getAlarmListByPara(alarm);
		if(null == list || list.size() != 1){
			return "modules/platAlarm/alarmList";
		}else{
			model.addAttribute("dataInfo", list.get(0));
		}
		return "modules/platAlarm/alarmInfo";
	}
	
	/**
	 * 获取安全报警列表
	 */
	@ResponseBody
	@RequestMapping(value = {"load"})
	public Pager<Alarm> load(Pager<Alarm> pager, Alarm alarm, HttpServletRequest request, HttpServletResponse response) {
		int pageNo = pager.getPage();
		int rows = pager.getRows();
		int beforePageNum = 0;
		int startPageNum = (pageNo-1)*rows+1;
		int endPageNum = pageNo*rows;
		
		List<Alarm> list = getAlarmListByPara(alarm);
		List<Alarm> showList = new ArrayList<Alarm>();
		for(Alarm task : list){
			 beforePageNum++;
			if(beforePageNum >= startPageNum && beforePageNum <= endPageNum){
				showList.add(task);
			}
		}
		pager.setList(showList);
        pager.setRecords(beforePageNum);
		return pager;
	}
	
	/**
	 * 获取安全报警列表
	 */
	@ResponseBody
	@RequestMapping(value = {"loadComputer"})
	public Pager<PlatAlarm> loadComputer(Pager<PlatAlarm> pager, PlatAlarm platAlarm, HttpServletRequest request, HttpServletResponse response) {
//		int pageNo = pager.getPage();
//		int rows = pager.getRows();
//		int beforePageNum = 0;
//		int startPageNum = (pageNo-1)*rows+1;
//		int endPageNum = pageNo*rows;
//		
//		List<Alarm> list = getAlarmListByPara(alarm);
//		List<Alarm> showList = new ArrayList<Alarm>();
//		for(Alarm task : list){
//			 beforePageNum++;
//			if(beforePageNum >= startPageNum && beforePageNum <= endPageNum){
//				showList.add(task);
//			}
//		}
//		pager.setList(showList);
//        pager.setRecords(beforePageNum);
		
		
		return platAlarmService.findPage(pager, platAlarm);
	}
	
	/**
	 * home页面模块展示用
	 */
	@ResponseBody
	@RequestMapping(value = {"findAlarmTopN"})
	public List<Alarm> findAlarmTopN(Alarm alarm, @RequestParam("topN") int topN) {
		List<Alarm> listAlarm = getAlarmListByPara(alarm);
		List<Alarm> list = new ArrayList<>();
		int i=0;
		
		if(null != listAlarm){
			for(Alarm item : listAlarm){
				if(i++ < topN){
					item.setCreateTime(item.getCreateTime().substring(0, 16));
					list.add(item);
				}else{
					break;
				}
			}
		}
		return list;
	}
	
	private List<Alarm> getAlarmListByPara(Alarm alarm){
		if("".equals(alarm.getEndDate())){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, 0);
			alarm.setEndDate(df.format(c.getTime()));
		}
		List<Alarm> showList = alarmService.getAlarmListByAlarm(alarm);
		return showList;
	}

	/**
	 * 今日温度
	 */
	@ResponseBody
	@RequestMapping(value = "getTodayT")
	public Map<String, Object> getTodayT(@RequestParam("roomId") int roomId){
		Map<String, Object> map = new HashMap<>();
		List<Object> dataList = new ArrayList<>();
		//-------真实数据
//		List<Map<String,Object>> wdList = alarmService.getWdByRoomId(roomId);
//		for(Map<String,Object> wdListItem : wdList){
//			Object[] item = { wdListItem.get("fCreateTime"), Float.parseFloat(wdListItem.get("fTemperature").toString()) };
//			dataList.add(item);
//		}
		//-------假数据
		int ss = 00;
		int mm = 00;
		int HH = 00;
		int num = 210;
		for(int i=0; i< 200; i++){
			ss = ss + 60;
			if(ss >= 60){
				ss = ss - 60;
				mm = mm + 1;
			}
			if(mm >= 60){
				mm = mm - 60;
				HH = HH + 1;
			}
			Date date = new Date(2017-1900, 05, 17, HH, mm, ss);
			int num1 = (int)(Math.random()*6);
			num = num + num1 - 3;
			if(num >= 240){
				num = num - 3;
			}else if(num <= 180){
				num = num + 3;
			}
			Object[] item = {date,(float)num/10};
			dataList.add(item);
		}
		map.put("data", dataList);
		return map;
	}
	
	/**
	 * 今日湿度
	 */
	@ResponseBody
	@RequestMapping(value = "getTodayH")
	public Map<String, Object> getTodayH(@RequestParam("roomId") int roomId){
		Map<String, Object> map = new HashMap<>();
		List<Object> dataList = new ArrayList<>();
		//-------真实数据
//		List<Map<String,Object>> wdList = alarmService.getSdByRoomId(roomId);
//		for(Map<String,Object> wdListItem : wdList){
//			Object[] item = { wdListItem.get("fCreateTime"), Float.parseFloat(wdListItem.get("fHumidity").toString()) };
//			dataList.add(item);
//		}
		//-------假数据
		int ss = 00;
		int mm = 00;
		int HH = 00;
		int num = 520;
		for(int i=0; i< 200; i++){
			ss = ss + 60;
			if(ss >= 60){
				ss = ss - 60;
				mm = mm + 1;
			}
			if(mm >= 60){
				mm = mm - 60;
				HH = HH + 1;
			}
			Date date = new Date(2017-1900, 05, 17, HH, mm, ss);
			int num1 = (int)(Math.random()*6);
			num = num + num1 - 3;
			if(num >= 590){
				num = num - 3;
			}else if(num <= 490){
				num = num + 3;
			}
			Object[] item = {date,(float)num/10};
			dataList.add(item);
		}
		map.put("data", dataList);
		return map;
	}	

	/**
	 * 安全报警---空调运行时长统计图
	 */
	@ResponseBody
	@RequestMapping(value = "getRunTime")
	public Map<String, Object> getRunTime(@RequestParam("roomId") int roomId){
		Map<String, Object> map = new HashMap<>();
		
		//真实数据
//		String operation = "温";
//		Calendar calendar = Calendar.getInstance();
//		List<Integer> list = new ArrayList<>();
//		List<String> categories = new ArrayList<>();
//		for(int i=29; i>=0; i--){
//			calendar.setTime(new Date());
//			calendar.add(Calendar.DAY_OF_MONTH, -i);
//			String dateTime = monthDay.format(calendar.getTime());
//			categories.add(dateTime);
//			
//			String startDate = sdf.format(calendar.getTime());
//			int howLong = getOperationRunTime(roomId, operation, startDate);
//			list.add(howLong);
//		}
//		map.put("categories", categories);
//		map.put("data", list);
		
		//假数据
		Object[][] data = {{1,3},{2,5},{3,5},{4,4},{5,2},{6,2},{7,7},{8,5},{9,6},{10,6},
	        	{11,5},{12,5},{13,6},{14,4},{15,2},{16,10},{17,3},{18,5},{19,6},{20,6},
	        	{21,5},{22,5},{23,6},{24,6},{25,4},{26,6},{27,7},{28,8},{29,6},{30,6}};

		map.put("data", data);
		return map;
	}
	
	public int getOperationRunTime(int roomId, String operation, String startDate){
		int howLong = 0;
		List<Integer> deviceIdList = alarmService.getDeviceIdListByRoomIdAndOperation(roomId, operation, startDate);
		for(Integer deviceId : deviceIdList){
			List<Map<String, Object>> resultList = alarmService.getOperationTimeByDeviceId(deviceId, operation, startDate);
			int count = 1; 
			String paraStr = ""; 
			long endTime = 0,startTime = 0;
			for(Map<String, Object> resultListItem : resultList){
				String fOperation = resultListItem.get("fOperation").toString();
				String fCreateTime = resultListItem.get("fCreateTime").toString();
				if(count == 1){
					if(fOperation.contains("止")){
						count = 1;
					}else{
						paraStr = fCreateTime;
						count = 2;
					}
				}else{
					if(fOperation.contains("止")){
						try {
							endTime = df.parse(fCreateTime).getTime();
							startTime = df.parse(paraStr).getTime();
							howLong += (endTime-startTime)/(1000*60*60);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						count = 1;
					}else{
						paraStr = fCreateTime;
						count = 2;
					}
				}
			}
		}
		return howLong;
	}

	/**
	 * 安全报警---湿度调节时长统计
	 */
	@ResponseBody
	@RequestMapping(value = "getHumidityCtrl")
	public Map<String, Object> getHumidityCtrl(@RequestParam("roomId") String roomId){
		Map<String, Object> map = new HashMap<>();
		
		Object[][] data = {{1,-3},{2,5},{3,5},{4,-4},{5,2},{6,2},{7,7},{8,-5},{9,6},{10,-6},
	        	{11,5},{12,5},{13,-6},{14,4},{15,2},{16,-10},{17,3},{18,5},{19,-6},{20,6},
	        	{21,5},{22,5},{23,6},{24,-6},{25,4},{26,6},{27,-7},{28,8},{29,6},{30,6}};

		map.put("data", data);
		return map;
	}

	/**
	 * 安全报警---历年今日空调运行时长
	 */
	@ResponseBody
	@RequestMapping(value = "getOldRunTime")
	public Map<String, Object> getOldRunTime(@RequestParam("roomId") int roomId){
		Map<String, Object> map = new HashMap<>();
		
		//真实数据
//		String operation = "温";
//		Calendar calendar = Calendar.getInstance();
//		List<Integer> data1 = new ArrayList<>();
//		List<Integer> data2 = new ArrayList<>();
//		List<String> categories = new ArrayList<>();
//		for(int i=4; i>=0; i--){
//			calendar.setTime(new Date());
//			calendar.add(Calendar.YEAR, -i);
//			String dateTime = yearDf.format(calendar.getTime());
//			categories.add(dateTime);
//			
//			String startDate = sdf.format(calendar.getTime());
//			int howLong = getOperationRunTime(roomId, operation, startDate);
//			data1.add(howLong);
//			if(i != 0){
//				calendar.add(Calendar.DAY_OF_YEAR, +1);
//				startDate = sdf.format(calendar.getTime());
//				howLong = getOperationRunTime(roomId, operation, startDate);
//				data2.add(howLong);
//			}
//		}
//		map.put("categories", categories);
//		map.put("data1", data1);
//		map.put("data2", data2);
		//假数据
		Object[] categories = {"2013", "2014", "2015", "2016", "2017"};
		map.put("categories", categories);
		
		Object[] data1 = {8, 7, 7, 8, 8};
		map.put("data1", data1);
		Object[] data2 = {6, 5, 3, 4, 5};
		map.put("data2", data2);
		
		return map;
	}
	
	/**
	 * 安全报警---历年今日湿度调节时长
	 */
	@ResponseBody
	@RequestMapping(value = "getOldHumidityCtrl")
	public Map<String, Object> getOldHumidityCtrl(@RequestParam("roomId") String roomId){
		Map<String, Object> map = new HashMap<>();
		
		Object[] categories = {"2013", "2014", "2015", "2016", "2017(预测)"};
		map.put("categories", categories);
		
		Object[] data1 = {-2, 4, -1, 2, 1};
		map.put("data1", data1);
		Object[] data2 = {-1, 2, -3, 1, 1};
		map.put("data2", data2);
		return map;
	}
	
	/**
	 * 安全报警---报警次数按月统计图
	 */
	@ResponseBody
	@RequestMapping(value = "getAlarmCountByMonth")
	public Map<String, Object> getAlarmCountByMonth(@RequestParam("roomId") String roomId){
		Map<String, Object> map = new HashMap<>();
		//真实数据
//		Calendar calendar = Calendar.getInstance();
//		List<String> categories = new ArrayList<>();
//		List<Integer> data1 = new ArrayList<>();
//		List<Integer> data2 = new ArrayList<>();
//		List<Integer> data3 = new ArrayList<>();
//		
//		for(int i=11; i>=0; i--){
//			calendar.setTime(new Date());
//			calendar.add(Calendar.MONTH, -i);
//			String dateTime = yearMonth.format(calendar.getTime());
//			categories.add(dateTime);
//			
//			List<Map<String,Object>> monthList = alarmService.getGradesCountByNow(i);
//			for(Map<String,Object> monthListItem : monthList){
//				int fLevel = (int) monthListItem.get("fLevel");
//				int fCount = (int) monthListItem.get("fCount");
//				if(fLevel == 1){
//					data1.add(fCount);
//				}else if(fLevel == 2){
//					data2.add(fCount);
//				}else if(fLevel == 3){
//					data3.add(fCount);
//				}
//			}
//		}
//		map.put("categories", categories);
//		map.put("data1", data1);
//		map.put("data2", data2);
//		map.put("data3", data3);
		
		//假数据
		String[] categories = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
		map.put("categories", categories);
		Object[] data1 = {1,3,2,2,1,0,2,1,3,2,0,0};
		Object[] data2 = {3,2,1,3,1,2,3,1,2,0,3,2};
		Object[] data3 = {3,2,3,3,2,4,3,4,2,0,3,2};
		map.put("data1", data1);
		map.put("data2", data2);
		map.put("data3", data3);
		return map;
	}
	
	/**
	 * 历年同月安全报警次数统计
	 */
	@ResponseBody
	@RequestMapping(value = "getAlarmCountByOldMonth")
	public Map<String, Object> getAlarmCountByOldMonth(@RequestParam("roomId") String roomId){
		Map<String, Object> map = new HashMap<>();
		//真实数据
//		Calendar calendar = Calendar.getInstance();
//		List<String> categories = new ArrayList<>();
//		List<Integer> data1 = new ArrayList<>();
//		List<Integer> data2 = new ArrayList<>();
//		List<Integer> data3 = new ArrayList<>();
//		
//		for(int i=4; i>=0; i--){
//			calendar.setTime(new Date());
//			calendar.add(Calendar.YEAR, -i);
//			String dateTime = yearMonth.format(calendar.getTime());
//			categories.add(dateTime);
//			
//			List<Map<String,Object>> monthList = alarmService.getGradesCountByNow(i);
//			for(Map<String,Object> monthListItem : monthList){
//				int fLevel = (int) monthListItem.get("fLevel");
//				int fCount = (int) monthListItem.get("fCount");
//				if(fLevel == 1){
//					data1.add(fCount);
//				}else if(fLevel == 2){
//					data2.add(fCount);
//				}else if(fLevel == 3){
//					data3.add(fCount);
//				}
//			}
//		}
//		map.put("categories", categories);
//		map.put("data1", data1);
//		map.put("data2", data2);
//		map.put("data3", data3);
		
		//假数据
		String[] categories = {"2013-04","2014-04","2015-04","2016-04","2017-04"};
		map.put("categories", categories);

		Object[] data1 = {1,3,2,2,1};
		Object[] data2 = {3,2,1,3,1};
		Object[] data3 = {3,2,3,3,2};
		map.put("data1", data1);
		map.put("data2", data2);
		map.put("data3", data3);
		return map;
	}
	
	@RequestMapping(value = "deleteComputerAlarm")
	@ResponseBody
	public Ajax deleteComputerAlarm(PlatAlarm platAlarm) {
		try {
			platAlarmService.deletePlatAlarmInfo(platAlarm);
			return new Ajax(true, "设备告警处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Ajax(false, "设备告警处理异常");
		}
	}
	
}