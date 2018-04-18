package com.law.verdict.vo;

import java.util.List;

public class Viewpoint {
	private String id;
	private List<String> conditions;//故意，致人轻伤
	private String action;//伤害他人
	private String crime;//罪名，故意伤害罪
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getConditions() {
		return conditions;
	}
	public void setConditions(List<String> conditions) {
		this.conditions = conditions;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getCrime() {
		return crime;
	}
	public void setCrime(String crime) {
		this.crime = crime;
	}
	
	
}
