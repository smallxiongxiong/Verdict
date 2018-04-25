package com.law.verdict;

import java.io.IOException;

import com.hankcs.hanlp.HanLP.Config;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;

public class UserWord2Vec {
	public static void main1(String[] args) {
		WordVectorModel wordVectorModel;
		try {
			wordVectorModel = new WordVectorModel("D:/data/word2vec/hanlp-wiki-vec-zh.txt");
			// System.out.println(wordVectorModel.similarity("山东", "江苏"));
			// System.out.println(wordVectorModel.similarity("山东", "上班"));
			// System.out.println(wordVectorModel.nearest("山东"));
			DocVectorModel docVectorModel = new DocVectorModel(wordVectorModel);
//			String[] documents = new String[] { "山东苹果丰收", "农民在江苏种水稻", "奥运会女排夺冠", "世界锦标赛胜出", "中国足球失败", };
//
//			for (int i = 0; i < documents.length; i++) {
//				docVectorModel.addDocument(i, documents[i]);
//			}
//			System.out.println("============体育=============");
//			List<Map.Entry<Integer, Float>> entryList = docVectorModel.nearest("体育");
//			for (Map.Entry<Integer, Float> entry : entryList) {
//				System.out.printf("%d %s %.2f\n", entry.getKey(), documents[entry.getKey()], entry.getValue());
//			}
//
//			System.out.println("============农业=============");
//			entryList = docVectorModel.nearest("农业");
//			for (Map.Entry<Integer, Float> entry : entryList) {
//				System.out.printf("%d %s %.2f\n", entry.getKey(), documents[entry.getKey()], entry.getValue());
//			}
//			 System.out.println(docVectorModel.similarity("山西副省长贪污腐败开庭", "陕西村干部受贿违纪"));
//			 System.out.println(docVectorModel.similarity("山西副省长贪污腐败开庭", "群众举报省长"));
//			 System.out.println(docVectorModel.similarity("山西副省长贪污腐败开庭", "股票基金增长"));
			 PerceptronSegmenter segmenter = new PerceptronSegmenter(Config.PerceptronCWSModelPath);
			 System.out.println(segmenter.segment("下雨天地面积水"));
			 segmenter.learn("下雨天 地面 积水");
			 System.out.println(segmenter.segment("下雨天地面积水"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
