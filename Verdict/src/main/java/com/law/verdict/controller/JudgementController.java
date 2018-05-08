package com.law.verdict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.law.verdict.model.JudgementWithBLOBs;
import com.law.verdict.service.JudgementService;
import com.law.verdict.vo.Article;

/**
 * 
 * @ClassName: JudgementController 
 * @Description: TODO() 
 * @author xiongbz
 * @date May 8, 2018 3:57:35 PM 
 *
 */
@RestController
@RequestMapping("/")
public class JudgementController {
	
	private static final String TYPE_ONE = "1";
	private static final String TYPE_TWO = "2";
	private static final String TYPE_THIRD = "3";
	private static final String TYPE_FOUR = "4";
	
	@Autowired
    private JudgementService judgementService;

    @RequestMapping("/")
    public ModelAndView findIndex(){
    	ModelAndView result = new ModelAndView("index");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/judgements/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public List<JudgementWithBLOBs> findAll(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
    	System.out.println("Num:"+pageNum+" size:"+pageSize);
        return judgementService.findAll(pageNum,pageSize);
    }
    
    @ResponseBody
    @RequestMapping(value = "/article/{docId}", produces = {"application/json;charset=UTF-8"})
    public Article findArticle(@PathVariable("docId") String docId){
        return judgementService.findArticleByDocId(docId);
    }
    
//    @ResponseBody
//    @RequestMapping(value = "/judgement/{docId}", produces = {"application/json;charset=UTF-8"})
//    public JudgementWithBLOBs findJudgement(@PathVariable("docId") String docId){
//        return judgementService.findJudgementByDocId(docId);
//    }
    
    @ResponseBody
    @RequestMapping(value = "/query/{idType}/{idValue}", produces = {"application/json;charset=UTF-8"})
    public List<JudgementWithBLOBs> findJudgement(@PathVariable("idType") String idType,@PathVariable("idValue") String idValue){
        String type = "";
    	if (TYPE_ONE.equals(idType)) {
    		return judgementService.findJudgementById(idValue);
        } else if (TYPE_TWO.equals(idType)) {
        	return judgementService.findJudgementByDocId(idValue);
        } else if (TYPE_THIRD.equals(idType)) {
        	type = "case_num";
        } else {
        	
        }
    	return null;
    }
}