package com.law.verdict.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @ClassName: IndexSearchService 
 * @Description: TODO() 
 * @author xiongbz
 * @date May 8, 2018 4:16:54 PM 
 *
 */
@Service
public class IndexSearchService {
	@Autowired
	private RestClient restClient;
	
	private static final String QUERY_METHOD="POST";
	private static final String END_POINT = "/legal/verdict/_search";

	public String queryIndex(String content)  {
		String method = "POST";
		HttpEntity entity = new NStringEntity(content, ContentType.APPLICATION_JSON);
		String result = "";
		Response response = null;
		try {
			response = restClient.performRequest(method, END_POINT, Collections.<String, String>emptyMap(), entity);
			result = EntityUtils.toString(response.getEntity());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
