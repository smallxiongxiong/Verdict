package com.law.verdict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.law.verdict.service.IndexSearchService;
@RestController
@RequestMapping("/es")
public class EsController {
	@Autowired
	private IndexSearchService indexSearchService;
	
	@ResponseBody
	@RequestMapping(value = "/query", produces = {"application/json;charset=UTF-8"})
	public String findJudgement(String content){
		System.out.println(content);
		String content1 = "{  \"query\": {\"match\": {\"appellor\": \"耐克国际有限公司\"} }}";
	    return indexSearchService.queryIndex(content1);
	}
}
