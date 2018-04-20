package com.law.verdict.parse.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JudgementSimple {

	private String judicialGist; // 裁判要旨段原文
	private String caseType; // 类型案件
	private String adjudicatTime; // 裁判日期
	private String caseName; // 案件名称
	private String caseID; // 文书ID
	private String judgmentProcess; // 审判程序
	private String caseNum; // 案号
	private String adjudicatCourt; // 审理法院，法院名称
	
	private String mark; // 标记
	private String endMethod; // 结案方式
	private String administrativeScope; // 行政管理范围
	private String administrativeType; // 行政行为种类
	private Statute legalBase; // 法条依赖
	private String originType;		//与原文Type对应
	private String appellor; 		//当事人

	private List<Statute> statutes = new LinkedList<>();
	private long timestamp; // 用来映射裁判日期

	
	
	
	private List<JudgementSimple> relatedFile = new LinkedList<>();

	public static final Map<String, String> KEY_MAP = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("裁判要旨段原文", "judicialGist");
			put("案件类型", "caseType");
			put("裁判日期", "adjudicatTime");
			put("案件名称", "caseName");
			put("文书ID", "caseID");
			put("审判程序", "judgmentProcess");
			put("案号", "caseNum");
			put("法院名称", "adjudicatCourt");
			put("审理法院", "adjudicatCourt");
			put("Type", "originType");
			put("Mark", "mark");
			put("结案方式", "endMethod");
		}
	};
	
	public void addStatute(Statute statute) {
		this.statutes.add(statute);
	}

	public String getJudicialGist() {
		return judicialGist;
	}

	public void setJudicialGist(String judicialGist) {
		this.judicialGist = judicialGist;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getAdjudicatTime() {
		return adjudicatTime;
	}

	public void setAdjudicatTime(String adjudicatTime) {
		this.adjudicatTime = adjudicatTime;
		setTimestamp(0L);
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseID() {
		return caseID;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public String getJudgmentProcess() {
		return judgmentProcess;
	}

	public void setJudgmentProcess(String judgmentProcess) {
		this.judgmentProcess = judgmentProcess;
	}

	public String getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}

	public String getAdjudicatCourt() {
		return adjudicatCourt;
	}

	public void setAdjudicatCourt(String adjudicatCourt) {
		this.adjudicatCourt = adjudicatCourt;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getEndMethod() {
		return endMethod;
	}

	public void setEndMethod(String endMethod) {
		this.endMethod = endMethod;
	}

	public String getAdministrativeScope() {
		return administrativeScope;
	}

	public void setAdministrativeScope(String administrativeScope) {
		this.administrativeScope = administrativeScope;
	}

	public String getAdministrativeType() {
		return administrativeType;
	}

	public void setAdministrativeType(String administrativeType) {
		this.administrativeType = administrativeType;
	}

	public Statute getLegalBase() {
		return legalBase;
	}

	public void setLegalBase(Statute legalBase) {
		this.legalBase = legalBase;
	}

	public String getOriginType() {
		return originType;
	}

	public void setOriginType(String originType) {
		this.originType = originType;
	}

	public List<JudgementSimple> getRelatedFile() {
		return relatedFile;
	}

	public void setRelatedFile(List<JudgementSimple> relatedFile) {
		this.relatedFile = relatedFile;
	}

	public void setTimestamp(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(this.adjudicatTime);
			this.timestamp = date.getTime();
		} catch (ParseException e) {
			this.timestamp = timestamp;
		}

	}

	public String getAppellor() {
		return appellor;
	}

	public void setAppellor(String appellor) {
		this.appellor = appellor;
	}

	@Override
	public String toString() {
		return "JudgementSimple [judicialGist=" + judicialGist + ", caseType=" + caseType + ", adjudicatTime="
				+ adjudicatTime + ", caseName=" + caseName + ", caseID=" + caseID + ", judgmentProcess="
				+ judgmentProcess + ", caseNum=" + caseNum + ", adjudicatCourt=" + adjudicatCourt + ", mark=" + mark
				+ ", endMethod=" + endMethod + ", administrativeScope=" + administrativeScope + ", administrativeType="
				+ administrativeType + ", legalBase=" + legalBase + ", originType=" + originType + ", appellor="
				+ appellor + ", statutes=" + statutes + ", timestamp=" + timestamp + ", relatedFile=" + relatedFile
				+ "]";
	}



}
