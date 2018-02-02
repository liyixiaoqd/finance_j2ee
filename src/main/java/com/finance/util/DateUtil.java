package com.finance.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static String getStrTodayYMD(){
	    return getStrTodayFormat("yyyy-MM-dd");
	}
	
	public static String getStrTodayYMDHMS(){
		return getStrTodayFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getStrTodayFormat(String format){
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    return sdf.format(dt);	
	}
	
	public static String dateToStr(Date date){
		return dateToStr(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	public static String dateToStr(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
	    return sdf.format(date);	
	}
	
	public static Date strToDate(String strDate){
		return strToDate(strDate,"yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * string转date
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static Date strToDate(String strDate,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String calcDateByToday(int stepDay){
        return calcDate(getStrTodayYMD(),"yyyy-MM-dd",stepDay);
	}
	
	/***
	 * 根据传入时间 与 计算天数 返回计算后的天数
	 * @param strDate
	 * @param stepDay
	 * @return
	 */
	public static String calcDate(String strDate,String format,int stepDay){
        Date d = strToDate(strDate,format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, stepDay);
        return dateToStr(calendar.getTime(),format);
	}
}
