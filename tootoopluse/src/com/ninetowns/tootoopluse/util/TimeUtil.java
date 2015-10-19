package com.ninetowns.tootoopluse.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

import com.ninetowns.tootoopluse.R;

public class TimeUtil {
	
	 /** 格式化日期的标准字符串 */
    private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
     * 将Date对象转换为指定格式的字符串
     * @param date Date对象
     * @return Date对象的字符串表达形式"yyyy-MM-dd HH:mm:ss"
     * */
    public static String formatDate(Date date)
    {
        return formatDate(date, FORMAT);  
    }
    
    /**
     * 将Date对象转换为指定格式的字符串
     * @param date Date对象
     * @param String format 格式化字符串
     * @return Date对象的字符串表达形式
     * */
    public static String formatDate(Date date, String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);  
        return dateFormat.format(date);  
    }

    
    
    public static String getTimeDisplay(long time, Context context ) {  
        Date getDate = new Date(time*1000);  
  
//        final long getTime = getDate.getTime();  
  
        final long currTime = System.currentTimeMillis();  
        final Date formatSysDate = new Date(currTime);  
  
        // 判断当前总天数  
        final int sysMonth = formatSysDate.getMonth() + 1;  
        final int sysYear = formatSysDate.getYear();  
  
        // 计算服务器返回时间与当前时间差值  1426586011  1426744555118
        final long seconds = (currTime - time) / 1000;  
        final long minute = seconds / 60;  
        final long hours = minute / 60;  
        final long day = hours / 24;  
  
        final long month = day / calculationDaysOfMonth(sysYear, sysMonth);  
        final long year = month / 12;  
        if (year > 0 || month > 0 || day > 0) {  
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(  
                    "yyyy-MM-dd HH:mm");  
            return simpleDateFormat.format(getDate);  
        } else if (hours > 0) {  
            return hours + context.getString(R.string.str_hoursago);  
        } else if (minute > 0) {  
            return minute + context.getString(R.string.str_minsago);  
        } else if (seconds > 0) {  
//            return "1" + context.getString(R.string.str_minsago);  
             return seconds + context.getString(R.string.str_secondago);  
        } else {  
//          return "1" + context.getString(R.string.str_secondago);  
            return "1" + context.getString(R.string.str_minsago); //都换成分钟前  
        }  
    }  
    
    
    /** 
    * @Title: formatChatRoomTime 
    * @Description: 格式化聊天室的时间
    * @param     设定文件 
    * @return void    返回类型 
    * @throws 
    */
    
    private static final int FIVE_MINUTES_DURATION=5*60*1000;
	private static final String format="MM月dd日  HH:mm";
    public static String  formatChatRoomTime(String time) {
    	Date date = null;
		try {
			date = new SimpleDateFormat(FORMAT).parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	final long l_time=date.getTime();
    	final long currentTime=System.currentTimeMillis();
    	final long seconds=currentTime-l_time;
    	if(seconds<=FIVE_MINUTES_DURATION){
    		//返回 ""时 在五分钟之内不显示时间
    		return "";
    	}
    	  // 判断当前总天数  
//    	final long currTime = System.currentTimeMillis();  
        final Date formatSysDate = new Date(currentTime);  
        final int sysMonth = formatSysDate.getMonth() + 1;  
        final int sysYear = formatSysDate.getYear();  
         final long minute = seconds / 60;  
         final long hours = minute / 60;  
         final long day = hours / 24;
         final long month = day / calculationDaysOfMonth(sysYear, sysMonth);  
         final long year = month / 12; 
         if (year > 0 ) {  
             return time;  
         }else if(day>0&&day<=1){
        	 return "昨天".concat(date.getHours()+date.getMinutes()+"");
         }else{
        	 return  new SimpleDateFormat(format).format(date)+"";  
         }
//		return time;

	}
    
    
    /** 
     * 计算月数 
     *  
     * @return 
     */  
    private static int calculationDaysOfMonth(int year, int month) {  
        int day = 0;  
        switch (month) {  
        // 31天  
        case 1:  
        case 3:  
        case 5:  
        case 7:  
        case 8:  
        case 10:  
        case 12:  
            day = 31;  
            break;  
        // 30天  
        case 4:  
        case 6:  
        case 9:  
        case 11:  
            day = 30;  
            break;  
        // 计算2月天数  
        case 2:  
            day = year % 100 == 0 ? year % 400 == 0 ? 29 : 28  
                    : year % 4 == 0 ? 29 : 28;  
            break;  
        }  
  
        return day;  
    }  
    
}