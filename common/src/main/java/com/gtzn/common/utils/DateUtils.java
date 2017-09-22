/**
 * 
 */
package com.gtzn.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author gtzn
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatDate(Date d, String formatType){
		// 获得当前时间
		DateFormat format = new SimpleDateFormat(formatType);
		// 转换为字符串
		return format.format(d);
	}
	public static Date formatDate(String d){
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 转换为字符串
		Date formatDate = null;
		try {
			formatDate = format.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatDate;
	}
	
	public static Date formatImportDate(String d){
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		// 转换为字符串
		Date formatDate = null;
		try {
			formatDate = format.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatDate;
	}
	
	public static String formatDateyyyyMMdd(Date d){
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		// 转换为字符串
		String formatDate = format.format(d);
		return formatDate;
	}
	
	public static Date formatCST(String date){
		SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 获取最近n天，不包含当天，经常用于统计图表的X轴
	 */
	public static String[] getLastDays(int days){
		String[] lastDays = new String[days];
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - (days+1));
		for (int i = 0 ; i < days ; i++) {
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
			lastDays[i] = formatDate(cal.getTime(),"yyyy-MM-dd");
		}
		return lastDays;
	}
	
	/**
	 * 获取最近12个月，经常用于统计图表的X轴
	 */
	public static String[] getLast12Months(){

		String[] last12Months = new String[12];
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) - 12);
		for (int i=0;i<12;i++) {
			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) + 1);
			last12Months[i] = formatDate(cal.getTime(),"yyyy-MM");
		}
		return last12Months;
	}
	
	/**
	 * 获取最近n个月，经常用于统计图表的X轴
	 */
	public static String[] getLastMonths(int month){
		String[] lastMonths = new String[month];
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) - month);
		for (int i=0;i<month;i++) {
			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) + 1);
			lastMonths[i] = formatDate(cal.getTime(),"yyyy-MM");
		}
		return lastMonths;
	}
	
	/**
	 * 获取最近n年，经常用于统计图表的X轴
	 */
	public static String[] getLastYears(int year){
		String[] lastYears = new String[year];
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,cal.get(Calendar.YEAR) - year);
		for (int i=0;i<year;i++) {
			cal.set(Calendar.YEAR,cal.get(Calendar.YEAR) + 1);
			lastYears[i] = formatDate(cal.getTime(),"yyyy");
		}
		return lastYears;
	}
	
    //获取指定月份的天数  
    public static int getDaysByYearMonth(int year, int month) {  
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }  
	
	/**
	 * 获取某月的日期
	 * @return
	 */
	public static String[] getDayByMonth(Date month){
		Calendar cal = Calendar.getInstance();  
	    cal.setTime(month);//month 为指定月份任意日期  
	    int year = cal.get(Calendar.YEAR);  
	    int m = cal.get(Calendar.MONTH)+1;  
	    int dayNumOfMonth = getDaysByYearMonth(year, m);  
	    cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始  
	    String[] days = new String[dayNumOfMonth];
	    for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {  
	        days[i] = formatDate(cal.getTime(),"yyyy-MM-dd");
	   }  
	    return days;
	}
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
//		String value = formatDateyyyyMMdd(formatCST("Fri Jul 29 00:00:00 CST 2016"));
//		System.out.println(value);
	}
}
