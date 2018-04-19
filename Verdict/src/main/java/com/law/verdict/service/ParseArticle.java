package com.law.verdict.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.corpus.document.sentence.word.IWord;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;
import com.law.verdict.model.JudgementWithBLOBs;
import com.law.verdict.vo.Article;
import com.law.verdict.vo.IObject;
import com.law.verdict.vo.Organization;
import com.law.verdict.vo.Person;

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
		// TODO Auto-generated method stub
		
	}

	private void parseCause(String cause) {
		// TODO Auto-generated method stub
		
	}

	private void parseFace(String facts) {
		List<String> factList = splitSentence(facts);
		for(String fact : factList){
			//segmenter.
		}
	}

	private void parseHead2(String head2) {
		//罪犯/n 吴友付/nr ，/w 男/b ，/w 1957年/t 4月/t 16日/t 出生/v ，/w 汉族/nz ，/w 四川省/ns 隆昌县/ns 人/n ，/w 文盲/n ，/w 现在/t 四川省达州/ns 监狱/n 服刑/v 。/w
		if(head2!=null&&!"".equals(head2.trim())){
			List<IWord> words = segmenterAnalyzer.analyze(head2).wordList;
			//原告 上诉人 罪犯  可以更新角色信息
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