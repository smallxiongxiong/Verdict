package com.law.verdict.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.corpus.document.sentence.word.IWord;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;
import com.law.verdict.model.JudgementWithBLOBs;
import com.law.verdict.vo.Article;
import com.law.verdict.vo.IObject;
import com.law.verdict.vo.Opinion;
import com.law.verdict.vo.Organization;
import com.law.verdict.vo.Person;
import com.law.verdict.vo.Viewpoint;

public class ParseArticle {
	private Article article ;
	private PerceptronLexicalAnalyzer segmenterAnalyzer;
	PerceptronSegmenter segmenter; 
	public ParseArticle() {
		super();
		article = new Article();
		try {
			segmenterAnalyzer = new PerceptronLexicalAnalyzer();
			segmenter = new PerceptronSegmenter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Article execute(JudgementWithBLOBs blobs) {
		parseTitle(blobs.getTitle());
		parseHead(blobs.getHead());
		parseHead2(blobs.getHead2());
		parseFace(blobs.getFacts());
		parseCause(blobs.getCause());
		parseResult(blobs.getJudgeResult());
		return article;
	}

	private void parseResult(String judgeResult) {
		List<String> res = new ArrayList<String>();
		res.add(judgeResult);
		article.getResult().add(res);
	}

	private void parseCause(String cause) {
		List<String> causes =splitSentence(cause);
		for(String cau : causes){
			Viewpoint point = new Viewpoint();
			point.setContext(cau);
			article.getViewpoint().add(point);
		}
	}

	private void parseFace(String facts) {
		String yuan_gao = "";
		if(article.getPlaintiff()[0]!=null){
			yuan_gao = article.getPlaintiff()[0].getPerson().getName();
		}
		String bei_gao = article.getDefendant()[0].getPerson().getName();
		List<String> factList = splitSentence(facts);
		String current = "";
		for(int i=0;i<factList.size();i++){
			List<Opinion> pOpinion = new ArrayList<Opinion>();
			List<Opinion> dOpinion = new ArrayList<Opinion>();
			String str = factList.get(i);
			Opinion op = new Opinion();
			op.setContext(str);
			Sentence sentence = segmenterAnalyzer.analyze(str);
			IWord firstName = sentence.findFirstWordByLabel("nr");
			if(firstName!=null && firstName.getValue().contains(yuan_gao) ){
				current = yuan_gao;
			}
			if(firstName!=null && firstName.getValue().contains(bei_gao) ){
				current = bei_gao;
			}
			
			if(current.equals(yuan_gao)){
				pOpinion.add(op);
				article.getpOpinion().add(pOpinion);
			}else if (current.equals(bei_gao)){//添加被告观点
				dOpinion.add(op);
				article.getdOpinion().add(dOpinion);
			}else{
				
			}
			
		}
	}

	private void parseHead2(String head2) {
		//罪犯/n 吴友付/nr ，/w 男/b ，/w 1957年/t 4月/t 16日/t 出生/v ，/w 汉族/nz ，/w 四川省/ns 隆昌县/ns 人/n ，/w 文盲/n ，/w 现在/t 四川省达州/ns 监狱/n 服刑/v 。/w
		if(head2!=null&&!"".equals(head2.trim())){
			List<IWord> words = segmenterAnalyzer.analyze(head2).wordList;
			//原告 上诉人 罪犯  可以更新角色信息
			if(words.size()<1)return;
			if("罪犯".equals(words.get(0).getValue())){
				//update 被告
			}
			if("原告".equals(words.get(0).getValue())){
				//update 原告
			}
			if("上诉人".equals(words.get(0).getValue())){
				//update 原告
			}
		}
	}

	private void parseHead(String head) {
		String[] list = head.split("\n");
		if(list.length==3){
			article.getCourts().setOrganization(new Organization(list[0]));
			article.setCaseNum(list[2]);
		}
		
	}

	private void parseTitle(String title) {
		int index =0;
		List<IWord> words = segmenterAnalyzer.analyze(title).wordList;
		for(int i =0 ;i<words.size();i++){
			if(index>2)break;
			if("v".equals(words.get(i).getLabel()))break;//
			if("nr".equals(words.get(i).getLabel())){			
				IObject object = new IObject();
				object.setPerson(new Person(words.get(i).getValue()));
				article.getDefendant()[index]=object;
				index++;
			}
		}
		article.setTitle(title);
	}
	private List<String> splitSentence(String document)
    {
		String default_sentence_separator = "[。:：“”？?！!；;]";
        List<String> sentences = new ArrayList<String>();
        for (String line : document.split("[\r\n]"))
        {
            line = line.trim();
            if (line.length() == 0) continue;
            for (String sent : line.split(default_sentence_separator))		// [，,。:：“”？?！!；;]
            {
                sent = sent.trim();
                if (sent.length() == 0) continue;
                sentences.add(sent);
            }
        }

        return sentences;
    }
}
