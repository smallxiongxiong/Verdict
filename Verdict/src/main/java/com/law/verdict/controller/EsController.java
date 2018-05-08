package com.law.verdict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.law.verdict.service.IndexSearchService;

/**
 * 
 * @ClassName: EsController 
 * @Description: TODO() 
 * @author xiongbz
 * @date May 8, 2018 3:54:08 PM 
 *
 */
@RestController
@RequestMapping("/es")
public class EsController {
	@Autowired
	private IndexSearchService indexSearchService;
	
	@ResponseBody
	@RequestMapping(value = "/query", produces = {"application/x-www-form-urlencoded;charset=utf-8"},method=RequestMethod.POST)
	public String findJudgement(String conditions){
		System.out.println(conditions);
	    return indexSearchService.queryIndex(conditions);
	}
	
}
