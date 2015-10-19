package com.ninetowns.library.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 
	 * @Title: StringUtils.java
	 * @Description: byte[]转化为 String
	 * @author wuyulong
	 * @date 2014-7-14 下午2:43:17
	 * @param
	 * @return String
	 */
	public static String ByteToString(byte[] arg2) {
		String string = "";
		if (arg2 != null)
			string = new String(arg2);
		return string;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(CharSequence input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
	/**
	 * 
	* @Title: StringUtils.java  
	* @Description: 有多少个汉字 
	* @author wuyulong
	* @date 2014-7-15 下午6:33:44  
	* @param 
	* @return int
	 */
public static int getChineseTextCount(String str){
	int count=0;
	 Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");  
     Matcher m = p.matcher(str);  
     while (m.find())  
     {  
    	 str = m.group(0);  
    	 count=str.length();
     }  
     return count;
}
/**
 * 
* @Title: StringUtils.java  
* @Description: 判断是否为汉字 
* @author wuyulong
* @date 2014-7-15 下午6:43:36  
* @param 
* @return boolean
 */
public static boolean isChineseText(String str){  
    
    char[] chars=str.toCharArray();   
    boolean isGB2312=false;   
    for(int i=0;i<chars.length;i++){  
                byte[] bytes=(""+chars[i]).getBytes();   
                if(bytes.length==2){   
                            int[] ints=new int[2];   
                            ints[0]=bytes[0]& 0xff;   
                            ints[1]=bytes[1]& 0xff;   
                            if(ints[0]>=0x81 && ints[0]<=0xFE && ints[1]>=0x40 && ints[1]<=0xFE){   
                                        isGB2312=true;   
                                        break;   
                            }   
                }   
    }   
    return isGB2312;   
}  
	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	
	public static boolean hasChinese(String msg) {
		int bytesLength = msg.getBytes().length;  
        int sLength = msg.length();  
        int hasNum = bytesLength - sLength;
         if(hasNum>0){  
        	return true;
        }
		return false;  
    }
	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 参数编码
	 * 
	 * @param value
	 * @return
	 */
	public static String encode(String s) {
		if (s == null) {
			return "";
		}
		try {
			return URLEncoder.encode(s, "UTF-8").replace("+", "%20")
					.replace("*", "%2A").replace("%7E", "~")
					.replace("#", "%23");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
