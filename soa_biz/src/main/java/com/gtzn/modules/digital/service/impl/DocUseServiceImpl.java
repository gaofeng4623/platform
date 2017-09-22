package com.gtzn.modules.digital.service.impl;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.gtzn.common.annotation.Source;
import com.gtzn.modules.base.entity.Constant;
import com.gtzn.modules.digital.dao.DocUseDao;
import com.gtzn.modules.digital.service.DocUseService;
import com.gtzn.modules.sys.entity.Dict;

@Service
@Source("digital")
public class DocUseServiceImpl implements DocUseService {

	@Autowired
	private DocUseDao docUseDao;

	
	
	public List<Dict> findDict(String type) {
		return docUseDao.findDict(type);
	}
	
	// 利用】借阅 总数统计
	/* (non-Javadoc)
	 * @see com.gtzn.modules.digital.service.impl.As#findUseAllCountByMonth()
	 */
	@Override
	public Map<String, Object> findUseAllCountByMonth() {

		Map<String, Object> data = Maps.newHashMap();

		String[] months = new String[12];// 前12个月
		Integer[] uses = new Integer[12];
		Integer[] borrows = new Integer[12];
		Integer[] totals = new Integer[12];

		Map<String, Integer> useMap = Maps.newHashMap();
		Map<String, Integer> borrowMap = Maps.newHashMap();
		// 利用统计
		List<Map<String, String>> useList = docUseDao.findUseCountByMonth();
		for (Map<String, String> m : useList) {
			useMap.put(m.get("month"), Integer.parseInt(m.get("count").toString()));
		}
		// 借阅统计
		List<Map<String, String>> borrowList = docUseDao.findBorrowCountByMonth();
		for (Map<String, String> m : borrowList) {
			borrowMap.put(m.get("month"), Integer.parseInt(m.get("count")));
		}
		// 处理没有情况
		DateTime dt = new DateTime();// 取得当前时间
		dt = dt.plusMonths(-11);
		for (int i = 0; i < 12; i++) {
			DateTime dt2 = dt.plusMonths(i);
			int mfy = dt2.getMonthOfYear();
			String m = mfy < 10 ? "0" + mfy : mfy+"";
			String ym = dt2.getYear() + "-" + m;
			// 利用的数量
			if (null == useMap.get(ym)) {
				uses[i] = 0;
			} else {
				uses[i] = useMap.get(ym);
			}
			// 借阅的数量
			if (null == borrowMap.get(ym)) {
				borrows[i] = 0;
			} else {
				borrows[i] = borrowMap.get(ym);
			}
			// 总数
			totals[i] = uses[i] + borrows[i];

			months[i] = ym;
		}
		data.put("months", months);
		data.put("uses", uses);
		data.put("borrows", borrows);
		data.put("totals", totals);
		return data;
	}

	
	public Map<String, Object> findUseAllCountByYear() {
		Map<String, Object> data = Maps.newHashMap();

		String[] years = new String[5];// 5年
		Integer[] uses = new Integer[5];
		Integer[] borrows = new Integer[5];

		Map<String, Integer> useMap = Maps.newHashMap();
		Map<String, Integer> borrowMap = Maps.newHashMap();
		// 利用统计
		List<Map<String, String>> useList = docUseDao.findUseCountByYear();
		for (Map<String, String> m : useList) {
			useMap.put(m.get("year"), Integer.parseInt(m.get("count").toString()));
		}
		// 借阅统计
		List<Map<String, String>> borrowList = docUseDao.findBorrowCountByYear();
		for (Map<String, String> m : borrowList) {
			borrowMap.put(m.get("year"), Integer.parseInt(m.get("count")));
		}
		// 处理没有情况
		DateTime dt = new DateTime();// 取得当前时间
		dt = dt.plusYears(-4);
		for (int i = 0; i < 5; i++) {
			DateTime dt2 = dt.plusYears(i);
			String y = dt2.getYear()+"";
			// 利用的数量
			if (null == useMap.get(y)) {
				uses[i] = 0;
			} else {
				uses[i] = useMap.get(y);
			}
			// 借阅的数量
			if (null == borrowMap.get(y)) {
				borrows[i] = 0;
			} else {
				borrows[i] = borrowMap.get(y);
			}
			years[i] = y;
		}
		data.put("years", years);
		data.put("uses", uses);
		data.put("borrows", borrows);
		return data;
	}
	// 档案利用
	/* (non-Javadoc)
	 * @see com.gtzn.modules.digital.service.impl.As#findUseCountByMonthUserType()
	 */
	@Override
	public Map<String, Object> findUseCountByMonthUserType() {
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, String>> useList = docUseDao.findUseCountByMonthUserType();

		Map<String, Integer> useMap01 = Maps.newHashMap();// 个人利用
		Map<String, Integer> useMap02 = Maps.newHashMap();// 单位利用
		for (Map<String, String> m : useList) {
			if ("01".equals(m.get("usertype"))) {
				useMap01.put(m.get("month"), Integer.parseInt(m.get("count")));
			} else if ("02".equals(m.get("usertype"))) {
				useMap02.put(m.get("month"), Integer.parseInt(m.get("count")));
			}
		}
		String[] months = new String[12];// 前12个月
		Integer[] use01 = new Integer[12];
		Integer[] use02 = new Integer[12];

		// 处理没有情况
		DateTime dt = new DateTime();// 取得当前时间
		dt = dt.plusMonths(-11);
		for (int i = 0; i < 12; i++) {
			DateTime dt2 = dt.plusMonths(i);
			int mfy = dt2.getMonthOfYear();
			String m = mfy < 10 ? "0" + mfy : mfy+"";
			String ym = dt2.getYear() + "-" + m;
			// 个人利用
			if (null == useMap01.get(ym)) {
				use01[i] = 0;
			} else {
				use01[i] = useMap01.get(ym);
			}
			// 单位利用
			if (null == useMap02.get(ym)) {
				use02[i] = 0;
			} else {
				use02[i] = useMap02.get(ym);
			}
			months[i] = ym;
		}
		data.put("months", months);
		data.put("use01", use01);
		data.put("use02", use02);

		data.put("total", docUseDao.findUseCount());
		
		return data;
	}

	
	
