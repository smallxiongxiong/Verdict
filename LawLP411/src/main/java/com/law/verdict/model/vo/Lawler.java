package com.law.verdict.model.vo;

public class Lawler {
	private String lawlerName;
	private String officeName;
	private String lawlerId;

	public String getLawlerName() {
		return lawlerName;
	}

	public void setLawlerName(String lawlerName) {
		this.lawlerName = lawlerName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getLawlerId() {
		return lawlerId;
	}

	public void setLawlerId(String lawlerId) {
		this.lawlerId = lawlerId;
	}

	@Override
	public String toString() {
		return "Lawler [lawlerName=" + lawlerName + ", officeName=" + officeName + ", lawlerId=" + lawlerId + "]";
	}

}
