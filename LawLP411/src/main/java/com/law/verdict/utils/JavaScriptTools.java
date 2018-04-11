package com.law.verdict.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaScriptTools {
	private static Logger logger = LoggerFactory.getLogger(JavaScriptTools.class);
	
	
	
	/**
	 * 
	 * @param path js文件执行路径
	 * @param method 执行的方法名
	 * @param param  方法参数
	 * @return
	 */
	public static Object executeJsFile(String path, String method, Map<String, Object> param) {
		Object result = null;
		ScriptEngineManager m = new ScriptEngineManager();
		// 获取JavaScript执行引擎
		ScriptEngine engine = m.getEngineByName("js");
		// 执行JavaScript代码
		String jsStr = readFile(path);
		try {
		    Set<Entry<String,Object>> entries = param.entrySet();
		    for(Entry<String,Object> item: entries) {
		    	engine.put(item.getKey(), item.getValue());
		    }
			engine.eval(jsStr);
			Invocable invocable = (Invocable) engine;
			result = invocable.invokeFunction(method);
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static Map<String, String> getCookiesByJsFile(String path) {
		logger.info("get Cookies value");
		Map<String, String> result = new HashMap<>();
		ScriptEngineManager m = new ScriptEngineManager();
		// 获取JavaScript执行引擎
		ScriptEngine engine = m.getEngineByName("js");
		// 执行JavaScript代码
		String jsParam = getParam();
		engine.put("vjkl5", jsParam);
		String jsStr = readFile(path);
		try {
			engine.eval(jsStr);
			Invocable invocable = (Invocable) engine;
			String vl5x = String.valueOf(invocable.invokeFunction("getKey"));
			result.put("vl5x", vl5x);
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("vjkl5", jsParam);
		logger.info("vjkl5 value: {}", jsParam);
		return result;
	}

	private static String getParam() {
		String result = "";
		try {
			URL url = new URL(
					"http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+2+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E6%B0%91%E4%BA%8B%E6%A1%88%E4%BB%B6");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");// 网页提交方式“GET”、“POST”
			String cookie = connection.getHeaderField("Set-Cookie");
			String[] tmpArray = cookie.split(";");
			for (String element : tmpArray) {
				String[] item = element.split("=");
				if ("vjkl5".equals(item[0])) {
					result = item[1];
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("get vjkl5 error, ", e);
		}
		logger.info("get vjkl5 value: {}", result);
		return result;
	}

	private static String readFile(String path) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
			String line = null;
			while (null != (line = br.readLine())) {
				sb.append(line).append(System.getProperty("line.separator"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
