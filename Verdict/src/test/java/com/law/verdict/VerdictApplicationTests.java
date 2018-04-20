package com.law.verdict;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.law.verdict.dao.JudgementMapper;
import com.law.verdict.model.JudgementExample;
import com.law.verdict.model.JudgementWithBLOBs;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={VerdictApplication.class})
public class VerdictApplicationTests {
	@Autowired
	private JudgementMapper judgementMapper;//
	@Test
	public void contextLoads() {
		JudgementExample example = new JudgementExample();
		//example.createCriteria().andIdIsNotNull().
		//List<Judgement> result = judgementMapper.selectByExample(example);
		//List<JudgementWithBLOBs> result = judgementMapper.selectByExampleWithBLOBs(example);
		List<JudgementWithBLOBs> result = judgementMapper.selectJudgement(1,20);
		System.out.println("=============="+result.size());
	}

}
