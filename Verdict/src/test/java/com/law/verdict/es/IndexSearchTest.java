package com.law.verdict.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.law.verdict.VerdictApplication;
import com.law.verdict.service.IndexSearchService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { VerdictApplication.class })
public class IndexSearchTest {

	@Autowired
	private IndexSearchService indexSearchService;
	@Test
	public void testQuery() {
		String content = "{\n" + "  \"query\": {\n" + "    \"match\": {\n" + "      \"user\": \"kimchy\"\n" + "    }\n"
				+ "  }\n" + "}";
		
		String result = indexSearchService.queryIndex(content);
		System.out.println(result);
		
	}
}
