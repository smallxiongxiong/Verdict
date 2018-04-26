package com.law.verdict.services.options;

import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.law.verdict.constant.CrawlerConstant;
import com.law.verdict.utils.JavaScriptTools;

/**
 * 獲取cookies的值
 * 
 * @author Fijon
 *
 */
public class GetCookiesOptions extends CrawlerOption<Map<String, String>> implements Callable<Map<String, String>> {
	private Logger log = LoggerFactory.getLogger(GetCookiesOptions.class);

	@Override
	public Map<String, String> done() {
		Map<String, String> result = JavaScriptTools.getCookiesByJsFile(CrawlerConstant.PATH_JS_COOKIE);
		log.info("cookies value: {}", result.toString());
		return result;
	}

}
