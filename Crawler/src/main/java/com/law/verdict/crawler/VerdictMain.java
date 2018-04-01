package com.law.verdict.crawler;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<String> dataStrs = DateTools.getScopeDay("2018-03-01", 31);
		for(String dataStr : dataStrs){
			for(String action : causeOfActionKey){
				for(String word:treeKeyWord){
					int Index =1;
					params.put("Index", Index+"");
					params.put("Page", "20");
					params.put("Param", "案由:"+action+",关键词:"+word+",裁判日期:"+dataStr+" TO "+dataStr);////关键词:继承,案由:遗嘱继承纠纷,裁判日期:2018-04-02 TO 2018-04-02
					params.put("Order", "法院层级");
					params.put("Direction", "asc");
					params.put("vl5x", specialParams.get("vl5x"));
					String list = "";
					do{
						String fileName = params.get("Param").replace(":", "_").replace("-", "").replace(" ", "").replace(",", "_")+"_"+Index;
						list = crawler.getContentList("http://wenshu.court.gov.cn/List/ListContent",params,specialParams.get("vjkl5"));
						if(!StringTools.isFailed(list)){
							FileTools.write("D:"+File.separator+"verdict", fileName, list, false);
						}
						Index++;
						params.put("Index", Index+"");
					}while(Index<101&&!StringTools.isFailed(list));
				}
			}
		}
	}
}
