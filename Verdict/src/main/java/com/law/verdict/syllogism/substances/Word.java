package com.law.verdict.syllogism.substances;

import java.util.Set;

import com.law.verdict.syllogism.Vector;
import com.law.verdict.syllogism.interfaces.WordI;

public class Word implements WordI{
	private String name;
	private Set set;//集合词
	private boolean isThis;//整体指代
	private Vector vec;
	
	public Word(String name) {
		super();
		this.name = name;
	}
	public Word(String name, Set set) {
		super();
		this.name = name;
		this.set = set;
	}
	public Word(String name, Set set, boolean isThis) {
		super();
		this.name = name;
		this.set = set;
		this.isThis = isThis;
	}
	
	public boolean isSet(){
		if(set!=null&&set.size()>0)return true;
		return false;
	}
	
	public boolean equals(Word other){
		return this.name.equals(other.name); 
	}
	
	public float similar(Word other){
		return this.vec.cosine(other.vec);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set getSet() {
		return set;
	}
	public void setSet(Set set) {
		this.set = set;
	}
	public boolean isThis() {
		return isThis;
	}
	public void setThis(boolean isThis) {
		this.isThis = isThis;
	}
	
	
}
