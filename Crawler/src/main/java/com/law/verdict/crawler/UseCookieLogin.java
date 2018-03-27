package com.law.verdict.crawler;

import java.util.Map;

public class UseCookieLogin {

	public static void main(String[] args) {
		Map map = Cookies.getCookies(
				"http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6");
		String sr = HttpRequest.sendPost("http://wenshu.court.gov.cn/List/ListContent", "Param=案件类型:刑事案件&Index=1&Page=5&Order=法院层级&Direction=asc&number= &guid= ",map);
		System.out.println(sr);
	}

}