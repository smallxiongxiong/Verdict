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
	List<String> listContents = null;

	@Before
	public void before() {
		listContents = FileReader.readByLine(LIST_CONTENT_PATH);
	}

	@Test
	public void test() {
		for (String item : listContents) {
			List<JudgementSimple> result = Parse.parseListContent(item);
			assertEquals(5, result.size());

		}
	}

}
