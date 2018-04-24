package com.law.verdict.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;
import com.law.verdict.dao.JudgementMapper;
import com.law.verdict.model.JudgementWithBLOBs;
import com.law.verdict.tools.StringTool;
import com.law.verdict.vo.Article;
@Service
public class JudgementService {

	@Autowired
	private JudgementMapper judgementMapper;//这里会报错，但是并不会影响
	
	public List<JudgementWithBLOBs> findAll(int pageNum,int pageSize){
		//PageHelper.startPage(1, 10);
		return judgementMapper.selectJudgement(pageNum*pageSize,20);
	}
	
	public Article findArticleByDocId(String docId){
		List<JudgementWithBLOBs> list = judgementMapper.selectJudgementByDocId(docId);
		JudgementWithBLOBs blobs = null;
		if(list.size()>0){
			blobs = list.get(0);
		}
		ParseArticle parse =  new ParseArticle();
		return parse.execute(blobs);
		
	}
	
	public List<JudgementWithBLOBs> findJudgementByDocId(String docId){
		List<JudgementWithBLOBs> list = judgementMapper.selectJudgementByDocId(docId);
		JudgementWithBLOBs blobs = null;
		if(list.size()>0){
			blobs = list.get(0);
			List<JudgementWithBLOBs> results = new ArrayList<JudgementWithBLOBs>();
			results.add(blobs);
			JudgementWithBLOBs newBlobs = new JudgementWithBLOBs();
			PerceptronSegmenter segmenter = null;
			try {
				segmenter = new PerceptronSegmenter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newBlobs.setTitle(separator(blobs.getTitle(),segmenter));
			newBlobs.setHead(blobs.getHead());
			newBlobs.setHead2(separator(blobs.getHead2(),segmenter));
			newBlobs.setFacts(separator(blobs.getFacts(),segmenter));
			newBlobs.setCause(separator(blobs.getCause(),segmenter));
			newBlobs.setJudgeResult(separator(blobs.getJudgeResult(),segmenter));
			newBlobs.setTailContent(blobs.getTailContent());
			results.add(newBlobs);
			return results;
		}
		return null;
		
	}

	public List<JudgementWithBLOBs> findJudgementById(String idValue) {
		List<JudgementWithBLOBs> list = judgementMapper.selectJudgementById(idValue);
		JudgementWithBLOBs blobs = null;
		if(list.size()>0){
			blobs = list.get(0);
			List<JudgementWithBLOBs> results = new ArrayList<JudgementWithBLOBs>();
			results.add(blobs);
			JudgementWithBLOBs newBlobs = new JudgementWithBLOBs();
			PerceptronSegmenter segmenter = null;
			try {
				segmenter = new PerceptronSegmenter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newBlobs.setTitle(separator(blobs.getTitle(),segmenter));
			newBlobs.setHead(blobs.getHead());
			newBlobs.setHead2(separator(blobs.getHead2(),segmenter));
			newBlobs.setFacts(separator(blobs.getFacts(),segmenter));
			newBlobs.setCause(separator(blobs.getCause(),segmenter));
			newBlobs.setJudgeResult(separator(blobs.getJudgeResult(),segmenter));
			newBlobs.setTailContent(blobs.getTailContent());
			results.add(newBlobs);
			return results;
		}
		return null;
	}

	private String separator(String str,PerceptronSegmenter segmenter) {
		String result = "";
		List<String> strs =StringTool.splitSentence(str);
		for(String s : strs){
			result += segmenter.segment(s).toString()+"\r\n";
		}
		
		return result;
	}

}
