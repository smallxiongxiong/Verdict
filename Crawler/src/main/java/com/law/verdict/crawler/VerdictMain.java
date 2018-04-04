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

import com.law.verdict.constant.CrawlerConstant;
import com.law.verdict.parse.Parse;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.utils.FileTools;
import com.law.verdict.utils.JavaScriptTools;
import com.law.verdict.utils.StringTools;

public class VerdictMain {
	public static void main(String[] args) throws ParseException {
		VerdictCrawler crawler = new VerdictCrawler();
		String url = "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";
		Map<String, String> specialParams = JavaScriptTools.getCookiesByJsFile(CrawlerConstant.PATH_JS_COOKIE);
		Map<String, String> params = new HashMap<String, String>();
		List<String> causeOfActionKey = FileTools.readTolines(new File("src/main/resources/casedict.txt"));
		List<String> isCrawlerWords = FileTools.readTolines(new File("src/main/resources/isCrawlerWords.txt"));
		causeOfActionKey.removeAll(isCrawlerWords);
		
		String caseType="民事案件";
		//String data1 = "2014-01-01";
		Calendar cal = Calendar.getInstance();
		int scope = 7;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//Date date = sdf.parse(data1);
		//cal.setTime(date);
		int times = 1;
		for (String cause : causeOfActionKey) {
			String data1 = "2014-01-01";
//			Date date = sdf.parse(data1);
//			cal.setTime(date);
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
				cal.setTime(sdf.parse(data1));
				cal.set(Calendar.DATE, scope * times);
				String data2 = sdf.format(cal.getTime());
				System.out.println("Time: " + data1 + "  " + data2);
				params.put("Param", "案件类型:"+caseType+",案由:" + cause + ",裁判日期:" + data1 + " TO " + data2);//// 关键词:继承,案由:遗嘱继承纠纷,裁判日期:2018-04-02
				String fileName = params.get("Param").replace(":", "_").replace("-", "").replace(" ", "").replace(",",
						"_") + "_" + Index;
				File listFileName = new File("D:" + File.separator + "verdict"+File.separator+fileName);
				if(listFileName.exists()){
					System.out.println(listFileName+": has exist!!");
					Index++;
					continue;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(params.toString() + "----------------------------------------------------");
				list = crawler.getContentList("http://wenshu.court.gov.cn/List/ListContent", params,
						specialParams.get("vjkl5"));
				System.out.println("list content:" + list);
				if (list.startsWith("RF")||list.contains("remind key")) {
					specialParams = JavaScriptTools.getCookiesByJsFile(CrawlerConstant.PATH_JS_COOKIE);
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
						File detailName = new File("D:" + File.separator + "verdict" + File.separator + caseType+File.separator +cause+File.separator+js.getCaseID());
						if(detailName.exists()){
							System.out.println(detailName+"has exist!!");
							continue;
						}
						String detail = crawler.getContentDetail(
								"http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx", js.getCaseID(),
								specialParams.get("vjkl5"));
						FileTools.write("D:" + File.separator + "verdict" + File.separator + caseType.trim()+File.separator+cause.trim(), js.getCaseID(),
								detail, false);
					}
					if (StringTools.isResponseOK(list)) {
						Index++;
					}
				} else {
					data1=data2;
					Index =1;
					times *= 2;
				}
				//System.out.println("Time: " + data1 + "  " + data2);
				params.put("Index", Index + "");
				if (sdf.parse(data2).after(new Date())) {
					break;
				}
				if(Index==100){
					times =1;
				}
			} while (!list.startsWith("RF"));
			
			FileTools.writeAndChangeRow(new File("src/main/resources/isCrawlerWords.txt"), cause, true);

		}

		WindowsUtils.killByName("chrome.exe");
	}
}
