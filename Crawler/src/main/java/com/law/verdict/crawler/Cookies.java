package com.law.verdict.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.os.WindowsUtils;

public class Cookies {
	public static Map getCookies(String url) {
		// "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6"
		Map<String, String> res = new HashMap<String, String>();
		WebDriver driver = DriverFactory.create();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String vl5x = (String) ((JavascriptExecutor) driver).executeScript("return getKey();");
		res.put("vl5x", vl5x);
		for (Cookie ck : driver.manage().getCookies()) {
			if ("vjkl5".equals(ck.getName())) {
				res.put("vjkl5", ck.getValue().trim());
			}
		}
		WindowsUtils.killByName("chrome.exe");
		return res;
	}

}