	// 档案借阅
	/* (non-Javadoc)
	 * @see com.gtzn.modules.digital.service.impl.As#findBorrowCountByMonthApplyType()
	 */
	@Override
	public Map<String, Object> findBorrowCountByMonthApplyType() {
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, String>> applyList = docUseDao.findBorrowCountByMonthApplyType();

		Map<String, Integer> applyMap1 = Maps.newHashMap();// 电子档案
		Map<String, Integer> applyMap2 = Maps.newHashMap();// 实物档案
		for (Map<String, String> m : applyList) {
			//实物
			if ("1".equals(m.get("applytype").toString())) {
				applyMap1.put(m.get("month"), Integer.parseInt(m.get("count")));
			}
			//电子
			else if ("0".equals(m.get("applytype").toString())) {
				applyMap2.put(m.get("month"), Integer.parseInt(m.get("count")));
			}
		}
		String[] months = new String[12];// 前12个月
		Integer[] apply1 = new Integer[12];
		Integer[] apply2 = new Integer[12];

		// 处理没有情况
		DateTime dt = new DateTime();// 取得当前时间
		dt = dt.plusMonths(-11);
		for (int i = 0; i < 12; i++) {
			DateTime dt2 = dt.plusMonths(i);
			int mfy = dt2.getMonthOfYear();
			String m = mfy < 10 ? "0" + mfy : mfy+"";
			String ym = dt2.getYear() + "-" + m;
			// 个人利用
			if (null == applyMap1.get(ym)) {
				apply1[i] = 0;
			} else {
				apply1[i] = applyMap1.get(ym);
			}
			// 单位利用
			if (null == applyMap2.get(ym)) {
				apply2[i] = 0;
			} else {
				apply2[i] = applyMap2.get(ym);
			}
			months[i] = ym;
		}
		data.put("months", months);
		data.put("apply1", apply1);
		data.put("apply2", apply2);
		data.put("total", docUseDao.findBorrowCount());
		return data;
	}

