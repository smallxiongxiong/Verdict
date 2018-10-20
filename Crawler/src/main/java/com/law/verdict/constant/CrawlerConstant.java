package com.law.verdict.constant;

import java.io.File;

public final class CrawlerConstant {
	public static final String URL_COOKIE_REQUEST = "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";
	public static final String URL_LIST_CONTENT = "http://wenshu.court.gov.cn/List/ListContent";
	public static final String URL_DETAIL_CONTENT = "http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx";
	public static final String URL_RELATED_FILE = "http://wenshu.court.gov.cn/Content/GetRelateFiles";
	public static final String URL_SUMMARY_CONTENT = "http://wenshu.court.gov.cn/Content/GetSummary";

	public static final String PATH_PRE = System.getProperty("pathPre");
	public static final String PATH_PRE_FILE = System.getProperty("pathData");
	public static final String PATH_JS_COOKIE = PATH_PRE + "/javascript/getkey.js";
	public static final String PATH_JS_GUID = PATH_PRE + "/javascript/getGuid.js";
	public static final String PATH_CASE_DICT = PATH_PRE + "/keywords/";
	public static final String PATH_CRAWLER_WORDS = PATH_PRE_FILE + "/haddone/";
	public static final String PATH_UN_SEARCH = PATH_PRE + "/verdict" + File.separator + "unsearch.txt";
	public static final String PATH_UN_CRAWLER = PATH_PRE + "/verdict" + File.separator + "uncralwer.txt";

	public static final String KEY_VJKL5 = "vjkl5";
	public static final String KEY_VL5X = "vl5x";
	public static final String KEY_MAP_DATA = "data";
	public static final String KEY_MAP_RESULT = "result";
	public static final String KEY_INDEX_NAME="index";

}
