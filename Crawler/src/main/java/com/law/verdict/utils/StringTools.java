package com.law.verdict.utils;

public class StringTools {
		public static boolean isFailed(String responseStr){
			if(responseStr.length()<1000)return true;
			return false;
		}
}
