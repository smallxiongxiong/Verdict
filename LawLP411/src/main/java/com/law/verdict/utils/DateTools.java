package com.law.verdict.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTools {
	/**
	 * 
	 * @param dateStr
	 * @param scope
	 * @param times
	 * @return
	 */
	public static List<String> getScopeDay(String dateStr, int scope, int times) {
		ArrayList<String> result = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(dateStr);
			cal.setTime(date);
			scope--;
			int index = 0;
			String tmpStr = dateStr + ",";
			int count = 0;
			while(index++ < times) {
				if(tmpStr.length() <= 0) {
					Date tmp= cal.getTime();
					tmpStr = sdf.format(tmp) + ",";		
				}else {					
					count +=scope;
					cal.set(Calendar.DATE, count);
					tmpStr+= sdf.format(cal.getTime());
					cal.set(Calendar.DATE, ++count);
					result.add(tmpStr);
					tmpStr = "";
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
}
