package index;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.law.verdict.CrawlerMain;
import com.law.verdict.index.IndexOperation;
import com.law.verdict.parse.Parse;
import com.law.verdict.parse.model.JudgementSimple;

import util.FileReader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlerMain.class)
public class IndexTest {

	@Autowired
	IndexOperation<JudgementSimple> opt;
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
	JudgementSimple js;

	@Before
	public void before() {
		listContents = FileReader.readByLine(LIST_CONTENT_PATH);
		relatedFiles = FileReader.readByLine(RELATED_FILE_PATH);
		summary = FileReader.readByLine(SUMMARY_PATH);
		content = FileReader.readByLine(CONTENT_PATH);
		html = FileReader.readByLine(HTML_PATH);
		dict = FileReader.readByLine("src/test/resources/data/dict.txt");
		keyWords = FileReader.readByLine("src/test/resources/data/treeContent.txt");
		System.setProperty("pathPre", "src/main/resources/");
		
		for (String item : summary) {
			js = new JudgementSimple();
			Parse.parseSummary(item, js);
			System.out.println(js.toString());
		}
		
		
	}

	
	
	@Test
	public void testSave() {
		Gson gson = new Gson();
		js.setCaseID("1000011");
		String content = gson.toJson(js);
		System.err.println(content);
		opt.addIndexDocument(content,js.getCaseID());
	}
}
