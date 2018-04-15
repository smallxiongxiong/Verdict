package com.law.verdict;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Controller
//@EnableWebMvc
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
