package com.law.verdict;

import java.io.IOException;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.corpus.document.sentence.word.IWord;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;
import com.hankcs.hanlp.model.perceptron.model.LinearModel;

public class LogicAnalysisTest {
	public static void main1(String[] args) {
		String title ="王天津间谍罪减刑刑事裁定书";
		String fact = "吉林省九台市人民检察院以吉九检刑诉字（2014）第461号起诉书，指控被告人孙某某、常某某犯非法销售间谍专业器材罪，"
				+ "于2014年11月13日向本院提起公诉，本院依法组成合议庭，公开庭审理了本案，九台市人民检察院指派检察员梁海燕出庭支持公诉。"
				+ "被告人孙某某、常某某到庭参加诉讼，现已审理终结。";
		String cause ="本院认为，公诉机关指控被告人孙某某、常某某非法销售间谍专用器材的事实，有经过庭审举证核实的证据予以证明，"
				+ "被告人孙某某、常某某的行为构成非法销售间谍专用器材罪，公诉机关指控的犯罪事实及罪名成立。"
				+ "鉴于被告人孙某某、常某某如实供述自己的罪行，违法所得已被追缴，可从轻处罚。"
				+ "关于被告人孙某某的辩护人认为，对公诉机关指控被告人孙某某犯非法销售间谍专用器材罪没有意见，"
				+ "但被告人孙某某系初犯，认罪态度好，具有悔罪表现，对其宣告缓刑，不致再危害社会的辩护意见，经审理认为，"
				+ "具有事实及法律依据，应予支持。关于被告人常某某的辩护人认为，被告人常某某的行为构成非法销售间谍专用器材罪，"
				+ "被告人常某某系初犯、偶犯，社会危害性较小，被告人丈夫长期在外地工作，有11岁孩子需要照顾，"
				+ "建议法院对被告人常某某从轻处罚的辩护意见。经审理认为，具有事实及法律依据，应予支持。"
				+ "依照《中华人民共和国刑法》第二百八十三条、第六十七条、第六十四条、第七十二条、第七十三条二款、三款之规定，判决如下：";
				
		String result ="一、被告人孙某某犯非法销售间谍专用器材罪，判处有期徒刑一年缓刑二年。"
				+ "（缓刑考验期，从判决确定之日起计算）二、被告人常某某犯非法销售间谍专用器材罪，判处有期徒刑一年缓刑二年。"
				+ "（缓刑考验期，从判决确定之日起计算）三、被告人孙某某违法所得人民币六千元、被告人常某某违法所得人民币一千二百五十元均予以追缴（已追缴）。"
				+ "如不服本判决，可在接到本判决书的第二日起十日内，通过本院或者直接上诉到吉林省长春市中级人民法院，书面上诉的，"
				+ "提交上诉状正本一份，副本二份。";
		
		try {
			PerceptronSegmenter segmenter = new PerceptronSegmenter();
			segmenter.learn("刑事判决书 ");
			PerceptronLexicalAnalyzer segmenterAnalyzer = new PerceptronLexicalAnalyzer();
			segmenterAnalyzer.learn("刑事判决书/n");
			Sentence s  = segmenterAnalyzer.analyze("罪犯吴友付，男，1957年4月16日出生，汉族，四川省隆昌县人，文盲，现在四川省达州监狱服刑。");
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
