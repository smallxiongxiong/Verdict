package com.law.verdict.parse.model;

import java.util.Date;

public class Judgement {
	private String id;
	private String docId;
	private String title;
	private Date pubDate;
	private String head;
	private String head2;
	private String facts;
	private String cause;
	private String judgeResult;
	private String tailContent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getHead2() {
		return head2;
	}

	public void setHead2(String head2) {
		this.head2 = head2;
	}

	public String getFacts() {
		return facts;
	}

	public void setFacts(String facts) {
		this.facts = facts;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getJudgeResult() {
		return judgeResult;
	}

	public void setJudgeResult(String judgeResult) {
		this.judgeResult = judgeResult;
	}

	public String getTailContent() {
		return tailContent;
	}

	public void setTailContent(String tailContent) {
		this.tailContent = tailContent;
	}

	@Override
	public String toString() {
		return "Judgement [id=" + id + ", head=" + head + ", head2=" + head2 + ", facts=" + facts + ", cause=" + cause
				+ ", judgeResult=" + judgeResult + ", tailContent=" + tailContent + "]";
	}

}
