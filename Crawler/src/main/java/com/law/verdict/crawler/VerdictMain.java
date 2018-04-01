package com.law.verdict.crawler;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.os.WindowsUtils;

import com.law.verdict.parse.Parse;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.utils.DateTools;
import com.law.verdict.utils.FileTools;
import com.law.verdict.utils.StringTools;

public class VerdictMain {
	public static void main(String[] args) {
		VerdictCrawler crawler = new VerdictCrawler();
		String url = "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";
		Map<String, String> specialParams =crawler.getSpecialParams(url);
		Map<String, String> params = new HashMap<String,String>();
		
		List<String> causeOfActionKey = FileTools.readTolines(new File("src/main/resources/casedict.txt"));
		List<String> treeKeyWord = FileTools.readTolines(new File("src/main/resources/treeKeyWord.txt"));
		List<String> dataStrs = DateTools.getScopeDay("2017-01-01", 30,7);
		
		for(String dataStr : dataStrs){
			String[] strs = dataStr.split(",");
			String data1 ="2018-03-01";
			String data2 ="2018-03-07";
			if(strs.length==2){
				data1 = strs[0];
				data2 = strs[1];
			}
			for(String action : causeOfActionKey){
				for(String word:treeKeyWord){
					System.out.println(word+"======================");
					int Index =1;
					params.put("Index", Index+"");
					params.put("Page", "20");
					params.put("Param", "案由:"+action+",关键词:"+word+",裁判日期:"+data1+" TO "+data2);////关键词:继承,案由:遗嘱继承纠纷,裁判日期:2018-04-02 TO 2018-04-02
					params.put("Order", "法院层级");
					params.put("Direction", "asc");
					params.put("vl5x", specialParams.get("vl5x"));
					String list = "";
					do{
						String fileName = params.get("Param").replace(":", "_").replace("-", "").replace(" ", "").replace(",", "_")+"_"+Index;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(params.toString()+"----------------------------------------------------");
						list = crawler.getContentList("http://wenshu.court.gov.cn/List/ListContent",params,specialParams.get("vjkl5"));
						System.out.println("list content:"+list);
						if(list.length()>100){
							FileTools.write("D:"+File.separator+"verdict", fileName, list, false);
							List<JudgementSimple> jsList =Parse.parseListContent(list);
							for(JudgementSimple js:jsList){
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								String detail = crawler.getContentDetail("http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx",js.getCaseID(),specialParams.get("vjkl5"));
								FileTools.write("D:"+File.separator+"verdict"+File.separator+"details", js.getCaseID(), list, false);
							}
						}
						if(StringTools.isResponseOK(list)){
							Index++;
						}else{
							specialParams =crawler.getSpecialParams(url);
							params.put("vl5x", specialParams.get("vl5x"));
						}
						params.put("Index", Index+"");
					}while(list.length()>100||list.startsWith("RF"));
				}
			}
		}
		WindowsUtils.killByName("chrome.exe");
	}
}
