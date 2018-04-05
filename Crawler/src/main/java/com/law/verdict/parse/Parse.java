package com.law.verdict.parse;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.law.verdict.parse.db.model.Judgement;
import com.law.verdict.parse.db.model.JudgementWithBLOBs;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.services.CrawlerDBOpt;

public class Parse {
	private static Logger logger = LoggerFactory.getLogger(Parse.class);
	/**
	 * 解析列表页内容
	 * 
	 * @param content
	 * @return
	 */
	public static List<JudgementSimple> parseListContent(String content) {

		List<JudgementSimple> result = new LinkedList<JudgementSimple>();
		if (null == content || content.trim().length() <= 0) {
			return result;
		}
		content = content.substring(1, content.length() - 1);
		content = content.replace("\\", "");
		JsonElement elements = new JsonParser().parse(content);
		JsonArray data = elements.getAsJsonArray();
		Iterator<JsonElement> it = data.iterator();
		while (it.hasNext()) {
			JsonElement e = it.next();
			JsonObject obj = e.getAsJsonObject();
			if (obj.has("Count")) {
				continue;
			}
			e = replaceKey(e, JudgementSimple.KEY_MAP);
			obj = e.getAsJsonObject();
			Gson gson = new Gson();
			JudgementSimple simple = gson.fromJson(obj.toString(), JudgementSimple.class);
			simple.setTimestamp(0);
			
			result.add(simple);
		}
		return result;
	}

	public static List<JudgementSimple> parseRelatedFiles(String content) {
		JsonElement elements = new JsonParser().parse(content);
		JsonObject data = elements.getAsJsonObject();
		JsonArray relatedJudges = data.get("RelateFile").getAsJsonArray();
		Iterator<JsonElement> it = relatedJudges.iterator();
		List<JudgementSimple> result = new LinkedList<>();
		while (it.hasNext()) {
			JsonElement e = it.next();
			e = replaceKey(e, JudgementSimple.KEY_MAP);
			JsonObject obj = e.getAsJsonObject();
			Gson gson = new Gson();
			JudgementSimple simple = gson.fromJson(obj.toString(), JudgementSimple.class);
			simple.setTimestamp(0);
			result.add(simple);
			System.out.println(simple.toString());
		}
		return result;
	}

	public static JudgementSimple parseSummary(String content, String docid) {

		return null;
	}

	public static JudgementWithBLOBs parseContent(String content) {
		if (null == content || content.isEmpty())
			return null;
		content = content.trim();
		content = getMainBody(content);
		content = content.replace("\\", "");
		content = content.trim();
		JudgementWithBLOBs judgement = null;
		try {
			
			JsonReader jsonReader = new JsonReader(new StringReader(content));
			jsonReader.setLenient(true);
			JsonElement elements = new JsonParser().parse(jsonReader);
			JsonObject obj = elements.getAsJsonObject();
			String title = obj.get("Title").getAsString();
			String pubDate = obj.get("PubDate").getAsString();
			String text = obj.get("Html").getAsString();
			judgement = parseHtmlContent(text);
			judgement.setTitle(title);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				judgement.setPubDate(sdf.parse(pubDate));
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("dateParseException",e);
			}
		}catch(Exception e) {
			logger.error("json parse Exception, content:{}", content);
		}
		return judgement;
	}

	/**
	 * 从字符串中，提取装有正文内容的json字符串
	 * 
	 * @param content
	 * @return
	 */
	private static String getMainBody(String content) {
		String begin = "= \"";
		int beginIndex = content.indexOf(begin);
		String end = "\"; ";
		int endIndex = content.indexOf(end);
		return content.substring(beginIndex + begin.length(), endIndex);
	}

	private static JudgementWithBLOBs parseHtmlContent(String html) {
		Document doc = Jsoup.parseBodyFragment(html);
		Elements headContent = doc.getElementsByAttributeValue("name", "WBSB");
		JudgementWithBLOBs detail = new JudgementWithBLOBs();
		StringBuilder sb = new StringBuilder();
		List<String> parseResult = parseDetailContent(headContent);
		for (String item : parseResult) {
			sb.append(item).append("\n");
		}
		detail.setHead(sb.toString());
		sb = new StringBuilder();
		Elements headContent1 = doc.getElementsByAttributeValue("name", "DSRXX");

		parseResult = parseDetailContent(headContent1);
		for (String item : parseResult) {
			sb.append(item).append("\n");
		}
		detail.setHead2(sb.toString());
		sb = new StringBuilder();

		Elements facts = doc.getElementsByAttributeValue("name", "SSJL");
		if (facts.isEmpty()) {
			facts = doc.getElementsByAttributeValue("name", "AJJBQK");
		}

		parseResult = parseDetailContent(facts);
		for (String item : parseResult) {
			sb.append(item).append("\n");
		}
		detail.setFacts(sb.toString());
		sb = new StringBuilder();

		Elements cause = doc.getElementsByAttributeValue("name", "CPYZ");
		parseResult = parseDetailContent(cause);
		for (String item : parseResult) {
			sb.append(item).append("\n");
		}
		detail.setCause(sb.toString());
		sb = new StringBuilder();
		Elements judgeResult = doc.getElementsByAttributeValue("name", "PJJG");
		parseResult = parseDetailContent(judgeResult);
		for (String item : parseResult) {
			sb.append(item).append("\n");
		}
		detail.setJudgeResult(sb.toString());
		sb = new StringBuilder();

		Elements tailContent = doc.getElementsByAttributeValue("name", "WBWB");

		parseResult = parseDetailContent(tailContent);
		for (String item : parseResult) {
			sb.append(item).append("\n");
		}
		detail.setTailContent(sb.toString());
		sb = new StringBuilder();

		return detail;
	}

	// 解析正文内容
	private static List<String> parseDetailContent(Elements begin) {
		List<String> result = new ArrayList<>();
		for (Element item : begin) {
			Element tmp = (Element) item.nextSibling();
			while (null != tmp) {
				result.add(tmp.text());
				tmp = tmp.nextElementSibling();
				if (null == tmp || "a".equals(tmp.tagName())) {
					break;
				}
			}
		}
		return result;
	}

	public static JsonElement replaceKey(JsonElement source, Map<String, String> rep) {
		if (source == null || source.isJsonNull()) {
			return JsonNull.INSTANCE;
		}
		if (source.isJsonPrimitive()) {
			return source;
		}
		if (source.isJsonArray()) {
			JsonArray jsonArr = source.getAsJsonArray();
			JsonArray jsonArray = new JsonArray();
			jsonArr.forEach(item -> {
				jsonArray.add(replaceKey(item, rep));
			});
			return jsonArray;
		}
		if (source.isJsonObject()) {
			JsonObject jsonObj = source.getAsJsonObject();
			Iterator<Entry<String, JsonElement>> iterator = jsonObj.entrySet().iterator();
			JsonObject newJsonObj = new JsonObject();
			iterator.forEachRemaining(item -> {
				String key = item.getKey();
				JsonElement value = item.getValue();
				if (rep.containsKey(key)) {
					String newKey = rep.get(key);
					key = newKey;
				}
				newJsonObj.add(key, replaceKey(value, rep));
			});

			return newJsonObj;
		}
		return JsonNull.INSTANCE;
	}
}
