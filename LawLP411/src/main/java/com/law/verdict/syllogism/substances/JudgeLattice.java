package com.law.verdict.syllogism.substances;

import com.law.verdict.syllogism.interfaces.LatticeI;
import com.law.verdict.syllogism.interfaces.PropositionI;
import com.law.verdict.syllogism.interfaces.WordI;

public class JudgeLattice implements LatticeI{
	private String type;
	private WordI bigConditionSubject;
	private WordI bigConditionPredicate;
	private WordI smallConditionSubject;
	private WordI smallConditionPredicate;
	private WordI resultSubject;
	private WordI resultPredicate;
	
	
	public JudgeLattice(WordI mid, WordI bigConditionPredicate, WordI smallConditionSubject,
			WordI smallConditionPredicate, WordI resultSubject, WordI resultPredicate) {
		super();
		this.bigConditionSubject = bigConditionSubject;
		this.bigConditionPredicate = bigConditionPredicate;
		this.smallConditionSubject = smallConditionSubject;
		this.smallConditionPredicate = smallConditionPredicate;
		this.resultSubject = resultSubject;
		this.resultPredicate = resultPredicate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public WordI getBigConditionSubject() {
		return bigConditionSubject;
	}
	public void setBigConditionSubject(WordI bigConditionSubject) {
		this.bigConditionSubject = bigConditionSubject;
	}
	public WordI getBigConditionPredicate() {
		return bigConditionPredicate;
	}
	public void setBigConditionPredicate(WordI bigConditionPredicate) {
		this.bigConditionPredicate = bigConditionPredicate;
	}
	public WordI getSmallConditionSubject() {
		return smallConditionSubject;
	}
	public void setSmallConditionSubject(WordI smallConditionSubject) {
		this.smallConditionSubject = smallConditionSubject;
	}
	public WordI getSmallConditionPredicate() {
		return smallConditionPredicate;
	}
	public void setSmallConditionPredicate(WordI smallConditionPredicate) {
		this.smallConditionPredicate = smallConditionPredicate;
	}
	public WordI getResultSubject() {
		return resultSubject;
	}
	public void setResultSubject(WordI resultSubject) {
		this.resultSubject = resultSubject;
	}
	public WordI getResultPredicate() {
		return resultPredicate;
	}
	public void setResultPredicate(WordI resultPredicate) {
		this.resultPredicate = resultPredicate;
	}
	
}
