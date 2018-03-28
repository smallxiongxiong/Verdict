package parse;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.law.verdict.parse.Parse;
import com.law.verdict.parse.model.JudgementSimple;

import util.FileReader;

public class ContentParseTest {
	final static String LIST_CONTENT_PATH = "src/test/resources/data/ListContent.txt";
	final static String RELATED_FILE_PATH = "src/test/resources/data/relatedFiles.txt";
	final static String SUMMARY_PATH = "src/test/resources/data/summary.txt";
	final static String CONTENT_PATH = "src/test/resources/data/content.txt";
	final static String HTML_PATH = "src/test/resources/data/html.txt";
	
	List<String> listContents = null;
	List<String> relatedFiles = null;
	List<String> summary = null;
	List<String> content = null;
	List<String> html = null;
	

	@Before
	public void before() {
		listContents = FileReader.readByLine(LIST_CONTENT_PATH);
		relatedFiles = FileReader.readByLine(RELATED_FILE_PATH);
		summary = FileReader.readByLine(SUMMARY_PATH);
		content = FileReader.readByLine(CONTENT_PATH);
		html = FileReader.readByLine(HTML_PATH);
	}

	//@Test
	public void test() {
		for (String item : listContents) {
			List<JudgementSimple> result = Parse.parseListContent(item);
			assertEquals(5, result.size());
		}
	}
	//@Test
	public void testRelatedFiles() {
		for(String item: relatedFiles) {
			Parse.parseRelatedFiles(item);
		}
	}
	
	//@Test
	public void testSummary() {
		for(String item: summary) {
			Parse.parseSummary(item, "");
		}
	}
	//@Test
	public void testContent() {
		for(String item: content) {
			Parse.parseContent(item);
		}
	}
	@Test
	public void testHtml() {
		for(String item: html) {
			Parse.parseHtmlContent(item);
		}
	}
}
