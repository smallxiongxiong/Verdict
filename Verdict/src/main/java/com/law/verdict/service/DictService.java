package com.law.verdict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.verdict.dao.DictMapper;
import com.law.verdict.model.Dict;
import com.law.verdict.model.JudgementWithBLOBs;
import com.law.verdict.vo.Article;
@Service
public class DictService {

	@Autowired
	private DictMapper dictMapper;//这里会报错，但是并不会影响
	
	public List<Dict> findAll(int pageNum,int pageSize){
		//PageHelper.startPage(1, 10);
		return dictMapper.selectAll(pageNum*pageSize,20);
	}
	
	public void save(Dict dict){
		dictMapper.myInsert(dict.getWord(),dict.getLabel(),dict.getFrequency(),dict.getIsopen());
	}
	
	public void update(Dict dict){
		//dictMapper.updateByExample(record, example)
	}
	
	public void delete(int id){
		dictMapper.myDelete(id);
	}
	
}
