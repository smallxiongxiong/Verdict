package com.law.verdict.utils;

public class StringTools {
//		public static boolean isResponseNULl(String responseStr){
//			if(responseStr.startsWith("RF")){
//				return false;
//			}else{
//				return true;
//			}
//		}
		public static boolean isResponseOK(String code){
			if(code.startsWith("RF")){
				return false;
			}
			return true;
		}
}
