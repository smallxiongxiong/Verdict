package com.law.verdict.tools;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hankcs.hanlp.seg.common.Term;

public class StringTool {
	public static String termList2String(List<Term> list){
		return list.toString();
	}
	
	
	/**
	 * 用来判断是否是ip地址
	 * @param value
	 * @return
	 */
	public static boolean isIPAddress(String value) {

		String patternStr = "/^(([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\/(\\d{1}|[0-2]{1}\\d{1}|3[0-2])$/";
		Pattern p = Pattern.compile(patternStr);
		Matcher m = p.matcher(value);
		return m.find();
	}
}
