package com.law.verdict.parse;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.law.verdict.parse.model.JudgementSimple;

public class Parse {
	public static List<JudgementSimple> parseListContent(String content) {
		List<JudgementSimple> result = new LinkedList<JudgementSimple>();
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
			System.out.println(simple.toString());
			result.add(simple);
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
