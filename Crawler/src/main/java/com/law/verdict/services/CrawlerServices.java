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
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
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
	private static final File FILE_UN_CRAWLER = new File(CrawlerConstant.PATH_UN_CRAWLER);
	private static final File FILE_UN_SEARCH = new File(CrawlerConstant.PATH_UN_SEARCH);
	private static ExecutorService pool = Executors.newFixedThreadPool(4);
	@Autowired
	CrawlerDBOpt crawlerdb;

	public void beginCrawler(String caseType) {
		Runnable target = new CrawlerTask(caseType);
		Thread thread = new Thread(target);
		thread.setName("crawler_one");

		thread.start();
	}

	public class CrawlerTask implements Runnable {
		private String caseType;
		VerdictCrawler crawler = new VerdictCrawler();

		public CrawlerTask(String caseType) {
			super();
			this.caseType = caseType;
		}

		CyclicBarrier c = new CyclicBarrier(3, new Thread());

		@Override
		public void run() {
			logger.info("begin run Crawler, caseType:{}", caseType);

			Map<String, String> specialParams = this.getCookies();
			Map<String, String> params = new HashMap<String, String>();
			List<String> causeOfActionKey = FileTools.readTolines(new File(CrawlerConstant.PATH_CASE_DICT));
			List<String> isCrawlerWords = FileTools.readTolines(new File(CrawlerConstant.PATH_CRAWLER_WORDS));
			causeOfActionKey.removeAll(isCrawlerWords);

			String caseType = this.caseType;
			Calendar cal = Calendar.getInstance();
			int scope = 7;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String vjkl5 = specialParams.get(CrawlerConstant.KEY_VJKL5);
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

					listContent = this.getListContent(params, vjkl5);

					logger.info("contentList return size： {}", listContent.length());
					if (null == listContent || listContent.startsWith("RF") || listContent.contains("remind key")) {
						logger.info(" DownLoadList return value： {}", listContent);
						specialParams = this.getCookies();
						params.put(CrawlerConstant.KEY_VL5X, specialParams.get(CrawlerConstant.KEY_VL5X));
						FileTools.writeAndChangeRow(FILE_UN_SEARCH, params.toString(), true);
						continue;
					} else if (listContent.length() > 22) {
						FileTools.write(PRE_FILE_PATH, fileName, listContent, false);
						getMoreContent(listContent, params, vjkl5, caseType, cause);
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

		/**
		 * 获取cookie信息
		 * 
		 * @return
		 */
		private Map<String, String> getCookies() {
			Map<String, String> result = new HashMap<>();
			int index = 0;
			do {
				if (index > 0) {
					try {
						index *= 2;
						logger.info("get cookies time sleep, {} ms", index);
						Thread.sleep(1000 * index);
					} catch (InterruptedException e) {
						logger.error("get cookies was interrupted", e);
					}
				}
				Callable<Map<String, String>> task = new GetCookiesCallable();
				Future<Map<String, String>> future = pool.submit(task);
				try {
					result = future.get(2, TimeUnit.MINUTES);
				} catch (Exception e) {
					logger.error("get cookies timeout", e);
				}
			} while (result == null || result.isEmpty());
			return result;
		}

		/**
		 * 指定时间内抓取列表页，超过三次后，如果还没有抓取到正文内容，则记录到文本中。
		 * 
		 * @param params
		 * @param vjkl5
		 * @return
		 */
		private String getListContent(Map<String, String> params, String vjkl5) {
			String listContent = null;
			int index = 0;
			do {
				Callable<String> task = new DownloadListContentCallable(crawler, params, vjkl5);
				Future<String> future = pool.submit(task);
				try {
					listContent = future.get(2, TimeUnit.MINUTES);
				} catch (Exception e) {
					logger.error("get List Content timeOut, param: " + params.toString(), e);
				}
			} while (listContent == null && ++index < 3);
			return listContent;
		}

		/**
		 * 
		 * @param listContent
		 * @param params
		 * @param vjkl5
		 * @param caseType
		 * @param cause
		 * @return
		 */
		private void getMoreContent(String listContent, Map<String, String> params, String vjkl5, String caseType,
				String cause) {
			List<JudgementSimple> jsList = new ArrayList<>();
			try {
				jsList = Parse.parseListContent(listContent);
			} catch (Exception e) {
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
				try {
				Callable<String> getSummaryTask = new DownloadSummary(crawler, js, vjkl5);
				Callable<String> getRelatedFileTask = new DownloadRelatedFile(crawler, js, vjkl5);
				Callable<String> getDetailTask = new DownloadDetailContentCallable(crawler, js, vjkl5);

				Map<String, Callable<String>> taskMap = new HashMap<>();
				taskMap.put("summayTask", getSummaryTask);
				taskMap.put("relattedFileTask", getRelatedFileTask);
				taskMap.put("detailTask", getDetailTask);

				Map<String, String> taskReaults = doTasks(taskMap, 2);

				String summary = taskReaults.get("summayTask");
				String relatedFile = taskReaults.get("relattedFileTask");
				String detail = taskReaults.get("DetailTask");
				
				
				

				if (null == detail || null == summary || relatedFile == null) {
					logger.error("some of verdict is empty, docID: {}, detail: {}, summary: {}, realtedFile:{}",
							js.getCaseID(), null == detail, null == summary, null == relatedFile);
					FileTools.writeAndChangeRow(FILE_UN_CRAWLER, js.getCaseID(), true);
					continue;
				}
				logger.info("get content detail, docID: {}, caseType: {}, cause: {}, detail.length: {}", js.getCaseID(),
						caseType, cause, detail.length());
				}catch(Exception e) {
					e.printStackTrace();
				}

				//crawlerdb.addToDb(js.getCaseID(), detail, caseType.trim(), cause.trim());
			}
		}

		/**
		 * 在指定的時間内執行需要的指定的任務，如果執行超時，則斷開，重新執行
		 * 
		 * @param task
		 * @param timeScope
		 * @return
		 */
		String doTask(Callable<String> task, int timeScope) {
			String result = null;
			timeScope = timeScope < 1 ? 1 : timeScope;
			int index = 0;
			do {
				Future<String> future = pool.submit(task);
				try {
					result = future.get(timeScope, TimeUnit.MINUTES);
				} catch (Exception e) {
					logger.error("do task Timeout", e);
				}
			} while (result == null && ++index < 3);
			return result;
		}

		/**
		 * 采用回调线程的方式启动队列读取数据
		 * 
		 * @param task
		 * @param timeScope
		 * @return
		 */
		Map<String, String> doTasks(Map<String, Callable<String>> task, int timeScope) {
			Map<String, String> result = new HashMap<>(5);
			Set<Map.Entry<String, Callable<String>>> entry = task.entrySet();
			Map<String, Future<String>> futureMap = new HashMap<>(5);
			// 提交线程
			for (Map.Entry<String, Callable<String>> item : entry) {
				futureMap.put(item.getKey(), pool.submit(item.getValue()));
			}
			String tmp = null;
			// 获取线程结果
			for (Map.Entry<String, Future<String>> item : futureMap.entrySet()) {
				try {
					tmp = item.getValue().get();
					result.put(item.getKey(), tmp);
				} catch (Exception e) {
					logger.error("do task Timeout", e);
					pool.submit(task.get(item.getKey()));
				}
			}

			// 判断线程处理结果的个数是否与预期线程数相等
			if (result.size() < task.size()) {
				Set<String> taskKey = task.keySet();
				Set<String> resultKey = result.keySet();
				taskKey.removeAll(resultKey);
				if (taskKey.size() > 0) {
					for (String item : taskKey) {
						int index = 0;
						tmp = null;
						do {
							Future<String> future = pool.submit(task.get(item));
							try {
								tmp = future.get(timeScope, TimeUnit.MINUTES);
								result.put(item, tmp);
							} catch (Exception e) {
								logger.error("do task Timeout", e);
							}
						} while (tmp == null && ++index < 3);
					}
				}
			}
			return result;
		}
	}

	abstract class CrawlerOption<T> {
		public abstract T done();

		public T call() {
			return done();
		}
	}

	class GetCookiesCallable extends CrawlerOption<Map<String, String>> implements Callable<Map<String, String>> {

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
			String result = crawler.getContentList(CrawlerConstant.URL_LIST_CONTENT, this.params, this.keyVjkl5);
			return result;
		}

	}

	class DownloadDetailContentCallable extends CrawlerOption<String> implements Callable<String> {
		private String param;
		private VerdictCrawler crawler;
		private JudgementSimple js;

		public DownloadDetailContentCallable(VerdictCrawler crawler, Map<String, Object> param) {

		}

		public DownloadDetailContentCallable(VerdictCrawler crawler, JudgementSimple js, String param) {
			super();
			this.crawler = crawler;
			this.js = js;
			this.param = param;
		}

		@Override
		public String done() {
			String detail = crawler.getContentDetail(CrawlerConstant.URL_DETAIL_CONTENT, this.js.getCaseID(),
					this.param);
			return detail;
		}
	}

	class DownloadRelatedFile extends CrawlerOption<String> implements Callable<String> {

		private String cookie;
		private JudgementSimple js;
		private VerdictCrawler crawler;

		public DownloadRelatedFile(VerdictCrawler crawler, JudgementSimple js, String cookie) {
			super();
			this.js = js;
			this.crawler = crawler;
			this.cookie = cookie;
		}

		@Override
		public String done() {
			Map<String, String> getReatedFileParam = new HashMap<String, String>();
			getReatedFileParam.put("docId", js.getCaseID());
			getReatedFileParam.put("court", js.getAdjudicatCourt());
			getReatedFileParam.put("caseNumber", js.getCaseNum());
			getReatedFileParam.put("caseType", js.getCaseType());
			String result = crawler.getRelatedFiles(CrawlerConstant.URL_RELATED_FILE, getReatedFileParam, this.cookie);
			List<JudgementSimple> relatedJS = Parse.parseRelatedFiles(result);
			this.js.setRelatedFile(relatedJS);
			return result;
		}

	}

	class DownloadSummary extends CrawlerOption<String> implements Callable<String> {

		private VerdictCrawler crawler;
		private String vjkl5;
		private JudgementSimple js;

		public DownloadSummary(VerdictCrawler crawler, JudgementSimple js, String vjkl5) {
			super();
			this.crawler = crawler;
			this.vjkl5 = vjkl5;
			this.js = js;
		}

		@Override
		public String done() {
			String summary = crawler.getSummary("http://wenshu.court.gov.cn/Content/GetSummary", js.getCaseID(), vjkl5);
			Parse.parseSummary(summary, this.js);
			return summary;
		}
	}
}
