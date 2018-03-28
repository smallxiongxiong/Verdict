package com.law.verdict.crawler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.os.WindowsUtils;

import com.law.verdict.parse.Parse;
import com.law.verdict.utils.FileUtils;

public class CrwalerMain {
	public static void main(String[] args) {
		int failedCount = 0;
		String url = "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";
		WebDriver driver = DriverFactory.create();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Map<String, String> res = new HashMap<String, String>();
		String vl5x = (String) ((JavascriptExecutor) driver).executeScript("return getKey();");
		res.put("vl5x", vl5x);
		for (Cookie ck : driver.manage().getCookies()) {
			if ("vjkl5".equals(ck.getName())) {
				res.put("vjkl5", ck.getValue().trim());
			}
		}
		int index = 1;
		int page = 5;
		String key = "%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B%3A%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";//
		String sr = "\"[]\"";
		String guid = " ";
		String number = " ";

		while (failedCount<100) {
			guid = (String) ((JavascriptExecutor) driver).executeScript(
					"return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)+'-'+(((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)+'-'+(((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)+'-'+(((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);");
			number = HttpRequest.sendPost("http://wenshu.court.gov.cn/ValiCode/GetCode", "guid=" + guid, res);
			sr = HttpRequest.sendPost("http://wenshu.court.gov.cn/List/ListContent",
					"Param=%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B%3A%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6&Index=" + index
							+ "&Page=" + page + "&Order=%E6%B3%95%E9%99%A2%E5%B1%82%E7%BA%A7&Direction=asc&number="
							+ number + "&guid=" + guid + "&vl5x=" + res.get("vl5x"),
					res);
			try {
				String output = URLDecoder.decode(key, "UTF-8").replace(":", "_");
				File file = new File("D:\\verdict\\SLC_" + output + "_" + index+"_"+System.currentTimeMillis());
				FileUtils.write(file, sr);
				File parseFile = new File("D:\\verdict\\PLC_" + output + "_" + index+"_"+System.currentTimeMillis());
				FileUtils.write(parseFile, Parse.parseListContent(sr).toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(isFailedRes(sr)){//if failed , try to get new cookie
				driver.get(url);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				res.put("vl5x", (String) ((JavascriptExecutor) driver).executeScript("return getKey();"));
				for (Cookie ck : driver.manage().getCookies()) {
					if ("vjkl5".equals(ck.getName())) {
						res.put("vjkl5", ck.getValue().trim());
					}
				}
				failedCount++;
			}else{
				index++;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		WindowsUtils.killByName("chrome.exe");
	}

	private static boolean isFailedRes(String sr) {
		if("".equals(sr)||"[]".equals(sr)||"\"[]\"".equals(sr)||sr.length()<20)return true;
		return false;
	}

}
