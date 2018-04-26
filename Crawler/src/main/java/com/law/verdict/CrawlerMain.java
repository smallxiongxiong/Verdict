package com.law.verdict;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.law.verdict.services.CrawlerServices;

@MapperScan("com.law.verdict.parse.dao")
@ComponentScan(basePackages={"com.law.verdict.services","com.law.verdict.index"})
@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
public class CrawlerMain implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(CrawlerMain.class);
	@Autowired
	CrawlerServices crawlerServices;
	private static Map<String, String> caseMap = new HashMap<>();
	static {
		caseMap.put("penal", "刑事案件");
		caseMap.put("civil", "民事案件");
		caseMap.put("admin", "行政案件");
		caseMap.put("compensation", "赔偿案件");
		caseMap.put("executive", "执行案件");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CrawlerMain.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(args.length);
		for(String value: args) {
			System.out.println(value);
		}
		
		if (null == crawlerServices) {
			System.out.println("CrawlerServices crawlerServices is null");
			System.exit(0);
		} else {
			Map<String, String> params = new HashMap<>();
			//params.put("penal", "刑事案件");
			//params.put("civil", "民事案件");
			params.put("admin", "行政案 件");
			//params.put("compensation", "赔偿案件");
			//params.put("executive", "执行案件");
			//crawlerServices.beginCrawler("刑事案件");
			crawlerServices.beginCrawler(params);
		}
		
		System.out.println("=========== START SERVCIES SUCCESS ===========");
		log.info("START SERVCIES");
	}
}
