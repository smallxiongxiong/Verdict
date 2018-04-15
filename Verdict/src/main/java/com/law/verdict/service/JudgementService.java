package com.law.verdict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.verdict.dao.JudgementMapper;
import com.law.verdict.model.JudgementWithBLOBs;
@Service
public class JudgementService {

	@Autowired
	private JudgementMapper judgementMapper;//这里会报错，但是并不会影响
	public List<JudgementWithBLOBs> findAll(int pageNum,int pageSize){
		//PageHelper.startPage(1, 10);
		return judgementMapper.selectJudgement(pageNum*pageSize,20);
	}

}
