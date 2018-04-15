package com.law.verdict.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.BasicTokenizer;

@Service
public class HanlpService {
	private static Logger logger = LoggerFactory.getLogger(HanlpService.class);

	public List<Term> basicTokenizer(String sourceStr) {
		logger.info("基础分词只进行基本NGram分词，不识别命名实体，不使用用户词典");
		return BasicTokenizer.segment(sourceStr);
	}
	
	public Sentence cRFLexicalAnalyzer(String sourceStr) {
		logger.info("CRF词法分析器");
		CRFLexicalAnalyzer analyzer;
		try {
			analyzer = new CRFLexicalAnalyzer();
			return	analyzer.analyze(sourceStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<Term> crfSegment(String sourceStr,boolean showNature,boolean customDict) {
		logger.info("CRF分词");
		HanLP.Config.ShowTermNature = showNature;    // 关闭词性显示
        final Segment segment = new CRFSegment().enableCustomDictionary(customDict);
        return segment.seg(sourceStr);
	}
	
}
