package com.law.verdict.syllogism.substances;

import com.law.verdict.syllogism.interfaces.PropositionI;
import com.law.verdict.syllogism.interfaces.WordI;

public class Proposition implements PropositionI {
	private WordI subject;
	private WordI predicate;
	private boolean isSure = true;
	private boolean isFull = true;
	public Proposition(WordI subject, WordI predicate, boolean isS, boolean isFull) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.isSure = isS;
		this.isFull = isFull;
	}
	public Proposition(String str){
		//字符串转命题
	}
	public WordI getSubject() {
		return subject;
	}
	public void setSubject(WordI subject) {
		this.subject = subject;
	}
	public WordI getPredicate() {
		return predicate;
	}
	public void setPredicate(WordI predicate) {
		this.predicate = predicate;
	}
	public boolean isSure() {
		return isSure;
	}
	public void setSure(boolean isS) {
		this.isSure = isS;
	}
	public boolean isFull() {
		return isFull;
	}
	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
	
}
