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
		
		/**
		 * unicode解码
		 * @param unicode
		 * @return
		 */
		public static String unicodetoString(String unicode){    
	        if(unicode==null||"".equals(unicode)){  
	            return null;  
	        }  
	        StringBuilder sb = new StringBuilder();    
	        int i = -1;    
	        int pos = 0;    
	        while((i=unicode.indexOf("\\u", pos)) != -1){    
	            sb.append(unicode.substring(pos, i));    
	            if(i+5 < unicode.length()){    
	                pos = i+6;    
	                sb.append((char)Integer.parseInt(unicode.substring(i+2, i+6), 16));    
	            }    
	        }    
	        return sb.toString();    
	    } 
		
		public static void main(String[] args) {
			
		}
}
