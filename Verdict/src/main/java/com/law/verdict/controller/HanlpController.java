package com.law.verdict.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hankcs.hanlp.seg.common.Term;
import com.law.verdict.service.HanlpService;

@RestController
@RequestMapping("/segment")
public class HanlpController {
	@Autowired
    private HanlpService hanlpService;

    @ResponseBody
    @RequestMapping(value = "/crf", produces = {"application/json;charset=UTF-8"})
    public List<String> crfSegment(String sentence){
    	//sentence ="我爱吃苹果，苹果好吃";
    	if(sentence==null)return null;
    	List<Term> list = hanlpService.crfSegment(sentence,true,false);
    	List<String> res = new ArrayList<String>();
    	for(int i=0;i<list.size();i++){
    		Term term = list.get(i);
    		res.add(term.word+" ");
    	}
    	return res;
    }
}