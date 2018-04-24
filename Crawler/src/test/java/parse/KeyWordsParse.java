package parse;

import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.law.verdict.parse.model.TreeContent;
import com.law.verdict.utils.FileTools;

public class KeyWordsParse {
	static String pathname = "src/test/resources/keywords/";
	static String endName = "src/main/resources/keywords/";
	private Map<String, List<String>> result = new HashMap<>();

	@Before
	public void before() {
		File directory = new File(pathname);
		String[] childFilepath = directory.list();
		for (String item : childFilepath) {
			result.put(item, FileTools.readTolines(new File(pathname + item)));
		}
	}

	@Test
	public void testFile() {
		Set<Map.Entry<String, List<String>>> value = result.entrySet();
		
		for (Map.Entry<String, List<String>> item : value) {
			String key = item.getKey().replace("json", "txt");
			File target = new File(endName + key);
			List<String> tmp = item.getValue();
			for (String line : tmp) {
				line = line.substring(1, line.length() - 1);
				line = line.replace("\\", "");
				JsonReader jsonReader = new JsonReader(new StringReader(line));
				jsonReader.setLenient(true);
				JsonParser parser = new JsonParser();
				JsonElement root = parser.parse(jsonReader);
				Gson gson = new Gson();
				JsonArray array = root.getAsJsonArray();
				TreeContent content = gson.fromJson(array.get(0), TreeContent.class);
				List<TreeContent> child = content.getChild();
				for (TreeContent detail : child) {
					System.out.println(detail.getKey());
					FileTools.writeAndChangeRow(target, detail.getKey(), true);
				}
				System.out.println("=================================");
			}
		}

	}
}
