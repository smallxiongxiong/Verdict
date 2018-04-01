package com.law.verdict.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {  
    public static String sendGet(String url, String param,String cookie) {  
        String result = "";  
        BufferedReader in = null;  
        try {  
            String urlNameString = url + "?" + param;  
            URL realUrl = new URL(urlNameString);  
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("cookie", "vjkl5="+cookie);
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            connection.connect();  
            Map<String, List<String>> map = connection.getHeaderFields();  
            for (String key : map.keySet()) {  
                System.out.println(key + "--->" + map.get(key));  
            }  
            in = new BufferedReader(new InputStreamReader(  
                    connection.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return result;  
    }  
    public static String sendPost(String url, String param, String cookie) {  
    	PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            URLConnection conn = realUrl.openConnection();  
            conn.setRequestProperty("accept", "*/*"); 
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("cookie", "vjkl5="+cookie);
            conn.setRequestProperty("user-agent",  "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            out = new PrintWriter(conn.getOutputStream());  
            out.print(param);
            out.flush();  
            in = new BufferedReader(  
                    new InputStreamReader(conn.getInputStream(),"utf-8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally{  
            try{  
                if(out!=null){  
                    out.close();  
                }  
                if(in!=null){  
                    in.close();  
                }  
            }  
            catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }      
}  
