package com.law.verdict.parse;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.law.verdict.parse.model.JudgementSimple;
import com.law.verdict.parse.model.Lawler;

public class Parse {
	/**
	 * 解析列表页内容
	 * 
	 * @param content
	 * @return
	 */
	public static List<JudgementSimple> parseListContent(String content) {
		
		List<JudgementSimple> result = new LinkedList<JudgementSimple>();
		if(null == content || content.trim().length() <= 0) {
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
			System.out.println(simple.toString());
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

	public static JudgementSimple parseContent(String content) {
		if (null == content || content.isEmpty())
			return null;
		content = content.trim();

		if (content.startsWith("var jsonHtmlData")) {
			int begin = content.indexOf("\"");
			int end = content.lastIndexOf("\"");
			String tmp = content.substring(begin + 1, end);
			tmp = tmp.replace("\\", "");
			tmp = tmp.trim();
			System.out.println(tmp);
			JsonReader jsonReader = new JsonReader(new StringReader(tmp));// 其中jsonContext为String类型的Json数据
			jsonReader.setLenient(true);

			JsonElement elements = new JsonParser().parse(jsonReader);
			JsonObject obj = elements.getAsJsonObject();
			System.out.println(obj.get("Title").getAsString());
			System.out.println(obj.get("PubDate").getAsString());
			System.out.println(obj.get("Html").getAsString());

		}

		JudgementSimple simple = new JudgementSimple();
		return simple;
	}

	public static Map<String, Object> parseHtmlContent(String html) {
		Document doc = Jsoup.parseBodyFragment(html);
		Elements e =  doc.getElementsByAttributeValue("name", "WBSB");
		for(Element item: e) {
			System.out.println(item.tagName());
			System.out.println(item.nextSibling().outerHtml());
		}
		return null;
	}

	public static void test() {
		Document doc = null;
		Pattern p = Pattern.compile("(.[^：]*)：([^，]*)，([^。]*)。");

		Elements divs = doc.getElementsByTag("div");

		List<Lawler> lawlers = new ArrayList<>();
		Matcher matcher = null;
		for (Element div : divs) {
			String tmp = div.text();
			matcher = p.matcher(tmp);
			if (matcher.find()) {
				System.out.println(tmp);
				System.out.println(matcher.group(1));
				System.out.println(matcher.group(2));
				System.out.println(matcher.group(3));
			} else {
				System.out.println(tmp);
			}
		}

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
