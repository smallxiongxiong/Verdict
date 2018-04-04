package com.law.verdict;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.law.verdict.constant.CrawlerConstant;
import com.law.verdict.crawler.VerdictCrawler;
import com.law.verdict.parse.Parse;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.services.CrawlerServices;
import com.law.verdict.utils.FileTools;
import com.law.verdict.utils.JavaScriptTools;
import com.law.verdict.utils.StringTools;

@SpringBootApplication
@MapperScan("com.law.verdict.parse.dao")
public class CrawlerMain implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(CrawlerMain.class);
	private static final String PRE_FILE_PATH = "E:" + File.separator + "verdict";
	@Autowired
	CrawlerServices crawlerServices;

	public static void main(String[] args) throws Exception {
		System.out.println("======================");
		SpringApplication.run(CrawlerMain.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("======================");
		Runnable target = new CrawlerTask("刑事案件");
		Thread thread = new Thread(target);
		thread.start();
	}

	class CrawlerTask implements Runnable {

		private String caseType;

		public CrawlerTask() {
		}

		public CrawlerTask(String caseType) {
			super();
			this.caseType = caseType;
		}

		@Override
		public void run() {
			System.out.println("====================== beginCrawler===========");
			VerdictCrawler crawler = new VerdictCrawler();
			Map<String, String> specialParams = JavaScriptTools.getCookiesByJsFile(CrawlerConstant.PATH_JS_COOKIE);
			Map<String, String> params = new HashMap<String, String>();
			List<String> causeOfActionKey = FileTools.readTolines(new File(CrawlerConstant.PATH_CASE_DICT));
			List<String> isCrawlerWords = FileTools.readTolines(new File(CrawlerConstant.PATH_CRAWLER_WORDS));
			causeOfActionKey.removeAll(isCrawlerWords);

			String caseType = this.caseType;
			// String data1 = "2014-01-01";
			Calendar cal = Calendar.getInstance();
			int scope = 7;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int times = 1;
			for (String cause : causeOfActionKey) {
				String data1 = "2014-01-01";
				times = 1; //// 2018-04-02
				int Index = 1;
				params.put("Index", Index + "");
				params.put("Page", "20");
				params.put("Order", "法院层级");
				params.put("Direction", "asc");
				params.put(CrawlerConstant.KEY_VL5X, specialParams.get(CrawlerConstant.KEY_VL5X));
				String list = "";
				do {
					try {
						cal.setTime(sdf.parse(data1));
					} catch (ParseException e1) {
						logger.error("sdf parse error, value:" + data1, e1);
					}
					cal.set(Calendar.DATE, scope * times);
					String data2 = sdf.format(cal.getTime());
					System.out.println("Time: " + data1 + "  " + data2);
					params.put("Param", "案件类型:" + caseType + ",案由:" + cause + ",裁判日期:" + data1 + " TO " + data2);//// 关键词:继承,案由:遗嘱继承纠纷,裁判日期:2018-04-02
					String fileName = params.get("Param").replace(":", "_").replace("-", "").replace(" ", "")
							.replace(",", "_") + "_" + Index;
					File listFileName = new File(PRE_FILE_PATH + File.separator + fileName);
					if (listFileName.exists()) {
						System.out.println(listFileName + ": has exist!!");
						Index++;
						continue;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					list = crawler.getContentList("http://wenshu.court.gov.cn/List/ListContent", params,
							specialParams.get(CrawlerConstant.KEY_VJKL5));

					logger.info(params.toString());
					logger.info(list.toString());
					if (list.startsWith("RF") || list.contains("remind key")) {
						specialParams = JavaScriptTools.getCookiesByJsFile(CrawlerConstant.PATH_JS_COOKIE);
						params.put(CrawlerConstant.KEY_VL5X, specialParams.get(CrawlerConstant.KEY_VL5X));
						continue;
					} else if (list.length() > 22) {
						FileTools.write(PRE_FILE_PATH, fileName, list, false);
						List<JudgementSimple> jsList = Parse.parseListContent(list);
						logger.info("paser jsList size: {}", jsList.size());

						for (JudgementSimple js : jsList) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							File detailName = new File(PRE_FILE_PATH + File.separator + caseType + File.separator
									+ cause + File.separator + js.getCaseID());
							if (detailName.exists()) {
								logger.info("{} has exist! Next", detailName);
								continue;
							}
							String detail = crawler.getContentDetail(CrawlerConstant.URL_DETAIL_CONTENT, js.getCaseID(),
									specialParams.get(CrawlerConstant.KEY_VJKL5));
							crawlerServices.addToDb(js.getCaseID(), detail);
						}
						if (StringTools.isResponseOK(list)) {
							Index++;
						}
					} else {
						data1 = data2;
						Index = 1;
						times *= 2;
					}
					params.put("Index", Index + "");
					try {
						if (sdf.parse(data2).after(new Date())) {
							break;
						}
					} catch (ParseException e) {
						e.printStackTrace();
						break;
					}
					if (Index == 100) {
						times = 1;
					}
				} while (!list.startsWith("RF"));

				FileTools.writeAndChangeRow(new File("src/main/resources/isCrawlerWords.txt"), cause, true);

			}
			System.out.println("====================== endCrawler===========");
		}

	}
}
