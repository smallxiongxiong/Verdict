package com.law.verdict.parse.model;

import java.util.LinkedList;
import java.util.List;

public class Statute {
	private String name;
	private List<ArticlesLaw> detailLaws = new LinkedList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addDetailLaw(ArticlesLaw law) {
		this.detailLaws.add(law);
	}
	public static class ArticlesLaw {
		private String name;
		private String content;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String toString() {
			return "ArticlesLaw [name=" + name + ", content=" + content + "]";
		}
		
	}
	@Override
	public String toString() {
		return "Statute [name=" + name + ", detailLaws=" + detailLaws + "]";
	}

}
