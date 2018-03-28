package com.law.verdict.parse.model;

import java.util.LinkedList;
import java.util.List;

public class Statute {
	private String name;
	private List<ArticlesLaw> detailLaws = new LinkedList<>();
	
	public static class ArticlesLaw{
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
	}

}
