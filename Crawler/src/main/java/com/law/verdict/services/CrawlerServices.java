package com.law.verdict.services;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.verdict.constant.CrawlerConstant;
import com.law.verdict.crawler.VerdictCrawler;
import com.law.verdict.parse.Parse;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.utils.FileTools;
import com.law.verdict.utils.JavaScriptTools;
import com.law.verdict.utils.StringTools;

@Service
public class CrawlerServices {
	private static Logger logger = LoggerFactory.getLogger(CrawlerServices.class);
	private static final String PRE_FILE_PATH = "E:" + File.separator + "verdict";
	private ExecutorService pool = Executors.newCachedThreadPool();
	@Autowired
	CrawlerDBOpt crawlerdb;
	private static final String PATH_UN_CRAWLER = "E:" + File.separator + "verdict" + File.separator + "uncralwer.txt";
	private static final String PATH_UN_SEARCH = "E:" + File.separator + "verdict" + File.separator + "unsearch.txt";
	private static final File FILE_UN_CRAWLER = new File(PATH_UN_CRAWLER);
	private static final File FILE_UN_SEARCH = new File(PATH_UN_SEARCH);

	public void beginCrawler(String caseType) {
		Runnable target = new CrawlerTask(caseType);
		Thread thread = new Thread(target);
		thread.setName("crawler_one");
		thread.start();
		System.out.println("====================== end ======================");
	}

	class CrawlerTask implements Runnable {
		private String caseType;

		public CrawlerTask(String caseType) {
			super();
			this.caseType = caseType;
		}

		@Override
		public void run() {
			logger.info("begin run Crawler, caseType:{}", caseType);
			VerdictCrawler crawler = new VerdictCrawler();
			Map<String, String> specialParams = this.getCookies();
			Map<String, String> params = new HashMap<String, String>();
			List<String> causeOfActionKey = FileTools.readTolines(new File(CrawlerConstant.PATH_CASE_DICT));
			List<String> isCrawlerWords = FileTools.readTolines(new File(CrawlerConstant.PATH_CRAWLER_WORDS));
			causeOfActionKey.removeAll(isCrawlerWords);

			String caseType = this.caseType;
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
				String listContent = "";
				do {
					try {
						cal.setTime(sdf.parse(data1));
					} catch (ParseException e1) {
						logger.error("sdf parse error, value:" + data1, e1);
					}
					cal.set(Calendar.DATE, scope * times);
					String data2 = sdf.format(cal.getTime());
					params.put("Param", "案件类型:" + caseType + ",案由:" + cause + ",裁判日期:" + data1 + " TO " + data2);//// 关键词:继承,案由:遗嘱继承纠纷,裁判日期:2018-04-02
					String fileName = params.get("Param").replace(":", "_").replace("-", "").replace(" ", "")
							.replace(",", "_") + "_" + Index;
					File listFileName = new File(PRE_FILE_PATH + File.separator + fileName);
					if (listFileName.exists()) {
						logger.info("fileName {} has exist!!", listFileName);
						Index++;
						continue;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					logger.info("contentList query param: {}", params.toString());
					int index = 0;
					
					//指定时间内抓取列表页，超过三次后，如果还没有抓取到正文内容，则记录到文本中。
					do {
						Callable<String> task = new DownloadListContentCallable(crawler, params, specialParams.get(CrawlerConstant.KEY_VJKL5));
						Future<String> future = pool.submit(task);
						try {
							listContent = future.get(2, TimeUnit.MINUTES);
						} catch (Exception e) {
							logger.error("get List Content timeOut, param: " + params.toString(), e);
						}
					} while (listContent == null && ++index < 3);
					
					logger.info("contentList return size： {}", listContent.length());
					if (listContent.startsWith("RF") || listContent.contains("remind key")) {
						logger.info(" DownLoadList return value： {}", listContent);
						specialParams = this.getCookies();
						params.put(CrawlerConstant.KEY_VL5X, specialParams.get(CrawlerConstant.KEY_VL5X));
						FileTools.writeAndChangeRow(FILE_UN_SEARCH, params.toString(), true);
						continue;
					} else if (listContent.length() > 22) {
						FileTools.write(PRE_FILE_PATH, fileName, listContent, false);
						List<JudgementSimple> jsList = new ArrayList<>();
						try{
							jsList = Parse.parseListContent(listContent);
						}catch(Exception e) {
							logger.error(params.toString());
							logger.error("parse list Content has Error ", e);
							jsList = new ArrayList<>();
						}
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

							String detail = null;
							index = 0;
							//抓取内容指定时间，超过三次后，如果还没有抓取到正文内容，则记录到文本中。
							do {
								Callable<String> task = new DownloadDetailContentCallable(crawler, js.getCaseID(),
										specialParams.get(CrawlerConstant.KEY_VJKL5));
								Future<String> future = pool.submit(task);
								try {
									detail = future.get(2, TimeUnit.MINUTES);
								} catch (Exception e) {
									logger.error("get Content timeOut, docId: " + js.getCaseID(), e);
								}
							} while (detail == null && ++index < 3);

							if (null == detail) {
								logger.error("detail is null, docID: {}", js.getCaseID());
								FileTools.writeAndChangeRow(FILE_UN_CRAWLER, js.getCaseID(), true);
								continue;
							}
							logger.info("get content detail, docID: {}, caseType: {}, cause: {}, detail.length: {}",
									js.getCaseID(), caseType, cause, detail.length());
							
							crawlerdb.addToDb(js.getCaseID(), detail, caseType.trim(), cause.trim());
						}
						if (StringTools.isResponseOK(listContent)) {
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
				} while (!listContent.startsWith("RF"));
				FileTools.writeAndChangeRow(new File(CrawlerConstant.PATH_CRAWLER_WORDS), cause, true);

			}
			logger.info("caseType {} crawler end", this.caseType);
		}

		private Map<String, String> getCookies(){
			Map<String, String> result = new HashMap<>();
			int index = 0;
			do {
				if(index > 0) {
					try {
						index *= 2;
						logger.info("get cookies time sleep, {} ms", index);
						Thread.sleep(1000 * index);
					} catch (InterruptedException e) {
						logger.error("get cookies was interrupted", e);
					}
				}
				Callable<Map<String,String>> task = new GetCookiesCallable();
				Future<Map<String,String>> future = pool.submit(task);
				try {
					result = future.get(2, TimeUnit.MINUTES);
				} catch (Exception e) {
					logger.error("get cookies timeout", e);
				}
			} while (result == null || result.isEmpty());
			return result;
		}
		
	}

