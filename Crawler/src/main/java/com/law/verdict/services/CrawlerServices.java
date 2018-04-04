package com.law.verdict.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.verdict.parse.Parse;
import com.law.verdict.parse.dao.JudgementMapper;
import com.law.verdict.parse.db.model.JudgementWithBLOBs;

@Service
public class CrawlerServices {
	private Logger logger = LoggerFactory.getLogger(CrawlerServices.class);
	@Autowired
	JudgementMapper judgeMentMapper;
	
   private static ExecutorService pool =	Executors.newFixedThreadPool(10);
	public void addToDb(String docId, String content) {
		Runnable task = this.new AddToDB(docId, content);
		pool.execute(task);
	}
	
	class AddToDB implements Runnable{
		private String content;
		private String docId;
	
		public AddToDB(String docId, String content) {
			super();
			this.content = content;
			this.docId = docId;
		}

		@Override
		public void run() {
			JudgementWithBLOBs result = Parse.parseContent(content);
			result.setDocId(this.docId);
			int record =judgeMentMapper.insert(result);
			logger.info("docID: {}, insert result: ", this.docId, record > 0);
			
		}
		
	}
}
