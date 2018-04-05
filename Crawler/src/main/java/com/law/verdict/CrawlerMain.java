package com.law.verdict;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.law.verdict.services.CrawlerServices;

@SpringBootApplication
@MapperScan("com.law.verdict.parse.dao")
@ComponentScan("com.law.verdict.services")
public class CrawlerMain implements CommandLineRunner {

	@Autowired
	CrawlerServices crawlerServices;

	public static void main(String[] args) throws Exception {
		System.out.println("======================");
		SpringApplication.run(CrawlerMain.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("=========== run begin ===========");
		if (null == crawlerServices) {
			System.out.println("CrawlerServices crawlerServices is null");
			System.exit(0);
		}else {
			crawlerServices.beginCrawler("刑事案件");
		}
	}
}
