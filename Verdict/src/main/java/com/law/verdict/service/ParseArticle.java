package com.law.verdict.service;

import com.law.verdict.model.JudgementWithBLOBs;
import com.law.verdict.vo.Article;

public class ParseArticle {
	private Article article ;
	
	public ParseArticle() {
		super();
		article = new Article();
	}

	public Article execute(JudgementWithBLOBs blobs) {
		parseTitle(blobs.getTitle());
		parseHead(blobs.getHead());
		parseHead2(blobs.getHead2());
		parseFace(blobs.getFacts());
		parseCause(blobs.getCause());
		parseResult(blobs.getJudgeResult());
		return article;
	}

	private void parseResult(String judgeResult) {
		// TODO Auto-generated method stub
		
	}

	private void parseCause(String cause) {
		// TODO Auto-generated method stub
		
	}

	private void parseFace(String facts) {
		// TODO Auto-generated method stub
		
	}

	private void parseHead2(String head2) {
		// TODO Auto-generated method stub
		
	}

	private void parseHead(String head) {
		// TODO Auto-generated method stub
		
	}

	private void parseTitle(String title) {
		// TODO Auto-generated method stub
		
	}

}
