package com.law.verdict.index;

import java.util.LinkedList;
import java.util.List;

import com.law.verdict.parse.db.model.JudgementWithBLOBs;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.parse.model.Statute;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class IndexDocument {
	private String caseName; // 案件名称
	private String head;
	private String head2;
	private String facts;
	private String cause;
	private String judgeResult;
	private String tailContent;

	private String abstractText; // 裁判要旨段原文
	private String caseType; // 类型案件
	private String verdictTime; // 裁判日期
	private String caseID; // 文书ID
	private String verdictProcess; // 审判程序
	private String caseNum; // 案号
	private String court; // 审理法院，法院名称
	private String mark; // 标记
	private String endMethod; // 结案方式
	private String executiveScope; // 行政管理范围
	private String executiveType; // 行政行为种类
	private Statute legalBase; // 法条依赖
	private String originType; // 与原文Type对应
	private String appellor; // 当事人

	private List<Statute> statutes = new LinkedList<>();
	private long timestamp; // 用来映射裁判日期

	private String relatedCaseNum;

	public IndexDocument(JudgementSimple js, JudgementWithBLOBs content) {
		this.caseName = js.getCaseName();
		this.head = null != content.getHead() ? content.getHead().replaceAll("\\s", " ") : "";
		this.head2 = null != content.getHead2() ? content.getHead2().replaceAll("\\s", " ") : "";
		this.facts = null != content.getFacts() ? content.getFacts().replaceAll("\\s", " ") : "";
		this.cause = null != content.getCause() ? content.getCause().replaceAll("\\s", " ") : "";
		this.judgeResult = null != content.getJudgeResult() ? content.getJudgeResult().replaceAll("\\s", " ") : "";
		this.tailContent = null != content.getTailContent() ? content.getTailContent().replaceAll("\\s", " ") : "";
		this.abstractText = null != js.getJudicialGist() ? js.getJudicialGist().replaceAll("\\s", " ") : "";
		this.caseType = js.getCaseType();
		this.verdictTime = js.getAdjudicatTime();
		this.caseID = js.getCaseID();
		this.verdictProcess = js.getJudgmentProcess();
		this.caseNum = js.getCaseNum();
		this.court = js.getAdjudicatCourt();
		this.mark = js.getMark();
		this.endMethod = js.getEndMethod();
		this.executiveScope = js.getAdministrativeScope();
		this.executiveType = js.getAdministrativeType();
		this.legalBase = js.getLegalBase();
		this.appellor = js.getAppellor();
		List<JudgementSimple> relatedFile = js.getRelatedFile();
		StringBuilder sb = new StringBuilder();
		for (JudgementSimple tmp : relatedFile) {
			sb.append(tmp.getCaseNum() + " ");
		}
		this.relatedCaseNum = sb.toString().trim();
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
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

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getVerdictTime() {
		return verdictTime;
	}

	public void setVerdictTime(String verdictTime) {
		this.verdictTime = verdictTime;
	}

	public String getCaseID() {
		return caseID;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public String getVerdictProcess() {
		return verdictProcess;
	}

	public void setVerdictProcess(String verdictProcess) {
		this.verdictProcess = verdictProcess;
	}

	public String getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
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

	public String getExecutiveScope() {
		return executiveScope;
	}

	public void setExecutiveScope(String executiveScope) {
		this.executiveScope = executiveScope;
	}

	public String getExecutiveType() {
		return executiveType;
	}

	public void setExecutiveType(String executiveType) {
		this.executiveType = executiveType;
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

	public String getAppellor() {
		return appellor;
	}

	public void setAppellor(String appellor) {
		this.appellor = appellor;
	}

	public List<Statute> getStatutes() {
		return statutes;
	}

	public void setStatutes(List<Statute> statutes) {
		this.statutes = statutes;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getRelatedCaseNum() {
		return relatedCaseNum;
	}

	public void setRelatedCaseNum(String relatedCaseNum) {
		this.relatedCaseNum = relatedCaseNum;
	}

	@Override
	public String toString() {
		return "IndexDocument [caseName=" + caseName + ", head=" + head + ", head2=" + head2 + ", facts=" + facts
				+ ", cause=" + cause + ", judgeResult=" + judgeResult + ", tailContent=" + tailContent
				+ ", abstractText=" + abstractText + ", caseType=" + caseType + ", verdictTime=" + verdictTime
				+ ", caseID=" + caseID + ", verdictProcess=" + verdictProcess + ", caseNum=" + caseNum + ", court="
				+ court + ", mark=" + mark + ", endMethod=" + endMethod + ", executiveScope=" + executiveScope
				+ ", executiveType=" + executiveType + ", legalBase=" + legalBase + ", originType=" + originType
				+ ", appellor=" + appellor + ", statutes=" + statutes + ", timestamp=" + timestamp + ", relatedCaseNum="
				+ relatedCaseNum + "]";
	}

}