	abstract class CrawlerOption<T>{
		public abstract T done();
		public T call() {
			return done();
		}
	}
	
	class GetCookiesCallable extends CrawlerOption<Map<String, String>> implements Callable<Map<String, String>>{

		@Override
		public Map<String, String> done() {
			return JavaScriptTools.getCookiesByJsFile(CrawlerConstant.PATH_JS_COOKIE);
		}
		
	}
	
	class DownloadListContentCallable extends CrawlerOption<String> implements Callable<String> {
		
		private String keyVjkl5;
		private VerdictCrawler crawler;
		private Map<String, String> params;
		
		
		public DownloadListContentCallable(VerdictCrawler crawler, Map<String, String> params, String keyVjkl5) {
			this.crawler = crawler;
			this.params = params;
			this.keyVjkl5 = keyVjkl5;
		}
		
		@Override
		public String done() {
			String result = crawler.getContentList(CrawlerConstant.URL_LIST_CONTENT, this.params,
					this.keyVjkl5);
			return result;
		}
		
	}
	
	class DownloadDetailContentCallable extends CrawlerOption<String> implements Callable<String> {
		private String docID;
		private String param;
		private VerdictCrawler crawler;

		public DownloadDetailContentCallable(VerdictCrawler crawler, Map<String, Object> param) {
			
		}
		
		public DownloadDetailContentCallable(VerdictCrawler crawler, String docID, String param) {
			super();
			this.crawler = crawler;
			this.docID = docID;
			this.param = param;
		}

		@Override
		public String done() {
			String detail = crawler.getContentDetail(CrawlerConstant.URL_DETAIL_CONTENT, this.docID, this.param);
			return detail;
		}
	}
	

}
