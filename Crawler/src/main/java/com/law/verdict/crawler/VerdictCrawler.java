package com.law.verdict.crawler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.law.verdict.constant.CrawlerConstant;
import com.law.verdict.utils.JavaScriptTools;

public class VerdictCrawler {
	private static Logger logger = LoggerFactory.getLogger(VerdictCrawler.class);

	public VerdictCrawler() {
		super();
	}
	
	/**
	 * 获取关键词列表
	 * @param baseUrl
	 * @param params
	 * @param vjkl5
	 * @return
	 */
	public String getTreeContent(String baseUrl, Map<String, String> params, String vjkl5) {
		
		return null;
	}
 
	public String getContentList(String baseUrl, Map<String, String> params, String vjkl5) {
		String guid = (String) JavaScriptTools.executeJsFile(CrawlerConstant.PATH_JS_GUID, "getGuid", new HashMap<>());
		String number = HttpRequest.sendPost("http://wenshu.court.gov.cn/ValiCode/GetCode", "guid=" + guid, vjkl5);
		params.put("guid", guid);
		params.put("number", number);
		String paramStr = formatParam(params);
		logger.info("getContentList: url: {}, param: {}, vjkl5: {}", baseUrl, paramStr, vjkl5);
		return HttpRequest.sendPost(baseUrl, paramStr, vjkl5);
	}

	private String formatParam(Map<String, String> params) {
		String resStr = "";
		for (Entry<String, String> en : params.entrySet()) {
			try {
				resStr += "&" + en.getKey() + "=" + URLEncoder.encode(en.getValue(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.error("Format Params error!", e);
			}
		}
		return resStr.substring(1);
	}

	public String getRelatedFiles(String url, Map<String, String> params2, String cookie) {
		logger.info("docID: {}, get RelatedFiles by get method", params2.get("docId"));
		String paramStr = formatParam(params2);
		return HttpRequest.sendPost(url, paramStr, cookie);
	}

	public String getSummary(String url, String docId, String cooke) {
		logger.info("docID: {},  get getSummary by get method", docId);
		return HttpRequest.sendPost(url, "docId=" + docId, cooke);
	}

	public String getContentDetail(String url, String docId, String cookie) {
		logger.info("docID: {},  get detail Content by get method", docId);
		return HttpRequest.sendGet(url, "DocID=" + docId, cookie);
	}
	
	

}
