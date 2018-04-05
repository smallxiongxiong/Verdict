package com.law.verdict.services;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.verdict.parse.Parse;
import com.law.verdict.parse.dao.JudgementMapper;
import com.law.verdict.parse.db.model.JudgementWithBLOBs;
import com.law.verdict.utils.FileTools;

@Service
public class CrawlerDBOpt {
	private Logger logger = LoggerFactory.getLogger(CrawlerDBOpt.class);
	@Autowired
	JudgementMapper judgeMentMapper;

	private static ExecutorService pool = Executors.newFixedThreadPool(10);

	public void addToDb(String docId, String content, String caseType, String cause) {
		Runnable task = this.new AddToDB(content, docId, caseType, cause);
		pool.execute(task);
		logger.info("submit add To Db: {}", docId);
	}

	class AddToDB implements Runnable {
		private String content;
		private String docId;
		private String caseType;
		private String cause;

		public AddToDB(String content, String docId, String caseType, String cause) {
			super();
			this.content = content;
			this.docId = docId;
			this.caseType = caseType;
			this.cause = cause;
		}

		@Override
		public void run() {
			JudgementWithBLOBs result = Parse.parseContent(content);
			if (null == result) {
				FileTools.write("E:" + File.separator + "verdict" + File.separator + this.caseType + File.separator
						+ this.cause, this.docId, content, false);
				logger.info("caseType: {}, cause: {}, docID: {}, parseFailure ", this.caseType, this.cause, this.docId);
			} else {
				result.setDocId(this.docId);
				int record = judgeMentMapper.insert(result);
				logger.info("docID: {}, insert result: {}", this.docId, record > 0);
			}
		}

	}
}
