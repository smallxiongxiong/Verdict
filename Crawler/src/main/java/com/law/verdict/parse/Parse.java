package com.law.verdict.parse;

import java.io.StringReader;
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
import com.law.verdict.parse.db.model.JudgementWithBLOBs;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.parse.model.Statute;
import com.law.verdict.utils.StringTools;

public class Parse {
	private static Logger logger = LoggerFactory.getLogger(Parse.class);

	/**
	 * 解析列表页内容
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static List<JudgementSimple> parseListContent(String content) throws RuntimeException {

		List<JudgementSimple> result = new LinkedList<JudgementSimple>();
		if (null == content || content.trim().length() <= 0) {
			return result;
		}
		content = content.substring(1, content.length() - 1);
		content = content.replace("\\", "");
		JsonElement elements = new JsonParser().parse(content);
		JsonArray data = elements.getAsJsonArray();
		Iterator<JsonElement> it = data.iterator();
		try {
			while (it.hasNext()) {
				JsonElement e = it.next();
				JsonObject obj = e.getAsJsonObject();
				if (obj.has("Count")) {
					int count = obj.get("Count").getAsInt();
					if (count <= 0) {
						break;
					}
					continue;
				}
				e = replaceKey(e, JudgementSimple.KEY_MAP);
				obj = e.getAsJsonObject();
				Gson gson = new Gson();
				JudgementSimple simple = gson.fromJson(obj.toString(), JudgementSimple.class);
				simple.setTimestamp(0);
				result.add(simple);
			}
		} catch (Exception e) {
			result = null;
			throw new RuntimeException(e);

		}
		return result;
	}

	/**
	 * 解析doc的关联doc
	 * 
	 * @param content
	 * @return
	 */
	public static List<JudgementSimple> parseRelatedFiles(String content) {
		JsonReader jsonReader = new JsonReader(new StringReader(content));
		jsonReader.setLenient(true);
		JsonElement elements = new JsonParser().parse(jsonReader);
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
		}
		return result;
	}

	/**
	 * 解析文书的摘要摘要文档
	 * 
	 * @param content
	 * @param docid
	 * @return
	 */
	public static void parseSummary(String content, JudgementSimple js) {
		content = content.substring(1, content.length() - 1);
		content = content.replace("\\u0027", "'");
		content = content.replace("\\", "");
		try {
			JsonReader jsonReader = new JsonReader(new StringReader(content));
			jsonReader.setLenient(true);
			JsonElement elements = new JsonParser().parse(jsonReader);
			JsonObject relatedObj = elements.getAsJsonObject();
			JsonArray relateInfoArray = relatedObj.get("RelateInfo").getAsJsonArray();
			parseRelatedInfoSummary(js, relateInfoArray);
			JsonArray legalBase = relatedObj.get("LegalBase").getAsJsonArray();
			parseLegalBaseSummary(js, legalBase);
		} catch (Exception e) {
			logger.error("json parse Summary Exception, content:" + content, e);

		}
	}

	/**
	 * 解析摘要文档中的发条信息
	 * 
	 * @param js
	 * @param legalBase
	 */
	private static void parseLegalBaseSummary(JudgementSimple js, JsonArray legalBase) {
		Iterator<JsonElement> it = legalBase.iterator();
		try {
			while (it.hasNext()) {
				JsonElement e = it.next();
				Statute tmp = new Statute();
				JsonObject tmpObj = e.getAsJsonObject();
				tmp.setName(tmpObj.get("法规名称").getAsString());
				JsonArray legalItem = tmpObj.get("Items").getAsJsonArray();
				Iterator<JsonElement> itemsIt = legalItem.iterator();
				while (itemsIt.hasNext()) {
					JsonElement legal = itemsIt.next();
					Statute.ArticlesLaw law = new Statute.ArticlesLaw();
					law.setName(legal.getAsJsonObject().get("法条名称").getAsString());
					law.setContent(legal.getAsJsonObject().get("法条内容").getAsString());
					tmp.addDetailLaw(law);
				}
				js.addStatute(tmp);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解析摘要中的摘要信息
	 * 
	 * @param js
	 * @param relateInfoArray
	 */
	private static void parseRelatedInfoSummary(JudgementSimple js, JsonArray relateInfoArray) {
		Iterator<JsonElement> it = relateInfoArray.iterator();
		try {
			while (it.hasNext()) {
				JsonElement e = it.next();
				if (e.isJsonObject()) {
					JsonObject obj = e.getAsJsonObject();
					String key = obj.get("key").getAsString();
					String name = obj.get("name").getAsString();
					String value = obj.get("value").getAsString();
					if ("reason".equals(key)) {
						if ("行政管理范围".equals(name)) {
							js.setAdministrativeScope(value);
						} else {
							js.setAdministrativeType(value);
						}
					} else if ("appellor".equals(key)) {
						js.setAppellor(value.replace(",", " "));
					}

				}
			}
		} catch (Exception e) {
			logger.error(relateInfoArray.toString(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解析文书的正文文档
	 * 
	 * @param content
	 * @return
	 */
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
			judgement.setPubDate(sdf.parse(pubDate));
		} catch (Exception e) {
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
