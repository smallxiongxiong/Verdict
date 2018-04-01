package com.law.verdict.crawler;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.os.WindowsUtils;

import com.law.verdict.parse.Parse;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.utils.DateTools;
import com.law.verdict.utils.FileTools;
import com.law.verdict.utils.StringTools;

public class VerdictMain {
	public static void main(String[] args) throws ParseException {
		VerdictCrawler crawler = new VerdictCrawler();
		String url = "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";
		Map<String, String> specialParams = crawler.getSpecialParams(url);
		Map<String, String> params = new HashMap<String, String>();

		List<String> causeOfActionKey = FileTools.readTolines(new File("src/main/resources/casedict.txt"));
		List<String> treeKeyWord = FileTools.readTolines(new File("src/main/resources/treeKeyWord.txt"));
		// List<String> dataStrs = DateTools.getScopeDay("2016-01-01", 180,4);

		// List<String> dataStrs = DateTools.getScopeDay("2016-01-01", 30, 9);

		String data1 = "2016-01-01";
		Calendar cal = Calendar.getInstance();
		int scope = 30;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(data1);
		cal.setTime(date);
		int times = 1;
		for (String cause : causeOfActionKey) {
			times = 1; //// 2018-04-02
			int Index = 1;
			params.put("Index", Index + "");
			params.put("Page", "20");
			params.put("Order", "法院层级");
			params.put("Direction", "asc");
			params.put("vl5x", specialParams.get("vl5x"));
			String list = "";
			do {
				System.out.println("=====: " + scope + "  " + times);
				cal.setTime(date);
				cal.set(Calendar.DATE, scope * times);
				String data2 = sdf.format(cal.getTime());
				params.put("Param", "案由:" + cause + ",裁判日期:" + data1 + " TO " + data2);//// 关键词:继承,案由:遗嘱继承纠纷,裁判日期:2018-04-02
				String fileName = params.get("Param").replace(":", "_").replace("-", "").replace(" ", "").replace(",",
						"_") + "_" + Index;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(params.toString() + "----------------------------------------------------");
				list = crawler.getContentList("http://wenshu.court.gov.cn/List/ListContent", params,
						specialParams.get("vjkl5"));
				System.out.println("list content:" + list);
				if (list.startsWith("RF")) {
					specialParams = crawler.getSpecialParams(url);
					params.put("vl5x", specialParams.get("vl5x"));
					continue;
				} else if (list.length() > 22) {
					FileTools.write("D:" + File.separator + "verdict", fileName, list, false);
					List<JudgementSimple> jsList = Parse.parseListContent(list);
					System.out.println("****************************" + jsList.size());
					for (JudgementSimple js : jsList) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String detail = crawler.getContentDetail(
								"http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx", js.getCaseID(),
								specialParams.get("vjkl5"));
						FileTools.write("D:" + File.separator + "verdict" + File.separator + "details", js.getCaseID(),
								detail, false);
					}
					if (StringTools.isResponseOK(list)) {
						Index++;
					}
				} else {
					times *= 2;
				}
				System.out.println("Time: " + data1 + "  " + data2);
				params.put("Index", Index + "");
				if (sdf.parse(data2).after(new Date())) {
					break;
				}
			} while (!list.startsWith("RF"));

		}

		WindowsUtils.killByName("chrome.exe");
	}
}
