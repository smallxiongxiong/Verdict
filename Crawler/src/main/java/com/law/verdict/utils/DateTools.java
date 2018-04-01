package com.law.verdict.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTools {
	/**
	 * @param dateStr  yyyy-MM-dd格式的字符时间 eg:2018-04-01
	 * @param scope    
	 * @return
	 */
	public static List<String> getScopeDay(String dateStr, int scope) {
		ArrayList<String> result = new ArrayList<>();
		result.add(dateStr);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = sdf.parse(dateStr);
			cal.setTime(date);
			int i = 0;
			scope--;
			while (i < scope) {
				int day = cal.get(Calendar.DATE);
				cal.set(Calendar.DATE, day + 1);
				Date tmp = cal.getTime();
				result.add(sdf.format(tmp));
				i++;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
}
