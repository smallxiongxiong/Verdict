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

/**
 * 
 * @ClassName: ParseArticle
 * @Description: TODO()
 * @author xiongbz
 * @date May 8, 2018 4:17:11 PM
 *
 */
public class ParseArticle {
	private static final String TYPE_ONE = "罪犯";
	private static final String TYPE_TWO = "原告";
	private static final String TYPE_THREE = "上诉人";

	private static final int NUM_THREE = 3;
	private static final String SPACIAL_WORDS = "\r\n";
	
	private static final String DEFAULT_SENTENCE_SEPARATOR = "[。:：“”？?！!；;]";
	
	private Article article;
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
		List<String> causes = splitSentence(cause);
		for (String cau : causes) {
			Viewpoint point = new Viewpoint();
			point.setContext(cau);
			article.getViewpoint().add(point);
		}
	}

	/**
	 * @Title: parseFace @Description: TODO() @param: @param facts @return:
	 * void @throws
	 */
	/**
	 * @Title: parseFace @Description: TODO() @param: @param facts @return:
	 * void @throws
	 */
	/**
	 * @Title: parseFace @Description: TODO() @param: @param facts @return:
	 * void @throws
	 */
	private void parseFace(String facts) {
		String plaintiff = "";
		if (article.getPlaintiff()[0] != null) {
			plaintiff = article.getPlaintiff()[0].getPerson().getName();
		}
		String defendant = article.getDefendant()[0].getPerson().getName();
		List<String> factList = splitSentence(facts);
		String current = "";
		for (int i = 0; i < factList.size(); i++) {
			List<Opinion> pOpinion = new ArrayList<Opinion>();
			List<Opinion> dOpinion = new ArrayList<Opinion>();
			String str = factList.get(i);
			Opinion op = new Opinion();
			op.setContext(str);
			Sentence sentence = segmenterAnalyzer.analyze(str);
			IWord firstName = sentence.findFirstWordByLabel("nr");
			if (firstName != null && firstName.getValue().contains(plaintiff)) {
				current = plaintiff;
			}
			if (firstName != null && firstName.getValue().contains(defendant)) {
				current = defendant;
			}

			if (current.equals(plaintiff)) {
				pOpinion.add(op);
				article.getpOpinion().add(pOpinion);
			} else if (current.equals(defendant)) {
				dOpinion.add(op);
				article.getdOpinion().add(dOpinion);
			} else {

			}

		}
	}

	private void parseHead2(String head2) {
		// 罪犯/n 吴友付/nr ，/w 男/b ，/w 1957年/t 4月/t 16日/t 出生/v ，/w 汉族/nz ，/w 四川省/ns 隆昌县/ns
		// 人/n ，/w 文盲/n ，/w 现在/t 四川省达州/ns 监狱/n 服刑/v 。/w
		if (head2 != null && !"".equals(head2.trim())) {
			List<IWord> words = segmenterAnalyzer.analyze(head2).wordList;
			// 原告 上诉人 罪犯 可以更新角色信息
			if (words.size() < 1) {
				return;
			}
			if (TYPE_ONE.equals(words.get(0).getValue())) {
				// update 被告
			}
			if (TYPE_TWO.equals(words.get(0).getValue())) {
				// update 原告
			}
			if (TYPE_THREE.equals(words.get(0).getValue())) {
				// update 原告
			}
		}
	}

	private void parseHead(String head) {
		String[] list = head.split("\n");
		if (list.length == NUM_THREE) {
			article.getCourts().setOrganization(new Organization(list[0]));
			article.setCaseNum(list[2]);
		}

	}

	private void parseTitle(String title) {
		int index = 0;
		List<IWord> words = segmenterAnalyzer.analyze(title).wordList;
		for (int i = 0; i < words.size(); i++) {
			if (index > 2) {
				break;
			}
			if ("v".equals(words.get(i).getLabel())) {
				break;
			}
			if ("nr".equals(words.get(i).getLabel())) {
				IObject object = new IObject();
				object.setPerson(new Person(words.get(i).getValue()));
				article.getDefendant()[index] = object;
				index++;
			}
		}
		article.setTitle(title);
	}

	private List<String> splitSentence(String document) {
		List<String> sentences = new ArrayList<String>();
		for (String line : document.split(SPACIAL_WORDS)) {
			line = line.trim();
			if (line.length() == 0) {
				continue;
			}
			// [，,。:：“”？?！!；;]
			for (String sent : line.split(DEFAULT_SENTENCE_SEPARATOR)) {
				sent = sent.trim();
				if (sent.length() == 0) {
					continue;
				}
				sentences.add(sent);
			}
		}

		return sentences;
	}
}
