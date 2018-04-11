package com.law.verdict;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.law.verdict.services.LawlpServices;

@SpringBootApplication
@MapperScan("com.law.verdict.parse.dao")
@ComponentScan("com.law.verdict.services")
public class LawlpMain implements CommandLineRunner {

	@Autowired
	LawlpServices lawlpServices;

	public static void main(String[] args) throws Exception {
		System.out.println("======================");
		SpringApplication.run(LawlpMain.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("=========== run begin ===========");
	}
}
