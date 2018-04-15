package com.law.verdict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.law.verdict.model.Judgement;
import com.law.verdict.model.JudgementWithBLOBs;
import com.law.verdict.service.JudgementService;

@RestController
@RequestMapping("/")
public class JudgementController {
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
}