package db;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.law.verdict.CrawlerMain;
import com.law.verdict.parse.Parse;
import com.law.verdict.parse.dao.JudgementMapper;
import com.law.verdict.parse.db.model.Judgement;
import com.law.verdict.parse.db.model.JudgementExample;
import com.law.verdict.parse.db.model.JudgementWithBLOBs;

import util.FileReader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlerMain.class)
public class SpringDBTest {
	final static String LIST_CONTENT_PATH = "src/test/resources/data/ListContent.txt";
	final static String RELATED_FILE_PATH = "src/test/resources/data/relatedFiles.txt";
	final static String SUMMARY_PATH = "src/test/resources/data/summary.txt";
	final static String CONTENT_PATH = "src/test/resources/data/content3.txt";
	final static String HTML_PATH = "src/test/resources/data/html.txt";

	List<String> listContents = null;
	List<String> relatedFiles = null;
	List<String> summary = null;
	List<String> content = null;
	List<String> html = null;
	List<String> dict = null;
	List<String> keyWords = null;
	@Autowired
	JudgementMapper judgeMentMapper;

	//@Before
	public void before() {
		listContents = FileReader.readByLine(LIST_CONTENT_PATH);
		relatedFiles = FileReader.readByLine(RELATED_FILE_PATH);
		summary = FileReader.readByLine(SUMMARY_PATH);
		content = FileReader.readByLine(CONTENT_PATH);
		html = FileReader.readByLine(HTML_PATH);
		dict = FileReader.readByLine("src/test/resources/data/dict.txt");
		keyWords = FileReader.readByLine("src/test/resources/data/treeContent.txt");
	}

	// @Test
	public void testContent() {
		for (String item : content) {
			JudgementWithBLOBs result = Parse.parseContent(item);
			System.out.println(result.toString());
			judgeMentMapper.insert(result);
		}
	}

	@Test
	public void testQuery() {
		JudgementExample example = new JudgementExample();
//		example.createCriteria().andDocIdEqualTo("0000149a-b9bc-47f9-be8d-c31e2ab0cdb7");
		example.createCriteria().andDocIdLike("%b9bc%");
		
		List<Judgement> result = judgeMentMapper.selectByExample(example);
		System.out.println("=============" + result.size());
	}
}
