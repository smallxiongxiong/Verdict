package com.law.verdict.vo;

import java.util.Date;
import java.util.List;

public class Article {
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private IObject [] plaintiff;//甲方
	private IObject [] defendant;//乙方
	private IObject [] courts;//法院
	private String title;//文书标题
	private String caseNum;//案件号
	private List<Thing> faces;//事实
	private List<List<Opinion>> pOpinion;//甲方观点
	private List<List<Opinion>> dOpinion;//乙方观点
	private List<Viewpoint> viewpoint;//法院观点
	private List<Law> basis;//法律依据，法条集合
	private List<List<String>> result;//判决结果
	private Date pubDate;
	
	public IObject[] getPlaintiff() {
		return plaintiff;
	}
	public void setPlaintiff(IObject[] plaintiff) {
		this.plaintiff = plaintiff;
	}
	public IObject[] getDefendant() {
		return defendant;
	}
	public void setDefendant(IObject[] defendant) {
		this.defendant = defendant;
	}
	public IObject[] getCourts() {
		return courts;
	}
	public void setCourts(IObject[] courts) {
		this.courts = courts;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
	public List<Thing> getFaces() {
		return faces;
	}
	public void setFaces(List<Thing> faces) {
		this.faces = faces;
	}
	public List<List<Opinion>> getpOpinion() {
		return pOpinion;
	}
	public void setpOpinion(List<List<Opinion>> pOpinion) {
		this.pOpinion = pOpinion;
	}
	public List<List<Opinion>> getdOpinion() {
		return dOpinion;
	}
	public void setdOpinion(List<List<Opinion>> dOpinion) {
		this.dOpinion = dOpinion;
	}

	public List<Viewpoint> getViewpoint() {
		return viewpoint;
	}
	public void setViewpoint(List<Viewpoint> viewpoint) {
		this.viewpoint = viewpoint;
	}
	public List<Law> getBasis() {
		return basis;
	}
	public void setBasis(List<Law> basis) {
		this.basis = basis;
	}
	public List<List<String>> getResult() {
		return result;
	}
	public void setResult(List<List<String>> result) {
		this.result = result;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	
}
