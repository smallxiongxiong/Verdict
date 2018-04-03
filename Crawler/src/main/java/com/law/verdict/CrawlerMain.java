package com.law.verdict;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.law.verdict.parse.dao")
public class CrawlerMain implements CommandLineRunner {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(CrawlerMain.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		
	}

}
