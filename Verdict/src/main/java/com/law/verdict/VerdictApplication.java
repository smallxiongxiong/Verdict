package com.law.verdict;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

/**
 * 
* <p>Title: VerdictApplication</p>  
* <p>Description: </p>  
* @author xiongbz  
* @date May 8, 2018
 */

@Controller
@SpringBootApplication
@MapperScan("com.law.verdict.dao")
public class VerdictApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(VerdictApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("start...");
		
	}
}
