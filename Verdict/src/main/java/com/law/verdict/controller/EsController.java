package com.law.verdict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.law.verdict.model.JudgementWithBLOBs;
import com.law.verdict.service.IndexSearchService;
@RestController
@RequestMapping("/es")
public class EsController {
	@Autowired
	private IndexSearchService indexSearchService;
	
	@ResponseBody
	@RequestMapping(value = "/query", produces = {"application/x-www-form-urlencoded;charset=utf-8"},method=RequestMethod.POST)
	public String findJudgement(String conditions){
		System.out.println(conditions);
		//String content1 = "{  \"query\": {\"match\": {\"appellor\": \"耐克国际有限公司\"} }}";
		String content = "{  \"query\": {\"match\": "+ conditions +"}}";
	    return indexSearchService.queryIndex(content);
	}
	
}
