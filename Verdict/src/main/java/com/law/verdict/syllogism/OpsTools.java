package com.law.verdict.syllogism;

import com.law.verdict.syllogism.substances.BigCondition;
import com.law.verdict.syllogism.substances.SmallCondition;

public class OpsTools {
	public static boolean onlyOneNo(BigCondition bc,SmallCondition sc){
		return bc.isSure^sc.isSure;
	}
	public static boolean hasFourConcept(BigCondition bc,SmallCondition sc){
		return true;
	}
}
