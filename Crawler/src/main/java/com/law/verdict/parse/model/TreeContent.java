package com.law.verdict.parse.model;

import java.util.List;

public class TreeContent {
	private String Key;
	private String Value;
	private String parent;
	private String id;
	private String Field;
	private int SortKey;
	private int IntValue;
	private List<TreeContent> Child;

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getField() {
		return Field;
	}

	public void setField(String field) {
		Field = field;
	}

	public int getSortKey() {
		return SortKey;
	}

	public void setSortKey(int sortKey) {
		SortKey = sortKey;
	}

	public int getIntValue() {
		return IntValue;
	}

	public void setIntValue(int intValue) {
		IntValue = intValue;
	}

	public List<TreeContent> getChild() {
		return Child;
	}

	public void setChild(List<TreeContent> child) {
		Child = child;
	}

}
