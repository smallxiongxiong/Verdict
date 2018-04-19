package com.law.verdict.vo;

import java.util.List;

public class Organization {
	private String id;
	private String name;
	private List<String> objectIds;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getObjectIds() {
		return objectIds;
	}
	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}
	public Organization(String name) {
		super();
		this.name = name;
	} 
	
}
