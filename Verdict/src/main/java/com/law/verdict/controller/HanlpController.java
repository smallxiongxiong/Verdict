package com.law.verdict.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hankcs.hanlp.seg.common.Term;
import com.law.verdict.model.Dict;
import com.law.verdict.service.DictService;
import com.law.verdict.service.HanlpService;

@RestController
@RequestMapping("/segment")
public class HanlpController {
	@Autowired
    private HanlpService hanlpService;
	@Autowired
    private DictService dictService;

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
    
    @RequestMapping(value = "/test", produces = {"application/json;charset=UTF-8"})
    public List<String> test(String source){
    	if(source==null)return null;
    	return hanlpService.testFC(source);
    }
    
    @RequestMapping(value = "/train", produces = {"application/x-www-form-urlencoded;charset=UTF-8"})
    public String train(String words){
    	if(words==null)return null;
    	if(hanlpService.trainFC(words)){
    		Dict d = new Dict();
        	d.setWord(words);
        	d.setLabel("long word");
        	d.setFrequency(1);
        	d.setIsopen((byte) 1);
        	dictService.save(d);
    		return "ok";
    	}
    	return "fail";
    }
    
    @RequestMapping(value = "/split/words", produces = {"application/json;charset=UTF-8"})
    public List<String> splitWords(String source,int type){
    	if(source==null)return null;
    	if(type==0) {
    		return hanlpService.testFC(source);
    	}else if(type == 1){
    		List<Term> list = hanlpService.split(source);
    		List<String> res = new ArrayList<String>();
        	for(int i=0;i<list.size();i++){
        		Term term = list.get(i);
        		res.add(term.word+" ");
        	}
        	return res;
    	}else {
    		return null;
    	}
    }
}