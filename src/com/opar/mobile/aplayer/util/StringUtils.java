package com.opar.mobile.aplayer.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;


import android.text.TextPaint;
import android.util.Log;


/**
 * 字符串工具类
 * 
 * @author tangjun
 * 
 */
public class StringUtils {

	public static final String EMPTY = "";

	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
	/** 用于生成文件 */
	private static final String DEFAULT_FILE_PATTERN = "yyyy-MM-dd-HH-mm-ss";
	private static final double KB = 1024.0;
	private static final double MB = 1048576.0;
	private static final double GB = 1073741824.0;
	public static final SimpleDateFormat DATE_FORMAT_PART = new SimpleDateFormat(
			"HH:mm");
	private static final double WB = 10000.0;
	private static final double YB = 100000000.0;
	public static String currentTimeString() {
		return DATE_FORMAT_PART.format(Calendar.getInstance().getTime());
	}

	public static char chatAt(String pinyin, int index) {
		if (pinyin != null && pinyin.length() > 0)
			return pinyin.charAt(index);
		return ' ';
	}

	/** 获取字符串宽度 */
	public static float GetTextWidth(String Sentence, float Size) {
		if (isEmpty(Sentence))
			return 0;
		TextPaint FontPaint = new TextPaint();
		FontPaint.setTextSize(Size);
		return FontPaint.measureText(Sentence.trim()) + (int) (Size * 0.1); // 留点余地
	}

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * String转换java.util.Date类型
	 */
	public static long parseDate(String time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		long lTime = 0;
		try {
			Date date = format.parse(time);
			lTime = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lTime;
	}
	

	/**
	 * 比较时间
	 */
	public static String getTimeDiff(String time) {
		long lTime = parseDate(time, DEFAULT_DATETIME_PATTERN);
		if(lTime != 0){
			return getTimeDiff(lTime);
		}
		return time;
	}
	
	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 * @return 例如2011-3-24
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_DATE_PATTERN);
	}

	public static String formatDate(long date) {
		return formatDate(new Date(date), DEFAULT_DATE_PATTERN);
	}

	/**
	 * 获取当前时间 格式为yyyy-MM-dd 例如2011-07-08
	 * 
	 * @return
	 */
	public static String getDate() {
		return formatDate(new Date(), DEFAULT_DATE_PATTERN);
	}

	/** 生成一个文件名，不含后缀 */
	public static String createFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FILE_PATTERN);
		return format.format(date);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getDateTime() {
		return formatDate(new Date(), DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * 格式化日期时间字符串
	 * 
	 * @param date
	 * @return 例如2011-11-30 16:06:54
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, DEFAULT_DATETIME_PATTERN);
	}

	public static String formatDateTime(long date) {
		return formatDate(new Date(date), DEFAULT_DATETIME_PATTERN);
	}
	
	/**
	 * 格林威时间转换
	 * 
	 * @param gmt
	 * @return
	 */
	public static String formatGMTDate(String gmt) {
		TimeZone timeZoneLondon = TimeZone.getTimeZone(gmt);
		return formatDate(Calendar.getInstance(timeZoneLondon)
				.getTimeInMillis());
	}
	
	/**
	 * 拼接数组
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(String[] array,String separator) {
		StringBuffer result = new StringBuffer();
		if (array != null && array.length > 0) {
			for (String str : array) {
				result.append(str);
				result.append(separator);
			}
			result.delete(result.length() - separator.length(), result.length());
		}
		return result.toString();
	}

	/**
	 * 拼接数组
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(List<String> array,String separator) {
		StringBuffer result = new StringBuffer("");
		if (array != null && array.size() > 0) {
			for (String str : array) {
				result.append(str);
				result.append(separator);
			}
			result.delete(result.length() - separator.length(), result.length());
		}
		return result.toString();
	}

	public static String join(final Iterator<String> iter,String separator) {
		StringBuffer result = new StringBuffer();
		if (iter != null) {
			while (iter.hasNext()) {
				String key = iter.next();
				result.append(key);
				result.append(separator);
			}
			if (result.length() > 0)
				result.delete(result.length() - separator.length(), result.length());
		}
		return result.toString();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? EMPTY : str.trim();
	}

	/**
	 * 转换时间显示
	 * 
	 * @param time
	 *            毫秒
	 * @return
	 */
	public static String generateTime(long time) {
		int totalSeconds = (int) (time / 1000);
		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes,
				seconds) : String.format("%02d:%02d", minutes, seconds);
	}
	
	/**
	 * 转换时间显示
	 * 
	 * @param time
	 *            秒
	 * @return
	 */
	public static String generateTime(int time) {
		int seconds = time % 60;
		int minutes = (time / 60) % 60;
		int hours = time / 3600;

		return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes,
				seconds) : String.format("%02d:%02d", minutes, seconds);
	}

	/** 根据秒速获取时间格式 */
	public static String gennerTime(int totalSeconds) {
		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		return String.format("%02d:%02d", minutes, seconds);
	}

	/**
	 * 转换文件大小
	 * 
	 * @param size
	 * @return
	 */
	public static String generateFileSize(long size) {
		String fileSize;
		if (size < KB)
			fileSize = size + "B";
		else if (size < MB)
			fileSize = String.format("%.1f", size / KB) + "KB";
		else if (size < GB)
			fileSize = String.format("%.1f", size / MB) + "MB";
		else
			fileSize = String.format("%.1f", size / GB) + "GB";

		return fileSize;
	}
	
	public static String getViewNum(long num) {
		String fileSize;
		if (num < WB)
			fileSize = String.valueOf(num);
		else if (num < YB)
			fileSize = String.format("%.1f", num / WB) + "万";
		else
			fileSize = String.format("%.1f", num / YB) + "亿";

		return fileSize;
	}
	public static String getViewNum(String fileSize) {
		try {
			long num = Long.parseLong(fileSize);
			fileSize=getViewNum(num);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return fileSize;
	}
	
	public static int getStringToInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	public static long getStringToLong(String number) {
		try {
			return Long.parseLong(number);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getTimeDiff(long time) {
		// Calendar cal = Calendar.getInstance();
		long diff = 0;
		// Date dnow = cal.getTime();
		String str = "";
		diff = System.currentTimeMillis() - time;
		Log.i("TAG","--time =" +time +",System = "+System.currentTimeMillis()+",diff = "+diff);
		if (diff > 2592000000L) {// 30 * 24 * 60 * 60 * 1000=2592000000 毫秒
			str = "1个月前";
		} else if (diff > 1814400000) {// 21 * 24 * 60 * 60 * 1000=1814400000 毫秒
			str = "3周前";
		} else if (diff > 1209600000) {// 14 * 24 * 60 * 60 * 1000=1209600000 毫秒
			str = "2周前";
		} else if (diff > 604800000) {// 7 * 24 * 60 * 60 * 1000=604800000 毫秒
			str = "1周前";
		} else if (diff > 86400000) { // 24 * 60 * 60 * 1000=86400000 毫秒
			// System.out.println("X天前");
			str = (int) Math.floor(diff / 86400000f) + "天前";
		} else if (diff > 3600000) {// 5 * 60 * 60 * 1000=18000000 毫秒
			// System.out.println("X小时前");
			str = (int) Math.floor(diff / 3600000f) + "小时前";
		} else if (diff > 60000) {// 1 * 60 * 1000=60000 毫秒
			// System.out.println("X分钟前");
			str = (int) Math.floor(diff / 60000) + "分钟前";
		} else {
			str = (int) Math.floor(diff / 1000) + "秒前";
		}
		return str;
	}

	/**
	 * 截取字符串
	 * 
	 * @param search
	 *            待搜索的字符串
	 * @param start
	 *            起始字符串 例如：<title>
	 * @param end
	 *            结束字符串 例如：</title>
	 * @param defaultValue
	 * @return
	 */
	public static String substring(String search, String start, String end,
			String defaultValue) {
		int start_len = start.length();
		int start_pos = StringUtils.isEmpty(start) ? 0 : search.indexOf(start);
		if (start_pos > -1) {
			int end_pos = StringUtils.isEmpty(end) ? -1 : search.indexOf(end,
					start_pos + start_len);
			if (end_pos > -1)
				return search.substring(start_pos + start.length(), end_pos);
			else
				return search.substring(start_pos + start.length());
		}
		return defaultValue;
	}

	/**
	 * 截取字符串
	 * 
	 * @param search
	 *            待搜索的字符串
	 * @param start
	 *            起始字符串 例如：<title>
	 * @param end
	 *            结束字符串 例如：</title>
	 * @return
	 */
	public static String substring(String search, String start, String end) {
		return substring(search, start, end, "");
	}

	/**
	 * 拼接字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String concat(String... strs) {
		StringBuffer result = new StringBuffer();
		if (strs != null) {
			for (String str : strs) {
				if (str != null)
					result.append(str);
			}
		}
		return result.toString();
	}

	/** 获取中文字符个数 */
	public static int getChineseCharCount(String str) {
		String tempStr;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			tempStr = String.valueOf(str.charAt(i));
			if (tempStr.getBytes().length == 3) {
				count++;
			}
		}
		return count;
	}

	/** 获取英文字符个数 */
	public static int getEnglishCount(String str) {
		String tempStr;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			tempStr = String.valueOf(str.charAt(i));
			if (!(tempStr.getBytes().length == 3)) {
				count++;
			}
		}
		return count;
	}

	public static String encode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	private static String URLDecoder(String s) {
		if (s == null || s.length() == 0)
			return "";
		try {
			s = java.net.URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		} catch (NullPointerException e) {
			return "";
		}
		return s;
	}
	
	/**
	 * 做URLEncode编码
	 * @param s
	 * @return
	 */
	public static String URLEncoder(String s) {
		if (s == null || s.length() == 0)
			return "";
		try {
			s = java.net.URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		} catch (NullPointerException e) {
			return "";
		}
		return s;
	}
}
