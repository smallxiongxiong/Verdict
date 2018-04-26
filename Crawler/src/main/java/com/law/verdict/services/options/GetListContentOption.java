package com.law.verdict.services.options;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.law.verdict.constant.CrawlerConstant;
import com.law.verdict.crawler.VerdictCrawler;
import com.law.verdict.parse.Parse;
import com.law.verdict.parse.model.JudgementSimple;

public class GetListContentOption extends CrawlerOption<Map<String, Object>> implements Callable<Map<String, Object>> {

	private VerdictCrawler crawler;
	private Map<String, String> params;
	private String keyVjkl5;

	public GetListContentOption(VerdictCrawler crawler, Map<String, String> params, String keyVjkl5) {
		super();
		this.crawler = crawler;
		this.params = params;
		this.keyVjkl5 = keyVjkl5;
	}

	@Override
	public Map<String, Object> done() {
		String content = crawler.getContentList(CrawlerConstant.URL_LIST_CONTENT, this.params, keyVjkl5);
		List<JudgementSimple> contentList = Parse.parseListContent(content);
		Map<String, Object> result = new HashMap<String, Object>();
		if (null == contentList) {
			result.put(CrawlerConstant.KEY_MAP_RESULT, false);
		} else {
			result.put(CrawlerConstant.KEY_MAP_RESULT, true);
			result.put(CrawlerConstant.KEY_MAP_DATA, contentList);

		}
		return result;
	}

}
