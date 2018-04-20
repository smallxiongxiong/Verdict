package com.law.verdict.vo;

import java.util.List;

/**
 * 甲方、乙方、法院
 *
 */
public class IObject {
	private String id;
	private String articleId;
	private int type;//机构1 人2
	private Person person;
	private Organization organization;
	private List<Person> refPerson;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public List<Person> getRefPerson() {
		return refPerson;
	}
	public void setRefPerson(List<Person> refPerson) {
		this.refPerson = refPerson;
	}
	
}
