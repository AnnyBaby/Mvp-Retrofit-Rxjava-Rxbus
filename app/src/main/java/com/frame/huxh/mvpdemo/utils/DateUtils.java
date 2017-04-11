package com.frame.huxh.mvpdemo.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint({ "SimpleDateFormat", "DefaultLocale" }) public class DateUtils {
	
	public static final int ONE_SECOND_MILLISECONDS = 1000;
	public static final int ONE_MINUTE_SECONDS = 60;
	public static final int ONE_HOUR_MINUTES = 60;
	public static final int ONE_DAY_HOURS = 24;
	public static final int ONE_MINUTE_MILLISECONDS = ONE_MINUTE_SECONDS * ONE_SECOND_MILLISECONDS;
	public static final int ONE_HOUR_SECONDS = ONE_HOUR_MINUTES * ONE_MINUTE_SECONDS;
	public static final int ONE_HOUR_MILLISECONDS = ONE_HOUR_SECONDS * ONE_SECOND_MILLISECONDS;
	public static final int ONE_DAY_MINUTES = ONE_DAY_HOURS * ONE_HOUR_MINUTES;
	public static final int ONE_DAY_SECONDS = ONE_DAY_MINUTES * ONE_MINUTE_SECONDS;
	public static final int ONE_DAY_MILLISECONDS = ONE_DAY_SECONDS * ONE_SECOND_MILLISECONDS;
	
	public static final String FORMAT1 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT2 = "yyyy/MM/dd HH:mm:ss";
	public static final String FORMAT3 = "yyyy-MM-dd HH:mm:ss EEE";
	public static final String FORMAT4 = "yyyy/MM/dd HH:mm:ss EEE";
    public static final String FORMAT5 = "yyyy-MM-dd";
    
	/**
	 * 日期格式化
	 * @param format
	 * @param time
	 * @return
	 */
	public static String format(String format, long time) {
		return new SimpleDateFormat(format).format(time).toString();
	}
	
	public static String format(String format,Date date) {
		return new SimpleDateFormat(format).format(date).toString();
	}
	
	/**
	 * 将时间格式化成中国式的字符串(xxxx年xx月xx日xx点xx分xx秒)
	 * @param time
	 * @return
	 */
	public static String formatChineseSecond(long time) {
		Calendar calendar = getCalendar(time);
		return String.format("%d年%d月%d日%d点%d分%d秒", getYear(calendar), getMonth(calendar), getDate(calendar), getHour(calendar), getMinute(calendar), getSecond(calendar));
	}
	
	/**
	 * 将时间格式化成中国式的字符串(xxxx年xx月xx日xx点xx分)
	 * @param time
	 * @return
	 */
	public static String formatChineseMinute(long time) {
		Calendar calendar = getCalendar(time);
		return String.format("%d年%d月%d日%d点%d分", getYear(calendar), getMonth(calendar), getDate(calendar), getHour(calendar), getMinute(calendar));
	}
	
	/**
	 * 将时间格式化成中国式的字符串(xxxx年xx月xx日xx点)
	 * @param time
	 * @return
	 */
	public static String formatChineseHour(long time) {
		Calendar calendar = getCalendar(time);
		return String.format("%d年%d月%d日%d点", getYear(calendar), getMonth(calendar), getDate(calendar), getHour(calendar));
	}
	
	/**
	 * 将时间格式化成中国式的字符串(xxxx年xx月xx日)
	 * @param time
	 * @return
	 */
	public static String formatChineseDate(long time) {
		Calendar calendar = getCalendar(time);
		return String.format("%d年%d月%d日", getYear(calendar), getMonth(calendar), getDate(calendar));
	}
	
	/**
	 * 将时间格式化成中国式的字符串(xxxx年xx月)
	 * @param time
	 * @return
	 */
	public static String formatChineseMonth(long time) {
		Calendar calendar = getCalendar(time);
		return String.format("%d年%d月", getYear(calendar), getMonth(calendar));
	}
	
	/**
	 * 将时间格式化成中国式的字符串(xxxx年)
	 * @param time
	 * @return
	 */
	public static String formatChineseYear(long time) {
		Calendar calendar = getCalendar(time);
		return String.format("%d年", getYear(calendar));
	}
	
	/**
	 * 根据年月日获取时间
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static long getTime(int year, int month, int date) {
		return getTime(year, month, date, 0, 0, 0);
	}
	
	/**
	 * 根据年月日时分秒获取时间
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static long getTime(int year, int month, int date, int hour, int minute, int second) {
		return getTime(year, month, date, hour, minute, second, 0);
	}
	
	/**
	 * 根据年月日时分秒毫秒获取时间
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public static long getTime(int year, int month, int date, int hour, int minute, int second, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1 + Calendar.JANUARY);
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return calendar.getTimeInMillis();
	}
	
	public static Calendar getCalendar(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar;
	}
	
	/**
	 * 获取年份
	 * @param time
	 * @return
	 */
	public static int getYear(long time) {
		return getYear(getCalendar(time));
	}
	
	/**
	 * 获取月份
	 * @param time
	 * @return
	 */
	public static int getMonth(long time) {
		return getMonth(getCalendar(time));
	}
	
	/**
	 * 获取天
	 * @param time
	 * @return
	 */
	public static int getDate(long time) {
		return getDate(getCalendar(time));
	}
	
	/**
	 * 获取时
	 * @param time
	 * @return
	 */
	public static int getHour(long time) {
		return getHour(getCalendar(time));
	}
	
	/**
	 * 获取分
	 * @param time
	 * @return
	 */
	public static int getMinute(long time) {
		return getMinute(getCalendar(time));
	}
	
	/**
	 * 获取秒
	 * @param time
	 * @return
	 */
	public static int getSecond(long time) {
		return getSecond(getCalendar(time));
	}
	
	/**
	 * 获取毫秒
	 * @param time
	 * @return
	 */
	public static int getMillisecond(long time) {
		return getMillisecond(getCalendar(time));
	}
	
	/**
	 * 获取星期
	 * @param time
	 * @return
	 */
	public static int getWeek(long time) {
		return getWeek(getCalendar(time));
	}
	
	/**
	 * 获取年份
	 * @param time
	 * @return
	 */
	public static int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取月份
	 * @param time
	 * @return
	 */
	public static int getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONTH) - Calendar.JANUARY + 1;
	}
	
	/**
	 * 获取天
	 * @param time
	 * @return
	 */
	public static int getDate(Calendar calendar) {
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取时
	 * @param time
	 * @return
	 */
	public static int getHour(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取分
	 * @param time
	 * @return
	 */
	public static int getMinute(Calendar calendar) {
		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取秒
	 * @param time
	 * @return
	 */
	public static int getSecond(Calendar calendar) {
		return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 获取毫秒
	 * @param time
	 * @return
	 */
	public static int getMillisecond(Calendar calendar) {
		return calendar.get(Calendar.MILLISECOND);
	}
	
	/**
	 * 获取星期
	 * @param time
	 * @return
	 */
	public static int getWeek(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 获取一年的天数
	 * @param year
	 * @return
	 */
	public static int getYearDateCount(int year) {
		//闰年的计算方式：是100倍数的年份并且是400的倍数，则为闰年；是100倍数的年份不是400倍数的则为平年;
		//非100倍数的年份并且是4的倍数，则为闰年；否则为平年。
		if(year % 100 == 0 && year % 400 == 0) {
			return 366;
		} else if(year % 100 == 0 && year % 400 != 0) {
			return 365;
		} else if(year % 4 == 0) {
			return 366;
		} else {
			return 365;
		}
	}
	
	/**
	 * 获取指定月的天数
	 * @param year 年份，如果month为2必须要传，其他月份可传0
	 * @param month
	 * @return
	 */
	public static int getMonthDateCount(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else if (month == 2) {
            return getYearDateCount(year) == 366 ? 29 : 28;
        } else {
            return 0;
        }
	}
	
	/**
	 * 获取天数
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int getDateCount(long beginTime, long endTime) {
		return (endTime - beginTime) % ONE_DAY_MILLISECONDS == 0 ? 
				(int)((endTime - beginTime) / ONE_DAY_MILLISECONDS) : (int)((endTime - beginTime) / ONE_DAY_MILLISECONDS) + 1;
	}
	
	/**
	 * 获取一年的起始时间
	 * @param year
	 * @return
	 */
	public static long getYearBeginTime(int year) {
		return getTime(year, 1, 1);
	}
	
	/**
	 * 获取一年的结束时间
	 * @param year
	 * @return
	 */
	public static long getYearEndTime(int year) {
		return getTime(year, 12, 31, 23, 59, 59, 999);
	}
	
	/**
	 * 获取一月的开始时间
	 * @param year
	 * @param month
	 * @return
	 */
	public static long getMonthBeginTime(int year, int month) {
		return getTime(year, month, 1);
	}
	
	/**
	 * 获取一月的结束时间
	 * @param year
	 * @param month
	 * @return
	 */
	public static long getMonthEndTime(int year, int month) {
		return getTime(year, month, getMonthDateCount(year, month), 23, 59, 59, 999);
	}
	
	/**
	 * 获取一天的开始时间
	 * @param time
	 * @return
	 */
	public static long getDateBeginTime(int year, int month, int date) {
		return getTime(year, month, date);
	}
	
	/**
	 *  获取一天的开始时间
	 * @param time
	 * @return
	 */
	public static long getDateBeginTime(long time) {
		return getDateBeginTime(getYear(time), getMonth(time), getDate(time));
	}
	
	/**
	 * 获取一天的结束时间
	 * @param time
	 * @return
	 */
	public static long getDateEndTime(int year, int month, int date) {
		return getTime(year, month, date, 23, 59, 59, 999);
	}
	
	/**
	 *  获取一天的结束时间
	 * @param time
	 * @return
	 */
	public static long getDateEndTime(long time) {
		return getDateEndTime(getYear(time), getMonth(time), getDate(time));
	}
	
	/**
	 * 获取第二天开始时间
	 * @param time
	 * @return
	 */
	public static long getNextDateBeginTime(long time) {
		return getDateEndTime(time) + 1;
	}
	
	/**
	 * 是否同一年
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameYear(long time1, long time2) {
		return getYear(time2) == getYear(time2);
	}
	
	/**
	 * 是否同一月
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameMonth(long time1, long time2) {
		return formatChineseMonth(time1).equals(formatChineseMonth(time2));
	}
	
	/**
	 * 是否同一天
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameDate(long time1, long time2) {
		return formatChineseDate(time1).equals(formatChineseDate(time2));
	}
	
	/**
	 * 是否同一时
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameHour(long time1, long time2) {
		return formatChineseHour(time1).equals(formatChineseHour(time2));
	}
	
	/**
	 * 是否同一分
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameMinute(long time1, long time2) {
		return formatChineseMinute(time1).equals(formatChineseMinute(time2));
	}
	
	/**
	 * 是否同一秒
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameSecond(long time1, long time2) {
		return formatChineseSecond(time1).equals(formatChineseSecond(time2));
	}
	
	/**
	 * 是否今天
	 * @param time
	 * @return
	 */
	public static boolean isToday(long time) {
		return isSameDate(time, System.currentTimeMillis());
	}
	
	/**
	 * 是否今天
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static boolean isToday(int year, int month, int date) {
		return isToday(getTime(year, month, date));
	}
	
	/**
	 * 解析时间，时间格式为(年月日xxx)
	 * @param date
	 * @return
	 */
	public static long parseDate(String date) {
		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;
		
		if(TextUtils.isEmpty(date)) {
			return 0;
		}
		
		try {
			String[] fileds = date.split("[-/ :]");
			if(fileds == null || fileds.length <= 1) {
				if(date.length() >= 4) {
					year = Integer.valueOf(date.substring(0, 4));
				}
				if(date.length() >= 6) {
					month = Integer.valueOf(date.substring(4, 6));
				}
				if(date.length() >= 8) {
					day = Integer.valueOf(date.substring(6, 8));
				}
				if(date.length() >= 10) {
					hour = Integer.valueOf(date.substring(8, 10));
				}
				if(date.length() >= 12) {
					minute = Integer.valueOf(date.substring(10, 12));
				}
				if(date.length() >= 14) {
					second = Integer.valueOf(date.substring(12, 14));
				}
			} else {
				if(fileds.length >= 1) {
					year = Integer.valueOf(fileds[0]);
				}
				if(fileds.length >= 2) {
					month = Integer.valueOf(fileds[1]);
				}
				if(fileds.length >= 3) {
					day = Integer.valueOf(fileds[2]);
				}
				if(fileds.length >= 4) {
					hour = Integer.valueOf(fileds[3]);
				}
				if(fileds.length >= 5) {
					minute = Integer.valueOf(fileds[4]);
				}
				if(fileds.length >= 6) {
					second = Integer.valueOf(fileds[5]);
				}
			}
		} catch (Exception e) {
			return 0;
		}
		return getTime(year, month, day, hour, minute, second);
	}
	
	   public static String formatDate(long time) {
	        if (time <= 0) {
	            return null;
	        } else {
	            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time)
	                    .toString();
	        }
	    }


	public static String formatDateWithoutTime(long time) {
		if (time <= 0) {
			return null;
		} else {
			return new SimpleDateFormat("yyyyMMdd").format(time)
					.toString();
		}
	}
}
