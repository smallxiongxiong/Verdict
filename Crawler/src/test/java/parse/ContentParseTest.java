package parse;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.law.verdict.parse.Parse;
import com.law.verdict.parse.model.Judgement;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.utils.DateTools;

import util.FileReader;

public class ContentParseTest {
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

	@Before
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
	public void test() {
		for (String item : listContents) {
			List<JudgementSimple> result = Parse.parseListContent(item);
			assertEquals(5, result.size());
		}
	}

	// @Test
	public void testRelatedFiles() {
		for (String item : relatedFiles) {
			Parse.parseRelatedFiles(item);
		}
	}

	// @Test
	public void testSummary() {
		for (String item : summary) {
			Parse.parseSummary(item, "");
		}
	}

	@Test
	public void testContent() {
		for (String item : content) {
			Judgement result = Parse.parseContent(item);
			System.out.println(result.toString());
		}
	}

	//@Test
	public void testHtml() {
		for (String item : html) {
			Judgement result = Parse.parseHtmlContent(item);
			System.out.println(result);
		}
	}

	public void testPatter() {
		String content = "委托代理人：李晓红，北京市正见永申律师事务所律师。";
		Pattern p = Pattern.compile("(.[^：]*)：([^，]*)，([^。]*)。");
		Matcher m = p.matcher(content);
		if (m.find()) {

			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
		}
	}

	//@Test
	public void testCalendr() {
		String content = "2018-03-01";
		List<String> result = new ArrayList<>();
		result.add(content);
		int scope = 5;
		int times = 10;
		 result = DateTools.getScopeDay(content, scope, times);
		 for(String item: result) {
			 System.out.println(item);
		 }

	}

	// @Test
	public void testKeyWords() {
		for (String content : keyWords) {
			content = content.replace("\\\"", "\"");
			JsonReader jsonReader = new JsonReader(new StringReader(content));// 其中jsonContext为String类型的Json数据
			jsonReader.setLenient(true);

			JsonElement elements = new JsonParser().parse(jsonReader);
			JsonArray array = elements.getAsJsonArray();
			Iterator<JsonElement> it = array.iterator();
			while (it.hasNext()) {
				JsonArray tmp = it.next().getAsJsonObject().get("Child").getAsJsonArray();
				Iterator<JsonElement> tmpIt = tmp.iterator();
				while (tmpIt.hasNext()) {
					String text = tmpIt.next().getAsJsonObject().get("Key").getAsString();
					System.out.println(text);
				}
			}
		}
	}

	// @Test
	public void testType() {
		String content = dict.get(0);
		JsonReader jsonReader = new JsonReader(new StringReader(content));// 其中jsonContext为String类型的Json数据
		jsonReader.setLenient(true);

		JsonElement elements = new JsonParser().parse(jsonReader);
		JsonObject obj = elements.getAsJsonObject();
		JsonArray array = obj.getAsJsonArray("treeNodes");
		Iterator<JsonElement> it = array.iterator();
		while (it.hasNext()) {
			JsonObject tmp = it.next().getAsJsonObject();
			String id = tmp.get("id").getAsString();
			if (9 == id.length()) {
				System.out.println(tmp.get("name").getAsString());
			}
		}

	}
}
