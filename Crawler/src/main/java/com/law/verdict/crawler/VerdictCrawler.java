package com.law.verdict.crawler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class VerdictCrawler {
	public WebDriver driver;
	public VerdictCrawler() {
		super();
		this.driver = DriverFactory.create();
	}
	public Map<String, String> getSpecialParams(String url) {
		Map<String, String> res = new HashMap<String, String>();
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
		return res;
	}
	public String getContentList(String baseUrl,Map<String,String> params,String vjkl5) {
		String guid = (String) ((JavascriptExecutor) driver).executeScript(
				"return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)+'-'+(((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)+'-'+(((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)+'-'+(((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);");
		String number = HttpRequest.sendPost("http://wenshu.court.gov.cn/ValiCode/GetCode", "guid=" + guid, vjkl5);
		params.put("guid", guid);
		params.put("number", number);
		String paramStr = formatParam(params);
		return HttpRequest.sendPost(baseUrl,paramStr,vjkl5);
	}
	private String formatParam(Map<String,String> params) {
		String resStr = "";
		for(Entry<String,String> en : params.entrySet()){
			try {
				resStr += "&"+en.getKey()+"="+ URLEncoder.encode(en.getValue(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(" Format Params error!");
			} 
		}
		return resStr.substring(1);
	}
	public void getHtml() {
		// TODO Auto-generated method stub
		
	}
	public String getRelatedFiles(String url, Map<String, String> params2, String cookie) {
		String paramStr = formatParam(params2);
		return HttpRequest.sendPost(url,paramStr,cookie);
	}
	public String getSummary(String url, String docId, String cooke) {
		return HttpRequest.sendPost(url,"docId="+docId,cooke);
	}
	public String getContentDetail(String url, String docId, String cookie) {
		return HttpRequest.sendGet(url,"DocID="+docId,cookie);
	}
	
	
}
