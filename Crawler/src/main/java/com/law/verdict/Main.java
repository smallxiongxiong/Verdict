package com.law.verdict;

import java.util.HashMap;
import java.util.Map;

import com.law.verdict.crawler.Cookies;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello Word");
		String url = "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";
		//Map<String, String> cookie = Cookies.getCookies(url);
		//System.out.println(cookie.toString());
		Map<String, String> cookie = new HashMap<String, String>();
		cookie.put("vjkl5", "648f9dffb367410105180e13de7acce909d16740");
		cookie.put("vl5x", "1a295ff6179c9c81bbffe424");
		
	}
}
