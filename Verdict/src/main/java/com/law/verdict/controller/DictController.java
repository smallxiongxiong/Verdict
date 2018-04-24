package com.law.verdict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.law.verdict.model.Dict;
import com.law.verdict.service.DictService;

@RestController
@RequestMapping("/")
public class DictController {
	@Autowired
    private DictService dictService;


    @ResponseBody
    @RequestMapping(value = "/dicts/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public List<Dict> findAll(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
    	System.out.println("Num:"+pageNum+" size:"+pageSize);
        return dictService.findAll(pageNum,pageSize);
    }
    
    @RequestMapping(value = "/dict/del/{id}", produces = {"application/json;charset=UTF-8"})
    public void deleteDict(@PathVariable("id") int id){
        dictService.delete(id);
    }
    
    @RequestMapping(value = "/dict/add",method = RequestMethod.POST)
    public Dict save(String key,String label, int frequency,boolean enable){
    	Dict d = new Dict();
    	d.setWord(key);
    	d.setLabel(label);
    	d.setFrequency(frequency);
    	d.setIsopen((byte) (enable? 1:0));
    	dictService.save(d);
    	System.out.println(d.getId()+"=========================================");
        return d;
    }
    
   
}