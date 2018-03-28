package com.law.verdict.crawler.GC;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.openqa.selenium.os.WindowsUtils;

import com.law.verdict.crawler.Cookies;
import com.law.verdict.crawler.HttpRequest;
import com.law.verdict.utils.FileUtils;

public class UseCookieLogin {

	public static void main(String[] args) {
		Map map = Cookies.getCookies(
				"http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6");
		int index=1;
		int page =5;
		String key ="%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B%3A%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";
		String sr = "\"[]\"";
		String guid = createGuid()+"-"+createGuid()+"-"+createGuid()+"-"+createGuid();
		do{
			sr = HttpRequest.sendPost("http://wenshu.court.gov.cn/List/ListContent", "Param=%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B%3A%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6&Index="+index+"&Page="+page+"&Order=%E6%B3%95%E9%99%A2%E5%B1%82%E7%BA%A7&Direction=asc&number= &guid= "+"&vl5x="+map.get("vl5x"),map);
		try {
				String output = URLDecoder.decode(key, "UTF-8").replace(":", "_");
				File file = new File("D:\\verdict\\"+output+"_"+index);
				FileUtils.write(file, sr);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			index++;
		}while(!"[]".equals(sr));
		System.out.println(sr);
		WindowsUtils.killByName("chrome.exe");
	}

	private static String createGuid() {
		//double a =((1+Math.random())* 0x10000 | 0);
		//return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
		return "";
	}

}