	/* (non-Javadoc)
	 * @see com.gtzn.modules.digital.service.impl.As#findUseCountByUsepurpose()
	 */
	@Override
	public List<Map<String, Object>> findUseCountByUsepurpose() {

		Map<String, String> useMap = Maps.newHashMap();
		double total = 0;
		List<Map<String, String>> useList = docUseDao.findUseCountByUsepurpose();
		for (Map<String, String> m : useList) {
			useMap.put(m.get("usepurpose"), m.get("count"));
			total += Double.parseDouble(m.get("count"));
		}
		// 创建一个数值格式化对象  
        NumberFormat numberFormat = NumberFormat.getInstance();  
        // 设置精确到小数点后2位  
        numberFormat.setMaximumFractionDigits(2);  
		List<Dict> dicts = docUseDao.findDict("LYMD");

		List<Map<String, Object>> lists = Lists.newArrayList();
		for (Dict dict : dicts) {
			Map<String, Object> m = Maps.newHashMap();
			m.put("label", dict.getLabel());
			if (null == useMap.get(dict.getValue())) {
				m.put("value", 0);
			} else {
				Double v =  Double.parseDouble(useMap.get(dict.getValue()));
				m.put("value", Double.parseDouble(numberFormat.format(v/total)));
			}
			lists.add(m);
		}
		/*
		 * 学术研究 编史修志 工作察考 经济建设 宣传教育 审核工龄 知青回津 办理公证 出国签证旅游
		 * 补证/离婚/房屋买卖过户/申办公租房/廉租房/限价房等 其他/社险减免/办理低保/提取公积金/失独补助等
		 */

		return lists;
	}

	/* (non-Javadoc)
	 * @see com.gtzn.modules.digital.service.impl.As#findBorrowCountByUsepurpose()
	 */
	@Override
	public List<Map<String, Object>> findBorrowCountByUsepurpose() {
		Map<String, String> borrowMap = Maps.newHashMap();
		List<Map<String, String>> borrwList = docUseDao.findBorrowCountByUsepurpose();
		Map<String, Object> usepurpose = Constant.borrow_purpose;
		// 创建一个数值格式化对象  
        NumberFormat numberFormat = NumberFormat.getInstance();  
        // 设置精确到小数点后2位  
        numberFormat.setMaximumFractionDigits(2);  
		double total = 0;
		for (Map<String, String> m : borrwList) {
			borrowMap.put(m.get("usepurpose"), m.get("count"));
			total += Double.parseDouble(m.get("count"));
		}
		List<Map<String, Object>> lists = Lists.newArrayList();
		for (String key : usepurpose.keySet()) {
			Map<String, Object> m = Maps.newHashMap();
			m.put("label", usepurpose.get(key));
			if (null == borrowMap.get(key)) {
				m.put("value", 0);
			} else {
				Double v =  Double.parseDouble(borrowMap.get(key));
				m.put("value", Double.parseDouble(numberFormat.format(v/total)));
			}
			lists.add(m);
		}
		return lists;
	}
	
	
	/* (non-Javadoc)
	 * @see com.gtzn.modules.digital.service.impl.As#findUnitCount()
	 */
	@Override
	public Map<String, Object> findUnitCount() {
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, String>> useList = docUseDao.findUseUnitCount();
		List<Map<String, String>> borrowList = docUseDao.findBorrowUnitCount();
		
		Map<String, Integer> useMap = Maps.newHashMap();
		Map<String, Integer> borrowMap = Maps.newHashMap();
		
		Set<String> units = Sets.newHashSet();// 所有全宗
		for (Map<String, String> m : useList) {
			useMap.put(m.get("unitname"), Integer.parseInt(m.get("count")));
			units.add(m.get("unitname"));
		}
		for (Map<String, String> m : borrowList) {
			borrowMap.put(m.get("unitname"), Integer.parseInt(m.get("count")));
			units.add(m.get("unitname"));
		}
		
		Integer[] uses = new Integer[units.size()];
		Integer[] borrows = new Integer[units.size()];
		int i = 0;
		for (String n : units) {
			if (null == useMap.get(n)) {
				uses[i] = 0;
			} else {
				uses[i] = useMap.get(n);
			}
			
			if (null == borrowMap.get(n)) {
				borrows[i] = 0;
			} else {
				borrows[i] = borrowMap.get(n);
			}
			
			
			i++;
		}
		int size = units.size();//获取集合的大小
		String[] array = new String[size];
		data.put("units", units.toArray(array));
		data.put("uses", uses);
		data.put("borrows", borrows);

		return data;
	}

	
	public Map<String, Object> findUseAgeRange() {
		Map<String, Object> m = docUseDao.findUseAgeRange();
		
		Map<String, Object> data = Maps.newHashMap();
		List<String> ageRange = Lists.newArrayList();
		Object[] uses = new Object[7];
		ageRange.add("20岁以下");
		uses[0] = m.get("n20");
		
		ageRange.add("20-30岁");
		uses[1] = m.get("n2030");
		
		ageRange.add("30-40岁");
		uses[2] = m.get("n3040");
		
		ageRange.add("40-50岁");
		uses[3] = m.get("n4050");
		
		ageRange.add("50-60岁");
		uses[4] = m.get("n5060");
		
		ageRange.add("60-70岁");
		uses[5] = m.get("n6070");
		
		ageRange.add("70岁以上");
		uses[6] = m.get("n70");
		
		int size = ageRange.size();//获取集合的大小
		String[] array = new String[size];
		data.put("ageRange", ageRange.toArray(array));
		data.put("uses", uses);
		return data;
	}
	
	
	public Map<String, Object> findUseCountByMonthUserTypeEq(String month, String usertype) {
		if ("个人利用".equals(usertype)) {
			usertype = "01";
		} else if ("单位利用".equals(usertype)) {
			usertype = "02";
		}
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, String>> list = docUseDao.findUseCountByMonthUserTypeEq(month, usertype);
		String[] months = new String[list.size()];
		Integer[] uses = new Integer[list.size()];
		int i = 0;
		for (Map<String, String> m : list) {
			months[i] = m.get("month");
			uses[i] = Integer.parseInt(m.get("count"));
			i++;
		}
		data.put("months", months);
		data.put("uses", uses);
		return data;
	}
	
