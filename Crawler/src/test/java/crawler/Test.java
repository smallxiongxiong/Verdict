package crawler;

import java.util.HashMap;
import java.util.Map;

import com.law.verdict.crawler.VerdictCrawler;

public class Test {
	public static void main(String[] args) {
		VerdictCrawler crawler = new VerdictCrawler();
		String url = "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";
		Map<String, String> specialParams =crawler.getSpecialParams(url);
		Map<String, String> params = new HashMap<String,String>();
		params.put("Index", "1");
		params.put("Page", "20");
		params.put("Param", "裁判日期:2018-03-01 TO 2018-03-01");
		params.put("Order", "法院层级");
		params.put("Direction", "asc");
		params.put("vl5x", specialParams.get("vl5x"));
		String list = crawler.getContentList("http://wenshu.court.gov.cn/List/ListContent",params,specialParams.get("vjkl5"));
		Map<String, String> params2 = new HashMap<String,String>();
		params2.put("docId", "8252121f-8260-4241-b707-018d52d151ca");
		params2.put("court", "最高人民法院");
		params2.put("caseNumber", "（2012）刑监字第182-1号");
		params2.put("caseType", "1");
		String related = crawler.getRelatedFiles("http://wenshu.court.gov.cn/Content/GetRelateFiles",params2,specialParams.get("vjkl5"));
		System.out.println("related==="+related);
		String summary = crawler.getSummary("http://wenshu.court.gov.cn/Content/GetSummary","8252121f-8260-4241-b707-018d52d151ca",specialParams.get("vjkl5"));
		System.out.println("summary==="+summary);
		String detail = crawler.getContentDetail("http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx","8252121f-8260-4241-b707-018d52d151ca",specialParams.get("vjkl5"));
		System.out.println("detail==="+detail);
	}

}
