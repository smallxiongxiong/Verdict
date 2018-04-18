package com.law.verdict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.verdict.dao.JudgementMapper;
import com.law.verdict.model.JudgementExample;
import com.law.verdict.model.JudgementWithBLOBs;
import com.law.verdict.vo.Article;
@Service
public class JudgementService {

	@Autowired
	private JudgementMapper judgementMapper;//这里会报错，但是并不会影响
	public List<JudgementWithBLOBs> findAll(int pageNum,int pageSize){
		//PageHelper.startPage(1, 10);
		return judgementMapper.selectJudgement(pageNum*pageSize,20);
	}
	
	public Article findBy(String docId){
		JudgementExample example = new JudgementExample();
		example.createCriteria().andDocIdEqualTo(docId);
		List<JudgementWithBLOBs> list = judgementMapper.selectByExampleWithBLOBs(example);
		JudgementWithBLOBs blobs = null;
		if(list.size()>0){
			blobs = list.get(0);
		}
		
		ParseArticle parse =  new ParseArticle();
		return parse.execute(blobs);
		
	}

	private ParseArticle ParseArticle() {
		// TODO Auto-generated method stub
		return null;
	}

}