	public Map<String, Object> findUseCountByClassEq(String month, String usertype) {
		if ("个人利用".equals(usertype)) {
			usertype = "01";
		} else if ("单位利用".equals(usertype)) {
			usertype = "02";
		}
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, String>> list = docUseDao.findUseCountByClassEq(month, usertype);
		String[] names = new String[list.size()];
		Integer[] uses = new Integer[list.size()];
		int i = 0;
		for (Map<String, String> m : list) {
			names[i] = m.get("name");
			uses[i] = Integer.parseInt(m.get("count"));
			i++;
		}
		data.put("names", names);
		data.put("uses", uses);
		return data;
	}
	
	public Map<String, Object> findBorrowCountByMonthApplyTypeEq(String month, String usertype) {
		if ("电子档案".equals(usertype)) {
			usertype = "1";
		} else if ("实物档案".equals(usertype)) {
			usertype = "0";
		}
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, String>> list = docUseDao.findBorrowCountByMonthApplyTypeEq(month, usertype);
		String[] months = new String[list.size()];
		Integer[] uses = new Integer[list.size()];
		int i = 0;
		for (Map<String, String> m : list) {
			months[i] = m.get("month");
			uses[i] = Integer.parseInt(m.get("count"));
			i++;
		}
		data.put("months", months);
		data.put("uses", uses);
		return data;
	}
	
	public Map<String, Object> findBorrowCountByClassEq(String month, String usertype) {
		if ("电子档案".equals(usertype)) {
			usertype = "1";
		} else if ("实物档案".equals(usertype)) {
			usertype = "0";
		}
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, String>> list = docUseDao.findBorrowCountByClassEq(month, usertype);
		String[] names = new String[list.size()];
		Integer[] uses = new Integer[list.size()];
		int i = 0;
		for (Map<String, String> m : list) {
			names[i] = m.get("name");
			uses[i] = Integer.parseInt(m.get("count"));
			i++;
		}
		data.put("names", names);
		data.put("uses", uses);
		return data;
	}
}
